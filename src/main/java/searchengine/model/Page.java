package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(indexes = @Index(columnList = "path"))
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // @Column(name="site_id", nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    //@JoinColumn(name= "id" )
    private SiteDB site;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private int code;
    //@Column(nullable = false, length=20000)
    @Column(columnDefinition = "TEXT",
            name = "page_content")
    private String content;

    public Page(SiteDB site, String path, int code, String content) {
        this.site = site;
        this.path = path;
        this.code = code;
        this.content = content;
    }


}
