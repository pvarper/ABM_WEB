package support.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import micrium.aes.AlgoritmoAES;

@Entity
@Table(name="MU_PARAMETRO")
@NamedQuery(name="Parametro.findAll", query="SELECT p FROM Parametro p")
public class Parametro
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final int TIPO_CADENA = 1;
  public static final int TIPO_FECHA = 2;
  public static final int TIPO_NUMERICO = 3;
  public static final int TIPO_BOOLEANO = 4;
  public static final int TIPO_COLOR = 5;
  @Id
  @Column(name="PARAMETRO_ID")
  private int parametroId;
  private String nombre;
  private Boolean comp;
  private int tipo;
  private String valorCadena;
  @Temporal(TemporalType.DATE)
  private Date valorFecha;
  private BigDecimal valorNumerico;
  private Boolean valorBooleano;
  @Column(name="DESCRIPCION_CAMPO")
  private String descripcionCampo;
  
  public Parametro() {}
  
  public Parametro(int parametroId, String nombre, int tipo, String valorCadena)
  {
    this.parametroId = parametroId;
    this.nombre = nombre;
    this.tipo = tipo;
    this.valorCadena = valorCadena;
  }
  
  public Integer getParametroId()
  {
    return Integer.valueOf(this.parametroId);
  }
  
  public void setParametroId(int parametroId)
  {
    this.parametroId = parametroId;
  }
  
  public String getNombre()
  {
    return this.nombre;
  }
  
  public void setNombre(String nombre)
  {
    this.nombre = nombre;
  }
  
  public int getTipo()
  {
    return this.tipo;
  }
  
  public void setTipo(int tipo)
  {
    this.tipo = tipo;
  }
  
  public String getValorCadena()
  {
    if (this.nombre.equals("LISTA_ESTADOS")) {
      return this.valorCadena;
    }
    return this.aes.desencriptar(this.valorCadena);
  }
  
  public void setValorCadena(String valorCadena)
  {
	  if (this.nombre.equals("LISTA_ESTADOS")) {
		  this.valorCadena =valorCadena;
		  return;
	    }
    this.valorCadena = this.aes.encriptar(valorCadena);
  }
  
  public Date getValorFecha()
  {
    return this.valorFecha;
  }
  
  public void setValorFecha(Date valorFecha)
  {
    this.valorFecha = valorFecha;
  }
  
  public BigDecimal getValorNumerico()
  {
    return this.valorNumerico;
  }
  
  public void setValorNumerico(BigDecimal valorNumerico)
  {
    this.valorNumerico = valorNumerico;
  }
  
  public Boolean getValorBooleano()
  {
    return this.valorBooleano;
  }
  
  public void setValorBooleano(Boolean valorBooleano)
  {
    this.valorBooleano = valorBooleano;
  }
  
  public String getDescripcionCampo()
  {
    return this.descripcionCampo;
  }
  
  public void setDescripcionCampo(String descripcionCampo)
  {
    this.descripcionCampo = descripcionCampo;
  }
  
  
  public Boolean getComp() {
	return comp;
}

public void setComp(Boolean comp) {
	this.comp = comp;
}

public String toString()
  {
    return 
    
      "Parametro [parametroId=" + this.parametroId + ", nombre=" + this.nombre + ", tipo=" + this.tipo + ", valorCadena=" + this.valorCadena + ", valorFecha=" + this.valorFecha + ", valorNumerico=" + this.valorNumerico + ", valorBooleano=" + this.valorBooleano + "]";
  }
  
  @Transient
  private AlgoritmoAES aes = new AlgoritmoAES();
}
