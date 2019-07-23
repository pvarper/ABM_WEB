package support.agenda.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.agenda.model.AgendaTipo;
import support.ussd.model.TArea;

public class AgendaTipoSolicitudDAO implements Serializable{
	 private static final long serialVersionUID = 1L;
	  @PersistenceContext(unitName="pUnit_dbSystem")
	  private transient EntityManager entityManager;
	  @Resource
	  private transient UserTransaction transaction;
	  
	  @SuppressWarnings("unchecked")
	  public List<AgendaTipo> getTipoSolicitante()
	  {
	    return this.entityManager.createQuery("SELECT a FROM agenda_tipo a  Order by a.id DESC").getResultList();
	  }

}
