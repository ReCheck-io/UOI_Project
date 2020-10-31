package relations;

import org.neo4j.ogm.annotation.*;
import uoi_nodes.UOINode;

@RelationshipEntity( type = "CONSISTS_OF")
public class ConsistsOf {
    @Id
    @GeneratedValue
    private long id;

    String timestamp;

    @StartNode
    private UOINode uoiNodeConsistedOf;

    @EndNode
    private UOINode uoiNode;

}
