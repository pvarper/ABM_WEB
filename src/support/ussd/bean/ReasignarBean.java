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

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.ServiciosBL;
import support.ussd.business.TAbmBL;
import support.ussd.model.TAbm;
import support.ussd.model.TAbmSer;
import support.ussd.model.TServicio;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ReasignarBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ReasignarBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private ServiciosBL platBL;
	
	@Inject
	private TAbmBL abmBL;
	
	@Inject
	private UsuarioBL utBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TServicio> listPlat;
	private List<MuUsuario> listUsuarioAdd;
	private TServicio pla;
	private MuUsuario usu;
	private String plaId;
	private List<SelectItem> selectItems;
	private String select;
	
	private List<SelectItem> selectItemsA;
	private String selectA;
	
	private List<SelectItem> selectItemsAbm;
	private String selectAbm;
	
	private String[] selectedMember;
	private List<SelectItem> selectMembers;
	

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TServicio();
			this.usu = new MuUsuario();
			this.listPlat = this.platBL.getList();
			listUsuarioAdd=new ArrayList<MuUsuario>();
			select="-1";
			selectAbm="-1";
			selectA="-1";
			fillSelectItems();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	private void fillSelectItems()
			  throws Exception
			{
		this.selectItemsAbm = new ArrayList<SelectItem>();
		  this.selectItemsAbm.add(new SelectItem("-1", "-- Seleccionar Abm Pendiente --"));
		
			  this.selectItems = new ArrayList<SelectItem>();
			  this.selectItems.add(new SelectItem("-1", "-- Seleccionar Administrador --"));
			  
			  this.selectItemsA = new ArrayList<SelectItem>();
			  this.selectItemsA.add(new SelectItem("-1", "-- Seleccionar Administrador --"));
			  
			  selectMembers = new ArrayList<SelectItem>();
			  
			  List<MuUsuario> listaRol = this.utBL.getUsersAdmin(); 
			  for (MuUsuario role : listaRol)
			  {
			    SelectItem sel = new SelectItem(role.getUsuarioId(), role.getNombre());
			    this.selectItems.add(sel);
			    this.selectItemsA.add(sel);
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
	
	
	
	public void reasignar() {
		
		if(select.equalsIgnoreCase("-1")){
			SysMessage.warn("Debe Seleccionar un Administrador");
			return;
		}else{
			if(selectAbm.equalsIgnoreCase("-1")){
				SysMessage.warn("Debe Seleccionar un ABM");
				return;
			}else{
				if(selectedMember.length==0){
					SysMessage.warn("Debe Seleccionar al menos un Servicio");
					return;
				}else{
					if(selectA.equalsIgnoreCase("-1")){
						SysMessage.warn("Debe Seleccionar el administrador al que desea reasignar");
						return;
					}
				}
			}
		}
		if(select.equalsIgnoreCase(selectA)){
			SysMessage.warn("La reasignacion debe hacerse entre administradores diferentes");
			return;
		}
		
		try {
			
			for (String id : selectedMember) {
				if(abmBL.reasignar(selectA, selectAbm, id)){
					SysMessage.info("se reasigno correctamente");
				}else{
					SysMessage.warn("no se reasigno");
				}
			}
			
		
//			SysMessage.info("Se reasigno correctamente");
		} catch (Exception e) {
			log.error("[reasignar] error al momento de reasignar", e);
			SysMessage.error("No se pudo reasignar, consultar con el administrador");
		}
	}

	public void obtenerPendientes(){
		  
	  	try {
	  		  this.selectItemsAbm = new ArrayList<SelectItem>();
	  		  this.selectItemsAbm.add(new SelectItem("-1", "-- Seleccionar Abm Pendiente --"));
	  		  selectMembers = new ArrayList<SelectItem>();
	  		  
	  		  if(!select.equalsIgnoreCase("-1")){
	  			List<Integer> luax=abmBL.obtenerServPendienteXusuarioDistinct(Long.valueOf(select));
				  for (Integer a : luax)
				  {
				    SelectItem sel = new SelectItem(a.toString(), a.toString());
				    this.selectItemsAbm.add(sel);
					log.debug("se agrego el abm a la lista:"+sel.getValue());


				  }
				  if(luax.isEmpty()){
					  log.debug("el administrador no tiene abm' pendientes");
					  SysMessage.warn("El administrador no tiene abm's pendientes");
				  }
	  		  }else{
	  			 SysMessage.warn("Debe Seleccionar un Administrador");
	  		  }
	  		 
			
		} catch (Exception e) {
			e.printStackTrace();
		}

}
	
	public void obtenerServiciosXAbmXAdmin(){
		  
	  	try {
	  		  this.selectMembers = new ArrayList<SelectItem>();
	  		  
	  		  if(!select.equalsIgnoreCase("-1")){
	  			  TAbm ab= abmBL.getTAbm(Integer.valueOf(selectAbm));
	  			List<TAbmSer> luax=abmBL.getTAbmSerXAbmXAdm(ab.getId(), Long.valueOf(select));
				  for (TAbmSer a : luax)
				  {
					TServicio ss= platBL.getServicio(a.getTServicio().getId());  
				    SelectItem sel = new SelectItem(ss.getId(),ss.getDescripcion());
				    this.selectMembers.add(sel);
					log.debug("se agrego el servicio a la lista:"+sel.getValue());


				  }
				  if(luax.isEmpty()){
					  log.debug("el abm no tiene servicios pendientes");
					  SysMessage.warn("el abm no tiene servicios pendientes");
				  }
	  		  }else{
	  			 SysMessage.warn("Debe Seleccionar un Administrador");
	  		  }
	  		 
			
		} catch (Exception e) {
			e.printStackTrace();
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

	public void newplauerimiento() {
		this.pla = new TServicio();
		select="-1";
	}

	
	
	


	public String getRoleId() {
		return this.plaId;
	}

	public void setRoleId(String RoleId) {
		this.plaId = RoleId;
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
		return this.controlPrivilegio.isAuthorized(FormularioID.REASIGNAR_ABM, idAccion);
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

	public List<SelectItem> getSelectItemsAbm() {
		return selectItemsAbm;
	}

	public void setSelectItemsAbm(List<SelectItem> selectItemsAbm) {
		this.selectItemsAbm = selectItemsAbm;
	}

	public String getSelectAbm() {
		return selectAbm;
	}

	public void setSelectAbm(String selectAbm) {
		this.selectAbm = selectAbm;
	}

	public String[] getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(String[] selectedMember) {
		this.selectedMember = selectedMember;
	}

	public List<SelectItem> getSelectMembers() {
		return selectMembers;
	}

	public void setSelectMembers(List<SelectItem> selectMembers) {
		this.selectMembers = selectMembers;
	}

	public List<SelectItem> getSelectItemsA() {
		return selectItemsA;
	}

	public void setSelectItemsA(List<SelectItem> selectItemsA) {
		this.selectItemsA = selectItemsA;
	}

	public String getSelectA() {
		return selectA;
	}

	public void setSelectA(String selectA) {
		this.selectA = selectA;
	}

	

	





	
	
}
