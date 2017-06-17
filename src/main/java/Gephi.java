/**
 * Created by ywein on 6/17/17.
 */

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;


import java.io.IOException;

public class Gephi {

    static String url = "http://localhost:8080/workspace1";

    Integer ECount = 0;
    Integer VCount = 0;

    public void updateGraph(String type, JSONObject node) {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put(type, node);

        //System.out.println(jsonObj.toString());
        try {
            HttpResponse<String> response = Unirest.post(url + "?operation=updateGraph")
                    .header("content-type", "application/json")
                    .header("cache-control", "no-cache")
                    .body(jsonObj.toString())
                    .asString();
            Integer i = 0;
            if (type.equals("ae")) {
                this.ECount++;
                i = this.ECount;
            }
            if (type.equals("an")) {
                this.VCount++;
                i = this.VCount;
            }
            System.out.println(i + " " + response.getStatus() + " " + response.getStatusText() + " " + response.getBody());
        } catch(UnirestException e) {
            e.printStackTrace();
        }

    }

    public static void updateGraphTest() {

        //System.out.println(jsonObj.toString());

        try {
            HttpResponse<JsonNode> response = Unirest.post(url + "?operation=updateGraph")
                    .header("content-type", "application/json")
                    .header("cache-control", "no-cache")
                    .body("{\"an\":{\"520248\":{\"id\":520248,\"label\":\"artist\"}}}")
                    .asJson();
            System.out.println(response.getBody());
        } catch(UnirestException e) {
            e.printStackTrace();
        }
    }
}
