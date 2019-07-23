package support.ussd.bean;



import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.util.Code;
import servicios.Result;
import support.ws.servicios.ServiciosI;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ObtenerDatosAR implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ObtenerDatosAR.class);
	ControlPrivilegio controlPrivilegio;


	@Inject
	private ControlerBitacora controlerBitacora;

	private String apellido;
	private String radioSeleccionado;
	
	@PostConstruct
	public void init() {
		try {
			
			apellido="";
			radioSeleccionado="1";
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	
	public String getRadioSeleccionado() {
		return radioSeleccionado;
	}


	public void setRadioSeleccionado(String radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}
	
	
	public void obtenerApellidos()   {	
		
	}
	
	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.ODECO_DIGITAL, idAccion);
	}
	
}
