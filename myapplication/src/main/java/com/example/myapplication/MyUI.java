package com.example.myapplication;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;
	// Campos del inventario
	private TextField codigo = new TextField("Código de inventario:");
	private TextField nombre = new TextField("Nombre:");
	private TextField cantidad = new TextField("Cantidad:");
	private HorizontalLayout layoutBotones = new HorizontalLayout();
	private Button btnAgregar = new Button("Agregar");
	private Button btnBorrar = new Button("Borrar");
	private Button btnEditar = new Button("Editar");
	private Button btnGuardar = new Button("Guardar");
	private TextArea descripcion = new TextArea("Descripción");
    
	private Inventario itemSeleccionado;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        // Titulo
        Label label = new Label("Sistema Gestión De Inventario");
        label.setStyleName(ValoTheme.LABEL_H1);

        // Listado del inventario 
        List<Inventario> listaInventario = new LinkedList<Inventario>();
        
        // Tabla que permite consultar los productos de inventario
        Grid<Inventario> grid = new Grid<>();
		grid.addItemClickListener(e -> itemSeleccionado = e.getItem());
        grid.addColumn(Inventario::getCodigo).setCaption("Código:");
        grid.addColumn(Inventario::getNombre).setCaption("Nombre:");
        grid.addColumn(Inventario::getDescripcion).setCaption("Descripción:");
        grid.addColumn(Inventario::getCantidad).setCaption("Cantidad de productos:");


        // Butones
        layoutBotones.addComponents(btnAgregar, btnBorrar, btnEditar);

        btnAgregar.addClickListener(evento -> {
        	// Muestra una ventana emergente
        	Window subWindow = new Window("Agregar Inventario");
        	subWindow.setWidth("50%");
        	subWindow.setHeight("80%");
        	VerticalLayout subContent = new VerticalLayout();
        	codigo.setWidth("100%");
        	nombre.setWidth("100%");
        	descripcion.setWidth("100%");
        	cantidad.setWidth("100%");
        	codigo.setRequiredIndicatorVisible(true);
        	nombre.setRequiredIndicatorVisible(true);
        	cantidad.setRequiredIndicatorVisible(true);
        	subContent.addComponents(codigo, nombre, descripcion, cantidad, btnGuardar);
        	subContent.setComponentAlignment(btnGuardar, Alignment.BOTTOM_CENTER);
        	subWindow.setContent(subContent);
        	subWindow.center();
        	subWindow.setModal(true);
        	UI.getCurrent().addWindow(subWindow);
        	
        	btnGuardar.addClickListener(e -> {
        		Inventario inventario1 = new Inventario(new Long(codigo.getValue()), nombre.getValue(), descripcion.getValue(), new Long(cantidad.getValue()));
        		listaInventario.add(inventario1);
        		grid.setItems(listaInventario);
        		subWindow.close();
        	});
        	codigo.clear();
        	nombre.clear();
        	descripcion.clear();
        	cantidad.clear();
        });
        
        btnBorrar.addClickListener(evento -> {
        	if(itemSeleccionado != null) {
        		listaInventario.remove(itemSeleccionado);
			grid.setItems(listaInventario);
			itemSeleccionado = null;
        	}
        });
        
        btnEditar.addClickListener(evento -> {
	        	if(itemSeleccionado != null) {
	        		// Muestra una ventana emergente para editar
	            	Window subWindow = new Window("Editar Inventario");
	            	subWindow.setWidth("50%");
	            	subWindow.setHeight("80%");
	                VerticalLayout subContent = new VerticalLayout();
	                codigo.setWidth("100%");
	                nombre.setWidth("100%");
	                descripcion.setWidth("100%");
	                cantidad.setWidth("100%");
	                codigo.setRequiredIndicatorVisible(true);
	                nombre.setRequiredIndicatorVisible(true);
	                cantidad.setRequiredIndicatorVisible(true);
	                System.out.println("itemSeleccionado----"+itemSeleccionado.getCodigo().toString());
	                codigo.setValue(itemSeleccionado.getCodigo().toString());
		            	nombre.setValue("Nombre prueba");
		            	System.out.println("itemSeleccionado----"+itemSeleccionado.getNombre().toString());
		            	descripcion.setValue(itemSeleccionado.getDescripcion());
		            	cantidad.setValue(itemSeleccionado.getCantidad().toString());
		            	System.out.println("itemSeleccionado----"+itemSeleccionado.getCantidad().toString());
	                subContent.addComponents(codigo, nombre, descripcion, cantidad, btnGuardar);
	                subContent.setComponentAlignment(btnGuardar, Alignment.BOTTOM_CENTER);
	                subWindow.setContent(subContent);
	                subWindow.center();
	                subWindow.setModal(true);
	        		UI.getCurrent().addWindow(subWindow);
	                
	        		btnGuardar.addClickListener(e -> {
	        			Inventario inventario1 = new Inventario(
	                			new Long(codigo.getValue()), nombre.getValue(), descripcion.getValue(), new Long(cantidad.getValue()));
	        			listaInventario.add(inventario1);
	            		grid.setItems(listaInventario);
	            		subWindow.close();
	        		});
	        		codigo.clear();
	        		nombre.clear();
	        		descripcion.clear();
	        		cantidad.clear();
	        	}
        });
        
        
        
        
        layout.addComponents(label, layoutBotones, grid);
        layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(layoutBotones, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
        
        setContent(layout);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1123L;
    }
}

//        if (itemSeleccionado != null) {
//			inventario.remove(itemSeleccionado);
//			pantalla.getTblVentas().setItems(productosSeleccionados);
//			actualizarCampoTotalVenta(productosSeleccionados);
//			itemSelecionado = null;
//} else {
////actualizarCampoTotalVenta(productosSeleccionados);
//System.out.println("Selecciona un producto a remover");
//} 

//        grid.addItemClickListener(event ->{
//        	
//        	Inventario item = event.getItem();
//        	
//        });