package com.mobilemedia.AppAlcaldiaSucre.screens;

import java.util.*;
import java.lang.String;

import com.mobilemedia.AppAlcaldiaSucre.componentes.DirectorioField;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaDirectorioCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.UrlsJsonCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;


import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ActiveRichTextField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.util.StringPattern.Match;

import com.mobilemedia.AppAlcaldiaSucre.objetos.*;
import com.mobilemedia.AppAlcaldiaSucre.JsonMe.*;

public class DirectorioScreen extends MainScreen implements CreadorJson.ScreenConJson {
	private static DirectorioScreen instance;
	
	public static DirectorioScreen getInstance() {
		if (instance == null) {
			instance = new DirectorioScreen();
		}
		return instance;
	}
	
	private JSONObject jsonArray;
	private ListaObjPersistentes objPersistente, objPersistenteRecuperado;
	private VerticalFieldManager contenedorNumeros;
	//com.mobilemedia.AppAlcaldiaSucre.screens.DirectorioScreen 0xfa3ab3bc51983469L
	private static final long id = 0xfa3ab3bc51983469L;
	//private ActiveRichTextField telefonos;
	private LabelFieldCustomColor labelPage;
	
	
	public DirectorioScreen() {
		jsonArray = null;
		objPersistente = (ListaObjPersistentes) PersistentStore.getPersistentObject(id).getContents();
		if (objPersistente == null) {
			objPersistente = new ListaObjPersistentes(id, ListaNoticiaCustom.TIEMPO_PERSISTENCIA);
		}
		
		labelPage = new LabelFieldCustomColor("Directorio", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
				ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
	 
		 HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);
		 headerPage.setBackground(BackgroundFactory.createSolidBackground(0xf8981c));
		 headerPage.setPadding(10,10,10,10);
		 headerPage.add(labelPage);
		
		contenedorNumeros = new VerticalFieldManager();
		
		add(headerPage);
		add(contenedorNumeros);
		
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
				cargarDirectorio();
			}
		}
		CreadorJson cj = new CreadorJson(UrlsJsonCustom.DirectorioScreenJson, this);
		cj.cargarJson();
	}
	
	public void cargarDirectorio() {
		Vector directorio = objPersistente.getListaObj();
		int numDirectorios = directorio.size();
		contenedorNumeros.deleteAll();
		
		if (numDirectorios > 0)
			for (int i = 0; i < numDirectorios; i++)
				contenedorNumeros.add(new DirectorioField((Directorio) directorio.elementAt(i), i == 0));
		else {
			LabelFieldCustomColor vacio = new LabelFieldCustomColor("No hay numeros disponibles",
																	Field.USE_ALL_WIDTH | DrawStyle.HCENTER,
																	ListaNoticiaCustom.VACIO_FONT_COLOR,
																	ListaNoticiaCustom.VACIO_FONT);
			vacio.setPadding(20,1,1,1);
			contenedorNumeros.add(vacio);
		}
	}
	
	public void setJson(JSONObject j) {
		this.jsonArray = j;
	}
	
	public void extraerInformacionJson() {
		JSONArray arregloDirectorio;
		JSONArray numAux;
		String [] telefonos;
		Directorio d;
		Vector directorioAux = new Vector();
		int numElementos = jsonArray.length();
		
		try {
			arregloDirectorio = jsonArray.getJSONArray("alcaldia");
			numElementos = arregloDirectorio.length();			
			for (int i = 0; i < numElementos; i++) {
				d = new Directorio();
				d.setInstitucion("Alcaldia");
				d.setDependencia(arregloDirectorio.getJSONObject(i).getString("nombre"));
				numAux = arregloDirectorio.getJSONObject(i).getJSONArray("telefonos");
				int tamAux = numAux.length();
				telefonos = new String [tamAux];
				for (int k = 0; k < tamAux; k++) {
					String sAux = numAux.getString(k);
					sAux = cleanString(sAux);
					System.out.println("NUMERO SIN ESPACIOS: " + sAux);
					if (sAux.length() <= 7) {
						sAux = "+580212"+sAux;
					}
					telefonos[k] = sAux;
				}
				d.setTelefonos(telefonos);
				directorioAux.addElement(d);
			}
			
			arregloDirectorio = jsonArray.getJSONArray("otros");
			numElementos = arregloDirectorio.length();
			for (int i = 0; i < numElementos; i++) {
				d = new Directorio();
				d.setInstitucion("Otros");
				d.setDependencia(arregloDirectorio.getJSONObject(i).getString("nombre"));
				numAux = arregloDirectorio.getJSONObject(i).getJSONArray("telefonos");
				int tamAux = numAux.length();
				telefonos = new String [tamAux];
				for (int k = 0; k < tamAux; k++) {
					String sAux = numAux.getString(k);
					sAux = cleanString(sAux);
					if (sAux.length() <= 7) {
						sAux = "+580212"+sAux;
					}
					telefonos[k] = sAux;
				}
				d.setTelefonos(telefonos);
				directorioAux.addElement(d);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("Error en el recorrido de JSON, favor revisar estandares");
		}
		
		objPersistente.setListaObj(directorioAux);
		synchronized (UiApplication.getEventLock()) {
			cargarDirectorio();
		}
		objPersistente.guardar(true);
	}
	
	public String cleanString (String s) {
		int tam = s.length();
		String num = "";
		for(int i = 0; i < tam; i++) {
			if (s.charAt(i) != ' ' && s.charAt(i) != '-' && s.charAt(i) != '.' && s.charAt(i) != '('
					&& s.charAt(i) != ')')  {
				num = num + s.charAt(i);
			}
		}
		return num;
	}
	
}