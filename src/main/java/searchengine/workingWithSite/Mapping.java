package searchengine.workingWithSite;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.model.SiteDB;
import searchengine.repositories.SiteRepositories;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

import static searchengine.model.Status.INDEXING;

public class Mapping extends RecursiveAction {
    SiteRepositories repositories;
    String url;
    public static String constantPart;
    private int counter;
    static private int currentCounter;

    public Mapping(SiteRepositories repositories, String url, int counter) {
        this.repositories = repositories;
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
            if (true/*!urlPool.contains(urlChildren)*/) {
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

                //на основе данных парсинга заполняем сущность SiteDb()
                SiteDB siteDB = new SiteDB(INDEXING, new Date(), "noError", url, name);
                //передаем сущность в репозиторий
                repositories.save(siteDB);

                /*****************************************************************************/
                Mapping task = new Mapping(repositories, urlChildren, counter);
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
