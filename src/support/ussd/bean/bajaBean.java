package support.ussd.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import servicios.Result;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.ActiveDirectory;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.PlataformaBL;
import support.ussd.business.RegistroBL;
import support.ussd.model.TPlataforma;
import support.ussd.model.TUsuarioAtributo;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class bajaBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(bajaBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private PlataformaBL platBL;
	@Inject
	private ControlerBitacora controlerBitacora;
	private List<TPlataforma> listPlat;
	@Inject
	private RegistroBL regBL;
	//private List<TRol> listRol;
	//private TRol det;
	private String[] selectedPlataforma; //lista de plataformas chekeadas
	private List<String> listaUsuarios;//lista de usuarios q sale del campo usuario
	private String usuario;//input text usuario
	private List<SelectItem> selectItemPlataforma;//lista de plataformas
//	private List<SelectItem> selectItemAtributosPlataforma;//lista de atributos de una plataforma
//	private List<SelectItem> selectPlataformaRol;//lista de roles de una plataforma chekeada
//	private HashMap<Long, Boolean> listaRendered;//esta lista contiene boolean si la plataforma fue tickeada
//	private HashMap<Long, Boolean> listaRenderedAtributo;//esta lista contiene boolean si la plataforma fue tickeada 
	//y contiene atributos extras
//	private HashMap<Long,String> listaRolesPlataforma;//lista de rol seleccionado de una plataforma chekeada
//	private String selectRolSeleccionado;//rol seleccionado
//	private boolean radioSeleccionado;//usuario windows o bccs
	private boolean radioMasivo;//masivo o simple
	private List<String> lineasArchivo;//usuario del archivo subido
	private boolean radioBuscar;
	private String buscarnombre;
	private String formato;

	
	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			usuario="";
			//this.listPlat = this.platBL.getListBajas();
			lineasArchivo= new ArrayList<String>();
			formato="Usuario";
			radioBuscar=true;
			fillListasSeleccionables();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	private void fillListasSeleccionables() {
		//cargar las plataformas
		
		
		selectItemPlataforma= new ArrayList<SelectItem>();

		List<TPlataforma> list2 = null;
		try {
			list2 = platBL.getListBajas();
			for (TPlataforma item : list2) {
				SelectItem s = new SelectItem(item.getId(),item.getNombre());
				HashMap<Long, Boolean> t= new HashMap<Long, Boolean>();
				t.put(item.getId(), true);
				selectItemPlataforma.add(s);
			}
			log.debug("Carga de Lista de Plataformas [ok]");
		} catch (Exception e) {
			log.error("Carga de Lista de Plataformas [fail]", e);
			SysMessage.error("Error al cargas la lista de plataformas");
		}
	}
	

	public void handleFileUpload(FileUploadEvent event) {
        
        try {
        	
        	
        	InputStreamReader fr =  new InputStreamReader(event.getFile().getInputstream(),"UTF-8");
        	BufferedReader br = new BufferedReader(fr);
        	String linea;
            while ((linea = br.readLine()) != null) {
            	lineasArchivo.add(linea);
//                SysMessage.info(linea);
            }
            fr.close();
            br.close();
            log.debug("se cargo el archivo masivo correctamente"); 	
			 SysMessage.info("subio el archivo: "+event.getFile().getFileName());
            
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al cargar el archivo",e);
			SysMessage.error("Error al cargar archivo");
		}
    }
	
	
	public void buscarPorNombre(){
		try {
			if(!radioBuscar){
				TUsuarioAtributo name = ActiveDirectory.obtenerUsuarioActiveDirectoryEH(buscarnombre);
				if(name ==null){
					SysMessage.warn("El usuario no existe en TIGOBO");
					return;
				}
				buscarnombre=name.getUsuario();	
			}else{
				
				TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoTerminalEH(buscarnombre);
				if(name ==null){
					SysMessage.warn("El usuario no existe en TSBOLIVIA");
					return;
				}
				
				buscarnombre=name.getUsuario();	
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al obtener el usuario");
		}
		
		
	}
	
	public void eliminar() {
		

			if(selectedPlataforma.length==0){
				SysMessage.warn("debe seleccionar al menos una plataforma");
				return;
			}
			
		

		
		if(!radioMasivo){//es masivo
				try {
					if(lineasArchivo.isEmpty()){
						SysMessage.warn("debe cargar un archivo");
						return;
					}
					
						for (String us : lineasArchivo) {
							for (String pla : selectedPlataforma) {	
								TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
					    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaWebSubsidio(us.trim());
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma SUBSIDIO");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaActivador(us.trim());
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma ACTIVADOR");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaLucky(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma LUCKY NUMBERS");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaDfp(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma DATOS FACTURACION");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("CRM SIEBEL")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaCrm(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma CRM SIEBEL");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("AS")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaAs(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma AS");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("BCCS")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaBccs(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA",us, platforma.getNombre(),"", "OK",  "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma BCCS");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
							    	res=ws.bajaWimax(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", us, "BCCS-WIMAX","", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma BCCS-WIMAX");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("TERMINAL SERVER")){
					    			TUsuarioAtributo u=ActiveDirectory.obtenerNombreCompletoTerminalEH(us);
					    			if(u==null){
					    				regBL.save("BAJA", us, "TERMINAL SERVER","", "ERROR",
												"El usuario no existe");
										log.error("BAJA TERMINAL ERROR: El usuario no existe");
					    			}else{
					    			support.util.Result r=ActiveDirectory.baja(u.getNombre());
									if(r.getCode().equalsIgnoreCase(Code.OK)){
										regBL.save("BAJA", us, "TERMINAL SERVER","", "OK", "");
										this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de BAJA el usuario: "+us+" en plataforma TERMINAL SERVER");
										log.info("alta BCCS:"+r.getDescription());
									}else{
										
										regBL.save("BAJA", us, "TERMINAL SERVER","", "ERROR",
												"Error al dar del BAJA, consulte logs");
										log.error("BAJA TERMINAL ERROR: "+r.getDescription());
									}
					    			}
					    		}
							}			
						}
						    
						

				SysMessage.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
				lineasArchivo.clear();
				   //this.visibleNuevoEditar = false;
				   //newplauerimiento();
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[Guardar] Error al procesar los usuarios",e);
					SysMessage.error("Error al procesar");
					return;
				}	

		}else{//simple
				try {
					listaUsuarios = new ArrayList<String>();

					StringTokenizer tokens = new StringTokenizer(usuario.trim(),",");
					while(tokens.hasMoreTokens()){
						listaUsuarios.add(tokens.nextToken());
					}
					
					
					for (String us : listaUsuarios) {						
					    	for (String pla : selectedPlataforma) {	
					    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
					    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
					    			ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaWebSubsidio(us.trim());
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma SUBSIDIO");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
								if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){			
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaActivador(us.trim());
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma ACTIVADOR");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}		    			
								}							
								if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){									
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaLucky(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma LUCKY NUMBERS");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
								}
								if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaDfp(us);
							    
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma DATOS FACTURACION");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
								}
								
								if(platforma.getNombre().equalsIgnoreCase("CRM SIEBEL")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaCrm(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", res.getData()+"", platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma CRM SIEBEL");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
								if(platforma.getNombre().equalsIgnoreCase("AS")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaAs(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma CRM SIEBEL");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("BCCS")){
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.bajaBccs(us);
							    	
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma BCCS");
							    	}else{
							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
							    	}
//							    	res=ws.bajaWimax(us);
//							    	
//							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
//							    		regBL.save("BAJA", us, "BCCS-WIMAX","", "OK", "");
//							    		this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de baja el usuario: "+us+" en plataforma BCCS-WIMAX");
//							    	}else{
//							    		regBL.save("BAJA", us, platforma.getNombre(),"", "ERROR", res.getDescription());
//							    	}
					    		}
					    		if(platforma.getNombre().equalsIgnoreCase("TERMINAL SERVER")){
					    			TUsuarioAtributo u=ActiveDirectory.obtenerNombreCompletoTerminalEH(us);
					    			if(u==null){
					    				regBL.save("BAJA", us, "TERMINAL SERVER","", "ERROR",
												"El usuario no existe");
										log.error("BAJA TERMINAL ERROR: El usuario no existe");
					    			}else{
					    			support.util.Result r=ActiveDirectory.baja(u.getNombre());
									if(r.getCode().equalsIgnoreCase(Code.OK)){
										regBL.save("BAJA", us, "TERMINAL SERVER","", "OK", "");
										this.controlerBitacora.accion(DescriptorBitacora.BAJA,"Se dio de BAJA el usuario: "+us+" en plataforma TERMINAL SERVER");
										log.info("alta BCCS:"+r.getDescription());
									}else{
										
										regBL.save("BAJA", us, "TERMINAL SERVER","", "ERROR",
												"Error al dar del BAJA, consulte logs");
										log.error("BAJA TERMINAL ERROR: "+r.getDescription());
									}
					    			}
					    		}
					    	} 
					}
				   SysMessage.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
				   lineasArchivo.clear();
				   //this.visibleNuevoEditar = false;
				  // newplauerimiento();
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[Guardar] Error al procesar los usuarios",e);
					SysMessage.error("Error al procesar");
					return;
				}
			
		}

	}

