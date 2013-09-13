package com.mobilemedia.AppAlcaldiaSucre.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ActiveRichTextField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.extension.component.PictureScrollField.ScrollEntry;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.mobilemedia.AppAlcaldiaSucre.objetos.Directorio;
import com.mobilemedia.AppAlcaldiaSucre.JsonMe.*;
import com.mobilemedia.AppAlcaldiaSucre.objetos.*;
import com.mobilemedia.AppAlcaldiaSucre.transport.BBRequestQueue;
import com.mobilemedia.AppAlcaldiaSucre.componentes.*;
import com.mobilemedia.AppAlcaldiaSucre.custom.DetalleNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;




public class DetalleDirectorioScreen extends MainScreen implements CreadorJson.ScreenConJson {
	
	private static DetalleDirectorioScreen instance;
	
	public static DetalleDirectorioScreen getInstance(Directorio d) {
		if (instance == null)
			instance = new DetalleDirectorioScreen(d);
		
		instance.directorio = d;
		instance.actualizar();
		
		return instance;
	}
	
	private ListaObjPersistentes objPersistente;
	//com.mobilemedia.AppAlcaldiaSucre.screens.DirectorioScreen 0xfa3ab3bc51983469L
	private static final long id = 0xfa3ab3bc51983469L;
	private Directorio directorio;
	private JSONObject jsonArray;
	
	private LabelFieldCustomColor dependencia, labelPage;
	private ActiveRichTextField num_tlf;
	ScrollEntry[] defaultEntries;
	
	public DetalleDirectorioScreen (Directorio directorio) {
		
		VerticalFieldManager pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE | Field.USE_ALL_HEIGHT | Field.USE_ALL_WIDTH);
    	pantalla.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
		
		num_tlf = new ActiveRichTextField("",
											RichTextField.USE_ALL_WIDTH |
											RichTextField.FIELD_HCENTER) {
			protected void paint (Graphics g) {
				g.setColor(DetalleNoticiaCustom.TEXTO_COLOR_FONT);
				super.paint(g);
			}
		};
		
		dependencia = new LabelFieldCustomColor("",
												Field.FIELD_VCENTER | Field.USE_ALL_WIDTH | DrawStyle.HCENTER,
												DetalleNoticiaCustom.TITULO_COLOR_FONT,
												DetalleNoticiaCustom.TITULO_FONT);
		
		num_tlf.setPadding(DetalleNoticiaCustom.PADDING_TEXTO);
		dependencia.setPadding(DetalleNoticiaCustom.PADDING_TITULO);
		num_tlf.setFont(DetalleNoticiaCustom.TEXTO_FONT);
		
		defaultEntries = new ScrollEntry[1];
		defaultEntries[0] = new ScrollEntry(Bitmap.getBitmapResource("imgTransparente"),null,null);
		
		labelPage = new LabelFieldCustomColor("Directorio", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
												ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
	 
		HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		headerPage.setBackground(BackgroundFactory.createSolidBackground(0xf8981c));
		headerPage.setPadding(10,10,10,10);
		headerPage.add(labelPage);
		
		pantalla.add(headerPage);
		pantalla.add(dependencia);
		pantalla.add(num_tlf);
		 
		add(pantalla);
		
		ScreenEngine.getInstance().activarEfectoVisualSlide(this);
	}
	
	public void actualizar() {
		limpiarPantalla();
		
		objPersistente = (ListaObjPersistentes) PersistentStore.getPersistentObject(id).getContents();
		
		System.out.println("**EXTRAE NUMS DE MEMORIA**");
		actualizarPantalla(1);
		//actualizarPantalla(2);
	}
	
	public void actualizarPantalla(int parte) {
		if (parte == 1) {
			dependencia.setText(directorio.getDependencia());
			num_tlf.setText(directorio.getTelefonos());
		}
	}
	
	public void limpiarPantalla() {
		dependencia.setText("");
		num_tlf.setText("");
	}
	
	public void setJson(JSONObject j){
    	this.jsonArray = j;
    }
	
	public void extraerInformacionJson() {
		JSONObject objetoJson;
    	JSONArray arregloJson;
    	int elementosArregloJson;

    	
    	try {
    		elementosArregloJson = jsonArray.getJSONArray("alcaldia").length();
    		for (int i = 0; i < elementosArregloJson; i++) {
    			objetoJson = jsonArray.getJSONArray("alcaldia").getJSONObject(i);
    			directorio.setDependencia(objetoJson.getString("nombre"));
    			arregloJson = objetoJson.getJSONArray("telefonos");
    			int tamAux = arregloJson.length();
    			String [] telefonos = new String[tamAux];
    			for (int k = 0; k < tamAux; k++) {
    				telefonos[k] = arregloJson.getString(k);
    			}
    			directorio.setTelefonos(telefonos);
    			synchronized (UiApplication.getEventLock()) { actualizarPantalla(1); }
    		}
    		
    		elementosArregloJson = jsonArray.getJSONArray("otros").length();
    		for (int i = 0; i < elementosArregloJson; i++) {
    			objetoJson = jsonArray.getJSONArray("alcaldia").getJSONObject(i);
    			directorio.setDependencia(objetoJson.getString("nombre"));
    			arregloJson = objetoJson.getJSONArray("telefonos");
    			int tamAux = arregloJson.length();
    			String [] telefonos = new String[tamAux];
    			for (int k = 0; k < tamAux; k++) {
    				telefonos[k] = arregloJson.getString(k);
    			}
    			directorio.setTelefonos(telefonos);
    			synchronized (UiApplication.getEventLock()) { actualizarPantalla(1); }
    		}
    	} catch (JSONException e) {
    		e.printStackTrace();
    		System.out.println("******ERROR: Recorrido del JSON******");
    	}
	}
	
	
	protected boolean keyChar(char c, int status, int time) {
		if (c == Characters.ESCAPE)
			BBRequestQueue.getInstance().cancelAllRequests(true);
		
		return super.keyChar(c, status, time);
	}
	
	public String cleanString (String s) {
		int tam = s.length();
		String num = "";
		for(int i = 0; i < tam; i++) {
			if (s.charAt(i) != ' ' && s.charAt(i) != '-' && s.charAt(i) != '.' && s.charAt(i) != '+' && s.charAt(i) != '('
					&& s.charAt(i) != ')')  {
				num = num + s.charAt(i);
			}
		}
		return num;
	}
	
	
}