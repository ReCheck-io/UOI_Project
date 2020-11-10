package uoi_project;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOIRepository extends Neo4jRepository<UOINode, Long> {

    void parent(UOINode parent);

    void historyOf(UOINode uoiNode);

    void children(UOINode uoiNode);
}