package support.ussd.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import micrium.aes.AlgoritmoAES;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import support.id.FormularioID;
import support.id.ParametroID;
import support.user.bean.ControlerBitacora;
import support.user.business.ParametroBL;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.TerminalesBL;
import support.ussd.model.Adjunto;
import support.ussd.model.TTerminales;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ReplicarBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ReplicarBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private TerminalesBL platBL;
	@Inject
	private ControlerBitacora controlerBitacora;

	@Inject
	ParametroBL controlParametro;
	
	AlgoritmoAES aes;
	
	private String directorio_bccs;
	private String directorio_bbd;
	private String directorio_sva;
	private String radioSeleccionado;

	private String[] selectedMember;
	private List<SelectItem> selectMembers;

	private List<Adjunto> listAdjunto;
	private String[] selectedAdjuntos;
	private String nombreProyecto;
	
	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			directorio_bccs=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BCCS);
			directorio_bbd=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BBD);
			directorio_sva=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_SVA);
			listAdjunto= new ArrayList<Adjunto>();
			radioSeleccionado="1";
			nombreProyecto="";
			selectedAdjuntos=null;
			cargarMembers();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	
	
	
	
	public void handleFileUpload(FileUploadEvent event) {
		// onclick="statusDialog.show()" oncomplete="statusDialog.hide()"
		// try {
		log.debug("[handleFileUpload] se va adjuntar el archivo");
		for (Adjunto a : listAdjunto) {
			if(event.getFile().getFileName().equalsIgnoreCase(a.getNombre())){
				SysMessage.warn("Ya se adjunto un archivo con el nombre: "+a.getNombre());
				return;
			}
		}
		
		Adjunto adjunto= new Adjunto();
		aes = new AlgoritmoAES();
		adjunto.setNombre(event.getFile().getFileName());
		String dir="";
		switch (Integer.valueOf(radioSeleccionado)) {
		case 1:
			dir=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BCCS);
			break;
		case 2:
			dir=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BBD);
			break;
		case 3:
			dir=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_SVA);
			break;

		default:
			break;
		}
		adjunto.setRuta(dir);
		try {
			adjunto.setInputStream(event.getFile().getInputstream());
			listAdjunto.add(adjunto);
			String[] a = new String[listAdjunto.size()];
			for (int i = 0; i < listAdjunto.size(); i++) {
				a[i] = listAdjunto.get(i).getNombre();
			}
			setSelectedAdjuntos(a);
		} catch (IOException e) {

			e.printStackTrace();
			SysMessage.error("Error al adjuntar el archivo");
			log.error(
					"[handleFileUpload] Notificacion : No Se adjunto el archivo",
					e);

		}

	

	}
	
	
	public Boolean copyFile(Adjunto adjunto,String ip) {
		Boolean sw = true;
		try {
			log.debug("[copyFile] se van a copiar los archivos adjuntos");					
	
					File file = new File(ip+adjunto.getRuta());
					if(file.exists()){
						OutputStream out = null;
						try {
							file = new File(ip+adjunto.getRuta()+adjunto.getNombre());
							out = new FileOutputStream(file);
							int read = 0;
							byte[] bytes = new byte[1024];

							while ((read = adjunto.getInputStream().read(bytes)) != -1) {
								out.write(bytes, 0, read);
							}

							adjunto.getInputStream().reset();
							out.flush();
							out.close();

							log.debug("[copyFile] se copio el adjunto "
									+ adjunto.toString());
						} catch (Exception e) {
							e.printStackTrace();
							log.error(
									"[copyFile] no se pudo crear el outputstream",
									e);
							return false;
						} finally {
							if (out != null) {
								out.close();
							}

						}
					}
//					else{
//						if(file.mkdirs()){
//							file = new File(adjunto.getRuta()+File.separator+adjunto.getNombre());
//							OutputStream out = null;
//							try {
//								out = new FileOutputStream(file);
//								int read = 0;
//								byte[] bytes = new byte[1024];
//
//								while ((read = adjunto.getInputStream().read(bytes)) != -1) {
//									out.write(bytes, 0, read);
//								}
//
//								adjunto.getInputStream().close();
//								out.flush();
//								out.close();
//
//								log.debug("[copyFile] se copio el adjunto "
//										+ adjunto.toString());
//							} catch (Exception e) {
//								e.printStackTrace();
//								log.error(
//										"[copyFile] no se pudo crear el outputstream",
//										e);
//								return false;
//							} finally {
//								if (out != null) {
//									out.close();
//								}
//
//							}
//						}
//					}
					

				
				log.debug("[copyFile] se copiaron los archivos adjuntos");
				
			

			return sw;

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e);
			return false;
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e1);
			return false;
		}

	}
			
	public void cargarMembers(){
		try {
			
			List<TTerminales> listter=platBL.getList();		
			selectMembers= new ArrayList<SelectItem>();
			for (TTerminales a : listter) {
				SelectItem sel= new SelectItem(a.getId(), a.getNombre());
				selectMembers.add(sel);
			}
			
			log.info("[obtenerGruposTerminal]Se recupero Grupos del terminal");
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al obtener los members de AD");
		}
			

	}
	
	public String check() throws Exception{
		return radioSeleccionado;
	}
	
	public void guardar() {
		
		try {
			if(nombreProyecto.isEmpty()){
				SysMessage.warn("El campo nombre del proyecto es obligatorio");
				return;
			}
			if (selectedMember.length == 0) {
				SysMessage.warn("No selecciono ningun terminal");
				return;
			}

			if(listAdjunto.isEmpty()){
				SysMessage.warn("No se adjunto ninguna dll");
				return;
			}			
			

			for (String id : selectedMember) {
				TTerminales t= platBL.getUser(Long.valueOf(id));
				
				String dir="";
				switch (Integer.valueOf(radioSeleccionado)) {
				case 1:
					dir=t.getIp()+(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BCCS);
					break;
				case 2:
					dir=t.getIp()+(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BBD);
					break;
				case 3:
					dir=t.getIp()+(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_SVA);
					break;

				default:
					break;
				}
				
				File smbFile= new File(dir);
				if(smbFile.isDirectory()){
					//List<Adjunto> aux= new ArrayList<Adjunto>();
					for (Adjunto adjunto : listAdjunto) {
						for (File a : smbFile.listFiles()) {
							if(a.getName().equalsIgnoreCase(adjunto.getNombre())){
								//Adjunto adaux= new Adjunto();
//								adaux.setNombre(adjunto.getNombre());
//								adaux.setInputStream(adjunto.getInputStream());
//								adaux.setRuta(adjunto.getRuta());
//								String ruta=(String) P.getParamVal(ParametroID.DIRECOTRIO_TERMINAL_BACKUP)+nombreProyecto+"-"+new Timestamp(Calendar.getInstance().getTimeInMillis());
//								ruta=ruta.replace(" ", "_");
//								adaux.setRuta(ruta);
//								aux.add(adaux);
								a.delete();
								log.info("Se elimino correctamente el archivo "+a.getName()+" para luego proceder con la copia de la nueva version de la dll");
							}						
						}
						if(copyFile(adjunto,t.getIp())){
							log.info("copio al servidor");
							controlerBitacora.accion(DescriptorBitacora.REPLICA, "Se replico la dll: "+adjunto.getNombre()+" del Servidor "+t.getNombre()+" con ip: "+t.getIp());
							SysMessage.info("Se replico la dll "+adjunto.getNombre()+" al terminal "+t.getNombre());
						}else{
							log.error("no copio al servidor");
							SysMessage.error("Error al replicar");
						}
					}
					//backup(t, aux);
					
				}
				
			
				
			}
			
			listAdjunto.clear();
			selectedAdjuntos=null;
			radioSeleccionado="1";
			selectedMember=null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al guardar: "+e.getMessage());
			SysMessage.error("Error al guardar");
			return ;
		}
	}
	
	public Boolean backup(TTerminales t, List<Adjunto> a){
		try {
			if(t.getNombre().equalsIgnoreCase("tscaja1")){
				log.info("se va sacar backup de las dll");
				
				for (Adjunto b : a) {
					copyFile(b, t.getIp());
				}
				
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();return false;
		}
	}

	public String getMENU_TYPE() {
		return "menu";
	}

	public String getFORM_TYPE() {
		return "formulario";
	}

	public String getACTION_TYPE() {
		return "accion";
	}

	public boolean isCasdadeDelete() {
		return ((Boolean) P.getParamVal(Integer.valueOf(14))).booleanValue();
	}



	
	public String[] getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(String[] selectedMember) {
		this.selectedMember = selectedMember;
	}



	





	





	public String getDirectorio_bccs() {
		return directorio_bccs;
	}





	public void setDirectorio_bccs(String directorio_bccs) {
		this.directorio_bccs = directorio_bccs;
	}





	public String getDirectorio_bbd() {
		return directorio_bbd;
	}





	public void setDirectorio_bbd(String directorio_bbd) {
		this.directorio_bbd = directorio_bbd;
	}





	public String getDirectorio_sva() {
		return directorio_sva;
	}





	public void setDirectorio_sva(String directorio_sva) {
		this.directorio_sva = directorio_sva;
	}





	public String getRadioSeleccionado() {
		return radioSeleccionado;
	}





	public void setRadioSeleccionado(String radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}





	public List<SelectItem> getSelectMembers() {
		return selectMembers;
	}





	public void setSelectMembers(List<SelectItem> selectMembers) {
		this.selectMembers = selectMembers;
	}

	public String[] getSelectedAdjuntos() {
		return selectedAdjuntos;
	}

	public void setSelectedAdjuntos(String[] selectedAdjuntos) {
		this.selectedAdjuntos = selectedAdjuntos;
	}



	public String getNombreProyecto() {
		return nombreProyecto;
	}





	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}





	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_REPLICAR, idAccion);
	}


}
