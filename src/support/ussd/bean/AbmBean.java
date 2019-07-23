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
import support.ussd.model.TAbmAdjunto;
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
public class AbmBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AbmBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private TAbmBL platBL;
	
	@Inject
	private ServiciosBL servBL;
	
	@Inject
	private UsuarioBL usuBL;
	
	@Inject
	private UsuarioBL utBL;
	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TAbm> listPlat;
	private TAbm pla;
	
	private TUsuarioAtributo userListAdmin;
	private List<TServicioABM> listServ;
	private TServicioABM serv;
	
	private List<TServicio> tsaux;
	private List<MuUsuario> lisadmin;
	private List<TUsuarioAtributo> lisadmin2;
	private List<Adjunto> listaAdjunto;
	
	private List<TAbmAdm> listAdm;
	private TAbmAdm adm;
	
	private List<SelectItem> selectItems;
	private String select;
	
	private String[] selectedAdjuntos;
	
	private String observacion;
	
	@Inject
	ParametroBL controlParametro;
	
	private HashMap<Long,String> listaServicioAdmin;
	
	private String nroABM;
	
	private static final Gson gson= new Gson();
	private boolean visibleNuevoEditar;

	
	@Inject
	private MailBL mailBL;
	
	AlgoritmoAES aes;

	@PostConstruct
	public void init() {
		try {
			nroABM="";
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TAbm();
			listaServicioAdmin = new HashMap<Long,String>();
			listaAdjunto= new ArrayList<Adjunto>();
			selectedAdjuntos=null;
			this.visibleNuevoEditar = false;
			
			//this.visibleNuevoEditarDetalle = false;
			//this.listPlat = this.platBL.getList();
			fillSelectItems();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	
	
	private void fillSelectItems()
			  throws Exception
			{
			  this.selectItems = new ArrayList<SelectItem>();
			  this.selectItems.add(new SelectItem("-1", "-- Seleccionar Administrador --"));
			  List<MuUsuario> listaRol = this.utBL.getUsers(); 
			  for (MuUsuario role : listaRol)
			  {
			    SelectItem sel = new SelectItem(role.getUsuarioId(), role.getNombre());
			    this.selectItems.add(sel);
			  }
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
		// onclick="statusDialog.show()" oncomplete="statusDialog.hide()"
		// try {
		log.debug("[handleFileUpload] se va adjuntar el archivo");

		
		Adjunto adjunto = new Adjunto();
		adjunto.setNombre(event.getFile().getFileName());
		//"C:\\Users\\pedro\\Desktop\\Adjuntos
		//adjunto.setRuta(controlParametro.getRutaAdjunto()+File.separator+adjunto.getNombre());
		aes = new AlgoritmoAES();
		adjunto.setRuta(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+"{abm}"+File.separator+adjunto.getNombre());
		try {
			adjunto.setInputStream(event.getFile().getInputstream());
			listaAdjunto.add(adjunto);
			String[] a = new String[listaAdjunto.size()];
			for (int i = 0; i < listaAdjunto.size(); i++) {
				a[i] = listaAdjunto.get(i).getNombre();
			}
			setSelectedAdjuntos(a);
		} catch (IOException e) {

			e.printStackTrace();
			SysMessage.error("Error al adjuntar el archivo");
			log.error(
					"[handleFileUpload] Notificacion : No Se adjunto el archivo",
					e);

		}

	

	}
	
	
	public Boolean copyFile(List<Adjunto> listAd) {
		Boolean sw = false;
		try {
			log.debug("[copyFile] se van a copiar los archivos adjuntos");
			//C:\Users\pedro\Desktop\Adjuntos
			//File directorio = new File(aes.desencriptar(controlParametro.getRutaAdjunto()));
			aes = new AlgoritmoAES();
			log.debug("[copyFile] se van a copiar los archivos adjuntos a la ruta "
					+ aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+File.separator);
			File directorio = new File(aes.desencriptar(controlParametro.getRutaAdjunto())+File.separator+pla.getAbm()+File.separator);
			
			if (directorio.exists()) {

				for (int i = 0; i < listAd.size(); i++) {
					String ruta = "";
					ruta = listAd.get(i).getRuta();
					File file = new File(ruta);
					OutputStream out = null;
					try {
						out = new FileOutputStream(file);
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = listAd.get(i).getInputStream().read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}

						listAd.get(i).getInputStream().close();
						out.flush();
						out.close();

						log.debug("[copyFile] se copio el adjunto "
								+ listAd.get(i).toString());
						sw=true;
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

				}
				log.debug("[copyFile] se copiaron los archivos adjuntos");

			} else {
				if(directorio.mkdirs()){
					for (int i = 0; i < listAd.size(); i++) {
						String ruta = "";
						ruta = listAd.get(i).getRuta();
						File file = new File(ruta);
						OutputStream out = null;
						try {
							out = new FileOutputStream(file);
							int read = 0;
							byte[] bytes = new byte[1024];

							while ((read = listAd.get(i).getInputStream().read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}

							listAd.get(i).getInputStream().close();
							out.flush();
							out.close();

							log.debug("[copyFile] se copio el adjunto "
									+ listAd.get(i).toString());
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
					}	
				log.debug("[copyFile] se copiaron los archivos adjuntos");
				sw=true;
				}

				
			}

			return sw;

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e);
			return false;
		}

	}

	
	
	public void buscar(){
		
		//System.out.println();
		try {
			
//			this.listPlat = this.platBL.getList();
//			for (TAbm a : listPlat) {
//				List<TAbmSer> b=platBL.getTAbmSerXAbm(a.getId(), a.getId());
//				int c=0;
//				for (TAbmSer tAbmSer : b) {
//					if(tAbmSer.getEstado().equalsIgnoreCase("OBSERVADO")||tAbmSer.getEstado().equalsIgnoreCase("PENDIENTE")){
//						c=c+1;
//					}
//				}
//				a.setFaltantes(c);
//				if(c==0 && a.getFechafinalizado()==null){
//					System.out.println(a.getAbm()+", "+a.getFechaenvio()+", "+a.getFechafinalizado());
//				}
//				if(c!=0 && a.getFechafinalizado()!=null){
//					System.out.println(a.getAbm()+", "+a.getFechaenvio()+", "+a.getFechafinalizado());
//				}
//				platBL.update(a);
//			}
//			if(true){
//				return;
//			}
			
			ServiciosI ser= new ServiciosI();
			Result res=ser.obtenerAbm(nroABM);
			
			if(!res.getCode().equals(Code.OK)){
				SysMessage.error("Error al obtener los servicios");
				log.error("[actualizar]ERROR: "+res.getDescription());
				return;
			}
			listPlat= new ArrayList<TAbm>();
			String ta=(String) res.getData();
			TABMS t=gson.fromJson(ta,new TypeToken<TABMS>(){}.getType());
			if(t.getAbm()==null){
				SysMessage.error("No se pudo obtener el abm: "+nroABM+", contactar con el administrador");
//				res=ser.obtenerServiciosAbm(nroABM);
//				if(!res.getCode().equals(Code.OK)){
//					SysMessage.error("Error al obtener los servicios");
//					log.error("[actualizar]ERROR: "+res.getDescription());
//					return;
//				}
//				String lss=(String) res.getData();
//				
//				listServ= new ArrayList<TServicioABM>();
//				//serv= new TServicioABM();
//				listServ=gson.fromJson(lss,new TypeToken<ArrayList<TServicioABM>>(){}.getType());
//				List<TServicio> ts=new ArrayList<TServicio>();
//				ts=servBL.getList();
//				tsaux=new ArrayList<TServicio>();
//				for (TServicioABM a : listServ) {
//					for (TServicio b : ts) {
//						if(a.getCodServicio().equals(b.getIdServicio().intValue())){
//							tsaux.add(b);
//						}
//					}
//					
//				}
//				HashMap<Long,MuUsuario> hm= new HashMap<Long,MuUsuario>();
//				lisadmin=new ArrayList<MuUsuario>();
//				for(TServicio a : tsaux){
//					if(!a.getTSerAdms().isEmpty()){
//						MuUsuario u= usuBL.getUser(a.getTSerAdms().get(0).getId().getIdAdm().intValue());
//						hm.put((long)u.getUsuarioId(),u);	
//
//					}
//				}
//				for (Entry<Long, MuUsuario> entry : hm.entrySet()) {
//				    //Long key = entry.getKey();
//				    //List<String> value = entry.getValue();
//				    MuUsuario value=entry.getValue();
//				    lisadmin.add(value);
//
//				}
				return;
			}
			pla=new TAbm();
			pla.setId((long) 1);
			pla.setAbm(t.getAbm());
			pla.setSolicitante(t.getSolicitante());
			pla.setUsuario(t.getUsuario());
			pla.setEhumano(t.getEhumano());
			pla.setCargo(t.getCargo());
			pla.setAreasucursal(t.getAreasucursal());
			String f=t.getFechafin().substring(11, 19);
			pla.setFechafin((f.equals("00:00:00"))?t.getFechafin():"No tiene");
			pla.setObservacionAbm(t.getObservacion_abm());
			listPlat.add(pla);
			
			res=ser.obtenerServiciosAbm(nroABM);
			if(!res.getCode().equals(Code.OK)){
				SysMessage.error("Error al obtener los servicios");
				log.error("[actualizar]ERROR: "+res.getDescription());
				return;
			}
			String lss=(String) res.getData();
			
			listServ= new ArrayList<TServicioABM>();
			//serv= new TServicioABM();
			listServ=gson.fromJson(lss,new TypeToken<ArrayList<TServicioABM>>(){}.getType());
			List<TServicio> ts=new ArrayList<TServicio>();
			ts=servBL.getList();
			tsaux=new ArrayList<TServicio>();
			for (TServicioABM a : listServ) {
				for (TServicio b : ts) {
					if(a.getCodServicio().equals(b.getIdServicio().intValue())){
						if(!b.getDescripcion().equalsIgnoreCase("ADJUNTAR")){
							if(b.getTipoUsuario()!=null){
								if(!b.getTipoServicio().isEmpty()){
									tsaux.add(b);
								}
							}
							
						}
						
					}
				}
				
			}
			HashMap<Long,MuUsuario> hm= new HashMap<Long,MuUsuario>();
			lisadmin=new ArrayList<MuUsuario>();
			lisadmin2= new ArrayList<TUsuarioAtributo>();
			for(TServicio a : tsaux){
				if(!a.getTSerAdms().isEmpty()){
					
					List<TSerAdm>tt=a.getTSerAdms();
					for (TSerAdm tSerAdm : tt) {
						MuUsuario u= usuBL.getUser(tSerAdm.getId().getIdAdm());
						hm.put((long)u.getUsuarioId(),u);	
						TUsuarioAtributo us= new TUsuarioAtributo();
						us.setCi(String.valueOf(u.getUsuarioId()));
						us.setDpto(String.valueOf(a.getId()));
						us.setCorreo(u.getCorreo());
						us.setMemberOf(a.getDescripcion());
						lisadmin2.add(us);
					}
					

				}
			}
		

			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Gestion ABM:Buscar] Error al obtener el ABM: "+nroABM+" ",e);
			SysMessage.error("Error al obtener el ABM");
		}
	}
	
	public void procesar(){
		
		try {
//			List<TAbmAdm> aux=platBL.getListServicioPorABM(1);
//			List<TAbmAdm> ll= new ArrayList<TAbmAdm>();
//			for (TAbmAdm f : aux) {
//				if(!f.getTAbm().getEstadoProceso().equalsIgnoreCase("FINALIZADO")){
//					ll.add(f);
//				}
//			}
//			for (TAbmAdm b : ll) {
//				TAbmSer a = new TAbmSer();
//				a.setTServicio(b.getTServicio());
//				a.setTAbm(b.getTAbm());
//				a.setUsuarioAsignado(b.getUsuarioAsignado());
//				a.setObservacion(b.getObservacion());
//				a.setFecha(b.getFecha());
//				a.setAdjunto(b.getAdjunto());
//				a.setEstado(b.getEstado());
//				platBL.migrar(a);
//			}
//			List<TAbmSer> lll= platBL.migrar2();
//			int c=1;
//			for (TAbmSer a : lll) {
////				List<TSerAdm> liu=servBL.getListAdmPorServicio((long)1);
//
//				System.out.println(c);
//				if (c == 3) {
//					System.out.println("aqui");
//				}
//
//				TServicio ss = servBL.getServicio(a.getTServicio().getId());
//				if (ss.getTSerAdms() != null) {
//					if (!ss.getTSerAdms().isEmpty()) {
//						a.setIdUsuario(ss.getTSerAdms().get(0).getId().getIdAdm());
//						platBL.updateMigrar(a);
//
//					}
//
//				}
//				c = c + 1;
//			}
//			if(0==0){
//				return ;
//			}
			HashMap<Long,MuUsuario> hm= new HashMap<Long,MuUsuario>();
			lisadmin=new ArrayList<MuUsuario>();
			List<TAbmSer> lista2= new ArrayList<TAbmSer>();
			for(TUsuarioAtributo a : lisadmin2){
					MuUsuario u= usuBL.getUser(Long.valueOf(a.getCi()));
					hm.put((long)u.getUsuarioId(),u);	
					TAbmSer ap= new TAbmSer();
					TServicio s= servBL.getServicio(Long.valueOf(a.getDpto()));
					ap.setTServicio(s);
					ap.setIdUsuario(Long.valueOf(a.getCi()));
					ap.setTAbm(pla);
					ap.setContraseña(s.getPwd());
					ap.setEstado("PENDIENTE");
					lista2.add(ap);
			}
			
			for (Entry<Long, MuUsuario> entry : hm.entrySet()) {
			    //Long key = entry.getKey();
			    //List<String> value = entry.getValue();
			    MuUsuario value=entry.getValue();
			    lisadmin.add(value);

			}
			if(lisadmin.isEmpty()){
				SysMessage.warn("La lista de mail esta vacia");
				return;
			}
			
				TAbm ab=platBL.getTAbm(pla.getAbm());
				if(ab!=null){
					log.info("[procesar] no se guardo el abm "+pla.getAbm()+" por que ya existe en la bd");
					SysMessage.info("El ABM ya fue procesado, Se volvio a enviar");
					return;
				}
				pla.setId(null);
				pla.setEstadoProceso("PEN");
				pla.setEstado(true);
				pla.setFaltantes(lisadmin2.size());
				pla.setFechaenvio(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				pla.setObservacionQflow(observacion);
				platBL.save(pla,tsaux,lista2);
				controlerBitacora.accion(DescriptorBitacora.ABM, "Se guardo y envió correctamente el ABM: "+nroABM);
				
				log.info("[procesar] se guardo el abm "+pla.getAbm()+" en la bd");
				//SysMessage.info("Se envio y se guardo correctamente");
			
			for (Adjunto a : listaAdjunto) {
				String r=a.getRuta();
				r=r.replace("{abm}", pla.getAbm()+"");
				a.setRuta(r);
			}
				
			if(!listaAdjunto.isEmpty()){
				if(copyFile(listaAdjunto)){
					log.info("se copio los adjuntos a disco");
				}else{
				log.error("no se copiaron los adunto a disco");
				}
			}				
			MuUsuario as=usuBL.getUserLogin("villarroelj");
			lisadmin.add(as);
//			as=usuBL.getUserLogin("villarroelj");
//			lisadmin.add(as);
			support.util.Result result = mailBL.sendEmail(lisadmin, listaAdjunto,pla,tsaux,observacion);
			List<TAbmAdjunto> listAbmAdj=new ArrayList<TAbmAdjunto>();
			for (Adjunto a : listaAdjunto) {
				TAbmAdjunto adj= new TAbmAdjunto();
				adj.setNombre(a.getNombre());
				adj.setRuta(a.getRuta());
				adj.setEstado(true);
				listAbmAdj.add(adj);
//				File fichero = new File(a.getRuta());
//				if (fichero.delete())
//					  log.info("El fichero "+a.getRuta()+" ha sido borrado satisfactoriamente");
//					else
//					  log.error("El fichero "+a.getRuta()+" no puede ser borrado");
			}
			platBL.updateAdjunto(pla,listAbmAdj);
			if (result.getCode().equalsIgnoreCase(Code.OK)) {
				log.info("Se envio y se guardo correctamente el abm "+pla.getAbm()+" en la bd");
				SysMessage.info("Se guardo y envió correctamente el ABM: "+nroABM);
				limpiar();
				return;
				
			}
			
			log.error("error al enviar");
			SysMessage.error("No se envio correctamente");
		} catch (Exception e) {
			log.error("[procesar] error al enviar o guardar",e);
			SysMessage.error("Ocurrio un error al momento de guardar y enviar");
		}
		
	}
	
	public void limpiar(){
		lisadmin.clear();
		lisadmin2.clear();
		nroABM="";
		listaAdjunto.clear();
		observacion="";
		listPlat.clear();
		tsaux.clear();
		selectedAdjuntos=null;
	}
	
	public void obtenerAdm(String id){
		System.out.println(id);
		select="2";
	} 
	
	public void delAdmin(TUsuarioAtributo us){
			
		try {
			lisadmin2.remove(us);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			SysMessage.error("Error al eliminar de la lista");
		}

	}
	
//	public void guardar() {
//		
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
//				this.platBL.update(this.pla);
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

	

	public void editAdmin() {
		
		try {
			if (this.userListAdmin != null) {
				this.visibleNuevoEditar = true;
				select=userListAdmin.getCi();
				
			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
		
	}
	
	public void editar(){
		try {
			if(select.equalsIgnoreCase("-1")){
				SysMessage.warn("Seleccionar un Administrador");
				return;
			}
			lisadmin2.remove(userListAdmin);
			TServicio a= servBL.getServicio(Long.valueOf(userListAdmin.getDpto()));
			MuUsuario u= usuBL.getUser(Long.valueOf(select));
			TUsuarioAtributo us= new TUsuarioAtributo();
			us.setCi(String.valueOf(u.getUsuarioId()));
			us.setDpto(String.valueOf(a.getId()));
			us.setCorreo(u.getCorreo());
			us.setMemberOf(a.getDescripcion());
			lisadmin2.add(us);
			this.visibleNuevoEditar = false;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[Editar] error al edita la lista de administradores",e);
			SysMessage.error("Error al editar");
		}
	}

	public void editAdmin(TUsuarioAtributo muRol) {
		this.userListAdmin = muRol;
		editAdmin();
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

	

//	public boolean isVisibleDialog() {
//		return this.visibleDialog;
//	}
//
//	public void setVisibleDialog(boolean visibleDialog) {
//		this.visibleDialog = visibleDialog;
//	}

	

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

	

	
	
	public List<TAbmAdm> getListAdm() {
		return listAdm;
	}


	public void setListAdm(List<TAbmAdm> listAdm) {
		this.listAdm = listAdm;
	}


	public TAbmAdm getAdm() {
		return adm;
	}


	public void setAdm(TAbmAdm adm) {
		this.adm = adm;
	}


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_ABM, idAccion);
	}


	public List<TServicioABM> getListServ() {
		return listServ;
	}


	public void setListServ(List<TServicioABM> listServ) {
		this.listServ = listServ;
	}


	public TServicioABM getServ() {
		return serv;
	}


	public void setServ(TServicioABM serv) {
		this.serv = serv;
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

	public List<TServicio> getTsaux() {
		return tsaux;
	}

	public void setTsaux(List<TServicio> tsaux) {
		this.tsaux = tsaux;
	}

	public List<MuUsuario> getLisadmin() {
		return lisadmin;
	}

	public void setLisadmin(List<MuUsuario> lisadmin) {
		this.lisadmin = lisadmin;
	}

	public String[] getSelectedAdjuntos() {
		return selectedAdjuntos;
	}

	public void setSelectedAdjuntos(String[] selectedAdjuntos) {
		this.selectedAdjuntos = selectedAdjuntos;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}


	public List<TUsuarioAtributo> getLisadmin2() {
		return lisadmin2;
	}


	public void setLisadmin2(List<TUsuarioAtributo> lisadmin2) {
		this.lisadmin2 = lisadmin2;
	}



	public boolean isVisibleNuevoEditar() {
		return visibleNuevoEditar;
	}



	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		this.visibleNuevoEditar = visibleNuevoEditar;
	}





	


	
	
}
