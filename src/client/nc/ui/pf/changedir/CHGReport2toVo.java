package nc.ui.pf.changedir;
import nc.vo.pf.change.UserDefineFunction;
/**
 * 付款汇率损益  报表vo->数据保存vo
 */
public class CHGReport2toVo extends nc.ui.pf.change.VOConversionUI {
/**
 * CHG20TO21 构造子注解。
 */
public CHGReport2toVo() {
	super();
}
/**
* 获得后续类的全录经名称。
* @return java.lang.String[]
*/
public String getAfterClassName() {
	return null;
}
/**
* 获得另一个后续类的全录径名称。
* @return java.lang.String[]
*/
public String getOtherClassName() {
	return null;
}
/**
* 获得字段对应。
* @return java.lang.String[]
*/
public String[] getField() {
	return new String[] {	
		    "H_pk_rept2->H_pk_rept2",
		    "H_sdate->H_sdate",
		    "H_ddate->H_ddate",
		    "H_pk_corp->H_pk_corp",
		    "H_ct_code->H_ct_code",
		    "H_pk_ct_type->H_pk_ct_type",
		    "H_pk_ct_manage->H_pk_ct_manage",
		    "H_pk_ct_manage_b->H_pk_ct_manage_b",
		    "H_projectid->H_projectid",
		    "H_custid->H_custid",
		    "H_pk_currtype->H_pk_currtype",
		    "H_def5->H_def5",
		    "H_def9->H_def9",
		    "H_yhwj->H_yhwj",
		    "H_xhwj->H_xhwj",
		    "H_ywbm->H_ywbm",
		    "H_bxhtfk->H_bxhtfk",
		    "H_num111->H_num111",
		    "H_byhtfk->H_byhtfk",
		    "H_num112->H_num112",
		    "H_num22->H_num22",
		    "H_num23->H_num23",
		    "H_num33->H_num33",
		    "H_num34->H_num34",
		    "H_num6->H_num6",
		    "H_num11->H_num11",
		    "H_num2->H_num2",
		    "H_num5->H_num5",
		    "H_num8->H_num8",
		    "H_num10->H_num10",
		    "H_yshl->H_yshl",
		    "H_sqhl->H_sqhl",
		    "H_bqhl->H_bqhl",
	};
}
/**
* 获得公式。
* @return java.lang.String[]
*/
public String[] getFormulas() {
	return null;
}
/**
* 返回用户自定义函数。
*/
public UserDefineFunction[] getUserDefineFunction() {
	return null;
}
}
