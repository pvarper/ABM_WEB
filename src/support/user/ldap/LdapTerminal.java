package support.user.ldap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import support.id.ParametroID;
import support.user.sys.P;
import support.ussd.model.TUsuarioAtributo;
import support.util.Result;

public class LdapTerminal {

	public static final String USER_NOMBRE = "givenName";
	public static final String USER_APELLIDO = "sn";
	public static final String USER_EMAIL = "mail";
	public static final String USER_LOGIN = "sAMAccountName";
	public static final String USER_NOMBRE_COMPLETO = "cn";
	public static final String USER_COD_EHUMANO = "initials";
	Hashtable<String, String> env;
	LdapContext ctx;
	SearchControls searchCtls;
	String searchFilter;
	String searchBase;
	private Conexion conexion;

	public LdapTerminal(Conexion conexion) throws UnsupportedEncodingException {
		this.conexion = conexion;
		IniciarTer();
		//IniciarTerGuardar();
	}

	// public void Iniciar() {
	// this.env = new Hashtable<String, String>();
	// this.env.put("java.naming.factory.initial",
	// "com.sun.jndi.ldap.LdapCtxFactory");
	//
	// this.env.put("java.naming.security.authentication", "simple");
	// this.env.put("java.naming.security.principal", this.conexion.getDominio()
	// + "\\" + this.conexion.getUsuario());
	// this.env.put("java.naming.security.credentials",
	// this.conexion.getClave());
	//
	// this.env.put("java.naming.provider.url", this.conexion.getUrl());
	// }

