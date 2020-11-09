package uoi_project;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UOIRepository extends Neo4jRepository<UOINode, Long> {
    void historyUOI(UOINode uoiNode);
    void children(UOINode uoiNode);
}