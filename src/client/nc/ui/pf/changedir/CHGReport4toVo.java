package nc.ui.pf.changedir;
import nc.vo.pf.change.UserDefineFunction;
/**
 * �ʽ�����  ����vo->���ݱ���vo
 */
public class CHGReport4toVo extends nc.ui.pf.change.VOConversionUI {
/**
 * CHG20TO21 ������ע�⡣
 */
public CHGReport4toVo() {
	super();
}
/**
* ��ú������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getAfterClassName() {
	return null;
}
/**
* �����һ���������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getOtherClassName() {
	return null;
}
/**
* ����ֶζ�Ӧ��
* @return java.lang.String[]
*/
public String[] getField() {
	return new String[] {	
		    "H_pk_rept4->H_pk_rept4",
		    "H_sdate->H_sdate",
		    "H_ddate->H_ddate",
		    "H_pk_corp->H_pk_corp",
		    "H_pk_accsubj->H_pk_accsubj",
		    "H_dmakedate->H_dmakedate",
		    "H_checkvalue->H_checkvalue",
		    "H_num20->H_num20",
		    "H_num21->H_num21",
		    "H_num22->H_num22",
		    "H_num23->H_num23",		    		    
		    "H_num1->H_num1",
		    "H_num2->H_num2",
		    "H_num3->H_num3",
		    "H_num4->H_num4",
		    "H_num5->H_num5",
		    "H_num6->H_num6",
		    "H_num7->H_num7",
		    "H_num8->H_num8",
		    "H_num9->H_num9",
		    "H_num10->H_num10",		  		 
	};
}
/**
* ��ù�ʽ��
* @return java.lang.String[]
*/
public String[] getFormulas() {
	return null;
}
/**
* �����û��Զ��庯����
*/
public UserDefineFunction[] getUserDefineFunction() {
	return null;
}
}
