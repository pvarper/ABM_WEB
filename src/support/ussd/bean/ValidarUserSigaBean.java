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
import support.user.sys.P;

import com.tigo.utils.SysMessage;

import servicios.Result;
import support.ws.servicios.*;
@ManagedBean
@ViewScoped
public class ValidarUserSigaBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ValidarUserSigaBean.class);
	ControlPrivilegio controlPrivilegio;

	@Inject
	private ControlerBitacora controlerBitacora;
	public String user;


	

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	private String ValidarUser(String user){ 	
		String repuesta="El campo usuario es obligatorio";
		if (user!=""){
			repuesta="OK";
		}
		return repuesta;
	}

	public void ConsultarUserSiga(){
		String respuesta=ValidarUser(getUser());
		
		if (!respuesta.equals("OK")){			
			SysMessage.warn(respuesta);
			return;
		}
		
		try {
			ServiciosI s= new ServiciosI();
			Result r=s.verificarUserSiga(this.user.toUpperCase());
			
			if (!r.getCode().equals("000")){
				SysMessage.error(r.getDescription());
				return;
			}
			boolean dato=(Boolean) r.getData();
			if (dato){
				
				SysMessage.info("El usuario existe en SIGA");
				}
			else
				SysMessage.info("El usuario no existe en SIGA");
			controlerBitacora.accion(DescriptorBitacora.VALIDARSIGA, "Se consultó el usuario: "+this.user.toUpperCase() );
			
		} catch (Exception e) {
			log.error("Error al ejecutar el metodo",e);
			SysMessage.error("Error al ejecutar el metodo");
		}
		
	}
	
	public String getMENU_TYPE() {
		return "menu";
	}

	public String getFORM_TYPE() {
		return "formulario";
	}

	public String getACTION_TYPE() {
		return "accion";
	}

	public boolean isCasdadeDelete() {
		return ((Boolean) P.getParamVal(Integer.valueOf(14))).booleanValue();
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.VALIDAR_USER_SIGA, idAccion);
	}


	
}
