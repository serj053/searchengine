package searchengine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import searchengine.model.SiteDB;

public interface SiteRepositories extends JpaRepository<SiteDB, Integer> {
}
