package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the t_cronologia database table.
 * 
 */
@Entity
@Table(name="t_cronologia")
@NamedQuery(name="TCronologia.findAll", query="SELECT t FROM TCronologia t")
public class TCronologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_CRONOLOGIA_ID_GENERATOR", sequenceName="T_CRONOLOGIA_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_CRONOLOGIA_ID_GENERATOR")
	private Long id;

	private Timestamp fecha;

	private String nombre;

	//bi-directional many-to-one association to TReporte
	@ManyToOne
	@JoinColumn(name="id_reporte")
	private TReporte TReporte;

	//bi-directional many-to-one association to THoracro
	@OneToMany(mappedBy="TCronologia")
	private List<THoracro> THoracros;

	public TCronologia() {
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

	public List<THoracro> getTHoracros() {
		return this.THoracros;
	}

	public void setTHoracros(List<THoracro> THoracros) {
		this.THoracros = THoracros;
	}

	public THoracro addTHoracro(THoracro THoracro) {
		getTHoracros().add(THoracro);
		THoracro.setTCronologia(this);

		return THoracro;
	}

	public THoracro removeTHoracro(THoracro THoracro) {
		getTHoracros().remove(THoracro);
		THoracro.setTCronologia(null);

		return THoracro;
	}

}