package th.go.dss.bgtreport.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import th.go.dss.topbudget.dao.TopBudgetBackEndDao;
import th.go.dss.topbudget.dao.TopBudgetDao;
import th.go.dss.topbudget.view.BgtHwNoPcmListExcelView;

@Controller
@Transactional
public class ExcelReportController {
	
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

	private static final Logger logger = LoggerFactory.getLogger(ExcelReportController.class);
	

	@RequestMapping(value="/bgtHwNoPcmList.xls") 
	public String bgtHwNoPcmListExcel(@RequestParam Integer fiscalYear, Model model) {

		logger.debug("fiscalYear=" + fiscalYear);
		List<Map<String, Object>> bgtHwNoPcmList = topBudgetBackEndDao.findBgtHwNoPcm(fiscalYear);
		
		logger.debug("list lenght = " + bgtHwNoPcmList.size());
		
		Map<String, Object> row = bgtHwNoPcmList.get(0);
			for(String key : row.keySet()) {
				logger.debug(key);
			}
		
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("bgtHwNoPcmList", bgtHwNoPcmList);
		
		return "bgtHwNoPcmList.xls";
	}
	
	@RequestMapping(value="/bgtOtherNoPcm.xls") 
	public String bgtOtherNoPcmExcel(@RequestParam Integer fiscalYear, Model model) {

		logger.debug("fiscalYear=" + fiscalYear);
		List<Map<String, Object>> bgtOtherNoPcmList = topBudgetBackEndDao.findBgtOtherNoPcm(fiscalYear);
		
		logger.debug("list lenght = " + bgtOtherNoPcmList.size());
		
		Map<String, Object> row = bgtOtherNoPcmList.get(0);
			for(String key : row.keySet()) {
				logger.debug(key);
			}
		
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("bgtOtherNoPcmList", bgtOtherNoPcmList);
		
		return "bgtOtherNoPcm.xls";
	}
	
	@RequestMapping(value="/bgtHasPcm.xls") 
	public String bgtHasPcmExcel(@RequestParam Integer fiscalYear, Model model) {

		logger.debug("fiscalYear=" + fiscalYear);
		List<Map<String, Object>> bgtHasPcmList = topBudgetBackEndDao.findBgtHasPcm(fiscalYear);
		
		logger.debug("list lenght = " + bgtHasPcmList.size());
		
		Map<String, Object> row = bgtHasPcmList.get(0);
			for(String key : row.keySet()) {
				logger.debug(key);
			}
		
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("bgtHasPcmList", bgtHasPcmList);
		
		return "bgtHasPcm.xls";
	}
	
	@RequestMapping(value="/bgtAll.xls") 
	public String bgtAllExcel(@RequestParam Integer fiscalYear, Model model) {

		logger.debug("fiscalYear=" + fiscalYear);
		List<Map<String, Object>> bgtAllList = topBudgetBackEndDao.findBgtAll(fiscalYear);
		
		logger.debug("list lenght = " + bgtAllList.size());
		
		Map<String, Object> row = bgtAllList.get(0);
			for(String key : row.keySet()) {
				logger.debug(key);
			}
		
		model.addAttribute("fiscalYear", fiscalYear);
		model.addAttribute("bgtAllList", bgtAllList);
		
		return "bgtAll.xls";
	}
	
}
