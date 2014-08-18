package nc.hd.vo.daterate;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
/**
 * 日期利率表
 * @author mlr
 */
public class RateVO extends SuperVO {
//	create table hd_rate(	  		
//		pk_rate char(20),
//		pk_corp char(4),
//		dbilldate char(10),
//		cdate  decimal(20,8),
//	    ddate  decimal(20,8),
//		vdef1 varchar(30),
//		vdef2 varchar(30),
//		vdef3 varchar(30),
//		vdef4 varchar(30),
//		vdef5 varchar(30),
//		vdef6 varchar(30),
//	    vdef7 varchar(30),
//		vdef8 varchar(30),
//	    ts char(19),
//	    dr int
//	)	
	private static final long serialVersionUID = 1L;
	public String pk_rate;//主键
	public String pk_corp; //公司 
	public UFDate dbilldate;//日期
	public UFDouble cdate;//存款利率
	public UFDouble ddate;//贷款款利率
	public String vdef1;
	public String vdef2;
	public String vdef3;
	public String vdef4;
	public String vdef5;
	public String vdef6;
	public String vdef7;
	public String vdef8;
	public UFDateTime ts;
	public Integer dr;
	

	public String getPk_rate() {
		return pk_rate;
	}
	public void setPk_rate(String pk_rate) {
		this.pk_rate = pk_rate;
	}

	public UFDate getDbilldate() {
		return dbilldate;
	}
	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}
	public UFDouble getCdate() {
		return cdate;
	}
	public void setCdate(UFDouble cdate) {
		this.cdate = cdate;
	}
	public UFDouble getDdate() {
		return ddate;
	}
	public void setDdate(UFDouble ddate) {
		this.ddate = ddate;
	}
	public String getPk_corp() {
		return pk_corp;
	}
	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public UFDateTime getTs() {
		return ts;
	}
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
//	public String getPk_corp() {
//		return pk_corp;
//	}
//	public void setPk_corp(String pk_corp) {
//		this.pk_corp = pk_corp;
//	}

	public String getVdef1() {
		return vdef1;
	}
	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}
	public String getVdef2() {
		return vdef2;
	}
	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}
	public String getVdef3() {
		return vdef3;
	}
	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}
	public String getVdef4() {
		return vdef4;
	}
	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}
	public String getVdef5() {
		return vdef5;
	}
	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}
	public String getVdef6() {
		return vdef6;
	}
	public void setVdef6(String vdef6) {
		this.vdef6 = vdef6;
	}
	public String getVdef7() {
		return vdef7;
	}
	public void setVdef7(String vdef7) {
		this.vdef7 = vdef7;
	}
	public String getVdef8() {
		return vdef8;
	}
	public void setVdef8(String vdef8) {
		this.vdef8 = vdef8;
	}
	@Override
	public String getPKFieldName() {
		return "pk_rate";
	}
	@Override
	public String getParentPKFieldName() {
		return null;
	}
	@Override
	public String getTableName() {
		return "hd_rate";
	}
	@Override
	public String getEntityName() {
		return "hd_rate";
	}
	
	


}
