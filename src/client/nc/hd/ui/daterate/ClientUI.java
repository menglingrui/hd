package nc.hd.ui.daterate;

import javax.swing.event.ListSelectionEvent;

import nc.hd.vo.daterate.ClientGetCheckClass;
import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.BillManageUI;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.pub.CircularlyAccessibleValueObject;

/**
 * 日期利率表
 * 
 * @author mlr
 */
public class ClientUI extends BillManageUI {
	private static final long serialVersionUID = 1L;

	protected ManageEventHandler createEventHandler() {
		return new ClientEventHandler(this, getUIControl());
	}

	public void setBodySpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	protected void setHeadSpecialData(CircularlyAccessibleValueObject vo,
			int intRow) throws Exception {
	}

	protected void setTotalHeadSpecialData(CircularlyAccessibleValueObject[] vos)
			throws Exception {
	}

	protected void initSelfData() {
	}

	public void setDefaultData() throws Exception {
		getBillCardPanel().getHeadItem("pk_corp").setValue(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
	}

	public void valueChanged(ListSelectionEvent arg0) {

	}
	/**
	 * 编辑后事件。 创建日期：(2001-3-23 2:02:27)
	 *
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public void afterEdit(nc.ui.pub.bill.BillEditEvent e) {
		String key=e.getKey();
		if(key.equals("vdef1")){
		   getBillCardPanel().execHeadEditFormulas();
		}
	}
	@Override
	public Object getUserObject() {
		
		return new ClientGetCheckClass();
	}

	@Override
	protected AbstractManageController createController() {
		return new ClientUICtrl();
	}

}
