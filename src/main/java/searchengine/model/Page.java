package searchengine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(indexes = @Index(columnList = "path"))//проиндексировать столбец path
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // @Column(name="site_id", nullable = false)
    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name= "s_id", nullable = false)
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
