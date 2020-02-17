package th.go.dss.BackOffice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import th.go.dss.BackOffice.model.BGT.BudgetUsage;

public interface BudgetUsageRepository extends
		PagingAndSortingRepository<BudgetUsage, Integer> {

	@Query("select budgetUsage " +
			"from BudgetUsage budgetUsage " +
			"	inner join budgetUsage.budgetAllocation budgetAllocation " +
			" 	inner join budgetAllocation.subProject subProject " +
			" 	inner join subProject.project project " +
			"where subProject.abbr = :subProjectAbbr AND project.fiscalYear = :fiscalYear " +
			"	AND budgetUsage.cancelFlag is null " +
			"")
	Page<BudgetUsage> findBySubProjectAndFiscalYear(
			@Param("subProjectAbbr") String subProjectAbbr, 
			@Param("fiscalYear") Integer fiscalYear,
			Pageable pageSpecification);

	
	@Query("select budgetUsage " +
			"from BudgetUsage budgetUsage " +
			"	inner join budgetUsage.budgetAllocation budgetAllocation " +
			" 	inner join budgetAllocation.subProject subProject " +
			" 	inner join subProject.project project " +
			"where budgetUsage.voucherNumber is null " +
			"	AND budgetUsage.status = 2" +
			"	AND project.fiscalYear = :fiscalYear " +
			"	AND subProject.abbr like :subProjectAbbr " +
			"	AND budgetUsage.cancelFlag is null " +
			"")
		Page<BudgetUsage> findSubmitNoVoucherNumber(@Param("fiscalYear") Integer fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr,
			Pageable pageSpecification);
	
	
	@Query("select budgetUsage " +
			"from BudgetUsage budgetUsage " +
			"	inner join budgetUsage.budgetAllocation budgetAllocation " +
			" 	inner join budgetAllocation.subProject subProject " +
			" 	inner join subProject.project project " +
			"where budgetUsage.voucherNumber is not null " +
			"	AND budgetUsage.dgaCode is not null " +
			"	AND project.fiscalYear = :fiscalYear " +
			"	AND subProject.abbr like :subProjectAbbr " +
			"	AND budgetUsage.cancelFlag is null "
			+ "order by budgetUsage.id desc " +
			"")
	Page<BudgetUsage> findVoucherNumberDGA(@Param("fiscalYear") Integer fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr,
			Pageable pageSpecification);
	
	@Query("select budgetUsage " +
			"from BudgetUsage budgetUsage " +
			"	inner join budgetUsage.budgetAllocation budgetAllocation " +
			" 	inner join budgetAllocation.subProject subProject " +
			" 	inner join subProject.project project " +
			"where budgetUsage.voucherNumber is not null " +
			"	AND budgetUsage.dgaCode is null " +
			"	AND project.fiscalYear = :fiscalYear " +
			"	AND subProject.abbr like :subProjectAbbr " +
			"	AND budgetUsage.cancelFlag is null "
			+ "order by budgetUsage.id desc " +
			"")
	Page<BudgetUsage> findVoucherNumberNoDGA(@Param("fiscalYear") Integer fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr,
			Pageable pageSpecification);
	
	@Query("select budgetUsage " +
			"from BudgetUsage budgetUsage " +
			"	inner join budgetUsage.budgetAllocation budgetAllocation " +
			" 	inner join budgetAllocation.subProject subProject " +
			" 	inner join subProject.project project " +
			"where subProject.abbr = :subProjectAbbr " +
			"	AND budgetUsage.dgaCode is null " +
			"	AND project.fiscalYear = :fiscalYear " +
			"	AND budgetUsage.cancelFlag is null " +
			"")
	Page<BudgetUsage> findBudgetUsageNoDGABySubProject(
			@Param("fiscalYear") String fiscalYear,
			@Param("subProjectAbbr") String subProjectAbbr, 
			Pageable pageSpecification);
	

}
