package web.repositories.version;

import web.entity.version.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VersionRepository extends JpaRepository<Version, Integer> {
    @Query(value="Select * From versions Order by id desc limit 1", nativeQuery = true)
    Version getVersion();
}
