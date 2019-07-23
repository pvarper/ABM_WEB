package support.user.ldap;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import org.apache.log4j.Logger;

import support.id.ParametroID;
import support.user.model.MuUsuario;
import support.user.sys.P;
import support.ussd.model.TUsuarioAtributo;
import support.util.Result;

public class ActiveDirectory {

	public static int EXIT_USER = 1;
	public static int NOT_EXIT_USER = 2;
	public static int ERROR = 3;

	private static Logger log = Logger.getLogger(ActiveDirectory.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int validarUsuario(String usuario, String password) throws LdapContextException{
		if (usuario == null || usuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
			return NOT_EXIT_USER;
		}
		Hashtable env = new Hashtable();
		try {
			env.put("java.naming.factory.initial", (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_initial_context_factory));
			env.put("java.naming.provider.url", (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
			env.put("java.naming.security.authentication", (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_authentication));
			env.put("java.naming.security.principal", (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_principal) + usuario);
			env.put("java.naming.security.credentials", password);

		} catch (Exception e) {
			log.error("[validarUsuario] usuario: " + usuario + ", Fallo al establecer conexion con LDAP", e);
			return ERROR;
		}
		try {
			InitialDirContext ctx = new InitialDirContext(env);
			ctx.close();
			return EXIT_USER;
		} catch (Exception e) {
			log.error("[validarUsuario] usuario: " + usuario + ", Fallo al establecer conexion con LDAP", e);
			throw (new LdapContextException(e.getMessage(),e)); 
			//return EXIT_USER;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean validarGrupo(String grupo) throws LdapContextException{
		InitialDirContext dirC = null;
		NamingEnumeration answer = null;
		NamingEnumeration ae = null;

		
		Hashtable<String, String> env = new Hashtable<String, String>();
		
		env.put("java.naming.factory.initial", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_initial_context_factory));
		env.put("java.naming.provider.url", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		env.put("java.naming.security.authentication", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_authentication));
		env.put("java.naming.security.principal", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_principal) + (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		env.put("java.naming.security.credentials", (String) P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
			
		try {
			dirC = new InitialDirContext(env);
		} catch (Exception e) {
			log.error("[validarGrupo] grupo: " + grupo + ", Fallo al validar el grupo", e);
			throw (new LdapContextException(e.getMessage(),e)); 
		}
		try {
			if (dirC != null) {
				String searchBase = "DC=tigo,DC=net,DC=bo";
				SearchControls searchCtls = new SearchControls();
				searchCtls.setSearchScope(2);
				String searchFilter = "(objectclass=group)";
				String[] returnAtts = { "cn" };
				searchCtls.setReturningAttributes(returnAtts);
				answer = dirC.search(searchBase, searchFilter, searchCtls);

				while (answer.hasMoreElements()) {
					SearchResult sr = (SearchResult) answer.next();
					Attributes attrs = sr.getAttributes();
					if (attrs != null) {
						for (ae = attrs.getAll(); ae.hasMore();) {
							Attribute attr = (Attribute) ae.next();
							if (attr.get().toString().equals(grupo)) {
								return true;
							}
						}

					}

				}

			}

		} catch (Exception e) {
			log.error("[validarGrupo] grupo: " + grupo + ", Fallo al validar el grupo", e);
		} finally {
			try {
				if (dirC != null)
					dirC.close();
			} catch (Exception e) {
				log.warn("[validarGrupo] grupo: " + grupo + ", Fallo al cerrar el contexto LDAP", e);
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static List<String> getListaGrupos(String usuario) throws LdapContextException{
		List<String> lGrupos = new ArrayList<String>();

		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial", P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_initial_context_factory));
		env.put("java.naming.provider.url", P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		env.put("java.naming.security.authentication", P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_authentication));
		env.put("java.naming.security.principal", (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_principal) + (String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		env.put("java.naming.security.credentials", P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
		
		InitialLdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
			String searchBase = "DC=tigo,DC=net,DC=bo";
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String returnAtts[] = { "memberOf" };
			String searchFilter = "(&(objectCategory=person)(objectClass=user)(mailNickname=" + usuario + "))";

			searchCtls.setReturningAttributes(returnAtts);

			NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
			int totalResults = 0;

			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				if (attrs != null) {
					for (NamingEnumeration ne = attrs.getAll(); ne.hasMore();) {
						Attribute attr = (Attribute) ne.next();
						String grupo;
						for (NamingEnumeration e = attr.getAll(); e.hasMore(); totalResults++) {
							grupo = e.next().toString().trim();
							grupo = grupo.substring(3, grupo.indexOf(",")).trim();
							lGrupos.add(grupo);
						}
					}
				}
			}

		} catch (Exception e) {
			log.error("[getListaGrupos] usuario: " + usuario + ", Error al obtener el listado de grupos", e);
			throw (new LdapContextException(e.getMessage(),e)); 
		} finally {
			try {
				ctx.close();
			} catch (Exception e2) {
				log.warn("[getListaGrupos] usuario: " + usuario + ", Fallo al cerrar el InitialLdapContext", e2);
			}
		}
		return lGrupos;
	}
	@SuppressWarnings("rawtypes")
	public static TUsuarioAtributo obtenerNombreCompletoActiveDirectory(String usuario) {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoActiveDirectory]Iniciando recuperacion de Nombre para loginAD=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
		// System.out.println( "Configurando parametros de conexiï¿½n al LDAP..."
		// );
		Ldap ld = new Ldap(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","distinguishedName","mobile","streetAddress"
					,"telephonenumber","l",};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerDatos(usuario, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setNombreTerminal(mapa.get("givenName")+"");
			u.setNombreCompletoTerminal(u.getNombre());
			u.setApellidosTerminal( "" + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephonenumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			// e.printStackTrace();
			log.error("[obtenerNombreCompletoActiveDirectory]Al intentar recuperar nombres para loginAD" + usuario);
			// System.out.println( e.getMessage() );
		}
		return null;
	}
	@SuppressWarnings("rawtypes")
	public static MuUsuario getNombreCompleto(String usuario) throws LdapContextException{

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
		
		Ldap ld = new Ldap(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","distinguishedName","mobile"};
			Map mapa = ld.obtenerDatos(usuario, returnAtts);
			if (mapa.isEmpty()) {
				return null;
			}
			MuUsuario user= new MuUsuario();
			user.setLogin(String.valueOf((String) mapa.get("sAMAccountName")).trim().toLowerCase());
			user.setNombre(String.valueOf(mapa.get("givenName") + " " + (String) mapa.get("sn")));
			user.setCorreo(String.valueOf((String) mapa.get("mail")).trim().toLowerCase().trim());
			user.setEhumano(String.valueOf((String) mapa.get("initials")).trim().toLowerCase());
			user.setTelefono(String.valueOf((String) mapa.get("mobile")).trim().toLowerCase());
			return user;

		} catch (Exception e) {
			log.error("[getNombreCompleto] usuario: " + usuario + ", Fallo al obtener el nombre completo del usuario", e);
			throw (new LdapContextException(e.getMessage(),e)); 
		}
	}
	@SuppressWarnings("rawtypes")
	public  static TUsuarioAtributo obtenerNombreCompletoTerminal(String usuario) throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoTerminal]Iniciando recuperacion de Nombre para loginAD=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","mobile" ,"streetAddress"
					,"telephoneNumber","l"};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerDatosTerminal(usuario, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephoneNumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[obtenerNombreCompletoTerminal]Al intentar recuperar nombres para loginAD" + usuario);
			// System.out.println( e.getMessage() );
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public  static TUsuarioAtributo obtenerNombreCompletoTerminalEH(String usuario) throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoTerminal]Iniciando recuperacion de Nombre para loginAD=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","mobile" ,"streetAddress"
					,"telephoneNumber","l"};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerUsuarioTerminalEH(usuario, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephoneNumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[obtenerNombreCompletoTerminal]Al intentar recuperar nombres para loginAD" + usuario);
			// System.out.println( e.getMessage() );
		}
		return null;
	}
	
	
	public  static Result guardar(TUsuarioAtributo usuario) throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[guardar]Iniciando la alta de usuario en el terminal, user=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			
			Result res = ld.guardar(usuario);
		return res;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[guardar]Error al intentar guardar el usuario: " + usuario);
			// System.out.println( e.getMessage() );
			Result res= new Result();
			res.error("Error: "+e.getMessage());
			return res;
		}
	}
	public  static Result baja(String usuario) throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[baja]Iniciando baja de usuario en AD,user=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			
			Result res = ld.baja(usuario);
		return res;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[baja]Error Al intentar dar de baja el usuario en el terminal," + usuario);
			// System.out.println( e.getMessage() );
			Result res= new Result();
			res.error("Error: "+e.getMessage());
			return res;
		}
	}
	
	public static Result obtenerGrupos() throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerGruposTerminal]Iniciando recuperacion de Grupos");

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			
			Result res = ld.ObtenerGrupos();
			return res;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[obtenerGruposTerminal]Al intentar recuperar los grupos ",e);
			// System.out.println( e.getMessage() );
			Result res= new Result();
			res.error("Error: ");
			return res;
		}
	}
	
