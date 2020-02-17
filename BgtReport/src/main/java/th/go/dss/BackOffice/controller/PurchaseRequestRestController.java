package th.go.dss.BackOffice.controller;

import java.sql.Timestamp;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import th.go.dss.BackOffice.model.PCM.PurchaseApprovalItemized;
import th.go.dss.BackOffice.model.PCM.PurchaseRequest;
import th.go.dss.BackOffice.repository.PurchaseRequestRepository;


@Controller
public class PurchaseRequestRestController {
	private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestRestController.class);
	
	@Autowired private PurchaseRequestRepository purchaseRequestRepository;
	
	
	@RequestMapping("/PurchaseRequests/{fiscalYear}/{subProjectAbbr}/findItemizedNotYetApproveOrBudgetUsage")
	@Transactional
	public @ResponseBody Page<PurchaseApprovalItemized> findItemizedNotYetApproveOrBudgetUsage(
			@PathVariable String subProjectAbbr,
			@PathVariable Integer fiscalYear,
			@RequestParam(required=false) Integer index){
		
		if(index == null) {
			index = 0;
		}
		
		if("ALL_SUBPROJECT".equals(subProjectAbbr)) {
			subProjectAbbr = "%";
		} else {
			// now change __ back to /
			subProjectAbbr = subProjectAbbr.replaceAll("__", "/");
			logger.debug("subProjectAbbr = " + subProjectAbbr);
		}
		
		Pageable pageSpecification = new PageRequest(0, 2000, new Sort(Direction.DESC, "id") );
		
		logger.debug("testing");
		
		Page<PurchaseApprovalItemized> page = purchaseRequestRepository.findPurchaseApprovalItemizedNotYetApproveOrBudgetUsage(
				fiscalYear, subProjectAbbr, pageSpecification);
				
		for(PurchaseApprovalItemized pai : page) {
			pai.getPurchaseRequest();
			logger.debug("-> " + pai.getPurchaseRequest().getPcmNumber());
			pai.getPurchaseRequest().setCreatedDate(Timestamp.valueOf(pai.getPurchaseRequest().getCreatedDate().toString()));
			Integer s = pai.getPurchaseRequest().getPurchaseLineItems().size();
			logger.debug("  purchaseLineItems size = " +  s);
			if(pai.getPurchaseRequest().getPurchaseApprovals().size()<1) {
				pai.getPurchaseRequest().getSubProjectAbbr();
			}	
		}
		
		//logger.debug("-> " + page.getTotalElements());
		return page;
		
	}
	
	@RequestMapping("/PurchaseRequests/{fiscalYear}/{subProjectAbbr}/findNotYetApproveOrBudgetUsage")
	@Transactional
	public @ResponseBody Page<PurchaseRequest> findNotYetApproveOrBudgetUsage(
			@PathVariable String subProjectAbbr,
			@PathVariable Integer fiscalYear,
			@RequestParam(required=false) Integer index){
		
		if(index == null) {
			index = 0;
		}
		
		if("ALL_SUBPROJECT".equals(subProjectAbbr)) {
			subProjectAbbr = "%";
		} else {
			// now change __ back to /
			subProjectAbbr = subProjectAbbr.replaceAll("__", "/");
			logger.debug("subProjectAbbr = " + subProjectAbbr);
		}
		
		Pageable pageSpecification = new PageRequest(0, 2000, new Sort(Direction.DESC, "id") );
		
		logger.debug("testing");
		
		Page<PurchaseRequest> page = purchaseRequestRepository.findNotYetApproveOrBudgetUsage(
				fiscalYear, subProjectAbbr, pageSpecification);
				
		for(PurchaseRequest purchaseRequest : page) {
			logger.debug("-> " + purchaseRequest.getPcmNumber());
			purchaseRequest.setCreatedDate(Timestamp.valueOf(purchaseRequest.getCreatedDate().toString()));
			Integer s = purchaseRequest.getPurchaseLineItems().size();
			logger.debug("  purchaseLineItems size = " +  s);
			if(purchaseRequest.getPurchaseApprovals().size()<1) {
				purchaseRequest.getSubProjectAbbr();
			}	
		}
		
		//logger.debug("-> " + page.getTotalElements());
		return page;
		
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
		logger.error(ex.getMessage());
	}
}
