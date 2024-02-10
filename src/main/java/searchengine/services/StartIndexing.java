package searchengine.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import searchengine.config.Site;
import searchengine.model.Page;
import searchengine.model.SiteDB;
import searchengine.repositories.PageRepositories;
import searchengine.repositories.SiteRepositories;
import searchengine.workingWithSite.SiteCrawl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static searchengine.model.Status.INDEXING;

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


    public List indexing() throws IOException {
        String[] result = SiteCrawl.connect(sites.get(2).getUrl());
        SiteDB st = new SiteDB(INDEXING, new Date(), "noError", "Url", "Name");
        SiteDB sdb = siteRepositories.save(st);
        int id = sdb.getId();
        Page page =new Page(st,result[0], 3,result[1]);
        pageRepositories.save(page);
//        for (Site site : sites) {
//            /*обходим все страницы сайта и добавляем все адреса в базу Page*/
//            System.out.println(site.getName());
//        }
        return sites;
    }
}


//        List<searchengine.model.Site> listSite = siteRepositories.findAll();
//        for(searchengine.model.Site site: listSite){
//            System.out.println(site.getName());
//        }
//        return listSite;