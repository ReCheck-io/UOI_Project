package io.recheck.uoi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "HISTORY_OF")
public class HistoryOf {
    @Id
    @GeneratedValue
    private long id;

    private String timestamp;
    private boolean validated;
    private String validator;

    @StartNode
    private UOINode uoiNodePartOf;

    @EndNode
    private UOINode uoiNode;

}
