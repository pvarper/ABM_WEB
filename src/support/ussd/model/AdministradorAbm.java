package support.ussd.model;

import java.util.List;

public class AdministradorAbm {
	
	private String admin;
	private String supervisor;
	private List<String> servicios;
	private List<String> abms;
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public List<String> getServicios() {
		return servicios;
	}
	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}
	public List<String> getAbms() {
		return abms;
	}
	public void setAbms(List<String> abms) {
		this.abms = abms;
	}
	public String getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	

}
