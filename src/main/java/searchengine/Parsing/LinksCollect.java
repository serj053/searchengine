package searchengine.Parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;


public class LinksCollect {
//https://www.youtube.com/
    String constantPart = "svetlovka";
    String regex = "http[s]?://\\w*." + constantPart + ".ru\\/[^#,\\s]*";

    public Set<String> urlGetting(String url, ConcurrentSkipListSet<String> urlStorage) throws IOException {
        Set<String> list = new HashSet<>();
     //   list.add(url);
        Connection connection = Jsoup.connect(url)
                .ignoreHttpErrors(true)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .referrer("http://www.google.com")
                .ignoreContentType(true)//игнорировать тип содержимого документа при анализе
                .timeout(1000*1000);
        Document document = connection.get();
        Elements elements = document.select("body").select("a");
        for(Element el: elements){
            String nextUrl = el.absUrl("href");
            nextUrl = nextUrl.endsWith("/")?nextUrl: new StringBuilder(nextUrl).append("/").toString();
            if(nextUrl.matches(regex) && !isFile(nextUrl) && !urlStorage.contains(nextUrl)){
                list.add(nextUrl);
                urlStorage.add(nextUrl);
            }
        }
        return list;
    }

    public boolean isFile(String link) {
        return link.contains("jpg") || link.contains(".jpeg") ||
                link.contains(".png") || link.contains(".gif") ||
                link.contains(".webp") || link.contains(".pdf") ||
                link.contains(".eps") || link.contains(".xlsx") ||
                link.contains(".doc") || link.contains(".pptx") ||
                link.contains(".docx") || link.contains("?_ga") ||
                link.contains(".php");
    }
}
