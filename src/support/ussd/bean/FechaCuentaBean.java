package support.ussd.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.RETURN;

import support.id.FormularioID;
import support.user.controler.ControlPrivilegio;
import support.user.model.UpdateCuenta;
import support.user.model.UsrOdeco;
import support.user.sys.IControlPrivilegios;
import support.util.Code;
import support.ws.servicios.ServiciosI;
import servicios.Result;

@ManagedBean
@ViewScoped
public class FechaCuentaBean implements Serializable, IControlPrivilegios {
	ControlPrivilegio controlPrivilegio;
	public static Logger log = Logger.getLogger(FechaCuentaBean.class);

	private static final long serialVersionUID = 1L;
	private static final int ADMINISTRADOR = 1;
	private static final String ADM_ACTIVACIONES = "penaa";
	private static final int ACTIVACIONES = 12;
	private static String pattern = "yyyy-MM-dd";
	private String cuenta;
	private Date fecha;
	private ServiciosI ws;
	private String usrLogin;
	private String usrIdRol;
	private List<UpdateCuenta> ltsUpdateCuenta;
	private UpdateCuenta updateCuenta;
	private Gson gson;

	/* DATOS CUENTA */

	@PostConstruct
	public void init() {
		usrLogin = "";
		usrIdRol = "";
		ws = new ServiciosI();
		this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
		gson = new Gson();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		usrLogin = (String) request.getSession().getAttribute("TEMP$USER_NAME");
		usrIdRol = (String) request.getSession()
				.getAttribute("TEMP$USER_IDROL");

		log.info("USUARIO: " + usrLogin + " ROL_ID:" + usrIdRol);
	}

	public UpdateCuenta getUpdateCuenta() {
		if (updateCuenta == null) {
			updateCuenta = new UpdateCuenta();
		}
		return updateCuenta;
	}

	public void setUpdateCuenta(UpdateCuenta updateCuenta) {
		this.updateCuenta = updateCuenta;
	}

	@Override
	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(
				FormularioID.UPDATEFECHANTIGUEDAD, idAccion);
	}

	public void update() throws ParseException, RemoteException,
			MalformedURLException {
		/* FORMATO DE FECHA */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String newFecha = simpleDateFormat.format(getFecha());
		Result respuesta;
		String oldFecha = ws.obtFechaAntiguedadCuenta(cuenta);
		/* VERIFICANDO SI ES ADMINISTRADOR */
		if (validarAdmin(Integer.parseInt(usrIdRol), usrLogin)) {
			log.info("INGRESANDO COMO ADMIN");
			respuesta = ws.updateFechaCuentaBccs(newFecha, cuenta);
			log.info("RESPUESTA_UPDATE: " + respuesta.getDescription());
			if (respuesta.getCode().equals(Code.OK)) {
				ws.logFechaCuenta(usrLogin, cuenta, newFecha, oldFecha);
				addMessageInfo("Se actualizo correctamente la fecha de la cuenta: "
						+ cuenta);
			} else {
				addMessageWarning("ERROR: No se puedo actualizar la fecha de la cuenta:"
						+ cuenta);
			}
		} else if (ACTIVACIONES == Integer.parseInt(usrIdRol)) {
			respuesta = ws.finTransAR(cuenta, "7");
			if (respuesta.getCode().contains(Code.OK)) {
				respuesta = ws.updateFechaCuentaBccs(newFecha, cuenta);
				log.info("RESPUESTA_UPDATE: " + respuesta.getDescription());
				if (respuesta.getCode().equals(Code.OK)) {
					ws.logFechaCuenta(usrLogin, cuenta, newFecha, oldFecha);
					addMessageInfo("Se actualizo correctamente la fecha de la cuenta: "
							+ cuenta);
				} else {
					addMessageWarning("ERROR: No se puedo actualizar la fecha de la cuenta:"
							+ cuenta);
				}
				setCuenta("");
				setFecha(null);
			} else {
				addMessageWarning(respuesta.getDescription());
			}
		} else {
			log.info("El usuario no esta autorizado para realizar esta accion");
			addMessageWarning("El usuario no esta autorizado para realizar esta accion");
		}
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	private boolean validarAdmin(int idRol, String login) {
		/* ID ADALBERTO PEÑA SU ROL=12 QUEDA SETEADO SU USER */
		log.info("ROL:" + idRol + " usuario:" + login);
		if ((ADMINISTRADOR == idRol)
				|| (ADM_ACTIVACIONES.equalsIgnoreCase(login.trim()))) {
			log.info("ADMIN, ADMIN_ACTIVACION:" + true);
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}

	}

	public void addMessageInfo(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void addMessageWarning(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public List<UpdateCuenta> getLtsUpdateCuenta() throws JsonSyntaxException,
			MalformedURLException, RemoteException {
		ServiciosI ser = new ServiciosI();
		ltsUpdateCuenta = new ArrayList<UpdateCuenta>();
		Type type = new TypeToken<List<UpdateCuenta>>() {
		}.getType();
		ltsUpdateCuenta = gson.fromJson((String) ser.ltsLogUpdateCuenta()
				.getData(), type);
		return ltsUpdateCuenta;
	}

	public void setLtsUpdateCuenta(List<UpdateCuenta> ltsUpdateCuenta) {

		this.ltsUpdateCuenta = ltsUpdateCuenta;
	}

}
