package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.user.model.MuUsuario;
import support.ussd.model.TAnitaUsuario;

@Named
public class UsuarioAnitaDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public void save(TAnitaUsuario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.persist(dato);
    this.transaction.commit();
  }
  
  public TAnitaUsuario get(Long id)
    throws Exception
  {
    return (TAnitaUsuario)this.entityManager.find(TAnitaUsuario.class, id);
  }
  
  public void update(TAnitaUsuario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  @SuppressWarnings("unchecked")
  public List<TAnitaUsuario> getList()
    throws Exception
  {
    return this.entityManager.createQuery("SELECT us FROM TAnitaUsuario us Order by us.idUsuario").getResultList();
  }
  
  @SuppressWarnings("unchecked")
  public TAnitaUsuario getUsuarioLogin(String login)
  {
    String consulta = "SELECT us FROM TAnitaUsuario us WHERE lower(us.usrLogin) = lower(:login) and us.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("login", login);
    List<TAnitaUsuario> lista = qu.getResultList();
    return lista.isEmpty() ? null : (TAnitaUsuario)lista.get(0);
  }
  @SuppressWarnings("unchecked")
  public TAnitaUsuario getUsuarioCI(String login)
  {
    String consulta = "SELECT us FROM TAnitaUsuario us WHERE lower(us.numeroci) = lower(:login) and us.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("login", login);
    List<TAnitaUsuario> lista = qu.getResultList();
    return lista.isEmpty() ? null : (TAnitaUsuario)lista.get(0);
  }
  
}
