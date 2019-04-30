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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import th.go.dss.BackOffice.model.BGT.BudgetUsage;
import th.go.dss.BackOffice.repository.BudgetUsageRepository;

@Controller
public class BudgetUsageRestController {

	private static final Logger logger = LoggerFactory.getLogger(BudgetUsageRestController.class);
	
	@Autowired private BudgetUsageRepository budgetUsageRepository;
	
	@RequestMapping(value="/BudgetUsage/{id}", method=RequestMethod.GET) 
	public @ResponseBody BudgetUsage getBudgetUsageById(
			@PathVariable Integer id) {
		BudgetUsage bu =  budgetUsageRepository.findOne(id);

		if(bu == null) {
			throw new NoResultException();
		}
		
		logger.debug("found : " + bu.getDescription());

		return bu;
	}
	
	@RequestMapping(value="/BudgetUsages/{fiscalYear}/{subProjectAbbr}", method=RequestMethod.GET) 
	public @ResponseBody Page<BudgetUsage> getBudgetUsageByFiscalYearAndSubProject(
			@PathVariable Integer fiscalYear,
			@PathVariable String subProjectAbbr,
			@RequestParam(required=false) Integer index
			){
		
			
		if(index == null ) {
			index=0;
		}
			
		Pageable pageSpecification = new PageRequest(0, 20, new Sort(Direction.DESC, "id") );
		
		logger.debug("testing");
		
		Page<BudgetUsage> page = budgetUsageRepository.findBySubProjectAndFiscalYear(
				subProjectAbbr, fiscalYear, pageSpecification);
		
		//logger.debug("-> " + page.getTotalElements());
		return page;
		
	}
	
	@RequestMapping(value="/BudgetUsages/{fiscalYear}/{subProjectAbbr}/findVoucherNumberNoDGA",method=RequestMethod.GET)
	public @ResponseBody Page<BudgetUsage> getBudgetUsagewithCheckStatusAndNoDGA(
			@PathVariable Integer fiscalYear,
			@PathVariable String subProjectAbbr,
			@RequestParam(required=false) Integer index
			){
		
		
		if(index == null ) {
			index=0;
		}
		
		if("ALL_SUBPROJECT".equals(subProjectAbbr)) {
			subProjectAbbr = "%";
		} else {
			// now change __ back to /
			subProjectAbbr = subProjectAbbr.replaceAll("__", "/");
			logger.debug("subProjectAbbr = " + subProjectAbbr);
		}
		
		Pageable pageSpecification = new PageRequest(index, 20, new Sort(Direction.DESC, "voucherDate") );
		
		Page<BudgetUsage> page = budgetUsageRepository.findVoucherNumberNoDGA
				(fiscalYear, subProjectAbbr, pageSpecification);
		
		// now we'll get the corresponding project
		for(BudgetUsage buLoop : page.getContent()) {
			buLoop.setCreatedDate(Timestamp.valueOf(buLoop.getCreatedDate().toString()));
			logger.debug(buLoop.getVoucherDate().toString());
			buLoop.getSubProjectAbbr();
			buLoop.getCreatedByUser().getLoginName();
		}
		
		return page;
	}

	
	//findSubmitNoVoucherNumber
	@RequestMapping(value="/BudgetUsages/{fiscalYear}/{subProjectAbbr}/findSubmitNoVoucherNumber",method=RequestMethod.GET)
	public @ResponseBody Page<BudgetUsage> getBudgetUsagewithSubmitNoVoucherNumber(
			@PathVariable Integer fiscalYear,
			@PathVariable String subProjectAbbr,
			@RequestParam(required=false) Integer index
			){
		
		if(index == null ) {
			index=0;
		}
		
		if("ALL_SUBPROJECT".equals(subProjectAbbr)) {
			subProjectAbbr = "%";
		} else {
			// now change __ back to /
			subProjectAbbr = subProjectAbbr.replaceAll("__", "/");
			logger.debug("subProjectAbbr = " + subProjectAbbr);
		}
		
		Pageable pageSpecification = new PageRequest(index, 20, new Sort(Direction.DESC, "id") );
		
		Page<BudgetUsage> page = budgetUsageRepository.findSubmitNoVoucherNumber(
				fiscalYear, subProjectAbbr, pageSpecification);
		
		// now we'll get the corresponding project
		for(BudgetUsage buLoop : page.getContent()) {
			buLoop.setCreatedDate(Timestamp.valueOf(buLoop.getCreatedDate().toString()));
			logger.debug("-- " +  buLoop.getCreatedDate().getClass().toString() + " -- " +  buLoop.getCreatedDate().toString() + "(" + (Long) buLoop.getCreatedDate().getTime() + ")");
			buLoop.getSubProjectAbbr();
			buLoop.getCreatedByUser().getLoginName();
			
		}
		
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
