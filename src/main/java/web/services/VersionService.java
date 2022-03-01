package web.services;

import web.entity.version.Version;
import web.repositories.version.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    public String getVersion() {

        Version version = versionRepository.getVersion();
        if(version == null) {
            return "No current version";
        }
        return version.getVersion();
    }
}
