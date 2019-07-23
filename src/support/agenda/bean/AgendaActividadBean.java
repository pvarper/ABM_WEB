package support.agenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import support.agenda.dao.AgendaActividadDAO;
import support.agenda.dao.AgendaAreaAsignadaDAO;
import support.agenda.dao.AgendaAreaDAO;
import support.agenda.dao.AgendaArquitectoDAO;
import support.agenda.dao.AgendaTipoDao;
import support.agenda.dao.AgendaTipoSolicitudDAO;
import support.agenda.model.AgendaActividad;
import support.agenda.model.AgendaArquitecto;
import support.agenda.model.AgendaTarea;
import support.agenda.model.AgendaTipo;
import support.user.dao.FormDAO;
import support.user.dao.UsuarioDAO;
import support.user.model.MuRol;
import support.user.model.MuUsuario;
import support.ussd.model.TArea;

@ManagedBean
@ViewScoped
public class AgendaActividadBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaActividadBean.class);
	private ScheduleModel eventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	/* LISTA DE OBJETOS */
	private List<TArea> list_area;
	private List<TArea> list_areaFuncional;
	private List<AgendaTipo> list_tipoSolicitud;
	private List<AgendaTipo> list_tipoActividad;
	private List<MuRol> list_areaAsignada;
	private List<MuUsuario> list_usuario;


	/* LISTA VARIABLES */
	private String area;
	private String areaFuncional;
	private String tipoSolicitud;
	private String tipoActividad;
	private String area_asignada;
	private String usuario;

	/* ACCESO A DAO */
	@Inject
	private AgendaActividadDAO agendaActividadDAO;
	@Inject
	private AgendaTipoSolicitudDAO agendaTipoDAO;
	@Inject
	private AgendaArquitectoDAO agendaArquitectoDAO;
	@Inject
	private AgendaAreaDAO agendaAreaDAO;
	@Inject
	private AgendaTipoDao agendaTipoDao;
	@Inject
	private AgendaAreaAsignadaDAO agendaAreaAsignadaDAO;
	@Inject 
	private UsuarioDAO agendaUsuarioDAO;
	
	/* OBJETOS UTILIZADOS */
	@Inject
	private AgendaActividad agendaActividad;
	@Inject
	private AgendaTipo agendaTipo;
	@Inject
	private AgendaArquitecto agendaArquitecto;
	@Inject 
	private MuRol muRol;
	@Inject
	private MuUsuario agendaUsuario;

	public List<MuRol> getList_areaAsignada() {
		return list_areaAsignada;
	}

	public void setList_areaAsignada(List<MuRol> list_areaAsignada) {
		this.list_areaAsignada = list_areaAsignada;
	}

	@PostConstruct
	public void init() {
		cargarAgenda();
		getList_tipoActividad();
		getList_area();
		getList_tipoSolicitud();
		getList_asig_area();
	}
	
	private void cargarAgenda() {
		eventModel = new DefaultScheduleModel();

		log.info("Iniciar carga de Agenda Actividad");
		List<AgendaActividad> agenda = listAgendaActividad();
		for (AgendaActividad agendaActividad : agenda) {
			eventModel.addEvent(new DefaultScheduleEvent(agendaActividad
					.getTitulo(), agendaActividad.getFechaInicio(),
					agendaActividad.getFechaFin(), agendaActividad));
		}
	}
	public List<MuRol> listAgendaAreaAsignada(){
		List<MuRol> agendaAreaAsignada = new ArrayList<MuRol>();
		agendaAreaAsignada = this.agendaAreaAsignadaDAO.getListAreaAsignada();
		log.info("tamaño de la Lista" + agendaAreaAsignada.size());
		return agendaAreaAsignada;
	
		
	}
