package hu.mik.java2.webshop.vaadin;


import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import hu.mik.java2.webshop.user.bean.User;
import hu.mik.java2.webshop.user.service.UserService;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


@SuppressWarnings("serial")
@SpringView(name = SignInView.VIEW_NAME)
public class SignInView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "profile";	
	String name, password;
	public static User actualUser = null;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Belépés");
		addHeader();
		this.addComponent(signIn());
	}
	
	private Component signIn() {
		VerticalLayout formLayout = new VerticalLayout();
		TextField userNameField = new TextField("Felhasználó név:");		
		PasswordField passWd = new PasswordField("Jelszó: ");
		Button logInButton = new Button("Belépés");
			
		logInButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				User user = userService.listUsersByUserName(userNameField.getValue());

				 if(user == null){
					
				 }else{
					 name = user.getUsername();
					 password =  user.getPasswd();
				 }
				if(userNameField.getValue().equals(name) && passWd.getValue().equals(password)) {
					actualUser = user;
					if(userNameField.getValue().equals("admin")) {
						getUI().getNavigator().navigateTo(AdminView.VIEW_NAME);
					} else {
						getUI().getNavigator().navigateTo(ProfilView.VIEW_NAME);
					}
				}else{
					Notification.show("Hibás felhasználó név és/vagy jelszó!");					
				}
			}
		});
		
		Button signUpButton= new Button("Regisztráció");
		signUpButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(RegistrationView.VIEW_NAME);
				
			}
		});
		
		formLayout.addComponent(userNameField);
		formLayout.addComponent(passWd);
		formLayout.addComponent(logInButton);
		formLayout.addComponent(signUpButton);		
		
		formLayout.setComponentAlignment(userNameField, Alignment.TOP_CENTER);
		formLayout.setComponentAlignment(passWd, Alignment.MIDDLE_CENTER);
		formLayout.setComponentAlignment(logInButton, Alignment.BOTTOM_CENTER);
		formLayout.setComponentAlignment(signUpButton, Alignment.BOTTOM_CENTER);
		
		return formLayout;
		
	}

	private void addHeader() {
		this.addComponent(new Label("Belépés"));
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	public static User getUser() {
		return actualUser;
	}

	public static User getActualUser() {
		return actualUser;
	}

	public static void setActualUser(User actualUser) {
		SignInView.actualUser = actualUser;
	}
	

}