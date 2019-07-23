package support.agenda.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import support.agenda.model.AgendaArquitecto;
import support.user.business.AgendaArquitectoBL;
import support.ussd.model.TArea;

@Named
public class AgendaArquitectoDAO implements Serializable {
	public static Logger log = Logger.getLogger(AgendaArquitectoDAO.class);
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(AgendaArquitecto agendaArquitecto) {
		try {
			this.transaction.begin();
			this.entityManager.persist(agendaArquitecto);
			this.transaction.commit();

		} catch (Exception e) {
			log.error("[AgendaArquitectoDao:Save]" + e.getMessage());
		}
	}
	 @SuppressWarnings("unchecked")
	  public List<TArea> getArquitecto()
	  {
	    return this.entityManager.createQuery("SELECT a FROM agenda_arquitecto a ").getResultList();
	  }
	
}
