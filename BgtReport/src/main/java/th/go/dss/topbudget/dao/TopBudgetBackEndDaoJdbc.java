package th.go.dss.topbudget.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TopBudgetBackEndDaoJdbc implements TopBudgetBackEndDao {

	private static final Logger logger = LoggerFactory.getLogger(TopBudgetBackEndDaoJdbc.class);
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}
	
	private RowMapper<Map<String, Object>> genericRowMapper = new RowMapper<Map<String, Object>>() {
		public Map<String, Object> mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				Map<String, Object> map = new HashMap <String, Object>();
				
				// now get all the column
				ResultSetMetaData rsmd  = rs.getMetaData();
				Integer columnCount = rsmd.getColumnCount();
				for(Integer i=0; i<columnCount;  i++) {
					//logger.debug("geting columnName: " + rsmd.getColumnName(i+1));
					map.put(rsmd.getColumnName(i+1),rs.getObject(i+1));
				}
			return map;
		}
	};
	
	/**
	 * 	public List<Map<String, Object>> OtdExpenseChangeArrival(String year,
			String period, String dataType) {
		List<Map<String, Object>> returnList = this.jdbcTemplate.query(
				"SELECT year, period, country_name, data_type, arrivals, arrivals_per " +
				"FROM OTD_EXPENSE_CHANGE oec, otd_country_report ocr " +
				"WHERE oec.country_id = ocr.country_id and year=? and period=? and data_type=?",
				genericRowMapper,
				year,period,dataType
				);
		return returnList;
	}
	 */
	
	
	@Override
	public List<Map<String, Object>> findBgtHwNoPcm(Integer fiscalYear) {
		String sql1 = "" +
			"SELECT  tran.subject_id as \"หมายเลข PCM\", " +				
			"	pab.appv_code as \"หมายเลข BGT\", " +
			"	 pab.voucher_number as \"หมายเลขงบประมาณรับ\", " +
			"	expt.expense_type_name as \"หมวดรายจ่าย\", " +
			"	exp3.dsp_super_name as \"หมวดค่าใช้จ่าย\", "+
			"	decode(datee2t(pab.vou_date),null,datee2t(pab.appv_date),datee2t(pab.vou_date)) as \"วันที่เบิกจ่าย\" , "+
			"	sub_proj.sub_proj_name as \"ชื่อโครงการ\", "+
			"	sub_proj.sub_proj_abbr as \"ชื่อย่อโครงการ\", "+ 
			"	pab.subject_name as \"ชื่อเรื่องใน BGT\", "+
			"	decode(pab.paid_amt,null,pab.appv_amt,pab.paid_amt)  \"งบประมาณเบิกจ่ายรวมใน BGT\", "+
			"	item.description as \"ชื่อรายการของที่จัดซื้อจัดจ้าง\", "+
			"	item.est_price as \"ราคาต่อหน่วย\", "+
			"	item.total_unit  as \"จำนวน\", "+
			"	item.uom as \"หน่วยนับ\", "+
			"	item.total_bgt  as \"ราคาที่สืบได้รวมต่อรายการ\", "+
			"	replace(decode(ven.company,'ร้านค้าทั่วไป',nvl(emp.emp_name,pab.request_name),decode(pab.vendor_vendor_id,null,nvl(emp.emp_name,pab.request_name),ven.company)),chr(10),'')   as \"ร้านค้า\", "+        
			"	pab.dga_code as \"เลขที่ฎีกา\", "+
			"	datee2t(pab.dga_date) as \"วันที่ฎีกา\", "+
			"	CG_LOW_VALUE2MEANING('BGT_STATUS',pab.status)   as \"สถานะการเบิกจ่าย\", "+
			"   ret_det.AMT as \"เบิกเกินส่งคืน\" "+
			"FROM    pln_appv_bgt pab  "+
			"	,pro_vendor ven "+
			"	,pln_alloc_bgt plab "+
			"	,(  select  decode(exp2.expense_type_code,NULL,exp.expense_type_code,exp2.expense_type_code) dsp_super_code "+ 
			"			,decode(exp2.expense_type_name,NULL,exp.expense_type_name,exp.expense_type_name) dsp_super_name "+ 
			"			,exp1.expense_type_code dsp_expense_type_code "+ 
			"			,substr(exp1.expense_type_name,1,200) dsp_expense_type_name "+ 
			"			,exp1.expense_type_id dsp_expense_type_id  "+
			"			,decode(nvl(exp1.super_parent_id,0),0,exp1.expense_type_id,exp1.super_parent_id) dsp_super_parent_id "+ 
			"			,exp.expense_type_name    "+
			"		FROM  glb_expense_type exp1  "+
			"			, glb_expense_type exp2  "+
			"			, glb_expense_type exp "+
			"		WHERE  (exp2.expense_type_id(+)  = exp1.super_parent_id ) "+
			"			and    (exp.expense_type_id(+) = exp1.expt_expense_type_id) ) exp3 "+
			"		,glb_budget bud "+
			"		,glb_expense_type expt "+
			"		,glb_product prod "+
			"		,glb_project proj "+
			"		,glb_sub_project sub_proj "+
			"		,hr_employee emp "+
			"		,glb_organization org "+
			"		,pro_proc_tran tran "+
			"		,pro_item      item "+
			"		,acc_return_desc ret_det "+
			"WHERE   pab.vendor_vendor_id = ven.vendor_id(+) "+
			"		and     pab.emp_emp_id = emp.emp_id(+) "+
			"		and     plab.alloc_bgt_id = pab.pab_alloc_bgt_id "+
			"		and     plab.budget_budget_id = bud.budget_id "+
			"		and     plab.expt_expense_type_id = expt.expense_type_id "+
			"		and     expt.expense_type_id = exp3.dsp_expense_type_id "+
			"		and     plab.gsp_sub_proj_id = sub_proj.sub_proj_id "+
			"		and     prod.product_id = proj.prod_product_id "+
			"		and     proj.project_id=  sub_proj.proj_project_id "+
			"		and     org.org_id = sub_proj.org_org_id "+
			"		and     ret_det.appv_appv_id(+) = pab.appv_id "+
			"		and     pab.proc_trans_id is not null "+
			"		and     pab.cancel_flg is null "+
			"		and     pab.fiscal_year = ? "+
			"		and     tran.trans_id = pab.proc_trans_id "+
			"		and     tran.trans_id = item.pro_proc_t_trans_id "+
			"		and    tran.cancel_flg = '0' "+
			"";

		return this.jdbcTemplate.query(sql1, genericRowMapper, fiscalYear);
	}

	@Override
	public List<Map<String, Object>> findBgtOtherNoPcm(Integer fiscalYear) {
		String sql = "" +
			"" +
			"SELECT pab.appv_code as \"หมายเลข BGT\", "+
			"	 pab.voucher_number as \"หมายเลขงบประมาณรับ\", " +
	        "	expt.expense_type_name as \"หมวดรายจ่าย\", " +
	        "	exp3.dsp_super_name as \"หมวดค่าใช้จ่าย\", " +
	        "	decode(datee2t(pab.vou_date),null,datee2t(pab.appv_date),datee2t(pab.vou_date)) as \"วันที่เบิกจ่าย\" , " +
	        "	sub_proj.sub_proj_name as \"ชื่อโครงการ\", " +
	        "	sub_proj.sub_proj_abbr as \"ชื่อย่อโครงการ\", " + 
	        "	pab.subject_name as \"ชื่อเรื่องใน BGT\", " +
	        "	decode(pab.paid_amt,null,pab.appv_amt,pab.paid_amt)  \"งบประมาณเบิกจ่ายรวมใน BGT\", " +
	        "		replace(decode(ven.company,'ร้านค้าทั่วไป',nvl(emp.emp_name,pab.request_name),decode(pab.vendor_vendor_id,null,nvl(emp.emp_name,pab.request_name),ven.company)),chr(10),'')   as \"ร้านค้า\", " +        
	        "		pab.dga_code as \"เลขที่ฎีกา\", " +
	        "	datee2t(pab.dga_date) as \"วันที่ฎีกา\", " +
	        "	CG_LOW_VALUE2MEANING('BGT_STATUS',pab.status)   as \"สถานะการเบิกจ่าย\", " +
			"   ret_det.AMT as \"เบิกเกินส่งคืน\" "+
	        "	from    pln_appv_bgt pab , " + 
	        "	pro_vendor ven , " +
	        "	pln_alloc_bgt plab ,         " +
	        "	  (select  decode(exp2.expense_type_code,NULL,exp.expense_type_code,exp2.expense_type_code) dsp_super_code  " +
	        "	          ,decode(exp2.expense_type_name,NULL,exp.expense_type_name,exp.expense_type_name) dsp_super_name " + 
	        "	           ,exp1.expense_type_code dsp_expense_type_code  " +
	        "	          ,substr(exp1.expense_type_name,1,200) dsp_expense_type_name " + 
	        "	         ,exp1.expense_type_id dsp_expense_type_id  " +
	        "	         ,decode(nvl(exp1.super_parent_id,0),0,exp1.expense_type_id,exp1.super_parent_id) dsp_super_parent_id " + 
	        "	        ,exp.expense_type_name    " +
	        "	from       glb_expense_type exp1  " +
	        "	             , glb_expense_type exp2 " + 
	        "	             , glb_expense_type exp " +
	        "	where  (exp2.expense_type_id(+)  = exp1.super_parent_id ) " +
	        "	and    (exp.expense_type_id(+) = exp1.expt_expense_type_id)) exp3, " +
	        "	glb_budget bud, " +
	        "	glb_expense_type expt, " +
	        "	glb_product prod, " +
	        "	glb_project proj, " +
	        "	glb_sub_project sub_proj, " +
	        "	hr_employee emp, " +
	        "	 glb_organization org  , " +
	        "	 acc_return_desc ret_det " +
	        "	where   pab.vendor_vendor_id = ven.vendor_id(+) " +
	        "	and     pab.emp_emp_id = emp.emp_id(+) " +
	        "	and     plab.alloc_bgt_id = pab.pab_alloc_bgt_id " +
	        "	and     plab.budget_budget_id = bud.budget_id " +
	        "	and     plab.expt_expense_type_id = expt.expense_type_id " +
	        "	and     expt.expense_type_id = exp3.dsp_expense_type_id " +
	        "	and     plab.gsp_sub_proj_id = sub_proj.sub_proj_id " +
	        "	and     prod.product_id = proj.prod_product_id " +
	        "	and     proj.project_id=  sub_proj.proj_project_id " +
	        "	and     org.org_id = sub_proj.org_org_id " +
	        "	and     ret_det.appv_appv_id(+) = pab.appv_id " +
	        "	and     pab.proc_trans_id is  null " +
	        "	and    pab.proc_app_id is null " +
	        "	and     pab.cancel_flg is null " +
	        "	and     pab.fiscal_year = ? " +
	        "	order by  to_number(substr(pab.appv_code,instr(pab.appv_code,'/',1,2)+1,length(pab.appv_code))) asc " + 
			"";

		return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear);
	}

	@Override
	public List<Map<String, Object>> findBgtHasPcm(Integer fiscalYear) {
		String sql = "" +
			"select  tran.subject_id as \"หมายเลข PCM\", " + 
		    "    pab.appv_code as \"หมายเลข BGT\", " +
			"	 pab.voucher_number as \"หมายเลขงบประมาณรับ\", " +
		    "    expt.expense_type_name as \"หมวดรายจ่าย\", " + 
		    "    exp3.dsp_super_name as \"หมวดค่าใช้จ่าย\", " + 
		    "    decode(datee2t(pab.vou_date),null,datee2t(pab.appv_date),datee2t(pab.vou_date)) as \"วันที่เบิกจ่าย\" , " + 
		    "    sub_proj.sub_proj_name as \"ชื่อโครงการ\", " + 
		    "    sub_proj.sub_proj_abbr as \"ชื่อย่อโครงการ\", " +  
		    "    pab.subject_name as \"ชื่อเรื่องใน BGT\", " + 
		    "    decode(pab.paid_amt,null,pab.appv_amt,pab.paid_amt)  \"งบประมาณเบิกจ่ายรวมใน BGT\", " + 
		    "    item.description as \"ชื่อรายการของที่จัดซื้อจัดจ้าง\", " + 
		    "    offer.unit_price as \"ราคาต่อหน่วย\", " + 
		    "    item.total_unit  as \"จำนวน\", " + 
		    "    item.uom as \"หน่วยนับ\", " + 
		    "    offer.item_amt  as \"ราคาที่สืบได้รวมต่อรายการ\", " + 
		    "    replace(decode(ven.company,'ร้านค้าทั่วไป',nvl(emp.emp_name,pab.request_name),decode(pab.vendor_vendor_id,null,nvl(emp.emp_name,pab.request_name),ven.company)),chr(10),'')   as \"ร้านค้า\", " +         
		    "    pab.dga_code as \"เลขที่ฎีกา\", " + 
		    "    datee2t(pab.dga_date) as \"วันที่ฎีกา\", " + 
		    "   CG_LOW_VALUE2MEANING('BGT_STATUS',pab.status)   as \"สถานะการเบิกจ่าย\", " +
		    "   ret_det.AMT as \"เบิกเกินส่งคืน\" "+
		    "from    pln_appv_bgt pab , " +  
		    "    pro_vendor ven , " + 
		    "    pln_alloc_bgt plab , " + 
		    "    pln_alloc_bgt_item pabi, " + 
		    "    (select  decode(exp2.expense_type_code,NULL,exp.expense_type_code,exp2.expense_type_code) dsp_super_code  " + 
		    "              ,decode(exp2.expense_type_name,NULL,exp.expense_type_name,exp.expense_type_name) dsp_super_name " +  
		    "               ,exp1.expense_type_code dsp_expense_type_code  " + 
		    "             ,substr(exp1.expense_type_name,1,200) dsp_expense_type_name " +  
		    "             ,exp1.expense_type_id dsp_expense_type_id  " + 
		    "             ,decode(nvl(exp1.super_parent_id,0),0,exp1.expense_type_id,exp1.super_parent_id) dsp_super_parent_id " +  
		    "            ,exp.expense_type_name    " + 
		    "    from       glb_expense_type exp1  " + 
		    "                 , glb_expense_type exp2  " + 
		    "                 , glb_expense_type exp " + 
		    "    where  (exp2.expense_type_id(+)  = exp1.super_parent_id ) " + 
		    "    and    (exp.expense_type_id(+) = exp1.expt_expense_type_id)) exp3, " + 
		    "    glb_budget bud, " + 
		    "    glb_expense_type expt, " + 
		    "    glb_product prod, " + 
		    "    glb_project proj, " + 
		    "    glb_sub_project sub_proj, " + 
		    "    hr_employee emp, " + 
		    "    glb_organization org  , " + 
		    "    pro_proc_tran tran, " + 
		    "    pro_item      item, " + 
		    "    pro_bidder bidder, " + 
		    "    pro_price_offer offer, " + 
		    "    pro_proc_app  app, " + 
		    "    acc_return_desc ret_det " + 
		    "where   pab.vendor_vendor_id = ven.vendor_id(+) " + 
		    "	and     pab.emp_emp_id = emp.emp_id(+) " + 
		    "	and     plab.alloc_bgt_id = pab.pab_alloc_bgt_id " + 
			"	and     plab.alloc_bgt_id = pabi.palb_alloc_bgt_id(+) " + 
			"	and     plab.budget_budget_id = bud.budget_id " + 
			"	and     plab.expt_expense_type_id = expt.expense_type_id " + 
			"	and     expt.expense_type_id = exp3.dsp_expense_type_id " + 
			"	and     plab.gsp_sub_proj_id = sub_proj.sub_proj_id " + 
			"	and     prod.product_id = proj.prod_product_id " + 
			"	and     proj.project_id=  sub_proj.proj_project_id " + 
			"	and     org.org_id = sub_proj.org_org_id " + 
			"	and     ret_det.appv_appv_id(+) = pab.appv_id " + 
			"	and     pab.proc_app_id is not null " + 
			"	and     pab.cancel_flg is null " + 
			"	and     pab.fiscal_year = ? " + 
			"	and     app.app_id = pab.proc_app_id " + 
			"	and     app.pro_proc_t_trans_id = tran.trans_id " + 
			"	and     item.pro_proc_t_trans_id = tran.trans_id " + 
			"	and     bidder.bidder_id = item.bidder_bidder_id " + 
			"	and     offer.bidder_bidder_id = bidder.bidder_id " + 
			"	and     offer.pro_item_item_id = item.item_id " + 
			"	and     app.vendor_vendor_id = bidder.vendor_vendor_id " + 
			"	and     app.pro_proc_t_trans_id = tran.trans_id " + 
			"	order by  \"หมายเลข PCM\", to_number(substr(pab.appv_code,instr(pab.appv_code,'/',1,2)+1,length(pab.appv_code))) asc " + 
			"";
		return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear);
	}

	@Override
	public List<Map<String, Object>> findParentExpenseType(Integer fiscalYear) {
		String sql = "" +
			"select pexpt.expense_type_id as p_expense_type_id, " +
			"	'(' || substr(pexpt.expense_type_code,1,1) || ') ' || pexpt.expense_type_name as p_expense_type_name, " +
			"	pexpt.expense_type_code as p_expense_type_code " +
			"from pln_appv_bgt pab, pln_alloc_bgt plab, glb_expense_type expt, glb_sub_project sproj," +
			"	glb_expense_type pexpt " + 
			"where plab.alloc_bgt_id = pab.pab_alloc_bgt_id and pab.fiscal_year=? " +
			"	and plab.expt_expense_type_id = expt.expense_type_id " +
			"	and plab.gsp_sub_proj_id = sproj.sub_proj_id " +
			"	and pexpt.expense_type_id = expt.super_parent_id " +
			"group by pexpt.expense_type_id, pexpt.expense_type_name, pexpt.expense_type_code " + 
			"order by pexpt.expense_type_id";
		return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear);
	}

	@Override
	public List<Map<String, Object>> findExpenseType(Integer fiscalYear, String parentExpenseTypeCode) {
		String sql = "" +
			"select expt.expense_type_id, expt.expense_type_code, expt.expense_type_name  " +
			"from pln_appv_bgt pab, pln_alloc_bgt plab, glb_expense_type expt, glb_sub_project sproj, " + 
			"	glb_expense_type pexpt " + 
			"where plab.alloc_bgt_id = pab.pab_alloc_bgt_id and pab.fiscal_year=? " +
			"	and plab.expt_expense_type_id = expt.expense_type_id " +
			"	and  plab.gsp_sub_proj_id = sproj.sub_proj_id " +
			"	and pexpt.expense_type_id = expt.super_parent_id " +
			"	and pexpt.expense_type_code = ?  " +
			"group by expt.expense_type_id, expt.expense_type_name, expt.expense_type_code " + 
			"order by expt.expense_type_id";
		return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear, parentExpenseTypeCode);
	}
	
	@Override
	public List<Map<String,Object>> findBGT(Integer fiscalYear, String expenseTypeCode){
		String sql = "" +
			"select   distinct tran.subject_id as \"หมายเลข PCM\", " +  
		    "    pab.appv_code as \"หมายเลข BGT\", " +
			"	 pab.voucher_number as \"เลขงบประมาณรับ\", " +
			" 	 get_desc_appv(pab.appv_id) as \"รายละเอียด\", " +
		    "    expt.expense_type_name as \"หมวดรายจ่าย\", " +
		    "    decode(datee2std(pab.vou_date,'DTE','SHORT_MON','SHORT_YR', ''),null,datee2std(pab.appv_date,'DTE','SHORT_MON','SHORT_YR', ''),datee2std(pab.vou_date,'DTE','SHORT_MON','SHORT_YR', '')) as \"วันที่เบิกจ่าย\" , " +
		    "    sub_proj.sub_proj_abbr as \"ชื่อย่อโครงการ\",  " +
		    "	 sub_proj.sub_proj_name as \"ชื่อโครงการ\", " +
		    "    pab.subject_name as \"ชื่อเรื่องใน BGT\", " +
		    "    decode(pab.paid_amt,null,pab.appv_amt,pab.paid_amt)  \"งบประมาณเบิกจ่ายรวมใน BGT\", " +
		    "    replace(decode(ven.company,'ร้านค้าทั่วไป',nvl(emp.emp_name,pab.request_name),decode(pab.vendor_vendor_id,null,nvl(emp.emp_name,pab.request_name),ven.company)),chr(10),'')   as \"ร้านค้า\", " +
		    "	 get_inv_appv(pab.appv_id) as \"รายละเอียดใบเสร็จรับเงิน\", "	+
		    " 	 pab.gf_code as \"เลข GF_DOC\", " +
		    "	 datee2std(pab.gf_date,'DTE','SHORT_MON','SHORT_YR', '') as \"วันที่ GF_DOC\", "	+
		    "    pab.dga_code as \"เลขที่ฎีกา\", " +
		    "    datee2std(pab.dga_date,'DTE','SHORT_MON','SHORT_YR', '') as \"วันที่ฎีกา\", " +
		    "    CG_LOW_VALUE2MEANING('BGT_STATUS',pab.status)   as \"สถานะการเบิกจ่าย\", " +
		    "	 pab.cancel_flg as \"ยกเลิก\", " +
		    "	 pab.remark as \"หมายเหตุ\", "+
		    "   ret_det.AMT as \"เบิกเกินส่งคืน\" "+
			"from    pln_appv_bgt pab ,  " +
			"	pro_vendor ven , " +
		    "    pln_alloc_bgt plab , " +
		    "    pln_alloc_bgt_item pabi, " +
		    "    glb_budget bud, " +
		    "    glb_expense_type expt, " +
		    "    glb_product prod, " +
		    "    glb_project proj, " +
		    "    glb_sub_project sub_proj, " +
		    "    hr_employee emp, " +
		    "    glb_organization org  ," +
		    "    pro_proc_tran tran, " +
		    "    pro_proc_app  app, " +
		    "    acc_return_desc ret_det " +
		    "where   pab.vendor_vendor_id = ven.vendor_id(+) " +
			"	and     pab.emp_emp_id = emp.emp_id(+) " +
			"	and     plab.alloc_bgt_id = pab.pab_alloc_bgt_id " +
			"	and     plab.alloc_bgt_id = pabi.palb_alloc_bgt_id(+) " +
			"	and     plab.budget_budget_id = bud.budget_id " +
			"	and     plab.expt_expense_type_id = expt.expense_type_id " +
			"	and     plab.gsp_sub_proj_id = sub_proj.sub_proj_id " +
			"	and     prod.product_id = proj.prod_product_id " +
			"	and     proj.project_id=  sub_proj.proj_project_id " +
			"	and     org.org_id = sub_proj.org_org_id " +
			"	and     ret_det.appv_appv_id(+)= pab.appv_id " +
			"	and     pab.fiscal_year = ? " +
			" 	and 	expt.expense_type_code like ? " +
			"	and     app.app_id (+) = pab.proc_app_id " +
			"	and     app.pro_proc_t_trans_id = tran.trans_id(+) " +
			"	order by  to_number(substr(pab.appv_code,instr(pab.appv_code,'/',1,2)+1,length(pab.appv_code))) asc " +
			"";
		if(expenseTypeCode == null ) {
			expenseTypeCode = "%";
		}
		
		return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear, expenseTypeCode);
	}

	@Override
	public List<Map<String, Object>> findBGTUsed(Integer fiscalYear,
			String parentExpenseTypeCode) {
		String sql = "" +
				"select sproj.sub_proj_abbr, sum(pab.paid_amt), sum(plab.alloc_amount) " +
				"	from pln_appv_bgt pab, " + 
				" 	pln_alloc_bgt plab, " +
				"	glb_expense_type expt," +
				"	glb_expense_type pexpt, " +
				"	glb_sub_project sproj " +
				"where plab.fiscal_year=? " +
				"	and pab.pab_alloc_bgt_id(+) = plab.alloc_bgt_id " +
				"	and plab.expt_expense_type_id = expt.expense_type_id " +
				"	and  plab.gsp_sub_proj_id = sproj.sub_proj_id " +
				"	and pexpt.expense_type_id = expt.super_parent_id " +
				"	and pexpt.expense_type_code = ?  " +
				"group by sproj.sub_proj_abbr";
			if(parentExpenseTypeCode == null || parentExpenseTypeCode.trim().length() ==0 ) {
				parentExpenseTypeCode = "%";
			}
			return this.jdbcTemplate.query(sql, genericRowMapper, fiscalYear, parentExpenseTypeCode);
	}

	@Override
	public List<Map<String, Object>> findBgtAll(Integer fiscalYear) {
		return findBGT(fiscalYear, null);
	}
}
