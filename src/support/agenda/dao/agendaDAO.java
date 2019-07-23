package support.agenda.dao;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import support.agenda.model.AgendaArquitecto;
import support.ussd.model.TAnitaUsuario;

@Named
public class agendaDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void Save_Agenda_Arquitecto(AgendaArquitecto a)
			throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {
		// TODO Auto-generated method stub
		this.transaction.begin();
		this.entityManager.persist(a);
		this.transaction.commit();
	}

	
}
