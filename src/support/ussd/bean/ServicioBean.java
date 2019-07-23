package support.ussd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import servicios.Result;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.ServiciosBL;
import support.ussd.model.TSerAdm;
import support.ussd.model.TServicio;
import support.ussd.model.TServicioABM;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ServicioBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ServicioBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private ServiciosBL platBL;
	
	@Inject
	private UsuarioBL utBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TServicio> listPlat;
	private List<MuUsuario> listUsuarioAdd;
	private TServicio pla;
	private MuUsuario usu;
	private String plaId;
	private boolean edit;
	
	
//	private List<TRol> listRol;
//	private TRol det;
	private String detId;
	private boolean editDetalle;
	//private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;
	//private boolean visibleNuevoEditarDetalle;
	
	private List<SelectItem> selectItems;
	private String select;
	
	private static final Gson gson= new Gson();

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TServicio();
			this.usu = new MuUsuario();
			this.visibleNuevoEditar = false;

			//this.visibleNuevoEditarDetalle = false;
			this.listPlat = this.platBL.getList();
			listUsuarioAdd=new ArrayList<MuUsuario>();
			select="-1";
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
			  List<MuUsuario> listaRol = this.utBL.getUsersAdmin(); 
			  for (MuUsuario role : listaRol)
			  {
			    SelectItem sel = new SelectItem(role.getUsuarioId(), role.getNombre());
			    this.selectItems.add(sel);
			  }
	}
	
	public void buscarServiciosXadmin(){
		try {
			if(select.equalsIgnoreCase("-1")){
				this.listPlat = this.platBL.getList();
				SysMessage.info("Se filtraron todos los servicios");
				return;
				}
			listPlat=platBL.getListServicioPorAdmin(Long.parseLong(select));
			//select="-1";
			log.info("[buscarServiciosXadmin] Se obtuvo correctamente los servicios del usuario con id:"+select );
			SysMessage.info("Finalizó la busqueda correctamente");
			
		} catch (Exception e) {
			log.error("[buscarServiciosXadmin] error al momento de buscar los servicios por administrador", e);
			SysMessage.error("Error de Codigo contactar al administrador");
		}
	}
	
	public void actualizar() {
		
	
		try {
			this.listPlat = this.platBL.getList();
			ServiciosI ser= new ServiciosI();
			Result res=ser.obtenerAllServicios();
			if(!res.getCode().equals(Code.OK)){
				SysMessage.error("Error al obtener los servicios");
				log.error("[actualizar]ERROR: "+res.getDescription());
				return;
			}
			String lss=(String) res.getData();
			
			List<TServicioABM> ls=gson.fromJson(lss,new TypeToken<ArrayList<TServicioABM>>(){}.getType());
			if(listPlat.isEmpty()){
				for (TServicioABM a : ls) {
					if(a.getEstado().equalsIgnoreCase("Habilitado")){
						TServicio s= new TServicio();
						s.setIdServicio((long)a.getCodServicio());
						s.setDescripcion(a.getDescripcion());
						s.setTipoServicio(a.getTipoServicio());
						s.setEstado(true);
						this.platBL.save(s);
					}
					
				}	
						
			}else{
				for (TServicioABM a : ls) {					
					TServicio s= platBL.getServicioABM(a.getCodServicio());
					if(s==null){
						s= new TServicio();
						s.setIdServicio((long)a.getCodServicio());
						s.setDescripcion(a.getDescripcion());
						s.setTipoServicio(a.getTipoServicio());
						s.setEstado(true);
						this.platBL.save(s);
					}else{
						if(a.getEstado().equalsIgnoreCase("DESHABILITADO")){
							platBL.delete(s);
						}else{

								s.setIdServicio((long)a.getCodServicio());
								s.setDescripcion(a.getDescripcion());
								s.setTipoServicio(a.getTipoServicio());
								s.setEstado(true);
								List<TSerAdm> ll=platBL.getListAdmPorServicio(1);
								List<MuUsuario> laux=new ArrayList<MuUsuario>();
								for (TSerAdm f : ll) {
									if(f.getId().getIdServicio().equals(s.getId())) {
										MuUsuario u= utBL.getUser(f.getId().getIdAdm());
										laux.add(u);
									}
								}
								this.platBL.update(s,laux);
							
						}
					}
				}	
			}
			this.listPlat = this.platBL.getList();
			SysMessage.info("Se guardó correctamente.");
			controlerBitacora.accion(DescriptorBitacora.SERVICIO, "Se actualizo correctamente la lista de servicios");
		} catch (Exception e) {
			log.error("[actualiza] error al momento de actuializar los servicios", e);
			SysMessage.error("Fallo al guardar en la Base de Datos.");
		}
	}
	
	public void guardar() {
		
//		if(select.equalsIgnoreCase("-1")){
//			SysMessage.warn("Debe seleccionar un administrador");
//			return;
//		}
		
//		try {
//			MuUsuario id=utBL.getUser(Integer.valueOf(select));
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//			SysMessage.error("Error al obtener el administrador");
//		}
		String str = this.platBL.validate(this.pla, this.plaId);
		if (!str.isEmpty()) {
			SysMessage.warn(str);
			return;
		}
		try {
			if (!this.edit) {

			} else {
				Long id = Long.valueOf(Long.parseLong(this.plaId));
				this.pla.setId(id);
				//this.pla.setTRols(this.listRol);
				//pla.setUso((radioAlta)?"DOCUMENTACION":"ALTA");
				pla.setDescripcion(pla.getDescripcion().trim());

				this.platBL.update(this.pla,listUsuarioAdd);
				this.controlerBitacora.update(DescriptorBitacora.SERVICIO, 
				          this.pla.getId()+"", this.pla.getDescripcion());
				this.visibleNuevoEditar = false;
			}
			//this.listPlat = this.platBL.getList();
			SysMessage.info("Se guardó correctamente.");
		} catch (Exception e) {
			log.error("[saveServicio] error al momento de modificar o guardar: "
					+ this.pla.getId() + " " + e.getLocalizedMessage(), e);
			SysMessage.error("Fallo al guardar en la Base de Datos.");
		}
	}

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

	public String delete() {
		if (this.pla != null) {
			try {
				//this.pla.setBaja(Boolean.valueOf(false));
				this.platBL.delete(this.pla);
				this.controlerBitacora.delete(DescriptorBitacora.PLATAFORMA,
						this.pla.getId() + "", "");
				this.listPlat = this.platBL.getList();
				SysMessage.info("Se eliminó correctamente.");
			} catch (Exception e) {
				log.error("[deletePlataforma]  error al eliminar el plauerimiento"
						+ this.pla.getId() + "  " + e);
				e.printStackTrace();
				SysMessage.error("Fallo al eliminar.");
			}
		} else {
			log.warn("[eliminar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontró ningun registro seleccionado.");
		}
		return "";
	}

