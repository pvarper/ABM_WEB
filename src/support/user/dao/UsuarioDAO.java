package support.user.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.commons.lang.StringEscapeUtils;
import support.user.model.MuUsuario;

@Named
public class UsuarioDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public void save(MuUsuario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.persist(dato);
    this.transaction.commit();
  }
  
  public MuUsuario get(Long id)
    throws Exception
  {
    return (MuUsuario)this.entityManager.find(MuUsuario.class, id);
  }
  
  public void update(MuUsuario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  @SuppressWarnings("unchecked")
  public List<MuUsuario> getList()
    throws Exception
  {
    return this.entityManager.createQuery("SELECT us FROM MuUsuario us WHERE us.estado=true Order by us.usuarioId").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuUsuario> getListAdmin()
    throws Exception
  {
    return this.entityManager.createQuery("SELECT us FROM MuUsuario us WHERE us.muUsuario<>null and us.estado=true Order by us.usuarioId").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuUsuario> getListNoAdmin()
    throws Exception
  {
    return this.entityManager.createQuery("SELECT us FROM MuUsuario us WHERE us.login<>'admin' and us.estado=true Order by us.login").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuUsuario> getListRol(String s)
    throws Exception
  {
    return this.entityManager.createQuery("SELECT us FROM MuUsuario us WHERE us.muRol.rolId=" + StringEscapeUtils.escapeSql(s) + " and us.estado=true").getResultList();
  }
 
  @SuppressWarnings("unchecked")
  public List<MuUsuario> getList(Integer idRol)
    throws Exception
  {
    String consulta = "SELECT us FROM MuUsuario us WHERE us.muRol.rolId = :idRol and us.muRol.estado = true and us.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("idRol", idRol);
    List<MuUsuario> lista = qu.getResultList();
    return lista;
  }
  @SuppressWarnings("unchecked")
  public MuUsuario getUsuarioLogin(String login)
  {
    String consulta = "SELECT us FROM MuUsuario us WHERE lower(us.login) = lower(:login) and us.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("login", login);
    List<MuUsuario> lista = qu.getResultList();
    return lista.isEmpty() ? null : (MuUsuario)lista.get(0);
  }

}
