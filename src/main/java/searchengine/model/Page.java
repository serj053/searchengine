package searchengine.model;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(columnList = "path"))
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // @Column(name="site_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL )
    //@JoinColumn(name= "id" )
    private SiteDB site;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private int code;
    @Column(nullable = false)
    private String content;

    public Page(SiteDB site, String path, int code, String content) {
        this.site = site;
        this.path = path;
        this.code = code;
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SiteDB getSite() {
        return site;
    }

    public void setSite(SiteDB site) {
        this.site = site;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
