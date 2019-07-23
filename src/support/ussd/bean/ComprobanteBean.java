package support.ussd.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import servicios.Result;
import support.id.FormularioID;
import support.id.ParametroID;
import support.user.bean.ControlerBitacora;
import support.user.controler.ControlPrivilegio;
import support.user.ldap.DescriptorBitacora;
import support.user.sys.IControlPrivilegios;
import support.user.sys.P;
import support.ussd.business.fechaBL;
import support.ussd.model.TFecha;
import support.util.Code;
import support.ws.servicios.ServiciosI;

import com.tigo.utils.SysMessage;

@ManagedBean
@ViewScoped
public class ComprobanteBean implements Serializable, IControlPrivilegios {
	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(ComprobanteBean.class);
	ControlPrivilegio controlPrivilegio;


	@Inject
	private ControlerBitacora controlerBitacora;
	
	@Inject
	private fechaBL feBL;
			
	private String radioSeleccionado;
	
	private List<String> selectItems;
	
	private String comprobante;
	private String serie;
	private String numero;
	private Date fechaIni;
	private Date fechaFin;
	
	private int sucursal;
	private int local;
	private int deposito;
	private int codigProducto;
	
	private SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		
	@PostConstruct
	public void init() {
		try {
			this.controlPrivilegio = ControlPrivilegio.getInstanceControl();
			fechaIni=getPrimerDiaDelMes();
			fechaFin=getUltimoDiaDelMes();
			radioSeleccionado="1";
			comprobante="";
			serie="";
			numero="";
			sucursal=0;
			local=0;
			deposito=0;
			codigProducto=0;
			fillSelectItems();
		} catch (Exception e) {
			log.error("[init] Fallo en el init.", e);
		}
	}
	
	public  Date getPrimerDiaDelMes() {

        Calendar cal = Calendar.getInstance();

        cal.set(cal.get(Calendar.YEAR),

                cal.get(Calendar.MONTH),

                cal.getActualMinimum(Calendar.DAY_OF_MONTH),

                cal.getMinimum(Calendar.HOUR_OF_DAY),

                cal.getMinimum(Calendar.MINUTE),

                cal.getMinimum(Calendar.SECOND));

        return cal.getTime();

    }

 

    public  Date getUltimoDiaDelMes() {

        Calendar cal = Calendar.getInstance();

        cal.set(cal.get(Calendar.YEAR),

                cal.get(Calendar.MONTH),

                cal.getActualMaximum(Calendar.DAY_OF_MONTH),

                cal.getMaximum(Calendar.HOUR_OF_DAY),

                cal.getMaximum(Calendar.MINUTE),

                cal.getMaximum(Calendar.SECOND));

        return cal.getTime();

    }
	
	private void fillSelectItems()throws Exception{
		
		 	this.selectItems = new ArrayList<String>();
			this.selectItems.add("-- Seleccionar Comprobante --");
			String comp=(String)P.getParamVal(ParametroID.COMPROBANTES);
			StringTokenizer tokens = new StringTokenizer(comp,",");
			while(tokens.hasMoreTokens()){
					
					 this.selectItems.add(tokens.nextToken());
			}
	}
	
	public String check() throws Exception{
		return radioSeleccionado;
	}
	
