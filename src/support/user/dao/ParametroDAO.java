package support.user.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import support.user.model.Parametro;

@Named
public class ParametroDAO
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
    return this.entityManager.createQuery("SELECT pa FROM Parametro pa where pa.comp=true Order by pa.parametroId").getResultList();
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
  
  
  
  public String getRutaAdjunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =60");
    return (String)q.getSingleResult();
  }
  
  public String getTemplateEnvio()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =72");
    return (String)q.getSingleResult();
  }
  
  public String getTemplateEnvioObservado()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =74");
    return (String)q.getSingleResult();
  }
  
  public String getLinkSistema()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =61");
    return (String)q.getSingleResult();
  }
  
  public String getDescripcionAdjunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =62");
    return (String)q.getSingleResult();
  }
  
  public String getAsunto()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =73");
    return (String)q.getSingleResult();
  }
  
  
  public String getMailSmtpHost()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =63");
    return (String)q.getSingleResult();
  }
  
  public String getMailTituloFrom()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =64");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpPort()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =65");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpAuth()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =66");
    
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpStartTlsEnable()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =67");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpSslEnable()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =68");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpUser()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =69");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpPw()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =70");
    return (String)q.getSingleResult();
  }
  
  public String getMailSmtpFrom()
  {
    Query q = this.entityManager
      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId =71");
    return (String)q.getSingleResult();
  }
  
  public String getWsPortabilidad(long idParametro){
	  Query q = this.entityManager
		      .createQuery("SELECT p.valorCadena FROM Parametro p where p.parametroId ="+idParametro);
		    return (String)q.getSingleResult();
	  
  }
  public double getTimeOutWsPortabilidad(long idParametro){
	  Query q = this.entityManager
		      .createQuery("SELECT p.valorNumerico FROM Parametro p where p.parametroId ="+idParametro);
	  		
		    return Double.valueOf(((BigDecimal)q.getSingleResult()).toString());
	  
  }
  
  public boolean getActiveWsPortabilidad(long idParametro){
	  Query q = this.entityManager
		      .createQuery("SELECT p.valorBooleano FROM Parametro p where p.parametroId ="+idParametro);
		    return (Boolean)q.getSingleResult();
	  
  }
  
  
}
