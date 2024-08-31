package searchengine.workingWithSite;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import searchengine.model.SiteDB;
import searchengine.model.Page;
import searchengine.repositories.PageRepositories;
import searchengine.repositories.SiteRepositories;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

import static searchengine.model.Status.INDEXING;

public class Mapping extends RecursiveAction {
    private final SiteRepositories siteRepositories;
    private final PageRepositories pageRepositories;
    private int id;
    private final String url;
    public static String constantPart;
    static private boolean flag = true;
    private int counter;
    static int currentCounter;
    static SiteDB sdb;

    public Mapping(SiteRepositories siteRepositories, PageRepositories pageRepositories, String url, int counter) {
        this.siteRepositories = siteRepositories;
        this.pageRepositories = pageRepositories;
        this.url = url;
        this.counter = counter;
    }

    @Override
    protected void compute() {

        ConcurrentSkipListSet<String> tempList;//временный список для переноса ссылок
        CopyOnWriteArrayList<Mapping> taskList = new CopyOnWriteArrayList<>();
        ParseHtml2 ph = new ParseHtml2();
        tempList = ph.getLinks(url, constantPart);//получаем все ссылки со страницы
        for (String urlChildren : tempList) {
            if (currentCounter > counter) {
//                Logger.getLogger(Mapping.class.getName()).info("currentCounter - "
//                + currentCounter + ",   counter - " + counter);
                return;
            }
            currentCounter++;
            /*********************************************************/
            Document document = null;
            try {
                document = Jsoup.connect(urlChildren)
                        .timeout(100000)
                        .followRedirects(false)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String url = urlChildren
                    .replace("'", "\"")
                    .replace("\\", "");
            String name = document.title()
                    .replace("'", "\"")
                    .replace("\\", "");
            String text = document.body().text().substring(0,10)
                    .replace("'", "\"")
                    .replace("\\", "");
            /************************link to database ************************************/
            String status = "";
            String statusTime = "";
            String lastError = "";
            //SiteDB sdb = null;
            if (flag) {
                //удаляем все записи из таблицы sitedb и page
                siteRepositories.deleteAll();
                //на основе данных парсинга заполняем сущность SiteDb()
                SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", this.url, name);
                //передаем сущность в репозиторий
                sdb = siteRepositories.save(siteDB);
                //  Page page = new Page(siteDB, url, 3, text);
                //   pageRepositories.save(page);
                id = sdb.getId();
                flag = false;
            } else {
                //на основе данных парсинга заполняем сущность SiteDb()
                //SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", url, name);
                //передаем сущность в репозиторий
                Optional<SiteDB> opt = siteRepositories.findById(id);
                SiteDB sdb1 = null;
                if (opt.isPresent()) {
                    sdb1 = opt.get();
                }
                //   SiteDB sdb2 = new SiteDB(INDEXING, new Date(), "noError", url, name);
                Page page = new Page(sdb, url, 3, text);
                pageRepositories.save(page);
            }

            /*****************************************************************************/
            Mapping task = new Mapping(siteRepositories, pageRepositories, urlChildren, counter);
            task.fork();
            taskList.add(task);
        }
        for (Mapping task : taskList) {
            task.join();//дожидаемся выполнения задачи и получаем результат (кода в объекте)
        }
        //     Logger.getLogger(Mapping.class.getName()).info("task size - "+taskList.size());
    }
}


/*

soup.connect("https://www.facebook.com/")
.userAgent("Mozilla/5.0 (Windows; U; WindowsNT
5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
.referrer("http://www.google.com")

* */