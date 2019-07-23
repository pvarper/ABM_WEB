package support.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MU_ROL")
public class MuRol
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @SequenceGenerator(name="MU_ROL_ROLID_GENERATOR", sequenceName="SEQ_MU_ROL", allocationSize=1)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MU_ROL_ROLID_GENERATOR")
  @Column(name="ROL_ID")
  private Integer rolId;
  private String descripcion;
  private boolean estado;
  private String nombre;
  
  public Integer getRolId()
  {
    return this.rolId;
  }
  
  public void setRolId(Integer rolId)
  {
    this.rolId = rolId;
  }
  
  public String getDescripcion()
  {
    return this.descripcion;
  }
  
  public void setDescripcion(String descripcion)
  {
    this.descripcion = descripcion;
  }
  
  public boolean getEstado()
  {
    return this.estado;
  }
  
  public void setEstado(boolean estado)
  {
    this.estado = estado;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
}
