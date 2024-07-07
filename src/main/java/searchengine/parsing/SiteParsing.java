package searchengine.parsing;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;

public class SiteParsing extends RecursiveAction {
    static ConcurrentSkipListSet<String> urlsPool;//коллекция подключений к базе данных
    String url;
    String reg;

    public SiteParsing(String url) {
        this.url = url;
        this.reg = "http[s]?://" + constantUrlPart(url) + "[^#,\\s]*";
    }


    @Override
    protected void compute() {


    }

    List<String> parsingPage(String url) throws IOException {
        Connection connection = Jsoup.connect(url)
                .ignoreHttpErrors(true)
                .ignoreContentType(true);
        Document doc = connection.get();
        Elements elements = doc.select("body")
                .select("a");

        return null;
    }

    String constantUrlPart(String url) {
        int start = url.indexOf("//");
        int end = url.indexOf(".ru");
        return url.substring(start + 2, end);
    }
}
