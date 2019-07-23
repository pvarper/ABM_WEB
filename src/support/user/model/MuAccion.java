package support.user.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MU_ACCION")
@NamedQuery(name="MuAccion.findAll", query="SELECT m FROM MuAccion m")
public class MuAccion
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  private Integer id;
  private boolean estado;
  private String nombre;
  @ManyToMany(mappedBy="muAccions", cascade={javax.persistence.CascadeType.ALL})
  private List<MuFormulario> muFormularios;
  
  public MuAccion() {}
  
  public MuAccion(Integer id, boolean estado, String nombre)
  {
    this.id = id;
    this.estado = estado;
    this.nombre = nombre;
  }
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
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
  
  public List<MuFormulario> getMuFormularios()
  {
    return this.muFormularios;
  }
  
  public void setMuFormularios(List<MuFormulario> muFormularios)
  {
    this.muFormularios = muFormularios;
  }
  
  public String toString()
  {
    return 
      "MuAccion [id=" + this.id + ", estado=" + this.estado + ", nombre=" + this.nombre + "]";
  }
}
