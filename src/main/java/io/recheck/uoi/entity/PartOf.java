package io.recheck.uoi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
@Data
@NoArgsConstructor
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

}