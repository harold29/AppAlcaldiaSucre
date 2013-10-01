package com.mobilemedia.AppAlcaldiaSucre.objetos;

import com.mobilemedia.AppAlcaldiaSucre.push.PersistentStorage;

import net.rim.device.api.util.Persistable;


public class Usuario implements Persistable {
	
	private String nombre;
	private String telefono;
	private long fechaNac;
	private String cedula;
	private long id;
	private int denunciasEnviadas;
	
	private static Usuario instance;
	
	static {
		instance = PersistentStorage.getUsuario();
		if (instance == null) {
			instance = new Usuario();
			PersistentStorage.setUsuario(instance);
		}
	}
	
	public static Usuario getInstance() {
		return instance;
	}
	
	public Usuario() {
		super();
		
		nombre = "";
		telefono = "";
		fechaNac = 0;
		cedula = "";
		id = 0;
		denunciasEnviadas = 0;
	}
	
	public static String getCedula() {
		return instance.cedula;
	}
	
	public static String getTelefono() {
		return instance.telefono;
	}
	
	public static String getNombre() {
		return instance.nombre;
	}
	
	public static long getFechaNac() {
		return instance.fechaNac;
	}
	
	public static long getId() {
		return instance.id;
	}
	
	public static int getNumDenunciasEnviadas() {
		return instance.denunciasEnviadas;
	}
	
	/*******************************************************************************************************************/
	
	public static void setCedula(String cedula) {
		instance.cedula = cedula;
	}
	
	public static void setTelefono (String telefono) {
		instance.telefono = telefono;
	}
	
	public static void setNombre (String nombre) {
		instance.nombre = nombre;
	}
	
	public static void setFechaNac (long fecha) {
		instance.fechaNac = fecha;
	}
	
	public static void setId (long id) {
		instance.id = id;
	}
	
	public static void setNumDenuncias (int denuncias) {
		instance.denunciasEnviadas = denuncias;
	}
}