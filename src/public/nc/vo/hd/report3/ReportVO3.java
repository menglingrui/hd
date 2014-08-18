package nc.vo.hd.report3;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;

public class ReportVO3 extends SuperVO{
	/**
	 * 
create table hd_rept3(  
 pk_rept3 char(20),
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
   
    num2x  decimal(38,8),
    num5x  decimal(38,8),
   num6x   decimal(38,8),
   num2    decimal(38,8),
   num5  decimal(38,8),
   num6    decimal(38,8),
   num100  decimal(38,8),
     num8x  decimal(38,8),
     num11x  decimal(38,8),
     num10x   decimal(38,8),
     num11  decimal(38,8),
      num200  decimal(38,8),
        num8  decimal(38,8),
         num10  decimal(38,8),
         
     yshl    decimal(38,8),
     sqhl decimal(38,8),
     bqhl decimal(38,8),
     ts char(19),
     dr smallint,
     
  )  
	 */
	private static final long serialVersionUID = -5426984265365542366L;
	private String pk_rept3;//主键
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
    
	 private UFDouble num2x;
	 private UFDouble num5x;
	 private UFDouble num6x;
	 private UFDouble num2;
	 private UFDouble num5;
	 private UFDouble num6;
	 private UFDouble num100;
	 private UFDouble num8x;
	 private UFDouble num11x;
	 private UFDouble num8;
	 private UFDouble num10x;
	 private UFDouble num11;
	 private UFDouble num200;
	 private UFDouble num10;
	 
      
    
    private UFDouble yshl;//预算汇率
    private UFDouble sqhl;//期初汇率
    private UFDouble bqhl;//期末汇率
    
    
    
	public String getPk_rept3() {
		return pk_rept3;
	}

	public void setPk_rept3(String pk_rept3) {
		this.pk_rept3 = pk_rept3;
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

	public UFDouble getNum2x() {
		return num2x;
	}

	public void setNum2x(UFDouble num2x) {
		this.num2x = num2x;
	}

	public UFDouble getNum5x() {
		return num5x;
	}

	public void setNum5x(UFDouble num5x) {
		this.num5x = num5x;
	}

	public UFDouble getNum6x() {
		return num6x;
	}

	public void setNum6x(UFDouble num6x) {
		this.num6x = num6x;
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

	public UFDouble getNum6() {
		return num6;
	}

	public void setNum6(UFDouble num6) {
		this.num6 = num6;
	}

	public UFDouble getNum100() {
		return num100;
	}

	public void setNum100(UFDouble num100) {
		this.num100 = num100;
	}

	public UFDouble getNum8x() {
		return num8x;
	}

	public void setNum8x(UFDouble num8x) {
		this.num8x = num8x;
	}

	public UFDouble getNum11x() {
		return num11x;
	}

	public void setNum11x(UFDouble num11x) {
		this.num11x = num11x;
	}

	public UFDouble getNum8() {
		return num8;
	}

	public void setNum8(UFDouble num8) {
		this.num8 = num8;
	}

	public UFDouble getNum10x() {
		return num10x;
	}

	public void setNum10x(UFDouble num10x) {
		this.num10x = num10x;
	}

	public UFDouble getNum11() {
		return num11;
	}

	public void setNum11(UFDouble num11) {
		this.num11 = num11;
	}

	public UFDouble getNum200() {
		return num200;
	}

	public void setNum200(UFDouble num200) {
		this.num200 = num200;
	}

	public UFDouble getNum10() {
		return num10;
	}

	public void setNum10(UFDouble num10) {
		this.num10 = num10;
	}

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

	private Integer dr;
    private UFTime ts;
    
    
    
 

	@Override
	public String getPKFieldName() {
		return "pk_rept3";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "hd_rept3";
	}

}
