package support.id;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="privilegio")
@ViewScoped
public class PrivilegioID
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final String ACCION_CREAR = "1";
  public static final String ACCION_MODIFICAR = "2";
  public static final String ACCION_ELIMINAR = "3";
  public static final String ACCION_EXPORTAR = "4";
  public static final String ACCION_REINTENTO_SINGLE = "5";
  public static final String ACCION_REINTENTO_ALL = "6";
  public static final String ACCION_INICIAR_PROCESS = "7";
  public static final String ACCION_PARAR_PROCESS = "8";
  public static final String ACCION_ENVIAR_MAIL = "9";
  public static final String ACCION_CONFIGURAR = "10";
  public static final String ACCION_CARGA_MASIVA = "11";
  public static final String ACCION_BUSCAR = "12";
  
  public String getACCION_CREAR()
  {
    return "1";
  }
  
  public String getACCION_MODIFICAR()
  {
    return "2";
  }
  
  public String getACCION_ELIMINAR()
  {
    return "3";
  }
  
  public String getACCION_EXPORTAR()
  {
    return "4";
  }
  
  public String getACCION_REINTENTO_SINGLE()
  {
    return "5";
  }
  
  public String getACCION_REINTENTO_ALL()
  {
    return "6";
  }
  
  public String getACCION_INICIAR_PROCESS()
  {
    return "7";
  }
  
  public String getACCION_PARAR_PROCESS()
  {
    return "8";
  }
  
  public String getACCION_ENVIAR_MAIL()
  {
    return "9";
  }
  
  public String getACCION_CONFIGURAR()
  {
    return "10";
  }
  
  public String getACCION_CARGA_MASIVA()
  {
    return "11";
  }
  
  public String getACCION_BUSCAR()
  {
    return "12";
  }
}
