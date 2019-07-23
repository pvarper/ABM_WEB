package support.ussd.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import support.id.FormularioID;
import support.id.ParametroID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.DocumentoBL;
import support.ussd.business.PlataformaBL;
import support.ussd.business.TipoDocumentoBL;
import support.ussd.model.TDocumento;
import support.ussd.model.TPlataforma;
import support.ussd.model.TTipoDocumento;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class DocumentoBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(DocumentoBean.class);
	ControlPrivilegio controlPrivilegio;
	@Inject
	private DocumentoBL platBL;
	
	@Inject
	private PlataformaBL plataformaBL;
	
	@Inject
	private TipoDocumentoBL tipoBL;

	@Inject
	private ControlerBitacora controlerBitacora;
	
	private List<TDocumento> listPlat;
	private TDocumento pla;
	private String plaId;
	private boolean edit;
	private boolean editRadio;
	private boolean visibleDialog = false;
	private boolean visibleNuevoEditar;
	private boolean visibleVerInfo;

	private List<SelectItem> selectPlataformas;
	private String selectedPlataforma;
	
	private List<SelectItem> selectTipo;
	private String selectedTipo;
	
	private InputStream inputStream;
	private String nombreArchivo;
	
	private String radioButton;
	private String radioEditarDoc;
	

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.pla = new TDocumento();
			this.visibleNuevoEditar = false;
			this.visibleVerInfo=false;
			this.listPlat = this.platBL.getList();	
			nombreArchivo="";
			radioButton="1";
			radioEditarDoc="1";
			fillSelectItems();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	private void fillSelectItems()
			  throws Exception
	{
			  this.selectPlataformas = new ArrayList<SelectItem>();
			  this.selectPlataformas.add(new SelectItem("-1", "-- Seleccionar Plataforma --"));
			  List<TPlataforma> listaRol = this.plataformaBL.getListDocumentos();
			  for (TPlataforma role : listaRol)
			  {
			    SelectItem sel = new SelectItem(role.getId(), role.getNombre());
			    this.selectPlataformas.add(sel);
			  }
			  
			  this.selectTipo = new ArrayList<SelectItem>();
			  this.selectTipo.add(new SelectItem("-1", "-- Seleccionar Tipo de Documento --"));
			  List<TTipoDocumento> listaRol2 = this.tipoBL.getList();
			  for (TTipoDocumento role : listaRol2)
			  {
			    SelectItem sel = new SelectItem(role.getId(), role.getNombre());
			    this.selectTipo.add(sel);
			  }
			  
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try {
			
			if(!edit){
				if(radioEditarDoc.equalsIgnoreCase("1")){
					File f = new File((String) P.getParamVal(ParametroID.RUTA_REPOSITORIO_DOCUMENTO)+event.getFile().getFileName());
					if (f.exists()){
						SysMessage.error("El nombre del archivo ya existe, por favor cambiar nombre");
						return;
					}
				}
				
			}
			
			inputStream=event.getFile().getInputstream();
			nombreArchivo=event.getFile().getFileName();
			
			SysMessage.info("Se cargo el archivo: "+event.getFile().getFileName());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[cargarArchivo] Error al cargar el archivo",e);
			SysMessage.error("Error al cargar el archivo");
		}
		
	}
	
	public Boolean copyFile() {
		Boolean sw = true;
		try {
			log.debug("[copyFile] se va a copiar el archivo");


					String ruta = (String) P.getParamVal(ParametroID.RUTA_REPOSITORIO_DOCUMENTO)+nombreArchivo;
					File file = new File(ruta);
					OutputStream out = null;
					try {
						out = new FileOutputStream(file);
						int read = 0;
						byte[] bytes = new byte[1024];

						while ((read = inputStream.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}

						inputStream.close();
						out.flush();
						out.close();

						log.debug("[copyFile] se copio el archivo ");
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

			return sw;

		} catch (IOException e) {
			e.printStackTrace();
			log.error("[save] Notificacion : error al copiar el archivo", e);
			return false;
		}

	}
	
   public void guardarDocumento() {
	   	try {
	   		
	   		File f = new File((String) P.getParamVal(ParametroID.RUTA_REPOSITORIO_DOCUMENTO));
			if (!f.exists()){
				SysMessage.error("El directorio del repositorio de documentos no existe");
				return;
			}


			if(pla.getClave()!=null){
				if(pla.getClave().trim().isEmpty()){
					SysMessage.warn("Las palabras claves no pueden ser vacias");
					return;
				}
				pla.setClave(pla.getClave().trim());
			}
			if(selectedPlataforma.equalsIgnoreCase("-1")){
				SysMessage.error("Debe seleccionar una plataforma");
				return;
			}
			if(selectedTipo.equalsIgnoreCase("-1")){
				SysMessage.error("Debe seleccionar un Tipo de Documento");
				return;
			}
			String rutaAntigua=pla.getRuta();
			if(radioButton.equalsIgnoreCase("1")){
				if(radioEditarDoc.equalsIgnoreCase("1")){
					if(inputStream==null){
						SysMessage.warn("Debe cargar un documento");
						return;
					}
					pla.setNombre(nombreArchivo);
					 pla.setRuta((String) P.getParamVal(ParametroID.RUTA_REPOSITORIO_DOCUMENTO)+nombreArchivo);
					 pla.setDescripcion(null);
				}
				
				 
			}else{
				pla.setRuta(null);			
				if(pla.getDescripcion()!=null){
					if(pla.getDescripcion().trim().isEmpty()){
						SysMessage.warn("El campo Descripcion es obligatorio");	
						return;
					}else{
						pla.setDescripcion(pla.getDescripcion().trim());
					}
				}else{
					SysMessage.warn("El campo Descripcion es obligatorio");	
					return;
				}
				if(pla.getNombre()!=null){
					if(pla.getNombre().trim().isEmpty()){
						SysMessage.warn("El campo Descripcion es obligatorio");	
						return;
					}else{
						pla.setNombre(pla.getNombre().trim());
						f = new File((String) P.getParamVal(ParametroID.RUTA_REPOSITORIO_DOCUMENTO)+pla.getNombre());
						if (f.exists()){
							SysMessage.error("El nombre del archivo ya existe, por favor cambiar nombre");
							return;
						}		
					}
				}else{
					SysMessage.warn("El campo Descripcion es obligatorio");	
					return;
				}
			}
			
			
			TPlataforma pp=plataformaBL.getPlataforma(Long.valueOf(selectedPlataforma));
			TTipoDocumento td=tipoBL.getUser(Long.valueOf(selectedTipo));

	   		if (!this.edit) {	
				 
				
				  pla.setEstado(true);
				  pla.setPlataformaId(pp);
				  pla.setTTipoDocumento(td);
				 
				  //doc.setClave(pla.getNombre());
				  pla.setClave(pla.getClave().trim());
				  if(radioButton.equalsIgnoreCase("1")){
					  if(!copyFile()){
						  log.error("error al copiar los archivos");
						  SysMessage.error("Error al copiar el archivo");
						  return;
					  }
				  }

				  platBL.save(pla);
				  this.controlerBitacora.insert(DescriptorBitacora.DOCUMENTO, 
				          this.pla.getId()+"", nombreArchivo);
				 
			} else {
				 
				 if(radioButton.equalsIgnoreCase("1")){
					 if(radioEditarDoc.equalsIgnoreCase("1")){
						 File d = new File(rutaAntigua);
							if (d.exists()){
								d.delete();
							}  
					 }
					
				 }
					
				  //pla.setNombre(nombreArchivo);
				  pla.setEstado(true);
				  pla.setPlataformaId(pp);
				  pla.setTTipoDocumento(td);				 
				  //doc.setClave(pla.getNombre());
				  pla.setClave(pla.getClave());
				  
				  if(radioButton.equalsIgnoreCase("1")){
					  if(radioEditarDoc.equalsIgnoreCase("1")){
						  if(!copyFile()){
							  log.error("error al copiar los archivos");
							  SysMessage.error("Error al copiar el archivo");
							  return;
						  }
					  }
					  
				  }
				  platBL.update(pla);
				  this.controlerBitacora.update(DescriptorBitacora.DOCUMENTO, 
				          this.pla.getId()+"", this.pla.getNombre());

			}
	   	 this.listPlat = this.platBL.getList();
		  newplauerimiento();
	   	 this.visibleNuevoEditar = false;
		 
		  SysMessage.info("Se guardó correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[guardarDocumento] error al guardar el comento",e);
			SysMessage.error("Error al guardar");
		}
		
   }
   
   public void copiar(String name){
	   File source = new File("E:\\RepositorioCS\\"+name);
	   File dest = new File("E:\\otro\\"+name);
	   try {
	       FileUtils.copyFile(source, dest);
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
   }

	
	public StreamedContent getFile(Long id) throws Exception {

		log.debug("[getFile] Se va decargar el documento con id : " + id);
		StreamedContent file;
		TDocumento doc= platBL.getUser(id);
		InputStream stream = new FileInputStream(doc.getRuta());
		file = new DefaultStreamedContent(stream, "aplication/pdf", doc.getNombre());
		log.debug("[getFile] Se va descargo el documento con id : " + id);
		return file;
	}
		

	public String delete() {
		if (this.pla != null) {
			try {
				//this.pla.setBaja(Boolean.valueOf(false));			
				this.platBL.delete(this.pla);
				radioButton="2";
				if(pla.getRuta()!=null){
					if(!pla.getRuta().trim().isEmpty()){
						radioButton="1";
					}
				}
				if(radioButton.equalsIgnoreCase("1")){
					File f = new File(pla.getRuta());
					if (f.exists()){
						f.delete();
					}
				}
			
				this.controlerBitacora.delete(DescriptorBitacora.DOCUMENTO,
						this.pla.getId() + "", pla.getNombre());
				this.listPlat = this.platBL.getList();
				SysMessage.info("Se eliminó correctamente.");
			} catch (Exception e) {
				log.error("[deleteDocumento]  error al eliminar el Area"
						+ this.pla.getId() + "  " + e);
				e.printStackTrace();
				SysMessage.error("Fallo al eliminar.");
			}
		} else {
			log.warn("[eliminar] No se encontro ningun registro seleccionado.");
			SysMessage.warn("No se encontró ningun registro seleccionado.");
		}
		return "";
	}


	public String delete(TDocumento plau) {
		this.pla = plau;
		return delete();
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

	public void newplauerimiento() {
		this.edit = false;
		this.editRadio=false;
		this.visibleNuevoEditar = true;
		this.pla = new TDocumento();
		inputStream=null;
		nombreArchivo="";
		selectedPlataforma="-1";
		selectedTipo="-1";
		radioButton="1";
		radioEditarDoc="1";
		
	}

	public void ediTArea() {
		
		try {
			if (this.pla != null) {
				this.visibleNuevoEditar = true;
				this.plaId = this.pla.getId() + "";
				selectedPlataforma=String.valueOf(pla.getPlataformaId().getId());
				selectedTipo=String.valueOf(pla.getTTipoDocumento().getId());
				if(pla.getRuta()==null){
					radioButton="2";
				}else{
					if(pla.getRuta().trim().isEmpty()){
						radioButton="2";
					}else{
						radioButton="1";
					}
				}
				editRadio=false;
				if(pla.getRuta()!=null){
					if(!pla.getRuta().trim().isEmpty()){
						this.editRadio = true;
					}
				}
				
				this.edit = true;
			} else {
				log.warn("[editar] No se encontro ningun registro seleccionado.");
				SysMessage.warn("No se encontro ningun registro seleccionado.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SysMessage.error("error al momento de obtener los datos para editar");
		}
		
	}

	public void ediTArea(TDocumento muRol) {
		this.pla = muRol;
		ediTArea();
	}

	public void verInfo(TDocumento muRol) {
		this.pla = muRol;
		this.visibleVerInfo=true;
	}

	public String getRoleId() {
		return this.plaId;
	}

	public void setRoleId(String RoleId) {
		this.plaId = RoleId;
	}

	public Boolean getEdit() {
		return Boolean.valueOf(this.edit);
	}

	public void setEdit(Boolean edit) {
		this.edit = edit.booleanValue();
	}

	public List<TDocumento> getlistPlat() {
		return this.listPlat;
	}

	public void setlistPlat(List<TDocumento> listPlat) {
		this.listPlat = listPlat;
	}

	public TDocumento getpla() {
		return this.pla;
	}

	public void setpla(TDocumento pla) {
		this.pla = pla;
	}

	public String getplaId() {
		return this.plaId;
	}

	public void setplaId(String plaId) {
		this.plaId = plaId;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isVisibleDialog() {
		return this.visibleDialog;
	}

	public void setVisibleDialog(boolean visibleDialog) {
		this.visibleDialog = visibleDialog;
	}

	public boolean isVisibleNuevoEditar() {
		return this.visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) throws Exception {
		this.listPlat = this.platBL.getList();
		this.visibleNuevoEditar = visibleNuevoEditar;
	}

	

	
	public boolean isVisibleVerInfo() {
		return visibleVerInfo;
	}

	public void setVisibleVerInfo(boolean visibleVerInfo) {
		this.visibleVerInfo = visibleVerInfo;
	}

	public String getRadioButton() {
		return radioButton;
	}

	public void setRadioButton(String radioButton) {
		this.radioButton = radioButton;
	}

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_DOC, idAccion);
	}

	public List<SelectItem> getSelectPlataformas() {
		return selectPlataformas;
	}

	public void setSelectPlataformas(List<SelectItem> selectPlataformas) {
		this.selectPlataformas = selectPlataformas;
	}

	public String getSelectedPlataforma() {
		return selectedPlataforma;
	}

	public void setSelectedPlataforma(String selectedPlataforma) {
		this.selectedPlataforma = selectedPlataforma;
	}

	public List<SelectItem> getSelectTipo() {
		return selectTipo;
	}

	public void setSelectTipo(List<SelectItem> selectTipo) {
		this.selectTipo = selectTipo;
	}

	public String getSelectedTipo() {
		return selectedTipo;
	}

	public void setSelectedTipo(String selectedTipo) {
		this.selectedTipo = selectedTipo;
	}

	public String getRadioEditarDoc() {
		return radioEditarDoc;
	}

	public void setRadioEditarDoc(String radioEditarDoc) {
		this.radioEditarDoc = radioEditarDoc;
	}

	public boolean isEditRadio() {
		return editRadio;
	}

	public void setEditRadio(boolean editRadio) {
		this.editRadio = editRadio;
	}





	
	
}
