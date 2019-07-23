package support.ussd.bean;

import java.io.Serializable;
import java.util.List;

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
import support.ussd.business.AreaBL;
import support.ussd.business.TerminalesBL;
import support.ussd.model.TArea;
import support.ussd.model.TTerminales;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class TerminalesBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(TerminalesBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private TerminalesBL platBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TTerminales> listPlat;
	private TTerminales pla;
	private String plaId;
	private boolean edit;
	private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;

	
	


	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TTerminales();
			this.visibleNuevoEditar = false;
			this.listPlat = this.platBL.getList();
			
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}



	public void guardar() {
		
//		if(listRol.isEmpty()){
//			SysMessage.warn("Se debe registrar al menos un rol");
//			return;
//		}
//		
		String str = this.platBL.validate(this.pla, this.plaId);
		if (!str.isEmpty()) {
			SysMessage.warn(str);
			return;
		}
		try {
			if (!this.edit) {
				this.pla.setEstado(true);
				pla.setNombre(pla.getNombre().trim());
				pla.setIp(pla.getIp().trim());
				this.platBL.save(this.pla);
				this.controlerBitacora.insert(DescriptorBitacora.TERMINALES, 
				          this.pla.getId()+"", this.pla.getNombre());
				this.visibleNuevoEditar = false;
			} else {
				Long id = Long.valueOf(Long.parseLong(this.plaId));
				this.pla.setId(id);
				pla.setNombre(pla.getNombre().trim());
				pla.setIp(pla.getIp().trim());
				this.platBL.update(this.pla);
				this.controlerBitacora.update(DescriptorBitacora.TERMINALES, 
				          this.pla.getId()+"", this.pla.getNombre());
				this.visibleNuevoEditar = false;
			}
			this.listPlat = this.platBL.getList();
			SysMessage.info("Se guardó correctamente.");
		} catch (Exception e) {
			log.error("[saveTerminales] error al momento de modificar o guardar: "
					+ this.pla.getId() + " " + e.getLocalizedMessage(), e);
			SysMessage.error("Fallo al guardar en la Base de Datos.");
		}
	}



	

	

	public String delete() {
		if (this.pla != null) {
			try {
				//this.pla.setBaja(Boolean.valueOf(false));
				this.platBL.delete(this.pla);
				this.controlerBitacora.delete(DescriptorBitacora.TERMINALES,
						this.pla.getId() + "", "");
				this.listPlat = this.platBL.getList();
				SysMessage.info("Se eliminó correctamente.");
			} catch (Exception e) {
				log.error("[deleteTerminal]  error al eliminar el Terminal"
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


	public String delete(TTerminales plau) {
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
		this.pla = new TTerminales();

	}

	public void ediTArea() {
		
		try {
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				//this.listRol = new ArrayList<TRol>();
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

	public void ediTArea(TTerminales muRol) {
		this.pla = muRol;
		ediTArea();
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

	public List<TTerminales> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TTerminales> listPlat) {
		this.listPlat = listPlat;
	}

	public TTerminales getpla() {
		return this.pla;
	}

	public void setpla(TTerminales pla) {
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

	public boolean isVisibleDialog() {
		return this.visibleDialog;
	}

	public void setVisibleDialog(boolean visibleDialog) {
		this.visibleDialog = visibleDialog;
	}

	public boolean isVisibleNuevoEditar() {
		return this.visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		this.visibleNuevoEditar = visibleNuevoEditar;
	}

	

	
	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_TERMINALES, idAccion);
	}





	
	
}
