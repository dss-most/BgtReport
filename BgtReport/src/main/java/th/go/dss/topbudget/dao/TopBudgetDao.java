package th.go.dss.topbudget.dao;

import java.util.List;

import th.go.dss.BackOffice.model.BGT.BudgetCodeType;
import th.go.dss.BackOffice.model.BGT.BudgetUsage;
import th.go.dss.BackOffice.model.PLN.Project;
import th.go.dss.BackOffice.model.PLN.SubProject;

public interface TopBudgetDao {
	public List<SubProject> findAllSubProject();
	
	
	public List<Object[]> findSubProjectTotalBudgetAllocation(Integer fiscalYear, BudgetCodeType bCode);
	
	public List<Object[]> findSubProjectTotalBudgetCredit(Integer fiscalYear, BudgetCodeType bCode);
	
	public List<Object[]> findSubProjectTotalBudgetDebit(Integer fiscalYear, BudgetCodeType bCode);

	public List<Object[]> findSubProjectTotalBudgetDebitedItem(Integer fiscalYear, BudgetCodeType bCode);

	public List<Object[]> findSubProjectTotalBudgetReserved(Integer fiscalYear, BudgetCodeType bCode);

	public List<Object[]> findSubProjectTotalBudgetUsed(Integer fiscalYear, BudgetCodeType bCode);


	public SubProject findSubProjectById(Integer id);

	public SubProject findSubProjectByIdAndBudgetUsed(Integer id,
			BudgetCodeType bCodeType);
	
	
 	
}
