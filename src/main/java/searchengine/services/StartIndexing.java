package searchengine.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import searchengine.config.Site;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "indexing-settings")
public class StartIndexing {
    private List<Site> sites;
    public List indexing(){
        for(Site site : sites){
            System.out.println(site.getName());
        }
        return sites;
    }
}
