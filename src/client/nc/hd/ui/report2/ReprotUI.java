package nc.hd.ui.report2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bd.accperiod.AccountCalendar;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.scm.util.ObjectUtils;
import nc.ui.trade.report.query.QueryDLG;
import nc.ui.zmpub.pub.report.buttonaction2.CaPuBtnConst;
import nc.ui.zmpub.pub.report.buttonaction2.IReportButton;
import nc.ui.zmpub.pub.tool.LongTimeTask;
import nc.ui.zmpub.pub.tool.SingleVOChangeDataUiTool;
import nc.vo.bd.period2.AccperiodmonthVO;
import nc.vo.hd.report2.ReportVO2;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
import nc.vo.zmpub.pub.tool.ZmPubTool;

/**
 * 收款汇率损益计算
 * 
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// 查询开始日期

	private String ddate = "";// 查询结束日期

	private String prid = "";// 项目id

	private String pk_ctpe = "1002XC1000000000177L";// 项目合同 （收款合同类型)

	private String pk_bz = "00010000000000000001";// 人民币币种主键

	private String jybm = "F2-01";// 项目收款单 交易类型编码
	
	private String jybm1 = "F2-02";// 项目收款单 交易类型编码

	private String bo = "nc.bs.hd.report2.ReportDMO";

	public static final int save1 = 150;// 报表保存按钮

	public static final int cal = 152;// 报表计算按钮

	public static final int qry = 154;// 报表查询按钮

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

		return new String[] { getSql(), getSql2(), getSql3(), getSql4(),
				getSql5(), getSql6() };
	}
    /**
     * 设置金额字段的显示精度 模版赋值前设置精度
     */
    protected void setDecimalDigits() {
        BillModel bm = getReportBase().getBillModel();
        BillItem[] items = bm.getBodyItems();
        if (items == null || items.length == 0)
            return;
        for (int i = 0; i < items.length; i++) {
            int idatatype = items[i].getDataType();
            if (idatatype == IBillItem.DECIMAL && isMnyItem(items[i].getKey())) {   
            	if(items[i].getKey().equals("yshl") || items[i].getKey().equals("sqhl") || items[i].getKey().equals("bqhl")){
            		items[i].setDecimalDigits(4);
            	}else{
                     /////////////////////------------------------------------------zpm注销，可以设置默认精度 为 8-------------??????????????????????????????????
                    items[i].setDecimalDigits(2);
            	}
            }
        }

    }
	
	private nc.vo.pub.CircularlyAccessibleValueObject[] sves;

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
		if (list == null || list.size() == 0) {
			return null;
		}
		ReportBaseVO[] vos1 = list.get(0);// 原有合同

		ReportBaseVO[] vos2 = list.get(1);// 新增合同

		ReportBaseVO[] zvos = CombinVO.addByContion2(vos1, vos2,
				new String[] { "pk_ct_manage_b" }, "");// 合并新增合同和 原有合同

		ReportBaseVO[] vos3 = list.get(2);// 本期付款单 新增合同

		ReportBaseVO[] zvos1 = (ReportBaseVO[]) CombinVO.combinData(vos3,
				new String[] { "pk_ct_manage_b" }, new String[] { "bxhtfk",
						"num111" }, ReportBaseVO.class);// 按来源合同行id合并数据

		ReportBaseVO[] vos4 = list.get(3);// 本期付款单 原有合同

		ReportBaseVO[] zvos2 = (ReportBaseVO[]) CombinVO.combinData(vos4,
				new String[] { "pk_ct_manage_b" }, new String[] { "byhtfk",
						"num112" }, ReportBaseVO.class);// 按来源合同行id合并数据

		ReportBaseVO[] avos = CombinVO.addByContion1(zvos, zvos1,
				new String[] { "pk_ct_manage_b" }, "");// 将付款信息追加到合同

		ReportBaseVO[] avos1 = CombinVO.addByContion1(avos, zvos2,
				new String[] { "pk_ct_manage_b" }, "");// 将付款信息追加到合同

		ReportBaseVO[] vos5 = list.get(4);// 本期付款单 原有合同

		ReportBaseVO[] zvos5 = (ReportBaseVO[]) CombinVO.combinData(vos5,
				new String[] { "pk_ct_manage_b" }, new String[] { "num22",
						"num23" }, ReportBaseVO.class);// 按来源合同行id合并数据

		ReportBaseVO[] avos2 = CombinVO.addByContion1(avos1, zvos5,
				new String[] { "pk_ct_manage_b" }, "");// 将付款信息追加到合同

		ReportBaseVO[] vos6 = list.get(5);// 本期付款单 原有合同
		ReportBaseVO[] zvos6 = (ReportBaseVO[]) CombinVO.combinData(vos6,
				new String[] { "pk_ct_manage_b" }, new String[] { "num33",
						"num34" }, ReportBaseVO.class);// 按来源合同行id合并数据

		ReportBaseVO[] avos3 = CombinVO.addByContion1(avos2, zvos6,
				new String[] { "pk_ct_manage_b" }, "");// 将付款信息追加到合同

		// 按照合同id 进行数据合并

		ReportBaseVO[] avos4 = (ReportBaseVO[]) CombinVO.combinData(avos3,
				new String[] { "pk_ct_manage" }, new String[] { "yhwj", "xhwj",
						"bxhtfk", "num111", "byhtfk", "num112", "num22",
						"num23", "num33", "num34" }, ReportBaseVO.class);

		//查看条件是否按  项目合并
		QueryDLG querylg= getQueryDlg();//获取查询对话框		
		ConditionVO[] cvos=querylg.getConditionVO();//获取已被用户填写的查询条件
		ConditionVO[] cvs=filterOrderBy(cvos);
		UFBoolean iscbin=new UFBoolean(false);
		if(cvs!=null && cvs.length>0){
			 iscbin=PuPubVO.getUFBoolean_NullAs(cvs[0].getValue(), new UFBoolean(false));
		}
		ReportBaseVO[] avos5=avos4;
		//是否按项目合并
		if(iscbin.booleanValue()==true){
			avos5 = (ReportBaseVO[]) CombinVO.combinData(avos4,
					new String[] { "projectid","pk_currtype"}, new String[] {"def5","def9", "yhwj", "xhwj",
							"bxhtfk", "num111", "byhtfk", "num112", "num22",
							"num23", "num33", "num34" }, ReportBaseVO.class);
		}
		setQueryDate(avos5);
		setRate(avos5);
		return avos5;
	}
	/**
	 * 查询汇率
	 * @param date
	 * @throws Exception 
	 */
	private UFDouble queryRate(String date,String pk_currtype) throws Exception {
		Class[] ParameterTypes = new Class[] { String.class, String.class};
		Object[] ParameterValues = new Object[] { date,pk_currtype };
		Object o = LongTimeTask.calllongTimeService("ic", null, "正在查询...", 1,
				bo, null, "queryRate", ParameterTypes, ParameterValues);
		 UFDouble uf = PuPubVO.getUFDouble_NullAsZero(o );
		return uf;
	}
	private void setRate(ReportBaseVO[] avos) throws Exception {
		if (avos == null || avos.length == 0) {
			return;
		}
		//为了防止重新查询汇率，所以用map来存放已经用过的汇率
		Map<String,String> map=new HashMap<String, String>();
		
		for (int i = 0; i < avos.length; i++) {
			map.put((String)avos[i].getAttributeValue("pk_currtype"),(String)avos[i].getAttributeValue("pk_currtype"));		
		}
		Map<String,UFDouble> hmap=new HashMap<String, UFDouble>();
		UFDate newdate=new UFDate(sdate);
		newdate=newdate.getDateBefore(1);
		String qsdate=newdate.toString();
	    for(String key:map.keySet()){
	    	UFDouble srate=queryRate(qsdate,key);
			UFDouble drate=queryRate(ddate,key);
			hmap.put(qsdate+key, srate);
			hmap.put(ddate+key, drate);
	    }
			
		for (int i = 0; i < avos.length; i++) {
			String pk_currtype =(String) avos[i].getAttributeValue("pk_currtype");
			UFDouble srate =hmap.get(qsdate+pk_currtype);
			if(srate==null ||srate.doubleValue()==0){
				String begindate=(String) avos[i].getAttributeValue("begindate");
				srate =queryRate(begindate,pk_currtype);
			}
			UFDouble drate =hmap.get(ddate+pk_currtype);	
			if(srate==null || srate.doubleValue()==0)
				srate=new UFDouble(1);
			if(drate==null || drate.doubleValue()==0)
				drate=new UFDouble(1);
		   	avos[i].setAttributeValue("sqhl", srate);
			avos[i].setAttributeValue("bqhl", drate);
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
		ReportVO2[] vos = (ReportVO2[]) SingleVOChangeDataUiTool
				.runChangeVOAryForUI(sves, ReportVO2.class,
						"nc.ui.pf.changedir.CHGReport2toVo");

		Class[] ParameterTypes = new Class[] { ReportVO2[].class, String.class,
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
	 * 查询付款合同 查询开始日期之前的 （原有合同）
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_corp , ");
		sql.append(" b.pk_ct_manage_b ,");// 合同表体主键
		sql.append(" h.pk_ct_type  , ");// 合同类型
		sql.append(" h.pk_ct_manage  , ");// 合同类型
		sql.append(" h.ct_code, ");// 合同号
		sql.append(" h.projectid ,");// 项目
		sql.append(" h.custid ,");// 客商id
		sql.append(" h.currid pk_currtype,");// 币种
		sql.append(" h.def5 ,");// 期初付款原币金额
		sql.append(" h.def9 ,");// 期初付款本币金额
		sql.append(" b.oritaxsummny yhwj");// 原有合同价税合计
		sql.append(" from ct_manage h ");
		sql.append(" join ct_manage_b b on h.pk_ct_manage=b.pk_ct_manage ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0) = 0 ");
		sql.append(" and isnull(b.dr,0)=0 ");
		sql.append(" and h.activeflag=0 ");// 合同激活标志
		sql.append(" and h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and h.pk_ct_type='" + pk_ctpe + "'");
		sql.append(" and h.currid <>'" + pk_bz + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话框
			ConditionVO[] vos = filterQuery(querylg.getConditionVO());// 获取已被用户填写的查询条件
			setdate(vos);
			if (vos == null || vos.length == 0) {

			} else {
				boolean isksri = false;
				for (int i = 0; i < vos.length; i++) {
					String name = vos[i].getFieldName();
					if (name.equals("开始日期")) {
						wheresql = wheresql + " and b.def2 < '"
								+ vos[i].getValue() + "'";
						isksri = true;
					}
					if (name.equals("项目")) {
						wheresql = wheresql + " and  h.projectid  = '"
								+ vos[i].getValue() + "'";
						prid = vos[i].getValue();
					}
				}
				if (isksri == false) {
					// 添加限制条件 不允许查出结果
					wheresql = wheresql + " and 1=0 ";
				}
			}
			sql.append(wheresql);
		}
		return sql.toString();
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
	 * 查询付款合同 查询 开始日期到结束日期 （新增合同）
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql2() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_corp , ");
		sql.append(" h.ct_code, ");// 合同号
		sql.append(" h.pk_ct_type  , ");// 合同类型
		sql.append(" h.pk_ct_manage  , ");// 合同类型
		sql.append(" b.pk_ct_manage_b ,");// 合同表体主键
		sql.append(" h.projectid ,");// 项目
		sql.append(" h.custid ,");// 客商id
		sql.append(" h.currid pk_currtype ,");// 币种
		sql.append(" h.def5 ,");// 期初付款原币金额
		sql.append(" h.def9 ,");// 期初付款本币金额
		sql.append(" b.oritaxsummny xhwj");// 新增合同价税合计
		sql.append(" from ct_manage h ");
		sql.append(" join ct_manage_b b on h.pk_ct_manage=b.pk_ct_manage ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0) = 0 ");
		sql.append(" and h.activeflag=0 ");// 合同激活标志
		sql.append(" and isnull(b.dr,0)=0 ");
		sql.append(" and h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and h.pk_ct_type='" + pk_ctpe + "'");
		sql.append(" and h.currid <>'" + pk_bz + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0)
			sql.append(" and " + getQuerySQL());
		return sql.toString();
	}

	/**
	 * 查询付款单 按开始日期 和 结束日期查询 按开始时间和结束时间过滤合同 即 新增合同本期付款
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql3() throws Exception {
//		sql.append(" b.dfybje  num22,");// 付款原币金额
//		sql.append(" b.dfbbwsje num23, ");// 付款本币金额
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// 公司
		sql.append(" h.ywbm  , ");// 交易类型
		sql.append(" h.djbh ,");// 单据号
		sql.append(" h.djrq ,");// 单据日期
		sql.append(" h.pj_jsfs, ");// 结算方式
		sql.append(" b.dfybje bxhtfk,");// 付款原币金额
		sql.append(" b.dfbbwsje num111, ");// 付款本币金额
		sql.append(" b.cksqsh pk_ct_manage_b ");// 上层来源单据行id
		sql.append(" from arap_djzb h ");
		sql.append(" join arap_djfb b on h.vouchid =b.vouchid  ");
		sql.append(" join ct_manage_b c on b.cksqsh=c.pk_ct_manage_b ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and  isnull(b.dr,0)=0 ");
		sql.append(" and  isnull(c.dr,0)=0 ");
		sql.append(" and h.dwbm='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and b.bzbm <>'" + pk_bz + "'");

		sql.append(" and h.djlxbm in ('" + jybm + "','"+jybm1+"') ");

		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话框
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
			ConditionVO[] vos1 = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						vos[i].setFieldCode("h.djrq");
						vos1[i].setFieldCode("c.def2");
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						vos[i].setValue(pk);
						vos1[i].setValue(pk);
						vos[i].setFieldCode("b.jobid");
						vos1[i].setFieldCode("b.jobid");
					}
				}
			}
			sql.append(" and " + querylg.getWhereSQL(vos));
			sql.append(" and " + querylg.getWhereSQL(vos1));

		}
		return sql.toString();
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
	 * 查询付款单 按开始日期 和 结束日期查询 过滤开始日期前的合同 即原有合同付款
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @throws Exception
	 */
	private String getSql4() throws Exception {
//		sql.append(" b.dfybje  num22,");// 付款原币金额
//		sql.append(" b.dfbbwsje num23, ");// 付款本币金额
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// 公司
		sql.append(" h.ywbm  , ");// 交易类型
		sql.append(" h.djbh ,");// 单据号
		sql.append(" h.djrq ,");// 单据日期
		sql.append(" b.dfybje byhtfk,");// 付款原币金额
		sql.append(" b.dfbbwsje num112, ");// 付款本币金额
		sql.append(" b.cksqsh pk_ct_manage_b ");// 上层来源单据行id
		sql.append(" from arap_djzb h ");
		sql.append(" join arap_djfb b on h.vouchid =b.vouchid  ");
		sql.append(" join ct_manage_b c on b.cksqsh=c.pk_ct_manage_b ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and  isnull(b.dr,0)=0 ");
		sql.append(" and  isnull(c.dr,0)=0 ");
		sql.append(" and h.dwbm='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and b.bzbm <>'" + pk_bz + "'");
		sql.append(" and h.djlxbm in ('" + jybm + "','"+jybm1+"') ");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						vos[i].setFieldCode("h.djrq");
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and c.def2< '" + vos[i].getValue()
									+ "'";
						}
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						vos[i].setValue(pk);
						vos[i].setFieldCode("b.jobid");
					}
				}
			}
			sql.append(" and " + querylg.getWhereSQL(vos));
			sql.append(wsql);
		}
		return sql.toString();
	}

	/**
	 * 查询原有合同累计已付款 (过滤结束日期之前的付款单 , 过滤开始日期前的合同 )
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @throws Exception
	 */
	private String getSql5() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// 公司
		sql.append(" h.ywbm  , ");// 交易类型
		sql.append(" h.djbh ,");// 单据号
		sql.append(" h.djrq ,");// 单据日期
		sql.append(" b.dfybje  num22,");// 付款原币金额
		sql.append(" b.dfbbwsje num23, ");// 付款本币金额
		sql.append(" b.cksqsh pk_ct_manage_b ");// 上层来源单据行id
		sql.append(" from arap_djzb h ");
		sql.append(" join arap_djfb b on h.vouchid =b.vouchid  ");
		sql.append(" join ct_manage_b c on b.cksqsh=c.pk_ct_manage_b ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and  isnull(b.dr,0)=0 ");
		sql.append(" and  isnull(c.dr,0)=0 ");
		sql.append(" and h.dwbm='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and b.bzbm <>'" + pk_bz + "'");
		sql.append(" and h.djlxbm in ('" + jybm + "','"+jybm1+"') ");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and c.def2< '" + vos[i].getValue()
									+ "'";
						}
						if (vos[i].getFieldName().equals("结束日期")) {
							wsql = wsql + " and h.djrq< ='" + vos[i].getValue()
									+ "'";
						}
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						wsql = wsql + " and b.jobid ='" + pk + "'";
					}
				}
			}
			sql.append(wsql);
		}
		return sql.toString();
	}

	/**
	 * 查询新增合同累计已付款 (过滤结束日期之前的付款单 , 过滤开始日期到结束日期之间的合同 )
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @throws Exception
	 */
	private String getSql6() throws Exception {
//		sql.append(" b.dfybje  num22,");// 付款原币金额
//		sql.append(" b.dfbbwsje num23, ");// 付款本币金额
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// 公司
		sql.append(" h.ywbm  , ");// 交易类型
		sql.append(" h.djbh ,");// 单据号
		sql.append(" h.djrq ,");// 单据日期
		sql.append(" b.dfybje num33,");// 付款原币金额
		sql.append(" b.dfbbwsje num34, ");// 付款本币金额
		sql.append(" b.cksqsh pk_ct_manage_b ");// 上层来源单据行id
		sql.append(" from arap_djzb h ");
		sql.append(" join arap_djfb b on h.vouchid =b.vouchid  ");
		sql.append(" join ct_manage_b c on b.cksqsh=c.pk_ct_manage_b ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0)=0 ");
		sql.append(" and  isnull(b.dr,0)=0 ");
		sql.append(" and  isnull(c.dr,0)=0 ");
		sql.append(" and h.dwbm='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and b.bzbm <>'" + pk_bz + "'");
		sql.append(" and h.djlxbm in ('" + jybm + "','"+jybm1+"') ");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and c.def2>='" + vos[i].getValue()
									+ "'";
						}
						if (vos[i].getFieldName().equals("结束日期")) {
							wsql = wsql + " and h.djrq< ='" + vos[i].getValue()
									+ "'";
							wsql = wsql + " and c.def2<= '" + vos[i].getValue()
									+ "'";

						}
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						wsql = wsql + " and b.jobid ='" + pk + "'";
					}
				}
			}
			sql.append(wsql);
		}

		return sql.toString();
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
		ConditionVO[] vos = filterQuery(querylg.getConditionVO());// 获取已被用户填写的查询条件
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
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report1.ReportVO1");

	}

	/**
	 * 设置报表功能节点号
	 */
	public String _getModelCode() {
		return "40205020";
	}

	private String getQuerySql() throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_rept2 , ");
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
		sql.append(" h.num23 ,");
		sql.append(" h.yhwj ");
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
					.serializableClone(filterQuery(querylg.getConditionVO()));// 获取已被用户填写的查询条件
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

	private String qsql = null;

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
				List<ReportBaseVO[]> list = getReportVO(new String[] { getQuerySql() });
				valute3();
				qsql = getQuerySql();
				ReportBaseVO[] vos = null;
				vos = list.get(0);
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
		m_buttonArray = new int[] { qry, cal, save1,IReportButton.PrintBtn ,IReportButton.LevelSubTotalBtn};
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
