package support.ussd.bean;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tigo.utils.ParametersWeb;
import com.tigo.utils.SysMessage;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.ReporteInBL;
import support.ussd.model.TAntecedente;
import support.ussd.model.TAppinvolucrada;
import support.ussd.model.TCronologia;
import support.ussd.model.TDato;
import support.ussd.model.THoraan;
import support.ussd.model.THoracro;
import support.ussd.model.TParticipante;
import support.ussd.model.TPlanaccion;
import support.ussd.model.TPuntosmejora;
import support.ussd.model.TReporte;

@ManagedBean
@ViewScoped
public class ReporteInBean
implements Serializable, IControlPrivilegios
{
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(ReporteInBean.class);
@Inject
private ReporteInBL userBL;
@Inject
private ControlerBitacora controlerBitacora;
private List<TReporte> listUser;
private TReporte user ;
private String userId;
private boolean edit;
private boolean visibleNuevoEditar;
private boolean visibleAgregarHoraAnt;
private boolean visibleAgregarHoraCro;
private ControlPrivilegio controlPrivilegio;
private Date fecha;
private Boolean booleanRender;

private List<String> listaTitulos;
private TDato datosGenerales;
private TAppinvolucrada appInvolucradas;
private List<TAppinvolucrada> listaAppInvolucradas;
private TParticipante participantes;
private List<TParticipante> listaParticipantes;
private TAntecedente antecedentes;
private List<TAntecedente> listaAntecedentes;
private THoraan horaAntecedente;
private List<THoraan> listaHoraAntecedentes;
private TCronologia cronologia;
private List<TCronologia> listaCronologia;
private THoracro horaCronologia;
private List<THoracro> listaHoraCronologia;
private TPlanaccion planAccion;
private List<TPlanaccion> listaPlanAccion;
private TPuntosmejora puntosMejora;
private List<TPuntosmejora> listaPuntoMejora;

private Date fechaInicial;
private Date fechaFinal;
private Date fechaAntecedente;
private Date fechaCronologia;
private Date fechaPlanAccion;
private Date horaAnt;
private Date horaCro;

@PostConstruct
public void init()
{
  try
  {
	cargarTitulos();
    this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
    newListas();
    this.user = new TReporte();
    this.listUser = this.userBL.getList();
    this.visibleNuevoEditar = false;
    this.visibleAgregarHoraAnt=false;
    this.visibleAgregarHoraCro=false;

  }
  catch (Exception e)
  {
    log.error("init|Fallo al inicializar la clase. " + e.getMessage());
  }
}

public void newListas(){
	listaAppInvolucradas= new ArrayList<TAppinvolucrada>();
    listaParticipantes= new ArrayList<TParticipante>();
    listaAntecedentes= new ArrayList<TAntecedente>();
    listaCronologia= new ArrayList<TCronologia>();
    listaPlanAccion= new ArrayList<TPlanaccion>();
    listaPuntoMejora= new ArrayList<TPuntosmejora>();
}

public void cargarTitulos(){
	listaTitulos= new ArrayList<String>();
	listaTitulos.add("1.- Datos Generales");
	listaTitulos.add("2.- APP, Sistemas y/o Plataformas involucradas");
	listaTitulos.add("3.- Participaron en la atención del incidente");
	listaTitulos.add("4.- Antecedentes");
	listaTitulos.add("5.- Cronología de eventos del incidente");
	listaTitulos.add("6.- Causa del incidente:");
	listaTitulos.add("7.- Solución");
	listaTitulos.add("8.- Plan de acción (Solución definitiva)");
	listaTitulos.add("9.- Puntos de Mejora");
	
}

public void saveUser()
{
 
//  //String name;
//  String str = "";
//  try
//  {
//    str = this.userBL.validate(this.user, this.userId);
//  }
//  catch (Exception e)
//  {
//    log.error("[saveUser] Fallo al intentar validar los parametros.", e);
//    str = "error con la conexion a la BD o otro problema";
//  }
//  if (!str.isEmpty())
//  {
//    SysMessage.error(str);
//    return;
//  }
	if(datosGenerales.getIncidente().trim().isEmpty()){
		SysMessage.warn("Por favor llenar el incidente");
		return;
	}
	datosGenerales.setIncidente(datosGenerales.getIncidente().trim());
	if(user.getNombre().trim().isEmpty()){
		SysMessage.warn("Por favor colocar el nombre");
		return;
	}
	
	if(fechaInicial==null){
		SysMessage.warn("Por favor seleccione una fecha inicial");
		return;
	}
	if(fechaFinal==null){
		SysMessage.warn("Por favor seleccione una fecha final");
		return;
	}
	if(listaAppInvolucradas.isEmpty()){
		SysMessage.warn("Por favor debe agregar APP, Sistemas y/o Plataformas involucradas");
		return;
	}
	if(listaParticipantes.isEmpty()){
		SysMessage.warn("Por favor debe agregar las personas que participaron en la atencion del incidente");
		return;
	}
	if(listaAntecedentes.isEmpty()){
		SysMessage.warn("Por favor debe agregar los antecedentes del incidente");
		return;
	}
	if(listaCronologia.isEmpty()){
		SysMessage.warn("Por favor debe agregar la cronologia de eventos del incidente");
		return;
	}
	if(datosGenerales.getDetallecausa().isEmpty()){
		SysMessage.warn("Por favor ingresar la cusa del incidente");
		return;
	}
	if(datosGenerales.getSolucion().isEmpty()){
		SysMessage.warn("Por favor ingresar la solucion");
		return;
	}
	if(listaPlanAccion.isEmpty()){
		SysMessage.warn("Por favor debe agregar una plan de accion del incidente");
		return;
	}
	if(listaPuntoMejora.isEmpty()){
		SysMessage.warn("Por favor debe agregar puntos de mejora del incidente");
		return;
	}
	
  try
  {
    if (!this.edit)
    {
    	user.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	user.setFechaInIncidencia(new Timestamp(fechaInicial.getTime()));
    	user.setFechaFinIncidencia(new Timestamp(fechaFinal.getTime()));
    	user.setEstado(true);
//    	user.setTAppinvolucradas(listaAppInvolucradas);
//    	user.setTParticipantes(listaParticipantes);
//    	user.setTAntecedentes(listaAntecedentes);
//    	user.setTCronologias(listaCronologia);
//    	user.setTPlanaccions(listaPlanAccion);
//    	user.setTPuntosmejoras(listaPuntoMejora);
      this.userBL.save(user,datosGenerales,listaAppInvolucradas,listaParticipantes,listaAntecedentes,listaCronologia,listaPlanAccion,listaPuntoMejora);
      this.controlerBitacora.insert(DescriptorBitacora.REPORTEIN, this.user.getId()+"", this.user.getNombre());
    }
    else
    {
      int id = Integer.parseInt(this.userId);
      user.setNombre(user.getNombre().toLowerCase());
      this.user.setId(Long.valueOf(id));
      this.userBL.update(this.user);
      this.controlerBitacora.update(DescriptorBitacora.REPORTEIN, this.user.getId()+"", this.user.getNombre());
    }
    this.listUser = this.userBL.getList();
    SysMessage.info("Se guardo correctamente.");
    newUser();
    this.visibleNuevoEditar = false;
  }
  catch (Exception e)
  {
    log.error("[saveUser] usuario: " + this.user.getNombre() + ", Fallo al guardar el usuario", e);
    SysMessage.error("Fallo al guardar el usuario");
  }
}

public void agregarAppInvolucradas(){
	appInvolucradas.setId((long) (listaAppInvolucradas.size()+1));
	listaAppInvolucradas.add(appInvolucradas);
	appInvolucradas= new TAppinvolucrada();
}

public void eliminarAppInvolucradas(long id){
	for (TAppinvolucrada a : listaAppInvolucradas) {
		if(a.getId()==id){
			listaAppInvolucradas.remove(a);
		}
	}
}

public void agregarParticipantes(){
	participantes.setId((long) (listaParticipantes.size()+1));
	listaParticipantes.add(participantes);
	participantes= new TParticipante();
}

public void eliminarParticipantes(long id){
	for (TParticipante a : listaParticipantes) {
		if(a.getId()==id){
			listaParticipantes.remove(a);
		}
	}
}

public void agregarAntecedente(){
	antecedentes.setId((long) (listaAntecedentes.size()+1));
	antecedentes.setFecha(new Timestamp(fechaAntecedente.getTime()));
	listaAntecedentes.add(antecedentes);
	antecedentes= new TAntecedente();
}

public void eliminarAntecedentes(long id){
	for (TAntecedente a : listaAntecedentes) {
		if(a.getId()==id){
			listaAntecedentes.remove(a);
		}
	}
}

public void agregarCronologia(){
	cronologia.setId((long) (listaCronologia.size()+1));
	cronologia.setFecha(new Timestamp(fechaCronologia.getTime()));
	listaCronologia.add(cronologia);
	cronologia= new TCronologia();
}

public void eliminarCronologia(long id){
	for (TCronologia a : listaCronologia) {
		if(a.getId()==id){
			listaCronologia.remove(a);
		}
	}
}

public void agregarPlanAccion(){
	planAccion.setId((long) (listaPlanAccion.size()+1));
	planAccion.setFecha(new Timestamp(fechaPlanAccion.getTime()));
	listaPlanAccion.add(planAccion);
	planAccion= new TPlanaccion();
}
public void eliminarPlanAccion(long id){
	for (TPlanaccion a : listaPlanAccion) {
		if(a.getId()==id){
			listaPlanAccion.remove(a);
		}
	}
}

public void agregarPuntosMejora(){
	puntosMejora.setId((long) (listaPuntoMejora.size()+1));
	listaPuntoMejora.add(puntosMejora);
	puntosMejora= new TPuntosmejora();
}

public void eliminarPuntosMejora(long id){
	for (TPuntosmejora a : listaPuntoMejora) {
		if(a.getId()==id){
			listaPuntoMejora.remove(a);
		}
	}
}


public void exportar(long id) {

	try {
		boolean result = Boolean.FALSE;
		
		TReporte rep=userBL.getReporte(id);
		TDato dato=userBL.getReporteDatos(rep.getId());
//		List<TAppinvolucrada> listaApp=userBL.getReporteApp(rep.getId());
//		System.out.println("tamaño: "+listaApp.size());
		log.debug("[exportar] se va exportar el reporte 1");
		String filenameReport = "report1";
		String name=rep.getNombre()+".pdf";
		Map<String, Object> rptParameters = new HashMap<String, Object>();
		rptParameters.put("Incidente", dato.getIncidente());
		rptParameters.put("idReporte", id);
		
		rptParameters.put("Logo", "C:\\Proyecto AutoGestion\\PEDRO\\ODECO_DIGITAL\\WebContent\\resources\\report\\logoTigo.png");
		rptParameters.put("Ticket", dato.getIdticket());
		rptParameters.put("FechaInicio", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rep.getFechaInIncidencia()));
		rptParameters.put("FechaFinal",  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rep.getFechaFinIncidencia()));
		rptParameters.put("Afectacion", dato.getAfectacion());
		rptParameters.put("BuAfectada", dato.getBuAfectada());
		rptParameters.put("Causa", dato.getCausa());
		rptParameters.put("Reporta", rep.getReporta());
		rptParameters.put("causaInciDetalle", dato.getDetallecausa());
		rptParameters.put("solucion", dato.getSolucion());
		

		result = exportReport(filenameReport, name,
				rptParameters);
		if (result) {			
			log.info("Se Exporto Correctamente el reporte 1");
			SysMessage.info("Se exporto correctamente");

			return;

		}
		log.error("No se exporto el reporte 1");
		SysMessage.error("Error al exportar");

		// return result;

	} catch (Exception e) {
		e.printStackTrace();
	}
}
public <E> boolean exportReport(String nameReport, String name,
		Map<String, Object> parameters) {
	log.info("Exportando el reporte " + nameReport
			+ " a xls ");
	boolean result = Boolean.FALSE;
	 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	try {
		listaAntecedentes= new ArrayList<TAntecedente>();
		JasperPrint jasperPrint = createJasperPrint(nameReport, parameters);

		JRPdfExporter exportador = new JRPdfExporter();
		
		exportador.setParameter(JRDocxExporterParameter.JASPER_PRINT, jasperPrint);
		//exportador.setParameter(JRXlsExporterParameter.OUTPUT_FILE, destFile);
//		exportador.setParameter(JRDocxExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//		exportador.setParameter(JRDocxExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//		exportador.setParameter(JRDocxExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//		exportador.setParameter(JRDocxExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		// exportador.setParameter(JRXlsExporterParameter.PASSWORD, "aaa");
		exportador.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, outputStream);
		exportador.exportReport();

		if (showPrintPDF(outputStream,name)) {
			log.info("El reporte " + nameReport
					+ " se eporto a xls");
			result = true;
		}

	} catch (JRException ex) {
		log.error("Error al intentar exportar el reporte " + nameReport
				+ " a xls", ex);
		ex.printStackTrace();
	
	} catch (Exception e) {
		log.error("Error al intentar exportar el reporte " + nameReport
				+ " a xls ", e);
	}

	return result;
}
private <E> JasperPrint createJasperPrintLista(String nameReport,Map<String, Object> parameters, List<E> beanCollection){
	
	try {
		
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\Proyecto AutoGestion\\PEDRO\\workspace\\ABM_WEB\\WebContent\\resources\\report\\report1.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, new JRBeanCollectionDataSource(beanCollection));

		return jasperPrint;
	}catch (JRException ex) {
		// TODO: handle exception
		return null;
	}
	
}
private <E> JasperPrint createJasperPrint(String nameReport,
		Map<String, Object> parameters) throws JRException, SQLException, ClassNotFoundException {

	Class.forName("org.postgresql.Driver"); 
    Connection cnx = DriverManager.getConnection("jdbc:postgresql://172.31.89.248:5432/pp", "usrussd", "Telecel123"); 
	JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("C:\\Proyecto AutoGestion\\PEDRO\\workspace\\ABM_WEB\\WebContent\\resources\\report\\report1.jasper");
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
			parameters,cnx);

	return jasperPrint;
}

