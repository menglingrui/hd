package nc.bs.hd.report4;
import java.util.ArrayList;
import java.util.List;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ArrayListProcessor;
import nc.jdbc.framework.processor.ArrayProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.util.SQLHelper;
import nc.vo.hd.report4.ReportVO4;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.zmpub.pub.report.ReportBaseVO;

/**
 * 资金计算
 * 
 * @author mlr
 */
public class ReportDMO {
	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	public ReportVO4 queryQiChu(ReportBaseVO vo) throws Exception {
		if (vo == null)
			return null;
		String pk_corp = PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("pk_corp"));

		String dmakedate = PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("dmakedate"));

		String checkvalue = PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("checkvalue"));
		
		String  pk_acc=PuPubVO.getString_TrimZeroLenAsNull(vo
				.getAttributeValue("pk_accsubj"));

		String wsql = " pk_corp='" + pk_corp + "' and dmakedate < '"
				+ dmakedate + "' and checkvalue='" + checkvalue
				+ "' and isnull(dr,0)=0 order by dmakedate";

		List list = (List) getDao().retrieveByClause(ReportVO4.class, wsql);
		if (list == null || list.size() == 0)
			return null;

		return (ReportVO4) list.get(list.size()-1);
	}
	String sql=" select cdate,ddate from hd_rate h where isnull(h.dr,0)=0 and  dbilldate <=? and cdate >0 and ddate>0 order by dbilldate desc";
    public List<UFDouble> queryRate(String dmakedate) throws DAOException{
    	SQLParameter pa=new SQLParameter();
    	pa.addParam(dmakedate);
    	List<UFDouble> list=new ArrayList<UFDouble>();
    	Object[] objs= (Object[]) getDao().executeQuery(sql, pa,new ArrayProcessor());
    	if(objs==null || objs.length==0)
    		return null;
    	list.add(PuPubVO.getUFDouble_NullAsZero(objs[0]));
    	list.add(PuPubVO.getUFDouble_NullAsZero(objs[1]));
        return list;   	
    }
	
	
	/**
	 * 校验 是否已经存在记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer valute(String prid, String sdate, String ddate)
			throws Exception {
		String wsql = " isnull(dr,0)=0 and pk_corp = '" + SQLHelper.getCorpPk()
				+ "'";

		if (prid != null && prid.length() > 0) {
			wsql = wsql + " and checkvalue='" + prid + "'";
		}

		wsql = wsql + " and dmakedate >='" + sdate + "'";

		wsql = wsql + " and dmakedate <='" + ddate + "'";

		String sql = " select count(0) from hd_rept4  where" + wsql;

		Object o = getDao().executeQuery(sql, new ColumnProcessor());

		Integer in = PuPubVO.getInteger_NullAs(o, -1);
		return in;
	}

	/**
	 * 校验 结束日期后面 是否已经有数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer valute1(String prid, String ddate) throws Exception {
		String wsql = " isnull(dr,0)=0 and pk_corp = '" + SQLHelper.getCorpPk()
				+ "'";

		if (prid != null && prid.length() > 0) {
			wsql = wsql + " and checkvalue='" + prid + "'";
		}

		wsql = wsql + " and dmakedate >'" + ddate + "'";

		String sql = " select count(0) from hd_rept4  where" + wsql;

		Object o = getDao().executeQuery(sql, new ColumnProcessor());

		Integer in = PuPubVO.getInteger_NullAs(o, -1);
		return in;
	}

	/**
	 * 数据保存
	 * 
	 * @param vos
	 * @throws Exception
	 */
	public void onBoSave(ReportVO4[] vos, String prid, String sdate,
			String ddate) throws Exception {
		if (vos == null || vos.length == 0)
			return;
		//checkvalue dmakedate
		ReportVO4[][] voss=(ReportVO4[][]) SplitBillVOs.getSplitVOs(vos, new String[]{"checkvalue"});
		for (int i = 0; i < voss.length; i++) {
			ReportVO4[] rvos=voss[i];
			if(rvos==null || rvos.length==0){
				continue;
			}
			String wsql = " isnull(dr,0)=0 and pk_corp = '"
					+ SQLHelper.getCorpPk() + "'";

		//	if (prid != null && prid.length() > 0) {
			wsql = wsql + " and checkvalue='" + rvos[0].getCheckvalue() + "'";
		//	}

			wsql = wsql + " and dmakedate >='" + sdate + "'";

			wsql = wsql + " and dmakedate <='" + ddate + "'";

			String sql = " update hd_rept4 set dr=1 where " + wsql;

			getDao().executeUpdate(sql);

			getDao().insertVOArray(rvos);
		}
	}

}
