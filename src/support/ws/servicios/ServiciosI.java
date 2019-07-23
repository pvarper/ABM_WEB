package support.ws.servicios;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.commons.beanutils.converters.CalendarConverter;
import org.primefaces.component.log.Log;

import com.google.gson.Gson;
import com.stefanini.activator.ws.dbupdate.*;

import servicios.Result;
import servicios.ServicioInsert;
import servicios.ServicioInsertLocator;
import servicios.ServicioInsertSoapBindingStub;
import servicios.Servicios;
import support.ussd.model.Cuentas;
import support.util.Code;

public class ServiciosI {
	private static final Gson gson = new Gson();

	public Result insertWebSubsidio(String login, String nombre, String iddpto,
			String email, String rol) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertWebSubsidio(login, nombre, iddpto, email, rol);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertLucky(String perfil, String email, String login)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertLuckyNumbers(perfil, email, login);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertDfp(String usuario, String nombre, String rol)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertDatosFacturacion(usuario, nombre, rol);

		System.out.println("code: " + res.getCode() + ", Des: "
				+ res.getDescription() + ", data: " + res.getData());
		return res;
	}

	public Result insertActivador(String username, String nombre, String email,
			String phone, String ci, String rol) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioActivador(username, nombre, email, phone,
				ci, rol);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertActivadorLDAP(String username, String nombre,
			String email, String phone, String ci, String rol)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioLdapActivador(username, nombre, email,
				phone, ci, rol);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result anular(String comprobante, String serie, String numero)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.anulacionCteBlanco(comprobante, serie,
				Integer.valueOf(numero));

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result cambioEstado(String comprobante, String serie, String numero)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws
				.cambioPC_CA(comprobante, serie, Integer.valueOf(numero));

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result cambioFecha(String comprobante, String serie, String numero,
			String fechaIni, String fechaFin) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.cambioFecha(comprobante, serie,
				Integer.valueOf(numero), fechaIni, fechaFin);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result cambioEstadoOdeco(String serie, String radio)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.cambioEstadoOdeco(serie, radio);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result volverFecha(String comprobante, String serie, String numero,
			String fecha) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.volverFecha(comprobante, serie,
				Integer.valueOf(numero), fecha);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerComandosCuenta(String cuenta, String fecha)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerComandosCuenta(cuenta, fecha);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result nivelacionBodega(int sucursal, int local, int deposito,
			int codigoProducto) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.nivelacionBodega(sucursal, local, deposito,
				codigoProducto);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertBCCS(String grupo, String usuario, String nombre,
			String ehumano, String dpto, String area, String tipoComp)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioBCCS(grupo, usuario, nombre, ehumano,
				dpto, area, tipoComp);
		// Result res= new Result();
		// res.setCode(Code.OK);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertWIMAX(String grupo, String usuario, String nombre,
			String ehumano, String dpto, String area, String tipoComp)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioWIMAX(grupo, usuario, nombre, ehumano,
				dpto, area, tipoComp);
		// Result res= new Result();
		// res.setCode(Code.OK);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerRolesSubsidio() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerRolesSubsidio();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCiudadesSubsidio() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCiudadesSubsidio();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerIdDptoSubsidio(String ciudad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerIdDptoSubsidio(ciudad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerIdRolSubsidio(String ciudad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerIdRolSubsidio(ciudad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerRolesLucky() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerRolesLucky();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerRolesDfp() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerRolesDfp();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerRolesActivador() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerRolesActivador();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerPerfilesBCCS() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerPerfilesBCCS();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerLocalidadesBCCS() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.obtenerLocalidadesBCCS();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCuentasCongeladasBCCS(String localidad,
			String modalidad, Integer cant) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws
				.obtenerCuentasCongeladasBCCS(localidad, modalidad, cant);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCuentasBCCS(String localidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCuentasBCCS(localidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result liberarCuentasReservadas() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.liberarCuentasReservadas();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCuentasPostPagoReservadoBCCS(String localidad,
			Integer cant) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCuentasPostPagoReservadoBCCS(localidad, cant);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCantidadCuentasPostPagoReservadoBCCS(String localidad,
			Integer cant) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCantidadCuentasPostPagoReservadoBCCS(localidad,
				cant);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCantidadCuentasPrePagoReservadoBCCS(String localidad,
			Integer cant) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCantidadCuentasPrePagoReservadoBCCS(localidad,
				cant);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result consultarDisponibilidad() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.consultarDisponibilidad();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerCuentasPrePagoReservadoBCCS(String localidad,
			Integer cant) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerCuentasPrePagoReservadoBCCS(localidad, cant);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result liberarPostPagaReservadaAlibre(String cuenta, String text)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.liberarPostPagaReservadaAlibre(cuenta, text);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result actualizarPREaPOSReservada(String cuenta, String texto)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.actualizarPREaPOSReservada(cuenta, texto);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result verificarCuentasCongeladas(String cuenta)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.verificarCuentasCongeladas(cuenta);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result verificarCuentaEnToolCuenta(String cuenta)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.verificarCuentaEnToolCuenta(cuenta);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result actualizarCuentaPosALI(String cuenta, String modalidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.actualizarCuentaPosALI(cuenta, modalidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result actualizarCuentaPreARe(String cuenta, String modalidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.actualizarCuentaPreARe(cuenta, modalidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result actualizarCuentaPreEnTool(String cuenta, String localidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.actualizarCuentaPreEnTool(cuenta, localidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result deleteCuentaEnTool(String cuenta)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.deleteCuentaEnTool(cuenta);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result actualizarModEnCerCuenta(String cuenta, String modalidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.actualizarModEnCerCuenta(cuenta, modalidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertarCuentaPreAToolCuenta(String cuenta, String localidad)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertarCuentaPreAToolCuenta(cuenta, localidad);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	// public Result actualizarCuentaPos(String cuenta) throws
	// MalformedURLException, RemoteException{
	// ServicioInsert s= new ServicioInsertLocator();
	// Servicios ws= new ServicioInsertSoapBindingStub(new
	// URL(s.getServiciosPortAddress()), s);
	//
	// Result res=ws.actualizarCuentaPos(cuenta);
	// //System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
	// return res;
	// }

	public Result insertASGenerico(String usuario, String nombre, String area)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioASGenerico(usuario, nombre, area);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertASBilling(String usuario, String nombre, String area)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioASBilling(usuario, nombre, area);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result insertASApp(String usuario, String nombre, String area)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.insertUsuarioASApp(usuario, nombre, area);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaWebSubsidio(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaWebSubsidio(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaLucky(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaLucky(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaDfp(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaDfp(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaCrm(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaCrm(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaActivador(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaActivador(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaAs(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaUsuarioAS(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaBccs(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaUsuarioBCCS(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result bajaWimax(String usuario) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.bajaUsuarioWIMAX(usuario);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerAbm(String abm) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerAbm(abm);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerServiciosAbm(String abm) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerServiciosAbm(abm);
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result obtenerAllServicios() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.obtenerAllServicios();
		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result migrarAgente(String id_Staff_vacacion,
			String id_Staff_Recepcion, int id_Opercacion)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.migrarAgente(id_Staff_vacacion, id_Staff_Recepcion,
				id_Opercacion);

		return res;
	}

	public Result obtenerCoordinadorOdeco() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.obtenerCoordinadorOdeco(Code.STAFF);
		return res;
	}

	public Result obtenerListaAgenteOdeco() throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.obtenerCoordinadorOdeco(Code.AGENTE);
		return res;
	}

	public Result verificarUserSiga(String user) throws MalformedURLException,
			RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);

		Result res = ws.verificarUserSiga(user);

		// System.out.println("code: "+res.getCode()+", Des: "+res.getDescription()+", data: "+res.getData());
		return res;
	}

	public Result updateFechaCuentaBccs(String fecha_antiguedad,
			String nroCuenta) throws RemoteException {
		String msjUpdate = "";
		Result respuesta = new Result();
		try {
			System.out.print("UPDATE FECHA: " + fecha_antiguedad + "\n");
			DateUpdateService s = new DateUpdateServiceLocator();
			DateUpdate ws = new DateUpdatePortBindingStub(new URL(
					s.getDateUpdatePortAddress()), s);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(fecha_antiguedad);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			System.out.print("FECHA: " + cal + "\n");
			msjUpdate = ws.updateDate(nroCuenta, cal);
			if (msjUpdate.contains("successfully")) {
				respuesta.setCode(Code.OK);
			} else {
				respuesta.setCode(Code.WARNING);
				respuesta
						.setDescription("Error al intentar actualizar la Fecha de Antiguedad");
			}
		} catch (Exception e) {
			System.out.print("ERROR:" + e.getMessage());
		}
		return respuesta;
	}

	public Result finTransAR(String cuenta, String dias)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.findTransAct(cuenta, dias);
		return res;
	}

	public Result logFechaCuenta(String user, String cuenta, String newFecha,
			String oldFecha) throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.logFechaCuenta(user, cuenta, newFecha, oldFecha);
		return res;
	}

	public String obtFechaAntiguedadCuenta(String cuenta)
			throws MalformedURLException, RemoteException {
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		Result res = ws.obtFechaCuentaBCCS(cuenta);
		return res.getDescription();
	}

	public Result ltsLogUpdateCuenta() throws MalformedURLException,
			RemoteException {
		Result respuesta = new Result();
		ServicioInsert s = new ServicioInsertLocator();
		Servicios ws = new ServicioInsertSoapBindingStub(new URL(
				s.getServiciosPortAddress()), s);
		respuesta = ws.ltsLogUpdateCuenta(null);
		return respuesta;
	}
}
