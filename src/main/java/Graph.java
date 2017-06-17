/**
 * Created by ywein on 6/17/17.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.tinkerpop.gremlin.driver.*;
import org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0;
import org.apache.tinkerpop.gremlin.structure.io.IoRegistry;
import org.apache.tinkerpop.gremlin.structure.io.gryo.GryoMapper;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import org.janusgraph.graphdb.tinkerpop.JanusGraphIoRegistry;

public class Graph {

    public Client client;

    public void connect() {

        try {
            GryoMapper mapper = GryoMapper.build().addRegistry(JanusGraphIoRegistry.getInstance()).create();
            MessageSerializer serializer = new GryoMessageSerializerV1d0(mapper);
            Cluster cluster = Cluster.build(new File("./src/main/conf/driver-settings.yaml")).serializer(serializer).create();
            Client client = cluster.connect().init();
            this.client = client;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Result>  getAllV() {
        Map params = new HashMap();
        List<Result> V = new ArrayList<Result>();
        try {
            V = this.client.submit("g.V().valueMap(true)", params).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return V;
    }

    public List<Result> getAllE() {
        Map params = new HashMap();
        List<Result> E = new ArrayList<Result>();
        try {
            E = this.client.submit("g.E()", params).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return E;
    }

    public List<Result> getAllEProps() {
        Map params = new HashMap();
        List<Result> E = new ArrayList<Result>();
        try {
            E = this.client.submit("g.E().valueMap(true)", params).all().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return E;
    }

}
