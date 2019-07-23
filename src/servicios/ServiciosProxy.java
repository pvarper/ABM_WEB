package servicios;

public class ServiciosProxy implements servicios.Servicios {
  private String _endpoint = null;
  private servicios.Servicios servicios = null;
  
  public ServiciosProxy() {
    _initServiciosProxy();
  }
  
  public ServiciosProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiciosProxy();
  }
  
  private void _initServiciosProxy() {
    try {
      servicios = (new servicios.ServicioInsertLocator()).getServiciosPort();
      if (servicios != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicios)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicios)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicios != null)
      ((javax.xml.rpc.Stub)servicios)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public servicios.Servicios getServicios() {
    if (servicios == null)
      _initServiciosProxy();
    return servicios;
  }
  
  public servicios.Result obtenerUsuarioBuscado(java.lang.String arg0) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerUsuarioBuscado(arg0);
  }
  
  public servicios.Result bajaWebConsultas(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaWebConsultas(LOGIN);
  }
  
  public servicios.Result actualizarCuentaPosALI(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarCuentaPosALI(CUENTA, MODALIDAD);
  }
  
  public servicios.Result bajaCrm(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaCrm(LOGIN);
  }
  
  public servicios.Result logFechaCuenta(java.lang.String user, java.lang.String cuenta, java.lang.String newFecha, java.lang.String oldFecha) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.logFechaCuenta(user, cuenta, newFecha, oldFecha);
  }
  
  public servicios.Result deleteCuentaEnTool(java.lang.String CUENTA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.deleteCuentaEnTool(CUENTA);
  }
  
  public servicios.Result actualizarPREaPOSReservada(java.lang.String CUENTA, java.lang.String TEXTO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarPREaPOSReservada(CUENTA, TEXTO);
  }
  
  public servicios.Result insertUsuarioASGenerico(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioASGenerico(USUARIO, NOMBRE, AREA);
  }
  
  public servicios.Result obtenerRolesLucky() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerRolesLucky();
  }
  
  public servicios.Result volverFecha(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO, java.lang.String FECHA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.volverFecha(COMPROBANTE, SERIE, NRO, FECHA);
  }
  
  public servicios.Result cambioFecha(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO, java.lang.String FECHA_INICIAL, java.lang.String FECHA_FINAL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.cambioFecha(COMPROBANTE, SERIE, NRO, FECHA_INICIAL, FECHA_FINAL);
  }
  
  public servicios.Result verificarCuentaEnToolCuenta(java.lang.String CUENTA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.verificarCuentaEnToolCuenta(CUENTA);
  }
  
  public servicios.Result anulacionCteBlanco(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.anulacionCteBlanco(COMPROBANTE, SERIE, NRO);
  }
  
  public servicios.Result obtenerServiciosAbm(java.lang.String ABM) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerServiciosAbm(ABM);
  }
  
  public servicios.Result obtenerPerfilesBCCS() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerPerfilesBCCS();
  }
  
  public servicios.Result obtenerIdDptoSubsidio(java.lang.String CIUDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerIdDptoSubsidio(CIUDAD);
  }
  
  public servicios.Result obtenerLocalidadesBCCS() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerLocalidadesBCCS();
  }
  
  public servicios.Result cambioPC_CA(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.cambioPC_CA(COMPROBANTE, SERIE, NRO);
  }
  
  public servicios.Result bajaWebSubsidio(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaWebSubsidio(LOGIN);
  }
  
  public servicios.Result obtenerCiudadesSubsidio() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCiudadesSubsidio();
  }
  
  public servicios.Result insertUsuarioBCCS(java.lang.String GRUPO, java.lang.String USUARIO, java.lang.String APELLIDOS, java.lang.String EHUMANO, java.lang.String DEPARTAMENTO, java.lang.String AREA, java.lang.String TIPO_COMPROBANTE) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioBCCS(GRUPO, USUARIO, APELLIDOS, EHUMANO, DEPARTAMENTO, AREA, TIPO_COMPROBANTE);
  }
  
  public servicios.Result insertUsuarioASApp(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioASApp(USUARIO, NOMBRE, AREA);
  }
  
  public servicios.Result bajaUsuarioBCCS(java.lang.String USUARIO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaUsuarioBCCS(USUARIO);
  }
  
  public servicios.Result obtenerCoordinadorOdeco(java.lang.String rol_id) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCoordinadorOdeco(rol_id);
  }
  
  public servicios.Result obtenerCuentasCongeladasBCCS(java.lang.String LOCALIDAD, java.lang.String MODALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCuentasCongeladasBCCS(LOCALIDAD, MODALIDAD, CANTIDAD);
  }
  
  public servicios.Result obtenerRolesActivador() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerRolesActivador();
  }
  
  public servicios.Result bajaDMS(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaDMS(LOGIN);
  }
  
  public servicios.Result bajaActivador(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaActivador(LOGIN);
  }
  
  public servicios.Result bajaDfp(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaDfp(LOGIN);
  }
  
  public servicios.Result verificarUserSiga(java.lang.String USER) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.verificarUserSiga(USER);
  }
  
  public servicios.Result verificarCuentasCongeladas(java.lang.String CUENTA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.verificarCuentasCongeladas(CUENTA);
  }
  
  public servicios.Result obtenerIdRolSubsidio(java.lang.String ROL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerIdRolSubsidio(ROL);
  }
  
  public servicios.Result ltsLogUpdateCuenta(java.lang.String cuenta) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.ltsLogUpdateCuenta(cuenta);
  }
  
  public servicios.Result actualizarCuentaPreARe(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarCuentaPreARe(CUENTA, MODALIDAD);
  }
  
  public servicios.Result pruebaWS(java.lang.String mseg) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.pruebaWS(mseg);
  }
  
  public servicios.Result obtenerUsuariosAR(java.lang.String arg0) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerUsuariosAR(arg0);
  }
  
  public servicios.Result insertWebSubsidio(java.lang.String LOGIN, java.lang.String NOMBRE, java.lang.String IDDPTO, java.lang.String EMAIL, java.lang.String ROL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertWebSubsidio(LOGIN, NOMBRE, IDDPTO, EMAIL, ROL);
  }
  
  public servicios.Result liberarCuentasReservadas() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.liberarCuentasReservadas();
  }
  
  public servicios.Result cambioEstadoOdeco(java.lang.String SERIE, java.lang.String ESTADO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.cambioEstadoOdeco(SERIE, ESTADO);
  }
  
  public servicios.Result bajaOdeco(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaOdeco(LOGIN);
  }
  
  public servicios.Result insertUsuarioLdapActivador(java.lang.String USERNAME, java.lang.String FULLNAME, java.lang.String EMAIL, java.lang.String PHONE, java.lang.String VENDOR_DOC_NUMBER, java.lang.String ROL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioLdapActivador(USERNAME, FULLNAME, EMAIL, PHONE, VENDOR_DOC_NUMBER, ROL);
  }
  
  public servicios.Result insertUsuarioWIMAX(java.lang.String GRUPO, java.lang.String USUARIO, java.lang.String APELLIDOS, java.lang.String EHUMANO, java.lang.String DEPARTAMENTO, java.lang.String AREA, java.lang.String TIPO_COMPROBANTE) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioWIMAX(GRUPO, USUARIO, APELLIDOS, EHUMANO, DEPARTAMENTO, AREA, TIPO_COMPROBANTE);
  }
  
  public servicios.Result insertLuckyNumbers(java.lang.String PERFIL, java.lang.String EMAIL, java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertLuckyNumbers(PERFIL, EMAIL, LOGIN);
  }
  
  public servicios.Result obtenerRolesSubsidio() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerRolesSubsidio();
  }
  
  public servicios.Result obtenerCuentasPostPagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCuentasPostPagoReservadoBCCS(LOCALIDAD, CANTIDAD);
  }
  
  public servicios.Result obtFechaCuentaBCCS(java.lang.String cuenta) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtFechaCuentaBCCS(cuenta);
  }
  
  public servicios.Result obtenerAllServicios() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerAllServicios();
  }
  
  public servicios.Result migrarAgente(java.lang.String id_Staff_vacacion, java.lang.String id_Staff_Recepcion, int id_Opercacion) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.migrarAgente(id_Staff_vacacion, id_Staff_Recepcion, id_Opercacion);
  }
  
  public servicios.Result findTransAct(java.lang.String cuenta, java.lang.String dias) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.findTransAct(cuenta, dias);
  }
  
  public servicios.Result bajaUsuarioWIMAX(java.lang.String USUARIO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaUsuarioWIMAX(USUARIO);
  }
  
  public servicios.Result nivelacionBodega(int SUCURSAL, int LOCAL, int DEPOSITO, int CODIGO_PROUCTO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.nivelacionBodega(SUCURSAL, LOCAL, DEPOSITO, CODIGO_PROUCTO);
  }
  
  public servicios.Result obtenerRolesDfp() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerRolesDfp();
  }
  
  public servicios.Result actualizarCiAR(java.lang.String ci, java.lang.String id) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarCiAR(ci, id);
  }
  
  public servicios.Result insertUsuarioASBilling(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioASBilling(USUARIO, NOMBRE, AREA);
  }
  
  public servicios.Result liberarPostPagaReservadaAlibre(java.lang.String CUENTA, java.lang.String TEXTO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.liberarPostPagaReservadaAlibre(CUENTA, TEXTO);
  }
  
  public servicios.Result bajaUsuarioAS(java.lang.String USUARIO) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaUsuarioAS(USUARIO);
  }
  
  public servicios.Result actualizarModEnCerCuenta(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarModEnCerCuenta(CUENTA, MODALIDAD);
  }
  
  public servicios.Result obtenerCuentasPrePagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCuentasPrePagoReservadoBCCS(LOCALIDAD, CANTIDAD);
  }
  
  public servicios.Result obtenerAbm(java.lang.String ABM) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerAbm(ABM);
  }
  
  public servicios.Result obtenerComandosCuenta(java.lang.String CUENTA, java.lang.String FECHA) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerComandosCuenta(CUENTA, FECHA);
  }
  
  public servicios.Result insertDms(java.lang.String USUARIO, java.lang.String TELEFONO, java.lang.String NOMBRES, java.lang.String APELLIDOS, java.lang.String EMAIL, java.lang.String PERFIL, java.lang.String VISIBILIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertDms(USUARIO, TELEFONO, NOMBRES, APELLIDOS, EMAIL, PERFIL, VISIBILIDAD);
  }
  
  public servicios.Result consultarDisponibilidad() throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.consultarDisponibilidad();
  }
  
  public servicios.Result insertUsuarioActivador(java.lang.String USERNAME, java.lang.String FULLNAME, java.lang.String EMAIL, java.lang.String PHONE, java.lang.String VENDOR_DOC_NUMBER, java.lang.String ROL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertUsuarioActivador(USERNAME, FULLNAME, EMAIL, PHONE, VENDOR_DOC_NUMBER, ROL);
  }
  
  public servicios.Result actualizarCuentaPreEnTool(java.lang.String CUENTA, java.lang.String LOCALIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.actualizarCuentaPreEnTool(CUENTA, LOCALIDAD);
  }
  
  public servicios.Result obtenerCuentasBCCS(java.lang.String arg0) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCuentasBCCS(arg0);
  }
  
  public servicios.Result obtenerCantidadCuentasPrePagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCantidadCuentasPrePagoReservadoBCCS(LOCALIDAD, CANTIDAD);
  }
  
  public servicios.Result obtenerCantidadCuentasPostPagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.obtenerCantidadCuentasPostPagoReservadoBCCS(LOCALIDAD, CANTIDAD);
  }
  
  public servicios.Result bajaLucky(java.lang.String LOGIN) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.bajaLucky(LOGIN);
  }
  
  public servicios.Result insertDatosFacturacion(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String ROL) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertDatosFacturacion(USUARIO, NOMBRE, ROL);
  }
  
  public servicios.Result insertarCuentaPreAToolCuenta(java.lang.String CUENTA, java.lang.String LOCALIDAD) throws java.rmi.RemoteException{
    if (servicios == null)
      _initServiciosProxy();
    return servicios.insertarCuentaPreAToolCuenta(CUENTA, LOCALIDAD);
  }
  
  
}