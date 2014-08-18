package nc.hd.ui.report3;
import java.util.List;
import nc.bd.accperiod.AccountCalendar;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIDialog;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.zmpub.pub.report.buttonaction2.CaPuBtnConst;
import nc.ui.zmpub.pub.report.buttonaction2.IReportButton;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.SingleVOChangeDataUiTool;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.hd.report2.ReportVO2;
import nc.vo.hd.report3.ReportVO3;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * 综合汇率损益计算
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// 查询开始日期

	private String ddate = "";// 查询结束日期

	private String prid = "";// 项目id

//	private String pk_ctpe = "1002A2100000000005FB";// 项目合同 （付款合同类型)
//
//	private String pk_bz = "00010000000000000001";// 人民币币种主键
//
//	private String jybm = "F3-01";// 项目付款单 交易类型编码
//
//	private String bo = "nc.bs.hd.report1.ReportDMO";

	public static final int save1 = 150;// 报表保存按钮

	public static final int cal = 152;// 报表计算按钮

	public static final int qry = 154;// 报表查询按钮
	
	private String bo = "nc.bs.hd.report3.ReportDMO";

	/**
	 * 接收查询的组合sql
	 * 
	 * @author mlr
	 * @说明：（鹤岗矿业） 2011-12-22上午10:41:05
	 * @return
	 */
	public String[] getSqls() throws Exception {
		sdate = null;
		ddate = null;
		prid = null;
		return new String[] { getQuerySql(),getQuerySql1() };
	}

	private CircularlyAccessibleValueObject[] sves;

	/**
	 * 设置到ui界面之前 处理分组查询后的数据
	 * 
	 * @author mlr
	 * @说明：（鹤岗矿业） 2011-12-22上午10:42:36
	 * @param list
	 * @return
	 */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		return null;
	}

//	public void onSave() throws Exception {
//		if (sves == null || sves.length == 0) {
//			this.showErrorMessage("没有数据");
//			return;
//		}
//
//		valudate(sves);
//		if (valudate1(sves)) {
//			int retu = showOkCancelMessage("已经存在 本会计期间的数据  如果继续  将会覆盖已保存的数据");
//			if (retu != 1) {
//				return;
//			}
//		}
//		ReportVO1[] vos = (ReportVO1[]) SingleVOChangeDataUiTool
//				.runChangeVOAryForUI(sves, ReportVO1.class,
//						"nc.ui.pf.changedir.CHGReport1toVo");
//
//		Class[] ParameterTypes = new Class[] { ReportVO1[].class, String.class,
//				String.class, String.class };
//
//		Object[] ParameterValues = new Object[] { vos, prid, sdate, ddate };
//
//		Object o = LongTimeTask.calllongTimeService("ic", null, "正在保存...", 1,
//				bo, null, "onBoSave", ParameterTypes, ParameterValues);
//
//	}

