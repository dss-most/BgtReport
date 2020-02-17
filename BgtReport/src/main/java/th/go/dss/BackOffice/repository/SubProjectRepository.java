package th.go.dss.BackOffice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import th.go.dss.BackOffice.model.BGT.BudgetAllocationItem;
import th.go.dss.BackOffice.model.HRX.Organization;
import th.go.dss.BackOffice.model.PCM.PurchaseRequest;
import th.go.dss.BackOffice.model.PLN.SubProject;

public interface SubProjectRepository extends
		PagingAndSortingRepository<SubProject, Long>, JpaSpecificationExecutor<SubProject> {
	
	@Query("" +
			"SELECT subProject " +
			"FROM SubProject subProject " +
			"	INNER JOIN subProject.project project " +
			"	INNER JOIN project.product product " +
			"WHERE project.fiscalYear = :fiscalYear " +
			"	AND subProject.owner is not null "	+
//			"	AND product.code like '19003%' " +
			"ORDER BY subProject.abbr ASC " +
			"")
	public List<SubProject> findByFiscalYear(@Param("fiscalYear") Integer fiscalYear);
	
	@Query("" +
			"SELECT subProject " +
			"FROM SubProject subProject " +
			"	INNER JOIN subProject.project project " +
			"	INNER JOIN project.product product " +
			"WHERE project.fiscalYear = :fiscalYear " +
			"	AND subProject.owner.id = :ownerId " +
//			"	AND product.code like '19003%' " +
			"ORDER BY subProject.abbr ASC " +
			"")
	public List<SubProject> findByFiscalYearAndOwner(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("ownerId") Integer ownerId);
	
	@Query("" +
			"SELECT subProject.id, sum(COALESCE(ba.amount,0)), sum(COALESCE(ba.transferAmount,0)) " +
			"FROM SubProject subProject " +
			"	INNER JOIN subProject.project project " +
			"	INNER JOIN project.product product " +
			"	INNER JOIN subProject.budgetAllocationSet ba " +
			"	INNER JOIN ba.budgetCode budgetCode " +
			"WHERE project.fiscalYear = :fiscalYear " +
			" 	AND budgetCode.code like :budgetCode " +
			"GROUP BY subProject")
	public  List<Object[]> findSubProjectBudgetAllocatedByBudgetCode(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("budgetCode") String budgetCode);
	
	
	@Query("" +
			"SELECT subProject.id, sum(bu.paidAmount), sum(bu.approvedAmount) " +
			"FROM SubProject subProject " +
			"	INNER JOIN subProject.project project " +
			"	INNER JOIN project.product product " +
			"	INNER JOIN subProject.budgetAllocationSet ba " +
			"	INNER JOIN ba.budgetUsageSet bu" +
			"	INNER JOIN ba.budgetCode budgetCode " +
			"WHERE project.fiscalYear = :fiscalYear " +
			" 	AND budgetCode.code like :budgetCode " +
			"	AND bu.cancelFlag is null " +
			"GROUP BY subProject")
	public  List<Object[]> findSubProjectBudgetUsedByBudgetCode(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("budgetCode") String budgetCode);
	
	@Query("" +
			"SELECT DISTINCT owner " +
			"FROM SubProject subProject " +
			"	INNER JOIN subProject.project project " +
			"	INNER JOIN subProject.owner owner " +
			"WHERE project.fiscalYear = :fiscalYear " +
			"ORDER BY owner.id ASC " +
			"")
	public List<Organization> findOrganizationHasSubProjectByFiscalYear(@Param("fiscalYear") Integer fiscalYerar);

	
	@Query("" +
			"SELECT subProject.id, sum(COALESCE(debit.amount,0)) " +
			"FROM DebitedBudget debit " +
			"	INNER JOIN debit.budgetAllocation ba " +
			"	INNER JOIN ba.budgetCode budgetCode " +
			"	INNER JOIN ba.subProject subProject " +
			"	INNER JOIN subProject.project project " +	
			"WHERE project.fiscalYear = :fiscalYear " +
			" 	AND budgetCode.code like :budgetCode " +
			"GROUP BY subProject")
	public List<Object[]> findSubProjectBudgetDebitByBudgetCode(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("budgetCode") String budgetCode);
	
	@Query("" +
			"SELECT subProject.id, item " +
			"FROM  BudgetAllocationItem item " +
			"	INNER JOIN item.budgetAllocation allocation " +
			"	INNER JOIN allocation.budgetCode budgetCode " +
			"	INNER JOIN allocation.subProject subProject " +
			"	INNER JOIN subProject.project project "  +
			"WHERE project.fiscalYear = :fiscalYear " +
			" 	AND budgetCode.code like :budgetCode" )
	public List<Object[]> findBUdgetAllocationItem(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("budgetCode") String budgetCode);
	
	@Query("" +
			"SELECT req " +
			"FROM PurchaseRequest req " +
			"	INNER JOIN req.purchaseLineItems lineItem " +
			"	INNER JOIN lineItem.allocationItem item " +
			"	INNER JOIN req.purchaseApprovals approvals " +
			"	LEFT OUTER JOIN approvals.budgetUsage bu " +
			"WHERE item = ?1 " )
	public PurchaseRequest findPurchaseRequestByAllocItem(BudgetAllocationItem item);

	
	@Query(""
			+ "SELECT DISTINCT project.fiscalYear "
			+ "FROM  Project project "
			+ "WHERE project.fiscalYear is not null "
			+ "ORDER BY project.fiscalYear desc ")
	public List<Integer> findFiscalYear();
	
	
	@Query(""
			+ "SELECT DISTINCT owner "
			+ "FROM SubProject subProject "
			+ "	INNER JOIN subProject.owner owner "
			+ "WHERE owner is not null "
			+ "	AND subProject.project.fiscalYear = ?1 "
			+ "ORDER BY owner.id asc ")	
	public List<Organization> findAllOrganizationFromFiscalYear(Integer fiscalYear);
	
	
}
 