package support.ussd.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import micrium.aes.AlgoritmoAES;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.ParametroBL;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.ActiveDirectory;
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.MailBL;
import support.ussd.business.TAbmBL;
import support.ussd.model.Adjunto;
import support.ussd.model.TAbm;
import support.ussd.model.TAbmAdjunto;
import support.ussd.model.TAbmSer;
import support.ussd.model.TUsuarioAtributo;
import support.util.Code;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class BandejaBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(BandejaBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private TAbmBL platBL;
	
	@Inject
	private UsuarioBL usuBL;
	
//	@Inject
//	private ServiciosBL servBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TAbm> listPlat;
	//private List<MuUsuario> listUsuarioAdd;
	private TAbm pla;
	//private MuUsuario usu;
	private String plaId;
	private boolean edit;
	private List<TAbmSer> listAbmAdm;
	private List<TAbmSer> listAbmAdmAux;
	private TAbmSer abmadmin;
	private String radioProce;
	
public TAbmSer getAbmadmin() {
		return abmadmin;
	}

	public void setAbmadmin(TAbmSer abmadmin) {
		this.abmadmin = abmadmin;
	}

	//	private List<TRol> listRol;
//	private TRol det;
	private String detId;
	private boolean editDetalle;
	//private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;
	private boolean visibleProcesar;
	//private boolean visibleNuevoEditarDetalle;
	
//	private List<SelectItem> selectItems;
//	private String select;
	@Inject
	private MailBL mailBL;
	//private static final Gson gson= new Gson();
	private List<SelectItem> selectItems;
	private String select;
	Adjunto adjunto = new Adjunto();
	AlgoritmoAES aes = new AlgoritmoAES();
	@Inject
	ParametroBL controlParametro;
	private boolean visibleVerAdmin;
	private List<MuUsuario> listUsuarioAdd;
	private List<TAbmAdjunto> listAdj;
	private boolean visibleVerAdjuntos;
	private MuUsuario usu;
	private TAbmAdjunto tadj;
	private String usuarioWindows;
	private String usuarioTerminal;

	public MuUsuario getUsu() {
		return usu;
	}

	public void setUsu(MuUsuario usu) {
		this.usu = usu;
	}

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TAbm();
			tadj= new TAbmAdjunto();
			this.visibleNuevoEditar = false;
			this.visibleProcesar=false;
			this.visibleVerAdmin=false;
			this.visibleVerAdjuntos=false;
			listAbmAdm= new ArrayList<TAbmSer>();
			//this.visibleNuevoEditarDetalle = false;
			//this.listPlat = this.platBL.getList();
			//select="-1";
			radioProce="1";
			select="-1";
			usuarioTerminal="";
			usuarioWindows="";
			fillSelectItems();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
//	private void filtrar()
//			  throws Exception
//			{
//			  HashMap<String, String> hm = new HashMap<String, String>();
//			  this.selectItems = new ArrayList<SelectItem>();
//			  this.selectItems.add(new SelectItem("-1", "-- Seleccionar Administrador --"));
//			  for (TAbmAdm a : listAbmAdm) {
//					for (TSerAdm b : a.getTServicio().getTSerAdms()) {
//						MuUsuario usuar=usuBL.getUser(b.getId().getIdAdm().intValue());
//						
//						hm.put(usuar.getUsuarioId().toString(), usuar.getNombre());
//							 //this.selectItems.add(sel);
//					}
//			  }
//			  for (Entry<String, String> entry : hm.entrySet()) {
//				    String key = entry.getKey();
//				    //List<String> value = entry.getValue();
//				    String value=entry.getValue();
//				    SelectItem sel = new SelectItem(key, value);
//				    this.selectItems.add(sel);
//
//				}
//			}
	
	public TAbmAdjunto getTadj() {
		return tadj;
	}

	public void setTadj(TAbmAdjunto tadj) {
		this.tadj = tadj;
	}

	private void fillSelectItems() throws Exception{
		 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		 String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
		 
		 listPlat=obtenerAbmPendiente(strIdUs);
		 
		 
		 
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
		// onclick="statusDialog.show()" oncomplete="statusDialog.hide()"
		// try {
		log.debug("[handleFileUpload] se va adjuntar el archivo");
		
		adjunto= new Adjunto();
		adjunto.setNombre(event.getFile().getFileName());
		//"C:\\Users\\pedro\\Desktop\\Adjuntos
		//adjunto.setRuta(controlParametro.getRutaAdjunto()+File.separator+adjunto.getNombre());
		adjunto.setRuta(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+"-"+abmadmin.getTServicio().getDescripcion());
		try {
			adjunto.setInputStream(event.getFile().getInputstream());

		} catch (IOException e) {

			e.printStackTrace();
			SysMessage.error("Error al adjuntar el archivo");
			log.error(
					"[handleFileUpload] Notificacion : No Se adjunto el archivo",
					e);

		}

	

	}
	
	public StreamedContent getFile(long id) throws Exception {

		log.debug("[getFile] Se va decargar el documento con id : " + id);
		StreamedContent file;
		TAbmSer doc= platBL.getTAbmSerXid(id).get(0);
		InputStream stream = new FileInputStream(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+"-"+doc.getTServicio().getDescripcion()+File.separator+doc.getAdjunto());
		file = new DefaultStreamedContent(stream, "aplication/pdf", doc.getAdjunto());
		log.debug("[getFile] Se va descargo el documento con id : " + id);
		return file;
	}
	
	public StreamedContent getFile2(TAbmAdjunto id) throws Exception {

		log.debug("[getFile] Se va decargar el documento con id : " + id);
		StreamedContent file;
		InputStream stream = new FileInputStream(id.getRuta());
		file = new DefaultStreamedContent(stream, "aplication/pdf",id.getNombre());
		log.debug("[getFile] Se va descargo el adjunto con nombre : " + id.getNombre());
		return file;
	}
	
	public Boolean copyFile(Adjunto adjunto, Long pk) {
		Boolean sw = true;
		try {
			log.debug("[copyFile] se van a copiar los archivos adjuntos");
			//C:\Users\pedro\Desktop\Adjuntos
			//File directorio = new File(aes.desencriptar(controlParametro.getRutaAdjunto()));
			
			
			File directorio = new File(adjunto.getRuta());
			log.debug("[copyFile] se van a copiar los archivos adjuntos a la ruta "
					+ directorio.getAbsolutePath());
			if (directorio.exists()) {
				TAbmSer doc = platBL.getTAbmSerXid(pk).get(0);
				File file=new File(adjunto.getRuta()+File.separator+doc.getAdjunto());
				file.delete();				
				file = new File(adjunto.getRuta()+File.separator+adjunto.getNombre());
				OutputStream out = null;
				try {
					out = new FileOutputStream(file);
					int read = 0;
					byte[] bytes = new byte[1024];

					while ((read = adjunto.getInputStream().read(bytes)) != -1) {
						out.write(bytes, 0, read);
					}

					adjunto.getInputStream().close();
					out.flush();
					out.close();

					log.debug("[copyFile] se copio el adjunto "
							+ adjunto.toString());
				} catch (Exception e) {
					e.printStackTrace();
					log.error(
							"[copyFile] no se pudo crear el outputstream",
							e);
					return false;
				} finally {
					if (out != null) {
						out.close();
					}

				}

			
			log.debug("[copyFile] se copiaron los archivos adjuntos");

			} else {
				if(directorio.mkdirs()){
					File file = new File(adjunto.getRuta()+File.separator+adjunto.getNombre());
					OutputStream out = null;
					try {
						out = new FileOutputStream(file);
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = adjunto.getInputStream().read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}

						adjunto.getInputStream().close();
						out.flush();
						out.close();

						log.debug("[copyFile] se copio el adjunto "
								+ adjunto.toString());
					} catch (Exception e) {
						e.printStackTrace();
						log.error(
								"[copyFile] no se pudo crear el outputstream",
								e);
						return false;
					} finally {
						if (out != null) {
							out.close();
						}

					}

				
				log.debug("[copyFile] se copiaron los archivos adjuntos");
				}
			}

			return sw;

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e);
			return false;
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e1);
			return false;
		}

	}
