package com.mobilemedia.AppAlcaldiaSucre.objetos;

import net.rim.device.api.util.Persistable;

public class Categoria implements Persistable {
	private String id;
	private String nombre;
	private String[] subCategorias;

	public Categoria(String id, String nombre, String[] subCategorias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.subCategorias = subCategorias;
	}
	
	public Categoria() { super(); }

	public String[] getSubCategorias(){
		return subCategorias;
	}
	
	public String getSubCategoria(int i){
		if ( 0 <= i && i < subCategorias.length) 
			return subCategorias[i];
		
		return null;
	}
	
	public int getNumSubCategorias(){
		return subCategorias.length;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	
	

	public void setId(String id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setSubCategorias(String[] subCategorias) {
		this.subCategorias = subCategorias;
	}
}
