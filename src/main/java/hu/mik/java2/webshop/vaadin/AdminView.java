package hu.mik.java2.webshop.vaadin;


import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.vaadin.data.provider.Query;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import hu.mik.java2.webshop.category.bean.Category;
import hu.mik.java2.webshop.category.service.CategoryService;
import hu.mik.java2.webshop.product.bean.Product;
import hu.mik.java2.webshop.product.service.ProductService;
import hu.mik.java2.webshop.user.bean.User;

@SuppressWarnings("serial")
@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "admin";

	private Grid<Product> productGrid;
	
	User actualUser = SignInView.getUser();

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("categoryServiceImpl")
	private CategoryService categoryService;

	@PostConstruct
	void init() {
		Page.getCurrent().setTitle("Web Shop - Admin panel");
		VerticalLayout adminPage = createAdministratorPage();
		this.setMargin(false);
		this.addComponent(adminPage);
	}

	private void setGridAutoHeight() {
		int size = productGrid.getDataProvider().size(new Query<>());
		double rows = ( size > 0 ) ? size : 1;
		productGrid.setSizeFull();
		productGrid.setHeightMode(HeightMode.ROW);
		productGrid.setHeightByRows(rows);
	}
	
	private VerticalLayout createAdministratorPage() {
		VerticalLayout root = new VerticalLayout();
		root.setMargin(false);

		productGrid = createGrid();
		setGridAutoHeight();
		
		CssLayout actions = createActions();
		actions.setSizeFull();
		
		root.addComponent(actions);
		root.addComponent(productGrid);

		root.setComponentAlignment(actions, Alignment.TOP_CENTER);
		root.setComponentAlignment(productGrid, Alignment.MIDDLE_CENTER);
		return root;
	}

	private CssLayout createActions() {
		CssLayout actions = new CssLayout();
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		Button btnDelete = new Button("Kijelöltek törlése");
		Button btnModify = new Button("Kijelöltek módosítása");
		Button btnNew = new Button("Új elem beszúrása");
		Button btnNewCategory = new Button("Új kategória");

		btnDelete.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Set<Product> products = productGrid.getSelectedItems();
				for (Product product : products) {
					productService.deleteProduct(product);
				}
				productGrid.setItems(productService.listProducts());
				setGridAutoHeight();
			}
		});
		
		btnModify.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Set<Product> products = productGrid.getSelectedItems();
				for (Product product : products) {
				if (product.getDiscount() == null) {
						product.setDiscount(0);
						Window modify = createModifyWindow(product);
						getUI().addWindow(modify);
					} else {
						Window modify = createModifyWindow(product);
						getUI().addWindow(modify);
					}
				}
			}
		});
		
		btnNewCategory.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Window newCategoryWindow = createNewCategoryWindow();
				getUI().addWindow(newCategoryWindow);
			}

			private Window createNewCategoryWindow() {
				Window adminWindow = new Window("Új termék kategória");
				VerticalLayout newCategoryLayout = new VerticalLayout();
				adminWindow.setContent(newCategoryLayout);
				
				newCategoryLayout.setWidth(250f, Unit.PIXELS);
				newCategoryLayout.setHeight(300f, Unit.PIXELS);
				
			    
				
				Button cancel = new Button("Mégse");				
				Button save = new Button("Új kategória mentése");
				
				//TextField txtNewCatId = new TextField("Kategória id");
				TextField txtNewCatName = new TextField("Kategória neve");
				
				List<Category> usedCategoryIds = categoryService.listCategories();
				
				//newCategoryLayout.addComponent(txtNewCatId);
				newCategoryLayout.addComponent(txtNewCatName);
				newCategoryLayout.addComponent(save);
				newCategoryLayout.addComponent(cancel);			
				
				cancel.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						adminWindow.close();
					}
				});
				
				
				save.addClickListener(new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Category newCategory = new Category();
						Integer catID;
						catID =  usedCategoryIds.size() + 1;
						newCategory.setCategory_id(catID);
						newCategory.setCategory_name(txtNewCatName.getValue());
						categoryService.save(newCategory);
						Notification.show("Sikeres mentés");
						
						adminWindow.close();
					}
				});
								
				adminWindow.center();
				return adminWindow;
			}
		});
		
		btnNew.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Window newProductWindow = createNewWindow();
				getUI().addWindow(newProductWindow);
			}
		});
		
		actions.addComponent(btnNew);
		actions.addComponent(btnModify);
		actions.addComponent(btnDelete);
		actions.addComponent(btnNewCategory);

		return actions;
	}

	private Grid<Product> createGrid() {
		Grid<Product> pgv = new Grid<>();
		List<Product> products = productService.listProducts();
		//pgv.addColumn(Product::getId).setCaption("Azonosító");
		pgv.addColumn(Product::getCategory).setCaption("Kategória");
		pgv.addColumn(Product::getProductName).setCaption("Név");
		pgv.addColumn(Product::getDescription).setCaption("Leírás");
		pgv.addColumn(Product::getPrice).setCaption("Ár");
		pgv.addColumn(Product::getDiscount).setCaption("Kedvezmény");
		pgv.setSelectionMode(SelectionMode.MULTI);
		pgv.setItems(products);
		return pgv;
	}

	private Window createModifyWindow(Product product) {
		Window adminWindow = new Window("Adminisztatív ablak - Módosítás");
		VerticalLayout modify = new VerticalLayout();
		adminWindow.setContent(modify);
		
		modify.setWidth(500f, Unit.PIXELS);
		modify.setHeight(500f, Unit.PIXELS);
		
		Button cancel = new Button("Mégse");
		
		Button save = new Button("Mentés");
		save.setEnabled(false);
		
		TextField txtProductName = new TextField("A termék neve:");
		txtProductName.setValue(product.getProductName());
		txtProductName.setMaxLength(255);
		txtProductName.setWidth(100f, Unit.PERCENTAGE);
		txtProductName.addValueChangeListener(event -> {
			save.setEnabled(true);
		});
		
		TextField txtPrice = new TextField("A termék ára:");
		txtPrice.setValue(product.getPrice().toString());
		txtPrice.setMaxLength(38);
		txtPrice.setWidth(100f, Unit.PERCENTAGE);
		txtPrice.addValueChangeListener(event -> {
			save.setEnabled(true);
		});
		
		TextField txtDescription = new TextField("A termék leírása:");
		txtDescription.setValue(product.getDescription());
		txtDescription.setMaxLength(255);
		txtDescription.setWidth(100f, Unit.PERCENTAGE);
		txtDescription.addValueChangeListener(event -> {
			save.setEnabled(true);
		});
		
		TextField txtDiscount = new TextField("A termék kedvezménye:");
		txtDiscount.setValue(product.getDiscount().toString());
		txtDiscount.setMaxLength(3);
		txtDiscount.setWidth(100f, Unit.PERCENTAGE);
		txtDiscount.addValueChangeListener(event -> {
			save.setEnabled(true);
		});
		
		modify.addComponent(txtProductName);
		modify.addComponent(txtPrice);
		modify.addComponent(txtDescription);
		modify.addComponent(txtDiscount);
		modify.addComponent(save);
		modify.addComponent(cancel);
		
		cancel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				adminWindow.close();
			}
		});
		
		save.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				product.setProductName(txtProductName.getValue());
				product.setPrice(Integer.parseInt(txtPrice.getValue()));
				product.setDescription(txtDescription.getValue());
				product.setDiscount(Integer.parseInt(txtDiscount.getValue()));
				productService.save(product);
				adminWindow.close();
				productGrid.setItems(productService.listProducts());
				setGridAutoHeight();
			}
		});
		
		adminWindow.center();
		return adminWindow;
	}
	
	private Window createNewWindow() {
		Window adminWindow = new Window("Adminisztatív ablak - Új termék");
		VerticalLayout newProductLayout = new VerticalLayout();
		adminWindow.setContent(newProductLayout);
		
		newProductLayout.setWidth(500f, Unit.PIXELS);
		newProductLayout.setHeight(500f, Unit.PIXELS);
		
		Button cancel = new Button("Mégse");
		
		Button save = new Button("Új termék mentése");
		List<Category> cat1 = categoryService.listCategories();		
		ComboBox<Category> cmbCategory = new ComboBox<>("Kategóriák");
		cmbCategory.setHeight("100%");
		cmbCategory.setItems(cat1);

		TextField txtProductName = new TextField("Az új termék neve:");
		txtProductName.setMaxLength(255);
		txtProductName.setWidth(100f, Unit.PERCENTAGE);
		
		TextField txtPrice = new TextField("Az új termék ára:");
		txtPrice.setMaxLength(38);
		txtPrice.setWidth(100f, Unit.PERCENTAGE);
		
		TextField txtDescription = new TextField("Az új termék leírása:");
		txtDescription.setMaxLength(255);
		txtDescription.setWidth(100f, Unit.PERCENTAGE);
		
		TextField txtDiscount = new TextField("Az új termék kedvezménye:");
		txtDiscount.setMaxLength(3);
		txtDiscount.setWidth(100f, Unit.PERCENTAGE);
		
		
		newProductLayout.addComponent(txtProductName);
		newProductLayout.addComponent(txtPrice);
		newProductLayout.addComponent(txtDescription);
		newProductLayout.addComponent(txtDiscount);
		newProductLayout.addComponent(cmbCategory);
		newProductLayout.addComponent(save);
		newProductLayout.addComponent(cancel);
		
		cancel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				adminWindow.close();
			}
		});
		
		save.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {	
				Category cat = cmbCategory.getValue();
				Product product = new Product();

				product.setProductName(txtProductName.getValue());
				product.setPrice(Integer.valueOf(txtPrice.getValue()));
				product.setDescription(txtDescription.getValue());
				product.setDiscount(Integer.valueOf(txtDiscount.getValue()));
				product.setCategory(cat);
				
				productService.save(product);
				adminWindow.close();
				productGrid.setItems(productService.listProducts());
				setGridAutoHeight();
			}
		});
		
		adminWindow.center();
		return adminWindow;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {}
}
