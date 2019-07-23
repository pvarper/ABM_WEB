package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_abm_adm database table.
 * 
 */
@Embeddable
public class TAbmAdmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_abm", insertable=false, updatable=false)
	private Long idAbm;

	@Column(name="id_servicio", insertable=false, updatable=false)
	private Long idServicio;

	public TAbmAdmPK() {
	}
	public Long getIdAbm() {
		return this.idAbm;
	}
	public void setIdAbm(Long idAbm) {
		this.idAbm = idAbm;
	}
	public Long getIdServicio() {
		return this.idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TAbmAdmPK)) {
			return false;
		}
		TAbmAdmPK castOther = (TAbmAdmPK)other;
		return 
			this.idAbm.equals(castOther.idAbm)
			&& this.idServicio.equals(castOther.idServicio);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAbm.hashCode();
		hash = hash * prime + this.idServicio.hashCode();
		
		return hash;
	}
}