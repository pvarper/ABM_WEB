package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.ussd.model.TArea;
import support.ussd.model.TTerminales;

@Named
public class TermianlesDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TTerminales dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TTerminales get(Long id) {
		return (TTerminales) this.entityManager.find(TTerminales.class, id);
	}

	public void update(TTerminales dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TTerminales) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TTerminales dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TTerminales) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TTerminales> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TTerminales us  where us.estado=true Order by us.id")
				.getResultList();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public TTerminales getTerminalNombre(String nombre) {
		String consulta = "SELECT us FROM TTerminales us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TTerminales> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TTerminales) lista.get(0);
	}

	

}
