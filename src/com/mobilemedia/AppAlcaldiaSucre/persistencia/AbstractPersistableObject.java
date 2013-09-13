package com.mobilemedia.AppAlcaldiaSucre.persistencia;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.Persistable;

public class AbstractPersistableObject implements Persistable{

	private long id;

	protected AbstractPersistableObject(long id){
		this.id = id;
	}
	
	public Object cargar(){
		PersistentObject store = PersistentStore.getPersistentObject(id);
		synchronized (store) {
			return store.getContents();
		}
	}
	public void guardar(){
		PersistentObject store = PersistentStore.getPersistentObject(id);
		synchronized (store) {
    		store.setContents(this);
			store.commit();
		}
	}
	
}
