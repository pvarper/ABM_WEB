package support.ussd.model;

public class Ciudad{
	
	private int id;
	private String ciudad;
	private String cantidad;
	
	public Ciudad(int i,String c, String ca){
		this.id=i;
		this.ciudad=c;
		this.cantidad=ca;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
