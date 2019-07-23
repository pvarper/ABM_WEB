package support.user.model;

public class UpdateCuenta {

	private String id;
	private String cuenta;
	private String dato_fecha_update;
	private String dato_fecha_actual;
	private String dato_fecha_accion;
	private String usr;
	private String id_rol;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDato_fecha_accion() {
		return dato_fecha_accion;
	}

	public void setDato_fecha_accion(String dato_fecha_accion) {
		this.dato_fecha_accion = dato_fecha_accion;
	}

	public String getDato_fecha_actual() {
		return dato_fecha_actual;
	}

	public void setDato_fecha_actual(String dato_fecha_actual) {
		this.dato_fecha_actual = dato_fecha_actual;
	}

	public String getDato_fecha_update() {
		return dato_fecha_update;
	}

	public void setDato_fecha_update(String dato_fecha_update) {
		this.dato_fecha_update = dato_fecha_update;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setcuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getId_rol() {
		return id_rol;
	}

	public void setId_rol(String id_rol) {
		this.id_rol = id_rol;
	}
}
