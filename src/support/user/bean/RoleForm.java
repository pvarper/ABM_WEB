package support.user.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import support.id.FormularioID;
import support.id.ParametroID;
import support.user.business.RoleBL;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.dao.FormularioDAO;
import support.user.ldap.DescriptorBitacora;
import support.user.model.AccionModel;
import support.user.model.MuAccion;
import support.user.model.MuRol;
import support.user.model.MuRolFormulario;
import support.user.model.MuRolFormularioPK;
import support.user.model.MuUsuario;
import support.user.model.RolModel;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class RoleForm implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(RoleForm.class);
	
	private static final String MENU_TYPE = "menu";
	private static final String FORM_TYPE = "formulario";
	private static final String ACTION_TYPE = "accion";
	
	ControlPrivilegio controlPrivilegio;
	
	@Inject
	private RoleBL roleBL;
	
	@Inject
	private UsuarioBL usuarioBL;

	@Inject
	private ControlerBitacora controlerBitacora;

	private List<MuRol> listRole;

	private MuRol role = new MuRol();
	private String roleId;
	private boolean edit;

	private TreeNode roles;
	private TreeNode[] nodosSeleccionados;
	@Inject
	private FormularioDAO daoFormulario;
	private boolean visibleDialog = false;
	private Integer rolId;

	private String selectNameRol;
	
	private boolean visibleNuevoEditar;

	@PostConstruct
	public void init() {
		try {
			controlPrivilegio = ControlPrivilegio.getInstanceControl();
			cargarRoles(0);
			role = new MuRol();
			this.visibleNuevoEditar = false;
			listRole = roleBL.getRoles();
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	public void cargarRoles(Integer rolEditarId) {
		if (rolEditarId == 1) {
			SysMessage.info("No se permite editar el Rol Administracion");
		} else {
			if (rolEditarId > 0) {
				visibleDialog = true;
				this.rolId = rolEditarId;
			}
			roles = new DefaultTreeNode(new RolModel(0, "", rolEditarId), null);
			roles.setExpanded(true);
			try {
				List<Object[]> lmenus = daoFormulario.findPadresPermisos(rolEditarId);
				for (Object[] formulario : lmenus) {
					cargarSubMenus(formulario, roles, rolEditarId);
				}
			} catch (Exception e) {
				log.error("[cargarRoles] Fallo al cargar los roles.", e);
			}
		}
	}

	private void cargarSubMenus(Object[] formulario, TreeNode tree,
			Integer rolEditarId) {
		Integer idFormulario;
		if(formulario[0] instanceof BigDecimal){
			idFormulario = ((BigDecimal) formulario[0]).intValue();	
		}if(formulario[0] instanceof BigInteger){
			idFormulario = ((BigInteger) formulario[0]).intValue();
		}
		else
			idFormulario = (Integer) formulario[0];
		
		String nombreFormulario = String.valueOf(formulario[1]);
		String permisoFormulario = String.valueOf(formulario[2]);
		List<Object[]> lHijos = daoFormulario.findHijosPermisos(idFormulario,
				rolEditarId);
		if (lHijos != null && !lHijos.isEmpty()) {
			TreeNode newtree = new DefaultTreeNode(MENU_TYPE, new RolModel(
					idFormulario, nombreFormulario, rolEditarId), tree);
			for (Object[] ff : lHijos) {
				cargarSubMenus(ff, newtree, rolEditarId);
			}
			newtree.setSelected(permisoFormulario.trim().equals("1"));
			newtree.setExpanded(true);

		} else {
			TreeNode newtree = new DefaultTreeNode(FORM_TYPE, new RolModel(
					idFormulario, nombreFormulario, rolEditarId), tree);
			newtree.setSelected(permisoFormulario.trim().equals("1"));
			newtree.setExpanded(true);
			/********************/
			List<MuAccion> listAcc = obtebnerAccionesPrivilegio(idFormulario,
					rolEditarId, permisoFormulario.trim().equals("1"));
			for (Iterator<MuAccion> iterator = listAcc.iterator(); iterator.hasNext();) {
				MuAccion muAccion = iterator.next();
				TreeNode newTreeAcc = new DefaultTreeNode(ACTION_TYPE, new AccionModel(muAccion.getId(), muAccion.getNombre(),
								idFormulario, rolEditarId), newtree);
				newTreeAcc.setSelected(muAccion.getEstado());
				newTreeAcc.setExpanded(true);
			}

			/********************/
		}
	}

	/*
	 * 
	 * */
	private List<MuAccion> obtebnerAccionesPrivilegio(Integer formId, Integer rolId, boolean navegar) {
		MuRolFormulario muRolFormulario = daoFormulario.findPrivilegios(formId,
				rolId);
		String privilegios = "";
		if (muRolFormulario != null) {
			privilegios = muRolFormulario.getPrivilegio();
			if (privilegios == null)
				privilegios = "";
		}
		List<String> listPrivilegios = Arrays.asList(privilegios.split("[,]"));
		List<MuAccion> lisAccionesForm = daoFormulario.findAccionesFormulario(formId);
		for (Iterator<MuAccion> iterator = lisAccionesForm.iterator(); iterator
				.hasNext();) {
			MuAccion muAccion = iterator.next();
			muAccion.setEstado(listPrivilegios.contains(muAccion.getId() + ""));
		}
		lisAccionesForm.add(0, new MuAccion(0,navegar,"Navegacion"));
		return lisAccionesForm;
	}

	public void guardarPermisos() {
		try {
			roleBL.deleteRolFormulario(rolId);
		} catch (Exception e) {
			log.error("[guardarPermisos] Fallo al intentar eliminar los permisos por formulario.",e);
		}

		for (TreeNode tn : nodosSeleccionados) {
			marcarRolFormulario(tn);
		}
		this.visibleDialog = false;
		SysMessage.info("Guardado correctamente.");
	}

	private void marcarRolFormulario(TreeNode tn) {
		if (tn != null) {
			TreeNode padre = tn.getParent();
			if (padre != null && padre.getData() != null
					&& ((RolModel) padre.getData()).getId() != 0) {
				marcarRolFormulario(padre);
			}
			/******************/
			if(tn.getData() instanceof RolModel){
				RolModel rolModel = (RolModel) tn.getData();
				MuRolFormularioPK pk = new MuRolFormularioPK();
				pk.setFormularioId((int) rolModel.getId());
				pk.setRolId((int) rolModel.getRolID());
				
				MuRolFormulario rf = new MuRolFormulario();
				rf.setId(pk);
				
				String privilegios = "";
				List<TreeNode> accines = tn.getChildren();
				boolean navegar = true;
				for (Iterator iterator = accines.iterator(); iterator.hasNext();) {
					TreeNode treeNode = (TreeNode) iterator.next();
					
					if( treeNode.getData() instanceof AccionModel){
						AccionModel accionModel = (AccionModel)treeNode.getData();
						if(accionModel.getId() != 0){
							if(treeNode.isSelected()){
								if(!privilegios.isEmpty())
									privilegios = privilegios+","+accionModel.getId();
								else
									privilegios = accionModel.getId()+"";
								navegar = true;
							}
						}else
							navegar = treeNode.isSelected();
					}
					
				}
				rf.setEstado(navegar);
				rf.setPrivilegio(privilegios);
				try {
					roleBL.updateRoleFormulario(rf);
				} catch (Exception e) {
					log.error(
							"[guardarPermisos] Fallo al registrar los permisos del rol:"
									+ rolModel.getRolID() + ", formulario:"
									+ rolModel.getId() + "", e);
				}
			}
			/******************/

		}
	}

	public void guardarRol() {
		log.debug("[saveRole]: Ingresando..");

		String str = roleBL.validate(role, roleId);
		if (!str.isEmpty()) {
			SysMessage.error(str);
			return;
		}
		try {
			if (!edit) {
				role.setNombre(role.getNombre().trim());
				role.setDescripcion(role.getDescripcion().trim());
				roleBL.saveRole(role);
				controlerBitacora.insert(DescriptorBitacora.ROL,
						role.getRolId() + "", role.getNombre());
			} else {
				int id = Integer.parseInt(roleId);
				role.setRolId(id);
				role.setNombre(role.getNombre().trim());
				role.setDescripcion(role.getDescripcion().trim());
				roleBL.updateRole(role);
				controlerBitacora.update(DescriptorBitacora.ROL,
						role.getRolId() + "", role.getNombre());
			}
			listRole = roleBL.getRoles();
			SysMessage.info("Se guardo correctamente.");
			newRole();
			visibleNuevoEditar = false;
		} catch (Exception e) {
			log.error("[saveRole] error al momento de modificar o guardar: "
					+ role.getRolId() + " " + e.getLocalizedMessage(),e);
			SysMessage.error("Fallo al guardar en la Base de Datos.");
		}
	}

	public void slectRole() {
		try {
			String Idstr = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("roleId");
			selectNameRol = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("roleName");
			roleId = Idstr;
		} catch (Exception e) {
			log.error(
					"[obtenerRole]  error al obtener Parametros Rol: "
							+ e.getMessage(), e);
		}
	}

	public String deleteRole() {
		log.debug("delte");
		try {
			int id = Integer.parseInt(roleId);
			if (id == 1) {
				SysMessage
						.warn("Este Rol no puede eliminarse es el rol de Adminstracion.");
				return "";
			}
			boolean swCascade = (Boolean)P.getParamVal(ParametroID.ACCION_DELETE_ROLE_CASCADE);
			if(!swCascade){		
				List<MuUsuario> listaUsuarios=usuarioBL.getUsersRol(roleId);
				if(!listaUsuarios.isEmpty()){
					SysMessage
					.warn("Este Rol no puede eliminarse por que tiene usuarios asociados");
					return "";	
			}

			}
			roleBL.deleteRole(id);
			controlerBitacora.delete(DescriptorBitacora.ROL, id + "",
					selectNameRol);
			listRole = roleBL.getRoles();
			SysMessage.info("Se eliminó correctamente.");
		} catch (Exception e) {
			log.error("[deleteRole]  error al eliminar el menu id:"
					+ role.getRolId() + "  " + e);
			SysMessage.error("Fallo al eliminar.");

		}
		return "";
	}

	public String getMENU_TYPE() {
		return MENU_TYPE;
	}

	public String getFORM_TYPE() {
		return FORM_TYPE;
	}

	public String getACTION_TYPE() {
		return ACTION_TYPE;
	}

	public boolean isCasdadeDelete() {
		return (Boolean) P.getParamVal(ParametroID.ACCION_DELETE_ROLE_CASCADE);
	}

	public void newRole() {
		edit = false;
		visibleNuevoEditar= true;
		role = new MuRol();
	}
	

	public void editRole() {
		if (role!= null) {
			if(role.getRolId() != 1){
				controlerBitacora.accion(DescriptorBitacora.ROL, "Se pretende editar el ROl: " + role.getNombre() + ".");
				visibleNuevoEditar = true;
				roleId = role.getRolId()+"";
				edit = true;
			}else{
				log.warn("[editar] El Rol Administrador no es Editable.");
				SysMessage.warn("El Rol Administrador no es Editable.");
			}

		} else {
			log.warn("[editar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontro ningun registro seleccionado.");
		}
	}
	
	public void editRole(MuRol muRol) {
		role = muRol;
		editRole();
	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String RoleId) {
		this.roleId = RoleId;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public MuRol getRole() {
		return role;
	}

	public void setRole(MuRol Role) {
		this.role = Role;
	}

	public List<MuRol> getListRole() {
		return listRole;
	}

	public void setListRole(List<MuRol> listRole) {
		this.listRole = listRole;
	}

	public TreeNode getRoles() {
		return roles;
	}

	public void setRoles(TreeNode roles) {
		this.roles = roles;
	}

	public TreeNode[] getNodosSeleccionados() {
		return nodosSeleccionados;
	}

	public void setNodosSeleccionados(TreeNode[] nodosSeleccionados) {
		this.nodosSeleccionados = nodosSeleccionados;
	}

	public boolean isVisibleDialog() {
		return visibleDialog;
	}

	public void setVisibleDialog(boolean visibleDialog) {
		this.visibleDialog = visibleDialog;
	}

	public String getSelectNameRol() {
		return selectNameRol;
	}

	public void setSelectNameRol(String selectNameRol) {
		this.selectNameRol = selectNameRol;
	}

	public boolean isVisibleNuevoEditar() {
		return visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		this.visibleNuevoEditar = visibleNuevoEditar;
	}

	@Override
	public boolean isAuthorized(int idAccion) {
		return controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_ROLES, idAccion);
	}

}
