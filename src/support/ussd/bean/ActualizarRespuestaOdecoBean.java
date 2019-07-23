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
public class ActualizarRespuestaOdecoBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ActualizarRespuestaOdecoBean.class);
	ControlPrivilegio controlPrivilegio;


	@Inject
	private ControlerBitacora controlerBitacora;

	private String serie;
	private String radioSeleccionado;
	
	@PostConstruct
	public void init() {
		try {
			
			serie="";
			radioSeleccionado="1";
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}


	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}

	
	public String getRadioSeleccionado() {
		return radioSeleccionado;
	}


	public void setRadioSeleccionado(String radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}
	
	
	public void guardarCambioEstado()   {	
		try {
			
			log.info("Se va cambiar el estado del odeco "+serie+" a estado "+radioSeleccionado);
			ServiciosI a = new ServiciosI();		
			Result res= a.cambioEstadoOdeco(serie,radioSeleccionado);
			if(res.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.info(res.getDescription());
				log.info(res.getDescription());
				controlerBitacora.accion(DescriptorBitacora.ODECO, "Se cambio el odeco "+serie+" a estado "+radioSeleccionado);
			}else{
				SysMessage.warn(res.getDescription());
				log.warn(res.getDescription());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("Error al actualizar el odeco "+serie);
			log.error("Error al actualiza rl odeco "+serie,e);
		}
		
						
		
	}
	
	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.ODECO_DIGITAL, idAccion);
	}
	
}
