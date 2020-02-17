package th.go.dss.BackOffice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import th.go.dss.BackOffice.model.PCM.PurchaseApprovalItemized;
import th.go.dss.BackOffice.model.PCM.PurchaseRequest;

public interface PurchaseRequestRepository extends
		PagingAndSortingRepository<PurchaseRequest, Long>, JpaSpecificationExecutor<PurchaseRequest> {

	@Query("select purchaseRequest " +
			"from PurchaseRequest purchaseRequest " +
			" 	inner join purchaseRequest.subProject subProject " +
			"	inner join subProject.project project " +
			"	inner join purchaseRequest.purchaseApprovals purchaseApprovals " +
			"where subProject.abbr = :subProjectAbbr " +
			"	AND purchaseRequest.status.id <> 21" +
			"	AND purchaseRequest.cancelFlag <> '1' " +
			"	AND ( purchaseApprovals.id is null  " +
//			"		purchaseRequest.id not in " +
//			"			( " +
//			"			select purchaseapproval_1.purchaseRequest.id " +
//			"			from PurchaseApproval purchaseapproval_1" +
//			"			) " +
			"		OR purchaseApprovals.id not in " +
			"			( " +
			"			select budgetUsage_1.purchaseApproval.id " +
			"			from BudgetUsage budgetUsage_1 " +
			"			where budgetUsage_1.purchaseApproval.id is not null " +
			
			"			)" +
			"		)" +
			"	AND project.fiscalYear = :fiscalYear " +
			"")
	Page<PurchaseRequest> findNotYetApproveOrBudgetUsage(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr, 
			Pageable pageSpecification);
	
	
	@Query(""
			+ "SELECT purchaseApprovalItemized "
			+ "FROM  PurchaseApprovalItemized purchaseApprovalItemized "
			+ "	inner join purchaseApprovalItemized.purchaseRequest purchaseRequest "
			+ "	inner join purchaseRequest.subProject subProject "
			+ " inner join subProject.project project "
			+ "WHERE  subProject.abbr = :subProjectAbbr  " 
			+ "	AND purchaseRequest.status.id <> 21 " 
			+ "	AND purchaseRequest.cancelFlag <> '1' "
			+ " AND purchaseApprovalItemized.id not in  "
			+ " ( select budgetUsage_1.purchaseApprovalItemized.id "
			+ "		from BudgetUsage budgetUsage_1"
			+ "		 where budgetUsage_1.purchaseApprovalItemized.id is not null"
			+ " ) "
			+ "AND project.fiscalYear = :fiscalYear  "
			)
	Page<PurchaseApprovalItemized> findPurchaseApprovalItemizedNotYetApproveOrBudgetUsage(
			@Param("fiscalYear") Integer fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr, 
			Pageable pageSpecification);
	
}
