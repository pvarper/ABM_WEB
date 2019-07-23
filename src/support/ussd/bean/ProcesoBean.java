package support.ussd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.id.FormularioID;
import support.user.controler.ControlPrivilegio;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.ProcesosBL;
import support.ussd.model.TDh;
import support.ussd.model.TFreem;
import support.ussd.model.TProceso;
import support.ussd.model.TTop;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ProcesoBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ProcesoBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private ProcesosBL platBL;
	
	 
	  private List<TDh> listTransaccion;
	  private List<TFreem> listFreem;

	private List<TTop> listPlat;
	private TTop pla;
	//private String plaId;
	//private boolean edit;
	private List<TProceso> listDetalle;
	private TProceso det;
	//private String detId;
	//private boolean editDetalle;
	//private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;
	
	private boolean visibleNuevoEditarDh;
	private boolean visibleNuevoEditarFreem;
//	private boolean visibleNuevoEditarDetalle;


	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TTop();
			this.visibleNuevoEditar = false;
			this.visibleNuevoEditarDh=false;
			//this.visibleNuevoEditarDetalle = false;

			this.listPlat = this.platBL.getList();
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
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

//	public void newplauerimiento() {
//		this.edit = false;
//		this.visibleNuevoEditar = true;
//		this.pla = new TTop();
//		this.det = new TProceso();
//		this.listDetalle = new ArrayList<TProceso>();
//	}

	public void ediTPlataforma() {
		if (this.pla != null) {
			this.visibleNuevoEditar = true;
			//this.plaId = this.pla.getId() + "";
			this.listDetalle = new ArrayList<TProceso>();
			this.listDetalle = this.platBL.getTtopProcesos(this.pla.getId());
			//this.edit = true;
		} else {
			log.warn("[editar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontro ningun registro seleccionado.");
		}
	}

	public void ediTPlataforma(TTop muRol) {
		this.pla = muRol;
		ediTPlataforma();
	}
	
	public void verDh() {
		if (this.pla != null) {
			this.visibleNuevoEditarDh = true;
			//this.plaId = this.pla.getId() + "";
			this.listTransaccion = new ArrayList<TDh>();
			this.listTransaccion = this.platBL.getTtopDh(this.pla.getId());
			//this.edit = true;
		} else {
			log.warn("[editar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontro ningun registro seleccionado.");
		}
	}

	public void verDh(TTop muRol) {
		this.pla = muRol;
		verDh();
	}
	
	public void verFreem() {
		if (this.pla != null) {
			this.visibleNuevoEditarFreem = true;
			//this.plaId = this.pla.getId() + "";
			this.listFreem = new ArrayList<TFreem>();
			this.listFreem = this.platBL.getTtopFreem(this.pla.getId());
			//this.edit = true;
		} else {
			log.warn("[editar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontro ningun registro seleccionado.");
		}
	}

	public void verFreem(TTop muRol) {
		this.pla = muRol;
		verFreem();
	}

	
//	public String getRoleId() {
//		return this.plaId;
//	}

//	public void setRoleId(String RoleId) {
//		this.plaId = RoleId;
//	}

//	public Boolean getEdit() {
//		return Boolean.valueOf(this.edit);
//	}
//
//	public void setEdit(Boolean edit) {
//		this.edit = edit.booleanValue();
//	}

	

//	
//	public String getplaId() {
//		return this.plaId;
//	}
//
//	public void setplaId(String plaId) {
//		this.plaId = plaId;
//	}

//	public void setEdit(boolean edit) {
//		this.edit = edit;
//	}

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

	

//	public String getDetId() {
//		return this.detId;
//	}
//
//	public void setDetId(String detId) {
//		this.detId = detId;
//	}

	public List<TTop> getListPlat() {
		return listPlat;
	}

	public void setListPlat(List<TTop> listPlat) {
		this.listPlat = listPlat;
	}

	public TTop getPla() {
		return pla;
	}


	public void setPla(TTop pla) {
		this.pla = pla;
	}

	public List<TProceso> getListDetalle() {
		return listDetalle;
	}





	public void setListDetalle(List<TProceso> listDetalle) {
		this.listDetalle = listDetalle;
	}





	public TProceso getDet() {
		return det;
	}





	public void setDet(TProceso det) {
		this.det = det;
	}





	public boolean isVisibleNuevoEditarDh() {
		return visibleNuevoEditarDh;
	}





	public List<TDh> getListTransaccion() {
		return listTransaccion;
	}





	public void setListTransaccion(List<TDh> listTransaccion) {
		this.listTransaccion = listTransaccion;
	}





	public void setVisibleNuevoEditarDh(boolean visibleNuevoEditarDh) {
		this.visibleNuevoEditarDh = visibleNuevoEditarDh;
	}





	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_PROCESOS, idAccion);
	}





	public List<TFreem> getListFreem() {
		return listFreem;
	}





	public void setListFreem(List<TFreem> listFreem) {
		this.listFreem = listFreem;
	}





	public boolean isVisibleNuevoEditarFreem() {
		return visibleNuevoEditarFreem;
	}





	public void setVisibleNuevoEditarFreem(boolean visibleNuevoEditarFreem) {
		this.visibleNuevoEditarFreem = visibleNuevoEditarFreem;
	}

	

	

	
	
	
}
