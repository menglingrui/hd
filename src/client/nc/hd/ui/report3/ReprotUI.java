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
 * �ۺϻ����������
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// ��ѯ��ʼ����

	private String ddate = "";// ��ѯ��������

	private String prid = "";// ��Ŀid

//	private String pk_ctpe = "1002A2100000000005FB";// ��Ŀ��ͬ �������ͬ����)
//
//	private String pk_bz = "00010000000000000001";// ����ұ�������
//
//	private String jybm = "F3-01";// ��Ŀ��� �������ͱ���
//
//	private String bo = "nc.bs.hd.report1.ReportDMO";

	public static final int save1 = 150;// �����水ť

	public static final int cal = 152;// ������㰴ť

	public static final int qry = 154;// �����ѯ��ť
	
	private String bo = "nc.bs.hd.report3.ReportDMO";

	/**
	 * ���ղ�ѯ�����sql
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:41:05
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
	 * ���õ�ui����֮ǰ ��������ѯ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		return null;
	}

//	public void onSave() throws Exception {
//		if (sves == null || sves.length == 0) {
//			this.showErrorMessage("û������");
//			return;
//		}
//
//		valudate(sves);
//		if (valudate1(sves)) {
//			int retu = showOkCancelMessage("�Ѿ����� ������ڼ������  �������  ���Ḳ���ѱ��������");
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
//		Object o = LongTimeTask.calllongTimeService("ic", null, "���ڱ���...", 1,
//				bo, null, "onBoSave", ParameterTypes, ParameterValues);
//
//	}

//	/**
//	 * У�� ��Ŀ ��ʼ���� �������� ���ά�� �Ƿ�������� ������� ������ʾ
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
//		Object o = LongTimeTask.calllongTimeService("ic", null, "����У��...", 1,
//				bo, null, "valute", ParameterTypes, ParameterValues);
//		Integer in = PuPubVO.getInteger_NullAs(o, -1);
//		if (in > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * �Ա�������ݺϷ��Խ���У��
	 * 
	 * @param sves2
	 */
	public void valudate(ReportBaseVO[] sves2) throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("û�п�ʼ����");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("û�н�������");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("��ʼ����   ����С��   ��������");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("�ӿ�ʼ����  ����������  û���ҵ�����ڼ� ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("�ӿ�ʼ����  ���������� �����ڼ�");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("��ʼ����  �� ����� ��ʼ���� ��һ�� ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("��������  �� ����� �������� ��һ�� ");
		}

	}

	/**
	 * ��ѯ�����ÿ�ʼ���ںͽ�������
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
	 * ���ò�ѯ��ʼ���ںͽ�������
	 * 
	 * @param vos
	 */
	private void setdate(ConditionVO[] vos) {
		if (vos == null || vos.length == 0) {
			return;
		}
		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getFieldCode().equals("b.def2")) {
				if (vos[i].getFieldName().equals("��ʼ����")) {
					sdate = vos[i].getValue();
				} else {
					ddate = vos[i].getValue();
				}
			}
		}
	}



	

	/**
	 * ������Ŀ ��Ŀ���������� id �����Ŀ�������� id
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
	 * ֻ��ȡ���ڴ���Ĳ�ѯ����
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-10����10:25:17
	 * @return
	 */
	private String getwheresql() {
		QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի���
		ConditionVO[] vos = querylg.getConditionVO();// ��ȡ�ѱ��û���д�Ĳ�ѯ����
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
	 * ��ѯ��� ���õ�ui����֮�� ��������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public void dealQueryAfter() throws Exception {
		super.dealQueryAfter();
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report3.ReportVO3");
	}

	/**
	 * ���ñ����ܽڵ��
	 */
	public String _getModelCode() {
		return "40205030";
	}
    /**
     * ��ѯ�����������
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
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and h.sdate ='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("��������")) {
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
	 * �Ա�������ݺϷ��Խ���У��
	 * 
	 * @param sves2
	 */
	public void valudate() throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("û�п�ʼ����");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("û�н�������");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("��ʼ����   ����С��   ��������");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("�ӿ�ʼ����  ����������  û���ҵ�����ڼ� ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("�ӿ�ʼ����  ���������� �����ڼ�");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("��ʼ����  �� ����� ��ʼ���� ��һ�� ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("��������  �� ����� �������� ��һ�� ");
		}

	}
	public void onSave() throws Exception {
		
		if (sves == null || sves.length == 0) {
			this.showErrorMessage("û������");
			return;
		}

		valudate();
		if (valudate1()) {
			int retu = showOkCancelMessage("�Ѿ����� ������ڼ������  �������  ���Ḳ���ѱ��������");
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

		Object o = LongTimeTask.calllongTimeService("ic", null, "���ڱ���...", 1,
				bo, null, "onBoSave", ParameterTypes, ParameterValues);

	}
	/**
	 * У�� ��Ŀ ��ʼ���� �������� ���ά�� �Ƿ�������� ������� ������ʾ
	 * 
	 * @param sves2
	 */
	public boolean valudate1() throws Exception {

		Class[] ParameterTypes = new Class[] { String.class, String.class,
				String.class };

		Object[] ParameterValues = new Object[] { prid, sdate, ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "����У��...", 1,
				bo, null, "valute", ParameterTypes, ParameterValues);
		Integer in = PuPubVO.getInteger_NullAs(o, -1);
		if (in > 0) {
			return true;
		} else {
			return false;
		}
	}
	 /**
     * ��ѯ�տ��������
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
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and h.sdate ='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("��������")) {
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
				// ��ձ�������
				clearBody();
				setDynamicColumn1();
				// �õ���ѯ���
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
					updateVOFromModel();// ���¼��س�ʼ����ʽ
					dealQueryAfter();// ��ѯ��ĺ������� һ������ Ĭ�����ݽ���֮��Ĳ���
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
     * �����ѯ�� ���õĽ���֮ǰ������
     * @param list
     * @return
     * @throws Exception 
     */
	private ReportBaseVO[] deal(List<ReportBaseVO[]> list) throws Exception {
		if (list == null || list.size() == 0) {
			return null;
		}
		ReportBaseVO[] vos1 = list.get(0);// ��������
		
		ReportBaseVO[] avos1 = (ReportBaseVO[]) CombinVO.combinData(vos1,
				new String[] { "projectid","pk_currtype"}, new String[] { "num6", "num11","num2","num5","num8","num10"}, ReportBaseVO.class);
		

		ReportBaseVO[] vos2 = list.get(1);// �տ�����
				
		ReportBaseVO[] avos2 = (ReportBaseVO[]) CombinVO.combinData(vos2,
				new String[] { "projectid","pk_currtype" }, new String[] { "num6", "num11","num2","num5","num8","num10"}, ReportBaseVO.class);
		
		// num2�����Ѹ����������        num5����δ�����������       num8�ۼ��Ѹ����������              num10�ۼ�δ�����������
		ReportBaseVO[] zvos = CombinVO.addByContion2(avos1, avos2,
				new String[] { "projectid","pk_currtype" }, "x");// �ϲ�������ͬ�� ԭ�к�ͬ
		
		
		setQueryDate(zvos);
		sves = zvos;
		return zvos;
	}

	/**
	 * ��ѯǰ����У��
	 * 
	 * @throws Exception
	 */
	private void valute3() throws Exception {

		if (sdate == null || sdate.length() == 0)
			throw new Exception("û�п�ʼ����");

		if (ddate == null || ddate.length() == 0)
			throw new Exception("û�н�������");

		if (ddate.compareTo(sdate) < 0) {
			throw new Exception("��ʼ����   ����С��   ��������");
		}

		AccountCalendar ac = AccountCalendar.getInstance();
		UFDate sd = new UFDate(sdate);
		UFDate dd = new UFDate(ddate);
		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);

		if (kjqjs == null) {
			throw new Exception("�ӿ�ʼ����  ����������  û���ҵ�����ڼ� ");
		}

		if (kjqjs.length > 1) {
			throw new Exception("�ӿ�ʼ����  ���������� �����ڼ�");
		}

		AccperiodmonthVO vo = kjqjs[0];

		UFDate ks = vo.getBegindate();
		UFDate ke = vo.getEnddate();

		if (!sd.equals(ks)) {
			throw new Exception("��ʼ����  �� ����� ��ʼ���� ��һ�� ");
		}

		if (!ke.equals(dd)) {
			throw new Exception("��������  �� ����� �������� ��һ�� ");
		}

	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { qry, save1,IReportButton.PrintBtn };
		return m_buttonArray;
	}

	protected void initPrivateButton() {
		addPrivateButton("��������", "��������", CaPuBtnConst.save);
		addPrivateButton("����", "����", cal);
		addPrivateButton("����", "����", save1);
		addPrivateButton("��ѯ", "��ѯ", qry);
		super.initPrivateButton();
	}

	protected void onBoElse(Integer intBtn) throws Exception {
		switch (intBtn) {
		case CaPuBtnConst.save: {
			onBoSave();
			this.showHintMessage("���α������ñ���ɹ�");
			break;
		}
		case save1: {
			onSave();
			this.showHintMessage("����ɹ�");
			break;
		}
		case cal: {
			super.onQuery();
			break;
		}
		case qry: {
			onQry();
			this.showHintMessage("��ѯ�ɹ�");
			break;
		}
		}
		super.onBoElse(intBtn);
	}

	/**
	 * ���������ð�ť
	 */
	public void onBoSave() {
		if (getBuff() == null) {
			return;
		} else {
			updateBuffer(getBuff());
		}
	}

}
