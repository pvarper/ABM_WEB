package support.ussd.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.ussd.business.RegistroBL;
import support.ussd.model.TRegistro;

@ManagedBean
@ViewScoped
public class RegistroBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(RegistroBean.class);
  @Inject
  private RegistroBL logBl;
  private List<TRegistro> listTransaccion;
  
  @PostConstruct
  public void init()
  {
    try
    {
      this.listTransaccion = this.logBl.getList();
    }
    catch (Exception e)
    {
      log.error("[init] Fallo al iniciar el bean.", e);
    }
  }

	public List<TRegistro> getListTransaccion() {
		return listTransaccion;
	}
	
	public void setListTransaccion(List<TRegistro> listTransaccion) {
		this.listTransaccion = listTransaccion;
	}
  
 
}

