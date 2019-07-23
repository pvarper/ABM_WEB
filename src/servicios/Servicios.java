/**
 * Servicios.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package servicios;

public interface Servicios extends java.rmi.Remote {
    public servicios.Result obtenerUsuarioBuscado(java.lang.String arg0) throws java.rmi.RemoteException;
    public servicios.Result bajaWebConsultas(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result actualizarCuentaPosALI(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException;
    public servicios.Result bajaCrm(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result logFechaCuenta(java.lang.String user, java.lang.String cuenta, java.lang.String newFecha, java.lang.String oldFecha) throws java.rmi.RemoteException;
    public servicios.Result deleteCuentaEnTool(java.lang.String CUENTA) throws java.rmi.RemoteException;
    public servicios.Result actualizarPREaPOSReservada(java.lang.String CUENTA, java.lang.String TEXTO) throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioASGenerico(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException;
    public servicios.Result obtenerRolesLucky() throws java.rmi.RemoteException;
    public servicios.Result volverFecha(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO, java.lang.String FECHA) throws java.rmi.RemoteException;
    public servicios.Result cambioFecha(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO, java.lang.String FECHA_INICIAL, java.lang.String FECHA_FINAL) throws java.rmi.RemoteException;
    public servicios.Result verificarCuentaEnToolCuenta(java.lang.String CUENTA) throws java.rmi.RemoteException;
    public servicios.Result anulacionCteBlanco(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO) throws java.rmi.RemoteException;
    public servicios.Result obtenerServiciosAbm(java.lang.String ABM) throws java.rmi.RemoteException;
    public servicios.Result obtenerPerfilesBCCS() throws java.rmi.RemoteException;
    public servicios.Result obtenerIdDptoSubsidio(java.lang.String CIUDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerLocalidadesBCCS() throws java.rmi.RemoteException;
    public servicios.Result cambioPC_CA(java.lang.String COMPROBANTE, java.lang.String SERIE, int NRO) throws java.rmi.RemoteException;
    public servicios.Result bajaWebSubsidio(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result obtenerCiudadesSubsidio() throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioBCCS(java.lang.String GRUPO, java.lang.String USUARIO, java.lang.String APELLIDOS, java.lang.String EHUMANO, java.lang.String DEPARTAMENTO, java.lang.String AREA, java.lang.String TIPO_COMPROBANTE) throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioASApp(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException;
    public servicios.Result bajaUsuarioBCCS(java.lang.String USUARIO) throws java.rmi.RemoteException;
    public servicios.Result obtenerCoordinadorOdeco(java.lang.String rol_id) throws java.rmi.RemoteException;
    public servicios.Result obtenerCuentasCongeladasBCCS(java.lang.String LOCALIDAD, java.lang.String MODALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerRolesActivador() throws java.rmi.RemoteException;
    public servicios.Result bajaDMS(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result bajaActivador(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result bajaDfp(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result verificarUserSiga(java.lang.String USER) throws java.rmi.RemoteException;
    public servicios.Result verificarCuentasCongeladas(java.lang.String CUENTA) throws java.rmi.RemoteException;
    public servicios.Result obtenerIdRolSubsidio(java.lang.String ROL) throws java.rmi.RemoteException;
    public servicios.Result ltsLogUpdateCuenta(java.lang.String cuenta) throws java.rmi.RemoteException;
    public servicios.Result actualizarCuentaPreARe(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException;
    public servicios.Result pruebaWS(java.lang.String mseg) throws java.rmi.RemoteException;
    public servicios.Result obtenerUsuariosAR(java.lang.String arg0) throws java.rmi.RemoteException;
    public servicios.Result insertWebSubsidio(java.lang.String LOGIN, java.lang.String NOMBRE, java.lang.String IDDPTO, java.lang.String EMAIL, java.lang.String ROL) throws java.rmi.RemoteException;
    public servicios.Result liberarCuentasReservadas() throws java.rmi.RemoteException;
    public servicios.Result cambioEstadoOdeco(java.lang.String SERIE, java.lang.String ESTADO) throws java.rmi.RemoteException;
    public servicios.Result bajaOdeco(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioLdapActivador(java.lang.String USERNAME, java.lang.String FULLNAME, java.lang.String EMAIL, java.lang.String PHONE, java.lang.String VENDOR_DOC_NUMBER, java.lang.String ROL) throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioWIMAX(java.lang.String GRUPO, java.lang.String USUARIO, java.lang.String APELLIDOS, java.lang.String EHUMANO, java.lang.String DEPARTAMENTO, java.lang.String AREA, java.lang.String TIPO_COMPROBANTE) throws java.rmi.RemoteException;
    public servicios.Result insertLuckyNumbers(java.lang.String PERFIL, java.lang.String EMAIL, java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result obtenerRolesSubsidio() throws java.rmi.RemoteException;
    public servicios.Result obtenerCuentasPostPagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtFechaCuentaBCCS(java.lang.String cuenta) throws java.rmi.RemoteException;
    public servicios.Result obtenerAllServicios() throws java.rmi.RemoteException;
    public servicios.Result migrarAgente(java.lang.String id_Staff_vacacion, java.lang.String id_Staff_Recepcion, int id_Opercacion) throws java.rmi.RemoteException;
    public servicios.Result findTransAct(java.lang.String cuenta, java.lang.String dias) throws java.rmi.RemoteException;
    public servicios.Result bajaUsuarioWIMAX(java.lang.String USUARIO) throws java.rmi.RemoteException;
    public servicios.Result nivelacionBodega(int SUCURSAL, int LOCAL, int DEPOSITO, int CODIGO_PROUCTO) throws java.rmi.RemoteException;
    public servicios.Result obtenerRolesDfp() throws java.rmi.RemoteException;
    public servicios.Result actualizarCiAR(java.lang.String ci, java.lang.String id) throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioASBilling(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String AREA) throws java.rmi.RemoteException;
    public servicios.Result liberarPostPagaReservadaAlibre(java.lang.String CUENTA, java.lang.String TEXTO) throws java.rmi.RemoteException;
    public servicios.Result bajaUsuarioAS(java.lang.String USUARIO) throws java.rmi.RemoteException;
    public servicios.Result actualizarModEnCerCuenta(java.lang.String CUENTA, java.lang.String MODALIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerCuentasPrePagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerAbm(java.lang.String ABM) throws java.rmi.RemoteException;
    public servicios.Result obtenerComandosCuenta(java.lang.String CUENTA, java.lang.String FECHA) throws java.rmi.RemoteException;
    public servicios.Result insertDms(java.lang.String USUARIO, java.lang.String TELEFONO, java.lang.String NOMBRES, java.lang.String APELLIDOS, java.lang.String EMAIL, java.lang.String PERFIL, java.lang.String VISIBILIDAD) throws java.rmi.RemoteException;
    public servicios.Result consultarDisponibilidad() throws java.rmi.RemoteException;
    public servicios.Result insertUsuarioActivador(java.lang.String USERNAME, java.lang.String FULLNAME, java.lang.String EMAIL, java.lang.String PHONE, java.lang.String VENDOR_DOC_NUMBER, java.lang.String ROL) throws java.rmi.RemoteException;
    public servicios.Result actualizarCuentaPreEnTool(java.lang.String CUENTA, java.lang.String LOCALIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerCuentasBCCS(java.lang.String arg0) throws java.rmi.RemoteException;
    public servicios.Result obtenerCantidadCuentasPrePagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException;
    public servicios.Result obtenerCantidadCuentasPostPagoReservadoBCCS(java.lang.String LOCALIDAD, java.lang.Integer CANTIDAD) throws java.rmi.RemoteException;
    public servicios.Result bajaLucky(java.lang.String LOGIN) throws java.rmi.RemoteException;
    public servicios.Result insertDatosFacturacion(java.lang.String USUARIO, java.lang.String NOMBRE, java.lang.String ROL) throws java.rmi.RemoteException;
    public servicios.Result insertarCuentaPreAToolCuenta(java.lang.String CUENTA, java.lang.String LOCALIDAD) throws java.rmi.RemoteException;
}
