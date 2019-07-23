package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_documento database table.
 * 
 */
@Entity
@Table(name="t_documento")
@NamedQuery(name="TDocumento.findAll", query="SELECT t FROM TDocumento t")
public class TDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_DOCUMENTO_ID_GENERATOR", sequenceName="T_DOCUMENTO_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_DOCUMENTO_ID_GENERATOR")
	private Long id;

	private String clave;

	private String descripcion;

	private Boolean estado;

	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="plataforma_id")
	private TPlataforma TPlataforma;

	private String ruta;

	//uni-directional many-to-one association to TTipoDocumento
	@ManyToOne
	@JoinColumn(name="tipo_id")
	private TTipoDocumento TTipoDocumento;

	public TDocumento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

	public TPlataforma getPlataformaId() {
		return this.TPlataforma;
	}

	public void setPlataformaId(TPlataforma plataformaId) {
		this.TPlataforma = plataformaId;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public TTipoDocumento getTTipoDocumento() {
		return this.TTipoDocumento;
	}

	public void setTTipoDocumento(TTipoDocumento TTipoDocumento) {
		this.TTipoDocumento = TTipoDocumento;
	}

}