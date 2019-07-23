package support.ussd.model;

import java.io.Serializable;

import javax.persistence.*;

import support.user.model.MuUsuario;


/**
 * The persistent class for the t_plataforma_administrador database table.
 * 
 */
@Entity
@Table(name="t_plataforma_administrador")
@NamedQuery(name="TPlataformaAdministrador.findAll", query="SELECT t FROM TPlataformaAdministrador t")
public class TPlataformaAdministrador implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TPlataformaAdministradorPK id;
	
	@ManyToOne
    @MapsId("plataformaId")
    @JoinColumn(name = "plataforma_id", insertable=false, updatable=false)
	private TPlataforma plataformaId;
 
    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", insertable=false, updatable=false)
    private MuUsuario usuarioId;

	private String descripcion;

	public TPlataformaAdministrador() {
	}

	public TPlataformaAdministradorPK getId() {
		return this.id;
	}

	public void setId(TPlataformaAdministradorPK id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TPlataforma getPlataformaId() {
		return plataformaId;
	}

	public void setPlataformaId(TPlataforma plataformaId) {
		this.plataformaId = plataformaId;
	}

	public MuUsuario getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(MuUsuario usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}