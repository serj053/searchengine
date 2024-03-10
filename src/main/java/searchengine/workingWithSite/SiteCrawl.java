package searchengine.workingWithSite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SiteCrawl {
    public static String[] connect(String url) throws IOException {
        // http://tinkoff-bank-4882.mypublideal.com/online
        //
        //String url = "https://lenta.ru/";
        String[] site = new String[3];
        Document doc = Jsoup.connect(url).ignoreHttpErrors(true).get();
        Document doc2 = null;
        String url2 = null;
        Elements list = doc.select("a");
        int n = 0;
        for (Element element : list) {
            if (n == 9) {
                url2 = element.attr("abs:href");
                if (url2 == null || url2.isEmpty()) {
                    continue;
                }
//                System.out.println(el);
                doc2 = Jsoup.connect(url2).ignoreHttpErrors(true).get();
//                String line = doc2.text();
//                String[] arr = line.split(" ");
//                for (int i = 0; i < arr.length; i++) {
//                    {
//                        System.out.print(arr[i]+" ");
//                        if (i % 15 == 0) {
//                            System.out.println();
//                        }
//                    }
//                }
//                System.out.println();//.contains("<script>"));
//                FileOutputStream file = new FileOutputStream("data/text.html");
//                file.write(doc2.text().getBytes());
                //    System.out.println(doc2.html());//.text());
            }
            n++;
        }

        site[0] = url2;
        assert doc2 != null;
        site[1] = doc2.text();
        site[2] = doc.title();
        System.out.println(site[1].length());
        return site;
    }

    public static void main(String[] args) throws IOException {
        String[] arr = SiteCrawl.connect("https://lenta.ru/");
        System.out.println("0 - " + arr[0]);
        System.out.println();
        System.out.println("1 - " + arr[1]);
        System.out.println();
        System.out.println("2 - " + arr[2]);
    }
}
/*
* https://lenta.ru/
* https://www.playback.ru
* https://www.skillbox.ru
*
* */