//	public List<MuUsuario> listUsuario(){
//		
////		List<MuUsuario> agendaUsuario = new ArrayList<MuUsuario>();
////		agendaUsuario= this.agendaUsuarioDAO.getList();
////		log.info("tamaño de la Lista" + agendaAreaAsignada.size());
////		return agendaAreaAsignada;	
//	}
	public List<AgendaActividad> listAgendaActividad() {
		List<AgendaActividad> agendaActividad = new ArrayList<AgendaActividad>();
		agendaActividad = this.agendaActividadDAO.getListActividad();
		log.info("tamaño de la Lista" + agendaActividad.size());
		return agendaActividad;
	}


	public ScheduleModel getEventModel() {
		return eventModel;
	}

	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE), 0, 0, 0);
		return calendar;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent() {
		if (event.getId() == null) {
			eventModel.addEvent(event);
			agendaActividad.setTitulo(event.getTitle());
		} else
			eventModel.updateEvent(event);
		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		agendaActividad = (AgendaActividad) event.getData();
		muRol=(MuRol)event.getData();
		area = agendaActividad.getIdAreaSolicitante() + "";
		areaFuncional = agendaActividad.getIdAreaFuncional() + "";
		tipoActividad = agendaActividad.getAgendaTipo1() + "";
		tipoSolicitud = agendaActividad.getAgendaTipo2() + "";
		log.info("----"+area_asignada+"--------");
		area_asignada = muRol.getRolId().toString();
		log.info("-****---"+area_asignada+"---****-----");
	}

	/* Ingresar nueva tarea */
	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public MuRol getMuRol() {
		return muRol;
	}

	public void setMuRol(MuRol muRol) {
		this.muRol = muRol;
	}
	public AgendaActividad getAgendaActividad() {
		return agendaActividad;
	}
	public List<MuUsuario> getList_usuario() {
		return list_usuario;
	}

	public void setList_usuario(List<MuUsuario> list_usuario) {
		this.list_usuario = list_usuario;
	}
	public void setAgendaActividad(AgendaActividad agendaActividad) {
		this.agendaActividad = agendaActividad;
	}

	public AgendaActividadDAO getAgendaActividadDAO() {
		return agendaActividadDAO;
	}

	public void setAgendaActividadDAO(AgendaActividadDAO agendaActividadDAO) {
		this.agendaActividadDAO = agendaActividadDAO;
	}
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public MuUsuario getAgendaUsuario() {
		return agendaUsuario;
	}

	public void setAgendaUsuario(MuUsuario agendaUsuario) {
		this.agendaUsuario = agendaUsuario;
	}

	public AgendaTipo getAgendaTipo() {
		return agendaTipo;
	}

	public void setAgendaTipo(AgendaTipo agendaTipo) {
		this.agendaTipo = agendaTipo;
	}

	public AgendaTipoSolicitudDAO getAgendaTipoDAO() {
		return agendaTipoDAO;
	}

	public void setAgendaTipoDAO(AgendaTipoSolicitudDAO agendaTipoDAO) {
		this.agendaTipoDAO = agendaTipoDAO;
	}

	public AgendaArquitecto getAgendaArquitecto() {
		return agendaArquitecto;
	}

	public void setAgendaArquitecto(AgendaArquitecto agendaArquitecto) {
		this.agendaArquitecto = agendaArquitecto;
	}

	public AgendaArquitectoDAO getAgendaArquitectoDAO() {
		return agendaArquitectoDAO;
	}

	public void setAgendaArquitectoDAO(AgendaArquitectoDAO agendaArquitectoDAO) {
		this.agendaArquitectoDAO = agendaArquitectoDAO;
	}

	public List<AgendaTipo> getList_tipoSolicitud() {
		log.info("Ingreso a cargar agenda_TipoSolicitud");
		list_tipoSolicitud = agendaTipoDao.getListAgenda_Tipo();
		return list_tipoSolicitud;
	}

	public void setList_tipoSolicitud(List<AgendaTipo> list_tipoSolicitud) {
		this.list_tipoSolicitud = list_tipoSolicitud;
	}

	public List<AgendaTipo> getList_tipoActividad() {
		log.info("Ingreso a cargar agenda_TipoActividad");
		list_tipoActividad = agendaTipoDao.getListAgenda_Tipo();
		return list_tipoActividad;
	}

	public void setList_tipoActividad(List<AgendaTipo> list_tipoActividad) {
		this.list_tipoActividad = list_tipoActividad;
	}

	public List<TArea> getList_areaFuncional() {
		log.info("Ingreso a cargar el area Funcional");
		list_areaFuncional = agendaAreaDAO.getListArea();
		return list_areaFuncional;
	}

	public void setList_areaFuncional(List<TArea> list_areaFuncional) {
		this.list_areaFuncional = list_areaFuncional;
	}

	public List<TArea> getList_area() {
		log.info("Ingreso a cargar el area");
		list_area = agendaAreaDAO.getListArea();
		return list_area;
	}
public List<MuRol> getList_asig_area(){
	log.info("Ingreso a cargar el area asignada");
	list_areaAsignada = agendaAreaAsignadaDAO.getListAreaAsignada();
	log.info(list_areaAsignada.get(0).getNombre() +" esta mostrando un nombre");
	return list_areaAsignada;
}
	public void setList_area(List<TArea> list_area) {
		this.list_area = list_area;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaFuncional() {
		return areaFuncional;
	}

	public void setAreaFuncional(String areaFuncional) {
		this.areaFuncional = areaFuncional;
	}

	public String getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}
	public String getArea_asignada() {
		return area_asignada;
	}

	public void setArea_asignada(String area_asignada) {
		this.area_asignada = area_asignada;
	}
}
