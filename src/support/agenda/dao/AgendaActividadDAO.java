package support.agenda.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.agenda.model.AgendaActividad;

@Named
public class AgendaActividadDAO implements Serializable{

	 private static final long serialVersionUID = 1L;
	  @PersistenceContext(unitName="pUnit_dbSystem")
	  private transient EntityManager entityManager;
	  @Resource
	  private transient UserTransaction transaction;
	  
	  @SuppressWarnings("unchecked")
	  public List<AgendaActividad> getListActividad()
	  {
	    return this.entityManager.createQuery("SELECT a FROM AgendaActividad a").getResultList();
	  }
	
}
