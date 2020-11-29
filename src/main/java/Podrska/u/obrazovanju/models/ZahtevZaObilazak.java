package Podrska.u.obrazovanju.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="zahtevi")
public class ZahtevZaObilazak {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long zahtevID;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datum;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date vreme;

    @Column(length = 50)
    private String stanje;

    @ManyToOne
    @JoinColumn(name="korisnikID")
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumn(name="ustanovaID")
    private Ustanova ustanova;

    public ZahtevZaObilazak(){};

    public ZahtevZaObilazak(Long zahtevID, Date datum, Date vreme, String stanje, Korisnik korisnik, Ustanova ustanova) {
        this.zahtevID = zahtevID;
        this.datum = datum;
        this.vreme = vreme;
        this.stanje = stanje;
        this.korisnik = korisnik;
        this.ustanova = ustanova;
    }

    public Long getZahtevID() {
        return zahtevID;
    }

    public void setZahtevID(Long zahtevID) {
        this.zahtevID = zahtevID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Ustanova getUstanova() {
        return ustanova;
    }

    public void setUstanova(Ustanova ustanova) {
        this.ustanova = ustanova;
    }
}
