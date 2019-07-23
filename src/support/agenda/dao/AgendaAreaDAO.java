package support.agenda.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import support.agenda.bean.AgendaActividadBean;
import support.ussd.model.TArea;

public class AgendaAreaDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaAreaDAO.class);
	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	@SuppressWarnings("unchecked")
	public List<TArea> getListArea() {
		try {
			return this.entityManager
					.createQuery(
							"SELECT a FROM TArea a where a.estado=true Order by a.id DESC")
					.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error al obtener la lista de AREA " + e.getCause());
			return null;
		}
	}

}
