package com.mobilemedia.AppAlcaldiaSucre.screens;

import org.w3c.dom.Text;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ActiveRichTextField;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.extension.component.PictureScrollField.ScrollEntry;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;


import com.mobilemedia.AppAlcaldiaSucre.JsonMe.*;
import com.mobilemedia.AppAlcaldiaSucre.componentes.LabelFieldCustomColor;
import com.mobilemedia.AppAlcaldiaSucre.componentes.PictureScrollFieldCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.DetalleNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.ListaNoticiaCustom;
import com.mobilemedia.AppAlcaldiaSucre.custom.UrlsJsonCustom;
import com.mobilemedia.AppAlcaldiaSucre.engine.ScreenEngine;
import com.mobilemedia.AppAlcaldiaSucre.objetos.CreadorJson;
import com.mobilemedia.AppAlcaldiaSucre.objetos.ListaObjPersistentes;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Noticia;
import com.mobilemedia.AppAlcaldiaSucre.transport.BBRequestQueue;




public class DetalleNoticiaScreen extends MainScreen implements CreadorJson.ScreenConJson {
	
	private static DetalleNoticiaScreen instance;
	
	public static DetalleNoticiaScreen getInstance(Noticia n) {
		if (instance == null)
			instance = new DetalleNoticiaScreen(n);
		
		instance.noticia = n;
		instance.actualizar();
	
		return instance;
	}
	
	
	
	private ListaObjPersistentes objPersistente;
	//com.mobilemedia.AppAlcaldiaSucre.screens.NoticiasScreen 0xc5e2dd4128f7de14L
	private static final long id = 0xc5e2dd4128f7de14L;
	
	private Noticia noticia;
	private JSONObject jsonObject;
	
	PictureScrollFieldCustom pictureScrollField;
	private BitmapField foto;
	private LabelFieldCustomColor fecha, titulo;
	private ActiveRichTextField texto;
	private VerticalFieldManager pantalla;
	ScrollEntry[] entries, defaultEntries;
	
	public DetalleNoticiaScreen(Noticia noticia) {
		
		foto = new BitmapField(null, Field.FIELD_HCENTER | Field.USE_ALL_WIDTH | Field.FOCUSABLE);
		texto = new ActiveRichTextField("",
										RichTextField.USE_ALL_WIDTH |
										RichTextField.FIELD_HCENTER | RichTextField.TEXT_ALIGN_LEFT) {
			protected void paint(Graphics g) {
				g.setColor(DetalleNoticiaCustom.TEXTO_COLOR_FONT);
				super.paint(g);
			}
		};
		
		titulo = new LabelFieldCustomColor("",
											Field.FIELD_VCENTER | Field.USE_ALL_WIDTH | DrawStyle.HCENTER,
											DetalleNoticiaCustom.TITULO_COLOR_FONT,
											DetalleNoticiaCustom.TITULO_FONT);
		
		fecha = new LabelFieldCustomColor("",
											Field.FIELD_RIGHT | Field.USE_ALL_WIDTH | DrawStyle.RIGHT,
											DetalleNoticiaCustom.FECHA_COLOR_FONT,
											DetalleNoticiaCustom.FECHA_HORA_FONT);
		
		foto.setPadding(DetalleNoticiaCustom.PADDING_FOTO);
		texto.setPadding(DetalleNoticiaCustom.PADDING_TEXTO);
		fecha.setPadding(DetalleNoticiaCustom.PADDING_FECHA_HORA);
		titulo.setPadding(DetalleNoticiaCustom.PADDING_TITULO);
		
		texto.setFont(DetalleNoticiaCustom.TEXTO_FONT);
		
		defaultEntries = new ScrollEntry[1];
		defaultEntries[0] = new ScrollEntry( Bitmap.getBitmapResource("imgTransparente.png"), null, null);
		
		pantalla = new VerticalFieldManager(Field.NON_FOCUSABLE);
    	pantalla.setBackground(BackgroundFactory.createSolidBackground(0xf1f1f1));
    	
    	LabelFieldCustomColor labelPage = new LabelFieldCustomColor("Noticias", Field.NON_FOCUSABLE | Field.FIELD_HCENTER, 
    																ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT_COLOR,
    																ListaNoticiaCustom.FECHA_ACTUALIZACION_FONT);
	 
    	HorizontalFieldManager headerPage = new HorizontalFieldManager(Field.FIELD_VCENTER | Field.FIELD_HCENTER | Field.USE_ALL_WIDTH);	
    	headerPage.setBackground(BackgroundFactory.createSolidBackground(0x00a94e));
    	headerPage.setPadding(10,10,10,10);	
    	headerPage.add(labelPage);
    	
    	pantalla.add(headerPage);
		pantalla.add(foto);
		pantalla.add(titulo);
		pantalla.add(fecha);
		pantalla.add(texto);
		
		add(pantalla);
		
		ScreenEngine.getInstance().activarEfectoVisualSlide(this);
	}
	