private boolean showPrintPDF(ByteArrayOutputStream outputStream, String nameReport){
	try {
		 FacesContext fcontext = FacesContext.getCurrentInstance();
	       ExternalContext externalContext = fcontext.getExternalContext();

	       HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	       response.setHeader("Content-disposition", "attachment; filename=\"" + nameReport + "\"");
	       response.setContentType("application/vnd.msword");
	       response.setContentLength(outputStream.size());

	       outputStream.writeTo(response.getOutputStream());
	       fcontext.responseComplete();
	       return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
      
   }

public void editRoleUser() throws Exception
{
  if (this.user != null)
  {

      this.userId = this.user.getId()+"";
      this.edit = true;
      datosGenerales=userBL.getReporteDatos(user.getId());
      this.visibleNuevoEditar = true;
  }
  else
  {
    log.warn("[editar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
}

public void editRoleUser(TReporte muUsuario) throws Exception
{
  this.user = muUsuario;
  editRoleUser();
}

public String deleteRoleUser()
{
  if (this.user != null)
  {
    try
    {
      this.userBL.delete(this.user);
      this.controlerBitacora.delete(DescriptorBitacora.REPORTEIN, this.user.getId()+"", this.user.getNombre());
      this.listUser = this.userBL.getList();
      SysMessage.info("Se elimino correctamente.");
    }
    catch (Exception e)
    {
      log.error("[deleteRoleUser]  error al eliminar el reporte con id:" + this.user.getId() + "  " + e);
      SysMessage.error("Fallo al eliminar.");
    }
  }
  else
  {
    log.warn("[eliminar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
  return "";
}



public String deleteRoleUser(TReporte muUsuario)
{
  this.user = muUsuario;
  return deleteRoleUser();
}

public void newUser()
{
  this.edit = false;
  this.user = new TReporte();
  datosGenerales= new TDato();
   appInvolucradas= new TAppinvolucrada();
   participantes= new TParticipante();
    antecedentes= new TAntecedente();
    horaAntecedente= new THoraan();
    cronologia= new TCronologia();
    horaCronologia= new THoracro();
    planAccion= new TPlanaccion();
    puntosMejora= new TPuntosmejora();
  this.visibleNuevoEditar = true;
  this.booleanRender = Boolean.valueOf(true);
}

public String getColor(boolean estado)
{
  String color;
  try
  {
    if (estado) {
      color = "background-color:#FFFFFF";
    } else {
      color = "background-color:#" + P.getParamVal(Integer.valueOf(15));
    }
  }
  catch (Exception e)
  {
    log.error("[Get Color] Error al evalor Color UsuarioRol:" + e.getMessage(), e);
    color = "background-color:#FFFFFF";
  }
  return color;
}

public String getUserId()
{
  return this.userId;
}

public void setUserId(String userId)
{
  this.userId = userId;
}

public Boolean getEdit()
{
  return Boolean.valueOf(this.edit);
}

public void setEdit(Boolean edit)
{
  this.edit = edit.booleanValue();
}

public TReporte getUser()
{
  return this.user;
}

public void setUser(TReporte user)
{
  this.user = user;
}





public List<TReporte> getListUser()
{
  return this.listUser;
}

public void setListUser(List<TReporte> listUser)
{
  this.listUser = listUser;
}

public boolean isVisibleNuevoEditar()
{
  return this.visibleNuevoEditar;
}

public void setVisibleNuevoEditar(boolean visibleNuevoEditar)
{
  this.visibleNuevoEditar = visibleNuevoEditar;
}

public boolean isAuthorized(int idAccion)
{
  return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_REPORTE_IN, idAccion);
}

public Date getFecha()
{
  return this.fecha;
}

public void setFecha(Date fecha)
{
  this.fecha = fecha;
}

public Boolean getBooleanRender()
{
  return this.booleanRender;
}




public List<THoraan> getListaHoraAntecedentes() {
	return listaHoraAntecedentes;
}

public void setListaHoraAntecedentes(List<THoraan> listaHoraAntecedentes) {
	this.listaHoraAntecedentes = listaHoraAntecedentes;
}

public List<THoracro> getListaHoraCronologia() {
	return listaHoraCronologia;
}

public void setListaHoraCronologia(List<THoracro> listaHoraCronologia) {
	this.listaHoraCronologia = listaHoraCronologia;
}

public List<TPuntosmejora> getListaPuntoMejora() {
	return listaPuntoMejora;
}

public void setListaPuntoMejora(List<TPuntosmejora> listaPuntoMejora) {
	this.listaPuntoMejora = listaPuntoMejora;
}

public TDato getDatosGenerales() {
	return datosGenerales;
}

public void setDatosGenerales(TDato datosGenerales) {
	this.datosGenerales = datosGenerales;
}

public TAppinvolucrada getAppInvolucradas() {
	return appInvolucradas;
}

public void setAppInvolucradas(TAppinvolucrada appInvolucradas) {
	this.appInvolucradas = appInvolucradas;
}

public TParticipante getParticipantes() {
	return participantes;
}

public void setParticipantes(TParticipante participantes) {
	this.participantes = participantes;
}

public TAntecedente getAntecedentes() {
	return antecedentes;
}

public void setAntecedentes(TAntecedente antecedentes) {
	this.antecedentes = antecedentes;
}

public THoraan getHoraAntecedente() {
	return horaAntecedente;
}

public void setHoraAntecedente(THoraan horaAntecedente) {
	this.horaAntecedente = horaAntecedente;
}

public TCronologia getCronologia() {
	return cronologia;
}

public void setCronologia(TCronologia cronologia) {
	this.cronologia = cronologia;
}

public THoracro getHoraCronologia() {
	return horaCronologia;
}

public void setHoraCronologia(THoracro horaCronologia) {
	this.horaCronologia = horaCronologia;
}

public TPlanaccion getPlanAccion() {
	return planAccion;
}

public void setPlanAccion(TPlanaccion planAccion) {
	this.planAccion = planAccion;
}

public TPuntosmejora getPuntosMejora() {
	return puntosMejora;
}

public void setPuntosMejora(TPuntosmejora puntosMejora) {
	this.puntosMejora = puntosMejora;
}

public List<String> getListaTitulos() {
	return listaTitulos;
}

public List<TAntecedente> getListaAntecedentes() {
	return listaAntecedentes;
}

public Date getHoraAnt() {
	return horaAnt;
}

public void setHoraAnt(Date horaAnt) {
	this.horaAnt = horaAnt;
}

public Date getHoraCro() {
	return horaCro;
}

public void setHoraCro(Date horaCro) {
	this.horaCro = horaCro;
}

public void setListaAntecedentes(List<TAntecedente> listaAntecedentes) {
	this.listaAntecedentes = listaAntecedentes;
}

public void setListaTitulos(List<String> listaTitulos) {
	this.listaTitulos = listaTitulos;
}

public List<TAppinvolucrada> getListaAppInvolucradas() {
	return listaAppInvolucradas;
}

public void setListaAppInvolucradas(List<TAppinvolucrada> listaAppInvolucradas) {
	this.listaAppInvolucradas = listaAppInvolucradas;
}

public List<TParticipante> getListaParticipantes() {
	return listaParticipantes;
}

public void setListaParticipantes(List<TParticipante> listaParticipantes) {
	this.listaParticipantes = listaParticipantes;
}



public List<TPlanaccion> getListaPlanAccion() {
	return listaPlanAccion;
}

public void setListaPlanAccion(List<TPlanaccion> listaPlanAccion) {
	this.listaPlanAccion = listaPlanAccion;
}

public void setBooleanRender(Boolean booleanRender)
{
  this.booleanRender = booleanRender;
}

public List<TCronologia> getListaCronologia() {
	return listaCronologia;
}

public void setListaCronologia(List<TCronologia> listaCronologia) {
	this.listaCronologia = listaCronologia;
}

public String getExpresionRegular()
{
  return ParametersWeb.EXPRESION_REGULAR_TODO;
}

public Date getFechaPlanAccion() {
	return fechaPlanAccion;
}

public void setFechaPlanAccion(Date fechaPlanAccion) {
	this.fechaPlanAccion = fechaPlanAccion;
}

public Date getFechaInicial() {
	return fechaInicial;
}

public void setFechaInicial(Date fechaInicial) {
	this.fechaInicial = fechaInicial;
}

public Date getFechaFinal() {
	return fechaFinal;
}

public void setFechaFinal(Date fechaFinal) {
	this.fechaFinal = fechaFinal;
}



public boolean isVisibleAgregarHoraAnt() {
	return visibleAgregarHoraAnt;
}

public void setVisibleAgregarHoraAnt(boolean visibleAgregarHoraAnt) {
	this.visibleAgregarHoraAnt = visibleAgregarHoraAnt;
}

public boolean isVisibleAgregarHoraCro() {
	return visibleAgregarHoraCro;
}

public void setVisibleAgregarHoraCro(boolean visibleAgregarHoraCro) {
	this.visibleAgregarHoraCro = visibleAgregarHoraCro;
}

public Date getFechaAntecedente() {
	return fechaAntecedente;
}

public Date getFechaCronologia() {
	return fechaCronologia;
}

public void setFechaCronologia(Date fechaCronologia) {
	this.fechaCronologia = fechaCronologia;
}

public void setFechaAntecedente(Date fechaAntecedente) {
	this.fechaAntecedente = fechaAntecedente;
}

public String getExpresionRegularNumero()
{
  return ParametersWeb.EXPRESION_REGULAR_NUMERO;
}
}
