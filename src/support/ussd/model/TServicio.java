package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_servicios database table.
 * 
 */
@Entity
@Table(name="t_servicios")
@NamedQuery(name="TServicio.findAll", query="SELECT t FROM TServicio t")
public class TServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_SERVICIOS_ID_GENERATOR", sequenceName="T_SERVICIOS_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_SERVICIOS_ID_GENERATOR")
	private Long id;

	private String descripcion;

	private Boolean estado;

	@Column(name="id_servicio")
	private Long idServicio;

	private String observacion;

	private String pwd;

	@Column(name="tipo_servicio")
	private String tipoServicio;

	@Column(name="tipo_usuario")
	private String tipoUsuario;

	//bi-directional many-to-one association to TSerAdm
	@OneToMany(mappedBy="TServicio",cascade=CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
	private List<TSerAdm> TSerAdms;

	public TServicio() {
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

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTipoServicio() {
		return this.tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public List<TSerAdm> getTSerAdms() {
		return this.TSerAdms;
	}

	public void setTSerAdms(List<TSerAdm> TSerAdms) {
		this.TSerAdms = TSerAdms;
	}

	public TSerAdm addTSerAdm(TSerAdm TSerAdm) {
		getTSerAdms().add(TSerAdm);
		TSerAdm.setTServicio(this);

		return TSerAdm;
	}

	public TSerAdm removeTSerAdm(TSerAdm TSerAdm) {
		getTSerAdms().remove(TSerAdm);
		TSerAdm.setTServicio(null);

		return TSerAdm;
	}

}