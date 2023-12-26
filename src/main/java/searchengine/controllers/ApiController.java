package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.Site;
import searchengine.services.SiteService;
import searchengine.services.StartIndexing;
import searchengine.services.StatisticsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StatisticsService statisticsService;
    private final StartIndexing startIndexing;
    private final SiteService siteService;

    public ApiController(StatisticsService statisticsService, StartIndexing startIndexing, SiteService siteService) {
        this.statisticsService = statisticsService;
        this.startIndexing = startIndexing;
        this.siteService = siteService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public List startIndexing() {
        return startIndexing.indexing();
    }

    @PostMapping("/addSite")
    public void add(@RequestBody Site site) {
        siteService.addSite(site);
    }
    @DeleteMapping("/del/{siteId}")
    public void deleteById(@PathVariable Integer siteId){
        siteService.deleteSiteById(siteId);
    }
}
