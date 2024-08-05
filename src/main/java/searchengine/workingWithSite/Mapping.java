package searchengine.workingWithSite;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.model.SiteDB;
import searchengine.model.Page;
import searchengine.repositories.PageRepositories;
import searchengine.repositories.SiteRepositories;

import java.io.IOException;
import java.util.Date;
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
    static private int currentCounter;

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
                return;
            }
            currentCounter++;
            if (true) {
                /*********************************************************/
                Document document = null;
                try {
                    document = Jsoup.connect(urlChildren)
                            .timeout(100000)
                            .userAgent("Chrome/81.0.4044.138")
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
                String text = document.body().text()
                        .replace("'", "\"")
                        .replace("\\", "");
                /************************link to database ************************************/
                String status = "";
                String statusTime = "";
                String lastError = "";
                SiteDB sdb = null;
                if (flag) {
  //                  Logger.getLogger(Mapping.class.getName()).info("in SiteDB");
                    //на основе данных парсинга заполняем сущность SiteDb()
                    SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", url, name);
                    //передаем сущность в репозиторий
                    sdb = siteRepositories.save(siteDB);
                    id = sdb.getId();
                    flag = false;
                } else {
  //                  Logger.getLogger(Mapping.class.getName()).info("in Page");
                    //на основе данных парсинга заполняем сущность SiteDb()
                    SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", url, name);
                    //передаем сущность в репозиторий
                    Page page = new Page(sdb, "", 3, "TEXT");
                    pageRepositories.save(page);
                }

                /*****************************************************************************/
                Mapping task = new Mapping(siteRepositories, pageRepositories, urlChildren, counter);
                task.fork();
                taskList.add(task);
            }
        }
        for (Mapping task : taskList) {
            task.join();//дожидаемся выполнения задачи и получаем результат (кода в объекте)
        }
        //     Logger.getLogger(Mapping.class.getName()).info("task size - "+taskList.size());
    }
}
