package support.ussd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import micrium.aes.AlgoritmoAES;

import org.apache.log4j.Logger;

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.ParametroBL;
import support.user.controler.ControlPrivilegio;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import cliente.ws.ClienteWS;
import cliente.ws.Response;

import com.tigo.utils.ParametersWeb;

@ManagedBean
@ViewScoped
public class PortabilidadBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(PortabilidadBean.class);
	ControlPrivilegio controlPrivilegio;

	@Inject
	private ControlerBitacora controlerBitacora;
	@Inject
	ParametroBL controlParametro;

	AlgoritmoAES aes;

	private String nroPortado;
	private String nroTemporal;
	private String Iccd;
	private String fechaBdpn;
	private String textArea;
	private String fechaHabilitacion;
	
	public List<String> listaPasos;

	@PostConstruct
	public void init() {
		try {
			aes = new AlgoritmoAES();
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();

		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	public void cargarPasos() {
		listaPasos= new ArrayList<String>(0);
		listaPasos.add("");
		
	}

	public void procesar() {
		textArea="";
		try {
			
			cargarTextArea("Se va eliminar el Numero Portado de BCCS "+nroPortado);
			log.info("[Procesar]Se va eliminar el Numero Portado de BCCS "+nroPortado);
			if(!consumirWS(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_BCCS), Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_BCCS_REQUEST)))			
				return;
			
			sleep(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_BCCS_SLEEP));
			
			cargarTextArea("Se va eliminar el Numero Portado de SVA "+nroPortado);
			log.info("[Procesar]Se va eliminar el Numero Portado de SVA "+nroPortado);
			if(!consumirWS(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_SVA), Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_SVA_REQUEST)))
				return;
			
			sleep(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_SVA_SLEEP));
			
			cargarTextArea("Se va quitar la cuenta "+nroPortado);
			log.info("[Procesar]Se va quitar la cuenta "+nroPortado);
			if(!consumirWS(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_QUITAR_CUENTA),Long.valueOf( ParametersWeb.WS_PORTABILIDAD_QUITAR_CUENTA_REQUEST)))
				return;
			
			sleep(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_QUITAR_CUENTA_SLEEP));
			
			log.info("[Procesar] Finalizo el proceso");
			
		} catch (Exception e) {
			e.printStackTrace();
			cargarTextArea("Excepcion generada al procesar "+e.getMessage());
			log.error("[procesar] Excepcion generada al procesar",e);
		}
	

	}
	
	private void cargarTextArea(String accion){
		textArea=textArea.concat(obtenerFecha());
		textArea=textArea.concat(accion);
		textArea=textArea.concat("<br />");		
	}

	private String obtenerFecha() {
		
		Calendar fecha = Calendar.getInstance();
		//Calendar fecha = new GregorianCalendar();
		return "[" + fecha.get(Calendar.DATE) + "/" + fecha.get(Calendar.MONTH) + "/"
				+ fecha.get(Calendar.YEAR)  + " " + fecha.get(Calendar.HOUR)  + ":"
				+ fecha.get(Calendar.MINUTE)  + ":" + fecha.get(Calendar.SECOND) + ":" + fecha.get(Calendar.MILLISECOND)  + "]";

	}
	
	private boolean validate(long idParametro){
		return controlParametro.getActiveWsPortabilidad(idParametro);
		
	}
	
	private boolean consumirWS(long idParametro,long idRequest){
		boolean respuesta=true;
		try {
			ClienteWS ws= new ClienteWS();
			
			String webService=aes.desencriptar(controlParametro.getWsPortabilidad(idParametro));
			String request=aes.desencriptar(controlParametro.getWsPortabilidad(idRequest));
			
			log.info("[consumirWS] webService: "+webService);
			log.info("[consumirWS] request: "+request);
			if(validate(Long.valueOf(ParametersWeb.WS_PORTABILIDAD_DELETE_BCCS_ACTIVE))){
				
				cargarTextArea("Se va consumir el WS: "+webService);	
				Response OUTPUT = ws.consumir(webService, replaceParametro(request));
				cargarTextArea("Se consumio el Web Service con codigo de respuesta: "+OUTPUT.getCodeResponse()+" y tiempo de respuesta "+OUTPUT.getTimeResponse());
				if(OUTPUT.getCodeResponse()!=200)
				cargarTextArea("Response "+OUTPUT.getResponse());
				
				log.info("[consumirWS]Se consumio el Web Service: "+webService+" con codigo de respuesta: "+OUTPUT.getCodeResponse()+" y tiempo de respuesta "+OUTPUT.getTimeResponse());			
				log.info("[consumirWS] Response: "+OUTPUT.getResponse());
				
			}else{
				cargarTextArea("El Ws: "+webService +" esta deshabilitado");
				log.info("[consumirWS]El Ws: "+webService +" esta deshabilitado");
				
			}
			
			return respuesta;
			
		} catch (Exception e) {
			e.printStackTrace();
			cargarTextArea("excepcion generada al consumir el WS "+e.getMessage());
			log.error("[consumirWS] excepcion generada al consumir el WS",e);
			respuesta=false;
			return respuesta;
		}
	
	}
	
	public String replaceParametro(String request){
		request=request.replace("{nroPortado}", nroPortado);
		request=request.replace("{nrotemporal}", nroTemporal);
		request=request.replace("{iccid}", Iccd);
		return request;
		
		
	}
	
	public void sleep(long idParametro){
		try {
			Double sleepTime=controlParametro.getTimeOutWsPortabilidad(idParametro);
			log.info("[consumirWS]Aplicando Sleep de: "+sleepTime.longValue());
			cargarTextArea("Aplicando Sleep del WS de: "+sleepTime.longValue());
			Thread.sleep(sleepTime.longValue());
		} catch (Exception e) {
			log.error("Error al aplicar sleep",e);
			cargarTextArea("Error al aplicar el sleep: "+e.getMessage());
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

	public String getNroPortado() {
		return nroPortado;
	}

	public void setNroPortado(String nroPortado) {
		this.nroPortado = nroPortado;
	}

	public String getNroTemporal() {
		return nroTemporal;
	}

	public void setNroTemporal(String nroTemporal) {
		this.nroTemporal = nroTemporal;
	}

	public String getIccd() {
		return Iccd;
	}

	public void setIccd(String iccd) {
		Iccd = iccd;
	}

	public String getTextArea() {
		return textArea;
	}

	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(
				FormularioID.ERROR_PORTABILIDAD, idAccion);
	}


	

}
