package th.go.dss.BackOffice.model.BGT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BudgetCodeType {
	
	ทุกหมวดรายจ่าย(0),			
	เงินเดือนและค่าจ้างประจำ(1),	// 2009
	ค่าตอบแทนและใช้สอย(2),		// 2023
	ลงทุน(3),					// 2056
	อุดหนุน(4),					// 
	รายจ่ายอื่น(5); 
	
	private final Integer id;
	
	BudgetCodeType(Integer id) {
		this.id = id;
	}
	
	public static BudgetCodeType getBudgetCodeType(Integer id) {
		switch (id) {
		case 0: 
			return ทุกหมวดรายจ่าย;
		case 1: 
			return เงินเดือนและค่าจ้างประจำ;
		case 2:
			return ค่าตอบแทนและใช้สอย;
		case 3:
			return ลงทุน;
		case 4:
			return อุดหนุน;
		case 5:
			return รายจ่ายอื่น;
		default:
			return null;
		}
	}
	
	public String toString() {
		switch (id) {
		case 0:
			return "ทุกหมวดรายจ่าย";
		case 1: 
			return "งบเงินเดือนและค่าจ้างประจำ";
		case 2:
			return "งบค่าตอบแทนและใช้สอย";
		case 3:
			return "งบลงทุน";
		case 4:
			return "งบอุดหนุน";
		case 5:
			return "งบรายจ่ายอื่น";
		default:
			return null;
		}
	}

	public String searchString() {
		switch (id) {
		case 0:
			return "%";
		case 1: 
			return "1%";
		case 2:
			return "2%";
		case 3:
			return "3%";
		case 4:
			return "4%";
		case 5:
			return "5%";
		default:
			return null;
		}
	}
	
	public static Pattern budgetCodePattern(BudgetCodeType budgetCodeType) {
		return budgetCodeType.getRegExPattern();
	}
	
	public  Pattern getRegExPattern() {
		switch (id) {
		case 0:
			return Pattern.compile(".*");
		case 1: 
			return Pattern.compile("^1.*");
		case 2:
			return Pattern.compile("^2.*");
		case 3:
			return Pattern.compile("^3.*");
		case 4:
			return Pattern.compile("^4.*");
		case 5:
			return Pattern.compile("^5.*");
		default:
			return null;
		}
	}
	
	
	public BudgetCodeType parseBudgetCodeString(String budgetCode) {
		for(BudgetCodeType bCode : BudgetCodeType.values()) {
			Matcher m = bCode.getRegExPattern().matcher(budgetCode);
			if(m.find()) {
				return bCode;
			}
		}
		return null;
	}
}
