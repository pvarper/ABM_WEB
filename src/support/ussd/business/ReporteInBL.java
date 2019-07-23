package support.ussd.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.ReporteInDAO;
import support.ussd.model.TAntecedente;
import support.ussd.model.TAppinvolucrada;
import support.ussd.model.TCronologia;
import support.ussd.model.TDato;
import support.ussd.model.TParticipante;
import support.ussd.model.TPlanaccion;
import support.ussd.model.TPuntosmejora;
import support.ussd.model.TReporte;

@Named
public class ReporteInBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ReporteInDAO dao;

	public String validate(TReporte user, String idStr) {
		if (user.getNombre().trim().isEmpty()) {
			return "El campo Nombre esta Vacio";
		}
//		if(!user.getDescripcion().isEmpty()){
//			if(!user.getDescripcion().trim().matches(ParametersWeb.TEXTO_COMUN)){
//				return "El campo Descripcion contiene caracteres especiales";
//			}
//		}
		
		TReporte usAux = this.dao.getTReporteNombre(user.getNombre().trim());
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

	public void save(TReporte user,TDato datos,List<TAppinvolucrada> app,List<TParticipante> part,List<TAntecedente> ant,
			List<TCronologia> cro,List<TPlanaccion> pla, List<TPuntosmejora> punt) throws Exception {
		this.dao.save(user, datos, app, part, ant, cro, pla, punt);
	}
	
	public void delete(TReporte user) throws Exception {
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

	public void update(TReporte user) throws Exception {
		TReporte userAux = this.dao.get(user.getId());
		userAux.setNombre(user.getNombre());
		userAux.setFecha(user.getFecha());
		userAux.setFechaFinIncidencia(user.getFechaFinIncidencia());
		userAux.setFechaInIncidencia(user.getFechaInIncidencia());
		userAux.setReporta(user.getReporta());
		userAux.setEstado(user.getEstado());
		this.dao.update(userAux);
	}
	

	public List<TReporte> getList() throws Exception {
		return this.dao.getList();
	}
//	public List<TPlataforma> getListAltas() throws Exception {
//		return this.dao.getListAltas();
//	}
//	
//	public List<TPlataforma> getListDocumentos() throws Exception {
//		return this.dao.getListDocumentos();
//	}

	public TReporte getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	public TReporte getReporte(Long idUser) throws Exception {
		return this.dao.getReporte(idUser);
	}
	public TDato getReporteDatos(Long idUser) throws Exception {
		return this.dao.getReporteDatos(idUser);
	}
	public List<TAppinvolucrada> getReporteApp(Long idUser) throws Exception {
		return this.dao.getReporteApp(idUser);
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

	public TReporte getReporteNombre(String nombre) {
		return this.dao.getTReporteNombre(nombre);
	}

}
