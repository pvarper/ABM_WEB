package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.ussd.model.TDh;
import support.ussd.model.TFreem;
import support.ussd.model.TProceso;
import support.ussd.model.TTop;


@Named
public class ProcesoDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;


	public TTop get(Long id) {
		return (TTop) this.entityManager.find(TTop.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TTop> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TTop us where us.estado='t' Order by us.id desc")
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public TTop getTTop(Long nombre) {
		String consulta = "SELECT us FROM TTop us WHERE us.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TTop> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TTop) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public TTop getTTopNombre(String nombre) {
		String consulta = "SELECT us FROM TTop us WHERE us.nombre = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TTop> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TTop) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<TProceso> getTtopProcesos(Long nombre) {
		String consulta = "SELECT us FROM TProceso us WHERE us.TTop.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TProceso> lista = qu.getResultList();
		return lista.isEmpty() ? null : lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<TDh> getTtopDh(Long nombre) {
		String consulta = "SELECT us FROM TDh us WHERE us.TTop.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TDh> lista = qu.getResultList();
		return lista.isEmpty() ? null : lista;
	}
	
	@SuppressWarnings("unchecked")
	public List<TFreem> getTtopFreem(Long nombre) {
		String consulta = "SELECT us FROM TFreem us WHERE us.TTop.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TFreem> lista = qu.getResultList();
		return lista.isEmpty() ? null : lista;
	}
}
