package support.agenda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the agenda_tarea database table.
 * 
 */
@Entity
@Table(name="agenda_tarea")
@NamedQuery(name="AgendaTarea.findAll", query="SELECT a FROM AgendaTarea a")
public class AgendaTarea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AGENDA_TAREA_ID_GENERATOR", sequenceName="AGENDA_TAREA_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AGENDA_TAREA_ID_GENERATOR")
	private Integer id;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_finalizacion")
	private Date fechaFinalizacion;

	@Column(name="id_area")
	private Integer idArea;

	@Column(name="id_usuario")
	private Integer idUsuario;

	//bi-directional many-to-one association to AgendaActividad
	@ManyToOne
	@JoinColumn(name="id_actividad")
	private AgendaActividad agendaActividad;

	//bi-directional many-to-one association to AgendaDetalle
	@ManyToOne
	@JoinColumn(name="id_detalle")
	private AgendaDetalle agendaDetalle;

	public AgendaTarea() {
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

	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Integer getIdArea() {
		return this.idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public AgendaActividad getAgendaActividad() {
		return this.agendaActividad;
	}

	public void setAgendaActividad(AgendaActividad agendaActividad) {
		this.agendaActividad = agendaActividad;
	}

	public AgendaDetalle getAgendaDetalle() {
		return this.agendaDetalle;
	}

	public void setAgendaDetalle(AgendaDetalle agendaDetalle) {
		this.agendaDetalle = agendaDetalle;
	}

}