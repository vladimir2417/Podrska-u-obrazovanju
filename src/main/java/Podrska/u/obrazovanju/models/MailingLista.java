package Podrska.u.obrazovanju.models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="mailingListe")
@NaturalIdCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MailingLista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long mailingListeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ustanoveID")
    private Ustanova ustanova;

    @OneToMany(
            mappedBy = "mailingLista",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<KorisnikMail> korisnici = new ArrayList<>();

    public MailingLista(){}

    public MailingLista(Long mailingListeID, Ustanova ustanova, List<KorisnikMail> korisnici) {
        this.mailingListeID = mailingListeID;
        this.ustanova = ustanova;
        this.korisnici = korisnici;
    }

    public Long getMailingListeID() {
        return mailingListeID;
    }

    public void setMailingListeID(Long mailingListeID) {
        this.mailingListeID = mailingListeID;
    }

    public Ustanova getUstanova() {
        return ustanova;
    }

    public void setUstanova(Ustanova ustanova) {
        this.ustanova = ustanova;
    }

    public List<KorisnikMail> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<KorisnikMail> korisnici) {
        this.korisnici = korisnici;
    }
}

