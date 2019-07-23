package support.user.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import support.id.ParametroID;
import support.user.dao.FormDAO;
import support.user.dao.GrupoAdDAO;
import support.user.dao.RoleDAO;
import support.user.dao.UsuarioDAO;
import support.user.model.MuFormulario;
import support.user.model.MuGrupoAd;
import support.user.model.MuRol;
import support.user.model.MuRolFormulario;
import support.user.model.MuRolFormularioPK;
import support.user.model.MuUsuario;
import support.user.sys.P;

@Named
public class RoleBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static Logger log = Logger.getLogger(RoleBL.class);
  @Inject
  private RoleDAO masterDao;
  @Inject
  private RoleDAO dao;
  @Inject
  private FormDAO formDao;
  @Inject
  private GrupoAdDAO grupoDao;
  @Inject
  private UsuarioDAO usuarioDao;
  
  public String validate(MuRol role, String idStr)
  {
    log.debug("[validate]: Ingresando..");
    if (role.getNombre().trim().isEmpty()) {
      return "El campo Nombre esta Vacio";
    }
    MuRol rolAux = this.dao.getName(role.getNombre().trim());
    if (rolAux == null) {
      return "";
    }
    if ((idStr != null) && (!idStr.isEmpty()))
    {
      int id = Integer.parseInt(idStr);
      if ((id == rolAux.getRolId().intValue()) && 
        (role.getNombre().trim().equalsIgnoreCase(rolAux.getNombre().trim()))) {
        return "";
      }
    }
    return "este nombre existe";
  }
  
  public void saveRole(MuRol role)
    throws Exception
  {
    role.setEstado(true);
    this.masterDao.save(role);
    List<MuFormulario> lista = this.formDao.getList();
    for (MuFormulario formulario : lista)
    {
      MuRolFormularioPK rfk = new MuRolFormularioPK();
      rfk.setRolId(role.getRolId());
      rfk.setFormularioId(formulario.getId());
      MuRolFormulario rolFor = new MuRolFormulario();
      rolFor.setEstado(true);
      rolFor.setId(rfk);
      
      this.masterDao.saveRolForulario(rolFor);
    }
  }
  
  public void updateRole(MuRol role)
    throws Exception
  {
    MuRol roleAux = this.dao.get(role.getRolId());
    roleAux.setNombre(role.getNombre());
    roleAux.setDescripcion(role.getDescripcion());
    this.dao.update(roleAux);
  }
  
  public void updateRoleFormulario(MuRolFormulario roleForm)
    throws Exception
  {
    this.dao.updateRolForulario(roleForm);
  }
  
  public void deleteRole(Integer idRole) throws Exception
  {
	  boolean swCascade = (Boolean)P.getParamVal(ParametroID.ACCION_DELETE_ROLE_CASCADE);
      log.info("[Eliminar ROl] Parametro Eliminacion Cascade:"+swCascade);

      if(swCascade){
          List<MuGrupoAd> listGrupo = grupoDao.getList(idRole);
          for (MuGrupoAd g : listGrupo) {
              g.setEstado(false);
              grupoDao.update(g);
          }

          List<MuUsuario> listUser = usuarioDao.getList(idRole);
          for (MuUsuario u : listUser) {
              u.setEstado(false);
              usuarioDao.update(u);
          }
      }

      List<MuRolFormulario> listRolForm = dao.getRolFormularioDelete(idRole);
      for (MuRolFormulario rf : listRolForm) {
          rf.setEstado(false);
          dao.updateRolFormulario(rf);
      }

      MuRol rol = dao.get(idRole);
      rol.setEstado(false);
      dao.update(rol);
  }
  
  public void deleteRolFormulario(Integer rolId)
    throws Exception
  {
    this.dao.deleteRolFormulario(rolId);
  }
  
  public MuRol getname(String name)
  {
    return this.dao.getName(name);
  }
  
  public List<MuRol> getRoles()
  {
    return this.dao.getList();
  }
  
  public MuRol getRole(int idRole)
  {
    return this.dao.get(Integer.valueOf(idRole));
  }
  
  public List<MuRolFormulario> getRolFormulario(Integer id)
  {
    return this.dao.getRolFormulario(id);
  }
  
  public void updateRoleFormularioList(List<String> listaAvil, int idRol) {}
}
