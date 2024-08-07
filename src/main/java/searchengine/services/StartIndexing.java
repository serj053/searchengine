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
import searchengine.workingWithSite.FromSite;
import searchengine.workingWithSite.SiteReading;

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
        FromSite.getData(sites.get(2).getUrl(), siteRepositories, pageRepositories);
        //������� ��� ������ �� ������� sitedb � page
        //siteRepositories.deleteAll();
        //�� ������ ������ �������� ��������� �������� SiteDb()
     //   SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", "url", "name");
        //�������� �������� � �����������
      //  siteRepositories.save(siteDB);
     //   Page page = new Page(siteDB, "url", 3, "text");
      //  pageRepositories.save(page);
     //   int id = siteDB.getId();
        return sites;
    }
}


//        List<searchengine.model.Site> listSite = siteRepositories.findAll();
//        for(searchengine.model.Site site: listSite){
//            System.out.println(site.getName());
//        }
//        return listSite;