	public void guardarAnular() {
		
		try {
			ServiciosI ws= new ServiciosI();
			Result res=ws.anular(comprobante, serie, numero);
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.error(mensajeAnular(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
				return;
			}
			SysMessage.info(mensajeAnular(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
			this.controlerBitacora.accion(DescriptorBitacora.ANULAR,"se anulo el comprobante: "+comprobante+"-"+serie+"-"+numero);
			newComprobante();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al anular: "+e.getMessage());
			SysMessage.error("Error al anular");
			return ;
		}
	}
	
	public void guardarCambioEstado() {
		
		try {
			ServiciosI ws= new ServiciosI();
			Result res=ws.cambioEstado(comprobante, serie, numero);
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.error(mensajeCambioEstado(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
				return;
			}
			SysMessage.info(mensajeCambioEstado(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
			this.controlerBitacora.accion(DescriptorBitacora.CAMBIOE,"se cambio de estado el comprobante: "+comprobante+"-"+serie+"-"+numero);
			newComprobante();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en el cambio de estado: "+e.getMessage());
			SysMessage.error("Error en el cambio de estado");
		}
	}
	
	public void nivelacionBodega() {
		
		try {
			if(sucursal==0 ||local==0||deposito==0||codigProducto==0){
				SysMessage.warn("Los campos no deben ser 0");
				return;
			}
			
			ServiciosI ws= new ServiciosI();
			Result res=ws.nivelacionBodega(sucursal, local, deposito, codigProducto);
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.error(mensajeNivelacionBodega(res.getDescription())+","+sucursal+"-"+local+"-"+deposito+"-"+codigProducto);
				return;
			}
			SysMessage.info(mensajeNivelacionBodega(res.getDescription())+","+sucursal+"-"+local+"-"+deposito+"-"+codigProducto);
			this.controlerBitacora.accion(DescriptorBitacora.NIVBOD,"se nivelo la bodega: "+sucursal+"-"+local+"-"+deposito+"-"+codigProducto);
			newComprobante();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en la nivelacion de bodega: "+e.getMessage());
			SysMessage.error("Error en la nivelacion de bodega");
		}
	}
	
	public void guardarCambioFecha() {
	
		try {
			if(comprobante.equals("-- Seleccionar Comprobante --")){
				SysMessage.warn("Debe Seleccionar un comprobante");
				return;
			}
			
			if (fechaIni == null || fechaFin == null) {
			SysMessage.warn("Debe seleccionar fechas");
	
				return;
			}
			
			ServiciosI ws= new ServiciosI();
			Result res=ws.cambioFecha(comprobante, serie, numero,formateador.format(fechaIni),formateador.format(fechaFin));
			if(!res.getCode().equalsIgnoreCase(Code.OK)){
				SysMessage.error(mensajeCambioFecha(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
				return;
			}
			feBL.save(comprobante, serie, numero, (String)res.getData());
			
			SysMessage.info(mensajeCambioFecha(res.getDescription())+","+comprobante+"-"+serie+"-"+numero);
			this.controlerBitacora.accion(DescriptorBitacora.CAMBIOF,"se cambio la fecha del comprobante: "+comprobante+"-"+serie+"-"+numero);
			newComprobante();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en el cambio de fecha: "+e.getMessage());
			SysMessage.error("Error en el cambio de fecha");
			return ;
		}
	}
	
	public void volverFecha() {
		
		try {
			List<TFecha> lista = feBL.getListPen();
			
			for (TFecha a : lista) {
				ServiciosI ws= new ServiciosI();
				Result res=ws.volverFecha(a.getComprobante(), a.getSerie(), a.getNumero(), a.getFechaAnterior());
				if(!res.getCode().equalsIgnoreCase(Code.OK)){
					this.controlerBitacora.accion(DescriptorBitacora.VOLVERF,"error al volver a la fecha "+a.getFechaAnterior()+" el comprobante: "+a.getComprobante()+"-"+a.getSerie()+"-"+a.getNumero());
					SysMessage.error("error al cambiar fecha comprobante: "+a.getComprobante()+"-"+a.getSerie()+"-"+a.getNumero());
					return;
				}
				this.controlerBitacora.accion(DescriptorBitacora.VOLVERF,"se volvio a la fecha "+a.getFechaAnterior()+" el comprobante: "+a.getComprobante()+"-"+a.getSerie()+"-"+a.getNumero());
				feBL.update(a);
			}	
			
			SysMessage.info("Finalizo el proceso");

			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en el cambio de fecha: "+e.getMessage());
			SysMessage.error("Error en el cambio de fecha");
			return ;
		}
	}
	public String mensajeAnular(String valor){
		String result="";
		 try {
			 int caso = Integer.parseInt(valor);
	         switch (caso){
	             
	                 case 0:
	                     result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_0);
	                         break;
	                 case 1:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_1);
	                         break;
	                 case 2:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_2);
	                         break;    
	                 case 3:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_3);
	                         break;  
	                 case 4:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_4);
	                         break;  
	                 case 5:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_5);
	                         break;  
	                 case 6:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_ANULAR_6);
	                         break;  
	         }
	         return result;
		} catch (Exception e) {
			return valor;
		}
		
	}
	
