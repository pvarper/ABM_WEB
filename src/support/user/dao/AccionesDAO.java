package support.user.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.user.model.MuAccion;

@Named
public class AccionesDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public MuAccion get(long id)
    throws Exception
  {
    return (MuAccion)this.entityManager.find(MuAccion.class, Long.valueOf(id));
  }
  @SuppressWarnings("unchecked")
  public List<MuAccion> getList()
  {
    return this.entityManager.createQuery("SELECT ac FROM MuAccion ac where ac.estado = true order by ac.id").getResultList();
  }
}
