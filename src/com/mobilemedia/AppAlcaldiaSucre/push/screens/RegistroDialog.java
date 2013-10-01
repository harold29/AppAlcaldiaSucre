package com.mobilemedia.AppAlcaldiaSucre.push.screens;

import java.util.Vector;

import com.mobilemedia.AppAlcaldiaSucre.objetos.ListaObjPersistentes;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Usuario;
import com.mobilemedia.AppAlcaldiaSucre.objetos.Categoria;

import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * Solicita Datos al Usuario
 */
public class RegistroDialog extends Dialog 
{
	/** Se usa el Patron de Diseño: Singleton */
	private static RegistroDialog instance;
	
	public static RegistroDialog getInstance(Usuario u){
		if (instance == null) 
			instance = new RegistroDialog();
		
		cargarInformacion(u);
		return instance;
	}
	/** FIN -- Se usa el Patron de Diseño: Singleton */

    private static AutoTextEditField correo;
    private static DateField fechaNac;
    private static CheckboxField[] checkCategorias;
	private static ObjectChoiceField estado, genero;
	private static VerticalFieldManager vfmCategorias;
	private static SeparatorField separador1, separador2, separador3;

	private static String[] listaEstados = { "-------",
		"Amazonas", "Anzoátegui", "Apure", "Aragua",
		"Barinas", "Bolívar",
		"Carabobo", "Cojedes", "Delta Amacuro", "Distrito Federal",
		"Falcón",
		"Guárico",
		"Lara",
		"Mérida", "Miranda", "Monagas",
		"Nueva Esparta",
		"Portuguesa",
		"Sucre",
		"Táchira",
		"Vargas",
		"Yaracuy",
		"Zulia" };
	private static String[] listaGenero = { "-------",
		"Masculino", "Femenino" };

	private static ListaObjPersistentes objPersistente;
	// com.mobilemedia.AppAlcaldiaSucre.Categorias
	private static final long ID = 0x515362faf440cd58L;

    public RegistroDialog( ) {
        super( Dialog.D_OK_CANCEL, "Ingrese sus Datos", Dialog.OK, null, Dialog.GLOBAL_STATUS );

        correo 		= new AutoTextEditField( "Correo Electrónico: ", 
                                            "", 50, 
                                            AutoTextEditField.AUTOCAP_OFF | 
                                            AutoTextEditField.AUTOREPLACE_OFF );
        fechaNac 	= new DateField("Fecha Nac.: ", System.currentTimeMillis(), DateField.DATE);
        
		estado 		= new ObjectChoiceField("Estado:", listaEstados, 0 );
		
		genero 		= new ObjectChoiceField("Sexo:", listaGenero, 0 );
		
		vfmCategorias = new VerticalFieldManager();
		
		separador1 = new SeparatorField();
		separador1.setPadding(5, 1, 5, 1);
		separador2 = new SeparatorField();
		separador2.setPadding(5, 1, 5, 1);
		separador3 = new SeparatorField();
		separador3.setPadding(5, 1, 5, 1);

		add ( separador1 );
		add( correo );
        add( fechaNac );
        add( genero );
        add( estado );
        add( vfmCategorias );
        add ( separador2 );
    }

	private static void cargarInformacion(Usuario usuario) {
        // El objeto debe existir, pues se crea estaticamente al iniciar la aplicacion.
        // Se trae de memoria
        objPersistente = (ListaObjPersistentes) PersistentStore.getPersistentObject(ID).getContents();
        // Lista de Objs Categoria, que estaban en memoria
        Vector categorias = ( objPersistente == null ? new Vector() : objPersistente.getListaObj() );

        int numCategorias = categorias.size();

        correo.setText( usuario.getCorreo() );
        fechaNac.setDate( usuario.getFechaNac() == 0 ? System.currentTimeMillis() : usuario.getFechaNac() );
        genero.setSelectedIndex( usuario.getGenero() );
        estado.setSelectedIndex( getIndexEstado( usuario.getEstado() ) );
        checkCategorias = new CheckboxField[ numCategorias ];
        vfmCategorias.deleteAll();

        if ( numCategorias > 0 ){
        	vfmCategorias.add ( separador3 );
        	vfmCategorias.add( new LabelField("Categorias: ") );
			for (int i = 0; i < numCategorias; i++){
				checkCategorias[i] = 
					new CheckboxField( ((Categoria) categorias.elementAt(i)).getNombre(), false );
					vfmCategorias.add( checkCategorias[i] );
			}
        }
	}

	private static int getIndexEstado(String edo) {
		int indice = 0;
		
		if ( edo != null && !edo.equals("") )
			for (indice = 1; indice < listaEstados.length; indice ++)
				if ( listaEstados[indice].equals(edo) ) break;
		
		return indice;
	}

	public String getCorreo() {
		return correo.getText();
	}

	public long getFechaNac() {
		return fechaNac.getDate();
	}

	public int getGenero() {
		return genero.getSelectedIndex();
	}

	public String getEstado() {
		return listaEstados[ estado.getSelectedIndex() ];
	}
	
	public String[] getZonas(){
		String[] zonasSeleccionadas;
		Vector indices = new Vector();
		int numZonas = checkCategorias.length;
		
		for (int i = 0; i < numZonas; i++)
			if ( checkCategorias[i].getChecked() )
				indices.addElement( new Integer(i) );
		
		zonasSeleccionadas = new String[ indices.size() ];
		
		for (int i = 0; i < indices.size(); i++ )
			zonasSeleccionadas[i] = checkCategorias[ ( (Integer) indices.elementAt(i) )
			                                         .intValue() ].getLabel();
		
		return zonasSeleccionadas;
	}
	
	public boolean esValido(){
		int indexAt = correo.getText().indexOf('@');
		int indexDot = correo.getText().indexOf('.', indexAt);
		int numCategorias = checkCategorias.length;
		long ahora = System.currentTimeMillis();
		long year = Long.parseLong("31556952000"); // 1 año
		
		if (correo.getText().length() == 0 || indexAt == -1 || indexDot == -1)
			Dialog.alert("Correo no válido");
		
		else if (fechaNac.getDate() <= (ahora - (120 * year)) ||  
				 fechaNac.getDate() >= (ahora - (  4 * year)) )
			Dialog.alert("Fecha de nacimiento no válida");

		else if (estado.getSelectedIndex() == 0 )
				Dialog.alert("Estado no válido");
		else if (genero.getSelectedIndex() == 0 )
			Dialog.alert("Sexo no válido");
		else{
			if ( numCategorias > 0 ){
				boolean seleccionado = false;

				for (int i = 0; i < numCategorias && !seleccionado; i++)
					seleccionado = checkCategorias[i].getChecked();
				
				if (!seleccionado)
					Dialog.alert("Seleccione al menos una Categoría");
				else 
					return true;
			}else
				return true;
		}

		return false;
	}
}