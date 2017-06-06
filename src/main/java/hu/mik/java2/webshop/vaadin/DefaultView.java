package hu.mik.java2.webshop.vaadin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("valo")
@UIScope
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";

	@Autowired
	private HomeRobot robot;

	@PostConstruct
	void init() {
		Page.getCurrent().setTitle(robot.getTitle());
		VerticalLayout page = new VerticalLayout();
		page.setMargin(false);
		
		Label lblWelcome = robot.createLabel(HomeRobot.WELCOME);
		Label lblDescription = robot.createLabel(HomeRobot.DESCRIPTION);
		Label lblWatermark = robot.createLabel(HomeRobot.WATERMARK);
		
		lblWelcome.addStyleName(ValoTheme.LABEL_COLORED);
		lblWelcome.addStyleName(ValoTheme.LABEL_H1);
		
		lblDescription.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		lblDescription.addStyleName(ValoTheme.LABEL_H2);
		lblWatermark.addStyleName(ValoTheme.LABEL_SMALL);
		lblWatermark.addStyleName(ValoTheme.LABEL_COLORED);
		
		page.addComponent(lblWelcome);
		
		page.addComponent(lblWatermark);
		page.addComponent(lblDescription);
		
		page.setComponentAlignment(lblWelcome, Alignment.TOP_CENTER);
		page.setComponentAlignment(lblWatermark, Alignment.MIDDLE_CENTER);
		page.setComponentAlignment(lblDescription, Alignment.BOTTOM_RIGHT);
		
		this.addComponent(page);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}
}