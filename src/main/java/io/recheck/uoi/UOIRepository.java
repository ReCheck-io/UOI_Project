package io.recheck.uoi;

import io.recheck.uoi.entity.UOINode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UOIRepository extends Neo4jRepository<UOINode, Long> {

    void parent(UOINode parent);

//    void historyOf(UOINode uoiNode);

    void children(UOINode children);

    UOINode findByUoi(@Param("uoi") String uoi);

    Iterable<UOINode> findAll();

}