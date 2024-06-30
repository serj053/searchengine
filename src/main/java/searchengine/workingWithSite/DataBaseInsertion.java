package searchengine.workingWithSite;

import java.io.IOException;

public class DataBaseInsertion {
    public static void main(String[] args) throws IOException {
        String[] res = SiteReading.connect("https://lenta.ru");
        for(String str: res){
            System.out.println(str);
        }
    }
}