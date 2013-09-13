package com.mobilemedia.AppAlcaldiaSucre.objetos;

import java.util.Vector;

import com.mobilemedia.AppAlcaldiaSucre.custom.Constantes;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.Persistable;

public class ListaObjPersistentes implements Persistable{
	
	private Vector listaObj; // Tiene Objs: Persistable
	private long fechaActualizacion;
	private long id;
	private long tiempoPersistencia;
	
	public ListaObjPersistentes(long id, long tiempo){
		this.id = id;
		listaObj = new Vector();
		fechaActualizacion = System.currentTimeMillis();
		tiempoPersistencia = tiempo; 
	}
	
	public boolean hayQueActualizar(){
		if (tiempoPersistencia == Constantes.PERMANENTE_PERSISTENCIA)  return false;
		
		return (System.currentTimeMillis() - fechaActualizacion) > tiempoPersistencia;//86400000;
	}
	
	public void agregarObjeto (Object o){
		listaObj.addElement(o);
	}

	public Vector getListaObj() {
		return listaObj;
	}

	public void setListaObj(Vector listaObj) {
		this.listaObj = listaObj;
	}
	
	// Recuperar datos persistentes
	public Object cargar(){
		PersistentObject store = PersistentStore.getPersistentObject(id);
		synchronized (store) {
			return store.getContents();
		}
	}
	
	// Almacenar datos persistentes
	public void guardar(boolean actualizarFecha){
		PersistentObject store = PersistentStore.getPersistentObject(id);
		synchronized (store) {
			store.setContents(this);
			store.commit();
		}
		
		if (actualizarFecha)
			fechaActualizacion = System.currentTimeMillis();
	}

	public long getFechaActualizacion() {
		return fechaActualizacion;
	}
}