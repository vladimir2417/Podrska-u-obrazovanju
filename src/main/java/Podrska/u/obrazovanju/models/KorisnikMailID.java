package Podrska.u.obrazovanju.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class KorisnikMailID implements Serializable {

    @Column(name = "korsinikID")
    private Long korisnikID;

    @Column(name = "mailingListeID")
    private Long mailingListeID;

    public KorisnikMailID(Long korisnikID, Long mailingListeID) {
        this.korisnikID = korisnikID;
        this.mailingListeID = mailingListeID;
    }

    public KorisnikMailID() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        KorisnikMailID that = (KorisnikMailID) o;
        return Objects.equals(korisnikID, that.korisnikID) && Objects.equals(mailingListeID, that.mailingListeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnikID, mailingListeID);
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }

    public Long getMailingListeID() {
        return mailingListeID;
    }

    public void setMailingListeID(Long mailingListeID) {
        this.mailingListeID = mailingListeID;
    }
}