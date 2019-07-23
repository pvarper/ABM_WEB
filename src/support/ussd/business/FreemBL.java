package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.FreemDAO;
import support.ussd.model.TFreem;

@Named
public class FreemBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private FreemDAO dao;

	
	
	public List<TFreem> getList() throws Exception {
		return this.dao.getList();
	}

	public TFreem getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	
}
