package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.TipoDocumentoDAO;
import support.ussd.model.TTipoDocumento;

import com.tigo.utils.ParametersWeb;

@Named
public class ActualizarRespuestaOdecoBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TipoDocumentoDAO dao;

	public String validate(TTipoDocumento user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		if(!user.getDescripcion().isEmpty()){
			if(!user.getDescripcion().trim().matches(ParametersWeb.EXPRESION_REGULAR_TODO)){
				return "El campo Descripcion tiene caracteres especiales";
			}
		}
		
		TTipoDocumento usAux = this.dao.getTipoDocumentoNombre(user.getNombre().trim());
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

	public void save(TTipoDocumento user) throws Exception {
		this.dao.save(user);
	}
	
	public void delete(TTipoDocumento user) throws Exception {
		user.setEstado(false);
		this.dao.update(user);
	}

	public void update(TTipoDocumento user) throws Exception {
		TTipoDocumento userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setDescripcion(user.getDescripcion());
		userAux.setEstado(user.getEstado());
		this.dao.update(userAux);
	}
	

	public List<TTipoDocumento> getList() throws Exception {
		return this.dao.getList();
	}

	public TTipoDocumento getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}


	public TTipoDocumento getTipoDocumentoNombre(String nombre) {
		return this.dao.getTipoDocumentoNombre(nombre);
	}

}
