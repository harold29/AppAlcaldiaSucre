package com.mobilemedia.AppAlcaldiaSucre.objetos;

import com.mobilemedia.AppAlcaldiaSucre.interfaces.*;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.util.Persistable;


public class Noticia implements Persistable, ObjConFoto {
	String id, titulo, resumen, foto;
	String[] fecha;
	String lugar, texto;
	String[] fotos_imageUrl, fotos_thumbnail;
	
	private byte[] bitmap;
	
	public Noticia() {
		this.id="";
		this.fecha = null;
		this.titulo="";
		this.resumen="";
		this.foto="";
		
		this.lugar="";
		this.texto="";
		fotos_imageUrl = null;
		fotos_thumbnail = null;
		
		this.bitmap = null;
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFecha() {
	//	System.out.println("FECHA!: " + this.fecha[0] + "-" + this.fecha[1] + "-" + this.fecha[2]);
		//String k = this.fecha[0]+"-"+this.fecha[1]+"-"+this.fecha[2];
		return this.fecha[0] + "-" + this.fecha[1] + "-" + this.fecha[2];
	}

	public void setFecha(String [] fecha) {
		this.fecha = fecha;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getUrlFoto() {
		return foto;
	}

	public void setUrlFoto(String foto) {
		this.foto = foto;
	}
	
	/***************Detalle de la Noticia***********************/
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public String[] getFotos_imagenUrl() {
		return fotos_imageUrl;
	}

	public void setFotos_imagenUrl(String[] fotos_imagenUrl) {
		this.fotos_imageUrl = fotos_imagenUrl;
	}
	
	public String[] getFotos_thumbnail() {
		return fotos_thumbnail;
	}
	
	public void setFotos_thumbnail(String [] fotos_thumbnail) {
		this.fotos_thumbnail = fotos_thumbnail; 
	}
	
	/*************************************************************/
	
	public Bitmap getFotoBitmap(){
		if (this.bitmap != null )
			return Bitmap.createBitmapFromBytes(bitmap, 0, bitmap.length, 1);
		
		return null;
	}
	
	public void setFotoBitmap(byte[] bitmap){
		this.bitmap = bitmap;
	}
	
	/**************************************************************/
	
	public String toString() {
		return "Noticia [id="
				+ id
				+", fecha="
				+ fecha
				+", titulo="
				+ titulo
				+", resumen="
				+resumen
				+", foto="
				+foto
				+", texto="
				+texto
				+", fotos_imageUrl="
				+fotos_imageUrl
				+", foto_thumbnail="
				+fotos_thumbnail
				+", bitmap"
				+(bitmap != null ? arrayToString(bitmap, bitmap.length)
						: null) + "]";
	}
	
	private String arrayToString(Object array, int len) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < len; i++) {
			if (i > 0)
				buffer.append(", ");
			if (array instanceof byte[])
				buffer.append(((byte[]) array)[i]);
			if (array instanceof Object[])
				buffer.append(((Object[]) array)[i]);
		}
		buffer.append("]");
		return buffer.toString();
	}

}
