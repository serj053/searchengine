package searchengine.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('INDEXING, INDEXED, FAILED')", unique=true, nullable=false)
    private Status status;
    @Column(nullable = false)
    private Date statusTime;
    private String lastError;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    private String name;

}
