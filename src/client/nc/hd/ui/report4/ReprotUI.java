package nc.hd.ui.report4;
import java.util.ArrayList;
import java.util.List;
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
import nc.vo.hd.report4.ReportVO4;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.zmpub.pub.report.ReportBaseVO;
import nc.vo.zmpub.pub.report2.CombinVO;
import nc.vo.zmpub.pub.report2.ZmReportBaseUI3;
import nc.vo.zmpub.pub.tool.ZmPubTool;
/**
 * ���ʽ���������
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// ��ѯ��ʼ����

	private String ddate = "";// ��ѯ��������

	private String prid = "";// ��Ŀid

	private String bo = "nc.bs.hd.report4.ReportDMO";

	public static final int save1 = 150;// �����水ť

	public static final int cal = 152;// ������㰴ť

	public static final int qry = 154;// �����ѯ��ť
	
	public  String depttype="00010000000000000002";//��ƿ�Ŀ �������� ������������
	
	
	public  String protype="UAP00000000000jobass";//��ƿ�Ŀ ��������  ������Ŀ����
	
    public  boolean isStartDate=true;//�Ƿ���ڿ�ʼ����

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

		return new String[] { getSql(),getSql3() };
	}

	private nc.vo.pub.CircularlyAccessibleValueObject[] sves;
	/**
	 * ���õ�ui����֮ǰ ��������ѯ�������
	 * 	//�����ֶ�ֵ ��Ӧ �� �������˳��
		//num20 �ڳ������ʽ�    num21�ڳ������ʽ�     num22�ڳ������Ϣ    num23�ڳ�������Ϣ		
		//pk_detail(ƾ֤��¼����)  dmakedate(��������)  num1(�����ʽ�) num2(�����ʽ�  checkvalue(���ŵ���) checkvaluex(������Ŀ����)			
		//����������� num3=num1-num2  �ۼ������������ num4=num3   ������� num5    �������� num6   	��һ������
		//�ۼ������������num4    �����Ϣ num7  ������Ϣnum8   	�ڶ�������	
		//�ۼ���Ϣnum9   		����������
		//�����num10           ���Ĳ�����
		
		
		//�����㷨��	
		//������̣�  ������ ��Ŀά�ȷ���  ,���鰴�������� ��			
		//���: ����������� num3=num1-num2  �ۼ������������ num4=num3   ������� num5    �������� num6
		//�������� ��Ŀ �������� �ۼƺ���
        //			For(int i=0;i<list.size();i++){
        //			  If(I >0){
        //				     ��list.get(i-1)��ȡ�� �ۼ������������  
	    //
        //				     �ӵ� list.get(i);
        //				}
        //				}
		//����������Ŀ����   �����Ϣ �ʹ�����Ϣ  �ۼ���Ϣ=�����Ϣ-������Ϣ		
		//�����=�ۼ������������+�ۼ���Ϣ
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
		//����������		
		//����׷�� ���ϲ�
		ReportBaseVO[] vos1 = list.get(0);// ���̸�������
		if(vos1==null || vos1.length==0)
			return null;
		ReportBaseVO[] vos2 = list.get(1);//  ���ʱ�
        ReportBaseVO[] zvs=CombinVO.addByContion1(vos1, vos2, new String[]{"dmakedate"}, "");	
		ReportBaseVO[] vos8 = (ReportBaseVO[]) CombinVO.combinData(zvs,
				new String[] { "checkvalue","dmakedate" }, new String[] { "num1", "num2",
						 }, ReportBaseVO.class);
        
        setRate(vos8);
		//������ ��Ŀά�ȷ���  ��Ŀ
		ReportBaseVO[][] fvos= (ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos8,new String[]{"checkvalue"});			
		if(fvos==null || fvos.length==0){
			return null;
		}
		for(int i=0;i<fvos.length;i++){			
			ReportBaseVO[] vos=fvos[i];
			if(vos==null || vos.length==0)
				continue;
			//���鰴��������
			nc.vo.trade.voutils.VOUtil.ascSort(vos, new String[]{"dmakedate"});				
			vos=setQiChu(vos);//�����ڳ�
		//	calqichu()
			fvos[i]=vos;	
		   //���: ����������� num3=num1-num2  �ۼ������������ num4=num3   
			for(int j=0;j<vos.length;j++){
				if(vos[j].getAttributeValue("dmakedate").equals("�ڳ����")){
					continue;
				}
				UFDouble num1=PuPubVO.getUFDouble_NullAsZero(vos[j].getAttributeValue("num1"));
				UFDouble num2=PuPubVO.getUFDouble_NullAsZero(vos[j].getAttributeValue("num2"));
				UFDouble num3=num1.sub(num2);
				vos[j].setAttributeValue("num3", num3);
				vos[j].setAttributeValue("num4", num3);
			}
			//���� �ۼ������������
			for(int k=0; k<vos.length;k++){
				if(vos[k].getAttributeValue("dmakedate").equals("�ڳ����")){
					continue;
				}
				if(k>0){
					//ȡ���ڵ��ۼ�������������ֵ
					UFDouble num4=PuPubVO.getUFDouble_NullAsZero(vos[k-1].getAttributeValue("num4"));
					//����������������ֵ
					UFDouble num41=PuPubVO.getUFDouble_NullAsZero(vos[k].getAttributeValue("num4"));
					//�������ۼ�������������ֵ  �ӵ�����
					UFDouble num42=num4.add(num41);
					
					vos[k].setAttributeValue("num4", num42);
				}
			}
			//��������Ϣ �� ������Ϣ	�ۼ���Ϣ  �����
			for(int n=0;n<vos.length;n++){
				if(vos[n].getAttributeValue("dmakedate").equals("�ڳ����")){
					continue;
				}
				//ȡ���ۼ�������������ֵ
				UFDouble num4=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num4"));
				 //������� num5    �������� num6
				if(!vos[n].getAttributeValue("dmakedate").equals("�ڳ����")){
					if(num4.doubleValue()>0){
						//��������Ϣ
						//ȡ�������
						UFDouble num5=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num5"));
						UFDouble num7=num4.multiply(num5).div(new UFDouble(100));
						vos[n].setAttributeValue("num7", num7);
						
						
					}else if(num4.doubleValue()<0){
						//���������Ϣ
						//ȡ��������
						UFDouble num6=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num6"));
						UFDouble num8=(new UFDouble(0).sub(num4.multiply(num6))).div(new UFDouble(100));
						vos[n].setAttributeValue("num8", num8);
					}
				}
				//�����ۼ���Ϣ
				//ȡ�����Ϣ
				UFDouble num7=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num7"));
				//ȡ������Ϣ
				UFDouble num8=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num8"));
                //�ۼ���Ϣ(������Ϣ)
				UFDouble num9=num7.sub(num8);
				vos[n].setAttributeValue("num9", num9);
				//�ۼ���Ϣ(�����ڳ�)
				if(n>0){
						//ȡ���ڵ��ۼ�������������ֵ
						UFDouble num91=PuPubVO.getUFDouble_NullAsZero(vos[n-1].getAttributeValue("num9"));
						//����������������ֵ
						UFDouble num92=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num9"));
						//�������ۼ�������������ֵ  �ӵ�����
						UFDouble num93=num91.add(num92);
						vos[n].setAttributeValue("num9", num93);
						num9=num93;
				}				
				//���������
				//ȡ�ۼ������������
				UFDouble num41=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num4"));
				UFDouble num10=num9.add(num41);
				vos[n].setAttributeValue("num10", num10);			
			}		
		}		
		//�鲢����
		List<ReportBaseVO> zlis=new ArrayList<ReportBaseVO>();
		for(int i=0;i<fvos.length;i++){
			for(int j=0;j<fvos[i].length;j++){
			   zlis.add(fvos[i][j])	;
			}
		}
		ReportBaseVO[] zvos=zlis.toArray(new ReportBaseVO[0]);
		sves = zvos;
		return zvos;
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
            	if(items[i].getKey().equals("num5") || items[i].getKey().equals("num6")){
            		items[i].setDecimalDigits(4);
            	}else{
                     /////////////////////------------------------------------------zpmע������������Ĭ�Ͼ��� Ϊ 8-------------??????????????????????????????????
                    items[i].setDecimalDigits(2);
            	}
            }
        }

    }
	
    private void setRate(ReportBaseVO[] zvs) throws Exception {
		for(int i=0;i<zvs.length;i++){
			String dmakedate=(String) zvs[i].getAttributeValue("dmakedate");
			UFDouble crate=PuPubVO.getUFDouble_NullAsZero(zvs[i].getAttributeValue("num5"));
			if(crate.doubleValue()==0){
				List<UFDouble> list=queryRate(dmakedate);
				if(list!=null&&list.size()>0){
					zvs[i].setAttributeValue("num5", list.get(0));
					zvs[i].setAttributeValue("num6", list.get(1));
				}
			}			
		}	
	}

	private List<UFDouble> queryRate(String dmakedate) throws Exception {


		Class[] ParameterTypes = new Class[] { String.class};
		Object[] ParameterValues = new Object[] {dmakedate};

		Object o = LongTimeTask.calllongTimeService("ic", null, "���ڲ�ѯ...", 1,
				bo, null, "queryRate", ParameterTypes, ParameterValues);
		return (List<UFDouble>) o;		
	}

	private ReportBaseVO[] setQiChu(ReportBaseVO[] vos) throws Exception {
    	StringBuffer error=new StringBuffer();
    	if(isStartDate){
			//��ѯ�ڳ�
			ReportBaseVO qcvo=queryQiCu(vos[0]);
			//num20 �ڳ������ʽ�    num21�ڳ������ʽ�     num22�ڳ������Ϣ    num23�ڳ�������Ϣ		
			//pk_detail(ƾ֤��¼����)  dmakedate(��������)  num1(�����ʽ�) num2(�����ʽ�  checkvalue(���ŵ���) checkvaluex(������Ŀ����)			

			if(qcvo==null){
				ReportBaseVO newvo=new ReportBaseVO();
				newvo.setAttributeValue("dmakedate", "�ڳ����");
				newvo.setAttributeValue("num1", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num20")));
				newvo.setAttributeValue("num2", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num21")));
				newvo.setAttributeValue("num7", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num22")));
				newvo.setAttributeValue("num8", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num23")));
				newvo.setAttributeValue("checkvalue", PuPubVO.getString_TrimZeroLenAsNull(vos[0].getAttributeValue("checkvalue")));
				calqichu(newvo);				
				qcvo=newvo;
			}
			List lis=new ArrayList();
			qcvo.setAttributeValue("dmakedate", "�ڳ����");
			lis.add(qcvo);
			for(int m=0;m<vos.length;m++){
				lis.add(vos[m]);
			}
			vos=(ReportBaseVO[]) lis.toArray(new ReportBaseVO[0]);				
		}else{
			ReportBaseVO qcvo=vos[0];
			ReportBaseVO newvo=new ReportBaseVO();
			newvo.setAttributeValue("dmakedate", "�ڳ����");
			newvo.setAttributeValue("num1", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num20")));
			newvo.setAttributeValue("num2", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num21")));
			newvo.setAttributeValue("num7", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num22")));
			newvo.setAttributeValue("num8", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num23")));
			calqichu(newvo);	
			
//			buf.append(" c.def18 num22,");//�ڳ������Ϣ
//			buf.append(" c.def19  num23, ");//�ڳ�������Ϣ
			newvo.setAttributeValue("checkvalue", PuPubVO.getString_TrimZeroLenAsNull(qcvo.getAttributeValue("checkvalue")));
			//vos �м����ڳ�
			List lis=new ArrayList();
			lis.add(newvo);
			for(int m=0;m<vos.length;m++){
				lis.add(vos[m]);
			}
			vos=(ReportBaseVO[]) lis.toArray(new ReportBaseVO[0]);
		}		
		return vos;
	}
	private void calqichu(ReportBaseVO newvo) {

			UFDouble num1=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num1"));
			UFDouble num2=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num2"));
			UFDouble num3=num1.sub(num2);
			newvo.setAttributeValue("num3", num3);
			newvo.setAttributeValue("num4", num3);
	
			//�����ۼ���Ϣ
			//ȡ�����Ϣ
			UFDouble num7=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num7"));
			//ȡ������Ϣ
			UFDouble num8=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num8"));
            //�ۼ���Ϣ(������Ϣ)
			UFDouble num9=num7.sub(num8);
			newvo.setAttributeValue("num9", num9);
			//�ۼ���Ϣ(�����ڳ�)				
			//���������
			//ȡ�ۼ������������
			UFDouble num41=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num4"));
			UFDouble num10=num9.add(num41);
			newvo.setAttributeValue("num10", num10);			
		}		
		
	/**
     * ��������  ��Ŀ ��˾ ��ѯ�ڳ�
     * @param reportBaseVO
     * @return
	 * @throws Exception 
     */
	private ReportBaseVO queryQiCu(ReportBaseVO vo) throws Exception {
		
		Class[] ParameterTypes = new Class[] {  ReportBaseVO.class};

		Object[] ParameterValues = new Object[] { vo};

		Object o = LongTimeTask.calllongTimeService("ic", null, "����У��...", 1,
				bo, null, "queryQiChu", ParameterTypes, ParameterValues);
		ReportVO4 vo1= (ReportVO4) o;
		if(vo1==null)
			return null;
	    ReportBaseVO nvo=new ReportBaseVO();
	    for(int i=0;i<vo1.getAttributeNames().length;i++){
	    	nvo.setAttributeValue(vo1.getAttributeNames()[i], vo1.getAttributeValue((vo1.getAttributeNames()[i])));
	    }		
		return nvo;
	}

	public void onSave() throws Exception {
		if (sves == null || sves.length == 0) {
			this.showErrorMessage("û������");
			return;
		}
		sves=getReportBase().getBodyDataVO();
		if (valudate1()) {
			int retu = showOkCancelMessage("��������ʱ��������Ѿ����� �Ƿ񸲸�ԭ������");
			if (retu != 1) {
				return;
			}
		}
		if (valudate2()) {
			int retu = showOkCancelMessage("���ڼ���������� ֮�������");
			if (retu != 1) {
				return;
			}
		}
		ReportVO4[] vos = (ReportVO4[]) SingleVOChangeDataUiTool
				.runChangeVOAryForUI(sves, ReportVO4.class,
						"nc.ui.pf.changedir.CHGReport4toVo");

		Class[] ParameterTypes = new Class[] { ReportVO4[].class, String.class,
				String.class, String.class };

		Object[] ParameterValues = new Object[] { vos, prid, sdate, ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "���ڱ���...", 1,
				bo, null, "onBoSave", ParameterTypes, ParameterValues);

	}

	/**
	 * У�� У�� �Ƿ��Ѿ����ڼ�¼
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
	 * У��У�� �������ں��� �Ƿ��Ѿ�������
	 * 
	 * @param sves2
	 */
	public boolean valudate2() throws Exception {

		Class[] ParameterTypes = new Class[] { String.class, 
				String.class };

		Object[] ParameterValues = new Object[] { prid,  ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "����У��...", 1,
				bo, null, "valute1", ParameterTypes, ParameterValues);
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
	 * ��ѯƾ֤  ��������Ϊ��Ŀ��
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql() throws Exception {
		StringBuffer buf=new StringBuffer();
		buf.append(" select h.pk_corp pk_corp,");
		buf.append(" h.pk_detail pk_detail,");
		buf.append(" h.pk_accsubj pk_accsubj ,");//��ƿ�Ŀ
		buf.append(" h.prepareddatev dmakedate,");//��������
		buf.append(" h.localdebitamount num1,");//��  �����ʽ�
		buf.append(" h.localcreditamount num2,");//��  �����ʽ�
		buf.append(" c.def2 num20,");//�ڳ������ʽ�
		buf.append(" c.def17  num21,");//�ڳ������ʽ�
		buf.append(" c.def18 num22,");//�ڳ������Ϣ
		buf.append(" c.def19  num23, ");//�ڳ�������Ϣ
		buf.append(" b.checkvalue");//���ŵ���������
		buf.append(" from gl_detail h ");
		buf.append(" join gl_freevalue b ");
		buf.append(" on h.assid=b.freevalueid ");
		
		buf.append(" join bd_jobbasfil c");
		buf.append(" on b.checkvalue=c.pk_jobbasfil");
		buf.append(" join bd_accsubj d");
		buf.append(" on  h.pk_accsubj=d.pk_accsubj ");
		buf.append(" where b.checktype='"+protype+"' ");
		buf.append(" and isnull(h.dr,0)=0");
		buf.append(" and isnull(b.dr,0)=0");
		buf.append(" and isnull(c.dr,0)=0");
		buf.append(" and isnull(d.dr,0)=0");
		buf.append(" and (d.subjcode like '1001%' or d.subjcode like '1002%')");
		if(getQuerySQL() != null && getQuerySQL().length() != 0){
			buf.append(" and "+getQuerySQL());
		}
		buf.append(" and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");	
		return buf.toString();
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

//	/**
//	 * ��ѯƾ֤ ��������Ϊ����
//	 * 
//	 * @���ߣ�mlr
//	 * @˵�������ɽ������Ŀ
//	 * @ʱ�䣺2012-9-5����10:06:38
//	 * @return
//	 * @throws Exception
//	 */
//	private String getSql2() throws Exception {
//		StringBuffer buf=new StringBuffer();
//		buf.append(" select ");
//		buf.append(" h.pk_detail pk_detail,");		
////		buf.append(" h.prepareddatev dmakedate,");//��������
////		buf.append(" h.localdebitamount num1,");//��  �����ʽ�
////		buf.append(" h.localcreditamount num2");//��  �����ʽ�
//		buf.append(" b.checkvalue");//������Ŀ����
//		buf.append(" from gl_detail h ");
//		buf.append(" join gl_freevalue b ");
//		buf.append(" on h.assid=b.freevalueid ");
//		buf.append(" where b.checktype='"+depttype+"' ");
//		buf.append(" and isnull(h.dr,0)=0");
//		buf.append(" and isnull(b.dr,0)=0");
//		if(getQuerySQL() != null && getQuerySQL().length() != 0){
//			buf.append(" and "+getQuerySQL());
//		}
//		buf.append(" and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");	
//		return buf.toString();
//	}

	
	/**
	 * ��ѯ ���ʱ�
	 * 
	 * @���ߣ�mlr
	 * @˵�������ɽ������Ŀ
	 * @ʱ�䣺2012-9-5����10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql3() throws Exception {
		isStartDate=false;
		StringBuffer buf=new StringBuffer();
		buf.append(" select ");
		buf.append(" h.pk_rate pk_rate,");		
		buf.append(" h.dbilldate dmakedate,");//��������
		buf.append(" h.cdate num5,");//ȡ�������
		buf.append(" h.ddate num6");//ȡ��������
		buf.append(" from hd_rate h ");
		buf.append(" where isnull(h.dr,0)=0");
		if(getQuerySQL() != null && getQuerySQL().length() != 0){
			QueryDLG querylg = getQueryDlg();// ��ȡ��ѯ�Ի�
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// ��ȡ�ѱ��û���д�Ĳ�ѯ����
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("prepareddatev")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and h.dbilldate >='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
							isStartDate=true;
						}
						if (vos[i].getFieldName().equals("��������")) {
							wsql = wsql + " and h.dbilldate  <='"
									+ vos[i].getValue() + "'";
							ddate = vos[i].getValue();
						}
					}
		
		        }
			
		}	
			buf.append(wsql);
		}
		buf.append(" and h.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"'");	
		return buf.toString();
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
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report4.ReportVO4");

	}

	/**
	 * ���ñ����ܽڵ��
	 */
	public String _getModelCode() {
		return "40205040";
	}

	private String getQuerySql() throws Exception {
//		create table hd_rept4(  
//				 pk_rept4 char(20),
//				     sdate char(20),
//				   ddate  char(20),
//				    pk_corp char(4),
//				   dmakedate char(10),
//				   checkvalue  char(20),
//				   pk_currtype char(20),
//				    num20  decimal(20,8),
//				    num21  decimal(20,8),
//				    num22  decimal(20,8),
//				   num1   decimal(20,8),
//				   num23    decimal(20,8),
//				   num2  decimal(20,8),
//				   num3    decimal(20,8),
//				   num4   decimal(20,8),
//				     num5  decimal(20,8),
//				     num6  decimal(20,8),
//				     num7   decimal(20,8),
//				     num8   decimal(20,8),
//				     num9  decimal(20,8),
//				     num10    decimal(20,8),
//				     ts char(19),
//				     dr smallint,
//				     
//				  ) 

		StringBuffer sql = new StringBuffer();
		sql.append(" select h.pk_rept4 , ");
		sql.append(" h.sdate  , ");
		sql.append(" h.ddate ,");
		sql.append(" h.pk_corp ,");
		sql.append(" h.dmakedate ,");
		sql.append(" h.pk_accsubj ,");
		sql.append(" h.checkvalue ,");
		sql.append(" h.num20 ,");
		sql.append(" h.num21 ,");
		sql.append(" h.num22 ,");
		sql.append(" h.num23 ,");
		sql.append(" h.num1 ,");
		sql.append(" h.num2 ,");
		sql.append(" h.num3 ,");
		sql.append(" h.num4 ,");
		sql.append(" h.num5 ,");
		sql.append(" h.num6 ,");
		sql.append(" h.num7 ,");
		sql.append(" h.num8 ,");
		sql.append(" h.num9 ,");
		sql.append(" h.num10 ");
		sql.append(" from hd_rept4 h  ");
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
					if (code.equals("prepareddatev")) {// ��Ч����
						if (vos[i].getFieldName().equals("��ʼ����")) {
							wsql = wsql + " and h.dmakedate >='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("��������")) {
							wsql = wsql + " and h.dmakedate  <='"
									+ vos[i].getValue() + "'";
							ddate = vos[i].getValue();
						}
					}
					if (code.equals("checkvalue")) {
						prid = vos[i].getValue();
						wsql = wsql + " and h.checkvalue  ='"
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

//		AccountCalendar ac = AccountCalendar.getInstance();
//		UFDate sd = new UFDate(sdate);
//		UFDate dd = new UFDate(ddate);
//		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);
//
//		if (kjqjs == null) {
//			throw new Exception("�ӿ�ʼ����  ����������  û���ҵ�����ڼ� ");
//		}
//
//		if (kjqjs.length > 1) {
//			throw new Exception("�ӿ�ʼ����  ���������� �����ڼ�");
//		}
//
//		AccperiodmonthVO vo = kjqjs[0];
//
//		UFDate ks = vo.getBegindate();
//		UFDate ke = vo.getEnddate();
//
//		if (!sd.equals(ks)) {
//			throw new Exception("��ʼ����  �� ����� ��ʼ���� ��һ�� ");
//		}
//
//		if (!ke.equals(dd)) {
//			throw new Exception("��������  �� ����� �������� ��һ�� ");
//		}

	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { qry, cal, save1, IReportButton.PrintBtn };
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
