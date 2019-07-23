package support.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MuRolFormularioPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Column(name="FORMULARIO_ID", insertable=false, updatable=false)
  private Integer formularioId;
  @Column(name="ROL_ID", insertable=false, updatable=false)
  private Integer rolId;
  
  public MuRolFormularioPK() {}
  
  public MuRolFormularioPK(Integer formularioId, Integer rolId)
  {
    this.formularioId = formularioId;
    this.rolId = rolId;
  }
  
  public long getFormularioId()
  {
    return this.formularioId.intValue();
  }
  
  public void setFormularioId(Integer formularioId)
  {
    this.formularioId = formularioId;
  }
  
  public long getRolId()
  {
    return this.rolId.intValue();
  }
  
  public void setRolId(Integer rolId)
  {
    this.rolId = rolId;
  }
  
  public int hashCode()
  {
    //int prime = 31;
    int result = 1;
    result = 31 * result + (
      this.formularioId == null ? 0 : this.formularioId.hashCode());
    result = 31 * result + (this.rolId == null ? 0 : this.rolId.hashCode());
    return result;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MuRolFormularioPK other = (MuRolFormularioPK)obj;
    if (this.formularioId == null)
    {
      if (other.formularioId != null) {
        return false;
      }
    }
    else if (!this.formularioId.equals(other.formularioId)) {
      return false;
    }
    if (this.rolId == null)
    {
      if (other.rolId != null) {
        return false;
      }
    }
    else if (!this.rolId.equals(other.rolId)) {
      return false;
    }
    return true;
  }
}
