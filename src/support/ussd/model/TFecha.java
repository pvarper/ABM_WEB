package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the t_fecha database table.
 * 
 */
@Entity
@Table(name="t_fecha")
@NamedQuery(name="TFecha.findAll", query="SELECT t FROM TFecha t")
public class TFecha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_FECHA_ID_GENERATOR", sequenceName="T_FECHA_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_FECHA_ID_GENERATOR")
	private Long id;

	private String comprobante;

	private String estado;

	private Timestamp fecha;

	@Column(name="\"fechaAnterior\"")
	private String fechaAnterior;

	private String numero;

	private String serie;

	public TFecha() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getFechaAnterior() {
		return this.fechaAnterior;
	}

	public void setFechaAnterior(String fechaAnterior) {
		this.fechaAnterior = fechaAnterior;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

}