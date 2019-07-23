package support.agenda.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.agenda.dao.AgendaTipoDao;
import support.agenda.model.AgendaTipo;

@ManagedBean
@ViewScoped
public class AgendaTipoBean {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaTipoBean.class);
	private List<AgendaTipo> tipoSolicitud;
	private List<AgendaTipo> tipoActividad;
	@Inject
	private AgendaTipoDao agendaTipoDao;

	@PostConstruct
	public void init() {
		getTipoSolicitud();
		getTipoActividad();
	}

	public List<AgendaTipo> getTipoSolicitud() {
		tipoSolicitud = agendaTipoDao.getListAgenda_Tipo();
		return tipoSolicitud;
	}

	public List<AgendaTipo> getTipoActividad() {
		tipoActividad = agendaTipoDao.getListAgenda_Tipo();
		return tipoActividad;
	}

	public void setTipoActividad(List<AgendaTipo> tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public void setTipoSolicitud(List<AgendaTipo> tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
}
