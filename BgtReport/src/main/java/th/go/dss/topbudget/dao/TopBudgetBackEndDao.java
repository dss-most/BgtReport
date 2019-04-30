package th.go.dss.topbudget.dao;

import java.util.List;
import java.util.Map;

public interface TopBudgetBackEndDao {

	List<Map<String, Object>> findBgtAll(Integer fiscalYear);
	List<Map<String,Object>> findBgtHwNoPcm(Integer fiscalYear);
	List<Map<String,Object>> findBgtOtherNoPcm(Integer fiscalYear);
	List<Map<String,Object>> findBgtHasPcm(Integer fiscalYear);
	
	List<Map<String,Object>> findParentExpenseType(Integer fiscalYear);
	
	List<Map<String,Object>> findExpenseType(Integer fiscalYear, String parentExpenseTypeCode);
	
	List<Map<String,Object>> findBGT(Integer fiscalYear, String expenseTypeCode);
	
	List<Map<String,Object>> findBGTUsed(Integer fiscalYear, String parentExpenseTypeCode);
	
}
