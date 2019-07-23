package support.agenda.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import support.agenda.bean.AgendaActividadBean;
import support.agenda.model.AgendaActividad;
import support.user.model.MuRol;

public class AgendaAreaAsignadaDAO {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaAreaAsignadaDAO.class);
	  @PersistenceContext(unitName="pUnit_dbSystem")
	  private transient EntityManager entityManager;
	  @Resource
	  private transient UserTransaction transaction;
	  
	  @SuppressWarnings("unchecked")
	  public List<MuRol> getListAreaAsignada()
	  {
			  log.info("Ingreso a la Lista del DAO");
			    return this.entityManager.createQuery("SELECT r FROM MuRol r WHERE  r.estado = true Order By r.rolId").getResultList();
			  
	  }
	
}
