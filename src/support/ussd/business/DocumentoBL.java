package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.DocumentoDAO;
import support.ussd.model.TDocumento;

import com.tigo.utils.ParametersWeb;

@Named
public class DocumentoBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private DocumentoDAO dao;

	public String validate(TDocumento user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		if(!user.getDescripcion().isEmpty()){
			if(!user.getDescripcion().trim().matches(ParametersWeb.EXPRESION_REGULAR_TODO)){
				return "El campo Descripcion contiene caracteres especiales";
			}
		}
		
		TDocumento usAux = this.dao.getTDocumentoNombre(user.getNombre().trim());
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

	public void save(TDocumento user) throws Exception {
		this.dao.save(user);
	}
	
	public void delete(TDocumento user) throws Exception {
		user.setEstado(false);
		this.dao.update(user);
	}

	public void update(TDocumento user) throws Exception {
		TDocumento userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setRuta(user.getRuta());
		userAux.setTTipoDocumento(user.getTTipoDocumento());
		userAux.setPlataformaId(user.getPlataformaId());
		userAux.setClave(user.getClave());
		userAux.setDescripcion(user.getDescripcion());
		userAux.setEstado(user.getEstado());
		this.dao.update(userAux);
	}
	

	public List<TDocumento> getList() throws Exception {
		return this.dao.getList();
	}

	public TDocumento getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}


	public TDocumento getTDocumentoNombre(String nombre) {
		return this.dao.getTDocumentoNombre(nombre);
	}

}
