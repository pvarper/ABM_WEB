package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_anita_usuarios database table.
 * 
 */
@Entity
@Table(name="t_anita_usuarios")
@NamedQuery(name="TAnitaUsuario.findAll", query="SELECT t FROM TAnitaUsuario t")
public class TAnitaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="T_ANITA_USUARIOS_IDUSUARIO_GENERATOR", sequenceName="SEQ_T_ANITA_USUARIOS",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_ANITA_USUARIOS_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario")
	private Long idUsuario;

	private String cargo;

	private String ehumano;

	private Boolean estado;

	private String nombres;

	private String numeroci;

	private String regional;

	private String telefono;

	@Column(name="usr_login")
	private String usrLogin;

	public TAnitaUsuario() {
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEhumano() {
		return this.ehumano;
	}

	public void setEhumano(String ehumano) {
		this.ehumano = ehumano;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNumeroci() {
		return this.numeroci;
	}

	public void setNumeroci(String numeroci) {
		this.numeroci = numeroci;
	}

	public String getRegional() {
		return this.regional;
	}

	public void setRegional(String regional) {
		this.regional = regional;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsrLogin() {
		return this.usrLogin;
	}

	public void setUsrLogin(String usrLogin) {
		this.usrLogin = usrLogin;
	}

}