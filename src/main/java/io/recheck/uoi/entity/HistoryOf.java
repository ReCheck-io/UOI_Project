package io.recheck.uoi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.StartNode;

@Data
@NoArgsConstructor
public class HistoryOf {
    @Id
    @GeneratedValue
    private long id;

    String timestamp;

    @StartNode
    private UOINode uoiNodePartOf;

    @EndNode
    private UOINode uoiNode;

}
