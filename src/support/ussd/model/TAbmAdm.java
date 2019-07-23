package support.ussd.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the t_abm_adm database table.
 * 
 */
@Entity
@Table(name="t_abm_adm")
@NamedQuery(name="TAbmAdm.findAll", query="SELECT t FROM TAbmAdm t")
public class TAbmAdm implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TAbmAdmPK id;

	private String estado;

	private String observacion;
	
	private String adjunto;
	
	private Timestamp fecha;

	@Column(name="usuario_asignado")
	private String usuarioAsignado;

	//bi-directional many-to-one association to TAbm
	@ManyToOne
	@JoinColumn(name="id_abm", insertable=false, updatable=false)
	private TAbm TAbm;

	//uni-directional many-to-one association to TServicio
	@ManyToOne
	@JoinColumn(name="id_servicio", insertable=false, updatable=false)
	private TServicio TServicio;

	public TAbmAdm() {
	}

	public TAbmAdmPK getId() {
		return this.id;
	}

	public void setId(TAbmAdmPK id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getUsuarioAsignado() {
		return this.usuarioAsignado;
	}

	public void setUsuarioAsignado(String usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public TAbm getTAbm() {
		return this.TAbm;
	}

	public void setTAbm(TAbm TAbm) {
		this.TAbm = TAbm;
	}

	public TServicio getTServicio() {
		return this.TServicio;
	}

	public void setTServicio(TServicio TServicio) {
		this.TServicio = TServicio;
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

}