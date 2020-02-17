package th.go.dss.bgtreport.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import th.go.dss.BackOffice.model.HRX.Organization;
import th.go.dss.BackOffice.repository.SubProjectRepository;
import th.go.dss.topbudget.dao.TopBudgetBackEndDao;
import th.go.dss.topbudget.dao.TopBudgetDao;

/**
 * Handles requests for the application home page.
 */
@Controller
@Transactional
public class HomeController {
	
	@Autowired
	private TopBudgetDao topBudgetDao;

	@Autowired
	private TopBudgetBackEndDao topBudgetBackEndDao;
	
	@Autowired
	private SubProjectRepository subProjectRepository;
	
	public static SimpleDateFormat thaiShortDate = new SimpleDateFormat("d MMM yyyy", new Locale("th","TH"));
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	private void addFiscalYearsToModel(Model model) {
		List<Integer> fiscalYears = subProjectRepository.findFiscalYear();
		model.addAttribute("fiscalYears", fiscalYears);
		
	}
	
	@RequestMapping(value = {"/","/home.html"}, method = RequestMethod.GET)
	public String home(Model model) {
		
		this.addFiscalYearsToModel(model);
		// do nothing for now 
		model.addAttribute("homeActive", true);
		model.addAttribute("now", thaiShortDate.format(new Date()));
		return "summary";

	}
	
	@RequestMapping(value="/detail4.html", method =RequestMethod.GET)
	public String detail4(Model model) {
		this.addFiscalYearsToModel(model);
		model.addAttribute("detail4Active", true);
		return "detail4";
	}
	
	@RequestMapping(value="/detail1.html")
	public String detail1(Model model) {
		// detail tab
		this.addFiscalYearsToModel(model);
		model.addAttribute("detail1Active", true);
		return "detail1";
	}
	
	@RequestMapping(value="/detail2.html")
	public String detail2(Model model) {
		// detail tab
		this.addFiscalYearsToModel(model);
		model.addAttribute("detail2Active", true);
		return "detail2";
	}
	
	@RequestMapping(value="/detail3.html")
	public String detail3(Model model) {
		this.addFiscalYearsToModel(model);
		// detail tab
		List<Organization> organizations = subProjectRepository.findOrganizationHasSubProjectByFiscalYear(2558);
		
		model.addAttribute("organizations", organizations);
		model.addAttribute("detail3Active", true);
		return "detail3";
	}
	
	@RequestMapping(value="/loading") 
	public String loading(Model model) {
		return "loading";
	}
	
	@RequestMapping(value="/listing") 
	public String listing(Model model, 
			@RequestParam (required=false) Integer fiscalYear) {
		this.addFiscalYearsToModel(model);
		logger.info("fiscalYear = " + fiscalYear);
		return "listing";
	}
	
}