//	public void add() throws NumberFormatException, Exception {
//		this.det = new TRol();
//		this.editDetalle=false;
//		this.visibleNuevoEditarDetalle = true;
//	}

//	public void guardarDetalle() {
//
//		try {
//			if(det.getIdrol()==0){
//				SysMessage.warn("El id rol no puede ser cero");
//				return ;
//			}
//			
//		
//			if (!this.editDetalle) {
//				det.setTPlataforma(pla);
//				det.setEstado(true);
//				this.listRol.add(this.det);
//				this.visibleNuevoEditarDetalle = false;
//			} else {
//				for (TRol a : this.listRol) {
//					
//					if(detId.equals("null")){
//						if (a.getNombre().equals(det.getNombre())) {
//							a.setTPlataforma(pla);
//							a.setNombre(det.getNombre());
//							a.setDescripcion(det.getDescripcion());	
//							a.setEstado(det.getEstado());
//						}
//					}else{
//						if (a.getId().longValue() == Long.parseLong(this.detId)) {
//							a.setTPlataforma(pla);
//							a.setNombre(det.getNombre());
//							a.setDescripcion(det.getDescripcion());	
//							a.setEstado(det.getEstado());
//						}
//					}
//					
//					
//				}
//				this.visibleNuevoEditarDetalle = false;
//			}
//		} catch (Exception localException) {
//			localException.printStackTrace();
//		}
//	}
//
//	public String deleteDetalle() {
//		if (this.det != null) {
//			try {
//				int pos = 0;
//				int r = 0;
//				for (TRol a : this.listRol) {
//					if (a.getId() == this.det.getId()) {
//						r = pos;
//						SysMessage.info("Se eliminó correctamente.");
//					}
//					pos++;
//				}
//				this.listRol.remove(r);
//			} catch (Exception e) {
//				log.error("[deletePlataforma]  error al eliminar el rol"
//						+ this.pla.getId() + "  " + e);
//				e.printStackTrace();
//				SysMessage.error("Fallo al eliminar.");
//			}
//		} else {
//			log.warn("[eliminar] No se encontró ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//		return "";
//	}
//
//	public String delete() {
//		if (this.pla != null) {
//			try {
//				//this.pla.setBaja(Boolean.valueOf(false));
//				this.platBL.delete(this.pla);
//				this.controlerBitacora.delete(DescriptorBitacora.PLATAFORMA,
//						this.pla.getId() + "", "");
//				this.listPlat = this.platBL.getList();
//				SysMessage.info("Se eliminó correctamente.");
//			} catch (Exception e) {
//				log.error("[deletePlataforma]  error al eliminar el plauerimiento"
//						+ this.pla.getId() + "  " + e);
//				e.printStackTrace();
//				SysMessage.error("Fallo al eliminar.");
//			}
//		} else {
//			log.warn("[eliminar] No se encontro ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//		return "";
//	}
//
//	public String deleteDetalle(TRol plau) {
//		this.det = plau;
//		return deleteDetalle();
//	}

