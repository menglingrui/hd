package nc.vo.hd.report2;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;

public class ReportVO2 extends SuperVO{
	/**
	 * 
create table hd_rept2(  
 pk_rept2 char(20),
     sdate char(20),
   ddate  char(20),
    pk_corp char(4),
   ct_code  varchar(120),
   pk_ct_type   char(20),
   pk_ct_manage  char(20),
   pk_ct_manage_b char(20),
    projectid  char(20),
   custid   char(20),
   pk_currtype char(20),
    def5  decimal(38,8),
    def9  decimal(38,8),
   xhwj   decimal(38,8),
   ywbm    decimal(38,8),
   bxhtfk  decimal(38,8),
   num111    decimal(38,8),
   byhtfk   decimal(38,8),
     num112  decimal(38,8),
     num22  decimal(38,8),
     num23   decimal(38,8),
     num33   decimal(38,8),
     num34 decimal(38,8),
        num2  decimal(38,8),
       num5  decimal(38,8),
        num8  decimal(38,8),
         num10  decimal(38,8),
     yhwj    decimal(38,8),
      num11 decimal(38,8),
     num6 decimal(38,8),
     ts char(19),
     dr smallint,
     
  )  
	 */
	
	private static final long serialVersionUID = -5426984265365542366L;
	private String pk_rept1;//主键
    private String sdate ;// 查询开始日期	
	private String ddate ;// 查询结束日期
	private String  pk_corp;
	private String ct_code; //合同号
	private String pk_ct_type ;  // 合同类型
	private String pk_ct_manage ;  // 合同类型
	private String pk_ct_manage_b; // 合同表体主键
	private String	projectid ;// 项目
	private String custid ;// 客商id
	private String pk_currtype; // 币种
	private UFDouble  def5; // 期初付款原币金额
	private UFDouble  def9; // 期初付款本币金额
	private UFDouble xhwj;// 新增合同价税合计
	//private String ywbm;   // 交易类型
	private UFDouble bxhtfk;// 付款原币金额
	private UFDouble num111; // 付款本币金额		
	private UFDouble byhtfk;// 付款原币金额
    private UFDouble num112; // 付款本币金额
    private UFDouble num22;// 付款原币金额
    private UFDouble num23; // 付款本币金额
    private UFDouble num33;// 付款原币金额
    private UFDouble num34; // 付款本币金额
    private UFDouble num2;// 付款原币金额
    private UFDouble num5; // 付款本币金额
    private UFDouble num8;// 付款原币金额
    private UFDouble num10; // 付款本币金额
    
    private UFDouble yshl;//预算汇率
    private UFDouble sqhl;//期初汇率
    private UFDouble bqhl;//期末汇率
    

    
    
	public UFDouble getYshl() {
		return yshl;
	}

	public void setYshl(UFDouble yshl) {
		this.yshl = yshl;
	}

	public UFDouble getSqhl() {
		return sqhl;
	}

	public void setSqhl(UFDouble sqhl) {
		this.sqhl = sqhl;
	}

	public UFDouble getBqhl() {
		return bqhl;
	}

	public void setBqhl(UFDouble bqhl) {
		this.bqhl = bqhl;
	}

	public UFDouble getNum2() {
		return num2;
	}

	public void setNum2(UFDouble num2) {
		this.num2 = num2;
	}

	public UFDouble getNum5() {
		return num5;
	}

	public void setNum5(UFDouble num5) {
		this.num5 = num5;
	}

	public UFDouble getNum8() {
		return num8;
	}

	public void setNum8(UFDouble num8) {
		this.num8 = num8;
	}

	public UFDouble getNum10() {
		return num10;
	}

	public void setNum10(UFDouble num10) {
		this.num10 = num10;
	}

	private UFDouble yhwj;//
    
	private UFDouble num6;//付款本期综合汇率损益
	    
	private UFDouble num11;//付款累计综合汇率损益
	private Integer dr;
    private UFTime ts;
    
    
    
    
	public UFDouble getNum6() {
		return num6;
	}

	public void setNum6(UFDouble num6) {
		this.num6 = num6;
	}

	public UFDouble getNum11() {
		return num11;
	}

	public void setNum11(UFDouble num11) {
		this.num11 = num11;
	}

	public UFDouble getYhwj() {
		return yhwj;
	}

	public void setYhwj(UFDouble yhwj) {
		this.yhwj = yhwj;
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

	public String getPk_rept1() {
		return pk_rept1;
	}

	public void setPk_rept1(String pk_rept1) {
		this.pk_rept1 = pk_rept1;
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

	public String getCt_code() {
		return ct_code;
	}

	public void setCt_code(String ct_code) {
		this.ct_code = ct_code;
	}

	public String getPk_ct_type() {
		return pk_ct_type;
	}

	public void setPk_ct_type(String pk_ct_type) {
		this.pk_ct_type = pk_ct_type;
	}

	public String getPk_ct_manage() {
		return pk_ct_manage;
	}

	public void setPk_ct_manage(String pk_ct_manage) {
		this.pk_ct_manage = pk_ct_manage;
	}

	public String getPk_ct_manage_b() {
		return pk_ct_manage_b;
	}

	public void setPk_ct_manage_b(String pk_ct_manage_b) {
		this.pk_ct_manage_b = pk_ct_manage_b;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPk_currtype() {
		return pk_currtype;
	}

	public void setPk_currtype(String pk_currtype) {
		this.pk_currtype = pk_currtype;
	}

	public UFDouble getDef5() {
		return def5;
	}

	public void setDef5(UFDouble def5) {
		this.def5 = def5;
	}

	public UFDouble getDef9() {
		return def9;
	}

	public void setDef9(UFDouble def9) {
		this.def9 = def9;
	}

	public UFDouble getXhwj() {
		return xhwj;
	}

	public void setXhwj(UFDouble xhwj) {
		this.xhwj = xhwj;
	}


	public UFDouble getBxhtfk() {
		return bxhtfk;
	}

	public void setBxhtfk(UFDouble bxhtfk) {
		this.bxhtfk = bxhtfk;
	}

	public UFDouble getNum111() {
		return num111;
	}

	public void setNum111(UFDouble num111) {
		this.num111 = num111;
	}

	public UFDouble getByhtfk() {
		return byhtfk;
	}

	public void setByhtfk(UFDouble byhtfk) {
		this.byhtfk = byhtfk;
	}

	public UFDouble getNum112() {
		return num112;
	}

	public void setNum112(UFDouble num112) {
		this.num112 = num112;
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

	public UFDouble getNum33() {
		return num33;
	}

	public void setNum33(UFDouble num33) {
		this.num33 = num33;
	}

	public UFDouble getNum34() {
		return num34;
	}

	public void setNum34(UFDouble num34) {
		this.num34 = num34;
	}

	@Override
	public String getPKFieldName() {
		return "pk_rept2";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "hd_rept2";
	}

}
