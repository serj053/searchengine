package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import searchengine.model.Page;

import java.util.List;

public interface PageRepositories extends JpaRepository<Page, Integer> {
    List<Page> findByPath(String path);
}
