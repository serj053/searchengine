package searchengine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Site {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private Status status;
    private Date statusTime;
    private String lastError;
    private String url;
    private String name;

}
