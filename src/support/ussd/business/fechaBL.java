package support.ussd.business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.fechaDAO;
import support.ussd.model.TFecha;

@Named
public class fechaBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private fechaDAO dao;

	
	public void save(String comprobante,String serie,String numero,String fechaAnterior) throws Exception {
		TFecha u= new TFecha();
		u.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		u.setComprobante(comprobante);
		u.setSerie(serie);
		u.setNumero(numero);
		u.setFechaAnterior(fechaAnterior);
		u.setEstado("PEN");
		this.dao.save(u);
	}
	
	public void update(TFecha u) throws Exception {
		TFecha userAux = this.dao.get(u.getId());
		u.setFecha(userAux.getFecha());
		u.setComprobante(userAux.getComprobante());
		u.setSerie(userAux.getSerie());
		u.setNumero(userAux.getNumero());
		u.setFechaAnterior(userAux.getFechaAnterior());
		u.setEstado("ENV");
		this.dao.update(u);
	}
	
	public List<TFecha> getList() throws Exception {
		return this.dao.getList();
	}
	public List<TFecha> getListPen() throws Exception {
		return this.dao.getListPen();
	}

	public TFecha getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	
}
