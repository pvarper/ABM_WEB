package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.user.model.MuUsuario;
import support.ussd.dao.ServiciosDAO;
import support.ussd.model.TSerAdm;
import support.ussd.model.TServicio;

import com.tigo.utils.ParametersWeb;

@Named
public class ServiciosBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ServiciosDAO dao;

	public String validate(TServicio user, String idStr) {

		if(user.getPwd()==null){
				return "El campo password es obligatorio";
		}else{
			if(!user.getPwd().trim().matches(ParametersWeb.EXPRESION_REGULAR_TODO)){
				return "El campo pwd tiene caracteres especiales";
			}
		}
		
		if(user.getObservacion()!=null){
			if(!user.getObservacion().trim().isEmpty()){
				if(!user.getObservacion().trim().matches(ParametersWeb.EXPRESION_REGULAR_TODO)){
					return "El campo Observacion tiene caracteres especiales";
				}
			}
			
		}
		
		return "";
	}

	public void save(TServicio user) throws Exception {
		this.dao.save(user);
	}
	
	public void delete(TServicio user) throws Exception {
		user.setEstado(false);
		this.dao.update(user,null);
	}

	public void update(TServicio user,List<MuUsuario> lista) throws Exception {
		TServicio userAux = this.dao.get(user.getId());
		userAux.setIdServicio(user.getIdServicio());
		userAux.setDescripcion(user.getDescripcion());
		//userAux.setTRols(user.getTRols());
		//userAux.setTAtributos(user.getTAtributos());
		userAux.setEstado(user.getEstado());
		userAux.setTipoServicio(user.getTipoServicio());
		userAux.setTipoUsuario(user.getTipoUsuario());
		userAux.setPwd((user.getPwd()==null)?null:user.getPwd().trim());
		userAux.setObservacion(user.getObservacion());
		userAux.setTSerAdms(user.getTSerAdms());
		this.dao.update(userAux,lista);
	}
	

	public List<TServicio> getList() throws Exception {
		return this.dao.getList();
	}
	
	public List<TServicio> getListServicioPorAdmin(Long a) throws Exception {
		return this.dao.getListServicioPorAdmin(a);
	}
	
	public List<TSerAdm> getListAdmPorServicio(long id) throws Exception {
		return this.dao.getListAdmPorServicio(id);
	}

	public TServicio getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	public TServicio getServicio(Long idUser) throws Exception {
		return this.dao.getServicio(idUser);
	}
	
	public TServicio getServicioABM(long idUser) throws Exception {
		return this.dao.getServicioABM(idUser);
	}

	public TServicio geTServicioNombre(String nombre) {
		return this.dao.geTServicioNombre(nombre);
	}

}
