package support.ussd.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the t_abm_adjuntos database table.
 * 
 */
@Entity
@Table(name="t_abm_adjuntos")
@NamedQuery(name="TAbmAdjunto.findAll", query="SELECT t FROM TAbmAdjunto t")
public class TAbmAdjunto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_ABM_ADJUNTOS_IDADJUNTO_GENERATOR", sequenceName="T_ABM_ADJUNTO_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_ABM_ADJUNTOS_IDADJUNTO_GENERATOR")
	@Column(name="id_adjunto")
	private Long idAdjunto;

	private String descripcion;

	private Boolean estado;

	@Column(name="id_abm")
	private Long idAbm;

	private String nombre;

	private String ruta;
	
	@ManyToOne
	@JoinColumn(name = "id_abm",insertable=false, updatable=false)
	private TAbm TAbm;

	public TAbmAdjunto() {
	}

	public Long getIdAdjunto() {
		return this.idAdjunto;
	}

	public void setIdAdjunto(Long idAdjunto) {
		this.idAdjunto = idAdjunto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getIdAbm() {
		return this.idAbm;
	}

	public void setIdAbm(Long idAbm) {
		this.idAbm = idAbm;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public TAbm getTAbm() {
		return TAbm;
	}

	public void setTAbm(TAbm tAbm) {
		TAbm = tAbm;
	}

}