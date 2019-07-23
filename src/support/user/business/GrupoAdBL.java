package support.user.business;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import support.user.dao.GrupoAdDAO;
import support.user.model.MuGrupoAd;
import support.user.model.MuRol;

@SuppressWarnings("serial")
@Named
public class GrupoAdBL implements Serializable
{
  @Inject
  private GrupoAdDAO dao;
  
  public String validate(MuGrupoAd user, String idStr)
  {
    if (user.getNombre().isEmpty()) {
      return "El campo Nombre esta Vacio";
    }
    MuGrupoAd usAux = this.dao.getGroupName(user.getNombre());
    if (usAux == null) {
      return "";
    }
    if ((idStr != null) && (!idStr.isEmpty()))
    {
      int id = Integer.parseInt(idStr);
      if ((id == usAux.getGrupoId()) && 
        (user.getNombre().equals(usAux.getNombre()))) {
        return "";
      }
    }
    return "este nombre de grupo ya existe";
  }
  
  public void saveGroupRole(MuGrupoAd group, int idRole)
    throws Exception
  {
    MuRol rol = new MuRol();
    rol.setRolId(Integer.valueOf(idRole));
    group.setMuRol(rol);
    group.setEstado(true);
    this.dao.save(group);
  }
  
  public void updateGroup(MuGrupoAd group, int idRole)
    throws Exception
  {
    MuRol rol = new MuRol();
    rol.setRolId(Integer.valueOf(idRole));
    
    MuGrupoAd groupAux = this.dao.get(group.getGrupoId());
    groupAux.setNombre(group.getNombre());
    groupAux.setDetalle(group.getDetalle());
    groupAux.setMuRol(rol);
    this.dao.update(groupAux);
  }
  
  public void deleteGroup(long idGroup)
    throws Exception
  {
    MuGrupoAd user = this.dao.get(idGroup);
    user.setEstado(false);
    this.dao.update(user);
  }
  
  public List<MuGrupoAd> getGroups()
  {
    return this.dao.getList();
  }
  
  public MuGrupoAd getGroup(int idGroup)
  {
    return this.dao.get(idGroup);
  }
}
