package support.agenda.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import support.agenda.model.AgendaActividad;
import support.agenda.model.AgendaArquitecto;
import support.user.business.AgendaArquitectoBL;

@ManagedBean
@ViewScoped
public class AgendaArquitectoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(AgendaArquitectoBean.class);
	@Inject
	private AgendaArquitectoBL agendaArquitectoBL;
	private AgendaArquitecto agendaArquitecto;

	public void save() {
		try {
			List<AgendaActividad> list = new ArrayList<AgendaActividad>();
			list.add(new AgendaActividad());
			agendaArquitecto = new AgendaArquitecto();
			agendaArquitecto.setId(1);
			agendaArquitecto.setNombre("Daniel Mendez");
			agendaArquitecto.setUsuario("mendezd");
			agendaArquitecto.setAgendaActividads(list);
			agendaArquitectoBL.saveFormulario(agendaArquitecto);
		} catch (Exception e) {
			log.error("[AgendaArquitectoBean:Save] - "
					+ e.getStackTrace().toString());
		}

	}
}
