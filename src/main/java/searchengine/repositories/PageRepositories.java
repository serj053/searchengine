package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.Page;

public interface PageRepositories extends JpaRepository<Page, Integer> {
}
