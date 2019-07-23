package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.AreaDAO;
import support.ussd.model.TArea;

import com.tigo.utils.ParametersWeb;

@Named
public class AreaBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AreaDAO dao;

	public String validate(TArea user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		if(!user.getDescripcion().isEmpty()){
			if(!user.getDescripcion().trim().matches(ParametersWeb.ER_LISTA_COMPROBANTES)){
				return "El campo Comprobantes no cuple el formato ej: CNV,CFP";
			}
		}
		
		TArea usAux = this.dao.geTAreaNombre(user.getNombre().trim());
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
	
	public String validatePla(TArea user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		
		
		TArea usAux = this.dao.geTAreaNombre(user.getNombre().trim());
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

	public void save(TArea user) throws Exception {
		this.dao.save(user);
	}
	
	public void delete(TArea user) throws Exception {
		user.setEstado(false);
		this.dao.update(user);
	}

	public void update(TArea user) throws Exception {
		TArea userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setDescripcion(user.getDescripcion());
		userAux.setEstado(user.getEstado());
		this.dao.update(userAux);
	}
	

	public List<TArea> getListArea() throws Exception {
		return this.dao.getListArea();
	}
	
	public List<TArea> getList() throws Exception {
		return this.dao.getList();
	}

	public TArea getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}


	public TArea geTAreaNombre(String nombre) {
		return this.dao.geTAreaNombre(nombre);
	}

}
