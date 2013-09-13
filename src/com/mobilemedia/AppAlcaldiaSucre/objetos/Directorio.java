package com.mobilemedia.AppAlcaldiaSucre.objetos;

import net.rim.device.api.util.Persistable;
import java.lang.*;

public class Directorio implements Persistable {
	String status, institucion, dependencia;
	String [] telefonos;
	
	public Directorio () {
		this.status = ""; this.institucion = ""; this.dependencia ="";
		this.telefonos = null;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getInstitucion() {
		return institucion;
	}
	
	public String getDependencia() {
		return dependencia;
	}
	
	public String getTelefonos() {
		int tam = telefonos.length;
		StringBuffer tel = new StringBuffer("Telefonos: \n");
		for (int i = 0; i < tam; i++) {
			tel.append(telefonos[i]+"\n");
		}
		return tel.toString();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
	
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	
	public void setTelefonos(String [] telefonos) {
		this.telefonos = telefonos;
	}
	
	public String toString () {
		return "Directorio [status"
				+ status
				+ ", institucion="
				+ institucion
				+ ", dependencia="
				+ dependencia
				+", telefonos"
				+ telefonos
				+ "]";
	}
	
	public String arrayToString(Object array, int len) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		for (int i = 0; i < len; i++) {
			if (i > 0)
				buffer.append(",");
			if (array instanceof byte[])
				buffer.append(((byte[]) array)[i]);
			if (array instanceof Object[])
				buffer.append(((Object[]) array)[i]);
		}
		buffer.append("]");
		return buffer.toString();
	}
}