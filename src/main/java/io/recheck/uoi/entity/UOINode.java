package io.recheck.uoi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.json.JSONObject;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

// API search, create, validate

@NodeEntity
public class UOINode {

    @Id
    @GeneratedValue
    private Long id;
    private String uoi;
    @Properties
    private Map<String, String> properties = new HashMap<>();
    private String timestamp;
    private String owner;


    private String parentUOI = null;



    private String uoiClass;
    private String countryCode;
    private LEVEL level;

    public UOINode() {

    }

    public UOINode(String countryCode, LEVEL level, String uoiClass) {
        this.uoi = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
    }

    public UOINode(String countryCode, LEVEL level, String uoiClass, String parentUOI) {
        this.uoi = countryCode + "." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.uoiClass = uoiClass;
        this.parentUOI = parentUOI;
    }


    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "PART_OF", direction = Relationship.OUTGOING)
    private UOINode parent = null;

    public UOINode(LEVEL level, String owner) {
        this.uoi = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
        this.owner = owner;
    }

    public void partOf(UOINode parent) {
        setParent(parent);
        setParentUOI(parent.getUoi());
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "CONSISTS_OF", direction = Relationship.OUTGOING)
    //TODO: da gi napravq da sa array sys strings inache se chupi programata
    private List<ConsistsOf> children = new ArrayList<>();

    public void consistsOf(UOINode childUOI) {
        if (this.getChildren() == null) {
            this.setChildren(children);
        }
        this.getChildren().add(childUOI);
    }

//    @JsonIgnoreProperties("UOINode")
//    @Relationship(type = "HISTORY_OF", direction = Relationship.OUTGOING)
//    private UOINode historyOf = null;

//    public void historyOf(UOINode presentUOI) {
//        setHistoryOf(presentUOI);
//    }

    public UOINode(UOINode parent) {
    }

    public LEVEL getLevel() {
        return level;
    }

    public void setLevel(LEVEL level) {
        this.level = level;
    }


    public UOINode(LEVEL level) {
        this.uoi = "NL." + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Date().getTime());
    }

    @Override
    public String toString() {
        JSONObject js = new JSONObject();
        js.put("uoi", getUoi());
        js.put("timestamp", getTimestamp());
        js.put("level", getLevel());
        if (getParentUOI() != null) {
            js.put("parentUOI", getParentUOI());
        }
        if (getUoiClass() != null) {
            js.put("uoiClass", getUoiClass());
        }
        if(!this.properties.isEmpty()){
            js.put("properties", this.properties);
        }
//        if (this.children != null){
//            js.put("children", this.children);
//        }
//        if (this.historyOf != null){
//            js.put("historyOf", this.historyOf);
//        }
        String res = js.toString().replaceAll("\\\\", "");
        return res;
    }


    public String getUoi() {
        return uoi;
    }

    public void setUoi(String uoi) {
        this.uoi = uoi;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
    public void addMoreProperties(String key, String value){
        this.properties.put(key, value);
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
    public String getUoiClass() {
        return uoiClass;
    }

    public void setUoiClass(String uoiClass) {
        this.uoiClass = uoiClass;
    }
//
//    public UOINode getHistoryOf() {
//        return historyOf;
//    }
//
//    public void setHistoryOf(UOINode historyOf) {
//        this.historyOf = historyOf;
//    }


}
