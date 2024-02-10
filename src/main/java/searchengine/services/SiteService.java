package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.model.SiteDB;
import searchengine.repositories.SiteRepositories;

import java.util.Date;
import java.util.Optional;

import static searchengine.model.Status.FAILED;
import static searchengine.model.Status.INDEXING;

@Service
public class SiteService {
    private final SiteRepositories siteRepositories;

    public SiteService(SiteRepositories siteRepositories) {
        this.siteRepositories = siteRepositories;
    }

    public SiteDB getSite(Integer id) {
        if (siteRepositories.existsById(id)) {
            Optional<SiteDB> site = siteRepositories.findById(id);
            if (site.isPresent())
                return site.get();
        }
        return new SiteDB(FAILED, new Date(), "error", "urlError", "nameError");
    }

    public void addSite(SiteDB site) {
        //SiteDB site = new SiteDB(INDEXING, new Date(), "ControlErrorr", "control@test.ru", "name");
        siteRepositories.save(site);
    }

    public void deleteSiteById(Integer siteId) {
        if (siteRepositories.existsById(siteId)) {
            SiteDB site = siteRepositories.getReferenceById(siteId);
            siteRepositories.delete(site);
        }
    }
}
