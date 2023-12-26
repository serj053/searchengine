package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.model.Site;
import searchengine.model.Status;
import searchengine.repositories.SiteRepositories;

import java.util.Date;

import static searchengine.model.Status.INDEXING;

@Service
public class AddSite {
    private SiteRepositories siteRepositories;

    public AddSite(SiteRepositories siteRepositories) {
        this.siteRepositories = siteRepositories;
    }

    public void addSite(Site site){
        //Site site = new Site(INDEXING, new Date(), "lastError", "url", "name");
        siteRepositories.save(site);
    }
}
