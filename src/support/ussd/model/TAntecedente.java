package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the t_antecedentes database table.
 * 
 */
@Entity
@Table(name="t_antecedentes")
@NamedQuery(name="TAntecedente.findAll", query="SELECT t FROM TAntecedente t")
public class TAntecedente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_ANTECEDENTES_ID_GENERATOR", sequenceName="T_ANTECEDENTES_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_ANTECEDENTES_ID_GENERATOR")
	private Long id;

	private Timestamp fecha;

	private String nombre;

	//bi-directional many-to-one association to TReporte
	@ManyToOne
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	//bi-directional many-to-one association to THoraan
	@OneToMany(mappedBy="TAntecedente")
	private List<THoraan> THoraans;

	public TAntecedente() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TReporte getTReporte() {
		return this.TReporte;
	}

	public void setTReporte(TReporte TReporte) {
		this.TReporte = TReporte;
	}

	public List<THoraan> getTHoraans() {
		return this.THoraans;
	}

	public void setTHoraans(List<THoraan> THoraans) {
		this.THoraans = THoraans;
	}

	public THoraan addTHoraan(THoraan THoraan) {
		getTHoraans().add(THoraan);
		THoraan.setTAntecedente(this);

		return THoraan;
	}

	public THoraan removeTHoraan(THoraan THoraan) {
		getTHoraans().remove(THoraan);
		THoraan.setTAntecedente(null);

		return THoraan;
	}

}