package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_horacroimg database table.
 * 
 */
@Entity
@Table(name="t_horacroimg")
@NamedQuery(name="THoracroimg.findAll", query="SELECT t FROM THoracroimg t")
public class THoracroimg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_HORACROIMG_ID_GENERATOR", sequenceName="T_HORACROIMG_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_HORACROIMG_ID_GENERATOR")
	private Long id;

	private String nombre;

	private String ruta;

	//bi-directional many-to-one association to THoracro
	@ManyToOne
	@JoinColumn(name="id_hora")
	private THoracro THoracro;

	public THoracroimg() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public THoracro getTHoracro() {
		return this.THoracro;
	}

	public void setTHoracro(THoracro THoracro) {
		this.THoracro = THoracro;
	}

}