	public String mensajeNivelacionBodega(String valor){
		String result="";
		 try {
			 int caso = Integer.parseInt(valor);
	         switch (caso){
	             	
	                 case 0:
	                     result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_0);
	                         break;
	                 case 1:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_1);
	                         break;
	                 case 2:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_2);
	                         break;    
	                 case 3:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_3);
	                         break;  
	                 case 5:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_5);
	                         break;  
	                 case 6:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_6);
	                         break;
	                 case 7:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_7);
	                         break;  
	                 case 8:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_NIVELACION_8);
	                         break;  
	         }
	         return result;
		} catch (Exception e) {
			return valor;
		}
		
		
	}
	
	public String mensajeCambioEstado(String valor){
		String result="";
		 try {
			 int caso = Integer.parseInt(valor);
	         switch (caso){
	             	
	                 case 0:
	                     result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_0);
	                         break;
	                 case 1:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_1);
	                         break;
	                 case 2:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_2);
	                         break;    
	                 case 3:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_3);
	                         break;  
	                 case 4:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_4);
	                         break;  
	                 case 5:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_ESTADO_5);
	                         break;  
	         }
	         return result;
		} catch (Exception e) {
			return valor;
		}
		
		
	}
	public String mensajeCambioFecha(String valor){
		String result="";
		 try {
			 int caso = Integer.parseInt(valor);
	         switch (caso){
	             	
	         		 case -1:
	         			result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA);
	         				 break;
	                 case 0:
	                     result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_0);
	                         break;
	                 case 1:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_1);
	                         break;
	                 case 2:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_2);
	                         break;    
	                 case 3:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_3);
	                         break;  
	                 case 4:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_4);
	                         break;  
	                 case 5:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_5);
	                         break;  
	                 case 6:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_6);
	                         break;
	                 case 7:
	                	 result=(String) P.getParamVal(ParametroID.TEXTO_RESPUESTA_CAMBIO_FECHA_7);
	                         break;
	         }
	         return result;
		} catch (Exception e) {
			return valor;
		}
	}

//	public void add() throws NumberFormatException, Exception {
//		this.det = new TRol();
//		this.editDetalle=false;
//		this.visibleNuevoEditarDetalle = true;
//	}

//	public void guardarDetalle() {
//
//		try {
//			if(det.getIdrol()==0){
//				SysMessage.warn("El id rol no puede ser cero");
//				return ;
//			}
//			
//		
//			if (!this.editDetalle) {
//				det.setTPlataforma(pla);
//				det.setEstado(true);
//				this.listRol.add(this.det);
//				this.visibleNuevoEditarDetalle = false;
//			} else {
//				for (TRol a : this.listRol) {
//					
//					if(detId.equals("null")){
//						if (a.getNombre().equals(det.getNombre())) {
//							a.setTPlataforma(pla);
//							a.setNombre(det.getNombre());
//							a.setDescripcion(det.getDescripcion());	
//							a.setEstado(det.getEstado());
//						}
//					}else{
//						if (a.getId().longValue() == Long.parseLong(this.detId)) {
//							a.setTPlataforma(pla);
//							a.setNombre(det.getNombre());
//							a.setDescripcion(det.getDescripcion());	
//							a.setEstado(det.getEstado());
//						}
//					}
//					
//					
//				}
//				this.visibleNuevoEditarDetalle = false;
//			}
//		} catch (Exception localException) {
//			localException.printStackTrace();
//		}
//	}
//
//	public String deleteDetalle() {
//		if (this.det != null) {
//			try {
//				int pos = 0;
//				int r = 0;
//				for (TRol a : this.listRol) {
//					if (a.getId() == this.det.getId()) {
//						r = pos;
//						SysMessage.info("Se eliminó correctamente.");
//					}
//					pos++;
//				}
//				this.listRol.remove(r);
//			} catch (Exception e) {
//				log.error("[deletePlataforma]  error al eliminar el rol"
//						+ this.pla.getId() + "  " + e);
//				e.printStackTrace();
//				SysMessage.error("Fallo al eliminar.");
//			}
//		} else {
//			log.warn("[eliminar] No se encontró ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//		return "";
//	}
//
//	public String delete() {
//		if (this.pla != null) {
//			try {
//				//this.pla.setBaja(Boolean.valueOf(false));
//				this.platBL.delete(this.pla);
//				this.controlerBitacora.delete(DescriptorBitacora.PLATAFORMA,
//						this.pla.getId() + "", "");
//				this.listPlat = this.platBL.getList();
//				SysMessage.info("Se eliminó correctamente.");
//			} catch (Exception e) {
//				log.error("[deletePlataforma]  error al eliminar el plauerimiento"
//						+ this.pla.getId() + "  " + e);
//				e.printStackTrace();
//				SysMessage.error("Fallo al eliminar.");
//			}
//		} else {
//			log.warn("[eliminar] No se encontro ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//		return "";
//	}
//
//	public String deleteDetalle(TRol plau) {
//		this.det = plau;
//		return deleteDetalle();
//	}

