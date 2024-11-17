package searchengine.Parsing;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Mapping2 extends RecursiveAction {
    String url;
    static ConcurrentSkipListSet<String> urlStorage = new ConcurrentSkipListSet<>();
    static ConcurrentSkipListSet<Mapping2> taskList = new ConcurrentSkipListSet<>();
    public Mapping2(String url) {
        this.url = url.endsWith("/") ? url : new StringBuilder(url).append("/").toString();
        System.out.println("storage  " + urlStorage.size());
    }

    @Override
    protected void compute() {
        Set<String> listTransfer = null;
        LinksCollect linksCollect = new LinksCollect();
        try {
            listTransfer = linksCollect.urlGetting(url, urlStorage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (listTransfer.isEmpty()) {
            return;
        }
        for (String urlTemp : listTransfer) {
            System.out.println(urlTemp);
            Mapping2 task = new Mapping2(urlTemp);
            task.fork();
            taskList.add(task);
            task.compute();
        }
        for (Mapping2 task : taskList) {
            task.join();//дожидаемся выполнения задачи и получаем результат (кода в объекте)
        }
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String url = "https://www.svetlovka.ru/";
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        Mapping2 mapping2 = new Mapping2(url);
        mapping2.compute();


        System.out.println("время парсинга - "
                + (System.currentTimeMillis() - start) / 1000 + " секунд");
    }

}
/*
 * https://rw6ase.narod.ru/index1/konstr/rk_ak_ustr/_rk_ak_ustr.html
 * */