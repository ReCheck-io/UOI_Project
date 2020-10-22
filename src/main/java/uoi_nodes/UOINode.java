package uoi_nodes;

import java.util.List;

public class UOINode {

    String uuid;
    double length;
    double height;
    double width;
    List materials;
    List child;
    String parent;
    String physicalID;
    String timestamp;
    String owner;
    String borrower;

    public UOINode(String uuid, String timestamp){
        this.uuid = uuid;
        this.timestamp = timestamp;
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

    public List getChild() {
        return child;
    }

    public void setChild(List child) {
        this.child = child;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPhysicalID() {
        return physicalID;
    }

    public void setPhysicalID(String physicalID) {
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

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }


}
