package uoi_project;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UOIRepository extends Neo4jRepository<UOINode, Long> {

    void parent(UOINode parent);

    void historyOf(UOINode uoiNode);

    void children(UOINode uoiNode);

    UOINode findByUuid(@Param("uuid") String uuid);

}