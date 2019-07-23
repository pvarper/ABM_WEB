package support.ussd.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
import support.user.model.MuRol;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.PlataformaBL;
import support.ussd.business.RegistroBL;
import support.ussd.model.ActivadorParametros;
import support.ussd.model.TPlataforma;
import support.ussd.model.TUsuarioAtributo;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class AltaBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AltaBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private PlataformaBL platBL;
	@Inject
	private ControlerBitacora controlerBitacora;
	private List<TPlataforma> listPlat;
	@Inject
	private RegistroBL regBL;
//	private List<TRol> listRol;
//	private TRol det;
	private String[] selectedPlataforma; //lista de plataformas chekeadas
	private List<String> listaUsuarios;//lista de usuarios q sale del campo usuario
	private String usuario;//input text usuario
	private List<SelectItem> selectItemPlataforma;//lista de plataformas
	private List<SelectItem> selectItemAtributosPlataforma;//lista de atributos de una plataforma
	private List<String> selectPlataformaRol;//lista de roles de una plataforma chekeada
	private HashMap<Long, Boolean> listaRendered;//esta lista contiene boolean si la plataforma fue tickeada
	private HashMap<Long, Boolean> listaRenderedAtributo;//esta lista contiene boolean si la plataforma fue tickeada 
	//y contiene atributos extras
	private HashMap<Long,String> listaRolesPlataforma;//lista de rol seleccionado de una plataforma chekeada
	private String selectRolSeleccionado;//rol seleccionado
	private boolean radioSeleccionado;//usuario windows o bccs
	private boolean radioMasivo;//masivo o simple
	private List<ActivadorParametros> lineasArchivo;//usuario del archivo subido
	private List<ActivadorParametros> lineasArchivoActivador;//usuario del archivo subido para activador
	private String formato;
	
	private HashMap<Long,List<String>> listaRolesPla;

	private String[] selectedMember;
	private List<String> selectMembers;
	private boolean visiblemember;
	private boolean visibleCiudad;
	private Boolean radiofechaSeleccionado;
	private String cargo;
	private String area;
	private Date fechaExp;
	
	private List<SelectItem> selectCiudadesSubsidio;
	private String selectCiudadSubsidio;
	
	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			usuario="";
			area="";
			cargo="";
			this.listPlat = this.platBL.getListAltas();
			visiblemember=false;
			visibleCiudad=false;
			radioMasivo=true;
			listaRolesPlataforma = new HashMap<Long,String>();
			lineasArchivo= new ArrayList<ActivadorParametros>();
			lineasArchivoActivador= new ArrayList<ActivadorParametros>();
			formato="Usuario";
			listaRolesPla=new HashMap<Long, List<String>>();
			radiofechaSeleccionado=false;
			fillListasSeleccionables();
			cargarListaRolesPlataforma();
			cargarMembers();
			obtenerCiudadesSubsidio();
			
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	private void fillListasSeleccionables() {
		//cargar las plataformas
		
		
		selectItemPlataforma= new ArrayList<SelectItem>();
		listaRendered = new HashMap<Long,Boolean>();
		listaRenderedAtributo = new HashMap<Long,Boolean>();

		List<TPlataforma> list2 = null;
		try {
			list2 = platBL.getListAltas();
			for (TPlataforma item : list2) {
				SelectItem s = new SelectItem(item.getId(),item.getNombre());
				HashMap<Long, Boolean> t= new HashMap<Long, Boolean>();
				t.put(item.getId(), true);
				selectItemPlataforma.add(s);
				listaRendered.put(item.getId(), false);
				listaRenderedAtributo.put(item.getId(), false);
				listaRolesPlataforma.put(item.getId(), "-1");
				listaRolesPla.put(item.getId(), null);
			}
			log.debug("Carga de Lista de Plataformas [ok]");
		} catch (Exception e) {
			log.error("Carga de Lista de Plataformas [fail]", e);
			SysMessage.error("Error al cargas la lista de plataformas");
		}
	}
	
	private void obtenerCiudadesSubsidio() throws Exception {
		this.selectCiudadesSubsidio = new ArrayList<SelectItem>();
		this.selectCiudadesSubsidio.add(new SelectItem("-1", "-- Seleccionar Ciudad --"));
		ServiciosI ws= new ServiciosI();
		Result res = ws.obtenerCiudadesSubsidio();
		if(!res.getCode().equalsIgnoreCase(Code.OK)){
			log.error("error al obtener las ciudades de la plataforma subsidio "+res.getDescription());
			SysMessage.error("Error al cargar las ciudades");
			return;
		}
		String listCiudades=(String)res.getData();
		List<String> aux = new ArrayList<String>();

		StringTokenizer tokens = new StringTokenizer(listCiudades.trim(),",");
		while(tokens.hasMoreTokens()){
			aux.add(tokens.nextToken());
		}
		for (String role : aux) {
			res=ws.obtenerIdDptoSubsidio(role);
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				log.error("error al obtener el idDpto de la plataforma subsidio "+res.getDescription());
				SysMessage.error("Error al cargar las ciudades");
				return;
			}
			SelectItem sel = new SelectItem(res.getData(), role);
			this.selectCiudadesSubsidio.add(sel);
		}
	}
	
	public List<String> obtenerRolesPlataforma(long idPlataforma){
		return listaRolesPla.get(idPlataforma);
	}
	
	public void cargarListaRolesPlataforma(){
		for (Entry<Long, List<String>> entry : listaRolesPla.entrySet()) {
		    Long key = entry.getKey();
		    //List<String> value = entry.getValue();
		    List<String> value=cargarPlataformaRol(key);
		    listaRolesPla.put(key, value);

		}
	}
	
	public void chek() throws NumberFormatException, Exception{
		
		//actualiza los check tickeados y los combos de roles de los check tikeados
		visiblemember=false;
		visibleCiudad=false;
		radiofechaSeleccionado=false;
		for (Long key : listaRendered.keySet()) {
			listaRendered.put(key,false);
		}

		for (String a : selectedPlataforma) {
			if(a.equals("74")){
				visiblemember=true;	
			}else{				
				listaRendered.put(Long.parseLong(a),true);
				if(a.equals("1")){
					visibleCiudad=true;
				}
			}		

		}
		for (Entry<Long, Boolean> entry : listaRendered.entrySet()) {
		    Long key = entry.getKey();
		    Boolean value = entry.getValue();
		    if(!value){
		    	listaRolesPlataforma.put(key, "-1");
		    }

		}
	}
	
