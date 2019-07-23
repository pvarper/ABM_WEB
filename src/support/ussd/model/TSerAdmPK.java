package support.ussd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the t_ser_adm database table.
 * 
 */
@Embeddable
public class TSerAdmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_servicio", insertable=false, updatable=false)
	private Long idServicio;

	@Column(name="id_adm", insertable=false, updatable=false)
	private Long idAdm;

	public TSerAdmPK() {
	}
	public Long getIdServicio() {
		return this.idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public Long getIdAdm() {
		return this.idAdm;
	}
	public void setIdAdm(Long idAdm) {
		this.idAdm = idAdm;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TSerAdmPK)) {
			return false;
		}
		TSerAdmPK castOther = (TSerAdmPK)other;
		return 
			this.idServicio.equals(castOther.idServicio)
			&& this.idAdm.equals(castOther.idAdm);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idServicio.hashCode();
		hash = hash * prime + this.idAdm.hashCode();
		
		return hash;
	}
}