	public void IniciarTer() throws UnsupportedEncodingException {
		this.env = new Hashtable<String, String>();
	
		System.out.println("antes del path");
		//version de produccion
		String path = (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dir_keystore);
		//version de prueba
		//String path = "E:\\pedro\\PEDRO\\workspace\\ABM_WEB\\src\\support\\user\\ldap\\ldap.jks";
		System.out.println(path);
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		String keystore = decodedPath;
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_default_pass));

		this.env.put("java.naming.factory.initial","com.sun.jndi.ldap.LdapCtxFactory");
		this.env.put("java.naming.security.authentication", "simple");
		this.env.put("java.naming.security.principal",this.conexion.getUsuario());
		this.env.put("java.naming.security.credentials",this.conexion.getClave());
		this.env.put("java.naming.security.protocol", "ssl");
		this.env.put("java.naming.provider.url", this.conexion.getUrl());

		// File fichero = new
		// File((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dir_keystore));
		// String path=fichero.getAbsolutePath();
		// U asd=this.getClass().getResource(System.getProperty("user.dir"));

	}
	
	public void IniciarTerGuardar() throws UnsupportedEncodingException {
		this.env = new Hashtable<String, String>();
	
		String path = this.getClass().getResource((String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dir_keystore)).getPath();
		System.out.println(path);
		String decodedPath = URLDecoder.decode(path, "UTF-8");
		String keystore = decodedPath;
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_default_pass));

		this.env.put("java.naming.factory.initial","com.sun.jndi.ldap.LdapCtxFactory");
		this.env.put("java.naming.security.authentication", "simple");
		this.env.put("java.naming.security.principal","CN=Administrator,CN=Users,DC=tsbolivia,DC=com,DC=bo");
		this.env.put("java.naming.security.credentials",this.conexion.getClave());
		this.env.put("java.naming.security.protocol", "ssl");
		this.env.put("java.naming.provider.url", this.conexion.getUrl());

		// File fichero = new
		// File((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dir_keystore));
		// String path=fichero.getAbsolutePath();
		// U asd=this.getClass().getResource(System.getProperty("user.dir"));

	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, String> obtenerDatosTerminal(String usuarioE,
			String[] datosReturn) throws Exception {
		Map<String, String> mapa = new HashMap<String, String>();
		try {
			this.ctx = new InitialLdapContext(this.env, null);

			this.searchBase = "DC=tsbolivia,DC=com,DC=bo";

			this.searchCtls = new SearchControls();

			this.searchCtls.setSearchScope(2);

			this.searchFilter = ("(&(objectCategory=person)(objectClass=user)(sAMAccountName="+ usuarioE + "))");
								// "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + usuario + "))")	
			this.searchCtls.setReturningAttributes(datosReturn);

			NamingEnumeration answer = this.ctx.search(this.searchBase,
					this.searchFilter, this.searchCtls);
			NamingEnumeration ae;
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					for (ae = attrs.getAll(); ae.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						String llave = attr.getID();
						mapa.put(llave, attr.get().toString());
						System.out.println("Llave: " + llave + " Valor: "
								+ attr.get().toString());
					}

				}

			}

			return mapa;
		} catch (NamingException ne) {
			throw ne;
		} catch (Exception e) {
			throw e;
		} finally {
			if (this.ctx != null)
				this.ctx.close();
		}
		// return mapa;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, String> obtenerUsuarioTerminal(String usuarioE,
			String[] datosReturn) throws Exception {
		Map<String, String> mapa = new HashMap<String, String>();
		try {
			this.ctx = new InitialLdapContext(this.env, null);

			this.searchBase = "DC=tsbolivia,DC=com,DC=bo";

			this.searchCtls = new SearchControls();

			this.searchCtls.setSearchScope(2);

			this.searchFilter = ("(&(objectCategory=person)(objectClass=user)(displayname="+ usuarioE + "))");
								// "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + usuario + "))")	
			this.searchCtls.setReturningAttributes(datosReturn);

			NamingEnumeration answer = this.ctx.search(this.searchBase,
					this.searchFilter, this.searchCtls);
			NamingEnumeration ae;
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					for (ae = attrs.getAll(); ae.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						String llave = attr.getID();
						mapa.put(llave, attr.get().toString());
						System.out.println("Llave: " + llave + " Valor: "
								+ attr.get().toString());
					}

				}

			}

			return mapa;
		} catch (NamingException ne) {
			throw ne;
		} catch (Exception e) {
			throw e;
		} finally {
			if (this.ctx != null)
				this.ctx.close();
		}
		// return mapa;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, String> obtenerUsuarioTerminalEH(String usuarioE,
			String[] datosReturn) throws Exception {
		Map<String, String> mapa = new HashMap<String, String>();
		try {
			this.ctx = new InitialLdapContext(this.env, null);

			this.searchBase = "DC=tsbolivia,DC=com,DC=bo";

			this.searchCtls = new SearchControls();

			this.searchCtls.setSearchScope(2);

			this.searchFilter = ("(&(objectCategory=person)(objectClass=user)(initials="+ usuarioE + "))");
								// "(&(objectCategory=person)(objectClass=user)(sAMAccountName=" + usuario + "))")	
			this.searchCtls.setReturningAttributes(datosReturn);

			NamingEnumeration answer = this.ctx.search(this.searchBase,
					this.searchFilter, this.searchCtls);
			NamingEnumeration ae;
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					for (ae = attrs.getAll(); ae.hasMore();) {
						Attribute attr = (Attribute) ae.next();
						String llave = attr.getID();
						mapa.put(llave, attr.get().toString());
						System.out.println("Llave: " + llave + " Valor: "
								+ attr.get().toString());
					}

				}

			}

			return mapa;
		} catch (NamingException ne) {
			throw ne;
		} catch (Exception e) {
			throw e;
		} finally {
			if (this.ctx != null)
				this.ctx.close();
		}
		// return mapa;
	}
	
	public Result guardar(TUsuarioAtributo u) throws Exception {
		
		try {
			this.ctx = new InitialLdapContext(this.env, null);
			
//
//				StringTokenizer tokens = new StringTokenizer(u.getNombre(), ",");
//				int t=0;
//				while (tokens.hasMoreTokens()) {
//					if(t==0){
//						u.setApellidosTerminal(tokens.nextToken().trim());
//					}
//					if(t==1){
//						u.setNombreTerminal(tokens.nextToken().trim());
//					}
//					if(t>1){
//						tokens.nextToken();
//					}
//					t = t + 1;
//				}

			
			// Create a container set of attributes
	        Attributes container = new BasicAttributes();

	        // Create the objectclass to add
	        Attribute objClasses = new BasicAttribute("objectClass");
	        objClasses.add("top");
	        objClasses.add("person");
	        objClasses.add("organizationalPerson");
	        objClasses.add("user");
			
	     // Assign the username, first name, and last name
	        String cnValue = new StringBuffer(u.getNombreTerminal().toUpperCase()).append(" ").append(u.getApellidosTerminal().toUpperCase()).toString();
	        Attribute cn = new BasicAttribute("cn", cnValue);
	        Attribute sAMAccountName = new BasicAttribute("sAMAccountName", u.getUsuario());
	        Attribute principalName = new BasicAttribute("userPrincipalName", u.getUsuario()+ "@" + "tsbolivia");
	        Attribute givenName = new BasicAttribute("givenName", u.getNombreTerminal().toUpperCase());
	        Attribute sn = new BasicAttribute("sn", u.getApellidosTerminal().toUpperCase());
	        Attribute uid = new BasicAttribute("uid", u.getNombreTerminal().toUpperCase());
	        Attribute eh;
	        if(u.getEh()==null || u.getEh().isEmpty()){
	        	 eh= new BasicAttribute("initials", " ");
	        }else{
	        	 eh = new BasicAttribute("initials", u.getEh());
	        }
	        Attribute displayname = new BasicAttribute("displayname", u.getNombreTerminal().toUpperCase()+" "+u.getApellidosTerminal().toUpperCase());
	        Attribute description= new BasicAttribute("description", u.getCargoTerminal());
	        Attribute office= (new BasicAttribute("physicalDeliveryOfficeName", u.getAreaTerminal())); 
	        if(u.getTelf()!=null){
	        	Attribute mobile= new BasicAttribute("mobile", u.getTelf());
	        	container.put(mobile);
	        }
	        if(u.getCorreo()!=null){
	        	 Attribute mail= new BasicAttribute("mail", u.getCorreo());
	        	 container.put(mail);
	        }
	        if(u.getIddpto()!=null){
	        	Attribute street= (new BasicAttribute("streetAddress", u.getIddpto())); 
	        	Attribute city= (new BasicAttribute("l", u.getDpto()));
	        	container.put(city);
	        	container.put(street);
	        }
	        if(u.getDpto()!=null){
	        	
	        	Attribute city= (new BasicAttribute("l", u.getDpto()));
	        	container.put(city);
	        	
	        }
	       if(u.getCi()!=null){
	    	   Attribute telephonenumber= (new BasicAttribute("telephonenumber", u.getCi()));
	    	   container.put(telephonenumber);
	       }
	              
	       

	        Attribute pwdlast= new BasicAttribute("pwdlastset", "0");
	       
	        
//	        int UF_ACCOUNTDISABLE = 0x0002;
	        int UF_PASSWD_NOTREQD = 0x0020;
//	        int UF_PASSWD_CANT_CHANGE = 0x0040;
	        int UF_NORMAL_ACCOUNT = 0x0200;
//	        int UF_DONT_EXPIRE_PASSWD = 0x10000;
	        int UF_PASSWORD_EXPIRED = 0x800000;
	        Attribute accountControl=(new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT 
	        			+ UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED )));
	        
	        if(u.getFechaExpiracion()!=null){   
	        	
	        	 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	        	 //Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC-04:00"));
	        	    c.clear();
	        	    //c.set(2016, 3, 4);
	        	    c.setTime(u.getFechaExpiracion());
	        	    long t1 = c.getTimeInMillis();
	        	    c.set(1601, 0, 1);
	        	    long t2 = c.getTimeInMillis();
	        	    long ldap = (t1 - t2) * 10000;

	        	
	        	 Attribute accountExp=(new BasicAttribute("accountExpires",String.valueOf(ldap)));
	        	 container.put(accountExp);
	        }
	       
	        
	        
	      //  Attribute member= (new BasicAttribute("memberof", "CN=UsuariosTSBolivia,OU=Usuarios Terminal - Bolivia,DC=tsbolivia,DC=com,DC=bo"));
	     // Add password
	        String newQuotedPassword = "\"" + "telecel1" + "\"";
	        byte[] newUnicodePassword = newQuotedPassword.getBytes();
	    	newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
	        
	        Attribute userPassword = new BasicAttribute("unicodePwd", newUnicodePassword);

	        // Add these to the container
	        container.put(accountControl);
	        container.put(eh);
	        container.put(displayname);
	        container.put(description);
	        container.put(office); 
	        container.put(pwdlast);   
	        container.put(objClasses);
	        container.put(sAMAccountName);
	        container.put(principalName);
	        container.put(cn);
	        container.put(sn);
	        container.put(givenName);
	        container.put(uid);
	        container.put(userPassword);
	        
	        // Create the entry
	        this.ctx.createSubcontext("cn="+cnValue+",ou=Usuarios Terminal - Bolivia,DC=tsbolivia,DC=com,DC=bo", container);
	        
	        StringTokenizer tokens = new StringTokenizer(u.getMemberOf(), ",");
	        BasicAttribute member = new BasicAttribute("member","cn="+cnValue+",ou=Usuarios Terminal - Bolivia,DC=tsbolivia,DC=com,DC=bo");
	        Attributes atts = new BasicAttributes();
	        atts.put(member);
	        ctx.modifyAttributes("cn=UsuariosTSBolivia,OU=Usuarios Terminal - Bolivia,DC=tsbolivia,DC=com,DC=bo", LdapContext.ADD_ATTRIBUTE, atts);
	        List<String> listaOu=new ArrayList<String>();
	        List<String> listaCi=new ArrayList<String>();
	        while (tokens.hasMoreTokens()) {
	        	listaOu.add(tokens.nextToken().trim().substring(4, 7));
	        	
	        }
	        tokens = new StringTokenizer(u.getMemberOf(), ",");
	        while (tokens.hasMoreTokens()) {
	        	listaCi.add(tokens.nextToken().trim());
	        	
	        }
	        for (int i = 0; i < listaCi.size(); i++) {
	        	 ctx.modifyAttributes("cn="+listaCi.get(i)+",OU="+obtciudad(listaOu.get(i))+",OU=Usuarios Grupos,DC=tsbolivia,DC=com,DC=bo", LdapContext.ADD_ATTRIBUTE, atts);
			}

 
