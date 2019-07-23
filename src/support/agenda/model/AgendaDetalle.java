package support.agenda.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the agenda_detalle database table.
 * 
 */
@Entity
@Table(name="agenda_detalle")
@NamedQuery(name="AgendaDetalle.findAll", query="SELECT a FROM AgendaDetalle a")
public class AgendaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AGENDA_DETALLE_ID_GENERATOR", sequenceName="AGENDA_DETALLE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AGENDA_DETALLE_ID_GENERATOR")
	private Integer id;

	private String descripcion;

	//bi-directional many-to-one association to AgendaTarea
	@OneToMany(mappedBy="agendaDetalle")
	private List<AgendaTarea> agendaTareas;

	public AgendaDetalle() {
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

	public List<AgendaTarea> getAgendaTareas() {
		return this.agendaTareas;
	}

	public void setAgendaTareas(List<AgendaTarea> agendaTareas) {
		this.agendaTareas = agendaTareas;
	}

	public AgendaTarea addAgendaTarea(AgendaTarea agendaTarea) {
		getAgendaTareas().add(agendaTarea);
		agendaTarea.setAgendaDetalle(this);

		return agendaTarea;
	}

	public AgendaTarea removeAgendaTarea(AgendaTarea agendaTarea) {
		getAgendaTareas().remove(agendaTarea);
		agendaTarea.setAgendaDetalle(null);

		return agendaTarea;
	}

}