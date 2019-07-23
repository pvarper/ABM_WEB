package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_puntosmejora database table.
 * 
 */
@Entity
@Table(name="t_puntosmejora")
@NamedQuery(name="TPuntosmejora.findAll", query="SELECT t FROM TPuntosmejora t")
public class TPuntosmejora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_PUNTOSMEJORA_ID_GENERATOR", sequenceName="T_PUNTOSMEJORA_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_PUNTOSMEJORA_ID_GENERATOR")
	private Long id;

	private String descripcion;

	private String responsable;

	//bi-directional many-to-one association to TReporte
	@ManyToOne
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	public TPuntosmejora() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public TReporte getTReporte() {
		return this.TReporte;
	}

	public void setTReporte(TReporte TReporte) {
		this.TReporte = TReporte;
	}

}