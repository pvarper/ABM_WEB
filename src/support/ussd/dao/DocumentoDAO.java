package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.ussd.model.TDocumento;

@Named
public class DocumentoDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TDocumento dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TDocumento get(Long id) {
		return (TDocumento) this.entityManager.find(TDocumento.class, id);
	}

	public void update(TDocumento dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TDocumento) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TDocumento dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TDocumento) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TDocumento> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TDocumento us  where us.estado=true Order by us.id DESC")
				.getResultList();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public TDocumento getTDocumentoNombre(String nombre) {
		String consulta = "SELECT us FROM TDocumento us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TDocumento> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TDocumento) lista.get(0);
	}

	

}
