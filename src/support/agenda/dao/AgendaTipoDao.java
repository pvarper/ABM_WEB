package support.agenda.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import support.agenda.model.AgendaTipo;

public class AgendaTipoDao {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaTipoDao.class);
	public List<AgendaTipo> listTipoServicio;

	@PersistenceContext(unitName = "pUnit_dbSystem")
	private transient EntityManager entityManager;
	@Resource
	private transient UserTransaction transaction;

	@SuppressWarnings("unchecked")
	public List<AgendaTipo> getListAgenda_Tipo() {
		try {
			log.info("Obteniendo lista de AGENDA_TIPO");
			return this.entityManager.createQuery("SELECT a FROM AgendaTipo a")
					.getResultList();
		} catch (Exception e) {
			log.error("Error al obtener la lista de TIPO SOLICITUD "
					+ e.getMessage());
			return null;
		}
	}
}
