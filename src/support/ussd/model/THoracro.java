package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the t_horacro database table.
 * 
 */
@Entity
@Table(name="t_horacro")
@NamedQuery(name="THoracro.findAll", query="SELECT t FROM THoracro t")
public class THoracro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_HORACRO_ID_GENERATOR", sequenceName="T_HORACRO_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_HORACRO_ID_GENERATOR")
	private Long id;

	private Time hora;

	private String nombre;

	//bi-directional many-to-one association to TCronologia
	@ManyToOne
	@JoinColumn(name="id_cro")
	private TCronologia TCronologia;

	//bi-directional many-to-one association to THoracroimg
	@OneToMany(mappedBy="THoracro")
	private List<THoracroimg> THoracroimgs;

	public THoracro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getHora() {
		return this.hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TCronologia getTCronologia() {
		return this.TCronologia;
	}

	public void setTCronologia(TCronologia TCronologia) {
		this.TCronologia = TCronologia;
	}

	public List<THoracroimg> getTHoracroimgs() {
		return this.THoracroimgs;
	}

	public void setTHoracroimgs(List<THoracroimg> THoracroimgs) {
		this.THoracroimgs = THoracroimgs;
	}

	public THoracroimg addTHoracroimg(THoracroimg THoracroimg) {
		getTHoracroimgs().add(THoracroimg);
		THoracroimg.setTHoracro(this);

		return THoracroimg;
	}

	public THoracroimg removeTHoracroimg(THoracroimg THoracroimg) {
		getTHoracroimgs().remove(THoracroimg);
		THoracroimg.setTHoracro(null);

		return THoracroimg;
	}

}