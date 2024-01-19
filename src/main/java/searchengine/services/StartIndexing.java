package searchengine.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import searchengine.config.Site;
import searchengine.repositories.PageRepositories;
import searchengine.repositories.SiteRepositories;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "indexing-settings")
public class StartIndexing {
    SiteRepositories siteRepositories;
    PageRepositories pageRepositories;
    private List<Site> sites;

    public StartIndexing(SiteRepositories siteRepositories, PageRepositories pageRepositories) {
        this.siteRepositories = siteRepositories;
        this.pageRepositories = pageRepositories;
    }


    public List indexing() {
        for (Site site : sites) {
            /*обходим все страницы сайта и добавляем все адреса в базу Page*/
            System.out.println(site.getName());
        }
        return sites;
    }
}


//        List<searchengine.model.Site> listSite = siteRepositories.findAll();
//        for(searchengine.model.Site site: listSite){
//            System.out.println(site.getName());
//        }
//        return listSite;