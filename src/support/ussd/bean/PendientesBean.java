package support.ussd.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.tigo.utils.SysMessage;

import support.user.business.UsuarioBL;
import support.user.model.MuUsuario;
import support.ussd.business.ServiciosBL;
import support.ussd.business.TAbmBL;
import support.ussd.model.AdministradorAbm;
import support.ussd.model.TAbmAdm;
import support.ussd.model.TAbmSer;
import support.ussd.model.TSerAdm;
import support.ussd.model.TServicio;

@ManagedBean
@ViewScoped
public class PendientesBean
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static Logger log = Logger.getLogger(PendientesBean.class);

  
  private List<TServicio> tsaux;
	private List<TAbmAdm> listAdm;
	private List<TAbmSer> listAdm2;
	
	private TAbmAdm adm;
  
	@Inject
	private TAbmBL abmBL;
	
	@Inject
	private ServiciosBL servBL;
	
	@Inject
	private UsuarioBL usuBL;
	
	private HashMap<Long,String> hm;
	private HashMap<String, List<String>> ab;
	
	private List<AdministradorAbm> pp;
	private List<AdministradorAbm> ppAux;
	
  @PostConstruct
  public void init()
  {
    try
    {
    	obtenerPendientes2();
    }
    catch (Exception e)
    {
      log.error("[init] Fallo al iniciar el bean.", e);
    }
  }
 
 private abstract class par{
	  private Long idServicio;
	  private String admin;
	  
	  public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	  
	  
  }
  
  public void obtenerPendientes(){
	  
	  	try {
	  		
	  		par pr;
	  		List<par> lispr= new ArrayList<PendientesBean.par>();
	  		
	  		hm= new HashMap<Long, String>();
		  listAdm=abmBL.getListServicioPorABM((long)1);
	  		for (TAbmAdm a : listAdm) {
	  			TServicio s= servBL.getServicio(a.getId().getIdServicio());
	  			if(s!=null){
	  				for (TSerAdm b : s.getTSerAdms()) {
		  				if(a.getEstado().equalsIgnoreCase("PENDIENTE")){
		  					MuUsuario u= usuBL.getUser(b.getId().getIdAdm());  					 						
		  					//hm.put(b.getTServicio().getId(),u.getCorreo());
		  					pr= new par() {};
		  					pr.setIdServicio(b.getTServicio().getId());
		  					pr.setAdmin(u.getCorreo());
		  					lispr.add(pr);
		  				}
		  				
					}
	  			}
			}
	  		
	  		ab= new HashMap<String, List<String>>();
	  		
	  		for (par par : lispr) {
	  			List<String> ls=new ArrayList<String>();
			 	for (TAbmAdm a : listAdm) {
					if(par.getIdServicio().equals(a.getTServicio().getId())){
						if(a.getEstado().equalsIgnoreCase("PENDIENTE")){
							if(ab.containsKey(par.getAdmin())){
								ls=ab.get(par.getAdmin());
								if(!ls.contains(a.getTAbm().getAbm().toString())){
									ls.add(a.getTAbm().getAbm().toString());
								}						
								ab.put(par.getAdmin(), ls);
							}else{
								ls.add(a.getTAbm().getAbm().toString());
								ab.put(par.getAdmin(), ls);
							}
						}
					}
				}
			}
	  		
//	  		for (Entry<Long, String> entry : hm.entrySet()) {
//			    Long key = entry.getKey();
//			   String value = entry.getValue();
//			    	
//			}
	  		pp=new ArrayList<AdministradorAbm>();
	  		for (Entry<String, List<String>> entry : ab.entrySet()) {
			    String key = entry.getKey();
			    List<String> value = entry.getValue();
			    AdministradorAbm aa= new AdministradorAbm();
			    aa.setAdmin(key);
			    aa.setAbms(value);
			    pp.add(aa);
			}
		  
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	  
	  
//	  List<TSerAdm>tt=servBL.getListAdmPorServicio(1);
//		for (TSerAdm tSerAdm : tt) {
//			MuUsuario u= usuBL.getUser(tSerAdm.getId().getIdAdm().intValue());
//			hm.put((long)u.getUsuarioId(),u);	
//		}
  }
  
  public void obtenerPendientes2(){
	  
	  	try {

		  listAdm2=abmBL.obtenerServPendienteAllAbm();
		  List<TAbmSer> luax= new ArrayList<TAbmSer>();
		  pp= new ArrayList<AdministradorAbm>();
		  HashMap<Long, Long> usrHM= new HashMap<Long,Long>();
		  for (TAbmSer a : listAdm2) {
			  if(a.getIdUsuario()!=null){
				  
				  usrHM.put(a.getIdUsuario(),a.getIdUsuario() ); 
			  }
			  
		  }
		  int c=1;
		  for (Entry<Long,Long> entry : usrHM.entrySet()) {
			  Long key = entry.getKey();
			  luax=abmBL.obtenerServPendienteXusuario(key);
			  List<String> listabm=new ArrayList<String>();
			  for (TAbmSer a : luax) {
				 String abm=String.valueOf(abmBL.get(a.getTAbm().getId()).getAbm().toString());
				 if(!listabm.contains(abm)){
					 listabm.add(abm);
				}
				 
			  }
			  AdministradorAbm aa= new AdministradorAbm();
			  MuUsuario u= usuBL.getUser(key.longValue());
			   aa.setAdmin(u.getCorreo());
			   aa.setSupervisor(u.getMuUsuario().getCorreo());
			   aa.setAbms(listabm);
			   pp.add(aa);
			   c=c+1;
			}
	  		ppAux=pp;
		  
		} catch (Exception e) {
			e.printStackTrace();
		}

}

  public void exportar(){
	    
//	  	if (!ppAux.isEmpty()){
//	  		pp= new ArrayList<AdministradorAbm>();
//	  		pp=ppAux;
//	  	}
	  	
		HSSFWorkbook libro = new HSSFWorkbook();

		log.info("[exportar] se va exportar los pendientes");

		HSSFSheet hoja = libro.createSheet();

		HSSFCellStyle curStyle2 = libro.createCellStyle();
		HSSFCellStyle curStyle3 = libro.createCellStyle();

		HSSFFont font = libro.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setUnderline(HSSFFont.U_SINGLE);
		font.setFontHeightInPoints((short) 9);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		HSSFFont font2 = libro.createFont();
		font2.setFontName(HSSFFont.FONT_ARIAL);
		font2.setFontHeightInPoints((short) 9);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		curStyle2.setBorderTop((short) 1); // single line border
		curStyle2.setBorderLeft((short) 1);
		curStyle2.setBorderRight((short) 1);
		curStyle2.setBorderBottom((short) 1); // single line border
		curStyle2.setFont(font2);

		curStyle3.setFillPattern(HSSFColor.YELLOW.index);


		curStyle3.setBorderTop((short) 1); // single line border
		curStyle3.setBorderLeft((short) 1);
		curStyle3.setBorderRight((short) 1);
		curStyle3.setBorderBottom((short) 1); // single line border
		curStyle3.setFont(font2);
		
		HSSFRow fila = hoja.createRow(0);
		HSSFCell celda = fila.createCell((short) 0);
		
		HSSFRichTextString texto = new HSSFRichTextString("prueba");
	
		celda.setCellStyle(curStyle2);


		//List<Matriz> lstmat = objetoMatriz.getFilas();
		//Matriz ma = lstmat.get(c);
		fila = hoja.createRow(0);
		celda = fila.createCell((short) 0);
		texto = new HSSFRichTextString("ADMINISTRADORES");
		celda.setCellValue(texto);
		celda.setCellStyle(curStyle3);
		
		celda = fila.createCell((short) 1);
		texto = new HSSFRichTextString("ABM's");
		celda.setCellValue(texto);
		celda.setCellStyle(curStyle3);


		int c = 0;
		int cc = 0;
		for (AdministradorAbm a: ppAux){

			try{
				cc = cc + 1;
				int sw = 0;
			
					fila = hoja.createRow(cc);
					celda = fila.createCell((short) 0);
					texto = new HSSFRichTextString(a.getAdmin());
					celda.setCellValue(texto);
					celda.setCellStyle(curStyle2);
					
					celda = fila.createCell((short) 1);
					String abm="[";
					for (String b : a.getAbms()) {
						abm=abm+","+b;
					}
					abm=abm+"]";
					texto = new HSSFRichTextString(abm);
					celda.setCellValue(texto);
					celda.setCellStyle(curStyle2);

				c = c + 1;
				//log.info("[exportar] se armo el archivo excel con los datos correctamente");
			}
			
			catch (Exception e){
				SysMessage.error("Error al armar el excel con los datos");
				log.error("Error al armar el excel con los datos",e);
				return;
			}

		}

		try{



			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();

			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

			response.setHeader("Content-Disposition", "attachment; filename=\"Pendientes.xls\"");
			response.setContentType("application/vnd.ms-excel");

			libro.write(response.getOutputStream());
			facesContext.responseComplete();
			log.info("[exportar] se exporto correctamente los abm's pendientes");
			
			SysMessage.info("Se exporto Correctamente");

			return;
		}
		catch (Exception e){
			
			log.error("[exportar] no se exporto correctamente lso pendientes",e);
			SysMessage.info("Se exporto Correctamente");

			return;
		}
	}


  
public List<TServicio> getTsaux() {
	return tsaux;
}

public void setTsaux(List<TServicio> tsaux) {
	this.tsaux = tsaux;
}

public List<TAbmAdm> getListAdm() {
	return listAdm;
}

public void setListAdm(List<TAbmAdm> listAdm) {
	this.listAdm = listAdm;
}

public TAbmAdm getAdm() {
	return adm;
}

public void setAdm(TAbmAdm adm) {
	this.adm = adm;
}

public HashMap<Long, String> getHm() {
	return hm;
}

public void setHm(HashMap<Long, String> hm) {
	this.hm = hm;
}

public HashMap<String, List<String>> getAb() {
	return ab;
}

public void setAb(HashMap<String, List<String>> ab) {
	this.ab = ab;
}

public List<AdministradorAbm> getPp() {
	return pp;
}

public void setPp(List<AdministradorAbm> pp) {
	this.pp = pp;
}

public List<AdministradorAbm> getPpAux() {
	return ppAux;
}

public void setPpAux(List<AdministradorAbm> ppAux) {
	this.ppAux = ppAux;
}




  
 
}

