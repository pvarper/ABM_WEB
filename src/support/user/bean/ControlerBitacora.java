package support.user.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import support.user.business.BitacoraBL;

@Named
public class ControlerBitacora
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  BitacoraBL logBl;

  @SuppressWarnings("rawtypes")
  public void insert(Enum dato, String id, String name)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionInsert(strIdUs, remoteAddr, dato, id, name);
  }
  @SuppressWarnings("rawtypes")
  public void update(Enum dato, String id, String name)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionUpdate(strIdUs, remoteAddr, dato, id, name);
  }
  @SuppressWarnings("rawtypes")
  public void delete(Enum dato, String id, String name)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionDelete(strIdUs, remoteAddr, dato, id, name);
  }
  @SuppressWarnings("rawtypes")
  public void find(Enum dato, String id, String name)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionFind(strIdUs, remoteAddr, dato, id, name);
  }
  @SuppressWarnings("rawtypes")
  public void cortar(Enum dato, String id)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionCortar(strIdUs, remoteAddr, dato, id);
  }
  @SuppressWarnings("rawtypes")
  public void reconectar(Enum dato, String id)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionReconectar(strIdUs, remoteAddr, dato, id);
  }
  @SuppressWarnings("rawtypes")
  public void accion(Enum dato, String accion)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accion(strIdUs, remoteAddr, dato, accion);
  }
  @SuppressWarnings("rawtypes")
  public void exportar(Enum dato, String id, String name)
  {
    HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
    String remoteAddr = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
    this.logBl.accionExportar(strIdUs, remoteAddr, dato, id, name);
  }
}
