package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_tipo_documento database table.
 * 
 */
@Entity
@Table(name="t_tipo_documento")
@NamedQuery(name="TTipoDocumento.findAll", query="SELECT t FROM TTipoDocumento t")
public class TTipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_TIPO_DOCUMENTO_ID_GENERATOR", sequenceName="T_TIPO_DOCUMENTO_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_TIPO_DOCUMENTO_ID_GENERATOR")
	private Long id;

	private String descripcion;

	private Boolean estado;

	private String nombre;

	public TTipoDocumento() {
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

}