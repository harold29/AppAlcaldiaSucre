package com.mobilemedia.AppAlcaldiaSucre.screens;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.mobilemedia.AppAlcaldiaSucre.componentes.BitmapButtonField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.componentes.NoticiaField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.ProgressAnimationField;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.UrlsJsonCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.objetos.*;
import com.mobilemedia.AppAlcaldiaSucre.JsonMe.*;
import com.mobilemedia.AppAlcaldiaSucre.objetos.CreadorJson;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class NoticiasScreen extends MainScreen implements CreadorJson.ScreenConJson {
	
	private static NoticiasScreen instance;
	
	public static NoticiasScreen getInstance(){
		if (instance == null) 
			instance = new NoticiasScreen();
		return instance;
	}
	
	private JSONObject jsonArray;
	private ListaObjPersistentes objPersistente, objPersistenteRecuperado;
	private VerticalFieldManager contenedorListaNoticias;
	private BitmapButtonField botonActualizar;
	private LabelFieldCustomColor labelPage;
	//com.mobilemedia.AppAlcaldiaSucre.screens.NoticiasScreen 0xc5e2dd4128f7de14L
	private static final long id = 0xc5e2dd4128f7de14L;
	
	
	public NoticiasScreen() {
		jsonArray = null;
		objPersistente = (ListaObjPersistentes) PersistentStore.getPersistentObject(id).getContents();
		if (objPersistente == null) {
			objPersistente = new ListaObjPersistentes(id, ListaNoticiaCustom.TIEMPO_PERSISTENCIA);
		}
		
		labelPage = new LabelFieldCustomColor("Noticias", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
					ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
		 
		 HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		 headerPage.setBackground(BackgroundFactory.createSolidBackground(0x00a94e));
		 headerPage.setPadding(10,10,10,10);
		 headerPage.add(labelPage);
		 
		
		 contenedorListaNoticias = new VerticalFieldManager();
		 
		 add(headerPage);
		 add(contenedorListaNoticias);
		 
		// botonActualizar.setChangeListener(actualizarLista);	
		 ScreenEngine.getInstance().activarEfectoVisualSlide(this);
		 actualizar(false);
	}
	
	FieldChangeListener actualizarLista = new FieldChangeListener() {		
		public void fieldChanged(Field field, int context) {
			actualizar(true);
		}
	};
	
	public void actualizar(boolean botonPresionado) {
		if (!botonPresionado) {
			objPersistenteRecuperado = (ListaObjPersistentes) objPersistente.cargar();
			if (objPersistenteRecuperado != null) {
				objPersistente = objPersistenteRecuperado;
				cargarNoticias();
			}
		}
		CreadorJson cj = new CreadorJson(UrlsJsonCustom.NoticiasScreenJson, this);
		cj.cargarJson();
	}
	
	
	public void cargarNoticias() {
		Vector noticias = objPersistente.getListaObj();
		int numNoticias = noticias.size();		
		contenedorListaNoticias.deleteAll();		
		if (numNoticias > 0) 
			for (int i = 0; i < numNoticias; i++)
				contenedorListaNoticias.add( new NoticiaField( (Noticia) noticias.elementAt(i), i == 0) );
		else {
			LabelFieldCustomColor vacio = new LabelFieldCustomColor("No hay Noticias nuevas", 
																Field.USE_ALL_WIDTH | DrawStyle.HCENTER,
																ListaNoticiaCustom.VACIO_FONT_COLOR,
																ListaNoticiaCustom.VACIO_FONT);
			vacio.setPadding(20,1,1,1);
			contenedorListaNoticias.add(vacio);
		}		
	}

	
	public void setJson(JSONObject j) {
		this.jsonArray = j;
	}
	
	public void extraerInformacionJson() {
		JSONArray arregloNoticias;
		JSONArray objetoAux;
		String [] fecha;
		Noticia n;
		Vector noticiasAux = new Vector();
		
		try {
			arregloNoticias = jsonArray.getJSONArray("noticias");
			int numElementos = arregloNoticias.length();
			System.out.println("TAMANO NOTICIAS: " + numElementos);
			for (int i = 0; i < numElementos; i++) {
				n = new Noticia();
				fecha = new String [3];
				n.setId(arregloNoticias.getJSONObject(i).getString("id"));
				objetoAux = arregloNoticias.getJSONObject(i).getJSONArray("fecha");
				int tamAux = objetoAux.length();
				for (int k = 0; k < tamAux;k++) {
					fecha[k] = objetoAux.getString(k);
				}
				n.setFecha(fecha);
				n.setTitulo(arregloNoticias.getJSONObject(i).getString("titulo"));
				n.setResumen(arregloNoticias.getJSONObject(i).getString("resumen"));
				n.setUrlFoto(arregloNoticias.getJSONObject(i).getString("foto"));
				noticiasAux.addElement(n);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("Error en el recorrido del JSON, favor revisar");
		}

		
		objPersistente.setListaObj(noticiasAux);
		synchronized (UiApplication.getEventLock()) {
			cargarNoticias();
		//	try 
		}
		objPersistente.guardar(true);
	}
		
	
}
