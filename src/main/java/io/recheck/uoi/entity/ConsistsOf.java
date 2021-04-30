package io.recheck.uoi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "CONSISTS_OF")
public class ConsistsOf {

    private Long id;

    private List<String> children = new ArrayList<>();

    String timestamp;

    @StartNode
    private UOINode uoiNodeChild;

    @EndNode
    private UOINode uoiNodeParent;

    public ConsistsOf (UOINode uoiNodeChild, UOINode uoiNodeParent){
        this.uoiNodeChild = uoiNodeChild;
        this.uoiNodeParent = uoiNodeParent;
        addChildName(uoiNodeChild.getUoi());
        this.timestamp = String.valueOf(new Date().getTime());
    }

    public void addChildName(String childUOI){
        if(this.children == null){
            this.children = new ArrayList<>();
        }
        this.children.add(childUOI);
    }

}