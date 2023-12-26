package searchengine.services;

import org.springframework.stereotype.Service;
import searchengine.repositories.PageRepositories;

@Service
public class AddPage {
    private final PageRepositories pageRepositories;

    public AddPage(PageRepositories pageRepositories) {
        this.pageRepositories = pageRepositories;
    }

    public void addPage(){


    }
}
