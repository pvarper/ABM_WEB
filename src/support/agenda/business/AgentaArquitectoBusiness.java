package support.agenda.business;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import support.agenda.dao.agendaDAO;
import support.agenda.model.AgendaArquitecto;

public class AgentaArquitectoBusiness implements Serializable {
	private static final long serialVersionUID = 1L;
	  @Inject
	  private agendaDAO dao;

public void save(String nombre, String usuario) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
	AgendaArquitecto a=new AgendaArquitecto();
	
	a.setNombre(nombre);
	a.setUsuario(usuario);
	this.dao.Save_Agenda_Arquitecto(a);
}
public static void main (String[] args) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {

	agendaDAO a = new agendaDAO();
	AgendaArquitecto aa=new AgendaArquitecto();
	aa.setId(21);
	aa.setNombre("aaaa");
	aa.setUsuario("bbbb");
	a.Save_Agenda_Arquitecto(aa);
}

}
