package support.ussd.bean;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
import support.ussd.model.Ciudad;
import support.ussd.model.Sucursal;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class LiberacionARBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(LiberacionARBean.class);
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
	
	private List<String> lineasArchivo;
	List<Sucursal> localidades;
	
	private byte[] exportContent;
	
	private List<String> cuentas;
	private String cuenta;
	
	private boolean visibleNuevoEditar;
	private boolean visibleDisponibilidad;
	
	private List<Ciudad> listaCiudades;
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
		
			lineasArchivo= new ArrayList<String>();
			fillSelectItems();
			cuentas= new ArrayList<String>();
			listaCiudades= new ArrayList<Ciudad>();
			this.visibleNuevoEditar = false;
			this.visibleDisponibilidad = false;
			
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
	
	
	
	public void liberarCuentasPos(){
		
		try {
			
			for (Sucursal s : localidades) {
				if(s.getId().equalsIgnoreCase(localidadSeleccionada)){
					localidadSeleccionada=s.getCod();
				}
				
			}
			
			
			
			if(cuentas.isEmpty()){
				SysMessage.warn("No se tiene ninguna cuenta para liberar");
				return;
			}
			
			ServiciosI ser= new ServiciosI();
			Result res;
			String ccuentas="";
			for (String linea : cuentas) {
				
				ccuentas=ccuentas.concat(linea+",");
						
			}
			ccuentas = ccuentas.substring(0, ccuentas.length() - 1);
			if(selectA.equalsIgnoreCase("POS")){///Si se va liberar cuentas pos			
				HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
				java. util. Date fecha = new Date();
		    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String text= "Lib POS AREG "+strIdUs+sdf.format(fecha);
			    
				res=ser.liberarPostPagaReservadaAlibre(ccuentas,text);
				if(!res.getCode().equalsIgnoreCase(Code.OK)){
					SysMessage.error("Error al liberar las cuentas postpago reservadas");
					log.error("Error al liberar la cuenta  "+ccuentas+ " postpago reservadas "+res.getDescription());
					return;
				}
				
				for (String linea : cuentas) {
					controlerBitacora.accion(DescriptorBitacora.LIBERACION_AR, "Se libero la cuenta "+linea+" postpago reservada");			
				}
				
				
				log.info("Se Libero las cuentas postpago reservadas ");
				SysMessage.info("Terminó el proceso de liberacion de las cuentas pospago reservadas correctamente");
				
		
			}else{
	
				HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			    String strIdUs = (String)request.getSession().getAttribute("TEMP$USER_NAME");
			    java. util. Date fecha = new Date();
		    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String text= "Cam PRE-POS AREG "+strIdUs+sdf.format(fecha);
				res=ser.actualizarPREaPOSReservada(ccuentas,text);
				if(!res.getCode().equalsIgnoreCase(Code.OK)){
					SysMessage.error("Error al cambiar de modalidad las cuentas prepago reservadas");
					log.error("Error al cambiar de modalidad la cuenta  "+ccuentas+ " prepago reservadas "+res.getDescription());
					return;
				}
				
				for (String linea : cuentas) {
					controlerBitacora.accion(DescriptorBitacora.LIBERACION_AR2, "Se cambio de modalidad la cuenta "+linea+" prepago reservada");			
				}
				
				
				log.info("Se cambio de modalidad las cuentas prepago reservadas ");
				SysMessage.info("Terminó el proceso de cambio de modaliad a POS de las cuentas prepago reservadas correctamente");
			}
			
			select="-1";
			selectA="-1";
		
			setVisibleNuevoEditar(false);
		} catch (Exception e) {
			log.error("Error al actulizar las cuentas",e);
			SysMessage.error("Error al actualizar o liberar las cuentas, consulte con el administrador");
		}
		
	}
	
	public void liberarCuentasReservadas(){
		
		try {
		ServiciosI s= new ServiciosI();
		Result res = s.liberarCuentasReservadas();
		if(!res.getCode().equalsIgnoreCase(Code.OK)){
		  log.error("Error al liberar la cuentas: "+res.getDescription());
		  return;
		}
		log.info("Se libero las cuentas reservadas para el activador");
	} catch (Exception e) {
		log.error("Error al liberar la cuentas reservadas",e);
	}
	
	}
	
	public void consultarDisponibilidad(){
		
		try {
			
			ServiciosI s= new ServiciosI();			
			Result res=s.consultarDisponibilidad();
	
			
			Type type = new TypeToken<List<Ciudad>>(){}.getType();
			this.listaCiudades=gson.fromJson((String)res.getData(),type);	
//			for (Ciudad a : listaCiudades) {
//				System.out.println(a.getCiudad()+":"+a.getCantidad());
//			}
			//this.visibleDisponibilidad=true;
			
			setVisibleDisponibilidad(true);
			
		} catch (Exception e) {
			log.error("Error al consultar la disponibilidad",e);
			SysMessage.error("Error al consultar la disponibilidad");
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
			
			ServiciosI ser= new ServiciosI();
			
			//Result res= ser.obtenerCuentasCongeladasBCCS(select, selectA, Integer.valueOf(cantidad));
			cuentas.clear();
			if(selectA.equalsIgnoreCase("POS")){
				Result ron=ser.obtenerCantidadCuentasPostPagoReservadoBCCS(select, 50);				
				Integer cant = (Integer)ron.getData();
				if(cant!=0){
				Result res= ser.obtenerCuentasPostPagoReservadoBCCS(select,50);
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("Error al momento de obtener las cuentas PostPaga Reseservadas: "+res.getDescription());
						SysMessage.error("Error al momento de obtener las cuentas PostPaga Reseservada");
						return;
					}
					String exp=(String) res.getData();
					if(exp.isEmpty()){
						SysMessage.info("No hay cuentas para esta localidad");
						select="-1";
						selectA="-1";
						return;
					}
					//exportContent = exp.getBytes();
					StringTokenizer tokens = new StringTokenizer(exp.trim(),",");
					while(tokens.hasMoreTokens()){
					cuentas.add(tokens.nextToken());
					}
				
				
					this.visibleNuevoEditar = true;
					//this.visibleDisponibilidad = true;
					controlerBitacora.accion(DescriptorBitacora.LIBERACION_AR, "Se obtuvieron cuentas post pago reservadas de la plataformas BCCS");
				}else{
					log.info("No existe cuentas Reservadas para liberar en dicha sucursal, se sugiere la opcion PRE mediante autorizaciones");
					log.error("No existe cuentas Reservadas para liberar en dicha sucursal, se sugiere la opcion PRE mediante autorizaciones");
					SysMessage.warn(ron.getDescription());
					return;
				}
			}else{
				//Result res= ser.obtenerCuentasPrePagoReservadoBCCS(select, Integer.valueOf(cantidad));
					Result res=ser.obtenerCantidadCuentasPrePagoReservadoBCCS(select, 50);				
					if((Integer)res.getData()==0){
						SysMessage.warn("No hay cuentas prepagos para liberar en este momento");
						return;
					}
					res= ser.obtenerCuentasBCCS(select);
					if(!res.getCode().equalsIgnoreCase(Code.OK)){
						log.error("Error al momento de obtener las prepaga reservadas : "+res.getDescription());
						SysMessage.error(res.getDescription());
						return;
					}
					String exp=(String) res.getData();
					if(exp.isEmpty()){
						SysMessage.info("No hay cuentas Prepagos para esta localidad");
						select="-1";
						selectA="-1";
					
						return;
					}
					exp=exp.replace("                    ", ",");

					StringTokenizer tokens = new StringTokenizer(exp.trim(),",");
					while(tokens.hasMoreTokens()){
						cuentas.add(tokens.nextToken());
					}
				
				//exportContent = exp.getBytes();
				
				this.visibleNuevoEditar = true;
				controlerBitacora.accion(DescriptorBitacora.LIBERACION_AR2, "Se obtuvieron cuentas prepaga reservadas de la plataformas BCCS");
				}
//				else{
//					log.info("No existe cuentas Prepagas para liberar en dicha sucursal");
//					log.error("No existe cuentas Prepagas para liberar en dicha sucursal");
//					SysMessage.warn(ron.getDescription());
//					return;
//				}
//			}
			
			SysMessage.info("se obtuvieron las cuentas correctamente");
			
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

public String deleteRoleUser(String row)
{
  if (!row.isEmpty())
  {
    
    try
    {
    	cuentas.remove(row);
      
    	SysMessage.info("Se elimino correctamente.");
      
    }
    catch (Exception e)
    {
      log.error("[deleteRoleUser]  error al eliminar la cuenta " + row + "  " + e);
      SysMessage.error("Fallo al eliminar.");
    }
  }
  else
  {
    log.warn("[eliminar] No se encontro ningun registro seleccionado.");
    SysMessage.warn("No se encontro ningun registro seleccionado.");
  }
  return "";
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

	public List<String> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<String> cuentas) {
		this.cuentas = cuentas;
	}

	public boolean isVisibleNuevoEditar() {
		return visibleNuevoEditar;
	}

	public void setVisibleNuevoEditar(boolean visibleNuevoEditar) {
		this.visibleNuevoEditar = visibleNuevoEditar;
		localidadSeleccionada=select;
		select="-1";
		selectA="-1";
		cuentas.clear();
		try {
			ServiciosI s= new ServiciosI();
			Result res = s.liberarCuentasReservadas();
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
			  log.error("Error al liberar la cuentas: "+res.getDescription());
			  return;
			}
			log.info("Se libero las cuentas reservadas para el activador");
		} catch (Exception e) {
			log.error("Error al liberar la cuentas reservadas",e);
		}
		
		
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	

	public boolean isVisibleDisponibilidad() {
		return visibleDisponibilidad;
	}

	public void setVisibleDisponibilidad(boolean visibleDisponibilidad) {
		this.visibleDisponibilidad = visibleDisponibilidad;
	}



	

	
	
	public List<Ciudad> getListaCiudades() {
		return listaCiudades;
	}

	public void setListaCiudades(List<Ciudad> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}



	





	
	
}