//	/**
//	 * 校验 项目 开始日期 结束日期 这个维度 是否存在数据 如果存在 给出提示
//	 * 
//	 * @param sves2
//	 */
//	public boolean valudate1(ReportBaseVO[] sves2) throws Exception {
//
//		Class[] ParameterTypes = new Class[] { String.class, String.class,
//				String.class };
//
//		Object[] ParameterValues = new Object[] { prid, sdate, ddate };
//
//		Object o = LongTimeTask.calllongTimeService("ic", null, "正在校验...", 1,
//				bo, null, "valute", ParameterTypes, ParameterValues);
//		Integer in = PuPubVO.getInteger_NullAs(o, -1);
//		if (in > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * 对保存的数据合法性进行校验
	 * 
	 * @param sves2
	 */
	public void valudate(ReportBaseVO[] sves2) throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("没有开始日期");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("没有结束日期");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("开始日期   必须小于   结束日期");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("从开始日期  到结束日期  没有找到会计期间 ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("从开始日期  到结束日期 跨会计期间");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("开始日期  与 会计月 开始日期 不一致 ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("结束日期  与 会计月 结束日期 不一致 ");
		}

	}

	/**
	 * 查询后设置开始日期和结束日期
	 * 
	 * @param avos
	 */
	private void setQueryDate(ReportBaseVO[] avos) {
		if (avos == null || avos.length == 0) {
			return;
		}
		for (int i = 0; i < avos.length; i++) {
			avos[i].setAttributeValue("sdate", sdate);
			avos[i].setAttributeValue("ddate", ddate);
		}
	}



	/**
	 * 设置查询开始日期和结束日期
	 * 
	 * @param vos
	 */
	private void setdate(ConditionVO[] vos) {
		if (vos == null || vos.length == 0) {
			return;
		}
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getFieldCode().equals("b.def2")) {
				if (vos[i].getFieldName().equals("开始日期")) {
					sdate = vos[i].getValue();
				} else {
					ddate = vos[i].getValue();
				}
			}
		}
	}



	

	/**
	 * 根据项目 项目管理档案主键 id 获得项目基本档案 id
	 * 
	 * @param value
	 * @throws BusinessException
	 */
	private String getManPk(String value) throws BusinessException {
		String colmu = "x->getColValue(bd_jobmngfil,pk_jobbasfil,pk_jobmngfil,pk_jobmngfil)";
		String pk = PuPubVO.getString_TrimZeroLenAsNull(ZmPubTool
				.execFomularClient(colmu, new String[] { "pk_jobmngfil" },
						new String[] { value }));
		return pk;
	}

	
	

	

	/**
	 * 只获取关于存货的查询条件
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-10上午10:25:17
	 * @return
	 */
	private String getwheresql() {
		QueryDLG querylg = getQueryDlg();// 获取查询对话框
		ConditionVO[] vos = querylg.getConditionVO();// 获取已被用户填写的查询条件
		ConditionVO vo = null;
		if (vos == null || vos.length == 0)
			return null;
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getFieldCode().equals("pk_invmandoc")) {
				vo = vos[i];
			}

		}
		if (vo == null) {
			return null;
		}
		String wsql = querylg.getWhereSQL(new ConditionVO[] { vo });
		return wsql;
	}

	/**
	 * 查询完成 设置到ui界面之后 后续处理
	 * 
	 * @author mlr
	 * @说明：（鹤岗矿业） 2011-12-22上午10:42:36
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void dealQueryAfter() throws Exception {
		super.dealQueryAfter();
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report3.ReportVO3");
	}

	/**
	 * 设置报表功能节点号
	 */
	public String _getModelCode() {
		return "40205030";
	}
    /**
     * 查询付款汇率损益
     * @return
     * @throws Exception
     */
	private String getQuerySql() throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_rept1 , ");
		sql.append(" h.sdate  , ");
		sql.append(" h.ddate ,");
		sql.append(" h.yshl ,");
		sql.append(" h.sqhl ,");
		sql.append(" h.bqhl ,");
		sql.append(" h.pk_corp ,");
		sql.append(" h.ct_code ,");
		sql.append(" h.pk_ct_type ,");
		sql.append(" h.pk_ct_manage ,");
		sql.append(" h.pk_ct_manage_b ,");
		sql.append(" h.projectid ,");
		sql.append(" h.custid ,");
		sql.append(" h.pk_currtype ,");
		sql.append(" h.def5 ,");
		sql.append(" h.def9 ,");
		sql.append(" h.xhwj ,");
		sql.append(" h.bxhtfk ,");
		sql.append(" h.num111 ,");
		sql.append(" h.byhtfk ,");
		sql.append(" h.num112 ,");
		sql.append(" h.num22 ,");
		sql.append(" h.num33 ,");
		sql.append(" h.num34 ,");
		sql.append(" h.num6 ,");
		sql.append(" h.num11 ,");
		sql.append(" h.num34,");
		sql.append(" h.yhwj, ");
		sql.append(" h.num2,");
		sql.append(" h.num5,");
		sql.append(" h.num8,");
		sql.append(" h.num10");
	
		sql.append(" from hd_rept1 h  ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");

		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and h.sdate ='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("结束日期")) {
							wsql = wsql + " and h.ddate  ='"
									+ vos[i].getValue() + "'";
							ddate = vos[i].getValue();
						}
					}
					if (code.equals("h.projectid")) {
						prid = vos[i].getValue();
						wsql = wsql + " and h.projectid  ='"
						+ vos[i].getValue() + "'";
					}
				}
			}
			sql.append(wsql);
		}
		return sql.toString();
	}
	/**
	 * 对保存的数据合法性进行校验
	 * 
	 * @param sves2
	 */
	public void valudate() throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("没有开始日期");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("没有结束日期");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("开始日期   必须小于   结束日期");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("从开始日期  到结束日期  没有找到会计期间 ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("从开始日期  到结束日期 跨会计期间");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("开始日期  与 会计月 开始日期 不一致 ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("结束日期  与 会计月 结束日期 不一致 ");
		}

	}
	public void onSave() throws Exception {
		
		if (sves == null || sves.length == 0) {
			this.showErrorMessage("没有数据");
			return;
		}

		valudate();
		if (valudate1()) {
			int retu = showOkCancelMessage("已经存在 本会计期间的数据  如果继续  将会覆盖已保存的数据");
			if (retu != 1) {
				return;
			}
		}
		ReportVO3[] vos = (ReportVO3[]) SingleVOChangeDataUiTool
				.runChangeVOAryForUI(sves, ReportVO3.class,
						"nc.ui.pf.changedir.CHGReport3toVo");

		Class[] ParameterTypes = new Class[] { ReportVO3[].class, String.class,
				String.class, String.class };

		Object[] ParameterValues = new Object[] { vos, prid, sdate, ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在保存...", 1,
				bo, null, "onBoSave", ParameterTypes, ParameterValues);

	}
	/**
	 * 校验 项目 开始日期 结束日期 这个维度 是否存在数据 如果存在 给出提示
	 * 
	 * @param sves2
	 */
	public boolean valudate1() throws Exception {

		Class[] ParameterTypes = new Class[] { String.class, String.class,
				String.class };

		Object[] ParameterValues = new Object[] { prid, sdate, ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在校验...", 1,
				bo, null, "valute", ParameterTypes, ParameterValues);
		Integer in = PuPubVO.getInteger_NullAs(o, -1);
		if (in > 0) {
			return true;
		} else {
			return false;
		}
	}
	 /**
     * 查询收款汇率损益
     * @return
     * @throws Exception 
     */
	private String getQuerySql1() throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_rept2 , ");
		sql.append(" h.sdate  , ");
		sql.append(" h.ddate ,");
		sql.append(" h.pk_corp ,");
		sql.append(" h.yshl ,");
		sql.append(" h.sqhl ,");
		sql.append(" h.bqhl ,");
		sql.append(" h.ct_code ,");
		sql.append(" h.pk_ct_type ,");
		sql.append(" h.pk_ct_manage ,");
		sql.append(" h.pk_ct_manage_b ,");
		sql.append(" h.projectid ,");
		sql.append(" h.custid ,");
		sql.append(" h.pk_currtype ,");
		sql.append(" h.def5 ,");
		sql.append(" h.def9 ,");
		sql.append(" h.xhwj ,");
		sql.append(" h.bxhtfk ,");
		sql.append(" h.num111 ,");
		sql.append(" h.byhtfk ,");
		sql.append(" h.num112 ,");
		sql.append(" h.num22 ,");
		sql.append(" h.num33 ,");
		sql.append(" h.num34 ,");
		sql.append(" h.num6 ,");
		sql.append(" h.num11 ,");
		sql.append(" h.num23 ,");
		sql.append(" h.yhwj, ");
		sql.append(" h.num2,");
		sql.append(" h.num5,");
		sql.append(" h.num8,");
		sql.append(" h.num10");
		sql.append(" from hd_rept2 h  ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");

		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and h.sdate ='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("结束日期")) {
							wsql = wsql + " and h.ddate  ='"
									+ vos[i].getValue() + "'";
							ddate = vos[i].getValue();
						}
					}
					if (code.equals("h.projectid")) {
						prid = vos[i].getValue();
						wsql = wsql + " and h.projectid  ='"
						+ vos[i].getValue() + "'";
			
					}
				}
			}
			sql.append(wsql);
		}
		return sql.toString();
	}


	private String[] qsqls = null;

	public void onQry() {
		getQueryDlg().showModal();
		if (getQueryDlg().getResult() == UIDialog.ID_OK) {
			try {
				// 清空表体数据
				clearBody();
				setDynamicColumn1();
				// 得到查询结果
				setColumn();
				sdate = null;
				ddate = null;
				prid = null;
				List<ReportBaseVO[]> list = getReportVO(new String[] { getQuerySql(),getQuerySql1() });		
				valute3();
				qsqls = getSqls();
				ReportBaseVO[] vos = null;
				vos = deal(list);
				sves = vos;
				if (vos == null || vos.length == 0)
					return;
				if (vos != null) {
					super.updateBodyDigits();
					setBodyVO(vos);
					updateVOFromModel();// 重新加载初始化公式
					dealQueryAfter();// 查询后的后续处理 一般用于 默认数据交叉之类的操作
				}
			} catch (BusinessException e) {
				e.printStackTrace();
				this.showErrorMessage(e.getMessage());

			} catch (Exception e) {
				e.printStackTrace();
				this.showErrorMessage(e.getMessage());
			}
		}
	}
    /**
     * 处理查询后 设置的界面之前的数据
     * @param list
     * @return
     * @throws Exception 
     */
	private ReportBaseVO[] deal(List<ReportBaseVO[]> list) throws Exception {
		if (list == null || list.size() == 0) {
			return null;
		}
		ReportBaseVO[] vos1 = list.get(0);// 付款损益
		
		ReportBaseVO[] avos1 = (ReportBaseVO[]) CombinVO.combinData(vos1,
				new String[] { "projectid","pk_currtype"}, new String[] { "num6", "num11","num2","num5","num8","num10"}, ReportBaseVO.class);
		

		ReportBaseVO[] vos2 = list.get(1);// 收款损益
				
		ReportBaseVO[] avos2 = (ReportBaseVO[]) CombinVO.combinData(vos2,
				new String[] { "projectid","pk_currtype" }, new String[] { "num6", "num11","num2","num5","num8","num10"}, ReportBaseVO.class);
		
		// num2本期已付款汇率损益        num5本期未付款汇率损益       num8累计已付款汇率损益              num10累计未付款汇率损益
		ReportBaseVO[] zvos = CombinVO.addByContion2(avos1, avos2,
				new String[] { "projectid","pk_currtype" }, "x");// 合并新增合同和 原有合同
		
		
		setQueryDate(zvos);
		sves = zvos;
		return zvos;
	}

	/**
	 * 查询前数据校验
	 * 
	 * @throws Exception
	 */
	private void valute3() throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("没有开始日期");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("没有结束日期");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("开始日期   必须小于   结束日期");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("从开始日期  到结束日期  没有找到会计期间 ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("从开始日期  到结束日期 跨会计期间");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("开始日期  与 会计月 开始日期 不一致 ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("结束日期  与 会计月 结束日期 不一致 ");
		}

	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { qry, save1,IReportButton.PrintBtn };
		return m_buttonArray;
	}

	protected void initPrivateButton() {
		addPrivateButton("保存设置", "保存设置", CaPuBtnConst.save);
		addPrivateButton("计算", "计算", cal);
		addPrivateButton("保存", "保存", save1);
		addPrivateButton("查询", "查询", qry);
		super.initPrivateButton();
	}

	protected void onBoElse(Integer intBtn) throws Exception {
		switch (intBtn) {
		case CaPuBtnConst.save: {
			onBoSave();
			this.showHintMessage("本次报表配置保存成功");
			break;
		}
		case save1: {
			onSave();
			this.showHintMessage("保存成功");
			break;
		}
		case cal: {
			super.onQuery();
			break;
		}
		case qry: {
			onQry();
			this.showHintMessage("查询成功");
			break;
		}
		}
		super.onBoElse(intBtn);
	}

	/**
	 * 报表保存设置按钮
	 */
	public void onBoSave() {
		if (getBuff() == null) {
			return;
		} else {
			updateBuffer(getBuff());
		}
	}

}
