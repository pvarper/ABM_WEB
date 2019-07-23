package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.TermianlesDAO;
import support.ussd.model.TTerminales;

@Named
public class TerminalesBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TermianlesDAO dao;

	public String validate(TTerminales user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		
		TTerminales usAux = this.dao.getTerminalNombre(user.getNombre().trim());
		if (usAux == null) {
			return "";
		}
		if ((idStr != null) && (!idStr.isEmpty())) {
			int id = Integer.parseInt(idStr);
			if ((id == usAux.getId().longValue())
					&& (user.getNombre().trim().equalsIgnoreCase(usAux.getNombre()))) {
				return "";
			}
		}
		return "El campo Nombre existe";
	}

	public void save(TTerminales user) throws Exception {
		this.dao.save(user);
	}
	
	public void delete(TTerminales user) throws Exception {
		user.setEstado(false);
		this.dao.update(user);
	}

	public void update(TTerminales user) throws Exception {
		TTerminales userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setIp(user.getIp());
		userAux.setEstado(user.getEstado());
		this.dao.update(userAux);
	}
	

	public List<TTerminales> getList() throws Exception {
		return this.dao.getList();
	}

	public TTerminales getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}


	public TTerminales getTerminalNombre(String nombre) {
		return this.dao.getTerminalNombre(nombre);
	}

}
