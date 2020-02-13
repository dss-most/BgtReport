package th.go.dss.BackOffice.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import th.go.dss.BackOffice.model.BGT.BudgetAllocationItem;
import th.go.dss.BackOffice.model.PCM.PurchaseRequest;
import th.go.dss.BackOffice.model.PLN.BudgetAllocationItemWeb;
import th.go.dss.BackOffice.model.PLN.SubProject;
import th.go.dss.BackOffice.model.PLN.SubProjectBudget;
import th.go.dss.BackOffice.repository.SubProjectRepository;

@Controller
public class SubProjectRestController {
	private static final Logger logger = LoggerFactory.getLogger(SubProjectRestController.class);
	private static SimpleDateFormat thaiShortDate = new SimpleDateFormat("d MMM yyyy", new Locale("th","TH"));
	
	@Autowired private SubProjectRepository subProjectRepository;
	
	
	@RequestMapping("/SubProjects/listAllFiscalYear") 
	public @ResponseBody List<Integer> findFiscalYear() {
		List<Integer> fiscalYears = subProjectRepository.findFiscalYear();
		return fiscalYears;
	}
		
	
	
	@RequestMapping("/SubProjects/{fiscalYear}/{organizationId}")
	public @ResponseBody List<SubProject> findByFiscalYearAndOwner(
			@PathVariable Integer fiscalYear,
			@PathVariable Integer organizationId
			){
		List<SubProject> subProjects = subProjectRepository.findByFiscalYearAndOwner(fiscalYear, organizationId);
		
		logger.debug("finding subProject with fiscalYear: " + fiscalYear + " and ownerId: " + organizationId);
		logger.debug("found " + subProjects.size() + " subProject");
		
		return subProjects;
				
	}
	
	@RequestMapping("/SubProjectBudget/{fiscalYear}/{budgetCode}")
	public @ResponseBody List<SubProjectBudget> findByFiscalYearAndBudgetCode(
			@PathVariable Integer fiscalYear, @PathVariable String budgetCode){
			List<SubProjectBudget> list = new ArrayList<SubProjectBudget>();
			
			List<SubProject> spList = subProjectRepository.findByFiscalYear(fiscalYear);
			Hashtable<Integer, SubProjectBudget> hashTable = new Hashtable<Integer, SubProjectBudget>(); 
			
			logger.debug("spList.size() : " + spList.size());
			
			for(SubProject sp : spList) {
				logger.debug("subproject: " + sp.getAbbr() +"("+sp.getId()+")");
				
				SubProjectBudget spb = new SubProjectBudget(sp);
				list.add(spb);
				hashTable.put(sp.getId(), spb);
			}
			
			String budgetCodeLike = budgetCode + "%";
						
			List<Object[]> budgetAlloc = subProjectRepository.findSubProjectBudgetAllocatedByBudgetCode(fiscalYear, budgetCodeLike);
			for(Object[] row : budgetAlloc) {
				Integer spId = (Integer) row[0];
				Double alloc = (Double) row[1];
				Double transfer = (Double) row[2];
				
				SubProjectBudget spb = hashTable.get(spId);
				if(spb != null) {
					spb.setBudgetAllocated(alloc + transfer);
				}
			}
			
			List<Object[]> budgetUsed = subProjectRepository.findSubProjectBudgetUsedByBudgetCode(fiscalYear, budgetCodeLike);
			for(Object[] row : budgetUsed) {
				Integer spId = (Integer) row[0];
				Double used = (Double) row[1];
				Double approved = (Double) row[2];
				
				SubProjectBudget spb = hashTable.get(spId);
				if(spb != null) {
					spb.setBudgetUsed(used);
					if(approved == null) {
						approved = 0.0;
					}
					if(used == null) {
						used = 0.0;
					}
					spb.setBudgetApproved(approved - used); 
				}
			}
			
			List<Object[]> budgetAllocItems = subProjectRepository.findBUdgetAllocationItem(fiscalYear, budgetCodeLike);
			for(Object[] row : budgetAllocItems) {
				Integer spId = (Integer) row[0];
				SubProjectBudget spb = hashTable.get(spId);
				if(spb != null) {
					if( spb.getBudgetAllocationItems() == null ) {
						spb.setBudgetAllocationItems( new ArrayList<BudgetAllocationItemWeb> () );
					}
					
					BudgetAllocationItem item = (BudgetAllocationItem) row[1];
					
					
					PurchaseRequest pr = subProjectRepository.findPurchaseRequestByAllocItem(item);
					
					BudgetAllocationItemWeb newBA = new BudgetAllocationItemWeb(item);
					if(pr!=null) {
						newBA.setPcmNumber(pr.getPcmNumber());
						newBA.setPcmCreatedDate(  thaiShortDate.format(pr.getCreatedDate()));
					}
					
					spb.getBudgetAllocationItems().add(newBA);
					
					  
				}
						
			}
			
			return list;
	}
	
	@ExceptionHandler({NoResultException.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void handleDataFormatException(Exception ex, HttpServletResponse response) {
		logger.info("Can't find ");
	}
	
	@ExceptionHandler({JsonMappingException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleJsonMappingException(Exception ex, HttpServletResponse response) {
		logger.error(ex.getMessage());
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleException(Exception ex, HttpServletResponse response) {
		ex.printStackTrace();
		logger.error(ex.getMessage());
	}
}
