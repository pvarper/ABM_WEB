package support.agenda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the agenda_tipo database table.
 * 
 */
@Entity
@Table(name="agenda_tipo")
@NamedQuery(name="AgendaTipo.findAll", query="SELECT a FROM AgendaTipo a")
public class AgendaTipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AGENDA_TIPO_ID_GENERATOR", sequenceName="AGENDA_TIPO_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AGENDA_TIPO_ID_GENERATOR")
	private Integer id;

	private String descripcion;

	private String nombre;

	private String tipo;

	//bi-directional many-to-one association to AgendaActividad
	@OneToMany(mappedBy="agendaTipo1")
	private List<AgendaActividad> agendaActividads1;

	//bi-directional many-to-one association to AgendaActividad
	@OneToMany(mappedBy="agendaTipo2")
	private List<AgendaActividad> agendaActividads2;

	public AgendaTipo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<AgendaActividad> getAgendaActividads1() {
		return this.agendaActividads1;
	}

	public void setAgendaActividads1(List<AgendaActividad> agendaActividads1) {
		this.agendaActividads1 = agendaActividads1;
	}

	public AgendaActividad addAgendaActividads1(AgendaActividad agendaActividads1) {
		getAgendaActividads1().add(agendaActividads1);
		agendaActividads1.setAgendaTipo1(this);

		return agendaActividads1;
	}

	public AgendaActividad removeAgendaActividads1(AgendaActividad agendaActividads1) {
		getAgendaActividads1().remove(agendaActividads1);
		agendaActividads1.setAgendaTipo1(null);

		return agendaActividads1;
	}

	public List<AgendaActividad> getAgendaActividads2() {
		return this.agendaActividads2;
	}

	public void setAgendaActividads2(List<AgendaActividad> agendaActividads2) {
		this.agendaActividads2 = agendaActividads2;
	}

	public AgendaActividad addAgendaActividads2(AgendaActividad agendaActividads2) {
		getAgendaActividads2().add(agendaActividads2);
		agendaActividads2.setAgendaTipo2(this);

		return agendaActividads2;
	}

	public AgendaActividad removeAgendaActividads2(AgendaActividad agendaActividads2) {
		getAgendaActividads2().remove(agendaActividads2);
		agendaActividads2.setAgendaTipo2(null);

		return agendaActividads2;
	}

}