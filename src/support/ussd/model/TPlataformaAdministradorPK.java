package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_plataforma_administrador database table.
 * 
 */
@Embeddable
public class TPlataformaAdministradorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="plataforma_id")
	private Long plataformaId;

	@Column(name="usuario_id")
	private Long usuarioId;

	public TPlataformaAdministradorPK() {
	}
	public Long getPlataformaId() {
		return this.plataformaId;
	}
	public void setPlataformaId(Long plataformaId) {
		this.plataformaId = plataformaId;
	}
	public Long getUsuarioId() {
		return this.usuarioId;
	}
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TPlataformaAdministradorPK)) {
			return false;
		}
		TPlataformaAdministradorPK castOther = (TPlataformaAdministradorPK)other;
		return 
			this.plataformaId.equals(castOther.plataformaId)
			&& this.usuarioId.equals(castOther.usuarioId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.plataformaId.hashCode();
		hash = hash * prime + this.usuarioId.hashCode();
		
		return hash;
	}
}