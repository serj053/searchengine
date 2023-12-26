package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.model.Site;
import searchengine.repositories.SiteRepositories;

@Service
public class SiteService {
    private SiteRepositories siteRepositories;

    public SiteService(SiteRepositories siteRepositories) {
        this.siteRepositories = siteRepositories;
    }

    public void addSite(Site site) {
        //Site site = new Site(INDEXING, new Date(), "lastError", "url", "name");
        siteRepositories.save(site);
    }

    public void deleteSiteById(Integer siteId) {
        System.out.println("** siteId " + siteId);
        if (siteRepositories.existsById(siteId)) {
            System.out.println("** in if in method deleteSiteById()");
            Site site = siteRepositories.getReferenceById(siteId);
            siteRepositories.delete(site);
            System.out.println("*** after siteRepositories.delete()");
        }
    }
}
