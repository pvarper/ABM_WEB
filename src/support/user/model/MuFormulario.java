package support.user.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="MU_FORMULARIO")
public class MuFormulario
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final boolean V_MODULO = true;
  public static final boolean V_PAGINA = false;
  @Id
  private Integer id;
  private boolean estado;
  @Column(name="FORMULARIO_ID")
  private Integer formularioId;
  private String nombre;
  private Integer orden;
  private String url;
  @ManyToMany(fetch=FetchType.EAGER, cascade={javax.persistence.CascadeType.REMOVE})
  @JoinTable(name="MU_FORMULARIO_ACCION", joinColumns={@javax.persistence.JoinColumn(name="FORMULARIO_ID")}, inverseJoinColumns={@javax.persistence.JoinColumn(name="ACCION_ID")})
  private List<MuAccion> muAccions;
  
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
  
  public Integer getFormularioId()
  {
    return this.formularioId;
  }
  
  public void setFormularioId(Integer formularioId)
  {
    this.formularioId = formularioId;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public Integer getOrden()
  {
    return this.orden;
  }
  
  public void setOrden(Integer orden)
  {
    this.orden = orden;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public List<MuAccion> getMuAccions()
  {
    return this.muAccions;
  }
  
  public void setMuAccions(List<MuAccion> muAccions)
  {
    this.muAccions = muAccions;
  }
  
  public String toString()
  {
    return 
    
      "MuFormulario [id=" + this.id + ", estado=" + this.estado + ", nombre=" + this.nombre + ", orden=" + this.orden + ", url=" + this.url + ", formularioId=" + this.formularioId + "]";
  }
}
