package io.recheck.uoi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
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

}