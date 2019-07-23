package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.ussd.model.TDh;

@Named
public class DhDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;



	public TDh get(Long id) {
		return (TDh) this.entityManager.find(TDh.class, id);
	}


	
	

	@SuppressWarnings("unchecked")
	public List<TDh> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TDh us  Order by us.id desc")
				.getResultList();
	}
	
	

}
