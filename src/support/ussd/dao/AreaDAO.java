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

@Named
public class AreaDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TArea dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TArea get(Long id) {
		return (TArea) this.entityManager.find(TArea.class, id);
	}

	public void update(TArea dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TArea) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TArea dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TArea) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TArea> getListArea() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TArea us  where us.estado=true and us.uso='PLATAFORMA' Order by us.id DESC")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TArea> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TArea us  where us.estado=true and us.uso='BCCS' Order by us.id DESC")
				.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public TArea geTAreaNombre(String nombre) {
		String consulta = "SELECT us FROM TArea us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TArea> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TArea) lista.get(0);
	}

	

}
