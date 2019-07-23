package support.user.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.user.dao.ParametroCompDAO;
import support.user.model.Parametro;

@Named
public class ParametroCompBL
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Inject
  private ParametroCompDAO dao;
  
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
  
  public String getInitialContextFactory()
  {
    return this.dao.getValorInitialContextFactory();
  }
  
  public String getValorProviderUrl()
  {
    return this.dao.getValorProviderUrl();
  }
  
  public String getValorSecurityAutenticacion()
  {
    return this.dao.getValorSecurityAutenticacion();
  }
  
  public String getValorSecurityPrincipal()
  {
    return this.dao.getValorSecurityPrincipal();
  }
  
  public String getValorSecurityCredential()
  {
    return this.dao.getValorSecurityCredential();
  }
  
  public String getValorSecurityUser()
  {
    return this.dao.getValorSecurityUser();
  }
  
  public String getValorSecurityDominio()
  {
    return this.dao.getValorSecurityDominio();
  }
  
  public String getValorUsuarioActiveDirectory()
  {
    return this.dao.getValorUsuarioActiveDirectory();
  }
  
  public String getValorUsuarioLogin()
  {
    return this.dao.getValorUsuarioLogin();
  }
  
  public String getValorUsuarioPassword()
  {
    return this.dao.getValorUsuarioPassword();
  }
  
  public String getValorUsuarioOpciones()
  {
    return this.dao.getValorUsuarioOpciones();
  }
  
  public String getValorUsuarioTiempoFuera()
  {
    return this.dao.getValorUsuarioTiempoFuera();
  }
  
  public String getRutaAdjunto()
  {
    return this.dao.getRutaAdjunto();
  }
  
  public String getCorreoRegulacion()
  {
    return this.dao.getCorreoRegulacion();
  }
  
  public String getCorreoATT()
  {
    return this.dao.getCorreoATT();
  }
  
  public String getCorreoAdmFuncional()
  {
    return this.dao.getCorreoAdmFuncional();
  }
  
  public String getTemplateEnvio()
  {
    return this.dao.getTemplateEnvio();
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
  
  public String getAsuntoReasignacion()
  {
    return this.dao.getAsuntoReasignacion();
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
  
  public String getRutaReporte()
  {
    return this.dao.getRutaReporte();
  }
  
  public String getRutaLogoOdeco()
  {
    return this.dao.getRutaLogoOdeco();
  }
  
  public String getRutaLogoTigo()
  {
    return this.dao.getRutaLogoTigo();
  }
  
  public String getRutaExportarReporte()
  {
    return this.dao.getRutaExportarReporte();
  }
  
  public String getFormaRegistro()
  {
    return this.dao.getFormaRegistro();
  }
  
  public String getTituloUno()
  {
    return this.dao.getTituloUno();
  }
  
  public String getTituloUnoReasignacion()
  {
    return this.dao.getTituloUnoReasignacion();
  }
  
  public String getTituloUnoAdmnFuncional()
  {
    return this.dao.getTituloUnoAdmFuncional();
  }
  
  public String getTituloDos()
  {
    return this.dao.getTituloDos();
  }
  
  public String getTituloDosAdmFuncional()
  {
    return this.dao.getTituloDosAdmFuncional();
  }
  
  public String getTituloTres()
  {
    return this.dao.getTituloTres();
  }
  
  public String getTituloCuatro()
  {
    return this.dao.getTituloCuatro();
  }
  
  public String getTituloCinco()
  {
    return this.dao.getTituloCinco();
  }
}
