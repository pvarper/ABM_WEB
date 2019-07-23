package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_appinvolucradas database table.
 * 
 */
@Entity
@Table(name="t_appinvolucradas")
@NamedQuery(name="TAppinvolucrada.findAll", query="SELECT t FROM TAppinvolucrada t")
public class TAppinvolucrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_APPINVOLUCRADAS_ID_GENERATOR", sequenceName="T_APPINVOLUCRADAS_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_APPINVOLUCRADAS_ID_GENERATOR")
	private Long id;

	private String administrador;

	private String nombre;

	private String subarea;

	//bi-directional many-to-one association to TReporte
	@ManyToOne(cascade = {CascadeType.ALL},fetch= FetchType.EAGER)
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	public TAppinvolucrada() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdministrador() {
		return this.administrador;
	}

	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSubarea() {
		return this.subarea;
	}

	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}

	public TReporte getTReporte() {
		return this.TReporte;
	}

	public void setTReporte(TReporte TReporte) {
		this.TReporte = TReporte;
	}

}