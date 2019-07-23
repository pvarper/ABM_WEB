package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_horaanimg database table.
 * 
 */
@Entity
@Table(name="t_horaanimg")
@NamedQuery(name="THoraanimg.findAll", query="SELECT t FROM THoraanimg t")
public class THoraanimg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_HORAANIMG_ID_GENERATOR", sequenceName="T_HORAANIMG_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_HORAANIMG_ID_GENERATOR")
	private Long id;

	private String nombre;

	private String ruta;

	//bi-directional many-to-one association to THoraan
	@ManyToOne
	@JoinColumn(name="id_hora")
	private THoraan THoraan;

	public THoraanimg() {
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

	public THoraan getTHoraan() {
		return this.THoraan;
	}

	public void setTHoraan(THoraan THoraan) {
		this.THoraan = THoraan;
	}

}