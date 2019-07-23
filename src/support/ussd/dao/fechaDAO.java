package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.ussd.model.TFecha;

@Named
public class fechaDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TFecha dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TFecha get(Long id) {
		return (TFecha) this.entityManager.find(TFecha.class, id);
	}

	public void update(TFecha dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TFecha) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TFecha dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TFecha) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TFecha> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TFecha us  Order by us.id desc")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TFecha> getListPen() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TFecha us where us.estado='PEN' Order by us.id desc")
				.getResultList();
	}
	
	

}
