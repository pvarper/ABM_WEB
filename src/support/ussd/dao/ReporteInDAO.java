package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.ussd.model.TAntecedente;
import support.ussd.model.TAppinvolucrada;
import support.ussd.model.TCronologia;
import support.ussd.model.TDato;
import support.ussd.model.TParticipante;
import support.ussd.model.TPlanaccion;
import support.ussd.model.TPuntosmejora;
import support.ussd.model.TReporte;

@Named
public class ReporteInDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TReporte dato,TDato datos,List<TAppinvolucrada> app,List<TParticipante> part,List<TAntecedente> ant,
			List<TCronologia> cro,List<TPlanaccion> pla, List<TPuntosmejora> punt) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			datos.setTReporte(dato);			
			this.entityManager.persist(datos);
			
			for (TAppinvolucrada a : app) {
				a.setTReporte(dato);
			}
			dato.setTAppinvolucradas(app);
			this.entityManager.merge(dato);
			
			for (TParticipante a : part) {
				a.setTReporte(dato);
			}
			dato.setTParticipantes(part);
			this.entityManager.merge(dato);
			
			for (TAntecedente a : ant) {
				a.setTReporte(dato);
			}
			dato.setTAntecedentes(ant);
			this.entityManager.merge(dato);
			
			for (TCronologia a : cro) {
				a.setTReporte(dato);
			}
			dato.setTCronologias(cro);
			this.entityManager.merge(dato);
			
			for (TPlanaccion a : pla) {
				a.setTReporte(dato);
			}
			dato.setTPlanaccions(pla);
			this.entityManager.merge(dato);
			
			for (TPuntosmejora a : punt) {
				a.setTReporte(dato);
			}
			dato.setTPuntosmejoras(punt);
			this.entityManager.merge(dato);
			
			
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TReporte get(Long id) {
		return (TReporte) this.entityManager.find(TReporte.class, id);
	}

	public void update(TReporte dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TReporte) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void delete(TReporte dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TReporte) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TReporte> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TReporte us  where us.estado=true Order by us.id desc")
				.getResultList();
	}

	
//	@SuppressWarnings("unchecked")
//	public List<TPlataforma> getListDocumentos() throws Exception {
//		return this.entityManager
//				.createQuery(
//						"SELECT us FROM TPlataforma us  where us.uso='DOCUMENTACION' and us.estado=true Order by us.id")
//				.getResultList();
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<TPlataforma> getListAltas() throws Exception {
//		return this.entityManager
//				.createQuery(
//						"SELECT us FROM TPlataforma us  where us.uso='ALTA' and us.estado=true Order by us.id")
//				.getResultList();
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<MuRol> getListRolPlataforma(Long idPlataforma) throws Exception {
//		String consulta = "SELECT us FROM TRol us  where us.TPlataforma.id=:idPlataforma and us.estado=true Order by us.id";
//		Query qu = this.entityManager.createQuery(consulta).setParameter(
//				"idPlataforma", idPlataforma);
//		return qu.getResultList();
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<TAtributo> getListAtributoPlataforma(Long idPlataforma) throws Exception {
//		String consulta = "SELECT us FROM TAtributo us  where us.TPlataforma.id=:idPlataforma and us.estado=true Order by us.id";
//		Query qu = this.entityManager.createQuery(consulta).setParameter(
//				"idPlataforma", idPlataforma);
//		return qu.getResultList();
//	}
	
//	@SuppressWarnings("unchecked")
//	public TRol getRol(Long idRol) throws Exception {
//		String consulta = "SELECT us FROM TRol us  where us.id=:idPlataforma and us.estado=true Order by us.id";
//		Query qu = this.entityManager.createQuery(consulta).setParameter(
//				"idPlataforma", idRol);
//		List<TRol> lista = qu.getResultList();
//		return lista.isEmpty() ? null : (TRol) lista.get(0);
//	}
	
	@SuppressWarnings("unchecked")
	public TReporte getReporte(Long nombre) {
		String consulta = "SELECT us FROM TReporte us WHERE us.id = :nombre and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TReporte> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TReporte) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public TDato getReporteDatos(Long nombre) {
		String consulta = "SELECT us FROM TDato us WHERE us.TReporte.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TDato> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TDato) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<TAppinvolucrada> getReporteApp(Long nombre) {
		String consulta = "SELECT us FROM TAppinvolucrada us WHERE us.TReporte.id = :nombre ";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TAppinvolucrada> lista = qu.getResultList();
		return lista.isEmpty() ? null : lista;
	}
	
	@SuppressWarnings("unchecked")
	public TReporte getTReporteNombre(String nombre) {
		String consulta = "SELECT us FROM TPlataforma us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter("nombre", nombre);
		List<TReporte> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TReporte) lista.get(0);
	}

}
