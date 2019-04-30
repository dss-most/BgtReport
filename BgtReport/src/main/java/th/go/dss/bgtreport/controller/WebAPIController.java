package th.go.dss.bgtreport.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import th.go.dss.topbudget.dao.TopBudgetBackEndDao;
import th.go.dss.topbudget.dao.TopBudgetDao;

@Controller
@Transactional
public class WebAPIController {
	@Autowired
	private TopBudgetDao topBudgetDao;
	
	public TopBudgetDao getTopBudgetDao() {
		return topBudgetDao;
	}

	public void setTopBudgetDao(TopBudgetDao topBudgetDao) {
		this.topBudgetDao = topBudgetDao;
	}
	
	@Autowired
	private TopBudgetBackEndDao topBudgetBackEndDao;
	

	public TopBudgetBackEndDao getTopBudgetBackEndDao() {
		return topBudgetBackEndDao;
	}

	public void setTopBudgetBackEndDao(TopBudgetBackEndDao topBudgetBackEndDao) {
		this.topBudgetBackEndDao = topBudgetBackEndDao;
	}

	private static final Logger logger = LoggerFactory.getLogger(WebAPIController.class);
	
	@RequestMapping(value="/api/json/findParentExpenseType") 
	public @ResponseBody List<Map<String,Object>> findParentExpenseType(
			@RequestParam Integer fiscalYear) {
		return topBudgetBackEndDao.findParentExpenseType(fiscalYear);
	}
	
	@RequestMapping(value="/api/json/findExpenseType")
	public @ResponseBody List<Map<String, Object>> findExpenseType(
			@RequestParam Integer fiscalYear,
			@RequestParam String parentExpenseTypeCode) {
		return topBudgetBackEndDao.findExpenseType(fiscalYear, parentExpenseTypeCode);
	}
	
	@RequestMapping(value="/api/json/findBGTByFiscalYearExpenseType")
	public @ResponseBody List<Map<String, Object>> findBGTByFiscalYearExpenseType(
			@RequestParam Integer fiscalYear,
			@RequestParam String parentExpenseTypeCode) {
		return topBudgetBackEndDao.findBGT(fiscalYear, parentExpenseTypeCode);
	}
	
}
