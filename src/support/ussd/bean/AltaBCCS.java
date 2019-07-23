package support.ussd.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

import servicios.Result;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.AreaBL;
import support.ussd.business.RegistroBL;
import support.ussd.model.TArea;
import support.ussd.model.TUsuarioAtributo;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class AltaBCCS implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	
	public static Logger log = Logger.getLogger(AltaBCCS.class);
	@Inject
	private ControlerBitacora controlerBitacora;
	@Inject
	private RegistroBL regBL;
	@Inject
	private AreaBL areBL;
	private String usuario;// input text usuario
	private List<String> lineasArchivo;// usuario del archivo subido
	private String formato;
	private String Cargo;
	private String Area;
	ControlPrivilegio controlPrivilegio;
	
	private List<String> selectPerfiles;
	private String[] selectedPerfil;
	private List<String> selectAreas;
	private String selectedArea;
//	private List<String> selectMembers;
//	private String[] selectedMember;
	private Boolean radioSeleccionado;
	private String radioPlataformas;
	XSSFWorkbook workbook = null;
	private Date fechaExp;
	private List<TUsuarioAtributo> listu;

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			usuario = "";
			Cargo="";
			Area="";
			radioPlataformas="1";
			lineasArchivo = new ArrayList<String>();
			radioSeleccionado=false;
			formato = "usuario;nombre;eh;dpto;mail;ci;telf";
			listu= new ArrayList<TUsuarioAtributo>();
			cargarperfiles();

		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	public void cargarperfiles(){
		try {
			ServiciosI ws= new ServiciosI();
			Result res=ws.obtenerPerfilesBCCS();
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				log.error("error al obtener los perfiles de la plataforma BCCS "+res.getDescription());
				SysMessage.error("Error al cargar los roles");
				return ;
			}
			selectPerfiles=new ArrayList<String>();
			//selectPerfiles.add("-- Seleccionar Perfiles --");
			String a = (String)res.getData();
			StringTokenizer tokens = new StringTokenizer(a,",");
			while(tokens.hasMoreTokens()){				
				selectPerfiles.add(tokens.nextToken());
			}
			
			selectAreas= new ArrayList<String>();
			selectAreas.add("--Seleccionar Area--");
			List<TArea> lis=areBL.getList();
			for (TArea tArea : lis) {
				selectAreas.add(tArea.getNombre());
			}
			
//			
//			support.util.Result r=ActiveDirectory.obtenerGrupos();
//			if(!r.getCode().equalsIgnoreCase(Code.OK)){
//				SysMessage.error("Error al cargar los memberof");
//				log.error("Error al cargar los memberof "+r.getDescription());
//				return;
//			}
//			selectMembers=(List<String>) r.getData();
			
			
		} catch (Exception e) {
			log.error("error al obtener los perfiles de la plataforma BCCS ,"+e.getMessage());
			SysMessage.error("Error al cargar los roles");
			return ;
		}
	}
	
	public String check() throws Exception{
		return radioPlataformas;
	}
	
	public void handleFileUpload(FileUploadEvent event) {

		try {

			InputStreamReader fr = new InputStreamReader(event.getFile()
					.getInputstream(), "UTF-8");
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while ((linea = br.readLine()) != null) {
				lineasArchivo.add(linea);
				// SysMessage.info(linea);
			}
			fr.close();
			br.close();

			SysMessage.info("subio el archivo: "
					+ event.getFile().getFileName());

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al cargar el archivo", e);
			SysMessage.error("Error al cargar archivo");
		}
	}
	
	public void handleFileUpload2(FileUploadEvent event) {
		
		listu.clear();
		if (event.getFile().equals(null)) {
			SysMessage.error("Archivo null");
			return;
		}
		// FileInputStream file;
		workbook = null;
		try {
			// --file =new FileInputStream(new inputs)
			workbook = new XSSFWorkbook(event.getFile().getInputstream());
		} catch (IOException e) {
			SysMessage.error("Error Leyendo el Archivo");
			return;
		}
		

		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();
		//Calendar calendar = new GregorianCalendar();
		TUsuarioAtributo tu = new TUsuarioAtributo();
		while (rowIterator.hasNext()) {		
			tu = new TUsuarioAtributo();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				// Job job = new Job();
				
				int pos=0;
				while (cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:

						if (HSSFDateUtil.isCellDateFormatted(cell)
								|| HSSFDateUtil
										.isCellInternalDateFormatted(cell)) {
							if(pos==2){
								tu.setEh(cell.getDateCellValue()+"");
							}
						} else {
							if(pos==2){
								tu.setEh(cell.getNumericCellValue()+"");
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						if(pos==0){
							tu.setUsuario(cell.getStringCellValue());
						}
						if(pos==1){
							tu.setNombreCompletoTerminal(cell.getStringCellValue());
						}
						if(pos==2){
							tu.setEh(cell.getStringCellValue());
						}
						if(pos==3){
							tu.setDpto(cell.getStringCellValue());
						}
						
						break;
					}
					
					pos=pos+1;
				}
				listu.add(tu);
			}
			tu= new TUsuarioAtributo();
		
		try {
			//int c=0;
			for (TUsuarioAtributo a : listu) {
			//	if(c>0){
					double d=Double.valueOf(a.getEh());
					a.setEh(String.valueOf((int)Math.floor(d)));
					System.out.println(a.getUsuario()+","+a.getNombreCompletoTerminal()+","+a.getEh()+","+a.getDpto());
				//}
				//c=c+1;
			}
			//listu.remove(0);
			
		} catch (Exception e) {
			log.error("Error al cargar el archivo", e);
			SysMessage.error("Error al cargar el archivo, consulte con el administrador");
		}
		
	}
	
	public void decidir(String id){
		System.out.println("este es el id: "+id);
		switch (Integer.valueOf(id)) {
			case 1:
				guardarAll();
				break;
			case 2:
				guardarBCCS();
				break;
			case 4:
				guardarAS();
				break;
		default:
			break;
		}
	}

	public void guardarAll() {

			try {
				if(selectedPerfil.length==0){
					SysMessage.warn("debe seleccionar al menos un perfil");
					return;
				}
				if(selectedArea.equalsIgnoreCase("--Seleccionar Area--")){
					SysMessage.warn("debe seleccionar un Area");
					return;
				}
//				if(selectedMember.length==0){
//					SysMessage.warn("debe seleccionar al menos un MemberOf");
//					return;
//				}
				if(radioSeleccionado){
					if(fechaExp==null){
						SysMessage.warn("debe seleccionar una fecha de expiracion");
						return;
					}
				}
				
				if (listu.isEmpty()) {
					SysMessage.warn("debe cargar un archivo");
					return;
				}
				
				
				for (TUsuarioAtributo u : listu) {
					

					String per="";
					boolean first = true;
					for (String as : selectedPerfil) {
						  if(first) {
							  per+=as.trim();
						      first=false;
						    } else {
						    	per+=","+as.trim();
						    }
					}

					u.setPerfil(per);
					u.setArea(selectedArea);
					u.setTipoComp(areBL.geTAreaNombre(selectedArea).getDescripcion());
										
				
					//System.out.println(us.getPerfil()+";"+us.getUsuario()+";"+us.getNombre()+";"+us.getEh()+";"+us.getDpto()+";"+us.getArea()+";"+us.getTipoComp());
					ServiciosI ws = new ServiciosI();
					Result res = ws.insertBCCS(u.getPerfil(), u.getUsuario(),
							u.getNombreCompletoTerminal(), u.getEh(), u.getDpto(),
							u.getArea(), u.getTipoComp());
					
					if (res.getCode().equalsIgnoreCase(Code.OK)) {
						regBL.save("ALTA", u.getUsuario() + "", "BCCS",
								u.getPerfil() + "-" + u.getArea(), "OK", "");
						this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Se dio de alta el usuario: "+u.getUsuario());
					} else {
						regBL.save("ALTA", u.getUsuario(), "BCCS",
								u.getPerfil() + "-" + u.getArea(), "ERROR",
								res.getDescription());
						//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
					}
					
					ws = new ServiciosI();
					res = ws.insertWIMAX(u.getPerfil(), u.getUsuario(),
							u.getNombreCompletoTerminal(), u.getEh(), u.getDpto(),
							u.getArea(), u.getTipoComp());
					
					if (res.getCode().equalsIgnoreCase(Code.OK)) {
						regBL.save("ALTA", u.getUsuario() + "", "WIMAX",
								u.getPerfil() + "-" + u.getArea(), "OK", "");
						this.controlerBitacora.accion(DescriptorBitacora.ALTAWIMAX,"Se dio de alta el usuario: "+u.getUsuario());
					} else {
						regBL.save("ALTA", u.getUsuario(), "WIMAX",
								u.getPerfil() + "-" + u.getArea(), "ERROR",
								res.getDescription());
						//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
					}
					
					
					
					ws = new ServiciosI();
					res = ws.insertASGenerico(u.getUsuario(), u.getNombreCompletoTerminal(), u.getArea());
					
					if (res.getCode().equalsIgnoreCase(Code.OK)) {
						regBL.save("ALTA", u.getUsuario() + "", "AS",
								u.getPerfil() + "-" + u.getArea(), "OK", "");
						this.controlerBitacora.accion(DescriptorBitacora.ALTAAS,"Se dio de alta el usuario: "+u.getUsuario());
					} else {
						regBL.save("ALTA", u.getUsuario(), "AS",
								u.getPerfil() + "-" + u.getArea(), "ERROR",
								res.getDescription());
						//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
					}
					

				}
				
				SysMessage
						.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
				lineasArchivo.clear();
				// this.visibleNuevoEditar = false;
				// newplauerimiento();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("[GuardarAll] Error al procesar los usuarios", e);
				SysMessage.error("Error al procesar");
				return;
			}	

	}
	
	public void guardarBCCS() {

		try {
			if(selectedPerfil.length==0){
				SysMessage.warn("debe seleccionar al menos un perfil");
				return;
			}
			if(selectedArea.equalsIgnoreCase("--Seleccionar Area--")){
				SysMessage.warn("debe seleccionar un Area");
				return;
			}

			
			if (listu.isEmpty()) {
				SysMessage.warn("debe cargar un archivo");
				return;
			}
			for (TUsuarioAtributo u : listu) {
				

				String per="";
				boolean first = true;
				for (String as : selectedPerfil) {
					  if(first) {
						  per+=as.trim();
					      first=false;
					    } else {
					    	per+=","+as.trim();
					    }
				}

				u.setPerfil(per);
				u.setArea(selectedArea);
				u.setTipoComp(areBL.geTAreaNombre(selectedArea).getDescripcion());
								
			
				//System.out.println(us.getPerfil()+";"+us.getUsuario()+";"+us.getNombre()+";"+us.getEh()+";"+us.getDpto()+";"+us.getArea()+";"+us.getTipoComp());
				ServiciosI ws = new ServiciosI();
				Result res = ws.insertBCCS(u.getPerfil(), u.getUsuario(),
						u.getNombreCompletoTerminal(), u.getEh(), u.getDpto(),
						u.getArea(), u.getTipoComp());
				
				if (res.getCode().equalsIgnoreCase(Code.OK)) {
					regBL.save("ALTA", u.getUsuario() + "", "BCCS",
							u.getPerfil() + "-" + u.getArea(), "OK", "");
					this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Se dio de alta el usuario: "+u.getUsuario());
				} else {
					regBL.save("ALTA", u.getUsuario(), "BCCS",
							u.getPerfil() + "-" + u.getArea(), "ERROR",
							res.getDescription());
					//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
				}
				
				ws = new ServiciosI();
				res = ws.insertWIMAX(u.getPerfil(), u.getUsuario(),
						u.getNombreCompletoTerminal(), u.getEh(), u.getDpto(),
						u.getArea(), u.getTipoComp());
				
				if (res.getCode().equalsIgnoreCase(Code.OK)) {
					regBL.save("ALTA", u.getUsuario() + "", "WIMAX",
							u.getPerfil() + "-" + u.getArea(), "OK", "");
					this.controlerBitacora.accion(DescriptorBitacora.ALTAWIMAX,"Se dio de alta el usuario: "+u.getUsuario());
				} else {
					regBL.save("ALTA", u.getUsuario(), "WIMAX",
							u.getPerfil() + "-" + u.getArea(), "ERROR",
							res.getDescription());
					//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
				}								
			}
			
			SysMessage
					.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
			lineasArchivo.clear();
			// this.visibleNuevoEditar = false;
			// newplauerimiento();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[guardarBCCS] Error al procesar los usuarios", e);
			SysMessage.error("Error al procesar");
			return;
		}	

	}
	
	
	
	public void guardarAS() {

		try {

			if(selectedArea.equalsIgnoreCase("--Seleccionar Area--")){
				SysMessage.warn("debe seleccionar un Area");
				return;
			}
			
			if (listu.isEmpty()) {
				SysMessage.warn("debe cargar un archivo");
				return;
			}
			for (TUsuarioAtributo u : listu) {
				

//				String per="";
//				boolean first = true;
//				for (String as : selectedPerfil) {
//					  if(first) {
//						  per+=as.trim();
//					      first=false;
//					    } else {
//					    	per+=","+as.trim();
//					    }
//				}

				//u.setPerfil(per);
				u.setArea(selectedArea);
				u.setTipoComp(areBL.geTAreaNombre(selectedArea).getDescripcion());
			
				
				
				
				ServiciosI ws = new ServiciosI();
				Result res = ws.insertASGenerico(u.getUsuario(), u.getNombreCompletoTerminal(), u.getArea());
			
				if (res.getCode().equalsIgnoreCase(Code.OK)) {
					regBL.save("ALTA", u.getUsuario() + "", "AS",
							u.getPerfil() + "-" + u.getArea(), "OK", "");
					this.controlerBitacora.accion(DescriptorBitacora.ALTAAS,"Se dio de alta el usuario: "+u.getUsuario());
				} else {
					regBL.save("ALTA", u.getUsuario(), "AS",
							u.getPerfil() + "-" + u.getArea(), "ERROR",
							res.getDescription());
					//this.controlerBitacora.accion(DescriptorBitacora.ALTABCCS,"Error al dar de alta el usuario: "+us.getUsuario());
				}
				

			}
			
			SysMessage
					.info("Finalizado, por favor verificar la vista Registros para visualizar el proceso de registro de los usuarios");
			lineasArchivo.clear();
			// this.visibleNuevoEditar = false;
			// newplauerimiento();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[guardarAS] Error al procesar los usuarios", e);
			SysMessage.error("Error al procesar");
			return;
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

	public void newplauerimiento() throws Exception {

		lineasArchivo = new ArrayList<String>();
		usuario = "";
		formato = "usuario;nombre;eh;dpto;mail;ci;telf";
		radioSeleccionado=false;
		radioPlataformas="1";

	}

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_ALTA,
				idAccion);
	}

	

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public List<String> getSelectPerfiles() {
		return selectPerfiles;
	}

	public void setSelectPerfiles(List<String> selectPerfiles) {
		this.selectPerfiles = selectPerfiles;
	}

	public List<String> getSelectAreas() {
		return selectAreas;
	}

	public void setSelectAreas(List<String> selectAreas) {
		this.selectAreas = selectAreas;
	}

	public String getSelectedArea() {
		return selectedArea;
	}

	public void setSelectedArea(String selectedArea) {
		this.selectedArea = selectedArea;
	}

//	public List<String> getSelectMembers() {
//		return selectMembers;
//	}
//
//	public void setSelectMembers(List<String> selectMembers) {
//		this.selectMembers = selectMembers;
//	}

	public String[] getSelectedPerfil() {
		return selectedPerfil;
	}

	public void setSelectedPerfil(String[] selectedPerfil) {
		this.selectedPerfil = selectedPerfil;
	}

//	public String[] getSelectedMember() {
//		return selectedMember;
//	}
//
//	public void setSelectedMember(String[] selectedMember) {
//		this.selectedMember = selectedMember;
//	}

	public String getCargo() {
		return Cargo;
	}

	public void setCargo(String cargo) {
		Cargo = cargo;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public Boolean getRadioSeleccionado() {
		return radioSeleccionado;
	}

	public void setRadioSeleccionado(Boolean radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}

	public Date getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(Date fechaExp) {
		this.fechaExp = fechaExp;
	}

	public String getRadioPlataformas() {
		return radioPlataformas;
	}

	public void setRadioPlataformas(String radioPlataformas) {
		this.radioPlataformas = radioPlataformas;
	}



}
