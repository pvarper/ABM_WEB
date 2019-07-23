package support.user.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import org.apache.commons.lang.StringEscapeUtils;
import support.user.model.Parametro;

@Named
public class ParametroCompDAO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @PersistenceContext(unitName="pUnit_dbSystem")
  private transient EntityManager entityManager;
  @Resource
  private transient UserTransaction transaction;
  
  public Parametro get(int id)
    throws Exception
  {
    return (Parametro)this.entityManager.find(Parametro.class, Integer.valueOf(id));
  }
  
  public void update(Parametro dato)
    throws Exception
  {
    this.transaction.begin();
    this.entityManager.merge(dato);
    this.transaction.commit();
  }
  @SuppressWarnings("unchecked")
  public List<Parametro> getList()
    throws Exception
  {
    return this.entityManager.createQuery("SELECT pa FROM Parametro pa where pa.comp=false Order by pa.parametroId").getResultList();
  }
  
  public Parametro getParametro(long Id)
  {
    Query q = this.entityManager
      .createQuery("SELECT p FROM Parametro p where p.parametroId = :Id");
    q.setParameter("Id", Long.valueOf(Id));
    return (Parametro)q.getSingleResult();
  }
  
  public String getValorParametro(long id)
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId = :id");
    q.setParameter("id", Long.valueOf(id));
    return (String)q.getSingleResult();
  }
  
  public String getValorInitialContextFactory()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =2");
    return (String)q.getSingleResult();
  }
  
  public String getValorProviderUrl()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =3");
    return (String)q.getSingleResult();
  }
  
  public String getValorSecurityAutenticacion()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =4");
    return (String)q.getSingleResult();
  }
  
  public String getValorSecurityPrincipal()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =5");
    return (String)q.getSingleResult();
  }
  
  public String getValorSecurityCredential()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =6");
    return (String)q.getSingleResult();
  }
  
  public String getValorSecurityUser()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =7");
    return (String)q.getSingleResult();
  }
  
  public String getValorSecurityDominio()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =8");
    return (String)q.getSingleResult();
  }
  
  public String getValorUsuarioActiveDirectory()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =9");
    return (String)q.getSingleResult();
  }
  
  public String getValorUsuarioLogin()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =10");
    return (String)q.getSingleResult();
  }
  
  public String getValorUsuarioPassword()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =11");
    return (String)q.getSingleResult();
  }
  
  public String getValorUsuarioOpciones()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valornumerico FROM Parametro p where p.parametroId =12");
    return (String)q.getSingleResult();
  }
  
  public String getValorUsuarioTiempoFuera()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valornumerico FROM Parametro p where p.parametroId =13");
    return (String)q.getSingleResult();
  }
  
  public String getRutaAdjunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =18");
    return (String)q.getSingleResult();
  }
  
  public String getCorreoRegulacion()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =16");
    return (String)q.getSingleResult();
  }
  
  public String getCorreoATT()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =17");
    return (String)q.getSingleResult();
  }
  
  public String getCorreoAdmFuncional()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =44");
    return (String)q.getSingleResult();
  }
  
  public String getTemplateEnvio()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =34");
    return (String)q.getSingleResult();
  }
  
  public String getLinkSistema()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =19");
    return (String)q.getSingleResult();
  }
  
  public String getDescripcionAdjunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =20");
    return (String)q.getSingleResult();
  }
  
  public String getAsunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =41");
    return (String)q.getSingleResult();
  }
  
  public String getAsuntoReasignacion()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =43");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpHost()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =21");
    return (String)q.getSingleResult();
  }
  
  public String getMailTituloFrom()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =22");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpPort()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =23");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpAuth()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =24");
    
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpStartTlsEnable()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =25");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpSslEnable()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =26");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpUser()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =27");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpPw()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =28");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpFrom()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =29");
    return (String)q.getSingleResult();
  }
  
  public String getRutaReporte()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =30");
    return (String)q.getSingleResult();
  }
  
  public String getRutaLogoOdeco()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =31");
    return (String)q.getSingleResult();
  }
  
  public String getRutaLogoTigo()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =32");
    return (String)q.getSingleResult();
  }
  
  public String getRutaExportarReporte()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.nombre ='" + StringEscapeUtils.escapeSql("RUTA EXPORTAR REPORTES") + "'");
    return (String)q.getSingleResult();
  }
  
  public String getFormaRegistro()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =33");
    return (String)q.getSingleResult();
  }
  
  public String getTituloUno()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =35");
    return (String)q.getSingleResult();
  }
  
  public String getTituloUnoReasignacion()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =42");
    return (String)q.getSingleResult();
  }
  
  public String getTituloUnoAdmFuncional()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =43");
    return (String)q.getSingleResult();
  }
  
  public String getTituloDos()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =36");
    return (String)q.getSingleResult();
  }
  
  public String getTituloDosAdmFuncional()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =46");
    return (String)q.getSingleResult();
  }
  
  public String getTituloTres()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =37");
    return (String)q.getSingleResult();
  }
  
  public String getTituloCuatro()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =38");
    return (String)q.getSingleResult();
  }
  
  public String getTituloCinco()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =39");
    return (String)q.getSingleResult();
  }
}
