package support.agenda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the agenda_actividad database table.
 * 
 */
@Entity
@Table(name="agenda_actividad")
@NamedQuery(name="AgendaActividad.findAll", query="SELECT a FROM AgendaActividad a")
public class AgendaActividad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AGENDA_ACTIVIDAD_ID_GENERATOR", sequenceName="AGENDA_ACTIVIDAD_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AGENDA_ACTIVIDAD_ID_GENERATOR")
	private Integer id;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_ejecucion")
	private Date fechaEjecucion;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="id_area_funcional")
	private Integer idAreaFuncional;

	@Column(name="id_area_solicitante")
	private Integer idAreaSolicitante;

	@Column(name="id_portal")
	private Integer idPortal;

	private String titulo;

	//bi-directional many-to-one association to AgendaArquitecto
	@ManyToOne
	@JoinColumn(name="id_arquitecto")
	private AgendaArquitecto agendaArquitecto;

	//bi-directional many-to-one association to AgendaTipo
	@ManyToOne
	@JoinColumn(name="id_tipo_actividad")
	private AgendaTipo agendaTipo1;

	//bi-directional many-to-one association to AgendaTipo
	@ManyToOne
	@JoinColumn(name="id_tipo_solicitud")
	private AgendaTipo agendaTipo2;

	//bi-directional many-to-one association to AgendaTarea
	@OneToMany(mappedBy="agendaActividad")
	private List<AgendaTarea> agendaTareas;

	public AgendaActividad() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEjecucion() {
		return this.fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Timestamp getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Timestamp getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Integer getIdAreaFuncional() {
		return this.idAreaFuncional;
	}

	public void setIdAreaFuncional(Integer idAreaFuncional) {
		this.idAreaFuncional = idAreaFuncional;
	}

	public Integer getIdAreaSolicitante() {
		return this.idAreaSolicitante;
	}

	public void setIdAreaSolicitante(Integer idAreaSolicitante) {
		this.idAreaSolicitante = idAreaSolicitante;
	}

	public Integer getIdPortal() {
		return this.idPortal;
	}

	public void setIdPortal(Integer idPortal) {
		this.idPortal = idPortal;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public AgendaArquitecto getAgendaArquitecto() {
		return this.agendaArquitecto;
	}

	public void setAgendaArquitecto(AgendaArquitecto agendaArquitecto) {
		this.agendaArquitecto = agendaArquitecto;
	}

	public AgendaTipo getAgendaTipo1() {
		return this.agendaTipo1;
	}

	public void setAgendaTipo1(AgendaTipo agendaTipo1) {
		this.agendaTipo1 = agendaTipo1;
	}

	public AgendaTipo getAgendaTipo2() {
		return this.agendaTipo2;
	}

	public void setAgendaTipo2(AgendaTipo agendaTipo2) {
		this.agendaTipo2 = agendaTipo2;
	}

	public List<AgendaTarea> getAgendaTareas() {
		return this.agendaTareas;
	}

	public void setAgendaTareas(List<AgendaTarea> agendaTareas) {
		this.agendaTareas = agendaTareas;
	}

	public AgendaTarea addAgendaTarea(AgendaTarea agendaTarea) {
		getAgendaTareas().add(agendaTarea);
		agendaTarea.setAgendaActividad(this);

		return agendaTarea;
	}

	public AgendaTarea removeAgendaTarea(AgendaTarea agendaTarea) {
		getAgendaTareas().remove(agendaTarea);
		agendaTarea.setAgendaActividad(null);

		return agendaTarea;
	}

}