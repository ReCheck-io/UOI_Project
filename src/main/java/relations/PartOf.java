package relations;

import org.neo4j.ogm.annotation.*;
import uoi_nodes.UOINode;

@RelationshipEntity(type = "PART_OF")
public class PartOf {
    @Id
    @GeneratedValue
    private long id;

    String timestamp;

    @StartNode
    private UOINode uoiNodePartOf;

    @EndNode
    private UOINode uoiNode;

}
