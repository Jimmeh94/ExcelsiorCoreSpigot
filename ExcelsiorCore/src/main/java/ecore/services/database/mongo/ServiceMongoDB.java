package ecore.services.database.mongo;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
                    String[] temp = document.getString("center").split(",");
                    Vector3d v = new Vector3d(Double.valueOf(temp[0]), Double.valueOf(temp[1]), Double.valueOf(temp[2]));
                    shape = new ShapeCircle(v, document.getDouble("radius"), document.getBoolean("ignoreY"));
                } else {
                    String[] left = document.getString("leftCorner").split(",");
                    Vector3d l = new Vector3d(Double.valueOf(left[0]), Double.valueOf(left[1]), Double.valueOf(left[2]));
                    String[] right = document.getString("rightCorner").split(",");
                    Vector3d r = new Vector3d(Double.valueOf(right[0]), Double.valueOf(right[1]), Double.valueOf(right[2]));
                    shape = new ShapeRectangle(l, r);
                }

                Node area = new Node(UUID.fromString(document.getString("uuid")),
                        Text.of(document.getString("name")), shape, document.getInteger("aiCap"));
                Managers.NODES.add(area);

                if(document.get("children") != null) {
                    for (Document child : (List<Document>) document.get("children")) {
                        Shape childShape;
                        if (child.getInteger("shape") == 0) {
                            String[] temp = child.getString("center").split(",");
                            Vector3d v = new Vector3d(Double.valueOf(temp[0]), Double.valueOf(temp[1]), Double.valueOf(temp[2]));
                            childShape = new ShapeCircle(v, child.getDouble("radius"), child.getBoolean("ignoreY"));
                        } else {
                            String[] left = child.getString("leftCorner").split(",");
                            Vector3d l = new Vector3d(Double.valueOf(left[0]), Double.valueOf(left[1]), Double.valueOf(left[2]));
                            String[] right = child.getString("rightCorner").split(",");
                            Vector3d r = new Vector3d(Double.valueOf(right[0]), Double.valueOf(right[1]), Double.valueOf(right[2]));
                            childShape = new ShapeRectangle(l, r);
                        }
                        area.addChild(new PointOfInterest(UUID.fromString(document.getString("uuid")), Text.of(child.getString("name")), childShape, area,
                                child.get("roles") == null ? null : child.getString("roles")));
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

        for(Node a: Managers.NODES.getObjects()){
            if(col.find(eq("name", a.getDisplayName().toPlain())).first() != null){
                //already exists
                continue;
            }

            List<Document> children = new ArrayList<>();
            if(a.getChildren().size() > 0){
                for(PointOfInterest p: a.getChildren()){
                    Document add = new Document("name", p.getDisplayName().toPlain()).append("uuid", p.getUuid().toString());

                    if(p.getShape() instanceof ShapeCircle){
                        add.append("shape", 0)
                                .append("center", ((ShapeCircle)p.getShape()).getCenterAsString())
                                .append("radius", ((ShapeCircle)p.getShape()).getRadius())
                                .append("ignoreY", ((ShapeCircle)p.getShape()).ignoreY());
                    } else {
                        add.append("shape", 1)
                                .append("leftCorner", ((ShapeRectangle)p.getShape()).getBottomLeftAsString())
                                .append("rightCorner", ((ShapeRectangle)p.getShape()).getTopRightAsString());

                    }
                    if(p.getAvailableRoles() != null) {
                        add.append("roles", p.getAvailableRolesAsString());
                    }
                    children.add(add);
                }
            }

            if(a.getShape() instanceof ShapeCircle){
                temp.add(new Document("name", a.getDisplayName().toPlain()).append("uuid", a.getUuid().toString())
                        .append("shape", 0)
                        .append("center", ((ShapeCircle)a.getShape()).getCenterAsString())
                        .append("radius", ((ShapeCircle)a.getShape()).getRadius())
                        .append("ignoreY", ((ShapeCircle)a.getShape()).ignoreY())
                        .append("aiCap", a.getAiCap())
                        .append("children", children));

            } else {
                temp.add(new Document("name", a.getDisplayName().toPlain())
                        .append("shape", 1)
                        .append("leftCorner", ((ShapeRectangle)a.getShape()).getBottomLeftAsString())
                        .append("rightCorner", ((ShapeRectangle)a.getShape()).getTopRightAsString())
                        .append("aiCap", a.getAiCap())
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
