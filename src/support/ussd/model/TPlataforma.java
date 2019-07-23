package support.ussd.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the t_plataforma database table.
 * 
 */
@Entity
@Table(name="t_plataforma")
@NamedQuery(name="TPlataforma.findAll", query="SELECT t FROM TPlataforma t")
public class TPlataforma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_PLATAFORMA_ID_GENERATOR", sequenceName="T_PLATAFORMA_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_PLATAFORMA_ID_GENERATOR")
	private Long id;

	private String descripcion;

	private Boolean estado;

	private String nombre;
	
	private String uso;
	
	//uni-directional many-to-one association to MuRol
		@ManyToOne
		@JoinColumn(name="area_id")
		private TArea plaArea;

//	//bi-directional many-to-one association to TAtributo
//	@OneToMany(mappedBy="TPlataforma", cascade=CascadeType.ALL,orphanRemoval=true)
//	private List<TAtributo> TAtributos;
//
	//bi-directional many-to-one association to TRol
//	@OneToMany(mappedBy="TPlataforma", cascade=CascadeType.ALL,orphanRemoval=true)
//	private List<TRol> TRols;
		

	@OneToMany(mappedBy = "plataformaId", cascade=CascadeType.ALL,orphanRemoval=true,fetch = FetchType.EAGER)
	private List<TPlataformaAdministrador> plataforma;

	public TPlataforma() {
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public TArea getPlaArea() {
		return plaArea;
	}

	public void setPlaArea(TArea plaArea) {
		this.plaArea = plaArea;
	}

	public List<TPlataformaAdministrador> getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(List<TPlataformaAdministrador> plataforma) {
		this.plataforma = plataforma;
	}

	
	

//	public List<TAtributo> getTAtributos() {
//		return this.TAtributos;
//	}
//
//	public void setTAtributos(List<TAtributo> TAtributos) {
//		this.TAtributos = TAtributos;
//	}

//	public TAtributo addTAtributo(TAtributo TAtributo) {
//		getTAtributos().add(TAtributo);
//		TAtributo.setTPlataforma(this);
//
//		return TAtributo;
//	}
//
//	public TAtributo removeTAtributo(TAtributo TAtributo) {
//		getTAtributos().remove(TAtributo);
//		TAtributo.setTPlataforma(null);
//
//		return TAtributo;
//	}
//
//	public List<TRol> getTRols() {
//		return this.TRols;
//	}
//
//	public void setTRols(List<TRol> TRols) {
//		this.TRols = TRols;
//	}

//	public TRol addTRol(TRol TRol) {
//		getTRols().add(TRol);
//		TRol.setTPlataforma(this);
//
//		return TRol;
//	}
//
//	public TRol removeTRol(TRol TRol) {
//		getTRols().remove(TRol);
//		TRol.setTPlataforma(null);
//
//		return TRol;
//	}

}