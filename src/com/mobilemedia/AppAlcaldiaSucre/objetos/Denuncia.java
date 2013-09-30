package com.mobilemedia.AppAlcaldiaSucre.objetos;


import net.rim.device.api.system.Bitmap;


public class Denuncia
{
	String nomDenunciante, dirDenunciante, tlfDenunciante, emailDenunciante, comentarios, tipo;
	String fecha;
	Bitmap foto;
	long id;
	
	public Denuncia()
	{
		this.nomDenunciante = ""; 
		this.dirDenunciante = "";
		this.tlfDenunciante = "";
		this.emailDenunciante = "";
		this.tipo = "";
		this.fecha = "";
		this.foto = null;
		this.id = 0;
	}
	
	/***************************************************************************/	
	
	public String getNombre() {
		return nomDenunciante;
	}
	
	public String getDireccion() {
		return dirDenunciante;
	}
	
	public String getTelefono() {
		return tlfDenunciante;
	}
	
	public String getEmail() {
		return emailDenunciante;
	}
	
	public String getComentarios() {
		return comentarios;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public Bitmap getFoto() {
		return foto;
	}
	
	public long getId() {
		return id;
	}
	
	/*****************************************************************************/

	public void setNombre (String nombre) {
		this.nomDenunciante = nombre;
	}
	
	public void setDireccion (String direccion) {
		this.dirDenunciante = direccion;
	}
	
	public void setTelefono (String telefono) {
		this.tlfDenunciante = telefono;
	}
	
	public void setEmail (String email) {
		this.emailDenunciante = email;
	}
	
	public void setComentarios (String comentarios) {
		this.comentarios = comentarios;
	}
	
	public void setTipo (String tipo) {
		this.tipo = tipo;
	}
	
	public void setFecha (String fecha) {
		this.fecha = fecha;
	}
	
	public void setFoto (Bitmap foto) {
		this.foto = foto;
	}
	
	public void setId (long id) {
		this.id = id;
	}
	
	/****************************************************************************/
	
	public String toString() {
		return "Denuncia [id="
				+ id
				+ ", nombre = "
				+ nomDenunciante
				+ ", direccion = "
				+ dirDenunciante
				+ ", telefono = "
				+ tlfDenunciante
				+ ", email = "
				+ emailDenunciante
				+ ", comentario = "
				+ comentarios
				+ ", tipo = "
				+ tipo
				+ ", foto = "
				+ foto.toString()
				+ " ]";
	}
}