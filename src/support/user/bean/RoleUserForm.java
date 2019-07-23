package support.user.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.user.business.RoleBL;
import support.user.business.UsuarioBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.ActiveDirectory;
import support.user.ldap.DescriptorBitacora;
import support.user.ldap.LdapContextException;
import support.user.model.MuRol;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.model.TPlataforma;

import com.tigo.utils.ParametersWeb;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class RoleUserForm
implements Serializable, IControlPrivilegios
{
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(RoleUserForm.class);
@Inject
private UsuarioBL userBL;
@Inject
private RoleBL rolBL;
@Inject
private ControlerBitacora controlerBitacora;
private List<MuUsuario> listUser;
private MuUsuario user = new MuUsuario();
private String userId;
private boolean edit;
private boolean visibleNuevoEditar;
private ControlPrivilegio controlPrivilegio;
private List<SelectItem> selectItems;
private List<SelectItem> selectItemsUs;
private Date fecha;
private String select;
private String selectUs;
private Boolean booleanRender;


@PostConstruct
public void init()
{
  try
  {
    this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
   
    this.user = new MuUsuario();
    this.listUser = this.userBL.getUsers();
    this.visibleNuevoEditar = false;
    fillSelectItems();
  }
  catch (Exception e)
  {
    log.error("init|Fallo al inicializar la clase. " + e.getMessage());
  }
}

	private void fillSelectItems() throws Exception {
		this.selectItems = new ArrayList<SelectItem>();
		this.selectItems.add(new SelectItem("-1", "Grupos_Rol"));
		List<MuRol> listaRol = this.rolBL.getRoles();
		for (MuRol role : listaRol) {
			SelectItem sel = new SelectItem(role.getRolId(), role.getNombre());
			this.selectItems.add(sel);
		}
		
		this.selectItemsUs = new ArrayList<SelectItem>();
		this.selectItemsUs.add(new SelectItem("-1", "Administradores"));
		List<MuUsuario> listaUs = this.userBL.getUsers();
		for (MuUsuario role : listaUs) {
			SelectItem sel = new SelectItem(role.getUsuarioId(), role.getNombre());
			this.selectItemsUs.add(sel);
		}
	}

	public void saveUser() {
		int idRole = Integer.parseInt(this.select);
		if (idRole == -1) {
			SysMessage.error("Campo rol es requerido");
			return;
		}
		Long idSup = Long.parseLong(this.selectUs);

		try {
			MuUsuario name = ActiveDirectory.getNombreCompleto(this.user
					.getLogin());
			if (name == null) {
				SysMessage.error("No se pudo obtener el nombre del usuario");
				return;
			} else {

				this.user.setLogin(name.getLogin());
				this.user.setNombre(name.getNombre());
				this.user.setCorreo(name.getCorreo());
				this.user.setEhumano(name.getEhumano());
				this.user.setTelefono(name.getTelefono());
				if (idSup!=-1){
					try {
						MuUsuario us=userBL.getUser(idSup);
						this.user.setMuUsuario(us);
					} catch (Exception e) {
						SysMessage.error("No se pudo obtener el nombre del supervisor");
						return;
						
					}
				
				}else{
					user.setMuUsuario(null);
				}
				
				
			}

		} catch (LdapContextException e1) {
			SysMessage.error("Error LDAP [" + P.getParamVal(Integer.valueOf(7))
					+ "]", e1.getMessage());
			log.error("Error Validar Usurio LDAP:" + e1.getMessage());
			return;
		}
		// String name;
		String str = "";
		try {
			str = this.userBL.validate(this.user, this.userId);
		} catch (Exception e) {
			log.error("[saveUser] Fallo al intentar validar los parametros.", e);
			str = "error con la conexion a la BD o otro problema";
		}
		if (!str.isEmpty()) {
			SysMessage.error(str);
			return;
		}
		try {
			if (!this.edit) {
				// user.setLogin(user.getLogin().toLowerCase());
				this.userBL.saveUserRole(this.user, idRole);
				this.controlerBitacora.insert(DescriptorBitacora.USUARIO,
						this.user.getUsuarioId() + "", this.user.getLogin());
			} else {
				int id = Integer.parseInt(this.userId);
				user.setLogin(user.getLogin().toLowerCase());
				this.user.setUsuarioId(Long.valueOf(id));
				this.userBL.updateUser(this.user, idRole);
				this.controlerBitacora.update(DescriptorBitacora.USUARIO,
						this.user.getUsuarioId() + "", this.user.getLogin());
			}
			this.listUser = this.userBL.getUsers();
			SysMessage.info("Se guardo correctamente.");
			newUser();
			this.visibleNuevoEditar = false;
		} catch (Exception e) {
			log.error("[saveUser] usuario: " + this.user.getNombre()
					+ ", Fallo al guardar el usuario", e);
			SysMessage.error("Fallo al guardar el usuario");
		}
	}

public void editRoleUser()
{
  if (this.user != null)
  {
    if (this.user.getUsuarioId().intValue() == 1)
    {
      SysMessage.warn("El Usuario " + this.user.getLogin() + " es el Aministrador del Sistema por defecto, No es posible su edicion..!");
    }
    else
    {
      this.controlerBitacora.accion(DescriptorBitacora.USUARIO, "Se pretende editar el USUARIO: " + this.user.getNombre() + ".");
      this.userId = this.user.getUsuarioId()+"";
      this.select = this.user.getMuRol().getRolId()+"";
      if(this.user.getMuUsuario()==null){
    	  this.selectUs="-1";
      }else{
    	  this.selectUs=this.user.getMuUsuario().getUsuarioId()+"";  
      }
      
      this.edit = true;
      this.visibleNuevoEditar = true;
    }
  }
  else
  {
    log.warn("[editar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
}

public void editRoleUser(MuUsuario muUsuario)
{
  this.user = muUsuario;
  editRoleUser();
}

public String deleteRoleUser()
{
  if (this.user != null)
  {
    if (this.user.getUsuarioId().intValue() == 1)
    {
      SysMessage.error("Este Usuario no se puede elimnar es usuario Interno.");
      return "";
    }
    try
    {
      this.userBL.deleteUser(this.user.getUsuarioId());
      this.controlerBitacora.delete(DescriptorBitacora.USUARIO, this.user.getUsuarioId()+"", this.user.getLogin());
      this.listUser = this.userBL.getUsers();
      SysMessage.info("Se elimino correctamente.");
    }
    catch (Exception e)
    {
      log.error("[deleteRoleUser]  error al eliminar el usuario id:" + this.user.getUsuarioId() + "  " + e);
      SysMessage.error("Fallo al eliminar.");
    }
  }
  else
  {
    log.warn("[eliminar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
  return "";
}

public String deleteRoleUser(MuUsuario muUsuario)
{
  this.user = muUsuario;
  return deleteRoleUser();
}

public void newUser()
{
  this.edit = false;
  this.user = new MuUsuario();
  this.select = "-1";
  this.selectUs = "-1";
  this.visibleNuevoEditar = true;
  this.booleanRender = Boolean.valueOf(true);
}

public String getColor(boolean estado)
{
  String color;
  try
  {
    if (estado) {
      color = "background-color:#FFFFFF";
    } else {
      color = "background-color:#" + P.getParamVal(Integer.valueOf(15));
    }
  }
  catch (Exception e)
  {
    log.error("[Get Color] Error al evalor Color UsuarioRol:" + e.getMessage(), e);
    color = "background-color:#FFFFFF";
  }
  return color;
}

public String getUserId()
{
  return this.userId;
}

public void setUserId(String userId)
{
  this.userId = userId;
}

public Boolean getEdit()
{
  return Boolean.valueOf(this.edit);
}

public void setEdit(Boolean edit)
{
  this.edit = edit.booleanValue();
}

public MuUsuario getUser()
{
  return this.user;
}

public void setUser(MuUsuario user)
{
  this.user = user;
}

public List<SelectItem> getSelectItems()
{
  return this.selectItems;
}

public void setSelectItems(List<SelectItem> selectItems)
{
  this.selectItems = selectItems;
}

public String getSelect()
{
  return this.select;
}

public void setSelect(String select)
{
  this.select = select;
}

public List<MuUsuario> getListUser()
{
  return this.listUser;
}

public void setListUser(List<MuUsuario> listUser)
{
  this.listUser = listUser;
}

public boolean isVisibleNuevoEditar()
{
  return this.visibleNuevoEditar;
}

public void setVisibleNuevoEditar(boolean visibleNuevoEditar)
{
  this.visibleNuevoEditar = visibleNuevoEditar;
}

public boolean isAuthorized(int idAccion)
{
  return this.controlPrivilegio.isAuthorized(3L, idAccion);
}

public Date getFecha()
{
  return this.fecha;
}

public void setFecha(Date fecha)
{
  this.fecha = fecha;
}

public Boolean getBooleanRender()
{
  return this.booleanRender;
}

public void setBooleanRender(Boolean booleanRender)
{
  this.booleanRender = booleanRender;
}

public String getExpresionRegular()
{
  return ParametersWeb.EXPRESION_REGULAR_TODO;
}

public String getExpresionRegularNumero()
{
  return ParametersWeb.EXPRESION_REGULAR_NUMERO;
}

public List<SelectItem> getSelectItemsUs() {
	return selectItemsUs;
}

public void setSelectItemsUs(List<SelectItem> selectItemsUs) {
	this.selectItemsUs = selectItemsUs;
}

public String getSelectUs() {
	return selectUs;
}

public void setSelectUs(String selectUs) {
	this.selectUs = selectUs;
}




}
