package io.recheck.uoi;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.recheck.uoi.entity.UOINode;

@Repository
public interface UOIRepository extends Neo4jRepository<UOINode, Long> {

    void parent(UOINode parent);

//    void historyOf(UOINode uoiNode);

    void children(UOINode uoiNode);

    UOINode findByUoi(@Param("uoi") String uoi);

    Iterable<UOINode> findAll();

}