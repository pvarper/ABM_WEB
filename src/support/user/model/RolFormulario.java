package support.user.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="mu_rol_formulario")
@NamedQuery(name="RolFormulario.findAll", query="SELECT r FROM RolFormulario r")
public class RolFormulario
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @EmbeddedId
  private RolFormularioPK id;
  private Boolean estado;
  @ManyToOne
  @JoinColumn(name="formulario_id", nullable=false, insertable=false, updatable=false)
  private MuFormulario formulario;
  @ManyToOne
  @JoinColumn(name="rol_id", nullable=false, insertable=false, updatable=false)
  private MuRol rol;
  
  public RolFormularioPK getId()
  {
    return this.id;
  }
  
  public void setId(RolFormularioPK id)
  {
    this.id = id;
  }
  
  public Boolean getEstado()
  {
    return this.estado;
  }
  
  public void setEstado(Boolean estado)
  {
    this.estado = estado;
  }
  
  public MuFormulario getFormulario()
  {
    return this.formulario;
  }
  
  public void setFormulario(MuFormulario formulario)
  {
    this.formulario = formulario;
  }
  
  public MuRol getRol()
  {
    return this.rol;
  }
  
  public void setRol(MuRol rol)
  {
    this.rol = rol;
  }
}
