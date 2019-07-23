package support.ussd.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the t_abm_ser database table.
 * 
 */
@Entity
@Table(name = "t_abm_ser")
@NamedQuery(name = "TAbmSer.findAll", query = "SELECT t FROM TAbmSer t")
public class TAbmSer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "T_ABM_SER_ID_GENERATOR", sequenceName = "T_ABM_SER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_ABM_SER_ID_GENERATOR")
	private Long id;

	private String adjunto;

	private String estado;
	
	private String contraseña;

	private Timestamp fecha;

	// uni-directional many-to-one association to TServicio
	@ManyToOne
	@JoinColumn(name = "id_servicio")
	private TServicio TServicio;

	@Column(name = "id_usuario")
	private Long idUsuario;

	private String observacion;

	@Column(name = "usuario_asignado")
	private String usuarioAsignado;

	// bi-directional many-to-one association to TAbm
	@ManyToOne
	@JoinColumn(name = "id_abm")
	private TAbm TAbm;

	public TAbmSer() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdjunto() {
		return this.adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
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

	

	public TServicio getTServicio() {
		return TServicio;
	}

	public void setTServicio(TServicio tServicio) {
		TServicio = tServicio;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

}