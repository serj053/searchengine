package searchengine.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.model.Site;
import searchengine.services.AddSite;
import searchengine.services.StartIndexing;
import searchengine.services.StatisticsService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final StatisticsService statisticsService;
    private final StartIndexing startIndexing;
    private final AddSite addSite;

    public ApiController(StatisticsService statisticsService, StartIndexing startIndexing, AddSite addSite) {
        this.statisticsService = statisticsService;
        this.startIndexing = startIndexing;
        this.addSite = addSite;
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
        addSite.addSite(site);
    }
}
