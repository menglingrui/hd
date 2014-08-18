package nc.hd.vo.daterate;

import java.io.Serializable;
import nc.vo.trade.pub.IBDGetCheckClass2;

public class ClientGetCheckClass implements IBDGetCheckClass2, Serializable {
	private static final long serialVersionUID = 1L;

	public String getUICheckClass() {
		return null;
	}

	public String getCheckClass() {
		return "nc.hd.bs.daterate.BSCheck";
	}
}
