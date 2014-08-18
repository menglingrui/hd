package nc.hd.ui.report1;
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
import nc.vo.hd.report1.ReportVO1;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
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
 * ��������������
 * 
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// ��ѯ��ʼ����

	private String ddate = "";// ��ѯ��������

	private String prid = "";// ��Ŀid

	private String pk_ctpe = "1002A2100000000005FB";// ��Ŀ��ͬ �������ͬ����)

	private String pk_bz = "00010000000000000001";// ����ұ�������

	private String jybm = "F3-01";// ��Ŀ��� �������ͱ���

	private String bo = "nc.bs.hd.report1.ReportDMO";
	public  String depttype="00010000000000000002";//��ƿ�Ŀ �������� ������������
	
	
	public  String protype="UAP00000000000jobass";//��ƿ�Ŀ ��������  ������Ŀ����

	public static final int save1 = 150;// �������水ť

	public static final int cal = 152;// �������㰴ť

	public static final int qry = 154;// ������ѯ��ť

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

		return new String[] { getSql(), getSql2(), getSql3(), getSql4(),
				getSql5(), getSql6() ,getSql7(),getSql8()};
	}
	public ReprotUI() {
		super();		
		setDefaultDecimalDigits();
	}
    /**
     * ���ý���ֶε���ʾ����
     */
    protected void setDefaultDecimalDigits() {
    	super.setDefaultDecimalDigits();
        BillModel bm = getReportBase().getBillModel();
        BillItem[] items = bm.getBodyItems();
        if (items == null || items.length == 0)
            return;
        for (int i = 0; i < items.length; i++) {
            int idatatype = items[i].getDataType();
            if (idatatype == IBillItem.DECIMAL) {
                items[i].setDecimalDigits(2);
            }
        }
    }
    /**
     * ���ý���ֶε���ʾ���� ģ�渳ֵǰ���þ���
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
                     /////////////////////------------------------------------------zpmע������������Ĭ�Ͼ��� Ϊ 8-------------??????????????????????????????????
                    items[i].setDecimalDigits(2);
            	}
            }
        }

    }

	private CircularlyAccessibleValueObject[] sves;

	/**
	 * ���õ�ui����֮ǰ ���������ѯ�������
	 * 
	 * @author mlr
	 * @˵�������׸ڿ�ҵ�� 2011-12-22����10:42:36
	 * @param list
	 * @return
	 */
	public ReportBaseVO[] dealBeforeSetUI(List<ReportBaseVO[]> list)
			throws Exception {
		if (list == null || list.size() == 0) {
			return null;
		}
		ReportBaseVO[] vos1 = list.get(0);// ԭ�к�ͬ

		ReportBaseVO[] vos2 = list.get(1);// ������ͬ

		ReportBaseVO[] zvos = CombinVO.addByContion2(vos1, vos2,
				new String[] { "pk_ct_manage_b" }, "");// �ϲ�������ͬ�� ԭ�к�ͬ

		ReportBaseVO[] vos3 = list.get(2);// ���ڸ�� ������ͬ

		ReportBaseVO[] zvos1 = (ReportBaseVO[]) CombinVO.combinData(vos3,
				new String[] { "pk_ct_manage_b" }, new String[] { "bxhtfk",
						"num111" }, ReportBaseVO.class);// ����Դ��ͬ��id�ϲ�����

		ReportBaseVO[] vos4 = list.get(3);// ���ڸ�� ԭ�к�ͬ

		ReportBaseVO[] zvos2 = (ReportBaseVO[]) CombinVO.combinData(vos4,
				new String[] { "pk_ct_manage_b" }, new String[] { "byhtfk",
						"num112" }, ReportBaseVO.class);// ����Դ��ͬ��id�ϲ�����

		ReportBaseVO[] avos = CombinVO.addByContion1(zvos, zvos1,
				new String[] { "pk_ct_manage_b" }, "");// ��������Ϣ׷�ӵ���ͬ

		ReportBaseVO[] avos1 = CombinVO.addByContion1(avos, zvos2,
				new String[] { "pk_ct_manage_b" }, "");// ��������Ϣ׷�ӵ���ͬ

		ReportBaseVO[] vos5 = list.get(4);// ���ڸ�� ԭ�к�ͬ

		ReportBaseVO[] zvos5 = (ReportBaseVO[]) CombinVO.combinData(vos5,
				new String[] { "pk_ct_manage_b" }, new String[] { "num22",
						"num23" }, ReportBaseVO.class);// ����Դ��ͬ��id�ϲ�����

		ReportBaseVO[] avos2 = CombinVO.addByContion1(avos1, zvos5,
				new String[] { "pk_ct_manage_b" }, "");// ��������Ϣ׷�ӵ���ͬ

		ReportBaseVO[] vos6 = list.get(5);// ���ڸ�� ԭ�к�ͬ
		ReportBaseVO[] zvos6 = (ReportBaseVO[]) CombinVO.combinData(vos6,
				new String[] { "pk_ct_manage_b" }, new String[] { "num33",
						"num34" }, ReportBaseVO.class);// ����Դ��ͬ��id�ϲ�����

		ReportBaseVO[] avos3 = CombinVO.addByContion1(avos2, zvos6,
				new String[] { "pk_ct_manage_b" }, "");// ��������Ϣ׷�ӵ���ͬ

		// ���պ�ͬid �������ݺϲ�

		ReportBaseVO[] avos4 = (ReportBaseVO[]) CombinVO.combinData(avos3,
				new String[] { "pk_ct_manage" }, new String[] { "yhwj", "xhwj",
						"bxhtfk", "num111", "byhtfk", "num112", "num22",
						"num23", "num33", "num34" }, ReportBaseVO.class);
		//�鿴�����Ƿ�  ��Ŀ�ϲ�
		QueryDLG querylg= getQueryDlg();//��ȡ��ѯ�Ի���		
		ConditionVO[] cvos=querylg.getConditionVO();//��ȡ�ѱ��û���д�Ĳ�ѯ����
		ConditionVO[] cvs=filterOrderBy(cvos);
		UFBoolean iscbin=new UFBoolean(false);
		if(cvs!=null && cvs.length>0){
			 iscbin=PuPubVO.getUFBoolean_NullAs(cvs[0].getValue(), new UFBoolean(false));
		}
//		buf.append(" e.dispname ct_code,");//��Ŀ����----��ͬ��
//		buf.append(" h.prepareddatev djrq,");//��������
//		buf.append(" d.pk_jobmngfil projectid, ");//��Ŀ
		ReportBaseVO[] vos7 = list.get(6);//������
		ReportBaseVO[] vos8 = (ReportBaseVO[]) CombinVO.combinData(vos7,
				new String[] { "ct_code","projectid","pk_currtype" }, new String[] { "yhwj", "xhwj",
						"bxhtfk", "num111", "byhtfk", "num112", "num22",
						"num23", "num33", "num34" }, ReportBaseVO.class);
		
		ReportBaseVO[] vos9 = list.get(7);//������
		ReportBaseVO[] vos10 = (ReportBaseVO[]) CombinVO.combinData(vos9,
				new String[] { "ct_code","projectid","pk_currtype" }, new String[] { "yhwj", "xhwj",
						"bxhtfk", "num111", "byhtfk", "num112", "num22",
						"num23", "num33", "num34" }, ReportBaseVO.class);
		
		
		ReportBaseVO[] avos5= CombinVO.comin(avos4, vos8);//�ϲ�������
		ReportBaseVO[] avos6= CombinVO.comin(avos5, vos10);//�ϲ�������
				
		//�Ƿ���Ŀ�ϲ�
		if(iscbin.booleanValue()==true){
			avos6 = (ReportBaseVO[]) CombinVO.combinData(avos6,
					new String[] { "projectid","pk_currtype"}, new String[] {"def5","def9", "yhwj", "xhwj",
							"bxhtfk", "num111", "byhtfk", "num112", "num22",
							"num23", "num33", "num34" }, ReportBaseVO.class);
		}
		setQueryDate(avos6);
		setRate(avos6);
		return avos6;
	}


	private void setRate(ReportBaseVO[] avos) throws Exception {
		if (avos == null || avos.length == 0) {
			return;
		}
		//Ϊ�˷�ֹ���²�ѯ���ʣ�������map������Ѿ��ù��Ļ���
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
		
	/**
	 * ��ѯ����
	 * @param date
	 * @throws Exception 
	 */
	private UFDouble queryRate(String date,String pk_currtype) throws Exception {
		Class[] ParameterTypes = new Class[] { String.class, String.class};
		Object[] ParameterValues = new Object[] { date,pk_currtype };
		Object o = LongTimeTask.calllongTimeService("ic", null, "���ڲ�ѯ...", 1,
				bo, null, "queryRate", ParameterTypes, ParameterValues);
		 UFDouble uf = PuPubVO.getUFDouble_NullAsZero(o );
		return uf;
	}
	public void onSave() throws Exception {
		if (sves == null || sves.length == 0) {
			this.showErrorMessage("û������");
			return;
		}
       /**
        * num2  num5 num8 num10
        */
		valudate();
		if (valudate1()) {
			int retu = showOkCancelMessage("�Ѿ����� ������ڼ������  �������  ���Ḳ���ѱ��������");
			if (retu != 1) {
				return;
			}
		}
		ReportVO1[] vos = (ReportVO1[]) SingleVOChangeDataUiTool
				.runChangeVOAryForUI(sves, ReportVO1.class,
						"nc.ui.pf.changedir.CHGReport1toVo");

		Class[] ParameterTypes = new Class[] { ReportVO1[].class, String.class,
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
	public boolean valudate1( ) throws Exception {

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
	 * �Ա�������ݺϷ��Խ���У��
	 * 
	 * @param sves2
	 */
	public void valudate( ) throws Exception {

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
	 * ��ѯ�����ͬ ��ѯ��ʼ����֮ǰ�� ��ԭ�к�ͬ��
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_corp , ");
		sql.append(" b.pk_ct_manage_b ,");// ��ͬ��������
		sql.append(" h.pk_ct_type  , ");// ��ͬ����
		sql.append(" h.pk_ct_manage  , ");// ��ͬ����
		sql.append(" h.ct_code, ");// ��ͬ��
		sql.append(" h.projectid ,");// ��Ŀ
		sql.append(" h.custid ,");// ����id
		sql.append(" h.currid pk_currtype,");// ����
		sql.append(" h.def5 ,");// �ڳ�����ԭ�ҽ��
		sql.append(" h.def9 ,");// �ڳ�����ҽ��
		sql.append(" b.oritaxsummny yhwj");// ԭ�к�ͬ��˰�ϼ�
		sql.append(" from ct_manage h ");
		sql.append(" join ct_manage_b b on h.pk_ct_manage=b.pk_ct_manage ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0) = 0 ");
		sql.append(" and isnull(b.dr,0)=0 ");
		sql.append(" and h.activeflag=0 ");// ��ͬ�����־
		sql.append(" and h.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey() + "'");
		sql.append(" and h.pk_ct_type='" + pk_ctpe + "'");
		sql.append(" and h.currid <>'" + pk_bz + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի���
			ConditionVO[] vos = filterQuery(querylg.getConditionVO());// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			setdate(vos);
			if (vos == null || vos.length == 0) {

			} else {
				boolean isksri = false;
				for (int i = 0; i < vos.length; i++) {
					String name = vos[i].getFieldName();
					if (name.equals("��ʼ����")) {
						wheresql = wheresql + " and b.def2 < '"
								+ vos[i].getValue() + "'";
						isksri = true;
					}
					if (name.equals("��Ŀ")) {
						wheresql = wheresql + " and  h.projectid  = '"
								+ vos[i].getValue() + "'";
						prid = vos[i].getValue();
					}
				}
				if (isksri == false) {
					// ������������ ������������
					wheresql = wheresql + " and 1=0 ";
				}
			}
			sql.append(wheresql);
		}
		return sql.toString();
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
	 * ��ѯ�����ͬ ��ѯ ��ʼ���ڵ��������� ��������ͬ��
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql2() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_corp , ");
		sql.append(" h.ct_code, ");// ��ͬ��
		sql.append(" h.pk_ct_type  , ");// ��ͬ����
		sql.append(" h.pk_ct_manage  , ");// ��ͬ����
		sql.append(" b.pk_ct_manage_b ,");// ��ͬ��������
		sql.append(" h.projectid ,");// ��Ŀ
		sql.append(" h.custid ,");// ����id
		sql.append(" h.currid pk_currtype ,");// ����
		sql.append(" h.def5 ,");// �ڳ�����ԭ�ҽ��
		sql.append(" h.def9 ,");// �ڳ�����ҽ��
		sql.append(" b.oritaxsummny xhwj");// ������ͬ��˰�ϼ�
		sql.append(" from ct_manage h ");
		sql.append(" join ct_manage_b b on h.pk_ct_manage=b.pk_ct_manage ");
		sql.append(" where ");
		sql.append(" isnull(h.dr,0) = 0 ");
		sql.append(" and h.activeflag=0 ");// ��ͬ�����־
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
	 * ��ѯ��� ����ʼ���� �� �������ڲ�ѯ ����ʼʱ��ͽ���ʱ����˺�ͬ �� ������ͬ���ڸ���
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return xyh�Ǻ�ͬ����pk,cksqsh�Ǻ�ͬ����pk
	 * @throws Exception
	 */
	private String getSql3() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// ��˾
		sql.append(" h.ywbm  , ");// ��������
		sql.append(" h.djbh ,");// ���ݺ�
		sql.append(" h.djrq ,");// ��������
		sql.append(" h.pj_jsfs, ");// ���㷽ʽ
		sql.append(" b.jfybje bxhtfk,");// ����ԭ�ҽ��
		sql.append(" b.jfbbje num111, ");// ����ҽ��
		sql.append(" b.cksqsh pk_ct_manage_b ");// �ϲ���Դ������id
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

		sql.append(" and h.djlxbm ='" + jybm + "'");

		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի���
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			ConditionVO[] vos1 = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
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
	 * ������Ŀ ��Ŀ������������ id �����Ŀ�������� id
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
	 * ��ѯ��� ����ʼ���� �� �������ڲ�ѯ ���˿�ʼ����ǰ�ĺ�ͬ ��ԭ�к�ͬ����
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @throws Exception
	 */
	private String getSql4() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// ��˾
		sql.append(" h.ywbm  , ");// ��������
		sql.append(" h.djbh ,");// ���ݺ�
		sql.append(" h.djrq ,");// ��������
		sql.append(" b.jfybje byhtfk,");// ����ԭ�ҽ��
		sql.append(" b.jfbbje num112, ");// ����ҽ��
		sql.append(" b.cksqsh pk_ct_manage_b ");// �ϲ���Դ������id
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
		sql.append(" and h.djlxbm ='" + jybm + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						vos[i].setFieldCode("h.djrq");
						if (vos[i].getFieldName().equals("��ʼ����")) {
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
	 * ��ѯԭ�к�ͬ�ۼ��Ѹ��� (���˽�������֮ǰ�ĸ�� , ���˿�ʼ����ǰ�ĺ�ͬ )
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @throws Exception
	 */
	private String getSql5() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// ��˾
		sql.append(" h.ywbm  , ");// ��������
		sql.append(" h.djbh ,");// ���ݺ�
		sql.append(" h.djrq ,");// ��������
		sql.append(" b.jfybje num22,");// ����ԭ�ҽ��
		sql.append(" b.jfbbje num23, ");// ����ҽ��
		sql.append(" b.cksqsh pk_ct_manage_b ");// �ϲ���Դ������id
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
		sql.append(" and h.djlxbm ='" + jybm + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and c.def2< '" + vos[i].getValue()
									+ "'";
						}
						if (vos[i].getFieldName().equals("��������")) {
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
	 * ��ѯ������ͬ�ۼ��Ѹ��� (���˽�������֮ǰ�ĸ�� , ���˿�ʼ���ڵ���������֮��ĺ�ͬ )
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @throws Exception
	 */
	private String getSql6() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select h.dwbm , ");// ��˾
		sql.append(" h.ywbm  , ");// ��������
		sql.append(" h.djbh ,");// ���ݺ�
		sql.append(" h.djrq ,");// ��������
		sql.append(" b.jfybje num33,");// ����ԭ�ҽ��
		sql.append(" b.jfbbje num34, ");// ����ҽ��
		sql.append(" b.cksqsh pk_ct_manage_b ");// �ϲ���Դ������id
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
		sql.append(" and h.djlxbm ='" + jybm + "'");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and c.def2>='" + vos[i].getValue()
									+ "'";
						}
						if (vos[i].getFieldName().equals("��������")) {
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

	
//	/**
//	 * ��ѯ������
//	 * 
//	 * @���ߣ�mlr
//	 * @˵�������ɽ������Ŀ
//	 * @ʱ�䣺2012-9-5����10:06:38
//	 * @throws Exception
//	 */
//	private String getSql7() throws Exception {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select h.pk_corp , ");// ��˾
//		sql.append(" h.djbh  ct_code,");//��������
//		sql.append(" h.bzbm pk_currtype , ");// ����
//		sql.append(" h.djrq    ,");// ��������
//		sql.append(" b.jobid projectid ,");// ��Ŀ
//		sql.append(" b.ybje xhwj ,");// ԭ�ҽ��
//		sql.append(" b.bbje ");// ���ҽ��
//		sql.append(" from er_jkbx_init  h ");
//		sql.append(" join er_finitem  b  on h.pk_jkbx =b.pk_jkbx ");
//		sql.append(" where ");
//		sql.append(" isnull(h.dr,0)=0 ");
//		sql.append(" and  isnull(b.dr,0)=0 ");
//		sql.append(" and h.dwbm='"
//				+ ClientEnvironment.getInstance().getCorporation()
//						.getPrimaryKey() + "'");
//		sql.append(" and h.bzbm <>'" + pk_bz + "'");
//		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
//			String wheresql = " ";
//			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
//			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
//					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
//			String wsql = "";
//			if (vos == null || vos.length == 0) {
//
//			} else {
//				for (int i = 0; i < vos.length; i++) {
//					String code = vos[i].getFieldCode();
//					if (code.equals("b.def2")) {// ��Ч����
//						if (vos[i].getFieldName().equals("��ʼ����")) {
//							wsql = wsql + " and  h.djrq >='" + vos[i].getValue()
//									+ "'";
//						}
//						if (vos[i].getFieldName().equals("��������")) {
//							wsql = wsql + " and h.djrq< ='" + vos[i].getValue()
//									+ "'";
//			
//
//						}
//					}
//					if (code.equals("h.projectid")) {
//						String value = vos[i].getValue();
//						String pk = getManPk(value);
//						wsql = wsql + " and b.jobid ='" + pk + "'";
//					}
//				}
//			}
//			sql.append(wsql);
//		}
//
//		return sql.toString();
//	}
	/**
	 * ��ѯƾ֤  ��������Ϊ��Ŀ��  ����ƾ֤
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql7() throws Exception {
		StringBuffer buf=new StringBuffer();
//		sql.append(" select h.pk_corp , ");// ��˾
//		sql.append(" h.djbh  ct_code,");//��������
//		sql.append(" h.bzbm pk_currtype , ");// ����
//		sql.append(" h.djrq    ,");// ��������
//		sql.append(" b.jobid projectid ,");// ��Ŀ
//		sql.append(" b.ybje xhwj ,");// ԭ�ҽ��
//		sql.append(" b.bbje ");// ���ҽ��
		buf.append(" select h.pk_corp pk_corp,");
		buf.append(" h.pk_detail pk_detail,");
		buf.append(" e.dispname ct_code,");//��Ŀ����----��ͬ��
		buf.append(" h.prepareddatev djrq,");//��������
		buf.append(" d.pk_jobmngfil projectid, ");//��Ŀ
		buf.append(" h.pk_currtype pk_currtype,");//����
		buf.append(" h.debitamount  xhwj, ");//ԭ�ҽ跽���
		buf.append(" h.localdebitamount bbje,");//���ҽ跽���
		buf.append(" h.debitamount bxhtfk,");// ����ԭ�ҽ��
		buf.append(" h.localdebitamount num111, ");// ����ҽ��
		buf.append(" h.debitamount num33,");// �ۼƸ���ԭ�ҽ��
		buf.append(" h.localdebitamount num34 ");// �ۼƸ���ҽ��
		
	//	buf.append(" h.localcreditamount num2,");//��  �����ʽ�
//		buf.append(" c.def2 num20,");//�ڳ������ʽ�
//		buf.append(" c.def17  num21,");//�ڳ������ʽ�
//		buf.append(" c.def18 num22,");//�ڳ������Ϣ
//		buf.append(" c.def19  num23, ");//�ڳ�������Ϣ
//		buf.append(" b.checkvalue");//���ŵ���������
		buf.append(" from gl_detail h ");
		buf.append(" join gl_freevalue b ");
		buf.append(" on h.assid=b.freevalueid ");
		
		buf.append(" join bd_jobbasfil c");
		buf.append(" on b.checkvalue=c.pk_jobbasfil");
		buf.append(" join bd_jobmngfil d");
		
		buf.append(" on b.checkvalue=d.pk_jobbasfil and d.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		buf.append(" join bd_accsubj e");
		buf.append(" on h.pk_accsubj =e.pk_accsubj ");
		buf.append(" where b.checktype='"+protype+"' ");
		buf.append(" and h.direction='D'");//��� ����Ϊ�跽
		buf.append(" and isnull(h.dr,0)=0 ");
		buf.append(" and isnull(b.dr,0)=0 ");
		buf.append(" and isnull(c.dr,0)=0 ");
		buf.append(" and isnull(d.dr,0)=0 ");
		buf.append(" and isnull(e.dr,0)=0 ");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and  h.prepareddatev >='" + vos[i].getValue()
									+ "'";
						}
						if (vos[i].getFieldName().equals("��������")) {
							wsql = wsql + " and h.prepareddatev< ='" + vos[i].getValue()
									+ "'";
			

						}
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						wsql = wsql + " and b.checkvalue ='" + pk + "'";
					}
				}
			}
			buf.append(wsql);
		}

		buf.append(" and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		buf.append(" and h.pk_currtype  <>'" + pk_bz + "'");
		buf.append(" and h.pk_accsubj in ( select pk_accsubj from bd_accsubj b where b.subjcode like '540102%' " +
				  "  or b.subjcode like '540103%' or b.subjcode like '54010107%' or b.subjcode like '540104%'  )");
		return buf.toString();
	}

	
	/**
	 * ��ѯƾ֤  ��������Ϊ��Ŀ��  ����ƾ֤  ��ѯ��ʼ����֮ǰ��
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql8() throws Exception {
		StringBuffer buf=new StringBuffer();
//		sql.append(" select h.pk_corp , ");// ��˾
//		sql.append(" h.djbh  ct_code,");//��������
//		sql.append(" h.bzbm pk_currtype , ");// ����
//		sql.append(" h.djrq    ,");// ��������
//		sql.append(" b.jobid projectid ,");// ��Ŀ
//		sql.append(" b.ybje xhwj ,");// ԭ�ҽ��
//		sql.append(" b.bbje ");// ���ҽ��
		buf.append(" select h.pk_corp pk_corp,");
		buf.append(" h.pk_detail pk_detail,");
		buf.append(" e.dispname ct_code,");//��Ŀ����----��ͬ��
		buf.append(" h.prepareddatev djrq,");//��������
		buf.append(" d.pk_jobmngfil projectid, ");//��Ŀ
		buf.append(" h.pk_currtype pk_currtype,");//����
		buf.append(" h.debitamount  yhwj, ");//ԭ�ҽ跽���
		buf.append(" h.localdebitamount bbje,");//���ҽ跽���
//		buf.append(" h.debitamount byhtfk,");// ����ԭ�ҽ��
//		buf.append(" h.localdebitamount num112, ");// ����ҽ��
		buf.append(" h.debitamount num22,");// �ۼƸ���ԭ�ҽ��
		buf.append(" h.localdebitamount num23 ");// �ۼƸ���ҽ��
	//	buf.append(" h.localcreditamount num2,");//��  �����ʽ�
//		buf.append(" c.def2 num20,");//�ڳ������ʽ�
//		buf.append(" c.def17  num21,");//�ڳ������ʽ�
//		buf.append(" c.def18 num22,");//�ڳ������Ϣ
//		buf.append(" c.def19  num23, ");//�ڳ�������Ϣ
//		buf.append(" b.checkvalue");//���ŵ���������
		buf.append(" from gl_detail h ");
		buf.append(" join gl_freevalue b ");
		buf.append(" on h.assid=b.freevalueid ");
		
		buf.append(" join bd_jobbasfil c");
		buf.append(" on b.checkvalue=c.pk_jobbasfil");
		buf.append(" join bd_jobmngfil d");
		
		buf.append(" on b.checkvalue=d.pk_jobbasfil and d.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		buf.append(" join bd_accsubj e");
		buf.append(" on h.pk_accsubj =e.pk_accsubj ");
		buf.append(" where b.checktype='"+protype+"' ");
		buf.append(" and h.direction='D'");//��� ����Ϊ�跽
		buf.append(" and isnull(h.dr,0)=0 ");
		buf.append(" and isnull(b.dr,0)=0 ");
		buf.append(" and isnull(c.dr,0)=0 ");
		buf.append(" and isnull(d.dr,0)=0 ");
		buf.append(" and isnull(e.dr,0)=0 ");
		if (getQuerySQL() != null && getQuerySQL().length() != 0) {
			String wheresql = " ";
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("b.def2")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and  h.prepareddatev <'" + vos[i].getValue()
									+ "'";
						}
//						if (vos[i].getFieldName().equals("��������")) {
//							wsql = wsql + " and h.prepareddatev< ='" + vos[i].getValue()
//									+ "'";
//			
//
//						}
					}
					if (code.equals("h.projectid")) {
						String value = vos[i].getValue();
						String pk = getManPk(value);
						wsql = wsql + " and b.checkvalue ='" + pk + "'";
					}
				}
			}
			buf.append(wsql);
		}

		buf.append(" and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");
		buf.append(" and h.pk_currtype  <>'" + pk_bz + "'");
		buf.append(" and h.pk_accsubj in ( select pk_accsubj from bd_accsubj b where  (b.subjcode like '540102%' " +
				  "  or b.subjcode like '540103%' or b.subjcode like '54010107%' or b.subjcode like '540104%'  " +
				  "  ) and  b.subjcode<>'54010199' and  b.subjcode<>'54010299' and  b.subjcode<>'54010399'" +
				  "  and  b.subjcode<>'54010499' and  b.subjcode<>'54010599' )");
	
		
		return buf.toString();
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
		ConditionVO[] vos = filterQuery(querylg.getConditionVO());// ��ȡ�ѱ��û���д�Ĳ�ѯ����
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
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report1.ReportVO1");
	}

	/**
	 * ���ñ������ܽڵ��
	 */
	public String _getModelCode() {
		return "40205010";
	}

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
		sql.append(" h.num34,");
		sql.append(" h.yhwj ");
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
					.serializableClone(filterQuery(querylg.getConditionVO()));// ��ȡ�ѱ��û���д�Ĳ�ѯ����
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

	private String qsql = null;

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
		m_buttonArray = new int[] { qry, cal, save1,  IReportButton.PrintBtn, IReportButton.LevelSubTotalBtn  ,   IReportButton.CrossBtn, CaPuBtnConst.save         
};
		return m_buttonArray;
	}
//    public int[] getReportButtonAry() {
//        m_buttonArray = new int[] { 
//        		IReportButton.QueryBtn,    
//                IReportButton.LevelSubTotalBtn,  
//                
//        		IReportButton.PrintBtn,
//        		CaPuBtnConst.onboRefresh,
//        		CaPuBtnConst.save,
//                };
//        return m_buttonArray;
//    }

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
	 * �����������ð�ť
	 */
	public void onBoSave() {
		if (getBuff() == null) {
			return;
		} else {
			updateBuffer(getBuff());
		}
	}

}