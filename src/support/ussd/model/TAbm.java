package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the t_abm database table.
 * 
 */
@Entity
@Table(name="t_abm")
@NamedQuery(name="TAbm.findAll", query="SELECT t FROM TAbm t")
public class TAbm implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name="T_ABM_ID_GENERATOR", sequenceName="T_ABM_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_ABM_ID_GENERATOR")
	private Long id;

	private Integer abm;

	private String adjunto;

	private String areasucursal;

	private String cargo;

	private String ehumano;

	private Boolean estado;

	@Column(name="estado_proceso")
	private String estadoProceso;

	private Integer faltantes;

	private String fechafin;

	@Column(name="observacion_abm")
	private String observacionAbm;

	@Column(name="observacion_qflow")
	private String observacionQflow;

	private String solicitante;

	private String usuario;
	
	transient
	private String color;
	
	private Timestamp fechaenvio;
	
	private Timestamp fechafinalizado;

	//bi-directional many-to-one association to TAbmAdm
	@OneToMany(mappedBy="TAbm", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TAbmAdm> TAbmAdms;
	
	//bi-directional many-to-one association to TAbmAdm
	@OneToMany(mappedBy="TAbm", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TAbmAdjunto> TAbmAdjuntos;
	

	//bi-directional many-to-one association to TAbmSer
	@OneToMany(mappedBy="TAbm", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TAbmSer> TAbmSers;
	public TAbm() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAbm() {
		return this.abm;
	}

	public void setAbm(Integer abm) {
		this.abm = abm;
	}

	public String getAdjunto() {
		return this.adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String getAreasucursal() {
		return this.areasucursal;
	}

	public void setAreasucursal(String areasucursal) {
		this.areasucursal = areasucursal;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEhumano() {
		return this.ehumano;
	}

	public void setEhumano(String ehumano) {
		this.ehumano = ehumano;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getEstadoProceso() {
		return this.estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public Integer getFaltantes() {
		return this.faltantes;
	}

	public void setFaltantes(Integer faltantes) {
		this.faltantes = faltantes;
	}

	public String getFechafin() {
		return this.fechafin;
	}

	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public String getObservacionAbm() {
		return this.observacionAbm;
	}

	public void setObservacionAbm(String observacionAbm) {
		this.observacionAbm = observacionAbm;
	}

	public String getObservacionQflow() {
		return this.observacionQflow;
	}

	public void setObservacionQflow(String observacionQflow) {
		this.observacionQflow = observacionQflow;
	}

	public String getSolicitante() {
		return this.solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}


	public Timestamp getFechaenvio() {
		return fechaenvio;
	}

	public void setFechaenvio(Timestamp fechaenvio) {
		this.fechaenvio = fechaenvio;
	}

	public Timestamp getFechafinalizado() {
		return fechafinalizado;
	}

	public void setFechafinalizado(Timestamp fechafinalizado) {
		this.fechafinalizado = fechafinalizado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<TAbmAdm> getTAbmAdms() {
		return this.TAbmAdms;
	}

	public void setTAbmAdms(List<TAbmAdm> TAbmAdms) {
		this.TAbmAdms = TAbmAdms;
	}

	public TAbmAdm addTAbmAdm(TAbmAdm TAbmAdm) {
		getTAbmAdms().add(TAbmAdm);
		TAbmAdm.setTAbm(this);

		return TAbmAdm;
	}

	public TAbmAdm removeTAbmAdm(TAbmAdm TAbmAdm) {
		getTAbmAdms().remove(TAbmAdm);
		TAbmAdm.setTAbm(null);

		return TAbmAdm;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<TAbmSer> getTAbmSers() {
		return TAbmSers;
	}

	public void setTAbmSers(List<TAbmSer> tAbmSers) {
		TAbmSers = tAbmSers;
	}

	public List<TAbmAdjunto> getTAbmAdjuntos() {
		return TAbmAdjuntos;
	}

	public void setTAbmAdjuntos(List<TAbmAdjunto> tAbmAdjuntos) {
		TAbmAdjuntos = tAbmAdjuntos;
	}
	
}