package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the t_horaan database table.
 * 
 */
@Entity
@Table(name="t_horaan")
@NamedQuery(name="THoraan.findAll", query="SELECT t FROM THoraan t")
public class THoraan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_HORAAN_ID_GENERATOR", sequenceName="T_HORAAN_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_HORAAN_ID_GENERATOR")
	private Long id;

	private Time hora;

	private String nombre;

	//bi-directional many-to-one association to TAntecedente
	@ManyToOne
	@JoinColumn(name="id_ante")
	private TAntecedente TAntecedente;

	//bi-directional many-to-one association to THoraanimg
	@OneToMany(mappedBy="THoraan")
	private List<THoraanimg> THoraanimgs;

	public THoraan() {
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

	public TAntecedente getTAntecedente() {
		return this.TAntecedente;
	}

	public void setTAntecedente(TAntecedente TAntecedente) {
		this.TAntecedente = TAntecedente;
	}

	public List<THoraanimg> getTHoraanimgs() {
		return this.THoraanimgs;
	}

	public void setTHoraanimgs(List<THoraanimg> THoraanimgs) {
		this.THoraanimgs = THoraanimgs;
	}

	public THoraanimg addTHoraanimg(THoraanimg THoraanimg) {
		getTHoraanimgs().add(THoraanimg);
		THoraanimg.setTHoraan(this);

		return THoraanimg;
	}

	public THoraanimg removeTHoraanimg(THoraanimg THoraanimg) {
		getTHoraanimgs().remove(THoraanimg);
		THoraanimg.setTHoraan(null);

		return THoraanimg;
	}

}