package support.ussd.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tigo.utils.SysMessage;

import servicios.Result;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.model.UsrOdeco;
import support.util.Code;
import support.ws.servicios.ServiciosI;

@ManagedBean
@ViewScoped
public class MigrarAgenteOdecoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MigrarAgenteOdecoBean.class);

	ControlPrivilegio controlPrivilegio;

	@Inject
	private ControlerBitacora controlerBitacora;

	private String id_Staff_vacacion;
	private String id_Staff_recepcion;
	private String id_operacion;
	private List<UsrOdeco> ltsVacacion;
	private List<UsrOdeco> ltsVacacion1;
	private List<UsrOdeco> ltsUsuarioOdeco;;
	private boolean bandera;
	private String msjConfir;

	Gson gson = new Gson();

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			getLtsVacacion1();
			getBandera();
			getLtsUsuarioOdeco();

		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	public void realizarOperacion() {
		log.info("iniciando Operacion");
		try {
			if ((id_operacion != null) && (!id_operacion.isEmpty())) {
				if ((id_Staff_vacacion != null)
						&& (!id_Staff_vacacion.isEmpty())) {
					if (id_operacion.contains("1")) {
						ServiciosI ws = new ServiciosI();
						Result res = ws.migrarAgente(id_Staff_vacacion,
								id_Staff_recepcion,
								Integer.parseInt(id_operacion));
						if (!res.getCode().equalsIgnoreCase(Code.OK)) {
							SysMessage.error(res.getDescription());
						} else {
							SysMessage.info(res.getDescription());
						}
					} else {

						if ((id_Staff_recepcion != null)
								&& (!id_Staff_recepcion.isEmpty())) {
							ServiciosI ws = new ServiciosI();
							Result res = ws.migrarAgente(id_Staff_vacacion,
									id_Staff_recepcion,
									Integer.parseInt(id_operacion));
							if (!res.getCode().equalsIgnoreCase(Code.OK)) {
								SysMessage.error(res.getDescription());
							} else {
								SysMessage.info(res.getDescription());
							}
						} else {
							SysMessage.info("Seleccione un STAFF recepción");
						}
					}
				} else {
					SysMessage.info("Seleccione un STAFF vacación");
				}
			} else {
				SysMessage.info("Seleccione una Operación");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(
					"[realizarOperacion] Notificacion : error al realizar la operacion de migracion o restauracion",
					e);
		}

	}

	public void cambiarBander() {
		if (id_operacion.contains("1")) {
			bandera = Boolean.TRUE;
			msjConfir = "Esta seguro que desea migrar los agentes";
		} else {
			bandera = Boolean.FALSE;
			msjConfir = "Esta seguro que desea restaurar los agentes";
		}
		log.info("BANDERA DEL COMBO BOX: " + bandera + " ID_OPERTION: "
				+ id_operacion);
	}

	public String getMsjConfir() {
		msjConfir = "Hola mundo";
		return msjConfir;
	}

	public void setMsjConfir(String msjConfir) {
		this.msjConfir = msjConfir;
	}

	public Boolean getBandera() {
		bandera = Boolean.FALSE;
		return bandera;
	}

	public void setBandera(Boolean bandera) {
		this.bandera = bandera;

	}

	public String getId_Staff_vacacion() {
		return id_Staff_vacacion;
	}

	public void setId_Staff_vacacion(String id_Staff_vacacion) {
		this.id_Staff_vacacion = id_Staff_vacacion;
	}

	public String getId_Operacion() {
		return id_operacion;
	}

	public void setId_Operacion(String id_Operacion) {
		this.id_operacion = id_Operacion;
	}

	public String getId_Staff_recepcion() {
		return id_Staff_recepcion;
	}

	public void setId_Staff_recepcion(String id_Staff_recepcion) {
		this.id_Staff_recepcion = id_Staff_recepcion;
	}

	public List<UsrOdeco> getLtsVacacion() {
		ltsVacacion = getLtsStaff();
		return ltsVacacion;
	}

	public List<UsrOdeco> getLtsStaff() {
		try {
			ServiciosI ser = new ServiciosI();
			ltsVacacion = new ArrayList<UsrOdeco>();
			Type type = new TypeToken<List<UsrOdeco>>() {
			}.getType();
			ltsVacacion = gson.fromJson((String) ser.obtenerCoordinadorOdeco()
					.getData(), type);
		} catch (Exception e) {
			log.error("[getLtsVacacion] error al obtener los Staff", e);
		}
		return ltsVacacion;
	}

	public void setLtsVacacion(List<UsrOdeco> ltsVacacion) {
		this.ltsVacacion = ltsVacacion;
	}

	public List<UsrOdeco> cargarLista() {
		log.info("Cargar nueva Lista");
		List<UsrOdeco> ltsVacacionAux = new ArrayList<UsrOdeco>();
		if (id_Staff_vacacion != null) {
			List<UsrOdeco> lista = getLtsVacacion();
			for (UsrOdeco usrOdeco : lista) {
				if (!usrOdeco.getIdUsuario().contains(id_Staff_vacacion)) {
					ltsVacacionAux.add(usrOdeco);
				}
			}
			ltsVacacion1 = ltsVacacionAux;
		}
		return ltsVacacionAux;
	}

	public List<UsrOdeco> getLtsVacacion1() {
		if (ltsVacacion1 == null) {
			ltsVacacion1 = new ArrayList<UsrOdeco>();
		}
		return ltsVacacion1;
	}

	public void setLtsVacacion1(List<UsrOdeco> ltsVacacion1) {
		this.ltsVacacion1 = ltsVacacion1;
	}

	/* ltsUsuarioOdeco */
	public List<UsrOdeco> getLtsUsuarioOdeco() {
		try {
			ServiciosI ser = new ServiciosI();
			ltsUsuarioOdeco = new ArrayList<UsrOdeco>();
			Type type = new TypeToken<List<UsrOdeco>>() {
			}.getType();
			ltsUsuarioOdeco = gson.fromJson((String) ser
					.obtenerListaAgenteOdeco().getData(), type);

		} catch (Exception e) {
			log.error("[getLtsVacacion] error al obtener los Staff", e);
		}
		return ltsUsuarioOdeco;
	}

	public void setLtsUsuarioOdeco(List<UsrOdeco> ltsUsuarioOdeco) {
		this.ltsUsuarioOdeco = ltsUsuarioOdeco;
	}
}
