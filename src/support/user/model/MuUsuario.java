package support.user.model;

import java.io.Serializable;

import javax.persistence.*;

import support.ussd.model.TPlataformaAdministrador;

import java.util.List;
import java.util.Set;


/**
 * The persistent class for the mu_usuario database table.
 * 
 */
@Entity
@Table(name="mu_usuario")
@NamedQuery(name="MuUsuario.findAll", query="SELECT m FROM MuUsuario m")
public class MuUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MU_USUARIO_USUARIOID_GENERATOR", sequenceName="SEQ_MU_USUARIO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MU_USUARIO_USUARIOID_GENERATOR")
	@Column(name="usuario_id")
	private Long usuarioId;

	private String correo;

	private String ehumano;

	private Boolean estado;

	private String login;

	private String nombre;

	private String telefono;

	//uni-directional many-to-one association to MuRol
	@ManyToOne
	@JoinColumn(name="rol_id")
	private MuRol muRol;

	//bi-directional many-to-one association to MuUsuario
	@ManyToOne
	@JoinColumn(name="id_supervisor")
	private MuUsuario muUsuario;

	//bi-directional many-to-one association to MuUsuario
	@OneToMany(mappedBy="muUsuario")
	private List<MuUsuario> muUsuarios;
	
	@OneToMany(mappedBy = "usuarioId")
    private List<TPlataformaAdministrador> plataformasAdmin;

	public MuUsuario() {
	}

	public Long getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public MuRol getMuRol() {
		return this.muRol;
	}

	public void setMuRol(MuRol muRol) {
		this.muRol = muRol;
	}

	public MuUsuario getMuUsuario() {
		return this.muUsuario;
	}

	public void setMuUsuario(MuUsuario muUsuario) {
		this.muUsuario = muUsuario;
	}

	public List<MuUsuario> getMuUsuarios() {
		return this.muUsuarios;
	}

	public void setMuUsuarios(List<MuUsuario> muUsuarios) {
		this.muUsuarios = muUsuarios;
	}

	public MuUsuario addMuUsuario(MuUsuario muUsuario) {
		getMuUsuarios().add(muUsuario);
		muUsuario.setMuUsuario(this);

		return muUsuario;
	}

	public MuUsuario removeMuUsuario(MuUsuario muUsuario) {
		getMuUsuarios().remove(muUsuario);
		muUsuario.setMuUsuario(null);

		return muUsuario;
	}
	
	public List<TPlataformaAdministrador> getPlataformasAdmin() {
		return plataformasAdmin;
	}

	public void setPlataformasAdmin(List<TPlataformaAdministrador> plataformasAdmin) {
		this.plataformasAdmin = plataformasAdmin;
	}
	
//	public Set<TPlataformaAdministrador> getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Set<TPlataformaAdministrador> usuario) {
//		this.usuario = usuario;
//	}

}