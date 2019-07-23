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
import java.util.List;

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
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.TAbmBL;
import support.ussd.model.Adjunto;
import support.ussd.model.TAbm;
import support.ussd.model.TAbmSer;

import com.sun.org.apache.bcel.internal.generic.LUSHR;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class BandejaAdminBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(BandejaAdminBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private TAbmBL platBL;
	
	@Inject
	private UsuarioBL usuBL;
	
//	@Inject
//	private ServiciosBL servBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	@Inject
	ParametroBL controlParametro;
	
	private List<TAbm> listPlat;
	//private List<MuUsuario> listUsuarioAdd;
	private TAbm pla;
	//private MuUsuario usu;
	private String plaId;
	private boolean edit;
	//private List<TAbmAdm> listAbmAdm;
	private List<TAbmSer> listAbmAdm2;
	//private List<TAbmSer> listAbmAdmAux;
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
	private boolean visibleVerAdmin;
	private MuUsuario usu;
	//private boolean visibleNuevoEditarDetalle;
	
	private List<SelectItem> selectItems;
	private String select;
	
	private List<MuUsuario> listUsuarioAdd;
	
//	private static final Gson gson= new Gson();
	AlgoritmoAES aes = new AlgoritmoAES();
	Adjunto adjunto = new Adjunto();
	
	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TAbm();
			this.visibleNuevoEditar = false;
			this.visibleProcesar=false;
			this.visibleVerAdmin=false;
			//listAbmAdm= new ArrayList<TAbmAdm>();
			listAbmAdm2= new ArrayList<TAbmSer>();
			//this.visibleNuevoEditarDetalle = false;
			this.listPlat = this.platBL.getList();
			select="-1";
			radioProce="1";

			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

//	private void fillSelectItems()
//			  throws Exception
//			{
//			  HashMap<String, String> hm = new HashMap<String, String>();
//			  this.selectItems = new ArrayList<SelectItem>();
//			  this.selectItems.add(new SelectItem("-1", "-- Seleccionar Administrador --"));
//			  for (TAbmSer a : listAbmAdm2) {
//
//					MuUsuario usuar=usuBL.getUser(a.getIdUsuario().intValue());
//						
//					hm.put(usuar.getUsuarioId().toString(), usuar.getNombre());
//
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

	public boolean isVisibleVerAdmin() {
		return visibleVerAdmin;
	}

	public void setVisibleVerAdmin(boolean visibleVerAdmin) {
		this.visibleVerAdmin = visibleVerAdmin;
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

public void bitacoraExportar(){
	try {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
		controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "El usuario "+strIdUs+" exportó accesos del ABM: "+listAbmAdm2.get(0).getTAbm().getAbm());

	} catch (Exception e) {
		log.error("Error al escribir en la bitacora",e);
		SysMessage.error("Error al momento de exportar");
	}
}	

public void ediTPlataforma() {
		
		try {
			//listAbmAdm.clear();		
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
			listAbmAdm2= new ArrayList<TAbmSer>();
			select="-1";
			adjunto= new Adjunto();
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				//listAbmAdm=platBL.getTAbmAdm(pla.getId());
				listAbmAdm2=platBL.getTAbmSerXAbmSinOrder(pla.getId());
				controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "El usuario "+strIdUs+" ingreso al ABM: "+listAbmAdm2.get(0).getTAbm().getAbm());
//				for (TAbmAdm a : l) {					
//					if(a.getId().getIdAbm().equals(this.pla.getId())){
//						listAbmAdm.add(a);
//					}
//					
//				}
				//this.listRol = new ArrayList<TRol>();
				//listRol=platBL.getListRolPlataforma(pla.getId());
				//fillSelectItems();
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

	public void ediTPlataforma(TAbm muRol) {
		this.pla = muRol;
		ediTPlataforma();
	}
	
//	public void buscarPordAdmin(){
//		
//		try {
//			mostrarTodos();
//			if(select.equals("-1")){
//				SysMessage.warn("Debe seleccionar un administrador para poder filtrar");
//				return;
//			}
//			//MuUsuario usuar=usuBL.getUser(Integer.valueOf(select));
//			listAbmAdmAux=listAbmAdm2;
//			
//			listAbmAdm2= new ArrayList<TAbmSer>();
//			for (TAbmSer a : listAbmAdmAux) {
//					if(a.getIdUsuario().intValue()==Integer.valueOf(select)){
//						listAbmAdm2.add(a);
//					}
//
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("Error al filtrar lo servicios");
//		}
//		
//	}
//	
	public void mostrarTodos(){
		try {
			listAbmAdm2.clear();
			
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				listAbmAdm2=platBL.getTAbmSerXAbmSinOrder(pla.getId());
//				for (TAbmAdm a : l) {					
//					if(a.getId().getIdAbm().equals(this.pla.getId())){
//						listAbmAdm.add(a);
//					}
//					
//				}
				//this.listRol = new ArrayList<TRol>();
				//listRol=platBL.getListRolPlataforma(pla.getId());

			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
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
	
	public Boolean copyFile(Adjunto adjunto, long pk) {
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
	
	public void guardar(){
		try {
			
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
			
			listAbmAdm2.clear();
			listAbmAdm2=platBL.getTAbmSerXAbmSinOrder(pla.getId());
//			for (TAbmAdm a : l) {					
//				if(a.getId().getIdAbm().equals(this.pla.getId())){
//					listAbmAdm.add(a);
//				}				
//			}
			int c=0;
			for (TAbmSer a : listAbmAdm2) {
				if(a.getId().equals(abmadmin.getId())){	
					break;
				}
				c=c+1;
			}
			
			listAbmAdm2.remove(c);
			if(radioProce.equals("1")){
				if(!abmadmin.getEstado().equalsIgnoreCase("PROCESADO")){
					abmadmin.setEstado("PROCESADO");
					abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					pla.setFaltantes(pla.getFaltantes()-1);
					controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "Se procesó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
				}
				
			}else{
				if(radioProce.equals("2")){
					if(!abmadmin.getEstado().equalsIgnoreCase("OBSERVADO")){
						abmadmin.setEstado("OBSERVADO");
						abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						pla.setEstadoProceso("OBSERVADO");
						
						controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "Se observó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
					}
					
				}else{

					if(radioProce.equals("3")){
						if(!abmadmin.getEstado().equalsIgnoreCase("RECHAZADO")){
							abmadmin.setEstado("RECHAZADO");
							abmadmin.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
							pla.setFaltantes(pla.getFaltantes()-1);
							controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "Se rechazó el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
						}				
					}else{
						if(abmadmin.getEstado().equalsIgnoreCase("OBSERVADO")){
							abmadmin.setEstado("PENDIENTE");
							abmadmin.setFecha(null);
							pla.setFaltantes(pla.getFaltantes());
							controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "Se cambio a pendiente el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
						}else{
							if(!abmadmin.getEstado().equalsIgnoreCase("PENDIENTE")){
								abmadmin.setEstado("PENDIENTE");
								abmadmin.setFecha(null);
								pla.setFaltantes(pla.getFaltantes()+1);
								controlerBitacora.accion(DescriptorBitacora.BANDEJAADMIN, "Se cambio a pendiente el servicio: "+abmadmin.getTServicio().getDescripcion()+" del ABM: "+abmadmin.getTAbm().getAbm());
							}
						}
						
							
					}
							
				}
			}
			
			
			abmadmin.setAdjunto((adjunto.getNombre()==null)?null:adjunto.getNombre());
			listAbmAdm2.add(abmadmin);
			
			if(pla.getFaltantes()==0){
				pla.setEstadoProceso("FIN");
				pla.setFechafinalizado(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			}else{
				pla.setEstadoProceso("PEN");
				pla.setFechafinalizado(null);
			}
			pla.setTAbmSers(listAbmAdm2);
			platBL.update(pla);
			visibleProcesar=false;
			ediTPlataforma();
			this.listPlat = this.platBL.getList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public StreamedContent getFile(long id) throws Exception {

		log.debug("[getFile] Se va decargar el documento con id : " + id);
		StreamedContent file;
		TAbmSer doc= platBL.getTAbmSerXid(id).get(0);
		InputStream stream = new FileInputStream(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+"-"+doc.getTServicio().getDescripcion()+File.separator+doc.getAdjunto());
		file = new DefaultStreamedContent(stream, "aplication/pdf", doc.getAdjunto());
		log.debug("[getFile] Se va descargo el documento con id : " + id);
		return file;
	}
	
	public void cerrar() {
		
		try {
			if (this.abmadmin != null) {
				adjunto = new Adjunto();
				radioProce = "1";
				this.visibleProcesar = true;

			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage
					.error("error al momento de obtener los datos para editar");
		}
		
	}
	
	public String closable(){
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('tabAddWidgetVar').clearFilters();");
		//context.execute("PF('dlgNuevoEditar2').clearFilters();");
		return "true";
	}
	
//	public void verAdmin(TAbmSer muRol) {
//		this.abmadmin = muRol;
//		verAdmin();
//	}
	
//	public void verAdmin() {
//		
//		try {
//			if (this.abmadmin != null) {
//				this.visibleVerAdmin = true;		
//				listUsuarioAdd= new ArrayList<MuUsuario>();
//
//						MuUsuario u=usuBL.getUser(this.abmadmin.getIdUsuario().intValue());
//						listUsuarioAdd.add(u);
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
	
	public void cerrar(TAbmSer muRol) {
		this.abmadmin = muRol;
		cerrar();
	}
	


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



	public boolean isVisibleNuevoEditar() {
		return this.visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		listAbmAdm2.clear();
		//listAdj.clear();
		this.visibleNuevoEditar = visibleNuevoEditar;
	}



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


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_BAND_ADMIN, idAccion);
	}

//	public List<TAbmAdm> getListAbmAdm() {
//		return listAbmAdm;
//	}
//
//	public void setListAbmAdm(List<TAbmAdm> listAbmAdm) {
//		this.listAbmAdm = listAbmAdm;
//	}

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

	public List<MuUsuario> getListUsuarioAdd() {
		return listUsuarioAdd;
	}

	public void setListUsuarioAdd(List<MuUsuario> listUsuarioAdd) {
		this.listUsuarioAdd = listUsuarioAdd;
	}

	public MuUsuario getUsu() {
		return usu;
	}

	public void setUsu(MuUsuario usu) {
		this.usu = usu;
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

	public List<TAbmSer> getListAbmAdm2() {
		return listAbmAdm2;
	}

	public void setListAbmAdm2(List<TAbmSer> listAbmAdm2) {
		this.listAbmAdm2 = listAbmAdm2;
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
