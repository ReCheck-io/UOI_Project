package uoi_project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONObject;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// API search, create, validate

@NodeEntity
public class UOINode {

    @Id
    @GeneratedValue
    private Long id;
    private String uuid;
    private List properties;
    private String timestamp;
    private String owner;


    private String parentUOI = null;
    private String uoiClass;
    private String countryCode;
    private LEVEL level;

    public UOINode() {

    }

    public UOINode(String countryCode, LEVEL level, String uoiClass) {
        this.uuid = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
    }

    public UOINode(String countryCode, LEVEL level, String uoiClass, String parentUOI) {
        this.uuid = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
        this.parentUOI = parentUOI;
    }

    public UOINode(UOINode parent, String timestamp) {
        //parent
        this.parent = new UOINode(parent);
        this.uuid = "NL." + UUID.randomUUID();
        this.timestamp = timestamp;
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "PART_OF", direction = Relationship.OUTGOING)
    private UOINode parent = null;

    public UOINode(LEVEL level, double length, double height, double width, String owner, String tenant, String ubid, double longitude, double latitude) {
        this.uuid = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.owner = owner;
    }

    public void partOf(UOINode parent) {
        setParent(parent);
        setParentUOI(parent.getUuid());
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "CONSISTS_OF", direction = Relationship.OUTGOING)
    private List<ConsistsOf> children = new ArrayList<>();

    public void consistsOf(UOINode childUOI) {
        if (this.getChildren() == null) {
            this.setChildren(children);
        }
        this.getChildren().add(childUOI);
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "HISTORY_OF", direction = Relationship.OUTGOING)
    private UOINode historyOf = null;

    public void historyOf(UOINode presentUOI) {
        setHistoryOf(presentUOI);
    }

    public UOINode(UOINode parent) {
    }

    public LEVEL getLevel() {
        return level;
    }

    public void setLevel(LEVEL level) {
        this.level = level;
    }


    public UOINode(LEVEL level) {
        this.uuid = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
    }

    @Override
    public String toString() {
        JSONObject js = new JSONObject();
        js.put("uuid", this.uuid);
        js.put("timestamp", this.timestamp);
        js.put("level", this.level);
        if (this.parentUOI != null) {
            js.put("parentUOI", this.parentUOI);
        } else {
            js.put("parentUOI", "null");
        }
        js.put("parent", this.parent);
        if (this.uoiClass != null) {
            js.put("uoiClass", this.uoiClass);
        } else {
            js.put("uoiClass", "null");
        }
        js.put("properties", this.properties);
        if (this.children != null){
            js.put("children", this.children);
        }else {
            js.put("children", "null");
        }   if (this.historyOf != null){
            js.put("historyOf", this.historyOf);
        }else {
            js.put("historyOf", "null");
        }
        String res = js.toString().replaceAll("\\\\", "");
        return res;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List getProperties() {
        return properties;
    }

    public void setProperties(List properties) {
        this.properties = properties;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParentUOI() {
        return parentUOI;
    }

    public void setParentUOI(String parentUOI) {
        this.parentUOI = parentUOI;
    }

    public UOINode getParent() {
        return parent;
    }

    public void setParent(UOINode parent) {
        this.parent = parent;
    }

    public UOINode getHistoryOf() {
        return historyOf;
    }

    public void setHistoryOf(UOINode historyOf) {
        this.historyOf = historyOf;
    }


}
