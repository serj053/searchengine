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
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

import static searchengine.model.Status.INDEXING;

public class Mapping extends RecursiveAction {
    public static SiteRepositories siteRepositories;
    public static PageRepositories pageRepositories;
    static int currentCounter;
    SiteDB sdb;
    boolean flag;
    private int id;
    private final String url;
    private final int counter;
    public static String constantPart;

    public Mapping(String url, int counter, SiteDB sdb, boolean flag) {
        Logger.getLogger(Mapping.class.getName()).info("url: "+url + "   sdb: " + sdb + "   flag: " + flag);
        this.flag = flag;
        this.sdb = sdb;
        if (flag) {
            this.url = getDataFromSite(url);
        } else {
            this.url = url;
        }
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
                // Logger.getLogger(Mapping.class.getName()).info("***  "+url +" body().text() is null");
                throw new RuntimeException("***  ошибочный url " + urlChildren + "  " + e);
            }
            String url = urlChildren
                    .replace("'", "\"")
                    .replace("\\", "");
            String text;
            Optional<String> textOpt = Optional.ofNullable(document.body().text());
            if (textOpt.isPresent()) {
                text = document.body().text()
                        .replace("'", "\"")
                        .replace("\\", "");
            } else {
                continue;
            }
            Page page = new Page(sdb, url, 3, text);

            List<Page> page1 = pageRepositories.findByPath(url);
            if (!page1.isEmpty()) {
                continue;
            }
            pageRepositories.save(page);
//            try {
//                DbWork2 nativeDB = new DbWork2();
//                nativeDB.getCurrentTime();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
            Mapping task = new Mapping(urlChildren, counter, sdb, flag);
            task.fork();
            taskList.add(task);
        }
        for (Mapping task : taskList) {
            task.join();//дожидаемся выполнения задачи и получаем результат (кода в объекте)
        }
        //     Logger.getLogger(Mapping.class.getName()).info("task size - "+taskList.size());
    }

    public String getDataFromSite(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    //              .timeout(100000)
                    .followRedirects(false)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String urlForDB = url
                .replace("'", "\"")
                .replace("\\", "");
        String nameForDB = document.title()
                .replace("'", "\"")
                .replace("\\", "");
        /************************link to database ************************************/
        String status = "";
        String statusTime = "";
        String lastError = "";
        //удаляем все записи из таблицы sitedb и page
        siteRepositories.deleteAll();
        pageRepositories.deleteAll();
        //на основе данных парсинга заполняем сущность SiteDb()
        SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", urlForDB, nameForDB);
        //передаем сущность в репозиторий
        sdb = siteRepositories.save(siteDB);
        flag = false;
        return url;
    }
}