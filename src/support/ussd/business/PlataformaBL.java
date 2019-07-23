package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import support.ussd.dao.PlataformaDAO;
import support.ussd.model.TPlataforma;

import com.tigo.utils.ParametersWeb;

@Named
public class PlataformaBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PlataformaDAO dao;

	public String validate(TPlataforma user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
		if(!user.getDescripcion().isEmpty()){
			if(!user.getDescripcion().trim().matches(ParametersWeb.TEXTO_COMUN)){
				return "El campo Descripcion contiene caracteres especiales";
			}
		}
		
		TPlataforma usAux = this.dao.geTPlataformaNombre(user.getNombre().trim(),user.getUso());
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

	public void save(TPlataforma user) throws Exception {
		this.dao.save(user);
	}
	
	public void savePlaAdm(TPlataforma user,DualListModel<String> selectItemsUsuario) throws Exception {
		this.dao.savePlaAdm(user, selectItemsUsuario);
	}
	
//	public void savePlatAdm(TPlataforma user,List<TPlataformaAdministrador> dato2) throws Exception {
//		this.dao.savePlatAdm(user,dato2);
//	}
	
	public void delete(TPlataforma user) throws Exception {
		user.setEstado(false);
//		List<TRol> lista=getListRolPlataforma(user.getId());
//		for (TRol t : lista) {
//			t.setEstado(false);
//		}
//		List<TAtributo> listaA=getListAtributoPlataforma(user.getId());
//		for (TAtributo t : listaA) {
//			t.setEstado(false);
//		}
//		user.setTRols(lista);
		//user.setTAtributos(listaA);
		this.dao.update(user);
	}

	public void update(TPlataforma user) throws Exception {
		TPlataforma userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setDescripcion(user.getDescripcion());
		userAux.setEstado(user.getEstado());
		userAux.setUso(user.getUso());
		userAux.setPlaArea(user.getPlaArea());
		userAux.setPlataforma(user.getPlataforma());
		this.dao.update(userAux);
	}
	

	public List<TPlataforma> getList() throws Exception {
		return this.dao.getList();
	}
	
	
	public List<TPlataforma> getListAltas() throws Exception {
		return this.dao.getListAltas();
	}
	public List<TPlataforma> getListBajas() throws Exception {
		return this.dao.getListBajas();
	}
	
	public List<TPlataforma> getListDocumentos() throws Exception {
		return this.dao.getListDocumentos();
	}

	public TPlataforma getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	public TPlataforma getPlataforma(Long idUser) throws Exception {
		return this.dao.getPlataforma(idUser);
	}
//	public List<TRol> getListRolPlataforma(Long idPlataforma)throws Exception{
//		return dao.getListRolPlataforma(idPlataforma);
//	}
	
//	public List<TAtributo> getListAtributoPlataforma(Long idPlataforma)throws Exception{
//		return dao.getListAtributoPlataforma(idPlataforma);
//	}
	
	
//	public TRol getRol(Long idrol) throws Exception {
//		return this.dao.getRol(idrol);
//	}

	public TPlataforma geTPlataformaNombre(String nombre,String uso) {
		return this.dao.geTPlataformaNombre(nombre,uso);
	}

}
