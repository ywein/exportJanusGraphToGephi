import org.apache.tinkerpop.gremlin.driver.Result;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ywein on 6/17/17.
 */

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.connect();

        Gephi gephi = new Gephi();

        //Gephi.updateGraphTest();
        List<Result> AllV = graph.getAllV();
        System.out.println(AllV.size());
        for (Result res : AllV) {
            HashMap<String, String> V = (HashMap<String, String>) res.getObject();
            JSONObject jsonObj = new JSONObject(V);
            JSONObject VConverted = new JSONObject(jsonObj.toString());
            Iterator<?> keys = VConverted.keys();

            while( keys.hasNext() ) {
                String key = (String)keys.next();
                VConverted.put(key, VConverted.get(key).toString());
            }
            JSONObject finJsonObj = new JSONObject();
            Integer id = VConverted.getInt("id");
            finJsonObj.put(id.toString(), VConverted);
            //System.out.println(finJsonObj);
            gephi.updateGraph("an", finJsonObj);
        }
        List<Result> AllE = graph.getAllE();
        System.out.println(AllE.size());
        for (Result res : AllE) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("source", res.getEdge().outVertex().id().toString());
            jsonObj.put("target", res.getEdge().inVertex().id().toString());
            jsonObj.put("label", res.getEdge().label().toString());
            jsonObj.put("directed", false);
            JSONObject finJsonObj = new JSONObject();
            String id = res.getEdge().id().toString();
            finJsonObj.put(id, jsonObj);
            //System.out.println(jsonString);
            gephi.updateGraph("ae", finJsonObj);
        }
    }
}
