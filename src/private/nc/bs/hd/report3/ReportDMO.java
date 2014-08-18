package nc.bs.hd.report3;
import nc.bs.dao.BaseDAO;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.hd.report2.ReportVO2;
import nc.vo.hd.report3.ReportVO3;
import nc.vo.scm.pu.PuPubVO;
/**
 * 综合汇率损益 后台处理
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
  public void onBoSave(ReportVO3[] vos,String prid ,String sdate ,String ddate) throws Exception{
	  if(vos==null || vos.length==0)
		  return; 
	  
	  String wsql=" isnull(dr,0)=0 and pk_corp = '"+SQLHelper.getCorpPk()+"'";
	  
	  if(prid!=null && prid.length()>0){
		  wsql=wsql+" and projectid='"+prid+"'";
	  }
	  
	  wsql=wsql+" and sdate='"+sdate+"'";
	  
	  wsql=wsql+" and ddate='"+ddate+"'";
	  
	  String sql=" update hd_rept3 set dr=1 where "+wsql;
	  
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
	  
	  String sql=" select count(0) from hd_rept3  where"+wsql;
	  
	  Object o= getDao().executeQuery(sql, new ColumnProcessor());
	  
	  Integer in=PuPubVO.getInteger_NullAs(o, -1);
	  return in;
  }	
}
