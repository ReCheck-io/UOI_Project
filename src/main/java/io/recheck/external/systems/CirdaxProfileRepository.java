package io.recheck.external.systems;

import io.recheck.external.systems.entity.CirdaxProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirdaxProfileRepository extends JpaRepository<CirdaxProfile, Long> {

    CirdaxProfile findBySystemName(String systemName);

}
