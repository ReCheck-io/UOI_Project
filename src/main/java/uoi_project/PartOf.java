package uoi_project;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "PART_OF")
public class PartOf {
    @Id
    @GeneratedValue
    private long id;

    String timestamp;

    @StartNode
    private UOINode uoiNodePartOf;

    @EndNode
    private UOINode parent;

    public PartOf(UOINode uoiNodePartOf, UOINode parent, String timestamp) {
        this.uoiNodePartOf = uoiNodePartOf;
        this.parent = parent;
        this.timestamp = timestamp;
    }

    public PartOf() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UOINode getParent() {
        return parent;
    }

    public void setParent(UOINode parent) {
        this.parent = parent;
    }

    public UOINode getUoiNodePartOf() {
        return uoiNodePartOf;
    }

    public void setUoiNodePartOf(UOINode uoiNodePartOf) {
        this.uoiNodePartOf = uoiNodePartOf;
    }

}