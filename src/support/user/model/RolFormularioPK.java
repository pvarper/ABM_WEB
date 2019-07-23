package support.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RolFormularioPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Column(name="rol_id", insertable=false, updatable=false)
  private Integer rolId;
  @Column(name="formulario_id", insertable=false, updatable=false)
  private Integer formularioId;
  
  public Integer getRolId()
  {
    return this.rolId;
  }
  
  public void setRolId(Integer rolId)
  {
    this.rolId = rolId;
  }
  
  public Integer getFormularioId()
  {
    return this.formularioId;
  }
  
  public void setFormularioId(Integer formularioId)
  {
    this.formularioId = formularioId;
  }
  
  public boolean equals(Object other)
  {
    if (this == other) {
      return true;
    }
    if (!(other instanceof RolFormularioPK)) {
      return false;
    }
    RolFormularioPK castOther = (RolFormularioPK)other;
    return 
      (this.rolId.equals(castOther.rolId)) && 
      (this.formularioId.equals(castOther.formularioId));
  }
  
  public int hashCode()
  {
    //int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.rolId.hashCode();
    hash = hash * 31 + this.formularioId.hashCode();
    
    return hash;
  }
}
