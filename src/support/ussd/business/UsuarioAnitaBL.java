package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.UsuarioAnitaDAO;
import support.ussd.model.TAnitaUsuario;

@Named
public class UsuarioAnitaBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  private UsuarioAnitaDAO dao;

  
  public String validate(TAnitaUsuario user, String idStr)
    throws Exception
  {
    if (user.getRegional().trim().isEmpty()) {
      return "El campo Regional esta Vacio";
    }
    if (user.getCargo().trim().isEmpty()) {
        return "El campo Cargo esta Vacio";
      }
    if (user.getNombres().trim().isEmpty()) {
        return "El campo Nombres esta Vacio";
      }
    if (user.getUsrLogin().trim().isEmpty()) {
        return "El campo Login esta Vacio";
      }
    
    
 
    TAnitaUsuario usAux = this.dao.getUsuarioLogin(user.getUsrLogin());
    if (usAux == null) {
      return "";
    }   
    
     if ((idStr != null) && (!idStr.isEmpty()))
     {
       int id = Integer.parseInt(idStr);
       if ((id == usAux.getIdUsuario().intValue()) && (user.getUsrLogin().equalsIgnoreCase(usAux.getUsrLogin())) ) {
           return "";
         }
     }
     
    return "El login ya existe";
  }
  
  public String validateCI(TAnitaUsuario user, String idStr)
		    throws Exception
		  {
		    
		    
		 
		    TAnitaUsuario usAux = this.dao.getUsuarioCI(user.getUsrLogin());
		    if (usAux == null) {
		      return "";
		    }   
		    
		     if ((idStr != null) && (!idStr.isEmpty()))
		     {
		       int id = Integer.parseInt(idStr);
		       if ((id == usAux.getIdUsuario().intValue()) && (user.getNumeroci().equalsIgnoreCase(usAux.getNumeroci())) ) {
		           return "";
		         }
		     }
		     
		    return "El CI ya existe";
		  }
  
  public void save(TAnitaUsuario user)
    throws Exception
  {
    this.dao.save(user);
  }
  
  public void update(TAnitaUsuario user)
    throws Exception
  {
	TAnitaUsuario userAux = this.dao.get(user.getIdUsuario());
    userAux.setRegional(user.getRegional());
    userAux.setCargo(user.getCargo());
    userAux.setNombres(user.getNombres());
    userAux.setUsrLogin(user.getUsrLogin());
    userAux.setNumeroci(user.getNumeroci());
    userAux.setEhumano(user.getEhumano());
    userAux.setTelefono(user.getTelefono());
    userAux.setEstado(user.getEstado());
    
    this.dao.update(userAux);
  }
  
  public void deleteUser(Long idUser)
    throws Exception
  {
	  TAnitaUsuario user = this.dao.get(idUser);
    user.setEstado(false);
    this.dao.update(user);
  }
  
  public List<TAnitaUsuario> getUsers()
    throws Exception
  {
    return this.dao.getList();
  }
  
  
 
}
