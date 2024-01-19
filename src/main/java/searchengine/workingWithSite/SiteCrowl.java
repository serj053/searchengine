package searchengine.workingWithSite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteCrowl {
    public static String[] connect(String urlSite) throws IOException {
        String[] list = new String[2];
        Document doc = Jsoup.connect(urlSite).ignoreHttpErrors(true).get();
        Elements elements = doc.select("a");
        int n = 0;
        for (Element element : elements) {
            if (n == 2 && list[0] != null) {
                String url = element.attr("abs:href");
                Document doc2 = Jsoup.connect(url).ignoreHttpErrors(true).get();
                list[0] = url;
                list[1] = doc2.text();
                n++;
            }
        }
        return list;
    }
}
