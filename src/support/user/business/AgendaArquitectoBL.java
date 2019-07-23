package support.user.business;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import support.agenda.bean.AgendaArquitectoBean;
import support.agenda.dao.AgendaArquitectoDAO;
import support.agenda.model.AgendaArquitecto;

@Named
public class AgendaArquitectoBL implements Serializable {
	public static Logger log = Logger.getLogger(AgendaArquitectoBL.class);
	private static final long serialVersionUID = 1L;

	@Inject
	AgendaArquitectoDAO agendaArquitectoDAO;

	public void saveFormulario(AgendaArquitecto agendaArquitecto)
			throws Exception {
		this.agendaArquitectoDAO.save(agendaArquitecto);
	}
}
