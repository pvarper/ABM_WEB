package support.ussd.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import servicios.Result;
import support.id.FormularioID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.model.MuUsuario;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.model.Cuentas;
import support.ussd.model.Sucursal;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class LiberacionBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(LiberacionBean.class);
	ControlPrivilegio controlPrivilegio;

	@Inject
	private ControlerBitacora controlerBitacora;

	private MuUsuario usu;
	private String plaId;
	private List<SelectItem> selectItems;
	private String select;
	private String localidadSeleccionada;
	
	private List<SelectItem> selectItemsA;
	private String selectA;
	
	private List<SelectItem> selectItemsB;
	private String selectB;
	
	private String cantidad;
	
	private List<String> lineasArchivo;
	List<Sucursal> localidades;
	
	private byte[] exportContent;
	
	Gson gson= new Gson();

	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			this.usu = new MuUsuario();
			localidadSeleccionada="";
			select="-1";
			selectA="-1";
			selectB="-1";
			cantidad="100";
			lineasArchivo= new ArrayList<String>();
			fillSelectItems();
			
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}

	private void fillSelectItems() {
		
		try {
			this.selectItems = new ArrayList<SelectItem>();
			this.selectItems.add(new SelectItem("-1", "-- Seleccionar Localidad --"));
			
			this.selectItemsA = new ArrayList<SelectItem>();
			this.selectItemsA.add(new SelectItem("-1", "-- Modalidad de Cuenta --"));
			SelectItem sel = new SelectItem("POS");
			this.selectItemsA.add(sel);
			sel = new SelectItem("PRE");
			this.selectItemsA.add(sel);
			
			this.selectItemsB = new ArrayList<SelectItem>();
			this.selectItemsB.add(new SelectItem("-1", "-- Modalidad de Cuenta --"));
			sel = new SelectItem("POS");
			this.selectItemsB.add(sel);
			sel = new SelectItem("PRE");
			this.selectItemsB.add(sel);
			
			
			
			
			ServiciosI ser= new ServiciosI();

			Result res = ser.obtenerLocalidadesBCCS();
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				log.error("[LiberacionBean:fillSelectItems]Error al obtener las localidades de BCCS: "+res.getDescription());
				SysMessage.error("Error al obtener las localidades");
				return;
			}
			Type type = new TypeToken<List<Sucursal>>(){}.getType();
			
			localidades=gson.fromJson((String) res.getData(),type);
			
			for (Sucursal suc : localidades) {
				sel = new SelectItem(suc.getId(),suc.getDescripcion());
				this.selectItems.add(sel);
				
			}
			
//			for (Entry<BigDecimal, HashMap<BigDecimal, String>> lo : localidades.entrySet()) {
//				HashMap<BigDecimal, String> valor=lo.getValue();
//				for (Entry<BigDecimal, String> v : valor.entrySet()) {
//					sel = new SelectItem(lo.getKey(),v.getValue());
//					this.selectItems.add(sel);
//				}
//				
//			}
			
		} catch (Exception e) {
			log.error("[fillSelectItems] error al obtener las localidades",e);
			SysMessage.error("Se produjo un error al obtener las localidades");
		}
		
		
	}
	
	
	
	public void actualizar(){
		
		try {
			
			for (Sucursal s : localidades) {
				if(s.getId().equalsIgnoreCase(localidadSeleccionada)){
					localidadSeleccionada=s.getCod();
				}
				
			}
			
			if(localidadSeleccionada.isEmpty()){
				SysMessage.warn("Debe seguir el proceso de obtencion de cuetnas congeladas para poder liberar");
				return;
			}
			
			if(lineasArchivo.isEmpty()){
				SysMessage.warn("Debe cargar un archivo");
				return;
			}
			if(selectB.equalsIgnoreCase("-1")){
				SysMessage.warn("Debe seleccionar una modalidad");
				return;
			}
			ServiciosI ser= new ServiciosI();
			Result res;
			
			if(selectB.equalsIgnoreCase("PRE")){///si la modalidad seleccionada por pantalla es PRE
				
				for (String linea : lineasArchivo) {
					

	            	res= ser.verificarCuentasCongeladas(linea.trim());
	    			if(!res.getCode().equalsIgnoreCase(Code.OK)){
	    				log.error(res.getDescription());
	    				SysMessage.error("Error al verificar las cuentas congeladas, consulte al administrador");
	    				lineasArchivo.clear();
	    				return;
	    			}
	    			Type type = new TypeToken<Cuentas>(){}.getType();
	    			
	    			Cuentas cuenta=gson.fromJson((String) res.getData(),type);
	    			
	    			if(cuenta.getEstado()==null){
	    				log.warn("La cuenta leida "+linea.trim()+" no esta en estado congelada");
	    				controlerBitacora.accion(DescriptorBitacora.LIBERACION, "La cuenta leida "+cuenta.getCuenta()+" no se encuentra en estado congelado");
	    				
	    				continue;
	    			}
					
					
					log.info("Datos de la cuenta:" +cuenta.toString());
					if (cuenta.getModalidad().equalsIgnoreCase("PRE")||cuenta.getModalidad().equalsIgnoreCase("POS")){// si la cuenta se encueentra con modalidad PRE
						
						res=ser.actualizarCuentaPreARe(cuenta.getCuenta(),"PRE");//se coloca la cuenta en RE y modalidad PRE
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							SysMessage.error("Error al actualizar la cuenta PRE "+cuenta.getCuenta()+" a estado RE de la plataforma, consulte al administrador");
							log.error("Error al actualizar la cuenta PRE "+cuenta.getCuenta()+ ", "+res.getDescription());
							return;
						}
						controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se actualizó la cuenta "+cuenta.getCuenta()+", modalidad: PRE, estado: RE");
						res=ser.verificarCuentaEnToolCuenta(cuenta.getCuenta());//se veriofica si la cuenta existe en la tool
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							log.error("Error al verificar la cuenta "+cuenta.getCuenta()+", "+res.getDescription());
							SysMessage.error("Error al verificar la cuenta en tool, consulte al adminsitrador");
							return;
						}
						
						String cu= (String)res.getData();
						if(cu.isEmpty()){
							res=ser.insertarCuentaPreAToolCuenta(cuenta.getCuenta(), localidadSeleccionada);//si no existe se inserta la cuenta
							if(!res.getCode().equalsIgnoreCase(Code.OK)){
								SysMessage.warn("Error al insertar la cuenta PRE "+cuenta+" a la tabla tool de la plataforma");
								log.error("Error al insertar la cuenta PRE "+cuenta+ " "+res.getDescription());
								return;
							}
							controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se inserto la cuenta "+cuenta.getCuenta()+" a la ToolCuenta, modalidad: PRE, estado: RE");
						}else{
							res=ser.actualizarCuentaPreEnTool(cuenta.getCuenta(), localidadSeleccionada);//si existe se actualiza
							if(!res.getCode().equalsIgnoreCase(Code.OK)){
								SysMessage.error("Error al actualizar la cuenta PRE "+cuenta+" a la tabla tool de la plataforma");
								log.error("Error al actualizar la cuenta PRE "+cuenta+ " "+res.getDescription());
								return;
							}
							controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se actualizo la cuenta "+cuenta.getCuenta()+" en la ToolCuenta, modalidad: PRE, estado: RE");
						}
						//actualizar la modalidad en la Cer Cuenta
						res=ser.actualizarModEnCerCuenta(cuenta.getCuenta(),"PRE" );//si existe se actualiza
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							SysMessage.error("Error al actualizar la cuenta  "+cuenta+" a la tabla CerCuenta de la plataforma");
							log.error("Error al actualizar la cuenta  "+cuenta+ " en la Cer cuenta "+res.getDescription());
							return;
						}
						controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se actualizo la cuenta "+cuenta.getCuenta()+" en la CerCuenta, modalidad: PRE, estado: RE");
						
					}		
					log.info("Se Libero la cuenta "+cuenta.getCuenta()+" y se cambio de modalidad "+cuenta.getModalidad()+" a modalidad PRE ");
				}
		
			}else{
				for (String linea : lineasArchivo) {
					
					
					res= ser.verificarCuentasCongeladas(linea.trim());
	    			if(!res.getCode().equalsIgnoreCase(Code.OK)){
	    				log.error(res.getDescription());
	    				SysMessage.error("Error al verificar las cuentas congeladas, consulte al administrador");
	    				lineasArchivo.clear();
	    				return;
	    			}
	    			Type type = new TypeToken<Cuentas>(){}.getType();
	    			
	    			Cuentas cuenta=gson.fromJson((String) res.getData(),type);
	    			
	    			if(cuenta.getEstado()==null){
	    				log.warn("La cuenta leida "+linea.trim()+" no esta en estado congelada");
	    				controlerBitacora.accion(DescriptorBitacora.LIBERACION, "La cuenta leida "+cuenta.getCuenta()+" no se encuentra en estado congelado");
	    				
	    				continue;
	    			}
	    			log.info("Datos de la cuenta:" +cuenta.toString());
					
					if (cuenta.getModalidad().equalsIgnoreCase("PRE")||cuenta.getModalidad().equalsIgnoreCase("POS")){// si la cuenta se encueentra con modalidad PRE
						res=ser.verificarCuentaEnToolCuenta(cuenta.getCuenta());
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							SysMessage.error("Error al verificar la cuenta  "+cuenta+" a la tabla Tool de la plataforma, consulte al administrador");
							log.error("Error al verificar la cuenta  "+cuenta+ " en la Tool, "+res.getDescription());
							return;
						}
						String cu=(String)res.getData();
						if(!cu.isEmpty()){
							res=ser.deleteCuentaEnTool(cuenta.getCuenta());
							if(!res.getCode().equalsIgnoreCase(Code.OK)){
								SysMessage.error("Error al eliminar la cuenta  "+cuenta+" a la tabla tool de la plataforma, consulte con el administrador");
								log.error("Error al eliminar la cuenta  "+cuenta+ " en la tool "+res.getDescription());
								return;
							}
							controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se elimino la cuenta "+cuenta.getCuenta()+" de la ToolCuenta, modalidad: POS");
						}
						res= ser.actualizarCuentaPosALI(cuenta.getCuenta(), "POS");
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							SysMessage.error("Error al actuializar la cuenta  "+cuenta+" a estado LI de la plataforma, consulte con el administrador");
							log.error("Error al actualizar la cuenta  "+cuenta+ " en la tool "+res.getDescription());
							return;
						}
						controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se actualizo la cuenta "+cuenta.getCuenta()+" a estado LI, modalidad: POS");
						res=ser.actualizarModEnCerCuenta(cuenta.getCuenta(),"POS" );//si existe se actualiza
						if(!res.getCode().equalsIgnoreCase(Code.OK)){
							SysMessage.error("Error al actualizar la cuenta  "+cuenta+" a la tabla CerCuenta de la plataforma");
							log.error("Error al actualizar la cuenta  "+cuenta+ " en la Cer cuenta "+res.getDescription());
							return;
						}
						controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se actualizo la cuenta "+cuenta.getCuenta()+" en la CerCuenta, modalidad: POS, estado: LI");
					}
					log.info("Se Libero la cuenta "+cuenta.getCuenta()+" y se cambio  de modalidad "+cuenta.getModalidad()+" a modalidad POS ");
				}	
			}
			
			SysMessage.info("Terminó el proceso de Liberacion de cuentas a estado "+selectB+" correctamente, por favor verifique la Bitacora");
			log.info("Terminó el proceso de Liberacion de cuentas a estado "+selectB+" correctamente, por favor verifique la Bitacora");
			controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Terminó el proceso de Liberacion de cuentas a estado "+selectB+" correctamente, por favor verifique la Bitacora");
			lineasArchivo.clear();
			localidadSeleccionada="";
			selectB="-1";
		} catch (Exception e) {
			log.error("Error al actulizar las cuentas",e);
			SysMessage.error("Error al actualizar las cuentas, consulte con el administrador");
		}
		
	}
	
	public void obtener(){
		
		try {
			
			if(select.equalsIgnoreCase("-1")){
				SysMessage.warn("Debe Seleccionar Localidad");
				return;
			}
			if(selectA.equalsIgnoreCase("-1")){
				SysMessage.warn("Debe Seleccionar una Modalidad");
				return;
			}
			if(cantidad.equalsIgnoreCase("0")){
				SysMessage.warn("La cantidad debe ser mayor a 0");
				return;
			}
			ServiciosI ser= new ServiciosI();
			
			Result res= ser.obtenerCuentasCongeladasBCCS(select, selectA, Integer.valueOf(cantidad));
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				log.error("Error al momento de obtener las cuentas congeladas: "+res.getDescription());
				SysMessage.error("Error al momento de obtener las cuentas congeladas");
				return;
			}

			String exp=(String) res.getData();
			if(exp.isEmpty()){
				SysMessage.info("No hay cuentas para esta localidad");
				select="-1";
				selectA="-1";
				cantidad="100";
				return;
			}

			exportContent = exp.getBytes();
			
			SysMessage.info("se obtuvieron las cuentas correctamente");
			localidadSeleccionada=select;
			select="-1";
			selectA="-1";
			cantidad="100";
			controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se obtuvieron cuentas de la plataformas BCCS");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al obtener las cuentas congeladas",e);
			SysMessage.error("Error al obtener las cuentas congeladas");
			
		}
		
	}
	
     

    public void export() {
    	
    	if(exportContent==null){
    		SysMessage.warn("Para exporta, debe obtener cuentas primeramente");
    		return;
    	}
    	
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("text/plain");
        ec.setResponseContentLength(exportContent.length);
        String attachmentName = "attachment; filename=\"export.txt\"";
        ec.setResponseHeader("Content-Disposition", attachmentName);
        try {
            OutputStream output = ec.getResponseOutputStream();
            Streams.copy(new ByteArrayInputStream(exportContent), output, false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        fc.responseComplete();
        exportContent=null;
        controlerBitacora.accion(DescriptorBitacora.LIBERACION, "Se exportaron cuentas de la plataformas BCCS");
    }

    public boolean isReady() {
        return exportContent != null;
    }
	
public void handleFileUpload(FileUploadEvent event) {
        
        try {
        	
        	//ServiciosI ser= new ServiciosI();					
        	lineasArchivo= new ArrayList<String>();
        	
        	InputStreamReader fr =  new InputStreamReader(event.getFile().getInputstream(),"UTF-8");
        	BufferedReader br = new BufferedReader(fr);
        	String linea;
            while ((linea = br.readLine()) != null) {
            	
            	lineasArchivo.add(linea.trim());
            }    
            
            fr.close();
            br.close();
            log.debug("se cargo el archivo masivo correctamente");
                  	
			SysMessage.info("subio el archivo: "+event.getFile().getFileName());
            
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al cargar el archivo",e);
			SysMessage.error("Error al cargar archivo");
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


	public String getRoleId() {
		return this.plaId;
	}

	public void setRoleId(String RoleId) {
		this.plaId = RoleId;
	}


	

	public String getplaId() {
		return this.plaId;
	}

	public void setplaId(String plaId) {
		this.plaId = plaId;
	}	
	
	public List<SelectItem> getSelectItems() {
		return selectItems;
	}


	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}


	public String getSelect() {
		return select;
	}


	public void setSelect(String select) {
		this.select = select;
	}


	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.LIBERAR_CUENTAS, idAccion);
	}

	public MuUsuario getUsu() {
		return usu;
	}

	public void setUsu(MuUsuario usu) {
		this.usu = usu;
	}

	public List<SelectItem> getSelectItemsA() {
		return selectItemsA;
	}

	public void setSelectItemsA(List<SelectItem> selectItemsA) {
		this.selectItemsA = selectItemsA;
	}

	public String getSelectA() {
		return selectA;
	}

	public void setSelectA(String selectA) {
		this.selectA = selectA;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public List<SelectItem> getSelectItemsB() {
		return selectItemsB;
	}

	public void setSelectItemsB(List<SelectItem> selectItemsB) {
		this.selectItemsB = selectItemsB;
	}

	public String getSelectB() {
		return selectB;
	}

	public void setSelectB(String selectB) {
		this.selectB = selectB;
	}



	

	





	
	
}
