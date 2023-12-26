package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.repositories.PageRepositories;

@Service
public class PageService {
    private final PageRepositories pageRepositories;

    public PageService(PageRepositories pageRepositories) {
        this.pageRepositories = pageRepositories;
    }

    public void addPage(){


    }
}
