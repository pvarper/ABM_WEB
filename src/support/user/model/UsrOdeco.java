package support.user.model;

public class UsrOdeco {
	private String idUsuario;
	private String login;
	private String nombre;
	private String telefono;
	private String correo;
	private String responsable_id;
	private String rol_id;
	private String estado;
	private String ciudad;
	private String especial;

	public String getEspecial() {
		return especial;
	}

	public void setEspecial(String especial) {
		this.especial = especial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRol_id() {
		return rol_id;
	}

	public void setRol_id(String rol_id) {
		this.rol_id = rol_id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getResponsable_id() {
		return responsable_id;
	}

	public void setResponsable_id(String responsable_id) {
		this.responsable_id = responsable_id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}
