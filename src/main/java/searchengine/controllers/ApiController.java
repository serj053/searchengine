package searchengine.controllers;

import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.SiteDB;
import searchengine.services.SiteService;
import searchengine.services.StartIndexing;
import searchengine.services.StatisticsService;

import java.io.IOException;
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
    public List startIndexing() throws IOException {
        return startIndexing.indexing();
    }

    @GetMapping("siteId/{id}")
    public SiteDB getSite(@PathVariable Integer id) {

        return siteService.getSite(id);
    }

    @PostMapping("/addSite")
    public void add(@RequestBody SiteDB site) {
        siteService.addSite(site);
        Logger.getLogger(ApiController.class.getName()).info("** site -" + site);
    }

    @DeleteMapping("/del/{siteId}")
    public void deleteById(@PathVariable Integer siteId) {
        siteService.deleteSiteById(siteId);
    }
}
