package searchengine.services;

import lombok.Getter;
import lombok.Setter;
import org.jboss.logging.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import searchengine.config.Site;
import searchengine.repositories.PageRepositories;
import searchengine.repositories.SiteRepositories;
import searchengine.workingWithSite.LetsParsing;

import java.io.IOException;
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


    public List indexing() throws IOException {
        //  for(Site s: sites){
//        Logger.getLogger(StartIndexing.class.getName()).info("site is " + sites.get(0).toString());
//        LetsParsing.getData(sites.get(0).getUrl(), siteRepositories, pageRepositories);
        new Thread(() -> {
            LetsParsing.getData(sites.get(1).getUrl(), siteRepositories, pageRepositories);
        }).start();
//           }

        //удаляем все записи из таблицы sitedb и page
        //siteRepositories.deleteAll();
        //на основе данных парсинга заполняем сущность SiteDb()
        //   SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", "url", "name");
        //передаем сущность в репозиторий
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