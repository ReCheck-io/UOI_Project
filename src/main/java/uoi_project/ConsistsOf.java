package uoi_project;

import org.neo4j.ogm.annotation.*;

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