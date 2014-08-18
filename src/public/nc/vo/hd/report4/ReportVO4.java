package nc.vo.hd.report4;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;

public class ReportVO4 extends SuperVO{
	/**
	 * 
create table hd_rept4(  
 pk_rept4 char(20),
     sdate char(20),
   ddate  char(20),
    pk_corp char(4),
   dmakedate char(10),
   checkvalue  char(20),
   pk_currtype char(20),
   pk_accsubj  char(20),
    num20  decimal(38,8),
    num21  decimal(38,8),
    num22  decimal(38,8),
   num1   decimal(38,8),
   num23    decimal(38,8),
   num2  decimal(38,8),
   num3    decimal(38,8),
   num4   decimal(38,8),
     num5  decimal(38,8),
     num6  decimal(38,8),
     num7   decimal(38,8),
     num8   decimal(38,8),
     num9  decimal(38,8),
     num10   decimal(38,8),
     
     ts char(19),
     dr smallint,
     
  )  
	 */
	
	//�����ֶ�ֵ ��Ӧ �� �������˳��
	//num20 �ڳ������ʽ�    num21�ڳ������ʽ�     num22�ڳ������Ϣ    num23�ڳ�������Ϣ		
	//pk_detail(ƾ֤��¼����)  dmakedate(��������)  num1(�����ʽ�) num2(�����ʽ�  checkvalue(������Ŀ����) 		
	//����������� num3=num1-num2  �ۼ������������ num4=num3   ������� num5    �������� num6   	��һ������
	//�ۼ������������num4    �����Ϣ num7  ������Ϣnum8   	�ڶ�������	
	//�ۼ���Ϣnum9   		����������
	//�����num10           ���Ĳ�����
	private static final long serialVersionUID = -5426984265365542366L;
	private String pk_rept4;//����
    private String sdate ;// ��ѯ��ʼ����	
	private String ddate ;// ��ѯ��������
	private String  pk_corp;
	private UFDouble  num20; // �ڳ������ʽ�
	private UFDouble  num21; // �ڳ������ʽ�
	private UFDouble num22;// �ڳ������Ϣ
	private UFDouble num23;//�ڳ�������Ϣ
	private String pk_detail;// ƾ֤��¼����
	private String pk_accsubj;//
	private String dmakedate; // ��������	
	private UFDouble num1;// �����ʽ�
    private UFDouble num2; // �����ʽ�
    private String checkvalue;// ������Ŀ����
    private UFDouble num3; // �����������
    
    private UFDouble num4; // �ۼ������������
    
    private UFDouble num5;// �������
    
    private UFDouble num6;//��������
    
    private UFDouble num7;// �����Ϣ
    
    private UFDouble num8;//������Ϣ
    
    private UFDouble num9;//�ۼ���Ϣ
    
    private UFDouble num10;//�����
    
    
	private Integer dr;
    private UFTime ts;
    
    
    
    
	public String getPk_accsubj() {
		return pk_accsubj;
	}

	public void setPk_accsubj(String pk_accsubj) {
		this.pk_accsubj = pk_accsubj;
	}

	public UFDouble getNum20() {
		return num20;
	}

	public void setNum20(UFDouble num20) {
		this.num20 = num20;
	}

	public UFDouble getNum21() {
		return num21;
	}

	public void setNum21(UFDouble num21) {
		this.num21 = num21;
	}

	public String getPk_detail() {
		return pk_detail;
	}

	public void setPk_detail(String pk_detail) {
		this.pk_detail = pk_detail;
	}

	public String getDmakedate() {
		return dmakedate;
	}

	public void setDmakedate(String dmakedate) {
		this.dmakedate = dmakedate;
	}

	public UFDouble getNum1() {
		return num1;
	}

	public void setNum1(UFDouble num1) {
		this.num1 = num1;
	}

	public UFDouble getNum2() {
		return num2;
	}

	public void setNum2(UFDouble num2) {
		this.num2 = num2;
	}

	public String getCheckvalue() {
		return checkvalue;
	}

	public void setCheckvalue(String checkvalue) {
		this.checkvalue = checkvalue;
	}

	public UFDouble getNum3() {
		return num3;
	}

	public void setNum3(UFDouble num3) {
		this.num3 = num3;
	}

	public UFDouble getNum4() {
		return num4;
	}

	public void setNum4(UFDouble num4) {
		this.num4 = num4;
	}

	public UFDouble getNum5() {
		return num5;
	}

	public void setNum5(UFDouble num5) {
		this.num5 = num5;
	}

	public UFDouble getNum7() {
		return num7;
	}

	public void setNum7(UFDouble num7) {
		this.num7 = num7;
	}

	public UFDouble getNum8() {
		return num8;
	}

	public void setNum8(UFDouble num8) {
		this.num8 = num8;
	}

	public UFDouble getNum9() {
		return num9;
	}

	public void setNum9(UFDouble num9) {
		this.num9 = num9;
	}

	public UFDouble getNum10() {
		return num10;
	}

	public void setNum10(UFDouble num10) {
		this.num10 = num10;
	}

	public UFDouble getNum6() {
		return num6;
	}

	public void setNum6(UFDouble num6) {
		this.num6 = num6;
	}

	

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFTime getTs() {
		return ts;
	}

	public void setTs(UFTime ts) {
		this.ts = ts;
	}



	public String getPk_rept4() {
		return pk_rept4;
	}

	public void setPk_rept4(String pk_rept4) {
		this.pk_rept4 = pk_rept4;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getDdate() {
		return ddate;
	}

	public void setDdate(String ddate) {
		this.ddate = ddate;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	
	public UFDouble getNum22() {
		return num22;
	}

	public void setNum22(UFDouble num22) {
		this.num22 = num22;
	}

	public UFDouble getNum23() {
		return num23;
	}

	public void setNum23(UFDouble num23) {
		this.num23 = num23;
	}

	
	@Override
	public String getPKFieldName() {
		return "pk_rept4";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "hd_rept4";
	}

}