//	        
	      //  ctx.modifyAttributes("cn="+u.getMemberOf()+",OU=SANTA CRUZ,OU=BENI,OU=PANDO,OU=LA PAZ,OU=ORURO,OU=POTOSI"
//	        		+ ",OU=COCHABAMBA,OU=CHUQUISACA,OU=TARIJA,OU=Usuario Grupos,DC=tsbolivia,DC=com,DC=bo", member);
	        
			Result res= new Result();
			res.ok("inserto");
			return res;
			
		} catch (NamingException ne) {
			ne.printStackTrace();
			Result res= new Result();
			res.error("error: "+ne.getMessage());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			Result res= new Result();
			res.error("error: "+e.getMessage());
			return res;
		} finally {
			if (this.ctx != null)
				this.ctx.close();
		}
		// return mapa;
	}
	
public Result baja(String u) throws Exception {
		
		try {
			this.ctx = new InitialLdapContext(this.env, null);
			
	        // Create the entry
	        this.ctx.unbind("cn="+u+",ou=Usuarios Terminal - Bolivia,DC=tsbolivia,DC=com,DC=bo");
	        //this.ctx.unbind("cn="+u+",ou=*,DC=tsbolivia,DC=com,DC=bo");
			
	        // this.ctx.unbind("cn="+u+",DC=tsbolivia,DC=com,DC=bo");
			//System.out.println(u);
			//this.ctx.removeFromEnvironment("uid=sishelea,cn="+u+",DC=tsbolivia,DC=com,DC=bo");
			
			
			
	 
			Result res= new Result();
			res.ok("elimino");
			return res;
			
		} catch (NamingException ne) {
			ne.printStackTrace();
			Result res= new Result();
			res.error("error: "+ne.getMessage());
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			Result res= new Result();
			res.error("error: "+e.getMessage());
			return res;
		} finally {
			if (this.ctx != null)
				this.ctx.close();
		}
		// return mapa;
	}
	
	public String obtciudad(String ou){
		if(ou.equalsIgnoreCase("lpz")){
    		return "LA PAZ";
    	}
		if(ou.equalsIgnoreCase("oru")){
			return "ORURO";     		
		}
		if(ou.equalsIgnoreCase("pts")){
			return "POTOSI";
		}
		if(ou.equalsIgnoreCase("cba")){
			return "COCHABAMBA";
		}
		if(ou.equalsIgnoreCase("scr")){
			return "SUCRE";
		}
		if(ou.equalsIgnoreCase("trj")){
			return "TARIJA";
		}
		if(ou.equalsIgnoreCase("pnd")){
			return "PANDO";
		}
		if(ou.equalsIgnoreCase("bni")){
			return "BENI";
		}
		if(ou.equalsIgnoreCase("scz")){
			return "SANTA CRUZ";
		}
		return "";
	}
	
	public Result ObtenerGrupos() throws NamingException, Exception {
        ArrayList l = new ArrayList();
        Result res= new Result();
        try {
            //Create the initial directory context
        	this.ctx = new InitialLdapContext(this.env, null);
            
            this.searchCtls=new SearchControls();
            this.searchBase = "ou=Usuarios Grupos,DC=tsbolivia,DC=com,DC=bo";

            //Especificar el alcance de búsqueda
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String returnAtts[] = {"cn"};

            //Especificado el filtro de busqueda LDAP
            this.searchFilter="(&(objectCategory=group))";
            this.searchCtls.setReturningAttributes(returnAtts);
            //(&(cn=%g)(objectclass=group))
            //Buscar un Objeto usando el Filtro
            NamingEnumeration answer = this.ctx.search(this.searchBase,this.searchFilter, this.searchCtls);
            int totalResults = 0;
            //Bucle a través de los resultados de la búsqueda
            String grupo = "";
            while (answer.hasMoreElements()) {
//            	 SearchResult rslt = (SearchResult) answer.next();
//                 Attributes attrs = rslt.getAttributes();
//                 l.add(attrs.get("cn"));
                SearchResult sr = (SearchResult) answer.next();
                
                Attributes attrs = sr.getAttributes();
                if (attrs != null) {

                    try {
                        for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
                            Attribute attr = (Attribute) ae.next();

                            for (NamingEnumeration e = attr.getAll(); e.hasMore(); totalResults++) {
                                //System.out.println(" " +  totalResults + ". " +  e.next());                                  
                                grupo = e.next().toString().trim();
                                //grupo = grupo.substring(3, grupo.indexOf(",")).trim();
                                l.add(grupo);
                                // System.out.println(grupo);
                            }
                        }
                    } catch (NamingException e) {
                        System.err.println("Problem listing membership: " + e);
                    }

                }
            }
            res.ok("Se obtuvo los grupos Correctamente");
            res.setData(l);
            return res;
            

        } catch (NamingException ne) {
        	ne.printStackTrace();
        	res=new Result();
        	res.error("Error al obtener los grupos: "+ne.getMessage());
        	return res;
                    
        } catch (Exception e) {
        	e.printStackTrace();
        	res=new Result();
        	res.error("Error al obtener los grupos: "+e.getMessage());
        	return res;
        } finally {
        	this.ctx.close();
        }

    }
	
	

}