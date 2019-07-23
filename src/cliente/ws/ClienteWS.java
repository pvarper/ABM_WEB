package cliente.ws;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ClienteWS {
	
	public static Logger log = Logger.getLogger(ClienteWS.class);
	
	public Response consumir(String wsURL,String xmlInput){
		
		Response res= new Response();
		long startTime = System.currentTimeMillis();
		
	try {
		
		 		
		log.debug("[ClienteWS:consumir] se va consumir el ws: "+wsURL);
		
		//Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		//String wsURL = "http://mdwqprosb12.tigo.net.bo:80/osb/FindAnnexBasicInfoByPhoneNumber/FindAnnexBasicInfoByPhoneNumberWS?wsdl";
		URL url = new URL(wsURL.trim());
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(10000);
		HttpURLConnection httpConn = (HttpURLConnection)connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
									
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction ="";
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Length",
		String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		
		OutputStream out = httpConn.getOutputStream();
		//Write the content of the request to the outputstream of the HTTP Connection.
		
		out.write(b);
		out.close();
		
		//Ready with sending the request.
		
		//Read the response.
		InputStreamReader isr =
		new InputStreamReader(httpConn.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		
		//Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
		outputString = outputString + responseString;
		}
		outputString=outputString.replaceAll("><", ">\n<");
		//Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
//		Document document = parseXmlFile(outputString);
//		NodeList nodeLst = document.getElementsByTagName("billingCycle");
//		String weatherResult = nodeLst.item(0).getTextContent();
//		System.out.println("Weather: " + weatherResult);
		 
		//Write the SOAP message formatted to the console.
		//String formattedSOAPResponse = formatXML(outputString);
//		System.out.println(formattedSOAPResponse);
//		System.out.println(httpConn.getResponseCode());
//		//System.out.println(httpConn.);
//		System.out.println(elapsedTime);
		long elapsedTime = System.currentTimeMillis() - startTime;
		res.setCodeResponse(httpConn.getResponseCode());
		res.setResponse(outputString);
		res.setTimeResponse(elapsedTime);
		log.debug("[ClienteWS:consumir] se consumio satisfacoriamente el ws: "+wsURL);
		return res;
	} catch (MalformedURLException e) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		e.printStackTrace();
		res.setCodeResponse(500);
		res.setResponse(e.getMessage());
		res.setTimeResponse(elapsedTime);
		log.error("[ClienteWS:consumir] error al consumir el ws: "+wsURL,e);
		return res;
	
	}catch(IOException ex) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		ex.printStackTrace();
		res.setCodeResponse(500);
		res.setResponse(ex.getMessage());
		res.setTimeResponse(elapsedTime);
		log.error("[ClienteWS:consumir] error al consumir el ws: "+wsURL,ex);
		return res;
		
	}
	
    
	 
	
	}
	
	//format the XML in your String
	public String formatXML(String unformattedXml) {
		try {
			Document document = parseXmlFile(unformattedXml);
			OutputFormat format = new OutputFormat(document);
			format.setIndenting(true);
			format.setIndent(3);
			format.setOmitXMLDeclaration(true);
			Writer out = new StringWriter();
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	

	private Document parseXmlFile(String in) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
//		ClienteWS weatherWebserviceTester = new ClienteWS();
//		try {
//			weatherWebserviceTester.getWeather("77802564");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
