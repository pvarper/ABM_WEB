package support.ussd.bean;

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

import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.UsuarioAnitaBL;
import support.ussd.model.TAnitaUsuario;

import com.tigo.utils.ParametersWeb;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class UsuarioAnitaBean
implements Serializable, IControlPrivilegios
{
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(UsuarioAnitaBean.class);
@Inject
private UsuarioAnitaBL userBL;
@Inject
private ControlerBitacora controlerBitacora;
private List<TAnitaUsuario> listUser;
private TAnitaUsuario user = new TAnitaUsuario();
private String userId;
private boolean edit;
private boolean visibleNuevoEditar;
private ControlPrivilegio controlPrivilegio;
private List<SelectItem>	selectEstados;
private String				selectEstado;

private Date fecha;
private String select;
private Boolean booleanRender;

@PostConstruct
public void init()
{
  try
  {
    this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
    this.user = new TAnitaUsuario();
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
		selectEstados= new ArrayList<SelectItem>();
		selectEstados.add(new SelectItem("true"));
		selectEstados.add(new SelectItem("false"));
	}

	public void saveUser() {

		
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
			
			str = this.userBL.validateCI(this.user, this.userId);
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
				user.setEstado(true);
				this.userBL.save(user);
				this.controlerBitacora.insert(DescriptorBitacora.ANITA,
						this.user.getIdUsuario() + "", this.user.getUsrLogin());
			} else {
				int id = Integer.parseInt(this.userId);
				
				this.user.setIdUsuario(Long.valueOf(id));
				this.user.setEstado(Boolean.valueOf(selectEstado));
				this.userBL.update(this.user);
				this.controlerBitacora.update(DescriptorBitacora.USUARIO,
						null, this.user.getUsrLogin());
			}
			this.listUser = this.userBL.getUsers();
			SysMessage.info("Se guardo correctamente.");
			newUser();
			this.visibleNuevoEditar = false;
		} catch (Exception e) {
			log.error("[saveUser] usuario: " + this.user.getUsrLogin()
					+ ", Fallo al guardar el usuario", e);
			SysMessage.error("Fallo al guardar el usuario");
		}
	}

public void editRoleUser()
{
  if (this.user != null)
  {

      this.controlerBitacora.accion(DescriptorBitacora.ANITA, "Se pretende editar el USUARIO: " + this.user.getNombres() + ".");
      this.userId = this.user.getIdUsuario()+"";
      selectEstado=String.valueOf(user.getEstado());

      this.edit = true;
      this.visibleNuevoEditar = true;
    
  }
  else
  {
    log.warn("[editar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
}

public void editRoleUser(TAnitaUsuario muUsuario)
{
  this.user = muUsuario;
  editRoleUser();
}

public String deleteRoleUser()
{
  if (this.user != null)
  {
   
    try
    {
      this.userBL.deleteUser(this.user.getIdUsuario());
      this.controlerBitacora.delete(DescriptorBitacora.USUARIO, this.user.getIdUsuario()+"", this.user.getUsrLogin());
      this.listUser = this.userBL.getUsers();
      SysMessage.info("Se elimino correctamente.");
    }
    catch (Exception e)
    {
      log.error("[deleteRoleUser]  error al eliminar el usuario id:" + this.user.getIdUsuario() + "  " + e);
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

public String deleteRoleUser(TAnitaUsuario muUsuario)
{
  this.user = muUsuario;
  return deleteRoleUser();
}

public void newUser() throws Exception
{
  this.edit = false;
  this.user = new TAnitaUsuario();
  this.select = "-1";
  this.visibleNuevoEditar = true;
  this.booleanRender = Boolean.valueOf(true);
  selectEstado="true";
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

public TAnitaUsuario getUser()
{
  return this.user;
}

public void setUser(TAnitaUsuario user)
{
  this.user = user;
}



public String getSelect()
{
  return this.select;
}

public void setSelect(String select)
{
  this.select = select;
}

public List<TAnitaUsuario> getListUser()
{
  return this.listUser;
}

public void setListUser(List<TAnitaUsuario> listUser)
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
  return this.controlPrivilegio.isAuthorized(FormularioID.GEST_ANITA, idAccion);
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

public List<SelectItem> getSelectEstados() {
	return selectEstados;
}

public void setSelectEstados(List<SelectItem> selectEstados) {
	this.selectEstados = selectEstados;
}

public String getSelectEstado() {
	return selectEstado;
}

public void setSelectEstado(String selectEstado) {
	this.selectEstado = selectEstado;
}




}
