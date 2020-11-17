package uoi_project;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RelationshipEntity(type = "CONSISTS_OF")
public class ConsistsOf {
    @Id
    @GeneratedValue
    private long id;

    private List<UOINode> children = new ArrayList<>();

    String timestamp;

    @StartNode
    private UOINode uoiNodeChild;

    @EndNode
    private UOINode uoiNodeParent;

    public ConsistsOf() {

    }

    public List<UOINode> getChildren() {
        return children;
    }

    public void setChildren(List<UOINode> children) {
        this.children = children;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UOINode getUoiNodeChild() {
        return uoiNodeChild;
    }

    public void setUoiNodeChild(UOINode uoiNodeConsistedOf) {
        this.uoiNodeChild = uoiNodeConsistedOf;
    }

    public UOINode getUoiNodeParent() {
        return uoiNodeParent;
    }

    public void setUoiNodeParent(UOINode uoiNodeParent) {
        this.uoiNodeParent = uoiNodeParent;
    }
}