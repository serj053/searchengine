package searchengine.parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ReadingSite {
    static String reg = "http[s]?://skillbox\\.ru[^#,\\s]*";

    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru";
        Connection connection = Jsoup.connect(url).ignoreHttpErrors(true)
                .ignoreContentType(true).followRedirects(true);
        Document document = connection.get();
        Elements elements = document.select("body").select("a");
        int n = 0;
        System.out.println(url);//выводим базовый url
        getNextPage(url);
        System.out.println("n " + n);
    }

    static void getNextPage(String url) throws IOException {
        Connection connection = Jsoup.connect(url).ignoreHttpErrors(true)
                .ignoreContentType(true);
        Document document = connection.get();
        Elements elements = document.select("body").select("a");
        if (elements.isEmpty())
            return;
        for (Element element : elements) {
            String nextUrl = element.absUrl("href");
            if (nextUrl.matches(reg))
                System.out.println(nextUrl);
            getNextPage(nextUrl);
        }
    }

}






