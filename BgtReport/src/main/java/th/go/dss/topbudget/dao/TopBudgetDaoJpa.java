package th.go.dss.topbudget.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import th.go.dss.BackOffice.model.BGT.BudgetCodeType;
import th.go.dss.BackOffice.model.BGT.BudgetUsage;
import th.go.dss.BackOffice.model.PLN.Project;
import th.go.dss.BackOffice.model.PLN.SubProject;
import th.go.dss.bgtreport.controller.HomeController;

public class TopBudgetDaoJpa implements TopBudgetDao {

	@PersistenceContext
	private EntityManager em;
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	private static final Logger logger = LoggerFactory.getLogger(TopBudgetDaoJpa.class);
	
	@Override
	public List<SubProject> findAllSubProject() {
		String queryString = "" +
				"from SubProject subProject ";
		
		Query query = em.createQuery(queryString);
		
		return query.getResultList();
	}


	@Override
	public List<Object[]> findSubProjectTotalBudgetCredit(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(cBudget.amount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"	join ba.creditedBudgetSet as cBudget " +
				"where sp.project.fiscalYear = :fiscalYear ";
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}
		
		
		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}
	
	@Override
	public List<Object[]> findSubProjectTotalBudgetDebit(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(dBudget.amount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"	join ba.debitedBudgetSet as dBudget " +
				"where sp.project.fiscalYear = :fiscalYear ";
				
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}
		
		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}

	@Override
	public List<Object[]> findSubProjectTotalBudgetDebitedItem(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(dBudget.amount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"	join ba.budgetAllocationItemSet as bai " +
				"	join bai.debitedBudgetSet dBudget " +
				"where sp.project.fiscalYear = :fiscalYear ";
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}
		
		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}

	@Override
	public List<Object[]> findSubProjectTotalBudgetReserved(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(bu.approvedAmount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"	join ba.budgetUsageSet bu " +
				"where sp.project.fiscalYear = :fiscalYear and bu.paidAmount is null " +
				"	and bu.cancelFlag is null ";
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}
			
		
		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}

	@Override
	public List<Object[]> findSubProjectTotalBudgetUsed(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(bu.paidAmount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"	join ba.budgetUsageSet bu " +
				"where sp.project.fiscalYear = :fiscalYear and bu.cancelFlag is null ";
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}
		
		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}

	@Override
	public List<Object[]> findSubProjectTotalBudgetAllocation(Integer fiscalYear, BudgetCodeType bCode) {
		String queryString = "" +
				"select sp.id, sum(ba.amount) " +
				"from SubProject as sp " +
				"	join sp.budgetAllocationSet as ba " +
				"where sp.project.fiscalYear = :fiscalYear ";
		
		String queryString2 = "" +
				"group by sp.id " +
				"order by sp.id ";
		
		if(bCode != null) {
			queryString += " and ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		}


		Query query = em.createQuery(queryString + queryString2);
		query.setParameter("fiscalYear", fiscalYear);
		
		return query.getResultList();
	}


	@Override
	public SubProject findSubProjectById(Integer id) {
		String queryString = "" +
				"select sp " +
				"from SubProject as sp " +
				"where sp.id = :id ";
		
		Query query = em.createQuery(queryString);
		query.setParameter("id", id);
		
		return (SubProject) query.getSingleResult();
	}

	@Override
	public SubProject findSubProjectByIdAndBudgetUsed(Integer id,
			BudgetCodeType bCode) {
		String queryString = "" +
				"select sp " +
				"from SubProject as sp " +
				"	join fetch sp.budgetAllocationSet ba " +
				"	left join fetch ba.budgetUsageSet " +
				"where sp.id = :id and " +
				"	ba.budgetCode.code like \'" + bCode.searchString() + "\'";
		
		Query query = em.createQuery(queryString);
		query.setParameter("id", id);
		
		return (SubProject) query.getSingleResult();
	}



}
