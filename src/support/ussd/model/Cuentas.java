package support.ussd.model;

public class Cuentas {
	
	private String cuenta;
	private String estado;
	private String modalidad;
	private String localidad;
	
	public Cuentas(){
		
	}
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public String toString() {
		return "Cuentas [cuenta=" + cuenta + ", estado=" + estado
				+ ", modalidad=" + modalidad + ", localidad=" + localidad + "]";
	}
	
	

}
