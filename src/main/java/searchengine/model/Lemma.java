package searchengine.model;

import javax.persistence.*;

@Entity
public class Lemma {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade=CascadeType.ALL)
    private Site site;
    @Column(nullable = false)
    private String lemma;
    @Column(nullable = false)
    private int frequency;

}