//	public String delete(TPlataforma plau) {
//		this.pla = plau;
//		return delete();
//	}

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

	public void newComprobante() throws Exception {
		fechaIni=getPrimerDiaDelMes();
		fechaFin=getUltimoDiaDelMes();
		radioSeleccionado="1";
		comprobante="";
		serie="";
		numero="";
		fillSelectItems();
	}
	
	public String textoAnular(){
		return (String) P.getParamVal(ParametroID.TEXTO_ANULAR);
	}
	
	public String textoCambioEstado(){
		return (String) P.getParamVal(ParametroID.TEXTO_CAMBIO_ESTADO);
	}
	
	public String textoCambioFecha(){
		return (String) P.getParamVal(ParametroID.TEXTO_CAMBIO_FECHA);
	}
	
//	public void ediTPlataforma() {
//		
//		try {
//			if (this.pla != null) {
//				this.visibleNuevoEditar = true;
//				this.plaId = this.pla.getId() + "";
//				//this.listRol = new ArrayList<TRol>();
//				listRol=platBL.getListRolPlataforma(pla.getId());
//
//				this.edit = true;
//			} else {
//				log.warn("[editar] No se encontro ningun registro seleccionado.");
//				SysMessage.warn("No se encontro ningun registro seleccionado.");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			SysMessage.error("error al momento de obtener los datos para editar");
//		}
//		
//	}
//
//	public void ediTPlataforma(TPlataforma muRol) {
//		this.pla = muRol;
//		ediTPlataforma();
//	}
//
//	public void editDetalle() {
//		if (this.det != null) {
//			this.visibleNuevoEditarDetalle = true;
//			this.detId = this.det.getId() + "";
//			this.editDetalle = true;
//		} else {
//			log.warn("[editar] No se encontro ningun registro seleccionado.");
//			SysMessage.warn("No se encontró ningun registro seleccionado.");
//		}
//	}
//
//	public void editDetalle(TRol muRol) {
//		this.det = muRol;
//		editDetalle();
//	}

	

	public boolean isAuthorized(int idAccion) {
		return this.controlPrivilegio.isAuthorized(FormularioID.FORM_GEST_ALTA, idAccion);
	}




	public String getRadioSeleccionado() {
		return radioSeleccionado;
	}




	public void setRadioSeleccionado(String radioSeleccionado) {
		this.radioSeleccionado = radioSeleccionado;
	}


	public String getComprobante() {
		return comprobante;
	}


	public void setComprobante(String comprobante) {
		this.comprobante = comprobante;
	}


	public String getSerie() {
		return serie;
	}


	public void setSerie(String serie) {
		this.serie = serie;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<String> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<String> selectItems) {
		this.selectItems = selectItems;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getSucursal() {
		return sucursal;
	}

	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getDeposito() {
		return deposito;
	}

	public void setDeposito(int deposito) {
		this.deposito = deposito;
	}

	public int getCodigProducto() {
		return codigProducto;
	}

	public void setCodigProducto(int codigProducto) {
		this.codigProducto = codigProducto;
	}


	
	
	

}
