package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.ussd.model.TRegistro;

@Named
public class RegistroDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TRegistro dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TRegistro get(Long id) {
		return (TRegistro) this.entityManager.find(TRegistro.class, id);
	}

	public void update(TRegistro dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TRegistro) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TRegistro dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TRegistro) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TRegistro> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TRegistro us  Order by us.id desc")
				.getResultList();
	}
	
	

}
