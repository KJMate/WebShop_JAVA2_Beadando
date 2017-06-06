package hu.mik.java2.webshop.vaadin;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;

@SpringComponent
@UIScope
public class HomeRobot {
	protected static final int WELCOME = 1;
	protected static final int WATERMARK = 2;
	protected static final int DESCRIPTION = 3;
	private static final String LBL_WELCOME = "<span style=\"font-family: Verdana;\">Üdvözlet kedves látogató, leendő vásárló!</span>";
	private static final String LBL_DESC = "<div style=\"text-align: center;margin: 2em 0;\"><h2>Az oldalt készítették:</h2><br/>Balla Dávid,<br/>Kunecz János Máté<br/>és<br/>Rudniczai Ferenc.</div>";
	private static final String LBL_WATERMARK = "Web-Shop \u00a9 2017";
	private static final String LBL_DEFAULT = "default";

	public String getTitle() {
		return "Web Shop - Home";
	}

	public Label createLabel(int type) {
		Label lbl;
		switch (type) {
		case WELCOME:
			lbl = new Label(LBL_WELCOME, ContentMode.HTML);
			break;
		case DESCRIPTION:
			lbl = new Label(LBL_DESC, ContentMode.HTML);
			break;
		case WATERMARK:
			lbl = new Label(LBL_WATERMARK);
			break;
		default:
			lbl = new Label(LBL_DEFAULT);
			break;
		}
		return lbl;
	}
}