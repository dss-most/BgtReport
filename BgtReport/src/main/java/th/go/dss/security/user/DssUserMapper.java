package th.go.dss.security.user;

import java.util.Collection;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.Transactional;

import th.go.dss.BackOffice.model.HRX.Organization;
import th.go.dss.topbudget.dao.BackOfficeDao;

@Transactional
public class DssUserMapper implements UserDetailsContextMapper {
	
	@Autowired
	private BackOfficeDao backOfficeDao;
	public void setBackOfficeDao(BackOfficeDao backOfficeDao) {this.backOfficeDao = backOfficeDao;}
	
	
	private static final  Logger logger = LoggerFactory
			.getLogger(DssUserMapper.class);


	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,
			String username, Collection<? extends GrantedAuthority> authorities) {
		
		String dn = ctx.getNameInNamespace();
		logger.debug(dn);
		
		
		DssUserDetails.Essence essence = new DssUserDetails.Essence();
		essence.setDn(dn);
		essence.setUsername(username);
		
		for(GrantedAuthority authority : authorities) {
			essence.addAuthority(authority);
		}
		
		Integer employeeId = null;
		
		try {
			employeeId = Integer.parseInt(ctx.getStringAttribute("employeeId"));
			essence.addAttribute("employeeId", employeeId);
			Organization internalOrg = backOfficeDao.findOrganizationByEmployeeId(employeeId);
			if (internalOrg != null) {
				essence.addAttribute("internalOrg", internalOrg);
			}
			logger.debug("employeeId: {}", employeeId);
			logger.debug("Organization: {}", internalOrg.getName());
			
		} catch (NumberFormatException e) {
			logger.debug("ctx employeeId: " + ctx.getStringAttribute("employeeId"));
		}
		
		
		DssUserDetails dssUserDetails = essence.createUserDetails();
		
		return dssUserDetails;
	}


	@Override
	public void mapUserToContext(UserDetails userDetails, DirContextAdapter ctx) {
		// DSS user does not need this method
		
	}
	
}
