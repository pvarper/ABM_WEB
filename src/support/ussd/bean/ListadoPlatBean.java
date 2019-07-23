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
import org.primefaces.model.DualListModel;

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuRol;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.AreaBL;
import support.ussd.business.PlataformaBL;
import support.ussd.model.TArea;
import support.ussd.model.TPlataforma;
import support.ussd.model.TPlataformaAdministrador;
import support.ussd.model.TPlataformaAdministradorPK;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ListadoPlatBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ListadoPlatBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private PlataformaBL platBL;
	
	@Inject
	private AreaBL areaBL;
	
	@Inject
	private UsuarioBL usuBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TPlataforma> listPlat;
	private TPlataforma pla;
	private String plaId;
	private boolean edit;
	
	private List<TArea> listAreas;
	private List<MuUsuario> listUsuarios;
	
	
//	private List<TRol> listRol;
//	private TRol det;
	private String detId;
	private boolean editDetalle;
	//private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;
	//private boolean visibleNuevoEditarDetalle;
	private String select;
	private String selectUsuario;
	private List<SelectItem> selectItems;
	private String radioAlta;
	
	private DualListModel<String> selectItemsUsuario;
	List<String> citiesSource = new ArrayList<String>();
    List<String> citiesTarget = new ArrayList<String>();
    List<String> citiesTargetAux = new ArrayList<String>();


	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TPlataforma();
			this.visibleNuevoEditar = false;
			//this.visibleNuevoEditarDetalle = false;
			this.listPlat = this.platBL.getListDocumentos();
			radioAlta="1";
			this.selectItems = new ArrayList<SelectItem>();			
			this.listAreas = this.areaBL.getListArea();
			
			this.selectItems.add(new SelectItem("-1", "-- Seleccionar Area --"));
			
			for ( TArea a : listAreas) {
				SelectItem sel = new SelectItem(a.getId(), a.getNombre());
				this.selectItems.add(sel);
			}
			
			
	        
			this.listUsuarios = this.usuBL.getUsers();
			for ( MuUsuario a : listUsuarios) {
				citiesSource.add(a.getLogin());
			}
			
			selectItemsUsuario = new DualListModel<String>(citiesSource, citiesTarget);
	
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	

	public void guardar() {
		
		if (select.equalsIgnoreCase("-1")){
			SysMessage.warn("Debe seleccionar un Area");
			return;
		}	
		
		if (selectItemsUsuario.getTarget().isEmpty()){
			SysMessage.warn("Debe seleccionar al menos un Administrador");
			return;
		}
	
		String str = this.platBL.validate(this.pla, this.plaId);
		if (!str.isEmpty()) {
			SysMessage.warn(str);
			return;
		}
		
		
		
		
		
		try {
			if (!this.edit) {
				//this.pla.setTRols(this.listRol);
				this.pla.setEstado(true);
				
				pla.setNombre(pla.getNombre().trim());
				pla.setDescripcion(pla.getDescripcion().trim());
				pla.setUso("DOCUMENTACION");
				TArea area=areaBL.getUser(Long.valueOf(select));
				pla.setPlaArea(area);
				this.platBL.savePlaAdm(this.pla,selectItemsUsuario);
				this.controlerBitacora.insert(DescriptorBitacora.PLATAFORMA, 
				          this.pla.getId()+"", this.pla.getNombre());
				this.visibleNuevoEditar = false;
			} else {
				Long id = Long.valueOf(Long.parseLong(this.plaId));
				this.pla.setId(id);
				TArea area=areaBL.getUser(Long.valueOf(select));
				pla.setPlaArea(area);			
				List<TPlataformaAdministrador> listaPlatAdmin= new ArrayList<TPlataformaAdministrador>();
				for (String a : selectItemsUsuario.getTarget()) {
					System.out.println(a);
					TPlataformaAdministrador b= new TPlataformaAdministrador();
					TPlataformaAdministradorPK c= new TPlataformaAdministradorPK();
					c.setPlataformaId(pla.getId());
					MuUsuario u=usuBL.getUserLogin(a);
					c.setUsuarioId(u.getUsuarioId());
					b.setPlataformaId(pla);
					b.setUsuarioId(u);
					b.setDescripcion("prueba3");
					b.setId(c);
					listaPlatAdmin.add(b);
				}
				pla.setPlataforma(listaPlatAdmin);
				this.platBL.update(this.pla);
				this.controlerBitacora.update(DescriptorBitacora.PLATAFORMA, 
				          this.pla.getId()+"", this.pla.getNombre());
				this.visibleNuevoEditar = false;
			}
			this.listPlat = this.platBL.getListDocumentos();
			SysMessage.info("Se guardó correctamente.");
		} catch (Exception e) {
//			log.error("[savePlataforma] error al momento de modificar o guardar: "
//					+ this.pla.getId() + " " + e.getLocalizedMessage(), e);
			e.printStackTrace();
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
				this.listPlat = this.platBL.getListDocumentos();
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

	public String delete(TPlataforma plau) {
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
		this.pla = new TPlataforma();
		radioAlta="1";
//		this.det = new TRol();
//		this.listRol = new ArrayList<TRol>();
	}

	public void ediTPlataforma() {
		
		try {
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";								
				
				//this.listRol = new ArrayList<TRol>();
				//listRol=platBL.getListRolPlataforma(pla.getId());
				this.select = this.pla.getPlaArea().getId()+"";
				citiesTarget.clear();
				
				List<TPlataformaAdministrador> a = pla.getPlataforma();
					for (TPlataformaAdministrador b : a) {
						
						citiesTarget.add(b.getUsuarioId().getLogin());
					}
					
					
					selectItemsUsuario = new DualListModel<String>(citiesSource, citiesTarget);	
				
				
				
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

	public void ediTPlataforma(TPlataforma muRol) {
		this.pla = muRol;
		ediTPlataforma();
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

	public List<TPlataforma> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TPlataforma> listPlat) {
		this.listPlat = listPlat;
	}

	public TPlataforma getpla() {
		return this.pla;
	}

	public void setpla(TPlataforma pla) {
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

	
	
	



	public String getRadioAlta() {
		return radioAlta;
	}

	public void setRadioAlta(String radioAlta) {
		this.radioAlta = radioAlta;
	}

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.LISTADO_PLATAFORMA, idAccion);
	}



	public String getSelect() {
		return select;
	}



	public void setSelect(String select) {
		this.select = select;
	}



	public List<SelectItem> getSelectItems() {
		return selectItems;
	}



	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}



	



	public DualListModel<String> getSelectItemsUsuario() {
		return selectItemsUsuario;
	}



	public void setSelectItemsUsuario(DualListModel<String> selectItemsUsuario) {
		this.selectItemsUsuario = selectItemsUsuario;
	}



	public List<String> getCitiesSource() {
		return citiesSource;
	}



	public void setCitiesSource(List<String> citiesSource) {
		this.citiesSource = citiesSource;
	}



	public List<String> getCitiesTarget() {
		return citiesTarget;
	}



	public void setCitiesTarget(List<String> citiesTarget) {
		this.citiesTarget = citiesTarget;
	}



	public String getSelectUsuario() {
		return selectUsuario;
	}



	public void setSelectUsuario(String selectUsuario) {
		this.selectUsuario = selectUsuario;
	}



	public List<String> getCitiesTargetAux() {
		return citiesTargetAux;
	}



	public void setCitiesTargetAux(List<String> citiesTargetAux) {
		this.citiesTargetAux = citiesTargetAux;
	}





	
	
}
