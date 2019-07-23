package support.user.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.user.dao.ParametroDAO;
import support.user.model.Parametro;

@Named
public class ParametroBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  private ParametroDAO dao;
  
  public String validate(Parametro parametro)
    throws Exception
  {
    String resp = "";
    if (parametro != null) {
      switch (parametro.getTipo())
      {
      case 1: 
        if ((parametro.getValorCadena() == null) || (parametro.getValorCadena().trim().isEmpty())) {
          resp = "[Error]El valor del Parametro " + parametro.getNombre() + ", esta Vacio.. ";
        }
        break;
      case 3: 
        if (parametro.getValorNumerico() == null) {
          resp = "[Error]El valor del Parametro " + parametro.getNombre() + ", esta Vacio.. ";
        }
        break;
      case 2: 
        if (parametro.getValorFecha() == null) {
          resp = "[Error]El valor del Parametro " + parametro.getNombre() + ", esta Vacio.. ";
        }
        break;
      case 4: 
        if (parametro.getValorBooleano() == null) {
          resp = "[Error]El valor del Parametro " + parametro.getNombre() + ", esta Vacio.. ";
        }
        break;
      }
    }
    return resp;
  }
  
  public void updateParametro(Parametro parametro)
    throws Exception
  {
    this.dao.update(parametro);
  }
  
  public List<Parametro> getParameters()
    throws Exception
  {
    return this.dao.getList();
  }
  
  public Parametro getParametro(int idParametro)
    throws Exception
  {
    return this.dao.get(idParametro);
  }
  
  public Parametro getParametro(long Id)
  {
    return this.dao.getParametro(Id);
  }
  
  public String getValorParametro(long Id)
  {
    return this.dao.getValorParametro(Id);
  }
  
  public String getRutaAdjunto()
  {
    return this.dao.getRutaAdjunto();
  }
  
  public String getTemplateEnvio()
  {
    return this.dao.getTemplateEnvio();
  }
  
  public String getTemplateEnvioObservado()
  {
    return this.dao.getTemplateEnvioObservado();
  }
  
  public String getLinkSistema()
  {
    return this.dao.getLinkSistema();
  }
  
  public String getDescripcionAdjunto()
  {
    return this.dao.getDescripcionAdjunto();
  }
  
  public String getAsunto()
  {
    return this.dao.getAsunto();
  }
   
  public String getMailSmtpHost()
  {
    return this.dao.getMailSmtpHost();
  }
  
  public String getMailTituloFrom()
  {
    return this.dao.getMailTituloFrom();
  }
  
  public String getMailSmtpPort()
  {
    return this.dao.getMailSmtpPort();
  }
  
  public String getMailSmtpAuth()
  {
    return this.dao.getMailSmtpAuth();
  }
  
  public String getMailSmtpStartTlsEnable()
  {
    return this.dao.getMailSmtpStartTlsEnable();
  }
  
  public String getMailSmtpSslEnable()
  {
    return this.dao.getMailSmtpSslEnable();
  }
  
  public String getMailSmtpUser()
  {
    return this.dao.getMailSmtpUser();
  }
  
  public String getMailSmtpPw()
  {
    return this.dao.getMailSmtpPw();
  }
  
  public String getMailSmtpFrom()
  {
    return this.dao.getMailSmtpFrom();
  }
  
  public String getWsPortabilidad(long idParametro){
	  return this.dao.getWsPortabilidad(idParametro);
	  
  }
  public double getTimeOutWsPortabilidad(long idParametro){
	  return this.dao.getTimeOutWsPortabilidad(idParametro);
  }
  
  public boolean getActiveWsPortabilidad(long idParametro){
	  return this.dao.getActiveWsPortabilidad(idParametro);
	  
  }
  
  
 
}
