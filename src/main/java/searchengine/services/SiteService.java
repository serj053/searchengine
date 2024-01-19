package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.model.Site;
import searchengine.repositories.SiteRepositories;

import java.util.Date;
import java.util.Optional;

import static searchengine.model.Status.INDEXING;

@Service
public class SiteService {
    private SiteRepositories siteRepositories;

    public SiteService(SiteRepositories siteRepositories) {
        this.siteRepositories = siteRepositories;
    }

    public Site getSite(Integer id) {
        if (siteRepositories.existsById(id)) {
            Optional<Site> site = siteRepositories.findById(id);
            if (site.isPresent())
                return site.get();
        }
        return new Site(INDEXING, new Date(), "error", "url", "name");
    }

    public void addSite() {
        Site site = new Site(INDEXING, new Date(), "ControlErrorr", "control@test.ru", "name");
        siteRepositories.save(site);
    }

    public void deleteSiteById(Integer siteId) {
        if (siteRepositories.existsById(siteId)) {
            Site site = siteRepositories.getReferenceById(siteId);
            siteRepositories.delete(site);
        }
    }
}
