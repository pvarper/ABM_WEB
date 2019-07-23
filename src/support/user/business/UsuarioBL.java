package support.user.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.user.dao.GrupoAdDAO;
import support.user.dao.RoleDAO;
import support.user.dao.UsuarioDAO;
import support.user.model.MuGrupoAd;
import support.user.model.MuRol;
import support.user.model.MuRolFormulario;
import support.user.model.MuUsuario;

@Named
public class UsuarioBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  private UsuarioDAO dao;
  @Inject
  private RoleDAO rolDao;
  @Inject
  private GrupoAdDAO groupDao;
  
  public String validate(MuUsuario user, String idStr)
    throws Exception
  {
    if (user.getLogin().trim().isEmpty()) {
      return "El campo Login esta Vacio";
    }
    MuUsuario usAux = this.dao.getUsuarioLogin(user.getLogin());
    if (usAux == null) {
      return "";
    }
    if ((idStr != null) && (!idStr.isEmpty()))
    {
      int id = Integer.parseInt(idStr);
      if ((id == usAux.getUsuarioId().intValue()) && 
        (user.getLogin().equalsIgnoreCase(usAux.getLogin()))) {
        return "";
      }
    }
    return "este login existe";
  }
  
  public void saveUserRole(MuUsuario user, int idRole)
    throws Exception
  {
    MuRol rol = new MuRol();
    rol.setRolId(Integer.valueOf(idRole));
    user.setMuRol(rol);
    user.setEstado(true);
    this.dao.save(user);
  }
  
  public void updateUser(MuUsuario user, int idRole)
    throws Exception
  {
    MuUsuario userAux = this.dao.get(user.getUsuarioId());
    userAux.setLogin(user.getLogin());
    userAux.setNombre(user.getNombre());
    userAux.setTelefono(user.getTelefono());
    MuRol rol = new MuRol();
    rol.setRolId(Integer.valueOf(idRole));
    userAux.setMuRol(rol);
    if(user.getMuUsuario()==null){
    	userAux.setMuUsuario(null);
    }else{
    	userAux.setMuUsuario(user.getMuUsuario());
    }
    
    this.dao.update(userAux);
  }
  
  public void deleteUser(Long idUser)
    throws Exception
  {
    MuUsuario user = this.dao.get(idUser);
    user.setEstado(false);
    this.dao.update(user);
  }
  
  public List<MuUsuario> getUsers()
    throws Exception
  {
    return this.dao.getList();
  }
  
  public List<MuUsuario> getUsersAdmin()
		    throws Exception
		  {
		    return this.dao.getListAdmin();
		  }
  
  public MuUsuario getUser(Long idUser)
    throws Exception
  {
    return this.dao.get(idUser);
  }
  
  public List<MuUsuario> getUsersNoadmin()
    throws Exception
  {
    return this.dao.getListNoAdmin();
  }
  
  public List<MuUsuario> getUsersRol(String s)
    throws Exception
  {
    return this.dao.getListRol(s);
  }
  
 
  
  public MuUsuario getUserLogin(String nombre)
  {
    return this.dao.getUsuarioLogin(nombre);
  }
  
  public List<String> getListFormUser(String login)
    throws Exception
  {
    Integer k = Integer.valueOf(1);
    MuUsuario user = this.dao.getUsuarioLogin(login);
    if (user != null) {
      k = user.getMuRol().getRolId();
    }
    List<MuRolFormulario> lista = this.rolDao.getRolFormularioUser(k);
    List<String> listaUrl = new ArrayList<String>();
    for (MuRolFormulario rolFormulario : lista) {
      listaUrl.add(rolFormulario.getMuFormulario().getUrl());
    }
    return listaUrl;
  }
  
  public List<MuRolFormulario> getRolFormularios(String login)
    throws Exception
  {
    Integer k = Integer.valueOf(1);
    MuUsuario user = this.dao.getUsuarioLogin(login);
    if (user != null) {
      k = user.getMuRol().getRolId();
    }
    return this.rolDao.getRolFormulario(k);
  }
  
  public Integer getIdRole(String login, List<Object> userGroups)
  {
    MuUsuario user = this.dao.getUsuarioLogin(login);
    if (user != null) {
      return user.getMuRol().getRolId();
    }
    for (Object object : userGroups)
    {
      MuGrupoAd gr = this.groupDao.getGroupName(object.toString());
      if (gr != null) {
        return gr.getMuRol().getRolId();
      }
    }
    return Integer.valueOf(-1);
  }
 
}
