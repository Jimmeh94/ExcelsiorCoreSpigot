package ecore.services.database.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ecore.ECore;
import ecore.services.UUIDUtils;
import ecore.services.location.ServiceLocationUtils;
import ecore.services.nodes.Node;
import ecore.services.nodes.Shape;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ServiceMongoDB {

    public static final String COLLECTION_NODES = "nodes";

    private MongoClient client;
    private MongoDatabase database;
    private String username, password, ip, databaseName;

    public ServiceMongoDB(String username, String password, String ip, String databaseName) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.databaseName = databaseName;
    }

    public void openConnection(){
        try {
            MongoClientURI uri = new MongoClientURI("mongodb://" + username + ":" + password + ip);
            client = new MongoClient(uri);
            if (database == null) {
                database = client.getDatabase(databaseName);
            }
        } catch( Exception e){
            System.out.println(e.toString());
        }
    }

    public boolean isConnected(){
        return client != null;
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    public void close() {
        writeNodes();
        client.close();
        client = null;
        database = null;
    }

    /**
     * Use one of the public static final strings at the top of this class
     * @param collection
     */
    public void loadData(String collection){
        switch (collection){
            case COLLECTION_NODES: loadNodes();
                break;
        }
    }

    private void loadNodes() {
        /**
         * Format for storing/loading nodes:
         * - name
         * - shape (0 = circle, 1 = rectangle)
         * - center/bottom left corner
         * - top right corner
         */
        MongoCollection<Document> areas = database.getCollection(COLLECTION_NODES);

        areas.find().forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                Shape shape;
                if(document.getInteger("shape") == 0){
                    shape = new Shape.ShapeCircle(ServiceLocationUtils.locationFromString(document.getString("center")), document.getDouble("radius"),
                            document.getBoolean("ignoreY"));
                } else {
                    shape = new Shape.ShapeRectangle(ServiceLocationUtils.locationFromString(document.getString("leftCorner")),
                            ServiceLocationUtils.locationFromString(document.getString("rightCorner")));
                }

                Node area = new Node(UUID.fromString(document.getString("uuid")),
                        document.getString("name"), shape);
                ECore.INSTANCE.getNodes().add(area);

                if(document.get("children") != null) {
                    for (Document child : (List<Document>) document.get("children")) {
                        Shape childShape;
                        if (child.getInteger("shape") == 0) {
                            childShape = new Shape.ShapeCircle(ServiceLocationUtils.locationFromString(document.getString("center")), document.getDouble("radius"),
                                    document.getBoolean("ignoreY"));
                        } else {
                            childShape = new Shape.ShapeRectangle(ServiceLocationUtils.locationFromString(document.getString("leftCorner")),
                                    ServiceLocationUtils.locationFromString(document.getString("rightCorner")));
                        }
                        area.addChild(new Node(UUID.fromString(document.getString("uuid")), child.getString("name"), childShape));
                    }
                }
            }
        });
    }

    private void writeNodes(){
        /**
         * Format for storing/loading nodes:
         * - name
         * - shape (0 = circle, 1 = rectangle)
         * - center/left corner
         * - right corner
         * - children (embedded document with same format as node minus children option)
         */
        List<Document> temp = new ArrayList<>();
        MongoCollection<Document> col = database.getCollection(COLLECTION_NODES);

        for(Node a: ECore.INSTANCE.getNodes().getObjects()){
            if(col.find(eq("name", a.getName())).first() != null){
                //already exists
                continue;
            }

            List<Document> children = new ArrayList<>();
            if(a.getChildren().size() > 0){
                for(Node p: a.getChildren()){
                    Document add = new Document("name", p.getName()).append("uuid", p.getUuid().toString());

                    if(p.getShape() instanceof Shape.ShapeCircle){
                        add.append("shape", 0)
                                .append("center", ServiceLocationUtils.locationToString((p.getShape()).getCenter()))
                                .append("radius", ((Shape.ShapeCircle)p.getShape()).getRadius())
                                .append("ignoreY", ((Shape.ShapeCircle)p.getShape()).isIgnoreY());
                    } else {
                        add.append("shape", 1)
                                .append("leftCorner", ServiceLocationUtils.locationToString(((Shape.ShapeRectangle)p.getShape()).getFirstCorner()))
                                .append("rightCorner", ServiceLocationUtils.locationToString(((Shape.ShapeRectangle)p.getShape()).getSecondCorner()));

                    }

                    children.add(add);
                }
            }

            if(a.getShape() instanceof Shape.ShapeCircle){
                temp.add(new Document("name", a.getName()).append("uuid", a.getUuid().toString())
                        .append("shape", 0)
                        .append("center", ServiceLocationUtils.locationToString((a.getShape()).getCenter()))
                        .append("radius", ((Shape.ShapeCircle)a.getShape()).getRadius())
                        .append("ignoreY", ((Shape.ShapeCircle)a.getShape()).isIgnoreY())
                        .append("children", children));

            } else {
                temp.add(new Document("name", a.getName())
                        .append("shape", 1)
                        .append("leftCorner", ServiceLocationUtils.locationToString(((Shape.ShapeRectangle)a.getShape()).getFirstCorner()))
                        .append("rightCorner", ServiceLocationUtils.locationToString(((Shape.ShapeRectangle)a.getShape()).getSecondCorner()))
                        .append("children", children));
            }
        }

        if(temp.size() > 0) {
            col.insertMany(temp);
        }
    }

    public Document fetchPlayerData(UUID uuid){
        FindIterable<Document> iterable = null;
        if (!isConnected())
            openConnection();
        if (database != null) {
            iterable = database.getCollection("players").find(eq("uuid", UUIDUtils.getRawUUID(uuid)));
            Document document = iterable.first();
            return document;
        }
        return null;
    }

    public Document fetchEmbeddedDocument(Document document, String field) { //for reading a single, non-embedded integer
        return (Document)document.get(field);
    }

}