//	public String delete(TPlataforma plau) {
//		this.pla = plau;
//		return delete();
//	}

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

	public void newplauerimiento() throws Exception {

		lineasArchivo= new ArrayList<String>();

		radioMasivo=false;
		selectedPlataforma= new String[0];
		this.listPlat = this.platBL.getList();
		usuario="";
		formato="Usuario";
		fillListasSeleccionables();
		
	}
	public String formato(){
		if(!radioMasivo){
			formato="Usuario\nUsuario\nUsuario";
		}else{
			formato="Usuario,Usuario,Usuario";
		}
		System.out.println(formato);
		return formato;
	}


	public List<TPlataforma> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TPlataforma> listPlat) {
		this.listPlat = listPlat;
	}



	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_ALTA, idAccion);
	}



	public String[] getSelectedPlataforma() {
		return selectedPlataforma;
	}



	public void setSelectedPlataforma(String[] selectedPlataforma) {
		this.selectedPlataforma = selectedPlataforma;
	}



	public List<SelectItem> getSelectItemPlataforma() {
		return selectItemPlataforma;
	}

	public void setSelectItemPlataforma(List<SelectItem> selectItemPlataforma) {
		this.selectItemPlataforma = selectItemPlataforma;
	}
	
	public boolean isRadioMasivo() {
		return radioMasivo;
	}

	public void setRadioMasivo(boolean radioMasivo) {
		this.radioMasivo = radioMasivo;
	}

	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public boolean isRadioBuscar() {
		return radioBuscar;
	}

	public void setRadioBuscar(boolean radioBuscar) {
		this.radioBuscar = radioBuscar;
	}

	public String getBuscarnombre() {
		return buscarnombre;
	}

	public void setBuscarnombre(String buscarnombre) {
		this.buscarnombre = buscarnombre;
	}


	

}
