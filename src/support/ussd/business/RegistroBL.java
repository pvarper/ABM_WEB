package support.ussd.business;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import support.ussd.dao.RegistroDAO;
import support.ussd.model.TRegistro;

@Named
public class RegistroBL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private RegistroDAO dao;

	
	public void save(String accion,String usuario,String plataforma,String perfil,String estado,String descripcion) throws Exception {
		TRegistro u= new TRegistro();
		u.setFecha(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		u.setAccion(accion);
		u.setUsuario(usuario);
		u.setPlataforma(plataforma);
		u.setPerfil(perfil);
		u.setEstado(estado);
		u.setDescripcion(descripcion);	
		this.dao.save(u);
	}
	
	public List<TRegistro> getList() throws Exception {
		return this.dao.getList();
	}

	public TRegistro getUser(Long idUser) throws Exception {
		return this.dao.get(idUser);
	}

	
}
