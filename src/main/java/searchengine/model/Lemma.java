package searchengine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Lemma {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int siteId;
    private String lemma;
    private int frequency;

}
