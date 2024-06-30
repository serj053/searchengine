package searchengine.parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadingSite {
    static String reg = "http[s]?://skillbox\\.ru[^#,\\s]*";


    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru";
        List<String> checkingList = new ArrayList<>();
        //checkingList.add(url);
        long start = System.currentTimeMillis();

        int n = 0;
        System.out.println(url);//выводим базовый url
        n = getNextPage(url, checkingList, n);
        checkingList.forEach(System.out::println);
        System.out.println("n " + n);
        System.out.println("time " + (System.currentTimeMillis() - start) / 1000 + " sec");

    }

    static int getNextPage(String url, List<String> checkingList, int n) throws IOException {
        Connection connection = Jsoup.connect(url).ignoreHttpErrors(true)
                .ignoreContentType(true);
        Document document = connection.get();
        Elements elements = document.select("body").select("a");
        if (elements.isEmpty())
            return n;
        for (Element element : elements) {
            String nextUrl = element.absUrl("href");
            if (nextUrl.matches(reg) && !checkingList.contains(nextUrl)) {
                n++;
                checkingList.add(nextUrl);
//                if (n == 4){
//                    return n;
//                    }
                getNextPage(nextUrl, checkingList, n);
            }
        }
        return n;
    }
}






