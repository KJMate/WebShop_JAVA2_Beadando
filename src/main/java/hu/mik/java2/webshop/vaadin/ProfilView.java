package hu.mik.java2.webshop.vaadin;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = ProfilView.VIEW_NAME)
public class ProfilView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "Profil";
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Profil");
		this.addComponent(new Label("Csecs"));
		//this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
