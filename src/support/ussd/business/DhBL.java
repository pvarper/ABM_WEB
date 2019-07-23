package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.DhDAO;
import support.ussd.model.TDh;

@Named
public class DhBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DhDAO dao;

	
	
	public List<TDh> getList() throws Exception {
		return this.dao.getList();
	}

	public TDh getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	
}
