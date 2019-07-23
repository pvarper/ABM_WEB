package support.user.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="mu_grupo_ad")
public class MuGrupoAd
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @SequenceGenerator(name="MU_GRUPO_AD_GRUPOID_GENERATOR", sequenceName="SEQ_MU_GRUPO_AD", allocationSize=1)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MU_GRUPO_AD_GRUPOID_GENERATOR")
  @Column(name="GRUPO_ID")
  private Integer grupoId;
  private String detalle;
  private boolean estado;
  private String nombre;
  @ManyToOne
  @JoinColumn(name="ROL_ID")
  private MuRol muRol;
  
  public long getGrupoId()
  {
    return this.grupoId.intValue();
  }
  
  public void setGrupoId(Integer grupoId)
  {
    this.grupoId = grupoId;
  }
  
  public String getDetalle()
  {
    return this.detalle;
  }
  
  public void setDetalle(String detalle)
  {
    this.detalle = detalle;
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
  
  public MuRol getMuRol()
  {
    return this.muRol;
  }
  
  public void setMuRol(MuRol muRol)
  {
    this.muRol = muRol;
  }
}