	public void actualizar() {
		limpiarPantalla();
		
		objPersistente = (ListaObjPersistentes) PersistentStore.getPersistentObject(id).getContents();
		
		if(noticia.getTexto().equalsIgnoreCase("") || objPersistente.hayQueActualizar()) {
			CreadorJson cj = new CreadorJson(UrlsJsonCustom.DetalleNoticiaScreenJson+"?idNoticia=" + noticia.getId(),
											instance);
			cj.cargarJson();
			System.out.println("ACTUALIZANDO...");
		} else {
			System.out.println("EXTRAYENDO DE MEMORIA");
			actualizaPantalla(1);
		//	actualizaPantalla(2);
		}
	}
	
	public void actualizaPantalla(int parte) {
		if (parte == 1) {
			limpiarPantalla();
			Bitmap bitmap = noticia.getFotoBitmap();
			if (bitmap != null && DetalleNoticiaCustom.MOSTRAR_IMG) {
				Bitmap bitmapEscalado = new Bitmap(DetalleNoticiaCustom.WIDTH_IMG,
													DetalleNoticiaCustom.HEIGHT_IMG);
				bitmap.scaleInto(bitmapEscalado, Bitmap.FILTER_BILINEAR, Bitmap.SCALE_TO_FILL);
				foto.setBitmap(bitmapEscalado);
			}
			
			if (DetalleNoticiaCustom.MOSTRAR_FECHA) 
				fecha.setText(!noticia.getFecha().equalsIgnoreCase("") ? "Fecha: " + noticia.getFecha() : "");
			
			titulo.setText(noticia.getTitulo());
			texto.setText(noticia.getTexto());
		}
	}
	
	public void limpiarPantalla() {
		if (DetalleNoticiaCustom.MOSTRAR_IMG)
			foto.setFocus();
		foto.setBitmap(null);
		fecha.setText("");
		titulo.setText("");
		//add(pantalla);
	}
	
	public void setJson(JSONObject j) {
		this.jsonObject = j;
	}
	
	public void extraerInformacionJson() {
		JSONArray arregloJson;
		
		try {
			noticia.setTitulo(jsonObject.getString("titulo"));
			System.out.println("TITULO: " + jsonObject.getString("titulo"));
			noticia.setTexto(jsonObject.getString("texto"));
			arregloJson = jsonObject.getJSONArray("fecha");
			int tamAux = arregloJson.length();
			String [] fecha = new String [tamAux];
			for (int i = 0; i < tamAux; i++) {
				fecha[i] = arregloJson.getString(i);
			}
			noticia.setFecha(fecha);
			arregloJson = jsonObject.getJSONArray("foto");
			noticia.setUrlFoto(arregloJson.getString(0));
			synchronized (UiApplication.getEventLock()) { actualizaPantalla(1); }
			
			objPersistente.guardar(false);
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("******ERROR: Recorrido del JSON******");
		}
	}
	
	protected boolean keyChar (char c, int status, int time) {
		if (c == Characters.ESCAPE)
			BBRequestQueue.getInstance().cancelAllRequests(true);
		
		return super.keyChar(c, status, time);
	}
}