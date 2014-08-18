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
		//更换 唯一校验
		BsUniqueCheck.FieldUniqueCheck(ivo, new String[]{"dbilldate"}, " 该日期 在数据库中已经存在");
	}

	public void dealAfter(int intBdAction, AggregatedValueObject billVo,
			Object userObj) throws Exception {
	}
}
