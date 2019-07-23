package support.ussd.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the t_reporte database table.
 * 
 */
@Entity
@Table(name="t_reporte")
@NamedQuery(name="TReporte.findAll", query="SELECT t FROM TReporte t")
public class TReporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_REPORTE_ID_GENERATOR", sequenceName="T_REPORTE_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_REPORTE_ID_GENERATOR")
	private Long id;

	private Boolean estado;

	private Timestamp fecha;

	@Column(name="fecha_fin_incidencia")
	private Timestamp fechaFinIncidencia;

	@Column(name="fecha_in_incidencia")
	private Timestamp fechaInIncidencia;

	private String nombre;

	private String reporta;
		
	//bi-directional many-to-one association to TAntecedente
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TAntecedente> TAntecedentes;

	//bi-directional many-to-one association to TAppinvolucrada
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TAppinvolucrada> TAppinvolucradas;

	//bi-directional many-to-one association to TCronologia
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TCronologia> TCronologias;

	//bi-directional many-to-one association to TParticipante
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TParticipante> TParticipantes;

	//bi-directional many-to-one association to TPlanaccion
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TPlanaccion> TPlanaccions;

	//bi-directional many-to-one association to TPuntosmejora
	@OneToMany(mappedBy="TReporte", cascade=CascadeType.ALL,orphanRemoval=true)
	private List<TPuntosmejora> TPuntosmejoras;

	public TReporte() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Timestamp getFechaFinIncidencia() {
		return this.fechaFinIncidencia;
	}

	public void setFechaFinIncidencia(Timestamp fechaFinIncidencia) {
		this.fechaFinIncidencia = fechaFinIncidencia;
	}

	public Timestamp getFechaInIncidencia() {
		return this.fechaInIncidencia;
	}

	public void setFechaInIncidencia(Timestamp fechaInIncidencia) {
		this.fechaInIncidencia = fechaInIncidencia;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getReporta() {
		return this.reporta;
	}

	public void setReporta(String reporta) {
		this.reporta = reporta;
	}

	public List<TAntecedente> getTAntecedentes() {
		return this.TAntecedentes;
	}

	public void setTAntecedentes(List<TAntecedente> TAntecedentes) {
		this.TAntecedentes = TAntecedentes;
	}

	public TAntecedente addTAntecedente(TAntecedente TAntecedente) {
		getTAntecedentes().add(TAntecedente);
		TAntecedente.setTReporte(this);

		return TAntecedente;
	}

	public TAntecedente removeTAntecedente(TAntecedente TAntecedente) {
		getTAntecedentes().remove(TAntecedente);
		TAntecedente.setTReporte(null);

		return TAntecedente;
	}

	public List<TAppinvolucrada> getTAppinvolucradas() {
		return this.TAppinvolucradas;
	}

	public void setTAppinvolucradas(List<TAppinvolucrada> TAppinvolucradas) {
		this.TAppinvolucradas = TAppinvolucradas;
	}

	public TAppinvolucrada addTAppinvolucrada(TAppinvolucrada TAppinvolucrada) {
		getTAppinvolucradas().add(TAppinvolucrada);
		TAppinvolucrada.setTReporte(this);

		return TAppinvolucrada;
	}

	public TAppinvolucrada removeTAppinvolucrada(TAppinvolucrada TAppinvolucrada) {
		getTAppinvolucradas().remove(TAppinvolucrada);
		TAppinvolucrada.setTReporte(null);

		return TAppinvolucrada;
	}

	public List<TCronologia> getTCronologias() {
		return this.TCronologias;
	}

	public void setTCronologias(List<TCronologia> TCronologias) {
		this.TCronologias = TCronologias;
	}

	public TCronologia addTCronologia(TCronologia TCronologia) {
		getTCronologias().add(TCronologia);
		TCronologia.setTReporte(this);

		return TCronologia;
	}

	public TCronologia removeTCronologia(TCronologia TCronologia) {
		getTCronologias().remove(TCronologia);
		TCronologia.setTReporte(null);

		return TCronologia;
	}

	public List<TParticipante> getTParticipantes() {
		return this.TParticipantes;
	}

	public void setTParticipantes(List<TParticipante> TParticipantes) {
		this.TParticipantes = TParticipantes;
	}

	public TParticipante addTParticipante(TParticipante TParticipante) {
		getTParticipantes().add(TParticipante);
		TParticipante.setTReporte(this);

		return TParticipante;
	}

	public TParticipante removeTParticipante(TParticipante TParticipante) {
		getTParticipantes().remove(TParticipante);
		TParticipante.setTReporte(null);

		return TParticipante;
	}

	public List<TPlanaccion> getTPlanaccions() {
		return this.TPlanaccions;
	}

	public void setTPlanaccions(List<TPlanaccion> TPlanaccions) {
		this.TPlanaccions = TPlanaccions;
	}

	public TPlanaccion addTPlanaccion(TPlanaccion TPlanaccion) {
		getTPlanaccions().add(TPlanaccion);
		TPlanaccion.setTReporte(this);

		return TPlanaccion;
	}

	public TPlanaccion removeTPlanaccion(TPlanaccion TPlanaccion) {
		getTPlanaccions().remove(TPlanaccion);
		TPlanaccion.setTReporte(null);

		return TPlanaccion;
	}

	public List<TPuntosmejora> getTPuntosmejoras() {
		return this.TPuntosmejoras;
	}

	public void setTPuntosmejoras(List<TPuntosmejora> TPuntosmejoras) {
		this.TPuntosmejoras = TPuntosmejoras;
	}

	public TPuntosmejora addTPuntosmejora(TPuntosmejora TPuntosmejora) {
		getTPuntosmejoras().add(TPuntosmejora);
		TPuntosmejora.setTReporte(this);

		return TPuntosmejora;
	}

	public TPuntosmejora removeTPuntosmejora(TPuntosmejora TPuntosmejora) {
		getTPuntosmejoras().remove(TPuntosmejora);
		TPuntosmejora.setTReporte(null);

		return TPuntosmejora;
	}

}