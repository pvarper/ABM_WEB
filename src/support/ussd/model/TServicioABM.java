package support.ussd.model;

import java.io.Serializable;

public class TServicioABM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer CodServicio;
	private String Descripcion;
	private String TipoServicio;
	private String Estado;
	
	public Integer getCodServicio() {
		return CodServicio;
	}
	public void setCodServicio(Integer codServicio) {
		CodServicio = codServicio;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getTipoServicio() {
		return TipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		TipoServicio = tipoServicio;
	}
	
	
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	@Override
	public String toString() {
		return "TServicio [CodServicio=" + CodServicio + ", Descripcion="
				+ Descripcion + ", TipoServicio=" + TipoServicio + "]";
	}
	
	

}
