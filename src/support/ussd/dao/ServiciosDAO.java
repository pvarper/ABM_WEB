package support.ussd.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.user.model.MuUsuario;
import support.ussd.model.TAbmAdm;
import support.ussd.model.TSerAdm;
import support.ussd.model.TSerAdmPK;
import support.ussd.model.TServicio;

@Named
public class ServiciosDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TServicio dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TServicio get(Long id) {
		return (TServicio) this.entityManager.find(TServicio.class, id);
	}

	public void update(TServicio dato,List<MuUsuario> lista) throws Exception {
		this.transaction.begin();

		
		List<TSerAdm> TSerAdms= new ArrayList<TSerAdm>();
		if(lista!=null){
			for (MuUsuario muUsuario : lista) {
				TSerAdm a= new TSerAdm();
				TSerAdmPK pk= new TSerAdmPK();
				pk.setIdAdm((long)muUsuario.getUsuarioId());
				pk.setIdServicio(dato.getId());			
				a.setTServicio(dato);
				a.setEstado("PENDIENTE");
				a.setId(pk);
				//this.entityManager.merge(this.entityManager.contains(a) ? a: (TSerAdm) this.entityManager.merge(a));
				TSerAdms.add(a);
			}	
		}
		dato.setTSerAdms(TSerAdms);
		
		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TServicio) this.entityManager.merge(dato));
		

		this.transaction.commit();
	}
	
	public void delete(TServicio dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TServicio) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TServicio> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TServicio us  where us.estado=true Order by us.id")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TSerAdm> getListAdmPorServicio(long a) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TSerAdm us")
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<TServicio> getListServicioPorAdmin(Long b) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TServicio us where us.id in (select a.id.idServicio FROM TSerAdm a"
						+ " where a.id.idAdm="+b+")")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public TServicio getServicio(Long nombre) {
		String consulta = "SELECT us FROM TServicio us WHERE us.id = :nombre and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TServicio> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TServicio) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public TServicio geTServicioNombre(String nombre) {
		String consulta = "SELECT us FROM TServicio us WHERE trim(lower(us.descripcion)) =trim(lower(:nombre))  and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter("nombre", nombre);
		List<TServicio> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TServicio) lista.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public TServicio getServicioABM(long nombre) {
		String consulta = "SELECT us FROM TServicio us WHERE us.idServicio = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TServicio> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TServicio) lista.get(0);
	}


}
