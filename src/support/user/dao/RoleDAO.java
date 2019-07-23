package support.user.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.user.model.MuRol;
import support.user.model.MuRolFormulario;

@Named
public class RoleDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public void save(MuRol dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.persist(dato);
    this.transaction.commit();
  }
  
  public void saveRolForulario(MuRolFormulario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.persist(dato);
    this.transaction.commit();
  }
  
  public MuRol get(Integer id)
  {
    return (MuRol)this.entityManager.find(MuRol.class, id);
  }
  
  public void update(MuRol dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  
  public void updateRolFormulario(MuRolFormulario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  
  public void updateRolForulario(MuRolFormulario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  @SuppressWarnings("unchecked")
  public List<MuRol> getList()
  {
    return this.entityManager.createQuery("SELECT r FROM MuRol r WHERE  r.estado = true Order By r.rolId").getResultList();
  }
  @SuppressWarnings("unchecked")
  public MuRol getName(String name)
  {
    String consulta = "SELECT r FROM MuRol r WHERE trim(lower(r.nombre)) =trim(lower(:name)) and r.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("name", name);
    List<MuRol> lista = qu.getResultList();
    return lista.isEmpty() ? null : (MuRol)lista.get(0);
  }
  @SuppressWarnings("unchecked")
  public List<MuRolFormulario> getRolFormulario(Integer id)
  {
    try
    {
      String consulta = "FROM MuRolFormulario r WHERE r.muRol.rolId = :id ORDER BY r.muFormulario.orden";
      Query qu = this.entityManager.createQuery(consulta).setParameter("id", id);
      return qu.getResultList();
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  public List<MuRolFormulario> getRolFormularioDelete(Integer idRol)
  {
    String consulta = "SELECT r FROM MuRolFormulario r WHERE r.muRol.rolId = :idRol and r.muRol.estado = true";
    Query qu = this.entityManager.createQuery(consulta).setParameter("idRol", idRol);
    return qu.getResultList();
  }
  
  public void deleteRolFormulario(Integer rolId)
    throws Exception
  {
    String sql = "UPDATE MuRolFormulario rf SET rf.estado = false, rf.privilegio = NULL WHERE rf.id.rolId = :rolId";
    this.transaction.begin();
    Query q = this.entityManager.createQuery(sql);
    q.setParameter("rolId", rolId);
    q.executeUpdate();
    this.transaction.commit();
  }
  @SuppressWarnings("unchecked")
  public List<MuRolFormulario> getRolFormularioUser(Integer id)
  {
    String consulta = "SELECT r FROM MuRolFormulario r WHERE  r.rol.rolId = :id  ORDER BY r.formulario.posicionColumna , r.formulario.posicionFila   ";
    Query qu = this.entityManager.createQuery(consulta).setParameter("id", id);
    return qu.getResultList();
  }
}