//	public String deleteDetalle(TRol plau) {
//		this.det = plau;
//		return deleteDetalle();
//	}

	public String delete(TServicio plau) {
		this.pla = plau;
		return delete();
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

	public void newplauerimiento() {
		this.edit = false;
		this.visibleNuevoEditar = true;
		this.pla = new TServicio();
		select="-1";
//		this.det = new TRol();
//		this.listRol = new ArrayList<TRol>();
	}

	public void ediTPlataforma() {
		
		try {
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				listUsuarioAdd= new ArrayList<MuUsuario>();
				List<TSerAdm> l=platBL.getListAdmPorServicio(this.pla.getId());
				for (TSerAdm tSerAdm : l) {
					
					if(tSerAdm.getId().getIdServicio().equals(this.pla.getId())){
						MuUsuario u=utBL.getUser(tSerAdm.getId().getIdAdm());
						listUsuarioAdd.add(u);
					}
					
				}
				//this.listRol = new ArrayList<TRol>();
				//listRol=platBL.getListRolPlataforma(pla.getId());

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

	public void ediTPlataforma(TServicio muRol) {
		this.pla = muRol;
		ediTPlataforma();
	}
	
	public void agregarAdmin(){
		

			if(select.equalsIgnoreCase("-1")){
			SysMessage.warn("Debe seleccionar un administrador");
			return;
			}
		
			try {
				usu=utBL.getUser(Long.valueOf(select));
				for (MuUsuario a : listUsuarioAdd) {
					if(a.getLogin().equals(usu.getLogin())){
						SysMessage.warn("No se permite administradores repetidos");
						return;
					}
				}
				listUsuarioAdd.add(usu);
				select="-1";
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				SysMessage.error("Error al obtener el administrador");
			}

	}
	
	public void delAdmin(MuUsuario us){
		

	
		try {
			listUsuarioAdd.remove(us);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			SysMessage.error("Error al eliminar de la lista");
		}

	}
	
	
	

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

	public List<TServicio> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TServicio> listPlat) {
		this.listPlat = listPlat;
	}

	public TServicio getpla() {
		return this.pla;
	}

	public void setpla(TServicio pla) {
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


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_SERVICIOS, idAccion);
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





	
	
}
