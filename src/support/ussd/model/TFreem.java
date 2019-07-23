package support.ussd.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the t_freem database table.
 * 
 */
@Entity
@Table(name="t_freem")
@NamedQuery(name="TFreem.findAll", query="SELECT t FROM TFreem t")
public class TFreem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_FREEM_ID_GENERATOR", sequenceName="T_FREEM_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_FREEM_ID_GENERATOR")
	private Long id;

	private String buffers;

	private String cached;
	private String total;


	private Boolean estado;



	private String free;

	private String nombre;



	private String shared;

	private String used;
	
	//bi-directional many-to-one association to TTop
		@ManyToOne
		@JoinColumn(name="id_top")
		private TTop TTop;

	public TFreem() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBuffers() {
		return this.buffers;
	}

	public void setBuffers(String buffers) {
		this.buffers = buffers;
	}

	public String getCached() {
		return this.cached;
	}

	public void setCached(String cached) {
		this.cached = cached;
	}


	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	

	public String getFree() {
		return this.free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public String getShared() {
		return this.shared;
	}

	public void setShared(String shared) {
		this.shared = shared;
	}

	public String getUsed() {
		return this.used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	public TTop getTTop() {
		return this.TTop;
	}

	public void setTTop(TTop TTop) {
		this.TTop = TTop;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}