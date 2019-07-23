package support.ussd.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.ussd.business.FreemBL;
import support.ussd.model.TFreem;

@ManagedBean
@ViewScoped
public class FreemBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(FreemBean.class);
  @Inject
  private FreemBL logBl;
  private List<TFreem> listTransaccion;
  
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

	public List<TFreem> getListTransaccion() {
		return listTransaccion;
	}
	
	public void setListTransaccion(List<TFreem> listTransaccion) {
		this.listTransaccion = listTransaccion;
	}
  
 
}

