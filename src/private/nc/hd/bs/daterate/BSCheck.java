package nc.hd.bs.daterate;
import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.zmpub.pub.check.BsUniqueCheck;
import nc.hd.vo.daterate.RateVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.trade.pub.IBDACTION;
public class BSCheck implements IBDBusiCheck {

	public void check(int intBdAction, AggregatedValueObject vo, Object userObj)
			throws Exception {
		if (intBdAction != IBDACTION.SAVE) {
			return;
		}
		if (vo == null || vo.getParentVO() == null) {
			return;
		}
		RateVO ivo = (RateVO) vo.getParentVO();
		//���� ΨһУ��
		BsUniqueCheck.FieldUniqueCheck(ivo, new String[]{"dbilldate"}, " ������ �����ݿ����Ѿ�����");
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
	}
}
