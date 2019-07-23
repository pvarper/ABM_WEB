package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.ussd.model.TTipoDocumento;

@Named
public class TipoDocumentoDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TTipoDocumento dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TTipoDocumento get(Long id) {
		return (TTipoDocumento) this.entityManager.find(TTipoDocumento.class, id);
	}

	public void update(TTipoDocumento dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TTipoDocumento) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TTipoDocumento dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TTipoDocumento) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TTipoDocumento> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TTipoDocumento us  where us.estado=true Order by us.id")
				.getResultList();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public TTipoDocumento getTipoDocumentoNombre(String nombre) {
		String consulta = "SELECT us FROM TTipoDocumento us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TTipoDocumento> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TTipoDocumento) lista.get(0);
	}

	

}
