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
 * 日资金流向计算表
 * @author mlr
 */
public class ReprotUI extends ZmReportBaseUI3 {

	private static final long serialVersionUID = 8264102894793741723L;

	private String sdate = "";// 查询开始日期

	private String ddate = "";// 查询结束日期

	private String prid = "";// 项目id

	private String bo = "nc.bs.hd.report4.ReportDMO";

	public static final int save1 = 150;// 报表保存按钮

	public static final int cal = 152;// 报表计算按钮

	public static final int qry = 154;// 报表查询按钮
	
	public  String depttype="00010000000000000002";//会计科目 辅助核算 部门类型主键
	
	
	public  String protype="UAP00000000000jobass";//会计科目 辅助核算  工程项目主键
	
    public  boolean isStartDate=true;//是否存在开始日期

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

		return new String[] { getSql(),getSql3() };
	}

	private nc.vo.pub.CircularlyAccessibleValueObject[] sves;
	/**
	 * 设置到ui界面之前 处理分组查询后的数据
	 * 	//报表字段值 对应 和 报表计算顺序
		//num20 期初流入资金    num21期初流出资金     num22期初存款利息    num23期初贷款利息		
		//pk_detail(凭证分录主键)  dmakedate(单据日期)  num1(流入资金) num2(流出资金）  checkvalue(部门档案) checkvaluex(工程项目档案)			
		//流入流出差额 num3=num1-num2  累计流入流出差额 num4=num3   存款利率 num5    贷款利率 num6   	第一步计算
		//累计流入流出差额num4    存款利息 num7  贷款利息num8   	第二步计算	
		//累计利息num9   		第三步计算
		//日余额num10           第四步计算
		
		
		//计算算法：	
		//计算过程：  按部门 项目维度分组  ,各组按日期升序 ，			
		//算出: 流入流出差额 num3=num1-num2  累计流入流出差额 num4=num3   存款利率 num5    贷款利率 num6
		//各个部门 项目 进行如下 累计核算
        //			For(int i=0;i<list.size();i++){
        //			  If(I >0){
        //				     从list.get(i-1)中取出 累计流入流出差额  
	    //
        //				     加到 list.get(i);
        //				}
        //				}
		//各个部门项目核算   存款利息 和贷款利息  累计利息=存款利息-贷款利息		
		//日余额=累计流入流出差额+累计利息
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
		//具体计算过程		
		//数据追加 ，合并
		ReportBaseVO[] vos1 = list.get(0);// 工程辅助和算
		if(vos1==null || vos1.length==0)
			return null;
		ReportBaseVO[] vos2 = list.get(1);//  利率表
        ReportBaseVO[] zvs=CombinVO.addByContion1(vos1, vos2, new String[]{"dmakedate"}, "");	
		ReportBaseVO[] vos8 = (ReportBaseVO[]) CombinVO.combinData(zvs,
				new String[] { "checkvalue","dmakedate" }, new String[] { "num1", "num2",
						 }, ReportBaseVO.class);
        
        setRate(vos8);
		//按部门 项目维度分组  科目
		ReportBaseVO[][] fvos= (ReportBaseVO[][]) SplitBillVOs.getSplitVOs(vos8,new String[]{"checkvalue"});			
		if(fvos==null || fvos.length==0){
			return null;
		}
		for(int i=0;i<fvos.length;i++){			
			ReportBaseVO[] vos=fvos[i];
			if(vos==null || vos.length==0)
				continue;
			//各组按日期升序
			nc.vo.trade.voutils.VOUtil.ascSort(vos, new String[]{"dmakedate"});				
			vos=setQiChu(vos);//设置期初
		//	calqichu()
			fvos[i]=vos;	
		   //算出: 流入流出差额 num3=num1-num2  累计流入流出差额 num4=num3   
			for(int j=0;j<vos.length;j++){
				if(vos[j].getAttributeValue("dmakedate").equals("期初余额")){
					continue;
				}
				UFDouble num1=PuPubVO.getUFDouble_NullAsZero(vos[j].getAttributeValue("num1"));
				UFDouble num2=PuPubVO.getUFDouble_NullAsZero(vos[j].getAttributeValue("num2"));
				UFDouble num3=num1.sub(num2);
				vos[j].setAttributeValue("num3", num3);
				vos[j].setAttributeValue("num4", num3);
			}
			//计算 累计流入流出差额
			for(int k=0; k<vos.length;k++){
				if(vos[k].getAttributeValue("dmakedate").equals("期初余额")){
					continue;
				}
				if(k>0){
					//取上期的累计流入流出差额的值
					UFDouble num4=PuPubVO.getUFDouble_NullAsZero(vos[k-1].getAttributeValue("num4"));
					//本期流入流出差额的值
					UFDouble num41=PuPubVO.getUFDouble_NullAsZero(vos[k].getAttributeValue("num4"));
					//将上期累计流入流出差额的值  加到本期
					UFDouble num42=num4.add(num41);
					
					vos[k].setAttributeValue("num4", num42);
				}
			}
			//计算存款利息 和 贷款利息	累计利息  日余额
			for(int n=0;n<vos.length;n++){
				if(vos[n].getAttributeValue("dmakedate").equals("期初余额")){
					continue;
				}
				//取到累计流入流出差额的值
				UFDouble num4=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num4"));
				 //存款利率 num5    贷款利率 num6
				if(!vos[n].getAttributeValue("dmakedate").equals("期初余额")){
					if(num4.doubleValue()>0){
						//计算存款利息
						//取存款利率
						UFDouble num5=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num5"));
						UFDouble num7=num4.multiply(num5).div(new UFDouble(100));
						vos[n].setAttributeValue("num7", num7);
						
						
					}else if(num4.doubleValue()<0){
						//计算贷款利息
						//取贷款利率
						UFDouble num6=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num6"));
						UFDouble num8=(new UFDouble(0).sub(num4.multiply(num6))).div(new UFDouble(100));
						vos[n].setAttributeValue("num8", num8);
					}
				}
				//计算累计利息
				//取存款利息
				UFDouble num7=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num7"));
				//取贷款利息
				UFDouble num8=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num8"));
                //累计利息(现在利息)
				UFDouble num9=num7.sub(num8);
				vos[n].setAttributeValue("num9", num9);
				//累计利息(加上期初)
				if(n>0){
						//取上期的累计流入流出差额的值
						UFDouble num91=PuPubVO.getUFDouble_NullAsZero(vos[n-1].getAttributeValue("num9"));
						//本期流入流出差额的值
						UFDouble num92=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num9"));
						//将上期累计流入流出差额的值  加到本期
						UFDouble num93=num91.add(num92);
						vos[n].setAttributeValue("num9", num93);
						num9=num93;
				}				
				//计算日余额
				//取累计流入流出差额
				UFDouble num41=PuPubVO.getUFDouble_NullAsZero(vos[n].getAttributeValue("num4"));
				UFDouble num10=num9.add(num41);
				vos[n].setAttributeValue("num10", num10);			
			}		
		}		
		//归并数据
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
            	if(items[i].getKey().equals("num5") || items[i].getKey().equals("num6")){
            		items[i].setDecimalDigits(4);
            	}else{
                     /////////////////////------------------------------------------zpm注销，可以设置默认精度 为 8-------------??????????????????????????????????
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

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在查询...", 1,
				bo, null, "queryRate", ParameterTypes, ParameterValues);
		return (List<UFDouble>) o;		
	}

	private ReportBaseVO[] setQiChu(ReportBaseVO[] vos) throws Exception {
    	StringBuffer error=new StringBuffer();
    	if(isStartDate){
			//查询期初
			ReportBaseVO qcvo=queryQiCu(vos[0]);
			//num20 期初流入资金    num21期初流出资金     num22期初存款利息    num23期初贷款利息		
			//pk_detail(凭证分录主键)  dmakedate(单据日期)  num1(流入资金) num2(流出资金）  checkvalue(部门档案) checkvaluex(工程项目档案)			

			if(qcvo==null){
				ReportBaseVO newvo=new ReportBaseVO();
				newvo.setAttributeValue("dmakedate", "期初余额");
				newvo.setAttributeValue("num1", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num20")));
				newvo.setAttributeValue("num2", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num21")));
				newvo.setAttributeValue("num7", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num22")));
				newvo.setAttributeValue("num8", PuPubVO.getUFDouble_NullAsZero(vos[0].getAttributeValue("num23")));
				newvo.setAttributeValue("checkvalue", PuPubVO.getString_TrimZeroLenAsNull(vos[0].getAttributeValue("checkvalue")));
				calqichu(newvo);				
				qcvo=newvo;
			}
			List lis=new ArrayList();
			qcvo.setAttributeValue("dmakedate", "期初余额");
			lis.add(qcvo);
			for(int m=0;m<vos.length;m++){
				lis.add(vos[m]);
			}
			vos=(ReportBaseVO[]) lis.toArray(new ReportBaseVO[0]);				
		}else{
			ReportBaseVO qcvo=vos[0];
			ReportBaseVO newvo=new ReportBaseVO();
			newvo.setAttributeValue("dmakedate", "期初余额");
			newvo.setAttributeValue("num1", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num20")));
			newvo.setAttributeValue("num2", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num21")));
			newvo.setAttributeValue("num7", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num22")));
			newvo.setAttributeValue("num8", PuPubVO.getUFDouble_NullAsZero(qcvo.getAttributeValue("num23")));
			calqichu(newvo);	
			
//			buf.append(" c.def18 num22,");//期初存款利息
//			buf.append(" c.def19  num23, ");//期初贷款利息
			newvo.setAttributeValue("checkvalue", PuPubVO.getString_TrimZeroLenAsNull(qcvo.getAttributeValue("checkvalue")));
			//vos 中加上期初
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
	
			//计算累计利息
			//取存款利息
			UFDouble num7=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num7"));
			//取贷款利息
			UFDouble num8=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num8"));
            //累计利息(现在利息)
			UFDouble num9=num7.sub(num8);
			newvo.setAttributeValue("num9", num9);
			//累计利息(加上期初)				
			//计算日余额
			//取累计流入流出差额
			UFDouble num41=PuPubVO.getUFDouble_NullAsZero(newvo.getAttributeValue("num4"));
			UFDouble num10=num9.add(num41);
			newvo.setAttributeValue("num10", num10);			
		}		
		
	/**
     * 根据日期  项目 公司 查询期初
     * @param reportBaseVO
     * @return
	 * @throws Exception 
     */
	private ReportBaseVO queryQiCu(ReportBaseVO vo) throws Exception {
		
		Class[] ParameterTypes = new Class[] {  ReportBaseVO.class};

		Object[] ParameterValues = new Object[] { vo};

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在校验...", 1,
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
			this.showErrorMessage("没有数据");
			return;
		}
		sves=getReportBase().getBodyDataVO();
		if (valudate1()) {
			int retu = showOkCancelMessage("计算的这段时间的数据已经存在 是否覆盖原有数据");
			if (retu != 1) {
				return;
			}
		}
		if (valudate2()) {
			int retu = showOkCancelMessage("存在计算结束日期 之后的数据");
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

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在保存...", 1,
				bo, null, "onBoSave", ParameterTypes, ParameterValues);

	}

	/**
	 * 校验 校验 是否已经存在记录
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
	 * 校验校验 结束日期后面 是否已经有数据
	 * 
	 * @param sves2
	 */
	public boolean valudate2() throws Exception {

		Class[] ParameterTypes = new Class[] { String.class, 
				String.class };

		Object[] ParameterValues = new Object[] { prid,  ddate };

		Object o = LongTimeTask.calllongTimeService("ic", null, "正在校验...", 1,
				bo, null, "valute1", ParameterTypes, ParameterValues);
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
	 * 查询凭证  辅助核算为项目的
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql() throws Exception {
		StringBuffer buf=new StringBuffer();
		buf.append(" select h.pk_corp pk_corp,");
		buf.append(" h.pk_detail pk_detail,");
		buf.append(" h.pk_accsubj pk_accsubj ,");//会计科目
		buf.append(" h.prepareddatev dmakedate,");//单据日期
		buf.append(" h.localdebitamount num1,");//借  流入资金
		buf.append(" h.localcreditamount num2,");//贷  流出资金
		buf.append(" c.def2 num20,");//期初流入资金
		buf.append(" c.def17  num21,");//期初流出资金
		buf.append(" c.def18 num22,");//期初存款利息
		buf.append(" c.def19  num23, ");//期初贷款利息
		buf.append(" b.checkvalue");//部门档案的主键
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

//	/**
//	 * 查询凭证 辅助核算为部门
//	 * 
//	 * @作者：mlr
//	 * @说明：完达山物流项目
//	 * @时间：2012-9-5上午10:06:38
//	 * @return
//	 * @throws Exception
//	 */
//	private String getSql2() throws Exception {
//		StringBuffer buf=new StringBuffer();
//		buf.append(" select ");
//		buf.append(" h.pk_detail pk_detail,");		
////		buf.append(" h.prepareddatev dmakedate,");//单据日期
////		buf.append(" h.localdebitamount num1,");//借  流入资金
////		buf.append(" h.localcreditamount num2");//贷  流出资金
//		buf.append(" b.checkvalue");//工程项目主键
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
	 * 查询 利率表
	 * 
	 * @作者：mlr
	 * @说明：完达山物流项目
	 * @时间：2012-9-5上午10:06:38
	 * @return
	 * @throws Exception
	 */
	private String getSql3() throws Exception {
		isStartDate=false;
		StringBuffer buf=new StringBuffer();
		buf.append(" select ");
		buf.append(" h.pk_rate pk_rate,");		
		buf.append(" h.dbilldate dmakedate,");//单据日期
		buf.append(" h.cdate num5,");//取存款利率
		buf.append(" h.ddate num6");//取贷款利率
		buf.append(" from hd_rate h ");
		buf.append(" where isnull(h.dr,0)=0");
		if(getQuerySQL() != null && getQuerySQL().length() != 0){
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("prepareddatev")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and h.dbilldate >='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
							isStartDate=true;
						}
						if (vos[i].getFieldName().equals("结束日期")) {
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
		sves=getReportBase().getBillModel().getBodyValueVOs("nc.vo.hd.report4.ReportVO4");

	}

	/**
	 * 设置报表功能节点号
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
			QueryDLG querylg = getQueryDlg();// 获取查询对话
			ConditionVO[] vos = (ConditionVO[]) ObjectUtils
					.serializableClone(querylg.getConditionVO());// 获取已被用户填写的查询条件
			String wsql = "";
			if (vos == null || vos.length == 0) {

			} else {
				for (int i = 0; i < vos.length; i++) {
					String code = vos[i].getFieldCode();
					if (code.equals("prepareddatev")) {// 生效日期
						if (vos[i].getFieldName().equals("开始日期")) {
							wsql = wsql + " and h.dmakedate >='" + vos[i].getValue()
									+ "'";
							sdate = vos[i].getValue();
						}
						if (vos[i].getFieldName().equals("结束日期")) {
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

//		AccountCalendar ac = AccountCalendar.getInstance();
//		UFDate sd = new UFDate(sdate);
//		UFDate dd = new UFDate(ddate);
//		AccperiodmonthVO[] kjqjs = ac.getMonthVOsByDates(sd, dd);
//
//		if (kjqjs == null) {
//			throw new Exception("从开始日期  到结束日期  没有找到会计期间 ");
//		}
//
//		if (kjqjs.length > 1) {
//			throw new Exception("从开始日期  到结束日期 跨会计期间");
//		}
//
//		AccperiodmonthVO vo = kjqjs[0];
//
//		UFDate ks = vo.getBegindate();
//		UFDate ke = vo.getEnddate();
//
//		if (!sd.equals(ks)) {
//			throw new Exception("开始日期  与 会计月 开始日期 不一致 ");
//		}
//
//		if (!ke.equals(dd)) {
//			throw new Exception("结束日期  与 会计月 结束日期 不一致 ");
//		}

	}

	public int[] getReportButtonAry() {
		m_buttonArray = new int[] { qry, cal, save1, IReportButton.PrintBtn };
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
