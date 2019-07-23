package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_ser_adm database table.
 * 
 */
@Entity
@Table(name="t_ser_adm")
@NamedQuery(name="TSerAdm.findAll", query="SELECT t FROM TSerAdm t")
public class TSerAdm implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TSerAdmPK id;

	private String estado;

	private String observacion;

	@Column(name="usuario_asignado")
	private String usuarioAsignado;

	//bi-directional many-to-one association to TServicio
	@ManyToOne
	@JoinColumn(name="id_servicio", insertable=false, updatable=false)
	private TServicio TServicio;

	public TSerAdm() {
	}

	public TSerAdmPK getId() {
		return this.id;
	}

	public void setId(TSerAdmPK id) {
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

	public TServicio getTServicio() {
		return this.TServicio;
	}

	public void setTServicio(TServicio TServicio) {
		this.TServicio = TServicio;
	}

}