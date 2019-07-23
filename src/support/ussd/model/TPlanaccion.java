package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the t_planaccion database table.
 * 
 */
@Entity
@Table(name="t_planaccion")
@NamedQuery(name="TPlanaccion.findAll", query="SELECT t FROM TPlanaccion t")
public class TPlanaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_PLANACCION_ID_GENERATOR", sequenceName="T_PLANACCION_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_PLANACCION_ID_GENERATOR")
	private Long id;

	private Timestamp fecha;

	private String responsable;

	private String tarea;

	//bi-directional many-to-one association to TReporte
	@ManyToOne
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	public TPlanaccion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getTarea() {
		return this.tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public TReporte getTReporte() {
		return this.TReporte;
	}

	public void setTReporte(TReporte TReporte) {
		this.TReporte = TReporte;
	}

}