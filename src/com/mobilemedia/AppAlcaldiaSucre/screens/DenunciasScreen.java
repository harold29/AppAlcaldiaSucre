package com.mobilemedia.AppAlcaldiaSucre.screens;

import javax.microedition.amms.control.camera.*;
import javax.microedition.media.*;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.ui.container.MainScreen;

import com.mobilemedia.AppAlcaldiaSucre.componentes.EditFieldDenuncias;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;

public class DenunciasScreen extends MainScreen {
	
	private static DenunciasScreen instance;
	
	public static DenunciasScreen getInstance() {
		if (instance == null) {
			instance = new DenunciasScreen();
		}
		return instance;
	}
	
	private LabelFieldCustomColor labelPage;
	
	public DenunciasScreen() {
		VerticalFieldManager pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | NO_VERTICAL_SCROLL | USE_ALL_HEIGHT | USE_ALL_WIDTH);
		pantalla.setBackground(BackgroundFactory.createSolidBackground(0x00FCB53E));
		
		labelPage = new LabelFieldCustomColor("Directorio", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xf8981c));
		headerPage.setPadding(10,10,10,10);
		headerPage.add(labelPage);
		pantalla.add(headerPage);
		
		VerticalFieldManager editArea = new VerticalFieldManager();
		//editArea.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		
		LabelFieldCustomColor labelEdit = new LabelFieldCustomColor("Nombre y Apellido:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField nombreDenunciante = new BasicEditField("", null, 40, USE_ALL_WIDTH);
		nombreDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		nombreDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(nombreDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Dirección:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField direccionDenunciante = new BasicEditField("",null,70,USE_ALL_WIDTH);
		direccionDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		direccionDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(direccionDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);


		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Número de Teléfono:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField numeroDenunciante = new BasicEditField("",null,20,USE_ALL_WIDTH);
		numeroDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		numeroDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(numeroDenunciante);
		editArea.setPadding(20,130,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Email:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField emailDenunciante = new BasicEditField("",null,30,USE_ALL_WIDTH);
		emailDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		emailDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(emailDenunciante);
		editArea.setPadding(20,90,0,30);
		pantalla.add(editArea);
		
		editArea = new VerticalFieldManager();
		labelEdit = new LabelFieldCustomColor("Comentarios:", USE_ALL_WIDTH, 0, getFont());
		BasicEditField comentarioDenunciante = new BasicEditField("",null,140,USE_ALL_WIDTH);
		comentarioDenunciante.setBorder(BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1)));
		comentarioDenunciante.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		editArea.add(labelEdit);
		editArea.add(comentarioDenunciante);
		editArea.setPadding(20,40,0,30);
		pantalla.add(editArea);
		
		/*editArea = new VerticalFieldManager();
		ButtonField submitButton = new ButtonField("Submit") {
             protected boolean navigationClick(int status, int time) {
                 return true;
             }
         };
         editArea.add(submitButton);
         pantalla.add(editArea);*/
		
	//	pantalla.add(headerPage);
	//	pantalla.add(label_field);
	//	pantalla.add(editArea);
	//	pantalla.add(direccionDenunciante);
	//	pantalla.add(numeroDenunciante);
	//	pantalla.add(emailDenunciante);
	//	pantalla.add(submitButton);
		
		//add(submitButton);
		add(pantalla);
		add(submitButton);
	}
}