package support.user.ldap;

public enum DescriptorBitacora
{
  FORMULARIO("GESTOR FORMULARIO"),
  PARAMETRO("GESTOR PARAMETRO"),
  ANULAR("ANULACION DE COMPROBANTES"),
  ALTABCCS("ALTA DE USUARIOS BCCS"),
  ALTAWIMAX("ALTA DE USUARIOS WIMAX"),
  ALTAAS("ALTA DE USUARIOS AS"),
  ALTATERMINAL("ALTA DE USUARIOS TERMINAL"),
  ALTA("ALTA DE USUARIOS"),
  BAJA("BAJA DE USUARIOS"),
  AREA("GESTOR AREAS"),
  REPLICA("REPLICA"),
  DOCUMENTO("GESTOR DOCUMENTO"),
  BANDEJA("MI BANDEJA"),
  BANDEJAADMIN("MI BANDEJA ADMIN"),
  SERVICIOSABM("SERVICIOS ABM "),
  TIPODOC("GESTOR TIPO DOCUMENTO"),
  CAMBIOE("CAMBIO DE ESTADO DE PC A CA"),
  NIVBOD("NIVELACION DE BODEGA"),
  REPORTEIN("REPORTE DE INCIDENCIAS"),
  CAMBIOF("CAMBIO DE FECHA"),
  VOLVERF("PROCESO VOLVER FECHA"),
  USUARIO("GESTOR USUARIO"),
  SERVICIO("GESTOR SERVICIO"),
  ABM("GESTION DE ABM"),
  TERMINALES("GESTION DE TERMINALES"),
  ODECO("CAMBIO ESTADO ODECO"),
  ANITA("GESTION DE USUARIOS ANITA"),
  LIBERACION_AR("LIBERACION DE CUENTAS POS ACTIVADOR"),
  LIBERACION_AR2("CAMBIO DE MODALIDAD DE PRE A POS ACTIVADOR"),
  LIBERACION("LIBERACION DE CUENTAS"),
  GRUPO(
    "GESTOR GRUPO"),  ROL(
    "ROL"),  MENSAJES("Administracion de Mensajes"),  REQUERIMIENTO(
    "GESTION REQUERIMIENTO"),  PLATAFORMA(
    "GESTION PLATAFORMA"),VALIDARSIGA("FORMULARIO VALIDAR USUARIO SIGA");
  
  private String formulario;
  
  private DescriptorBitacora(String formulario)
  {
    this.formulario = formulario;
  }
  
  public String getFormulario()
  {
    return this.formulario;
  }
}