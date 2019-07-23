package support.ussd.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the t_datos database table.
 * 
 */
@Entity
@Table(name="t_datos")
@NamedQuery(name="TDato.findAll", query="SELECT t FROM TDato t")
public class TDato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_DATOS_ID_GENERATOR", sequenceName="T_DATOS_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_DATOS_ID_GENERATOR")
	private Long id;

	private String afectacion;

	@Column(name="bu_afectada")
	private String buAfectada;

	private String causa;

	private String detallecausa;

	private String idticket;

	private String incidente;

	private String solucion;

	//uni-directional many-to-one association to TReporte
	@ManyToOne
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	public TDato() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAfectacion() {
		return this.afectacion;
	}

	public void setAfectacion(String afectacion) {
		this.afectacion = afectacion;
	}

	public String getBuAfectada() {
		return this.buAfectada;
	}

	public void setBuAfectada(String buAfectada) {
		this.buAfectada = buAfectada;
	}

	public String getCausa() {
		return this.causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public String getDetallecausa() {
		return this.detallecausa;
	}

	public void setDetallecausa(String detallecausa) {
		this.detallecausa = detallecausa;
	}

	public String getIdticket() {
		return this.idticket;
	}

	public void setIdticket(String idticket) {
		this.idticket = idticket;
	}

	public String getIncidente() {
		return this.incidente;
	}

	public void setIncidente(String incidente) {
		this.incidente = incidente;
	}

	public String getSolucion() {
		return this.solucion;
	}

	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}

	public TReporte getTReporte() {
		return this.TReporte;
	}

	public void setTReporte(TReporte TReporte) {
		this.TReporte = TReporte;
	}

}