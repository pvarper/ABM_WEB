package support.agenda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the agenda_arquitecto database table.
 * 
 */
@Entity
@Table(name="agenda_arquitecto")
@NamedQuery(name="AgendaArquitecto.findAll", query="SELECT a FROM AgendaArquitecto a")
public class AgendaArquitecto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AGENDA_ARQUITECTO_ID_GENERATOR", sequenceName="AGENDA_ARQUITECTO_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AGENDA_ARQUITECTO_ID_GENERATOR")
	private Integer id;

	private String nombre;

	private String usuario;

	//bi-directional many-to-one association to AgendaActividad
	@OneToMany(mappedBy="agendaArquitecto")
	private List<AgendaActividad> agendaActividads;

	public AgendaArquitecto() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<AgendaActividad> getAgendaActividads() {
		return this.agendaActividads;
	}

	public void setAgendaActividads(List<AgendaActividad> agendaActividads) {
		this.agendaActividads = agendaActividads;
	}

	public AgendaActividad addAgendaActividad(AgendaActividad agendaActividad) {
		getAgendaActividads().add(agendaActividad);
		agendaActividad.setAgendaArquitecto(this);

		return agendaActividad;
	}

	public AgendaActividad removeAgendaActividad(AgendaActividad agendaActividad) {
		getAgendaActividads().remove(agendaActividad);
		agendaActividad.setAgendaArquitecto(null);

		return agendaActividad;
	}

}