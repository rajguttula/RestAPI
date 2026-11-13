package files;

import io.restassured.path.json.JsonPath;

public class ReusebleMathods {

	public static JsonPath rawToJon(String response) {
		JsonPath js=new JsonPath(response);
		return js;
	}
}