//	public void verAdmin(TAbmAdm muRol) {
//		this.abmadmin = muRol;
//		verAdmin();
//	}
	
//	public void verAdmin() {
//		
//		try {
//			if (this.abmadmin != null) {
//				this.visibleVerAdmin = true;		
//				listUsuarioAdd= new ArrayList<MuUsuario>();
//				List<TSerAdm> l=servBL.getListAdmPorServicio(this.abmadmin.getId().getIdServicio());
//				for (TSerAdm tSerAdm : l) {
//					
//					if(tSerAdm.getId().getIdServicio().equals(this.abmadmin.getId().getIdServicio())){
//						MuUsuario u=usuBL.getUser(tSerAdm.getId().getIdAdm().intValue());
//						listUsuarioAdd.add(u);
//					}
//					
//				}
//
//
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
	public String closable(){
		RequestContext context = RequestContext.getCurrentInstance();
		//listAbmAdm.clear();
		context.execute("PF('tabAddWidgetVar').clearFilters();");
		context.execute("PF('dlgNuevoEditar2').clearFilters();");
		return "true";
	}
	public String columAdmin(Long id){
		try {
			return usuBL.getUser(id).getCorreo();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String columSuper(Long id){
		try {
			return usuBL.getUser(id).getMuUsuario().getCorreo();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
//	public String pintarRojo(TAbm abm){
//		
//		try {
//			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//			String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
//			String res= "highlight2";
//			abm.setColor("Verde");
//			if(!abm.getEstadoProceso().equals("FINALIZADO")){
//				
//				List<TAbmSer> l2=platBL.getTAbmSerXAbm(abm.getId());
////				List<TAbmAdm> l2=new ArrayList<TAbmAdm>();
////				for (TAbmAdm a : l) {					
////					if(a.getId().getIdAbm().equals(abm.getId())){
////						l2.add(a);
////					}
////					
////				}
//				for (TAbmSer a : l2) {
//					if(a.getEstado().equals("PENDIENTE")){
//
//							MuUsuario uu=usuBL.getUser(a.getIdUsuario().intValue());
//							if(uu.getLogin().equals(strIdUs)){
//								res="highlight";
//								abm.setColor("Rojo");
//							}
//
//						
//					}
//				}
//			} 
//			
//				
//			return res;
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("Error al pintar fila pendiente");
//			return "";
//		}
//	}
//	
	private List<TAbm> obtenerAbmPendiente(String strIdUs){
		HashMap<TAbm,TAbm> hm= new HashMap<TAbm, TAbm>();
		List<TAbmSer> listAdm= new ArrayList<TAbmSer>();
		
		List<TAbm> res= new ArrayList<TAbm>();
		MuUsuario u= usuBL.getUserLogin(strIdUs);
		try {
	  		
	  		hm= new HashMap<TAbm, TAbm>();
	  		listAdm=platBL.obtenerServPendienteXusuarioO((long)u.getUsuarioId());
	  		for (TAbmSer a : listAdm) {				 						
	  			hm.put(a.getTAbm(),a.getTAbm());	
	
			}
	  			
	  		for (Entry<TAbm, TAbm> entry : hm.entrySet()) {
//	  			TAbm key = entry.getKey();
			    TAbm value = entry.getValue();
			    
				 res.add(value);
			}
	  		
		  return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
//	public void actualizar() {
//		
//	
//		try {
//			this.listPlat = this.platBL.getList();
//			ServiciosI ser= new ServiciosI();
//			Result res=ser.obtenerAllServicios();
//			if(!res.getCode().equals(Code.OK)){
//				SysMessage.error("Error al obtener los servicios");
//				log.error("[actualizar]ERROR: "+res.getDescription());
//				return;
//			}
//			String lss=(String) res.getData();
//			
//			List<TServicioABM> ls=gson.fromJson(lss,new TypeToken<ArrayList<TServicioABM>>(){}.getType());
//			if(listPlat.isEmpty()){
//				for (TServicioABM a : ls) {
//					if(a.getEstado().equalsIgnoreCase("Habilitado")){
//						TServicio s= new TServicio();
//						s.setIdServicio((long)a.getCodServicio());
//						s.setDescripcion(a.getDescripcion());
//						s.setTipoServicio(a.getTipoServicio());
//						s.setEstado(true);
//						this.platBL.save(s);
//					}
//					
//				}	
//						
//			}else{
//				for (TServicioABM a : ls) {					
//					TServicio s= platBL.getServicioABM(a.getCodServicio());
//					if(s==null){
//						s= new TServicio();
//						s.setIdServicio((long)a.getCodServicio());
//						s.setDescripcion(a.getDescripcion());
//						s.setTipoServicio(a.getTipoServicio());
//						s.setEstado(true);
//						this.platBL.save(s);
//					}else{
//						if(a.getEstado().equalsIgnoreCase("DESHABILITADO")){
//							platBL.delete(s);
//						}else{
//
//								s.setIdServicio((long)a.getCodServicio());
//								s.setDescripcion(a.getDescripcion());
//								s.setTipoServicio(a.getTipoServicio());
//								s.setEstado(true);
//								List<TSerAdm> ll=platBL.getListAdmPorServicio(1);
//								List<MuUsuario> laux=new ArrayList<MuUsuario>();
//								for (TSerAdm f : ll) {
//									if(f.getId().getIdServicio().equals(s.getIdServicio())) {
//										MuUsuario u= utBL.getUser(f.getId().getIdAdm().intValue());
//										laux.add(u);
//									}
//								}
//								this.platBL.update(s,laux);
//							
//						}
//					}
//				}	
//			}
//			this.listPlat = this.platBL.getList();
//			SysMessage.info("Se guardó correctamente.");
//		} catch (Exception e) {
//			log.error("[actualiza] error al momento de actuializar los servicios", e);
//			SysMessage.error("Fallo al guardar en la Base de Datos.");
//		}
//	}
	
//	public void guardar() {
//		
////		if(select.equalsIgnoreCase("-1")){
////			SysMessage.warn("Debe seleccionar un administrador");
////			return;
////		}
//		
////		try {
////			MuUsuario id=utBL.getUser(Integer.valueOf(select));
////
////		} catch (Exception e) {
////			e.printStackTrace();
////			log.error(e.getMessage());
////			SysMessage.error("Error al obtener el administrador");
////		}
//		String str = this.platBL.validate(this.pla, this.plaId);
//		if (!str.isEmpty()) {
//			SysMessage.warn(str);
//			return;
//		}
//		try {
//			if (!this.edit) {
//
//			} else {
//				Long id = Long.valueOf(Long.parseLong(this.plaId));
//				this.pla.setId(id);
//				//this.pla.setTRols(this.listRol);
//				//pla.setUso((radioAlta)?"DOCUMENTACION":"ALTA");
//				pla.setDescripcion(pla.getDescripcion().trim());
//
//				this.platBL.update(this.pla,listUsuarioAdd);
//				this.controlerBitacora.update(DescriptorBitacora.SERVICIO, 
//				          this.pla.getId()+"", this.pla.getDescripcion());
//				this.visibleNuevoEditar = false;
//			}
//			this.listPlat = this.platBL.getList();
//			SysMessage.info("Se guardó correctamente.");
//		} catch (Exception e) {
//			log.error("[saveServicio] error al momento de modificar o guardar: "
//					+ this.pla.getId() + " " + e.getLocalizedMessage(), e);
//			SysMessage.error("Fallo al guardar en la Base de Datos.");
//		}
//	}

//	public void add() throws NumberFormatException, Exception {
//		//this.det = new MuRol();
//		this.editDetalle=false;
//		this.visibleNuevoEditarDetalle = true;
//	}

//	public void guardarDetalle() {
//
//		try {
////			if(det.getIdrol()==0){
////				SysMessage.warn("El id rol no puede ser cero");
////				return ;
////			}
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

//	public String deleteDetalle(TRol plau) {
//		this.det = plau;
//		return deleteDetalle();
//	}

//	public String delete(TServicio plau) {
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

//	public void newplauerimiento() {
//		this.edit = false;
//		this.visibleNuevoEditar = true;
//		this.pla = new TServicio();
//		select="-1";
////		this.det = new TRol();
////		this.listRol = new ArrayList<TRol>();
//	}

	public void ediTPlataforma() {
		
		try {
			usuarioTerminal="";
			usuarioWindows="";
			listAbmAdm= new ArrayList<TAbmSer>();
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
			MuUsuario u= usuBL.getUserLogin(strIdUs);
			adjunto= new Adjunto();
			select="-1";
			if (this.pla != null) {
				
				try {
					TUsuarioAtributo name = ActiveDirectory.obtenerUsuarioActiveDirectoryEH(pla.getEhumano());
					if(name.getUsuario()==null){
						usuarioWindows="";
					}else{
						usuarioWindows=name.getUsuario();
					}
					
					
					name = ActiveDirectory.obtenerNombreCompletoTerminalEH(pla.getEhumano());
					if(name.getUsuario()==null){
						usuarioTerminal="";
					}else{
						usuarioTerminal=name.getUsuario();
					}
				} catch (Exception e) {
					log.error("Nose pudieron obtener los usuarios de terminal o windows");
					e.printStackTrace();
				}
				
				
				
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				listAbmAdmAux=platBL.getTAbmSerXAbm(pla.getId(),(long)u.getUsuarioId());
				for (TAbmSer a : listAbmAdmAux) {					
					if(a.getIdUsuario().equals((long)u.getUsuarioId())){
						listAbmAdm.add(a);
					}				
				}
				for (TAbmSer a : listAbmAdmAux) {					
					if(!a.getIdUsuario().equals((long)u.getUsuarioId())){
						listAbmAdm.add(a);
					}				
				}
				controlerBitacora.accion(DescriptorBitacora.BANDEJA, "El usuario "+strIdUs+" ingreso al ABM: "+listAbmAdmAux.get(0).getTAbm().getAbm());
				this.edit = true;
			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
		
	}
	
public void verAdjuntos() {
		
		try {	
			if (this.pla != null) {
				List<TAbmAdjunto> l=platBL.getListAbmAdjuntos(pla.getId());
				
				this.visibleVerAdjuntos = true;
				listAdj= new ArrayList<TAbmAdjunto>();
				for (TAbmAdjunto a : l) {
					TAbmAdjunto b = new TAbmAdjunto();
					b.setIdAdjunto(a.getIdAdjunto());
					b.setNombre(a.getNombre());
					b.setDescripcion(a.getDescripcion());
					b.setRuta(a.getRuta());
					listAdj.add(b);
				}
				
			} else {
				log.warn("[Ver Adjuntos] el ABM "+pla.getAbm()+" no contiene adjuntos");
				SysMessage.warn("El ABM "+pla.getAbm()+" no contiene adjuntos");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
		
	}

	public void ediTPlataforma(TAbm muRol) {
		listAbmAdm.clear();
		this.pla = muRol;
		
		ediTPlataforma();
	}
	
//public void buscarPordAdmin(){
//		
//		try {
//			mostrarTodos();
//			if(select.equals("-1")){
//				SysMessage.warn("Debe seleccionar un administrador para poder filtrar");
//				return;
//			}
//			MuUsuario usuar=usuBL.getUser(Integer.valueOf(select));
//			listAbmAdmAux=listAbmAdm;
//			
//			listAbmAdm= new ArrayList<TAbmAdm>();
//			for (TAbmAdm a : listAbmAdmAux) {
//				for (TSerAdm b : a.getTServicio().getTSerAdms()) {					
//					if(b.getId().getIdAdm().intValue()==usuar.getUsuarioId()){
//						listAbmAdm.add(a);
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("Error al filtrar lo servicios");
//		}
//		
//	}
	
//	public void mostrarTodos(){
//		try {
//			listAbmAdm.clear();
//			
//			if (this.pla != null) {
//				this.visibleNuevoEditar = true;
//				this.plaId = this.pla.getId() + "";
//				listAbmAdm=platBL.getTAbmAdm(pla.getId());
////				for (TAbmAdm a : l) {					
////					if(a.getId().getIdAbm().equals(this.pla.getId())){
////						listAbmAdm.add(a);
////					}
////					
////				}
//				//this.listRol = new ArrayList<TRol>();
//				//listRol=platBL.getListRolPlataforma(pla.getId());
//
//			} else {
//				log.warn("[editar] No se encontro ningun registro seleccionado.");
//				SysMessage.warn("No se encontro ningun registro seleccionado.");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("error al momento de obtener los datos para editar");
//		}
//	}
	
	public void guardar(){
		try {
			
			if(radioProce.equals("2")||radioProce.equals("3")){
				if(abmadmin.getObservacion().isEmpty()){
					SysMessage.warn("Si va Observar o Rechazar, debe detallar el porque en el campo Observación");
					return;
				}
							
			}
			
			if (adjunto != null) {
				if (adjunto.getNombre() != null) {
					if (copyFile(adjunto, abmadmin.getId())) {
						log.info("se copio los adjuntos a disco");
					} else {
						log.error("no se copiaron los adunto a disco");
					}
				}else{
					File directorio = new File(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+"-"+abmadmin.getTServicio().getDescripcion());
					if (directorio.exists()) {
						if(eliminarHijos(directorio)&&directorio.delete()){
							System.out.println("elimino");
						}else{
							System.out.println("no elimino");
						}
							
					}
				}

			}
			
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");	
			MuUsuario u=usuBL.getUserLogin(strIdUs);
			listAbmAdm.clear();
			listAbmAdm=platBL.getTAbmSerXAbm(pla.getId(),(long)u.getUsuarioId());

			
			int c=0;
			for (TAbmSer a : listAbmAdm) {
				if(a.getId().equals(abmadmin.getId())){	
					break;
				}
				c=c+1;
			}
			listAbmAdm.remove(c);
			if(radioProce.equals("1")){
				if(!abmadmin.getEstado().equalsIgnoreCase("PROCESADO")){
					abmadmin.setEstado("PROCESADO");
					abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					pla.setFaltantes(pla.getFaltantes()-1);
					controlerBitacora.accion(DescriptorBitacora.BANDEJA, "Se procesó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
				}
				
			}else{
				if(radioProce.equals("2")){
					if(!abmadmin.getEstado().equalsIgnoreCase("OBSERVADO")){
						abmadmin.setEstado("OBSERVADO");
						abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						pla.setEstadoProceso("OBSERVADO");
						controlerBitacora.accion(DescriptorBitacora.BANDEJA, "Se observó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
						MuUsuario as=usuBL.getUserLogin("villarroelj");
						List<MuUsuario> lisadmin= new ArrayList<MuUsuario>();
						lisadmin.add(as);
//						as=usuBL.getUserLogin("villarroelj");
//						lisadmin.add(as);
						
						support.util.Result result = mailBL.sendEmailObsFin(lisadmin,pla,abmadmin.getTServicio().getDescripcion(),abmadmin.getObservacion(),strIdUs+"@tigo.net.bo");
						if (result.getCode().equalsIgnoreCase(Code.OK)) {
							log.info("se envio correctamente el servicio observado");
						}else{
							log.info("no se envio correctamente el servicio observado");
						}
					}
					
				}else{

						if(!abmadmin.getEstado().equalsIgnoreCase("RECHAZADO")){
							abmadmin.setEstado("RECHAZADO");
							abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
							pla.setFaltantes(pla.getFaltantes()-1);
							controlerBitacora.accion(DescriptorBitacora.BANDEJA, "Se rechazó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
						}				

				}		
				}
			abmadmin.setAdjunto((adjunto.getNombre()==null)?null:adjunto.getNombre());
			listAbmAdm.add(abmadmin);
			//pla.setFaltantes(pla.getFaltantes()-1);
			if(pla.getFaltantes()==0){
				pla.setEstadoProceso("FIN");
				pla.setFechafinalizado(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				MuUsuario as=usuBL.getUserLogin("villarroelj");
				List<MuUsuario> lisadmin= new ArrayList<MuUsuario>();
				lisadmin.add(as);
//				as=usuBL.getUserLogin("villarroelj");
//				lisadmin.add(as);
				support.util.Result result = mailBL.sendEmailFin(lisadmin,pla);
				if (result.getCode().equalsIgnoreCase(Code.OK)) {
					log.info("se envio correctamente el abm finalizado");
				}else{
					log.info("no se envio correctamente el abm finalizado");
				}
			}else{
				pla.setEstadoProceso("PEN");
				
			}
			pla.setTAbmSers(listAbmAdm);
			platBL.update(pla);
			visibleProcesar=false;
			adjunto= new Adjunto();
			fillSelectItems();
			radioProce="1";
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean eliminarHijos(File dir){
		File[] hijos= dir.listFiles();
		boolean hijosEliminados=true;
		for (int i = 0; hijos!=null && i<hijos.length; i++) {
			File hijo=hijos[i];
			if(hijo.isDirectory()){
				hijosEliminados=this.eliminarHijos(hijo)&&hijosEliminados;
			}
			if(hijo.exists()){
				hijosEliminados=hijo.delete()&&hijosEliminados;
			}
		}
		return hijosEliminados;
	}
	public void cerrar() {
		
		try {
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");	
			radioProce="1";
			MuUsuario usu=usuBL.getUserLogin(strIdUs);
			if (this.abmadmin != null) {
				
				
				if(abmadmin.getEstado().equalsIgnoreCase("PENDIENTE")||abmadmin.getEstado().equalsIgnoreCase("OBSERVADO")){
				

						if(abmadmin.getIdUsuario().intValue()==usu.getUsuarioId()){
							this.visibleProcesar=true;
							return;
						}

					log.warn("[cerrar] El servicio "+abmadmin.getTServicio().getDescripcion() +" no esta vinculado al usuario: "+strIdUs);
					SysMessage.warn("El servicio "+abmadmin.getTServicio().getDescripcion() +" no esta vinculado al usuario: "+strIdUs);
					//this.listRol = new ArrayList<TRol>();
					//listRol=platBL.getListRolPlataforma(pla.getId());
					
					

				}else{
					log.warn("[cerrar] El servicio "+abmadmin.getTServicio().getDescripcion() +" esta procesado");
					SysMessage.warn("El servicio "+abmadmin.getTServicio().getDescripcion() +" esta procesado");
				}
				
			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
		
	}

	public void cerrar(TAbmSer muRol) {
		this.abmadmin = muRol;
		cerrar();
	}
	
//	
//	public void agregarAdmin(){
//		
//
//			if(select.equalsIgnoreCase("-1")){
//			SysMessage.warn("Debe seleccionar un administrador");
//			return;
//			}
//		
//			try {
//				usu=utBL.getUser(Integer.valueOf(select));
//				for (MuUsuario a : listUsuarioAdd) {
//					if(a.getLogin().equals(usu.getLogin())){
//						SysMessage.warn("No se permite administradores repetidos");
//						return;
//					}
//				}
//				listUsuarioAdd.add(usu);
//				select="-1";
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error(e.getMessage());
//				SysMessage.error("Error al obtener el administrador");
//			}
//
//	}
	
//	public void delAdmin(MuUsuario us){
//		
//
//	
//		try {
//			listUsuarioAdd.remove(us);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//			SysMessage.error("Error al obtener el administrador");
//		}
//
//	}
	
	
	

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

//	public void editDetalle(TRol muRol) {
//		this.det = muRol;
//		editDetalle();
//	}

	public String getRoleId() {
		return this.plaId;
	}

	public void setRoleId(String RoleId) {
		this.plaId = RoleId;
	}

	public Boolean getEdit() {
		return Boolean.valueOf(this.edit);
	}

	public void setEdit(Boolean edit) {
		this.edit = edit.booleanValue();
	}

	public List<TAbm> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TAbm> listPlat) {
		this.listPlat = listPlat;
	}

	public TAbm getpla() {
		return this.pla;
	}

	public void setpla(TAbm pla) {
		this.pla = pla;
	}

	public String getplaId() {
		return this.plaId;
	}

	public void setplaId(String plaId) {
		this.plaId = plaId;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

//	public boolean isVisibleDialog() {
//		return this.visibleDialog;
//	}
//
//	public void setVisibleDialog(boolean visibleDialog) {
//		this.visibleDialog = visibleDialog;
//	}

	public boolean isVisibleNuevoEditar() {
		return this.visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		listAbmAdm= new ArrayList<TAbmSer>();
		RequestContext context = RequestContext.getCurrentInstance();
		//listAbmAdm.clear();
		context.execute("PF('tabAddWidgetVar').clearFilters();");
		context.execute("PF('dlgNuevoEditar2').clearFilters();");

		this.visibleNuevoEditar = visibleNuevoEditar;
	}

//	public boolean isVisibleNuevoEditarDetalle() {
//		return this.visibleNuevoEditarDetalle;
//	}
//
//	public void setVisibleNuevoEditarDetalle(boolean visibleNuevoEditarDetalle) {
//		this.visibleNuevoEditarDetalle = visibleNuevoEditarDetalle;
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

	public String getDetId() {
		return this.detId;
	}

	public void setDetId(String detId) {
		this.detId = detId;
	}

	public boolean isEditDetalle() {
		return this.editDetalle;
	}

	public void setEditDetalle(boolean editDetalle) {
		this.editDetalle = editDetalle;
	}

	
	
//	public List<SelectItem> getSelectItems() {
//		return selectItems;
//	}
//
//
//	public void setSelectItems(List<SelectItem> selectItems) {
//		this.selectItems = selectItems;
//	}
//
//
//	public String getSelect() {
//		return select;
//	}
//
//
//	public void setSelect(String select) {
//		this.select = select;
//	}


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_BAND, idAccion);
	}

	public List<TAbmSer> getListAbmAdm() {
		return listAbmAdm;
	}

	public void setListAbmAdm(List<TAbmSer> listAbmAdm) {
		this.listAbmAdm = listAbmAdm;
	}

	public boolean isVisibleProcesar() {
		return visibleProcesar;
	}

	public void setVisibleProcesar(boolean visibleProcesar) {
		this.visibleProcesar = visibleProcesar;
	}

	public String getRadioProce() {
		return radioProce;
	}

	public void setRadioProce(String radioProce) {
		this.radioProce = radioProce;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public boolean isVisibleVerAdmin() {
		return visibleVerAdmin;
	}

	public void setVisibleVerAdmin(boolean visibleVerAdmin) {
		this.visibleVerAdmin = visibleVerAdmin;
	}

	public List<MuUsuario> getListUsuarioAdd() {
		return listUsuarioAdd;
	}

	public void setListUsuarioAdd(List<MuUsuario> listUsuarioAdd) {
		this.listUsuarioAdd = listUsuarioAdd;
	}


	public boolean isVisibleVerAdjuntos() {
		return visibleVerAdjuntos;
	}



	public void setVisibleVerAdjuntos(boolean visibleVerAdjuntos) {
		this.visibleVerAdjuntos = visibleVerAdjuntos;
	}

	public List<TAbmAdjunto> getListAdj() {
		return listAdj;
	}

	public void setListAdj(List<TAbmAdjunto> listAdj) {
		this.listAdj = listAdj;
	}

	public String getUsuarioWindows() {
		return usuarioWindows;
	}

	public void setUsuarioWindows(String usuarioWindows) {
		this.usuarioWindows = usuarioWindows;
	}

	public String getUsuarioTerminal() {
		return usuarioTerminal;
	}

	public void setUsuarioTerminal(String usuarioTerminal) {
		this.usuarioTerminal = usuarioTerminal;
	}

//
//	public List<MuUsuario> getListUsuarioAdd() {
//		return listUsuarioAdd;
//	}
//
//	public void setListUsuarioAdd(List<MuUsuario> listUsuarioAdd) {
//		this.listUsuarioAdd = listUsuarioAdd;
//	}
//
//	public MuUsuario getUsu() {
//		return usu;
//	}
//
//	public void setUsu(MuUsuario usu) {
//		this.usu = usu;
//	}





	
	
}