//	public List<TAtributo> obtenerListaAtributosPlataforma(long idPlataforma) throws Exception{
//		return platBL.getListAtributoPlataforma(idPlataforma);
//	}
	public void agregarRol(long idPlataforma){
		//guardo el rol que se selecciono de una plataforma
		listaRolesPlataforma.put(idPlataforma, selectRolSeleccionado);
		
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        
        try {
        	
        	if(selectedPlataforma==null||selectedPlataforma.length==0){
        		SysMessage.warn("Debe seleccionar al menos una plataforma para poder cargar un archivo");
        		return;
        	}
        	
        	List<String> ll= new ArrayList<String>();
        	
        	InputStreamReader fr =  new InputStreamReader(event.getFile().getInputstream(),"UTF-8");
        	BufferedReader br = new BufferedReader(fr);
        	String linea;
            while ((linea = br.readLine()) != null) {
            	ll.add(linea);
//                SysMessage.info(linea);
            }

            		for (String a : ll) {        			
    					StringTokenizer tokens = new StringTokenizer(a.trim(),",");
    					int c=0;
    					ActivadorParametros ac = new ActivadorParametros();
    					while(tokens.hasMoreTokens()){
    						if(c==0){							
    							ac.setUsuario(tokens.nextToken().trim());
    						}
    						if(c==1){
    							ac.setCi(tokens.nextToken().trim());
    						}
    						if(c==2){
    							ac.setStaff(tokens.nextToken().trim());
    						}
    						if(c==3){
    							ac.setCorreo(tokens.nextToken().trim());
    						}
    						if(c==4){
    							ac.setNombre(tokens.nextToken().trim());
    						}
    						
    						c=c+1;
    					}	
    					lineasArchivo.add(ac);
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
	
	public Boolean rendered(long id){
		return listaRendered.get(id);
	}
	
	
	public List<String> cargarPlataformaRol(long idPlataforma){
		selectPlataformaRol = new ArrayList<String>();
		
		List<MuRol> list2 = new ArrayList<MuRol>();
		try {
				
				TPlataforma platforma=platBL.getPlataforma(idPlataforma);
	    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
	    			selectPlataformaRol.add("-- Seleccione Rol --");
	    			ServiciosI ws= new ServiciosI();
					Result res=ws.obtenerRolesSubsidio();
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("error al obtener los roles de la plataforma subsidio "+res.getDescription());
						SysMessage.error("Error al cargar los roles");
						return null;
					}

					String a = (String)res.getData();
					StringTokenizer tokens = new StringTokenizer(a,",");
					long id=1;
					while(tokens.hasMoreTokens()){				
						MuRol r= new MuRol();
						r.setRolId((int)(id));
						r.setNombre(tokens.nextToken());
						list2.add(r);
						id=id+1;
					}
					for (MuRol item : list2) {
						//SelectItem s = new SelectItem(item.getId(),item.getNombre());
						selectPlataformaRol.add(item.getNombre());
						selectRolSeleccionado=listaRolesPlataforma.get(idPlataforma);
					}
					log.debug("Carga de Lista de roles de la plataforma: "+idPlataforma+" [ok]");

				
				
	    		}
	    		if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
	    			selectPlataformaRol.add("-- Seleccione Rol --");
	    			ServiciosI ws= new ServiciosI();
					Result res=ws.obtenerRolesLucky();
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("error al obtener los roles de la plataforma lucky "+res.getDescription());
						SysMessage.error("Error al cargar los roles");
						return null;
					}

					String a = (String)res.getData();
					StringTokenizer tokens = new StringTokenizer(a,",");
					long id=1;
					while(tokens.hasMoreTokens()){				
						MuRol r= new MuRol();
						r.setRolId((int)id);
						r.setNombre(tokens.nextToken());
						list2.add(r);
						id=id+1;
					}
					for (MuRol item : list2) {
						//SelectItem s = new SelectItem(item.getId(),item.getNombre());
						selectPlataformaRol.add(item.getNombre());
						selectRolSeleccionado=listaRolesPlataforma.get(idPlataforma);
					}
					log.debug("Carga de Lista de roles de la plataforma: "+idPlataforma+" [ok]");

				
				
	    		}
	    		if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
	    			selectPlataformaRol.add("-- Seleccione Rol --");
	    			ServiciosI ws= new ServiciosI();
					Result res=ws.obtenerRolesDfp();
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("error al obtener los roles de la plataforma datos facturacion "+res.getDescription());
						SysMessage.error("Error al cargar los roles");
						return null;
					}

					String a = (String)res.getData();
					StringTokenizer tokens = new StringTokenizer(a,",");
					long id=1;
					while(tokens.hasMoreTokens()){				
						MuRol r= new MuRol();
						r.setRolId((int)id);
						r.setNombre(tokens.nextToken());
						list2.add(r);
						id=id+1;
					}
					for (MuRol item : list2) {
						//SelectItem s = new SelectItem(item.getId(),item.getNombre());
						selectPlataformaRol.add(item.getNombre());
						selectRolSeleccionado=listaRolesPlataforma.get(idPlataforma);
					}
					log.debug("Carga de Lista de roles de la plataforma: "+idPlataforma+" [ok]");

				
				
	    		}
	    		if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
	    			selectPlataformaRol.add("-- Seleccione Rol --");
	    			ServiciosI ws= new ServiciosI();
					Result res=ws.obtenerRolesActivador();
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("error al obtener los roles de la plataforma ACTIVADOR "+res.getDescription());
						SysMessage.error("Error al cargar los roles");
						return null;
					}

					String a = (String)res.getData();
					StringTokenizer tokens = new StringTokenizer(a,",");
					long id=1;
					while(tokens.hasMoreTokens()){				
						MuRol r= new MuRol();
						r.setRolId((int)id);
						r.setNombre(tokens.nextToken());
						list2.add(r);
						id=id+1;
					}
					for (MuRol item : list2) {
						//SelectItem s = new SelectItem(item.getId(),item.getNombre());
						selectPlataformaRol.add(item.getNombre());
						selectRolSeleccionado=listaRolesPlataforma.get(idPlataforma);
					}
					log.debug("Carga de Lista de roles de la plataforma: "+idPlataforma+" [ok]");			
	    		}
	    		
	    		
			
	    		return selectPlataformaRol;
		} catch (Exception e) {
			log.error("Carga de Lista de roles de la plataforma: "+idPlataforma+" [fail]", e);
			SysMessage.error("Error al cargas la lista de roles de la paltaforma: "+idPlataforma+"");
			return null;
		}
		
	}
	
	public void cargarMembers(){
		try {
			support.util.Result r=ActiveDirectory.obtenerGrupos();
			if(!r.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.error("Error al cargar los memberof");
				log.error("Error al cargar los memberof "+r.getDescription());
				return;
			}
			selectMembers=(List<String>) r.getData();
			log.info("[obtenerGruposTerminal]Se recupero Grupos del terminal");
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al obtener los members de AD");
		}
			

	}
	
	public void guardar() {
		
		try {
			if(selectedPlataforma.length==0){
				SysMessage.warn("debe seleccionar al menos una plataforma");
				return;
			}
			boolean sw=false;
			for (String id : selectedPlataforma) {
				if(!id.equals("74")){

						TPlataforma p=platBL.getPlataforma(Long.valueOf(id));
						String idRol=listaRolesPlataforma.get(Long.valueOf(id));
						if(idRol==null || idRol.equals("-1")){
							SysMessage.warn("debe seleccionar un rol para la plataforma: "+p.getNombre());
							sw=true;
						}
					
				}else{
					
					
					if(selectedMember.length==0){
						SysMessage.warn("debe seleccionar al menos un MemberOf");
						sw=true;
					}
					if(cargo==null || cargo.isEmpty()){
						SysMessage.warn("debe llenar el campo cargo");
						sw=true;
					}
					if(area==null || area.isEmpty()){
						SysMessage.warn("debe llenar el campo area");
						sw=true;
					}
					if(radiofechaSeleccionado){
						if(fechaExp==null){
							SysMessage.warn("debe seleccionar una fecha de expiracion");
							return;
						}
					}
				}
				
			}
			if(sw){
				return;
			}
		
			if(visibleCiudad){
				if(selectCiudadSubsidio.equalsIgnoreCase("-1")){
					SysMessage.warn("debe seleccionar una ciudad para la plataforma subsidio");
					return;
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al guardar: "+e.getMessage());
			SysMessage.error("Error al guardar");
			return ;
		}
		
		if(!radioMasivo){//es masivo
			if(!radioSeleccionado){//es user windows	
				try {
					if(lineasArchivo.isEmpty()){
						SysMessage.warn("debe cargar un archivo");
						return;
					}
					
						for (ActivadorParametros us : lineasArchivo) {
							
							
//							TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us.getUsuario());
//						    if (name==null)
//						    {
//						     // SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
//						      regBL.save("ALTA", us.getUsuario(), "AD","", "ERROR", "No se pudo Obtener el login en AD");
//						      
//						    }else{

						    	for (String pla : selectedPlataforma) {	
						    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
						    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
						    			String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										
										ServiciosI ws= new ServiciosI();
//										Result res=ws.obtenerIdRolSubsidio(idrol);
//								    	if(!res.getCode().equalsIgnoreCase(Code.OK)){
//								    		log.error("[Guardar] error al obtener el id del rol: "+idrol+" error: "+res.getDescription());
//								    		SysMessage.error("Error al guardar");
//								    		return;
//								    	}
										TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us.getUsuario());
										if (name==null)
										{
										SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
										regBL.save("ALTA", us.getUsuario(), "AD","", "ERROR", "No se pudo Obtener el login en AD");
														      
										}else{
											Result res=ws.insertWebSubsidio(name.getUsuario(), name.getNombre(), selectCiudadSubsidio, name.getCorreo(),idrol);

									    	if(res.getCode().equalsIgnoreCase(Code.OK)){
									    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
									    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma SUBSIDIO");
									    	}else{
									    		regBL.save("ALTA", us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
									    	}
										}
										
										
						    		}
									if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
										String telf="";
//										if(name.getTelf()==null||name.getTelf().isEmpty()){
//											telf="77390000";
//										}else{
//											telf=name.getTelf();
//										}
								    	Result res=ws.insertActivador(us.getUsuario(), us.getNombre(), us.getCorreo(),us.getStaff(),us.getCi(), idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+us.getUsuario()+" en plataforma ACTIVADOR");
								    	}else{
								    		regBL.save("ALTA",us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}		    			
									}
									if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
										
										
										TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us.getUsuario());
										if (name==null)
										{
										SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
										regBL.save("ALTA", us.getUsuario(), "AD","", "ERROR", "No se pudo Obtener el login en AD");
														      
										}else{
											String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
											//TRol rol=platBL.getRol(Long.parseLong(idrol));
											ServiciosI ws= new ServiciosI();
											Result res=ws.insertLucky(idrol, name.getCorreo(), name.getUsuario());
//									    	Thread.sleep(1000);
									    	if(res.getCode().equalsIgnoreCase(Code.OK)){
									    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
									    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma LUCKY NUMBERS");
									    	}else{
									    		regBL.save("ALTA", us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
									    	}
										}
										
								    	
									}
									if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
										
										TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us.getUsuario());
										if (name==null)
										{
										SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
										regBL.save("ALTA", us.getUsuario(), "AD","", "ERROR", "No se pudo Obtener el login en AD");
														      
										}else{
											String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
											//TRol rol=platBL.getRol(Long.parseLong(idrol));
											ServiciosI ws= new ServiciosI();
									    	Result res=ws.insertDfp(name.getUsuario(), name.getNombre(), idrol);
//									    	Thread.sleep(1000);
									    	if(res.getCode().equalsIgnoreCase(Code.OK)){
									    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
									    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma DATOS FACTURACION");
									    	}else{
									    		regBL.save("ALTA", us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
									    	}
										}
										
										
									}
									if(platforma.getNombre().equalsIgnoreCase("TERMINAL SERVER")){
										
										TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us.getUsuario());
										if (name==null)
										{
										SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
										regBL.save("ALTA", us.getUsuario(), "AD","", "ERROR", "No se pudo Obtener el login en AD");
														      
										}else{
											if(radiofechaSeleccionado){
												name.setFechaExpiracion(fechaExp);
											}
											String per="";
											Boolean first = true;
											for (String as : selectedMember) {
												  if(first) {
													  per+=as.trim();
												      first=false;
												    } else {
												    	per+=","+as.trim();
												    }
											}
											name.setMemberOf(per);
											name.setCargoTerminal(cargo.trim());
											name.setAreaTerminal(area.trim());

											support.util.Result r=ActiveDirectory.guardar(name);
											if(r.getCode().equalsIgnoreCase(Code.OK)){
												regBL.save("ALTA", name.getUsuario() + "", "TERMINAL",
														cargo + "-" + area, "OK", "");
												this.controlerBitacora.accion(DescriptorBitacora.ALTATERMINAL,"Se dio de alta el usuario: "+name.getUsuario());
												log.info("alta BCCS:"+r.getDescription());
											}else{
												if(r.getDescription().indexOf("ENTRY_EXISTS")!=-1){
													regBL.save("ALTA", name.getUsuario(), "TERMINAL",
															cargo + "-" + area, "ERROR",
															"El usuario ya existe");
													log.error("alta Terminal ERROR: "+r.getDescription());
												}else{
													regBL.save("ALTA", name.getUsuario(), "TERMINAL",
															cargo + "-" + area, "ERROR",
															"Error al dar del alta, consulte logs");
													log.error("alta Terminal ERROR: "+r.getDescription());
												}
												
											}
										}
										
										
									}
									
								}
						    }
						//}
				//}

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

			}else{//usuario bccs
				try {
					if(lineasArchivo.isEmpty()){
						SysMessage.warn("debe cargar un archivo");
						return;
					}
					for (ActivadorParametros us : lineasArchivo) {
						
							TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoTerminal(us.getUsuario());
						    if (name==null)
						    {
						     // SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
						      regBL.save("ALTA", us.getUsuario(), "TS BOLIVIA","", "ERROR", "No se pudo Obtener el login en TS");
						      
						    }else{

						    	for (String pla : selectedPlataforma) {	
						    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
						    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
						    			String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										
										ServiciosI ws= new ServiciosI();
//										Result res=ws.obtenerIdRolSubsidio(idrol);
//								    	if(!res.getCode().equalsIgnoreCase(Code.OK)){
//								    		log.error("[Guardar] error al obtener el id del rol: "+idrol+" error: "+res.getDescription());
//								    		SysMessage.error("Error al guardar");
//								    		return;
//								    	}
										
								    	Result res=ws.insertWebSubsidio(name.getUsuario(), name.getNombre(), selectCiudadSubsidio, name.getCorreo(), idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma SUBSIDIO");
								    	}else{
								    		regBL.save("ALTA",us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}
						    		}
									if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
										String telf="";
										if(name.getTelf()==null||name.getTelf().isEmpty()){
											telf="77390000";
										}else{
											telf=name.getTelf();
										}
								    	Result res=ws.insertActivadorLDAP(name.getUsuario(), name.getNombre(), us.getCorreo(),us.getStaff(),us.getCi(), idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma ACTIVADOR");
								    	}else{
								    		regBL.save("ALTA", us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}		    			
									}
									if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
								    	Result res=ws.insertLucky(idrol, name.getCorreo(), name.getUsuario());
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma LUCKY NUMBERS");
								    	}else{
								    		regBL.save("ALTA",us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}
									}
									if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
								    	Result res=ws.insertDfp(name.getUsuario(), name.getNombre(),idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma DATOS FACTURACION");
								    	}else{
								    		regBL.save("ALTA", us.getUsuario(), platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}
									}
									
								}
						    }
								
					}
					SysMessage.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
					lineasArchivo.clear();
					} catch (Exception e) {
						e.printStackTrace();
						log.error("[Guardar] Error al procesar los usuarios",e);
						SysMessage.error("Error al procesar");
						return;
					}
			}
		}else{//simple
			
			if(!radioSeleccionado){//user windows
				try {
					listaUsuarios = new ArrayList<String>();

					StringTokenizer tokens = new StringTokenizer(usuario.trim(),",");
					while(tokens.hasMoreTokens()){
						listaUsuarios.add(tokens.nextToken());
					}
					
					
					for (String us : listaUsuarios) {
//						TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us);
//						
//					    if (name==null)
//					    {
//					     // SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
//					      regBL.save("ALTA", us, "AD","", "ERROR", "No se pudo Obtener el login en AD");
//					      
//					    }else{
					    	
					    	for (String pla : selectedPlataforma) {	
					    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
					    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
					    			
					    			TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us);
					    			if (name==null)
					    			{
					    					SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
					    					regBL.save("ALTA", us, "AD","", "ERROR", "No se pudo Obtener el login en AD");
					    									      
					    			}else{
						    			String idrol=listaRolesPlataforma.get(Long.parseLong(pla));									
										ServiciosI ws= new ServiciosI();
//										Result res=ws.obtenerIdRolSubsidio(idrol);
//								    	if(!res.getCode().equalsIgnoreCase(Code.OK)){
//								    		log.error("[Guardar] error al obtener el id del rol: "+idrol+" error: "+res.getDescription());
//								    		SysMessage.error("Error al guardar");
//								    		return;
//								    	}
										
								    	Result res=ws.insertWebSubsidio(name.getUsuario(), name.getNombre(), selectCiudadSubsidio, name.getCorreo(), idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma SUBSIDIO");
								    	}else{
								    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}

					    			}
					    			

					    		}
								if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
								
							     regBL.save("ALTA", us, platforma.getNombre(),"", "ERROR", "Utilizar solo la opcion masiva para registrar usuarios de windows al Activador");
							    			    			
								}							
								if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
									
									
									TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us);
									if (name==null)
									{
									SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
									regBL.save("ALTA", us, "AD","", "ERROR", "No se pudo Obtener el login en AD");
													      
									}else{
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
								    	Result res=ws.insertLucky(idrol, name.getCorreo(), name.getUsuario());
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma LUCKY NUMBERS");
								    	}else{
								    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}
									}
									
									
								}
								if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
									
									
									TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us);
									if (name==null)
									{
									SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
									regBL.save("ALTA", us, "AD","", "ERROR", "No se pudo Obtener el login en AD");
													      
									}else{
										String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
										//TRol rol=platBL.getRol(Long.parseLong(idrol));
										ServiciosI ws= new ServiciosI();
								    	Result res=ws.insertDfp(name.getUsuario(), name.getNombre(), idrol);
//								    	Thread.sleep(1000);
								    	if(res.getCode().equalsIgnoreCase(Code.OK)){
								    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
								    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma DATOS FACTURACION");
								    	}else{
								    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
								    	}
									}
									
									
								}
								
								if(platforma.getNombre().equalsIgnoreCase("TERMINAL SERVER")){
									
									TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoActiveDirectory(us);
									if (name==null)
									{
									SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
									regBL.save("ALTA", us, "AD","", "ERROR", "No se pudo Obtener el login en AD");
													      
									}else{
										if(radiofechaSeleccionado){
											name.setFechaExpiracion(fechaExp);
										}
										String per="";
										Boolean first = true;
										for (String as : selectedMember) {
											  if(first) {
												  per+=as.trim();
											      first=false;
											    } else {
											    	per+=","+as.trim();
											    }
										}
										name.setMemberOf(per);
										name.setCargoTerminal(cargo.trim());
										name.setAreaTerminal(area.trim());

										support.util.Result r=ActiveDirectory.guardar(name);
										if(r.getCode().equalsIgnoreCase(Code.OK)){
											regBL.save("ALTA", name.getUsuario() + "", "TERMINAL",
													cargo + "-" + area, "OK", "");
											this.controlerBitacora.accion(DescriptorBitacora.ALTATERMINAL,"Se dio de alta el usuario: "+name.getUsuario());
											log.info("alta BCCS:"+r.getDescription());
										}else{
											if(r.getDescription().indexOf("ENTRY_EXISTS")!=-1){
												regBL.save("ALTA", name.getUsuario(), "TERMINAL",
														cargo + "-" + area, "ERROR",
														"El usuario ya existe");
												log.error("alta Terminal ERROR: "+r.getDescription());
											}else{
												regBL.save("ALTA", name.getUsuario(), "TERMINAL",
														cargo + "-" + area, "ERROR",
														"Error al dar del alta, consulte logs");
												log.error("alta Terminal ERROR: "+r.getDescription());
											}
											
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
			}else{//usuario bccs
				try {
					
//					for (String pla : selectedPlataforma) {	
//			    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
//			    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
//			    			SysMessage.warn("Para la plataforma subsidio solo de forma masiva");
//			    			return;
//			    		}
//			    		if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
//			    			SysMessage.warn("Para la plataforma activador solo de forma masiva porque contiene atributos extras");
//			    			return;
//			    		}
//			    	}
					
					listaUsuarios = new ArrayList<String>();
					
					StringTokenizer tokens = new StringTokenizer(usuario.trim(),",");
					while(tokens.hasMoreTokens()){
						listaUsuarios.add(tokens.nextToken());
					}
					

				
					for (String us : listaUsuarios) {
						TUsuarioAtributo name = ActiveDirectory.obtenerNombreCompletoTerminal(us);						
					    if (name==null)
					    {
					     // SysMessage.error("AD: No se pudo obtener el nombre del usuario: "+us);
					      regBL.save("ALTA", us, "TERMINAL SERVER","", "ERROR", "No se pudo Obtener el login en TS");
					      
					    }else{
					    	
					    	for (String pla : selectedPlataforma) {	
					    		TPlataforma platforma=platBL.getPlataforma(Long.parseLong(pla));
					    		if(platforma.getNombre().equalsIgnoreCase("SUBSIDIO")){
					    			String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
									ServiciosI ws= new ServiciosI();
									Result res=ws.obtenerIdRolSubsidio(idrol);
							    	if(!res.getCode().equalsIgnoreCase(Code.OK)){
							    		log.error("[Guardar] error al obtener el id del rol: "+idrol+" error: "+res.getDescription());
							    		SysMessage.error("Error al guardar");
							    		return;
							    	}
									
							    	res=ws.insertWebSubsidio(name.getUsuario(), name.getNombre(), selectCiudadSubsidio, name.getCorreo(), (String)res.getData());
//							    	Thread.sleep(1000);
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma SUBSIDIO");
							    	}else{
							    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
							    	}
					    		}
								if(platforma.getNombre().equalsIgnoreCase("ACTIVADOR")){
									String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
									//TRol rol=platBL.getRol(Long.parseLong(idrol));
									ServiciosI ws= new ServiciosI();
									String telf="";
									if(name.getTelf()==null||name.getTelf().isEmpty()){
										telf="77390000";
									}else{
										telf=name.getTelf();
									}
							    	Result res=ws.insertActivadorLDAP(name.getUsuario(), name.getNombre(), name.getCorreo(),telf,name.getCi(), idrol);
//							    	Thread.sleep(1000);
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma ACTIVADOR");
							    	}else{
							    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
							    	}		    			
								}			
								if(platforma.getNombre().equalsIgnoreCase("LUCKY NUMBERS")){
									String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
									//TRol rol=platBL.getRol(Long.parseLong(idrol));
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.insertLucky(idrol, name.getCorreo(), name.getUsuario());
//							    	Thread.sleep(1000);
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma LUCKY NUMBERS");
							    	}else{
							    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
							    	}
								}
								if(platforma.getNombre().equalsIgnoreCase("DATOS FACTURACION")){
									String idrol=listaRolesPlataforma.get(Long.parseLong(pla));
									//TRol rol=platBL.getRol(Long.parseLong(idrol));
									ServiciosI ws= new ServiciosI();
							    	Result res=ws.insertDfp(name.getUsuario(), name.getNombre(), idrol);
//							    	Thread.sleep(1000);
							    	if(res.getCode().equalsIgnoreCase(Code.OK)){
							    		regBL.save("ALTA", res.getData()+"", platforma.getNombre(),idrol, "OK", "");
							    		this.controlerBitacora.accion(DescriptorBitacora.ALTA,"Se dio de alta el usuario: "+name.getUsuario()+" en plataforma DATOS FACTURACION");
							    	}else{
							    		regBL.save("ALTA", us, platforma.getNombre(),idrol, "ERROR", res.getDescription());
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

	public void newplauerimiento() throws Exception {
//		this.edit = false;
//
//		this.pla = new TPlataforma();
//		this.det = new MuRol();
//		this.listRol = new ArrayList<MuRol>();
		
		lineasArchivo= new ArrayList<ActivadorParametros>();
		listaRolesPlataforma.clear();
		radioSeleccionado=false;
		radioMasivo=true;
		selectedPlataforma= new String[0];
		//pla.setNombre("");
		listaRendered.clear();
		this.listPlat = this.platBL.getList();
		//selectRolSeleccionado="2";
		listaRolesPlataforma = new HashMap<Long,String>();
		usuario="";
		formato="Usuario";
		//listaPlataformaAtributos= new ArrayList<TPlataforma>();
		fillListasSeleccionables();
		
	}

//	public void ediTPlataforma() {
//		
//		try {
//			if (this.pla != null) {
//				this.visibleNuevoEditar = true;
//				this.plaId = this.pla.getId() + "";
//				//this.listRol = new ArrayList<TRol>();
//				listRol=platBL.getListRolPlataforma(pla.getId());
//
//				this.edit = true;
//			} else {
//				log.warn("[editar] No se encontro ningun registro seleccionado.");
//				SysMessage.warn("No se encontro ningun registro seleccionado.");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("error al momento de obtener los datos para editar");
//		}
//		
//	}
//
//	public void ediTPlataforma(TPlataforma muRol) {
//		this.pla = muRol;
//		ediTPlataforma();
//	}
//
//	public void editDetalle() {
//		if (this.det != null) {
//			this.visibleNuevoEditarDetalle = true;
//			this.detId = this.det.getId() + "";
//			this.editDetalle = true;
//		} else {
//			log.warn("[editar] No se encontro ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//	}
//
//	public void editDetalle(TRol muRol) {
//		this.det = muRol;
//		editDetalle();
//	}

//	public String getRoleId() {
//		return this.plaId;
//	}
//
//	public void setRoleId(String RoleId) {
//		this.plaId = RoleId;
//	}
//
//	public Boolean getEdit() {
//		return Boolean.valueOf(this.edit);
//	}
//
//	public void setEdit(Boolean edit) {
//		this.edit = edit.booleanValue();
//	}

	public List<TPlataforma> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TPlataforma> listPlat) {
		this.listPlat = listPlat;
	}

//	public TPlataforma getpla() {
//		return this.pla;
//	}
//
//	public void setpla(TPlataforma pla) {
//		this.pla = pla;
//	}
//
//	public String getplaId() {
//		return this.plaId;
//	}

//	public void setplaId(String plaId) {
//		this.plaId = plaId;
//	}
//
//	public void setEdit(boolean edit) {
//		this.edit = edit;
//	}


//	public List<TRol> getListDetalle() {
//		return this.listRol;
//	}
//
//	public void setListDetalle(List<TRol> listRol) {
//		this.listRol = listRol;
//	}
//
//	public TRol getDet() {
//		return this.det;
//	}
//
//	public void setDet(TRol det) {
//		this.det = det;
//	}

	public String formato(){
		if(!radioMasivo){
			formato="Usuario\nUsuario\nUsuario";
		}else{
			formato="Usuario,Usuario,Usuario";
		}
		return formato;
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

	public List<String> getSelectPlataformaRol() {
		return selectPlataformaRol;
	}

	public void setSelectPlataformaRol(List<String> selectPlataformaRol) {
		this.selectPlataformaRol = selectPlataformaRol;
	}

	public HashMap<Long, String> getListaRolesPlataforma() {
		return listaRolesPlataforma;
	}

	public void setListaRolesPlataforma(HashMap<Long, String> listaRolesPlataforma) {
		this.listaRolesPlataforma = listaRolesPlataforma;
	}

	public String getSelectRolSeleccionado() {
		return selectRolSeleccionado;
	}

	public void setSelectRolSeleccionado(String selectRolSeleccionado) {
		this.selectRolSeleccionado = selectRolSeleccionado;
	}
	
	
	public boolean isRadioSeleccionado() {
		return radioSeleccionado;
	}

//	public String[] getSelectRolSeleccionado() {
//		return selectRolSeleccionado;
//	}
//
//	public void setSelectRolSeleccionado(String[] selectRolSeleccionado) {
//		this.selectRolSeleccionado = selectRolSeleccionado;
//	}

	public void setRadioSeleccionado(boolean radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}

	public boolean isRadioMasivo() {
		return radioMasivo;
	}

	public void setRadioMasivo(boolean radioMasivo) {
		this.radioMasivo = radioMasivo;
	}

	public List<SelectItem> getSelectItemAtributosPlataforma() {
		return selectItemAtributosPlataforma;
	}

	public void setSelectItemAtributosPlataforma(
			List<SelectItem> selectItemAtributosPlataforma) {
		this.selectItemAtributosPlataforma = selectItemAtributosPlataforma;
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

	public String[] getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(String[] selectedMember) {
		this.selectedMember = selectedMember;
	}

	public List<String> getSelectMembers() {
		return selectMembers;
	}

	public void setSelectMembers(List<String> selectMembers) {
		this.selectMembers = selectMembers;
	}

	public boolean isVisiblemember() {
		return visiblemember;
	}

	public void setVisiblemember(boolean visiblemember) {
		this.visiblemember = visiblemember;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Boolean getRadiofechaSeleccionado() {
		return radiofechaSeleccionado;
	}

	public void setRadiofechaSeleccionado(Boolean radiofechaSeleccionado) {
		this.radiofechaSeleccionado = radiofechaSeleccionado;
	}

	public Date getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(Date fechaExp) {
		this.fechaExp = fechaExp;
	}

	public List<SelectItem> getSelectCiudadesSubsidio() {
		return selectCiudadesSubsidio;
	}

	public void setSelectCiudadesSubsidio(List<SelectItem> selectCiudadesSubsidio) {
		this.selectCiudadesSubsidio = selectCiudadesSubsidio;
	}

	public String getSelectCiudadSubsidio() {
		return selectCiudadSubsidio;
	}

	public void setSelectCiudadSubsidio(String selectCiudadSubsidio) {
		this.selectCiudadSubsidio = selectCiudadSubsidio;
	}

	public boolean isVisibleCiudad() {
		return visibleCiudad;
	}

	public void setVisibleCiudad(boolean visibleCiudad) {
		this.visibleCiudad = visibleCiudad;
	}

//	public List<TUsuarioAtributo> getListaUsuarioAtributos() {
//		return listaUsuarioAtributos;
//	}
//
//	public void setListaUsuarioAtributos(
//			List<TUsuarioAtributo> listaUsuarioAtributos) {
//		this.listaUsuarioAtributos = listaUsuarioAtributos;
//	}
//
//	public TUsuarioAtributo getUsuarioAtributo() {
//		return usuarioAtributo;
//	}
//
//	public void setUsuarioAtributo(TUsuarioAtributo usuarioAtributo) {
//		this.usuarioAtributo = usuarioAtributo;
//	}
//
//	public List<TPlataforma> getListaPlataformaAtributos() {
//		return listaPlataformaAtributos;
//	}
//
//	public void setListaPlataformaAtributos(
//			List<TPlataforma> listaPlataformaAtributos) {
//		this.listaPlataformaAtributos = listaPlataformaAtributos;
//	}
	



}
