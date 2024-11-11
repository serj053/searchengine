package searchengine.Parsing;

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
import java.util.concurrent.ConcurrentSkipListSet;

public class CollectLinks {
    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru";
        CollectLinks links = new CollectLinks();
//        ConcurrentSkipListSet<String> list =links.get(url);
//        System.out.println(list.size());
        long start = System.currentTimeMillis();
        links.recursiveGet(url, 0);
        System.out.println((System.currentTimeMillis() - start) / 1000 + " секунд");
    }


    String template = "https://skillbox.ru[^#,\\s]*";
    ConcurrentSkipListSet<String> list = new ConcurrentSkipListSet<>();

    public void recursiveGet(String url, int indent) throws IOException {
        url = url.endsWith("/") ? url : new StringBuilder(url).append("/").toString();
        Set<String> secondList = get(url);
        int n = 0;
        if (!secondList.isEmpty()) {
            //           System.out.println(url);
            String size = String.valueOf(secondList.size());
            System.out.println(size.indent(indent) + " full count - " + list.size());

            indent++;
            for (String nextUrl : secondList) {
                n++;
                System.out.println("indent - " + indent + "  " + nextUrl);
                recursiveGet(nextUrl, indent);
            }
        } else {
            return;
        }
        System.out.println("n - " + n);
    }

    public Set<String> get(String url) throws IOException {
        Set<String> transferList = new HashSet<>();
        Connection connection = Jsoup.connect(url).ignoreHttpErrors(true)
                .timeout(1000 * 1000);//.followRedirects(true);
        Document document = connection.get();
        Elements elements = document.select("body").select("a");
        //      System.out.println("elements.size() - " + elements.size());
        int n = 0;
        list.add(url);
        for (Element element : elements) {
            String el = element.attr("href");
            el = el.endsWith("/") ? el : new StringBuilder(el).append("/").toString();
            if (el.matches(template) && !isFile(el) && !list.contains(el)) {
                transferList.add(el);
                //              n++;
                //         System.out.println(el);
                list.add(el);
            }
            //          System.out.println("до Set - " + n);
        }
        return transferList;
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
