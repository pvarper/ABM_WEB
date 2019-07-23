package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the t_top database table.
 * 
 */
@Entity
@Table(name="t_top")
@NamedQuery(name="TTop.findAll", query="SELECT t FROM TTop t")
public class TTop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_TOP_ID_GENERATOR", sequenceName="T_TOP_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_TOP_ID_GENERATOR")
	private Long id;

	private String cpu;

	private String descripcion;

	private Boolean estado;

	private Timestamp fecha;

	private String memoria;

	private String servidor;

	private String swap;
	
	private String administrador;

	//bi-directional many-to-one association to TProceso
	@OneToMany(mappedBy="TTop")
	private List<TProceso> TProcesos;

	public TTop() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpu() {
		return this.cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
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

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getMemoria() {
		return this.memoria;
	}

	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public String getServidor() {
		return this.servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getSwap() {
		return this.swap;
	}

	public void setSwap(String swap) {
		this.swap = swap;
	}

	public List<TProceso> getTProcesos() {
		return this.TProcesos;
	}

	public void setTProcesos(List<TProceso> TProcesos) {
		this.TProcesos = TProcesos;
	}

	public TProceso addTProceso(TProceso TProceso) {
		getTProcesos().add(TProceso);
		TProceso.setTTop(this);

		return TProceso;
	}

	public TProceso removeTProceso(TProceso TProceso) {
		getTProcesos().remove(TProceso);
		TProceso.setTTop(null);

		return TProceso;
	}

	public String getAdministrador() {
		return administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	
	
	
}