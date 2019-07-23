package support.ussd.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tigo.utils.SysMessage;

import micrium.aes.AlgoritmoAES;
import servicios.Result;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.ParametroBL;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.MailBL;
import support.ussd.business.ServiciosBL;
import support.ussd.business.TAbmBL;
import support.ussd.model.Adjunto;
import support.ussd.model.TABMS;
import support.ussd.model.TAbm;
import support.ussd.model.TAbmAdm;
import support.ussd.model.TAbmSer;
import support.ussd.model.TSerAdm;
import support.ussd.model.TServicio;
import support.ussd.model.TServicioABM;
import support.ussd.model.TUsuarioAtributo;
import support.util.Code;
import support.ws.servicios.ServiciosI;

@ManagedBean
@ViewScoped
public class InterpretarBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(InterpretarBean.class);
	ControlPrivilegio controlPrivilegio;
	

	@Inject
	private ControlerBitacora controlerBitacora;
	
	
	private String observacion;
	
	@Inject
	ParametroBL controlParametro;

	
	private String nroABM;
	private String fecha;
	char[] car;
	char[] resultado;
	int contRes;
	int pos;
	
	private static final Gson gson= new Gson();


	@PostConstruct
	public void init() {
		try {
			nroABM="";
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			observacion="";
			fecha="";
			
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	
	

	
	
	
	
	
	
	
	public void buscar(){
		
	
		try {
			nroABM=nroABM.trim();
			if (nroABM.isEmpty()){
				SysMessage.warn("No se permite espacio en campo cuenta");
				return;
			}
			fecha=fecha.trim();
			if (fecha.isEmpty()){
				SysMessage.warn("No se permite espacio en campo fecha");
				return;
			}
			ServiciosI ser= new ServiciosI();
			Result res=ser.obtenerComandosCuenta(nroABM, fecha);
			
			if(!res.getCode().equals(Code.OK)){
				SysMessage.error("Error al obtener los comando con error de la cuenta "+nroABM);
				log.error("[Obtener Comando con ERROR]ERROR: "+res.getDescription());
				return;
			}
			observacion=(String) res.getData();
//			System.out.println(a);
//			car=a.toCharArray();
//			pos=0;
//			resultado= new char[car.length];
//			contRes=0;
//			esCorcheteAbierto();
//			observacion=resultado.toString();
			
	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Obtener Comandos:Buscar] Error al obtener los comandos con error de la cuenta: "+nroABM+" ",e);
			SysMessage.error("Error al obtener los comandos con error");
		}
	}
	
	public boolean eof(){
		return (pos<car.length)?false:true;
	}
	
	public void esCorcheteAbierto(){
		if(!eof()){
			if(car[pos]=='[') {
				pos=pos+1;
				esC();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
		
				
	}
	public void esC(){
		if(!eof()){
			if(car[pos]=='C') {
				pos=pos+1;
				esO();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esO(){
		if(!eof()){
			if(car[pos]=='O') {
				pos=pos+1;
				esM();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esM(){
		if(!eof()){
			if(car[pos]=='M') {
				pos=pos+1;
				esM2();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esM2(){
		if(!eof()){
			if(car[pos]=='M') {
				pos=pos+1;
				esA();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esA(){
		if(!eof()){
			if(car[pos]=='A') {
				pos=pos+1;
				esN();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esN(){
		if(!eof()){
			if(car[pos]=='N') {
				pos=pos+1;
				esD();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esD(){
		if(!eof()){
			if(car[pos]=='D') {
				pos=pos+1;
				esCorcheteCerrado();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esCorcheteCerrado(){
		if(!eof()){
			if(car[pos]==']') {
				pos=pos+1;
				esSaltoLinea();
			}else {
				pos=pos+1;
				esCorcheteAbierto();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esSaltoLinea(){
		if(!eof()){
			if(car[pos]=='\n') {
				pos=pos-1;
				escoma();
			}else {
				pos=pos+1;
				esSaltoLinea();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void escoma(){
		if(!eof()){
			if(car[pos]==',') {
				pos=pos+1;
				resultado[contRes]=car[pos];
				contRes=contRes+1;
				guardarHastaSalto();
			}else {
				pos=pos-1;
				escoma();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void guardarHastaSalto(){
		if(!eof()){
			if(car[pos]!='\n') {
				pos=pos+1;
				resultado[contRes]=car[pos];
				contRes=contRes+1;
				guardarHastaSalto();
			}else {
				resultado[contRes]='\n';
				contRes=contRes+1;
				pos=pos+1;
				esE();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esE(){
		if(!eof()){
			if(car[pos]=='E') {
				pos=pos+1;
				esRor();
			}else {
				pos=pos+1;
				esE();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
		}
				
	}
	public void esRor(){
		if(!eof()){
			if(car[pos]=='R' ||car[pos]=='r') {
				pos=pos+1;
				esRor();
			}else {
				pos=pos+1;
				esE();
				
			}
		}else{
			System.out.println("Finalizo la cinta");
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


	

	public String getNroABM() {
		return nroABM;
	}




	public void setNroABM(String nroABM) {
		this.nroABM = nroABM;
	}




	


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.INTERPRETAR, idAccion);
	}


	


	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}











	public String getFecha() {
		return fecha;
	}











	public void setFecha(String fecha) {
		this.fecha = fecha;
	}






	


	
	
}
