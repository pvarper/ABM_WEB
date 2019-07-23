package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import support.ussd.dao.TAbmDAO;
import support.ussd.model.TAbm;
import support.ussd.model.TAbmAdjunto;
import support.ussd.model.TAbmAdm;
import support.ussd.model.TAbmAdmPK;
import support.ussd.model.TAbmSer;
import support.ussd.model.TServicio;

@Named
public class TAbmBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TAbmDAO dao;



	public void save(TAbm user,List<TServicio> lista, List<TAbmSer> lista2) throws Exception {
		this.dao.save(user,lista,lista2);
	}
	
	public void updateAdjunto(TAbm user,List<TAbmAdjunto> lista3) throws Exception {
		this.dao.updateAdjunto(user,lista3);
	}
	
	public void migrar(TAbmSer a) throws Exception {
		this.dao.migrar(a);
	}
	
	public void delete(TAbm user) throws Exception {
		user.setEstado(false);
		this.dao.update(user);
	}

	public void update(TAbm user) throws Exception {
		TAbm userAux = this.dao.get(user.getId());
		userAux.setAbm(user.getAbm());
		userAux.setAdjunto(user.getAdjunto());
		userAux.setAreasucursal(user.getAreasucursal());
		userAux.setCargo(user.getCargo());
		userAux.setEhumano(user.getEhumano());
		userAux.setFaltantes(user.getFaltantes());
		userAux.setFechafin(user.getFechafin());
		userAux.setObservacionAbm(user.getObservacionAbm());
		userAux.setObservacionQflow(user.getObservacionQflow());
		userAux.setSolicitante(user.getSolicitante());
		userAux.setUsuario(user.getUsuario());
		userAux.setEstado(user.getEstado());
		userAux.setTAbmAdms(user.getTAbmAdms());
		userAux.setTAbmSers(user.getTAbmSers());
		userAux.setEstadoProceso(user.getEstadoProceso());
		userAux.setFechafinalizado(user.getFechafinalizado());
		userAux.setFechaenvio(user.getFechaenvio());
		this.dao.update(userAux);
	}
	
	public void updateMigrar(TAbmSer user) throws Exception {
		TAbmSer userAux = this.dao.getMigrar(user.getId());
		userAux.setIdUsuario(user.getIdUsuario());
		this.dao.updateMigrar(userAux);
	}

	public List<TAbm> getList() throws Exception {
		return this.dao.getList();
	}


	public TAbm get(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}
	
	public TAbmSer getMigrar(Long idUser) throws Exception {
		return this.dao.getMigrar(idUser);
	}

	public TAbm getTAbm(int idUser) throws Exception {
		return this.dao.getTAbm(idUser);
	}
	
	public List<TAbmAdm> getTAbmAdm(Long idUser) throws Exception {
		return this.dao.getTAbmAdm(idUser);
	}
	
	public List<TAbmSer> getTAbmSerXAbm(Long idUser, Long id) throws Exception {
		return this.dao.getTAbmSerXAbm(idUser,id);
	}
	
	public List<TAbmSer> getTAbmSerXAbmXAdm(Long abm, Long id) throws Exception {
		return this.dao.getTAbmSerXAbmXAdm(abm, id);
	}
	
	public List<TAbmSer> getTAbmSerXAbmSinOrder(Long idUser) throws Exception {
		return this.dao.getTAbmSerXAbmSinOrder(idUser);
	}
	
	public List<TAbmSer> getTAbmSerXid(Long idUser) throws Exception {
		return this.dao.getTAbmSerXid(idUser);
	}
	
	public boolean reasignar(String adminReasignado, String nroAbm,String servicio) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		return this.dao.reasignar(adminReasignado, nroAbm,servicio);
	}
	
	public List<TAbmSer> migrar2() throws Exception {
		return this.dao.migrar2();
	}
	
	public List<TAbmSer> obtenerServPendienteAllAbm() throws Exception {
		return this.dao.obtenerServPendienteAllAbm();
	}
	
	public List<TAbmSer> obtenerServPendienteXusuario(Long id) throws Exception {
		return this.dao.obtenerServPendienteXusuario(id);
	}
	
	public List<TAbmSer> obtenerServPendienteXusuarioO(Long id) throws Exception {
		return this.dao.obtenerServPendienteXusuarioO(id);
	}
	
	public List<Integer> obtenerServPendienteXusuarioDistinct(Long id) throws Exception {
		return this.dao.obtenerServPendienteXusuarioDistinct(id);
	}

	public List<TAbmAdm> getListServicioPorABM(long id) throws Exception {
		return this.dao.getListServicioPorABM(id);
	}
	
	public List<TAbmAdm> getListAbmAdm(TAbmAdmPK id) throws Exception {
		return this.dao.getListAbmAdm(id);
	}
	public List<TAbmAdjunto> getListAbmAdjuntos(long id) throws Exception {
		return this.dao.getListAbmAdjuntos(id);
	}
}