//	@SuppressWarnings("rawtypes")
//	public static String getNombreCompletoTerminal(String usuario) throws LdapContextException, UnsupportedEncodingException{
//
//		Conexion conexion = new Conexion();
//		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
//		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
//		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
//		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));
//		
//		LdapTerminal ld = new LdapTerminal(conexion);
//		try {
//			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","mobile"};
//			Map mapa = ld.obtenerDatosTerminal(usuario, returnAtts);
//			if (mapa.isEmpty()) {
//				return "";
//			}
//
//			return String.valueOf(mapa.get("givenName") + " " + (String) mapa.get("sn")).trim();
//
//		} catch (Exception e) {
//			log.error("[getNombreCompleto] usuario: " + usuario + ", Fallo al obtener el nombre completo del usuario", e);
//			throw (new LdapContextException(e.getMessage(),e)); 
//		}
//	}
	
	@SuppressWarnings("rawtypes")
	public static TUsuarioAtributo obtenerUsuarioActiveDirectoryEH(String nombre) {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoActiveDirectoryEH]Iniciando recuperacion de Nombre para loginAD=" + nombre);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
		// System.out.println( "Configurando parametros de conexiï¿½n al LDAP..."
		// );
		Ldap ld = new Ldap(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","distinguishedName","mobile","streetAddress"
					,"telephonenumber","l",};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerDatosPorEH(nombre, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setNombreTerminal(mapa.get("givenName")+"");
			u.setNombreCompletoTerminal(u.getNombre());
			u.setApellidosTerminal( "" + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephonenumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			// e.printStackTrace();
			log.error("[obtenerNombreCompletoActiveDirectory]Al intentar recuperar nombres para loginAD" + nombre);
			// System.out.println( e.getMessage() );
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static TUsuarioAtributo obtenerUsuarioActiveDirectory(String nombre) {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoActiveDirectory]Iniciando recuperacion de Nombre para loginAD=" + nombre);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_security_credentials));
		// System.out.println( "Configurando parametros de conexiï¿½n al LDAP..."
		// );
		Ldap ld = new Ldap(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","distinguishedName","mobile","streetAddress"
					,"telephonenumber","l",};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerDatosPorNombre(nombre, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setNombreTerminal(mapa.get("givenName")+"");
			u.setNombreCompletoTerminal(u.getNombre());
			u.setApellidosTerminal( "" + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephonenumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			// e.printStackTrace();
			log.error("[obtenerNombreCompletoActiveDirectory]Al intentar recuperar nombres para loginAD" + nombre);
			// System.out.println( e.getMessage() );
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public  static TUsuarioAtributo obtenerUsuarioTerminal(String usuario) throws UnsupportedEncodingException {
		Logger log = Logger.getLogger(ActiveDirectory.class);
		log.info("[obtenerNombreCompletoTerminal]Iniciando recuperacion de Nombre para loginAD=" + usuario);

		Conexion conexion = new Conexion();
		conexion.setDominio((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_dominio));
		conexion.setUrl((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_provider_url));
		conexion.setUsuario((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_user));
		conexion.setClave((String)P.getParamVal(ParametroID.DIRECTORIO_ACTIVO_TER_security_credentials));

		LdapTerminal ld = new LdapTerminal(conexion);
		try {
			String[] returnAtts = { "cn", "sAMAccountName", "mail", "sn", "givenName", "initials","mobile" ,"streetAddress"
					,"telephoneNumber","l",};
			// System.out.println( "Consultando los datos al LDAP..." );
			Map mapa = ld.obtenerUsuarioTerminal(usuario, returnAtts);
			if (mapa.isEmpty()) {

				return null;
			}

			TUsuarioAtributo u=new TUsuarioAtributo();
			u.setNombre(mapa.get("givenName") + " " + (String) mapa.get("sn"));
			u.setCorreo((String)mapa.get("mail"));
			u.setTelf((String)mapa.get("mobile"));
			u.setUsuario((String)mapa.get("sAMAccountName"));
			u.setEh((String)mapa.get("initials"));
			u.setIddpto((String)mapa.get("streetAddress"));
			u.setCi((String)mapa.get("telephoneNumber"));
			u.setDpto((String)mapa.get("l"));
			return u;

		} catch (Exception e) {
			 e.printStackTrace();
			log.error("[obtenerNombreCompletoTerminal]Al intentar recuperar nombres para loginAD" + usuario);
			// System.out.println( e.getMessage() );
		}
		return null;
	}
	

}