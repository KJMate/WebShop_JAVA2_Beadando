package hu.mik.java2.webshop.vaadin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;

@SuppressWarnings("serial")
@SpringView(name = RegistrationView.VIEW_NAME)
public class RegistrationView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "registartion";
	List<User> users = new ArrayList<>();

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Regisztráció");
		this.addComponent(reg());
	}

	private Component reg() {

		VerticalLayout vertLayout = new VerticalLayout();
		vertLayout.setSizeFull();
		// vertLayout.setComponentAlignment(childComponent, alignment);
		// VerticalLayout vertLayoutLeft = new VerticalLayout();
		// VerticalLayout vertLayoutRight = new VerticalLayout();

		// AbsoluteLayout absLayout = new AbsoluteLayout();
		// absLayout.setSizeFull();

		Panel regPanelLeft = new Panel("Regisztráció:");
		Panel regPanelRight = new Panel("");
		regPanelLeft.addStyleName("panelka");
		regPanelRight.addStyleName(ValoTheme.PANEL_BORDERLESS);
		regPanelLeft.setWidth("35%");
		regPanelLeft.setHeight("400");

		regPanelRight.setSizeUndefined();

		vertLayout.addComponent(regPanelLeft);
		vertLayout.setComponentAlignment(regPanelLeft, Alignment.MIDDLE_CENTER);

		vertLayout.addComponent(regPanelRight);
		vertLayout.setComponentAlignment(regPanelRight, Alignment.TOP_CENTER);

		FormLayout _content_l = new FormLayout();
		FormLayout _content_2 = new FormLayout();

		TextField userName = new TextField("Felhasználó Név:");
		PasswordField pw_0 = new PasswordField("Jelszó:");
		PasswordField pw_1 = new PasswordField("Jelszó ismét:");
		TextField email = new TextField("E-mail cím:");
		TextField keresztNev = new TextField("Keresztnév:");
		TextField vezetekNev = new TextField("Vezetéknév:");
		TextField city = new TextField("Város:");
		TextField street = new TextField("Utca:");
		TextField houseNumber = new TextField("Házszám:");
		TextField postalCode = new TextField("Irányítószám:");
		TextField phone = new TextField("Telefonszám:");
		Button regisztBtn = new Button("Regisztráció");
		
		 users = userService.listUsers();

		// LISZTENER:
		regisztBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				if (userName.getValue().equals("") && pw_1.getValue().equals("") && email.getValue().equals("")
						&& vezetekNev.getValue().equals("") && keresztNev.getValue().equals("")
						&& city.getValue().equals("") && street.getValue().equals("")
						&& houseNumber.getValue().equals("") && postalCode.getValue().equals("")
						&& phone.getValue().equals("")) {

					Notification.show("Nincs kitöltve minden adat");

				} else {
					User user = new User();
					
				/*	for (int i = 0; i <= users.size(); i++) {
						if(users.get(i).getUsername().equals(userName.getValue())){
							
							Notification.show("Már van ilyen nevű felhasználó: " + userName.getValue() );
							userName.setValue("");
							
							
						}else{
							user.setUsername(userName.getValue());
						}
					}*/
					
					for(User user1 : users){
						if(user1.getUsername().equals(userName.getValue())){
							Notification.show("Már van ilyen nevű felhasználó: " + userName.getValue() );
							userName.setValue("");
						}else{
							user.setUsername(userName.getValue());
						}
					}
					
					user.setEmail(email.getValue());
					user.setFirst_name(vezetekNev.getValue());
					user.setLast_name(keresztNev.getValue());
					user.setCity(city.getValue());
					user.setStreet(street.getValue());
					user.setHouseNumber((Integer.valueOf(houseNumber.getValue())));
					user.setPostalCode((Integer.valueOf(postalCode.getValue())));
					user.setPhoneNumber((Integer.valueOf(phone.getValue())));
					user.setSavedProducts("nincs");

					if (pw_1.getValue().equals(pw_0.getValue())) {
						user.setPasswd(pw_1.getValue());
					} else {
						Notification.show("A két jelszó nem egyezik meg");
					}

					userService.save(user);
					Notification.show("Sikeres regisztráció");

					userName.setValue("");
					pw_1.setValue("");
					pw_0.setValue("");
					email.setValue("");
					vezetekNev.setValue("");
					keresztNev.setValue("");
					city.setValue("");
					street.setValue("");
					houseNumber.setValue("");
					postalCode.setValue("");
					phone.setValue("");
				}

			}
		});

		vertLayout.addComponent(regisztBtn);
		// hozzáadás a content-hez:
		_content_l.addComponent(userName);
		_content_l.addComponent(pw_0);
		_content_l.addComponent(pw_1);
		_content_l.addComponent(email);
		_content_l.addComponent(keresztNev);
		_content_l.addComponent(vezetekNev);
		_content_l.addComponent(city);
		_content_l.addComponent(street);
		_content_l.addComponent(houseNumber);
		_content_l.addComponent(postalCode);
		_content_l.addComponent(phone);
		_content_l.setSizeUndefined();
		_content_l.setMargin(true);

		_content_2.addComponent(regisztBtn);

		regPanelLeft.setContent(_content_l);
		regPanelRight.setContent(_content_2);

		/*
		 * _content_2.addComponent(city); _content_2.addComponent(street);
		 * _content_2.addComponent(houseNumber);
		 * _content_2.addComponent(postalCode); _content_2.addComponent(phone);
		 * _content_2.setSizeUndefined(); _content_2.setMargin(true);
		 * 
		 * panelRight.setContent(_content_2);
		 */
		/*
		 * vertLayout.addComponent(pw_0); vertLayout.addComponent(pw_1);
		 * vertLayout.addComponent(email); vertLayout.addComponent(keresztNev);
		 * vertLayout.addComponent(vezetekNev); vertLayout.addComponent(city);
		 * vertLayout.addComponent(street);
		 * vertLayout.addComponent(houseNumber);
		 * vertLayout.addComponent(postalCode); vertLayout.addComponent(phone);
		 */
		// pozicionálás:

		/*
		 * vertLayout.setComponentAlignment(userName, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(pw_0, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(pw_1, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(email, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(keresztNev, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(vezetekNev, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(city, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(street, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(houseNumber, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(postalCode, Alignment.TOP_CENTER);
		 * vertLayout.setComponentAlignment(phone, Alignment.TOP_CENTER);
		 */

		return vertLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
