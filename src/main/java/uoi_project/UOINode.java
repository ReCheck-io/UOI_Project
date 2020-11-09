package uoi_project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.sql.Timestamp;
import java.util.*;

// API search, create, validate

@NodeEntity
public class UOINode {

    @Id
    @GeneratedValue
    private Long id;
    private String uuid;
    private double length;
    private double height;
    private double width;
    private List materials;
    private List physicalID;
    private String timestamp;
    private String owner;
    private String tenant;
    private String level;

    // unique building ID https://github.com/pnnl/buildingid
    private String ubid;

    //needed for beacons/chip
    private double longitude;
    private double latitude;
    private List resources;

    public UOINode() {

    }

    public UOINode(UOINode parent, String timestamp) {
        //parent
        this.parent = new UOINode(parent);
        this.uuid = "NL " + UUID.randomUUID();
        this.timestamp = timestamp;
    }

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "PART_OF", direction = Relationship.OUTGOING)
    private UOINode parent;

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "CONSISTS_OF", direction = Relationship.OUTGOING)
    private List<UOINode> children = new ArrayList<>();

    @JsonIgnoreProperties("UOINode")
    @Relationship(type = "HISTORY_OF", direction = Relationship.OUTGOING)
    private UOINode historyUOI;

    public void historyOf(UOINode presentUOI) {
        presentUOI.setHistoryUOI(this);
    }

    public UOINode(UOINode parent) {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void partOf(UOINode uoiNode) {
        parent = new UOINode(uoiNode);
    }

    public void consistsOf(UOINode childUOI) {
        if (this.getChildren() == null) {
            this.setChildren(children);
        }

        this.getChildren().add(childUOI);
    }


    public UOINode(String level) {
        this.uuid = "NL " + UUID.randomUUID();
        this.level = level;
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return this.uuid + "UOI in the work";
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List getResources() {
        return resources;
    }

    public void setResources(List resources) {
        this.resources = resources;
    }

    public String getUuid() {
        return uuid;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUbid() {
        return ubid;
    }

    public void setUbid(String ubid) {
        this.ubid = ubid;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public List getMaterials() {
        return materials;
    }

    public void setMaterials(List materials) {
        this.materials = materials;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public List getPhysicalID() {
        return physicalID;
    }

    public void setPhysicalID(List physicalID) {
        this.physicalID = physicalID;
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

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public UOINode getParent() {
        return parent;
    }

    public void setParent(UOINode parent) {
        this.parent = parent;
    }

    public UOINode getHistoryUOI() {
        return historyUOI;
    }

    public void setHistoryUOI(UOINode historyUOI) {
        this.historyUOI = historyUOI;
    }
}
