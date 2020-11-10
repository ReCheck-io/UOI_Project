package uoi_project;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PART_OF")
public class PartOf {
    @Id
    @GeneratedValue
    private long id;

    public PartOf(UOINode uoiNode, UOINode parent, String timestamp) {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    String timestamp;

    public UOINode getParent() {
        return parent;
    }

    public void setParent(UOINode parent) {
        this.parent = parent;
    }

    private UOINode parent;

    public UOINode getUoiNodePartOf() {
        return uoiNodePartOf;
    }

    public void setUoiNodePartOf(UOINode uoiNodePartOf) {
        this.uoiNodePartOf = uoiNodePartOf;
    }

    public UOINode getUoiNode() {
        return uoiNode;
    }

    public void setUoiNode(UOINode uoiNode) {
        this.uoiNode = uoiNode;
    }

    @StartNode
    private UOINode uoiNodePartOf;

    @EndNode
    private UOINode uoiNode;

}