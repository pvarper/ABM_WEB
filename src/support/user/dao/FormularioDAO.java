package support.user.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import support.user.model.MuAccion;
import support.user.model.MuFormulario;
import support.user.model.MuRolFormulario;

@Named
public class FormularioDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(FormularioDAO.class);
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public MuFormulario find(long id)
  {
    return (MuFormulario)this.entityManager.find(MuFormulario.class, Long.valueOf(id));
  }
  @SuppressWarnings("unchecked")
  public List<MuFormulario> findPadres(Integer rolId)
  {
    String sql = "SELECT f FROM MuFormulario f,  MuRolFormulario rf where  f.id = rf.id.formularioId and f.formularioId is null AND rf.id.rolId = :rolId AND rf.estado = true AND f.estado = true order by f.orden";
    Query query = this.entityManager.createQuery(sql, MuFormulario.class);
    query.setParameter("rolId", rolId);
    return query.getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuFormulario> findHijos(Integer raizId, Integer rolId)
  {
    String sql = "SELECT f FROM MuFormulario f, MuRolFormulario rf where f.id = rf.id.formularioId and f.formularioId = :raizId AND rf.id.rolId = :rolId AND rf.estado = true AND f.estado = true order by f.orden";
    Query query = this.entityManager.createQuery(sql, MuFormulario.class);
    query.setParameter("raizId", raizId);
    query.setParameter("rolId", rolId);
    return query.getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<Object[]> findPadresPermisos(Integer rolId)
  {
    String sql = "SELECT F.id, F.nombre, CASE WHEN (SELECT FF.id FROM MuFormulario FF, MuRolFormulario RR where FF.id = RR.id.formularioId and RR.id.rolId = :rolId AND FF.estado = true AND RR.estado = true AND F.id = FF.id) IS NULL THEN 0 ELSE 1 END  as permiso FROM MuFormulario F, MuRolFormulario R where F.id = R.id.formularioId and F.formularioId is null and R.id.rolId = 1 AND F.estado = true AND R.estado = true  order by F.orden";
    
    Query query = this.entityManager.createQuery(sql);
    query.setParameter("rolId", rolId);
    return query.getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<Object[]> findHijosPermisos(Integer raizId, Integer rolId)
  {
    String sql = "SELECT F.id, F.nombre, CASE WHEN (SELECT FF.id FROM MuFormulario FF, MuRolFormulario RR where FF.id = RR.id.formularioId and RR.id.rolId = :rolId AND FF.estado = true AND RR.estado = true AND F.id = FF.id) IS NULL THEN 0 ELSE 1 END  as permiso FROM MuFormulario F, MuRolFormulario R where F.id = R.id.formularioId and F.formularioId = :raizId and R.id.rolId = 1 AND F.estado = true AND R.estado = true  order by F.orden";
    
    Query query = this.entityManager.createQuery(sql);
    query.setParameter("raizId", raizId);
    query.setParameter("rolId", rolId);
    return query.getResultList();
  }
  @SuppressWarnings("unchecked")
  public List<MuRolFormulario> findPrivilegios(Integer rolId)
  {
    String sql = "SELECT rf FROM MuRolFormulario rf WHERE rf.id.rolId = :rolId and rf.estado = true";
    Query query = this.entityManager.createQuery(sql);
    query.setParameter("rolId", rolId);
    return query.getResultList();
  }
  
  public MuRolFormulario findPrivilegios(Integer formId, Integer rolId)
  {
    String sql = "SELECT rf FROM MuRolFormulario rf WHERE rf.id.rolId = :rolId AND rf.id.formularioId = :formId";
    Query query = this.entityManager.createQuery(sql);
    query.setParameter("formId", formId);
    query.setParameter("rolId", rolId);
    List list = query.getResultList();
    if (list.size() > 0) {
      return (MuRolFormulario)list.get(0);
    }
    return null;
  }
  @SuppressWarnings("unchecked")
  public List<MuAccion> findAccionesFormulario(Integer formId)
  {
    String sql = "SELECT AC FROM MuAccion AC INNER JOIN AC.muFormularios formulario WHERE formulario.id = :formId AND AC.estado = true";
    Query query = this.entityManager.createQuery(sql, MuAccion.class);
    query.setParameter("formId", formId);
    return query.getResultList();
  }
}
