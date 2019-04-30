package th.go.dss.topbudget.dao;

import th.go.dss.BackOffice.model.HRX.Organization;

public interface BackOfficeDao {
	public Organization findOrganizationByEmployeeId(Integer employeeId);
}
