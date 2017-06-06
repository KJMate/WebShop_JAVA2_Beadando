package hu.mik.java2.webshop.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
@Theme("valo")
@SpringUI
@SpringViewDisplay
public class HomeUI extends UI implements ViewDisplay {

	private Panel homeViewDisplay;
	
	@Override
	protected void init(VaadinRequest request) {
		//root
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(false);
		root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		root.addStyleName("");
		setContent(root);
		//navbar
		final CssLayout navBar = new CssLayout();
		navBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navBar.addComponent(createNavButton("Kezdőlap", DefaultView.VIEW_NAME));
		navBar.addComponent(createNavButton("Belépés", SignInView.VIEW_NAME));
		navBar.addComponent(createNavButton("Termékek", ProductsView.VIEW_NAME));
		navBar.addComponent(createNavButton("Kosár", ShoppingCartView.VIEW_NAME));
		//menubar
		final VerticalLayout menuBar = new VerticalLayout();
		menuBar.setMargin(false);
		menuBar.setHeight(150f, Unit.PIXELS);
		menuBar.addStyleName(ValoTheme.WINDOW_TOP_TOOLBAR);
		menuBar.addStyleName(ValoTheme.MENU_ROOT);
		menuBar.addComponent(navBar);
		menuBar.setComponentAlignment(navBar, Alignment.MIDDLE_CENTER);
		root.addComponent(menuBar);
		//display
		homeViewDisplay = new Panel();
		homeViewDisplay.setSizeFull();
		homeViewDisplay.addStyleName(ValoTheme.PANEL_BORDERLESS);
		root.addComponent(homeViewDisplay);
		root.setExpandRatio(homeViewDisplay, 1.0f);
	}

	private Button createNavButton(String caption, final String viewName) {
		Button btn = new Button(caption);
		btn.addStyleName(ValoTheme.BUTTON_SMALL);
		btn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(viewName);
			}
		});
		btn.addStyleName(ValoTheme.BUTTON_LARGE);
		btn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		return btn;
	}
	
	@Override
	public void showView(View view) {
		homeViewDisplay.setContent((Component) view);
	}
}