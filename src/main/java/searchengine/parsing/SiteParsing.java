package searchengine.parsing;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;

public class SiteParsing extends RecursiveAction {
    ConcurrentSkipListSet<String> urlsPool;
    String url;

    @Override
    protected void compute() {

    }
}
