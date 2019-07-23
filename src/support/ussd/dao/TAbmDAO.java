package support.ussd.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import support.ussd.model.TAbm;
import support.ussd.model.TAbmAdjunto;
import support.ussd.model.TAbmAdm;
import support.ussd.model.TAbmAdmPK;
import support.ussd.model.TAbmSer;
import support.ussd.model.TServicio;

@Named
public class TAbmDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	public void save(TAbm dato,List<TServicio> lista, List<TAbmSer> lista2) throws Exception {

		try {
			this.transaction.begin();
			
			this.entityManager.persist(dato);
			List<TAbmAdm> TAbmAdms= new ArrayList<TAbmAdm>();
			if(lista!=null){
				for (TServicio muUsuario : lista) {
					TAbmAdm a= new TAbmAdm();
					TAbmAdmPK pk= new TAbmAdmPK();
					pk.setIdServicio(muUsuario.getId());
					pk.setIdAbm(dato.getId());			
					a.setTAbm(dato);
					a.setTServicio(muUsuario);
					a.setEstado("PENDIENTE");
					a.setId(pk);
					//this.entityManager.merge(this.entityManager.contains(a) ? a: (TSerAdm) this.entityManager.merge(a));
					TAbmAdms.add(a);
				}	
			}
			
			
			//dato.setTAbmAdjuntos(lista3);
			dato.setTAbmAdms(TAbmAdms);
			dato.setTAbmSers(lista2);
			//this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TAbm) this.entityManager.merge(dato));
			this.entityManager.merge(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}
	
	
	
	public void updateAdjunto(TAbm dato,List<TAbmAdjunto> lista3) throws Exception {

		try {
			this.transaction.begin();
			
			if(lista3!=null){
				for (TAbmAdjunto a : lista3) {					
					a.setTAbm(dato);
					a.setIdAbm(dato.getId());
				}	
			}
			
			dato.setTAbmAdjuntos(lista3);
			//this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TAbm) this.entityManager.merge(dato));
			this.entityManager.merge(dato);
			this.transaction.commit();
		} catch (Exception e) {
			this.transaction.rollback();
			throw e;
		}

	}

	public TAbm get(Long id) {
		return (TAbm) this.entityManager.find(TAbm.class, id);
	}
	
	public TAbmSer getMigrar(Long id) {
		return (TAbmSer) this.entityManager.find(TAbmSer.class, id);
	}

	public void update(TAbm dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TAbm) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void updateMigrar(TAbmSer dato) throws Exception {
		this.transaction.begin();

		this.entityManager.merge(this.entityManager.contains(dato) ? dato: (TAbmSer) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	
	public void migrar(TAbmSer dato) throws Exception {
		this.transaction.begin();

		this.entityManager.persist(dato);

		this.transaction.commit();
	}
	
	public void delete(TAbm dato) throws Exception {
		this.transaction.begin();

		this.entityManager.remove(this.entityManager.contains(dato) ? dato: (TAbm) this.entityManager.merge(dato));

		this.transaction.commit();
	}
	

	@SuppressWarnings("unchecked")
	public List<TAbm> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbm us  where us.estado=true Order by us.id desc")
				.getResultList();
	}
	
	

	@SuppressWarnings("unchecked")
	public TAbm getTAbm(int nombre) {
		String consulta = "SELECT us FROM TAbm us WHERE us.abm =:nombre  and us.estado=true";
		Query qu = this.entityManager.createQuery(consulta).setParameter("nombre", nombre);
		List<TAbm> lista = qu.getResultList();
		return lista.isEmpty() ? null : (TAbm) lista.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmAdm> getTAbmAdm(Long nombre) {		
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmAdm us WHERE us.id.idAbm="+nombre)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> getTAbmSerXAbm(Long nombre, Long id) {		
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us WHERE us.TAbm.id="+nombre)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> getTAbmSerXAbmXAdm(Long abm, Long id) {		
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us WHERE us.TAbm.id="+abm+" and us.idUsuario="+id)
				.getResultList();
	}
	
	
	public boolean reasignar(String adminReasignado, String nroAbm,String servicio) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		String consulta = "update t_abm_ser set id_usuario="+adminReasignado+" where"
				+ " id_abm=(select id from t_abm where abm="+nroAbm+") and id_servicio="+servicio;
		transaction.begin();
		int res=this.entityManager.createNativeQuery(consulta).executeUpdate();
		transaction.commit();
		return  res==-1? false:true;
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> getTAbmSerXAbmSinOrder(Long nombre) {		
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us WHERE us.TAbm.id="+nombre)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> getTAbmSerXid(Long nombre) {		
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us WHERE us.id="+nombre)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmAdm> getListServicioPorABM(long a) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmAdm us")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> migrar2() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us ")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> obtenerServPendienteAllAbm() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us where us.estado='PENDIENTE'")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> obtenerServPendienteXusuario(Long id) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us where us.estado='PENDIENTE' and us.idUsuario="+id)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmSer> obtenerServPendienteXusuarioO(Long id) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmSer us where (us.estado='PENDIENTE' OR us.estado='OBSERVADO') and us.idUsuario="+id)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> obtenerServPendienteXusuarioDistinct(Long id) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT distinct us.TAbm.abm FROM TAbmSer us where us.estado='PENDIENTE' and us.idUsuario="+id)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmAdm> getListAbmAdm(TAbmAdmPK a) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmAdm us where us.id.idAbm="+a.getIdAbm() +" and us.id.idServicio="+a.getIdServicio())
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TAbmAdjunto> getListAbmAdjuntos(long a) throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TAbmAdjunto us where us.TAbm.id="+a )
				.getResultList();
	}

}
