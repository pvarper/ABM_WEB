package support.ussd.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.ussd.business.DhBL;
import support.ussd.model.TDh;

@ManagedBean
@ViewScoped
public class DhBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(DhBean.class);
  @Inject
  private DhBL logBl;
  private List<TDh> listTransaccion;
  
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

	public List<TDh> getListTransaccion() {
		return listTransaccion;
	}
	
	public void setListTransaccion(List<TDh> listTransaccion) {
		this.listTransaccion = listTransaccion;
	}
  
 
}

