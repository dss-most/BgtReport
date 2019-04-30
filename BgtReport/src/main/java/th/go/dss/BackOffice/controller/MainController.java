package th.go.dss.BackOffice.controller;

import javax.management.QueryExp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysema.query.types.expr.BooleanExpression;
import static com.mysema.query.alias.Alias.$;
import static com.mysema.query.alias.Alias.alias;


import th.go.dss.BackOffice.model.BGT.BudgetUsage;
import th.go.dss.BackOffice.model.PLN.SubProject;


import th.go.dss.BackOffice.repository.BudgetUsageRepository;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired private BudgetUsageRepository budgetUsageRepository;

	
	
}
