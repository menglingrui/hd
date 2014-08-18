package nc.vo.zmpub.pub.tool;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.pu.PuPubVO;
/** 
 * 该工具类首先从缓存中取数,如果缓存中没有才查询数据库  
 * 这样提高了查询效率
 * @author zhf
 */
public class ZmPubTool {
	public static final Integer INTEGER_ZERO_VALUE = new Integer(0); // 整数零
	private static nc.bs.pub.formulaparse.FormulaParse fp = new nc.bs.pub.formulaparse.FormulaParse();
	/**
	 * 用于后台
	 * @param fomular 执行的公式 如查询仓库编码的公式：storcode->getColValue(bd_strodoc,storcode,pk_stordoc,storid)   
	 * @param names   传入值的名字  如：new String[]{"storid"}
	 * @param values  传入值 如:new String[]{"0001AE10000000018ES9"} 
	 * @return
	 * @throws BusinessException
	 */
	public static final Object execFomular(String fomular, String[] names,
			String[] values) throws BusinessException {
		fp.setExpress(fomular);
		if (names.length != values.length) {
			throw new BusinessException("传入参数异常");
		}
		int index = 0;
		for (String name : names) {
			fp.addVariable(name, values[index]);
			index++;
		}
		return fp.getValue();
	}
	public static String getSql(String sql) {
		if (sql == null)
			return null;
		if (sql.contains("'自由'")) {
			sql = sql.replace("'自由'", " 8");
		}
		if (sql.contains("'审批通过'")) {
			sql = sql.replace("'审批通过'", " 1");
		}
		return sql;
	}
	/**
	 * 重新设置公司
	 * @param items
	 * @param pk_corp
	 * @param invmname
	 * @param invbasname
	 * @throws BusinessException
	 */
	public static void setPk_invmandocForClient(CircularlyAccessibleValueObject[] items, String pk_corp,
			String invmname, String invbasname) throws BusinessException {
	   if(items==null || items.length==0)
		   return;
	   for(int i=0;i<items.length;i++){
		 String pk_invbasdoc=PuPubVO.getString_TrimZeroLenAsNull(items[i].getAttributeValue(invbasname));
		   
		 String pk_invmandoc=PuPubVO.getString_TrimZeroLenAsNull(
				ZmPubTool.execFomularClient("pk_invmandoc->getColValue2(bd_invmandoc,pk_invmandoc,pk_corp,pk_corp,pk_invbasdoc,pk_invbasdoc)",
				new String[]{"pk_corp","pk_invbasdoc"}, new String[]{pk_corp,pk_invbasdoc})) ;		 
		 items[i].setAttributeValue(invmname, pk_invmandoc);
	   }
		
	}

	private static nc.ui.pub.formulaparse.FormulaParse fpClient = new nc.ui.pub.formulaparse.FormulaParse();
	/**
	 * 用于前台
	 * @param fomular 执行的公式 如查询仓库编码的公式：storcode->getColValue(bd_strodoc,storcode,pk_stordoc,storid)   
	 * @param names   传入值的名字  如：new String[]{"storid"}
	 * @param values  传入值 如:new String[]{"0001AE10000000018ES9"} 
	 * @return
	 * @throws BusinessException
	 */
	public static final Object execFomularClient(String fomular,
			String[] names, String[] values) throws BusinessException {
		fpClient.setExpress(fomular);
		if (names.length != values.length) {
			throw new BusinessException("传入参数异常");
		}
		int index = 0;
		for (String name : names) {
			fpClient.addVariable(name, values[index]);
			index++;
		}
		return fpClient.getValue();
	}
	public static String getString_NullAsTrimZeroLen(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString().trim();
	}
	// 暂时使用以下方式定义 步长
	public static final int STEP_VALUE = 10;
	public static final int START_VALUE = 10;

	/**
	 * 
	 * @author zhf
	 * @说明：（鹤岗矿业）对vo进行行号设置 2011-1-26下午03:34:51
	 * @param voaCA
	 * @param sBillType
	 * @param sRowNOKey
	 */
	public static void setVOsRowNoByRule(
			CircularlyAccessibleValueObject[] voaCA, String sRowNOKey) {

		if (voaCA == null)
			return;
		int index = START_VALUE;
		for (CircularlyAccessibleValueObject vo : voaCA) {
			vo.setAttributeValue(sRowNOKey, String.valueOf(index));
			index = index + STEP_VALUE;
		}

	}
	/**
	 * 这是库存单据 是否批次管理
	 * @param bill
	 * @throws BusinessException
	 */
	public static void dealIcGenBillVO(GeneralBillVO bill)
	throws BusinessException {
		if (bill == null)
			return;
		GeneralBillItemVO[] items = null;

		bill.getParentVO().setStatus(VOStatus.NEW);
		items = bill.getItemVOs();
		setVOsRowNoByRule(items, "crowno");
		if (items == null || items.length == 0)
			return;
		String inv_fomu = "wholemanaflag->getColValue(bd_invmandoc,wholemanaflag,pk_invmandoc,invman)";
		String[] names = new String[] { "invman" };
		String[] values = new String[1];
		for (GeneralBillItemVO item : items) {
			item.setStatus(VOStatus.NEW);
			// 设置是否批次管理
			values[0] = item.getCinventoryid();
			if (isLotMgtForInv(item.getCinventoryid(), inv_fomu, names, values))
				item.setAttributeValue("isLotMgt", 1);// 批次管理
			// 设置是否辅计量管理
		}
		// }
}
	/**
	 * 查看存货 是否批次管理
	 * zhf
	 * 
	 * @param invmanid
	 * @param fomular
	 * @param names
	 * @param values
	 * @return
	 * @throws BusinessException
	 */
	public static boolean isLotMgtForInv(String invmanid, String fomular,
			String[] names, String[] values) throws BusinessException {
		return PuPubVO.getUFBoolean_NullAs(execFomular(fomular, names, values),
				UFBoolean.FALSE).booleanValue();
	}
	/**
	 * zhf
	 * 获得库存单据动作名称
	 * @param icbilltype
	 * @return
	 */
	public static String getIcBillSaveActionName(String icbilltype) {
		if (icbilltype.equalsIgnoreCase(ScmConst.m_otherIn)
				|| icbilltype.equalsIgnoreCase(ScmConst.m_otherOut))
			return "WRITE";
		else
			return "PUSHSAVE";
	}
	
}
