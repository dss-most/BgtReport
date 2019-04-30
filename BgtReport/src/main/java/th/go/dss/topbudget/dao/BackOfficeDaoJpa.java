package th.go.dss.topbudget.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import th.go.dss.BackOffice.model.HRX.Organization;

public class BackOfficeDaoJpa implements BackOfficeDao {

	@PersistenceContext
	private EntityManager em;
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Organization findOrganizationByEmployeeId(Integer employeeId) {
		String queryString = "" +
				"select emp.currentPosition.internalOrg " +
				"from Employee emp " +
				"where emp.id = :employeeId ";
		
		Query query = em.createQuery(queryString);
		query.setParameter("employeeId", employeeId);
		
		
		return (Organization) query.getSingleResult();
	}

}
