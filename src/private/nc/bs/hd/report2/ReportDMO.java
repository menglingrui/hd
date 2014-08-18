package nc.bs.hd.report2;
import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.hd.report2.ReportVO2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * 付款汇率损益 后台处理
 * @author mlr
 */
public class ReportDMO {
	private BaseDAO dao=null;
	public BaseDAO getDao(){
		if(dao==null){
			dao=new BaseDAO();
		}
		return dao;	
	}
	
 /**
  * 数据保存	
  * @param vos
 * @throws Exception 
  */
  public void onBoSave(ReportVO2[] vos,String prid ,String sdate ,String ddate) throws Exception{
	  if(vos==null || vos.length==0)
		  return; 
	  
	  String wsql=" isnull(dr,0)=0 and pk_corp = '"+SQLHelper.getCorpPk()+"'";
	  
	  if(prid!=null && prid.length()>0){
		  wsql=wsql+" and projectid='"+prid+"'";
	  }
	  
	  wsql=wsql+" and sdate='"+sdate+"'";
	  
	  wsql=wsql+" and ddate='"+ddate+"'";
	  
	  String sql=" update hd_rept2 set dr=1 where "+wsql;
	  
	  getDao().executeUpdate(sql);
	  
	  getDao().insertVOArray(vos);
  }
 /**
  * 校验 	是否已经存在 记录
  * @return
  * @throws Exception
  */
  public Integer  valute(String prid ,String sdate ,String ddate) throws  Exception{
	  String wsql=" isnull(dr,0)=0 and pk_corp = '"+SQLHelper.getCorpPk()+"'";
	  
	  if(prid!=null && prid.length()>0){
		  wsql=wsql+" and projectid='"+prid+"'";
	  }
	  
	  wsql=wsql+" and sdate='"+sdate+"'";
	  
	  wsql=wsql+" and ddate='"+ddate+"'";
	  
	  String sql=" select count(0) from hd_rept2  where"+wsql;
	  
	  Object o= getDao().executeQuery(sql, new ColumnProcessor());
	  
	  Integer in=PuPubVO.getInteger_NullAs(o, -1);
	  return in;
  }	
  String sql1=" select rate from bd_currrate where pk_currinfo=? and ratedate<=? and rate>0 and isnull(dr,0)=0 order by  ratedate desc";
  public UFDouble queryRate(String date,String pk_currtype) throws BusinessException{
	  SQLParameter pa=new SQLParameter();  
	 String pk_currtype1= (String) ZmPubTool.execFomular("pk_currtype1->getColValue(bd_currinfo,pk_currinfo,pk_currtype,pk_currtype)", 
			  new String[]{"pk_currtype"}, 
			  new String[]{pk_currtype});
	  pa.addParam(pk_currtype1);
	  pa.addParam(date);
	  UFDouble rate =PuPubVO.getUFDouble_NullAsZero(getDao().executeQuery(sql1, pa, new ColumnProcessor())) ;
	  if(rate.doubleValue()>0)
		  return rate;	  
	 return new UFDouble(1); 
  }
}
