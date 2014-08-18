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
	
	//报表字段值 对应 和 报表计算顺序
	//num20 期初流入资金    num21期初流出资金     num22期初存款利息    num23期初贷款利息		
	//pk_detail(凭证分录主键)  dmakedate(单据日期)  num1(流入资金) num2(流出资金）  checkvalue(工程项目档案) 		
	//流入流出差额 num3=num1-num2  累计流入流出差额 num4=num3   存款利率 num5    贷款利率 num6   	第一步计算
	//累计流入流出差额num4    存款利息 num7  贷款利息num8   	第二步计算	
	//累计利息num9   		第三步计算
	//日余额num10           第四步计算
	private static final long serialVersionUID = -5426984265365542366L;
	private String pk_rept4;//主键
    private String sdate ;// 查询开始日期	
	private String ddate ;// 查询结束日期
	private String  pk_corp;
	private UFDouble  num20; // 期初流入资金
	private UFDouble  num21; // 期初流出资金
	private UFDouble num22;// 期初存款利息
	private UFDouble num23;//期初贷款利息
	private String pk_detail;// 凭证分录主键
	private String pk_accsubj;//
	private String dmakedate; // 单据日期	
	private UFDouble num1;// 流入资金
    private UFDouble num2; // 流出资金
    private String checkvalue;// 工程项目档案
    private UFDouble num3; // 流入流出差额
    
    private UFDouble num4; // 累计流入流出差额
    
    private UFDouble num5;// 存款利率
    
    private UFDouble num6;//贷款利率
    
    private UFDouble num7;// 存款利息
    
    private UFDouble num8;//贷款利息
    
    private UFDouble num9;//累计利息
    
    private UFDouble num10;//日余额
    
    
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
