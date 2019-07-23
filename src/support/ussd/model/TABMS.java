package support.ussd.model;

import java.io.Serializable;


public class TABMS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer abm;
	private String solicitante;
	private String usuario;
	private String ehumano;
	private String cargo;
	private String areasucursal;
	private String fechafin;
	private String observacion_abm;
	
	private String observacion_qflow;
	private String adjunto;
	private String estado_proceso;
	
	private Integer faltantes;
	private Boolean estado;
	
	public Integer getAbm() {
		return abm;
	}
	public void setAbm(Integer abm) {
		this.abm = abm;
	}
	public String getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEhumano() {
		return ehumano;
	}
	public void setEhumano(String ehumano) {
		this.ehumano = ehumano;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getAreasucursal() {
		return areasucursal;
	}
	public void setAreasucursal(String areasucursal) {
		this.areasucursal = areasucursal;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
//	public String getObservaciones() {
//		return observacion_abm;
//	}
//	public void setObservaciones(String observaciones) {
//		this.observacion_abm = observaciones;
//	}
	
	
	public String getObservacion_abm() {
		return observacion_abm;
	}
	public void setObservacion_abm(String observacion_abm) {
		this.observacion_abm = observacion_abm;
	}
	public String getObservacion_qflow() {
		return observacion_qflow;
	}
	public void setObservacion_qflow(String observacion_qflow) {
		this.observacion_qflow = observacion_qflow;
	}
	public String getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}
	public String getEstado_proceso() {
		return estado_proceso;
	}
	public void setEstado_proceso(String estado_proceso) {
		this.estado_proceso = estado_proceso;
	}
	public Boolean getEstado() {
		return estado;
	}
	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
		
	
	public Integer getFaltantes() {
		return faltantes;
	}
	public void setFaltantes(Integer faltantes) {
		this.faltantes = faltantes;
	}
	@Override
	public String toString() {
		return "TABM [abm=" + abm + ", solicitante=" + solicitante
				+ ", usuario=" + usuario + ", ehumano=" + ehumano + ", cargo="
				+ cargo + ", areasucursal=" + areasucursal + ", fechafin="
				+ fechafin + ", observaciones=" + observacion_abm + "]";
	}
	
	
}
