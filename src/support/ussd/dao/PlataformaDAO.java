package support.ussd.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.primefaces.model.DualListModel;

import support.user.business.UsuarioBL;
import support.user.model.MuUsuario;
import support.ussd.model.TPlataforma;
import support.ussd.model.TPlataformaAdministrador;
import support.ussd.model.TPlataformaAdministradorPK;

@Named
public class PlataformaDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;
	
	@Inject
	private UsuarioBL usuBL;

	public void save(TPlataforma dato) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}
	
	
	public void savePlaAdm(TPlataforma dato,DualListModel<String> selectItemsUsuario) throws Exception {

		try {
			this.transaction.begin();
			this.entityManager.persist(dato);

			List<TPlataformaAdministrador> listaPlatAdmin= new ArrayList<TPlataformaAdministrador>();
			
			for (String a : selectItemsUsuario.getTarget()) {
				TPlataformaAdministrador b= new TPlataformaAdministrador();
				TPlataformaAdministradorPK c= new TPlataformaAdministradorPK();
				c.setPlataformaId(dato.getId());
				MuUsuario u=usuBL.getUserLogin(a);
				c.setUsuarioId(u.getUsuarioId());
				b.setPlataformaId(dato);
				b.setUsuarioId(u);
				b.setDescripcion("prueba");
				b.setId(c);
				listaPlatAdmin.add(b);
			}
			dato.setPlataforma(listaPlatAdmin);
			
			//this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TPlataforma) this.entityManager.merge(dato));
			this.entityManager.persist(dato);
			
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		
		}
	}
//	public void savePlatAdm(List<TPlataformaAdministrador> dato2) throws Exception {
//
//		try {
//			this.transaction.begin();
//			this.entityManager.persist(dato);
//			this.transaction.commit();
//		} catch (Exception e) {
//			this.transaction.rollback();
//			throw e;
//		}
//
//	}

	public TPlataforma get(Long id) {
		return (TPlataforma) this.entityManager.find(TPlataforma.class, id);
	}

	public void update(TPlataforma dato) throws Exception {
//		deleteDetalle(dato.getId());
		this.transaction.begin();
		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TPlataforma) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	
	
	public void delete(TPlataforma dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TPlataforma) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TPlataforma> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TPlataforma us  where us.estado=true and us.uso<>'DOCUMENTACION' Order by us.id ASC")
				.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<TPlataforma> getListDocumentos() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TPlataforma us  where us.uso='DOCUMENTACION' and us.estado=true Order by us.id ASC")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TPlataforma> getListAltas() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TPlataforma us  where us.uso='ALTA' and us.estado=true Order by us.id")
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<TPlataforma> getListBajas() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TPlataforma us  where us.uso='BAJA' and us.estado=true Order by us.id")
				.getResultList();
	}
	

	public void deleteDetalle(Long idPlataforma) throws Exception {
		String consulta = "delete from TPlataformaAdministrador us where us.plataformaId.id=:idPlataforma";
		Query qu = this.entityManager.createQuery(consulta).setParameter("idPlataforma", idPlataforma);
		int deletecoun=qu.executeUpdate();
		System.out.println(deletecoun);
		
	}
	
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
	public TPlataforma getPlataforma(Long nombre) {
		String consulta = "SELECT us FROM TPlataforma us WHERE us.id = :nombre and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter(
				"nombre", nombre);
		List<TPlataforma> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TPlataforma) lista.get(0);
	}
	@SuppressWarnings("unchecked")
	public TPlataforma geTPlataformaNombre(String nombre,String uso) {
		String consulta = "SELECT us FROM TPlataforma us WHERE trim(lower(us.nombre)) =trim(lower(:nombre)) and us.uso=:uso and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter("nombre", nombre);
		qu.setParameter("uso", uso);
		List<TPlataforma> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TPlataforma) lista.get(0);
	}

}
