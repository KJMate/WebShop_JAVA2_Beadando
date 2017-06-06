package hu.mik.java2.webshop.vaadin;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = ShoppingCartView.VIEW_NAME)
public class ShoppingCartView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "cart";
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Shopping Cart");
		addComponent(new Label("Shopping Cart"));
		this.addComponent(design1());
	}
	
	private Component design1() {
		VerticalLayout layout1 = new VerticalLayout();
		
		
		return layout1;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// nothing to do here!!!
	}
}