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
 * The persistent class for the t_dh database table.
 * 
 */
@Entity
@Table(name="t_dh")
@NamedQuery(name="TDh.findAll", query="SELECT t FROM TDh t")
public class TDh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_DH_ID_GENERATOR", sequenceName="T_DH_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_DH_ID_GENERATOR")
	private Long id;

	private String avail;

	private Boolean estado;

	private String filesystem;

	private String mountedon;


	private String size;

	private String type;

	private String use;

	private String used;
	
	//bi-directional many-to-one association to TTop
		@ManyToOne
		@JoinColumn(name="id_top")
		private TTop TTop;

	public TDh() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvail() {
		return this.avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}



	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	

	public String getFilesystem() {
		return this.filesystem;
	}

	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}

	public String getMountedon() {
		return this.mountedon;
	}

	public void setMountedon(String mountedon) {
		this.mountedon = mountedon;
	}

	

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUse() {
		return this.use;
	}

	public void setUse(String use) {
		this.use = use;
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

}