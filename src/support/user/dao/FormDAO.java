package support.user.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import support.user.model.MuAccion;
import support.user.model.MuFormulario;
import support.user.model.MuRolFormulario;
import support.user.model.MuRolFormularioPK;

@Named
public class FormDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public void save(MuFormulario dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.persist(dato);
    String privilegio = null;
    if (dato.getMuAccions() != null)
    {
      privilegio = "";
      for (MuAccion muAccion : dato.getMuAccions()) {
        if (privilegio.isEmpty()) {
          privilegio = muAccion.getId()+"";
        } else {
          privilegio = privilegio + "," + muAccion.getId();
        }
      }
    }
    MuRolFormulario rolForm = new MuRolFormulario(new MuRolFormularioPK(dato.getId(), Integer.valueOf(1)));
    rolForm.setPrivilegio(privilegio);
    this.entityManager.persist(rolForm);
    this.transaction.commit();
  }
  
  public void update(MuFormulario dato)
    throws Exception
  {
    String sql = "UPDATE MuRolFormulario rf SET rf.privilegio = :privilegio WHERE rf.id.formularioId = :IdForm";
    this.transaction.begin();
    this.entityManager.merge(dato);
    String privilegio = null;
    if ((dato.getMuAccions() != null) && (!dato.getMuAccions().isEmpty()))
    {
      privilegio = "";
      for (MuAccion muAccion : dato.getMuAccions()) {
        if (privilegio.isEmpty()) {
          privilegio = muAccion.getId()+"";
        } else {
          privilegio = privilegio + "," + muAccion.getId();
        }
      }
    }
    Query q = this.entityManager.createQuery(sql);
    q.setParameter("IdForm", dato.getId());
    q.setParameter("privilegio", privilegio);
    q.executeUpdate();
    this.transaction.commit();
  }
  
  public void remove(long idForm)
    throws Exception
  {
    String sql = "Delete from MuRolFormulario rf where rf.id.formularioId = :idForm";
    this.transaction.begin();
    this.entityManager.createQuery(sql).setParameter("idForm", Long.valueOf(idForm)).executeUpdate();
    
    sql = "Delete from MuFormulario f where f.id = :idForm";
    this.entityManager.createQuery(sql).setParameter("idForm", Long.valueOf(idForm)).executeUpdate();
    this.transaction.commit();
  }
  
  public MuFormulario get(long id)
    throws Exception
  {
    return (MuFormulario)this.entityManager.find(MuFormulario.class, Long.valueOf(id));
  }
  
  @SuppressWarnings("unchecked")
  public List<MuFormulario> getList()
  {
    return this.entityManager.createQuery("SELECT us FROM MuFormulario us").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuFormulario> getListMod()
  {
    return this.entityManager.createQuery("SELECT us FROM MuFormulario us where us.formularioId is null ORDER BY us.formularioId, us.orden").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuFormulario> getListPage()
  {
    return this.entityManager.createQuery("SELECT us FROM MuFormulario us where us.formularioId is not null ORDER BY us.formularioId, us.orden").getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuFormulario> getListPage(long idModForm)
  {
    return this.entityManager.createQuery("SELECT us FROM MuFormulario us where us.formularioId = :idModForm ORDER BY us.formularioId, us.orden").setParameter("idModForm", Long.valueOf(idModForm)).getResultList();
  }
  
  public Integer getMaxIDForm()
  {
    Integer response = Integer.valueOf(0);
    String sql = "SELECT max(f.id) FROM MuFormulario f";
    Query query = this.entityManager.createQuery(sql);
    List<?> list = query.getResultList();
    if (list.size() == 0) {
      response = Integer.valueOf(0);
    } else if (list.get(0) == null) {
      response = Integer.valueOf(0);
    } else {
      response = (Integer)list.get(0);
    }
    return response;
  }
  
  public Integer getMaxOrdenForm(long formularioId)
  {
    Integer response = Integer.valueOf(0);
    String sql = "SELECT max(f.orden) FROM MuFormulario f where f.formularioId = :formularioId";
    Query query = this.entityManager.createQuery(sql);
    query.setParameter("formularioId", Long.valueOf(formularioId));
    List<?> list = query.getResultList();
    if (list.size() == 0) {
      response = Integer.valueOf(0);
    } else if (list.get(0) == null) {
      response = Integer.valueOf(0);
    } else {
      response = (Integer)list.get(0);
    }
    return response;
  }
  
  public Integer getMaxOrdenMod()
  {
    Integer response = Integer.valueOf(0);
    String sql = "SELECT max(f.orden) FROM MuFormulario f where f.formularioId = null";
    Query query = this.entityManager.createQuery(sql);
    List<?> list = query.getResultList();
    if (list.size() == 0) {
      response = Integer.valueOf(0);
    } else if (list.get(0) == null) {
      response = Integer.valueOf(0);
    } else {
      response = (Integer)list.get(0);
    }
    return response;
  }
}
