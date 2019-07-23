package support.ussd.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import support.ussd.model.TFreem;

@Named
public class FreemDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;



	public TFreem get(Long id) {
		return (TFreem) this.entityManager.find(TFreem.class, id);
	}


	
	

	@SuppressWarnings("unchecked")
	public List<TFreem> getList() throws Exception {
		return this.entityManager
				.createQuery(
						"SELECT us FROM TFreem us  Order by us.id desc")
				.getResultList();
	}
	
	

}
