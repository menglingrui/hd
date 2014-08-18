package nc.hd.ui.daterate;

import nc.hd.vo.daterate.RateVO;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.trade.pub.HYBillVO;

public class ClientUICtrl extends AbstractManageController implements
		ISingleController {

	public String[] getCardBodyHideCol() {
		return null;
	}

	public int[] getCardButtonAry() {
		return new int[] { IBillButton.Add, IBillButton.Query,
				IBillButton.Edit, IBillButton.Save, IBillButton.Delete,
				IBillButton.Cancel, IBillButton.Return,  };

	}

	public int[] getListButtonAry() {
		return new int[] { IBillButton.Add, IBillButton.Query,
				IBillButton.Edit, IBillButton.Save, IBillButton.Delete,
				IBillButton.Cancel, IBillButton.Card, };

	}

	public boolean isShowCardRowNo() {
		return true;
	}

	public boolean isShowCardTotal() {
		return true;
	}

	public String getBillType() {
		return "40205005";
	}

	public String[] getBillVoName() {
		return new String[] { HYBillVO.class.getName(),
				RateVO.class.getName(), RateVO.class.getName() };
	}

	public String getBodyCondition() {
		return null;
	}

	public String getBodyZYXKey() {
		return null;
	}

	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	public String getChildPkField() {
		return null;
	}

	public String getHeadZYXKey() {
		return null;
	}

	public String getPkField() {
		return null;
	}

	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	public boolean isExistBillStatus() {
		return false;
	}

	public boolean isLoadCardFormula() {
		return true;
	}

	public String[] getListBodyHideCol() {
		return null;
	}

	public String[] getListHeadHideCol() {
		return null;
	}

	public boolean isShowListRowNo() {
		return true;
	}

	public boolean isShowListTotal() {
		return true;
	}

	public boolean isSingleDetail() {
		return false; // µ¥±íÍ·
	}
}
