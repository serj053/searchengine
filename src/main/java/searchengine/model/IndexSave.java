package searchengine.model;

import javax.persistence.*;

@Entity
@Table(name = "save_index")
public class IndexSave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "page_id", nullable = false)
    private int pageId;
    @Column(name = "lemma_id", nullable = false)
    private int lemmaId;
    @Column(nullable = false)
    private float rank_v;
}
