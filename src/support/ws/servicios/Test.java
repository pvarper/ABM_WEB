package support.ws.servicios;

import servicios.Result;

public class Test {

	public static void main(String[] args) {
		// try {
		// support.util.Result r=ActiveDirectory.obtenerGrupos();
		// if(!r.getCode().equalsIgnoreCase(Code.OK)){
		// System.out.println("error: "+r.getDescription());
		// return;
		// }
		// List<String> selectMembers=(List<String>) r.getData();
		// for (String string : selectMembers) {
		// System.out.println("grupo: "+string);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//

		try {
			ServiciosI ser = new ServiciosI();
			Result res = new Result();
			res = ser.obtenerLocalidadesBCCS();
			System.out.println(res.getData().toString());

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}

	}

}
