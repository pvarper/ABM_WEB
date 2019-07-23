package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.ProcesoDAO;
import support.ussd.model.TDh;
import support.ussd.model.TFreem;
import support.ussd.model.TProceso;
import support.ussd.model.TTop;

@Named
public class ProcesosBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ProcesoDAO dao;

	

	

	public List<TTop> getList() throws Exception {
		return this.dao.getList();
	}


	public TTop getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	public TTop getTTop(Long idUser) throws Exception {
		return this.dao.getTTop(idUser);
	}


	public TTop getTTopNombre(String nombre) {
		return this.dao.getTTopNombre(nombre);
	}
	
	public List<TProceso> getTtopProcesos(long id) {
		return this.dao.getTtopProcesos(id);
	}
	public List<TDh> getTtopDh(long id) {
		return this.dao.getTtopDh(id);
	}
	public List<TFreem> getTtopFreem(long id) {
		return this.dao.getTtopFreem(id);
	}

}
