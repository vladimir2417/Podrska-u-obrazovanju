package Podrska.u.obrazovanju.models;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="korisnik")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long korisnikID;

    @NotBlank(message = "Unesite ime")
    @Size(min=2, max=50, message = "Vase ime mora imati između 2 i 50 karaktera")
    @Column(length = 50)
    private String ime;

    @NotBlank(message = "Unesite prezime")
    @Column(length = 50)
    private String prezime;

    @NotBlank(message = "Unesite email")
    @Column(length = 100)
    private String email;

    @NotBlank(message = "Unesite username")
    @Column(length = 50)
    private String username;

    @Size(min = 8, message = "Sifra mora imati najmanje 8 karaktera")
    private String password;

    private Integer brPoena;

    @ManyToOne
    @JoinColumn(name="ulogeID")
    private TipKorisnika uloga;

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<KorisnikMail> mailingListe = new ArrayList<>();

    @OneToMany(mappedBy = "korisnik", cascade = CascadeType.ALL)
    private List<ZahtevZaObilazak> zahtevi = new ArrayList<>();

    public Korisnik() { }

    public Korisnik(Long korisnikID, @Size(min = 2, max = 50, message = "Vase ime mora imati između 2 i 50 karaktera") String ime, String prezime, String email, String username, @Size(min = 8, message = "Sifra mora imati najmanje 8 karaktera") String password, Integer brPoena, TipKorisnika uloga, List<KorisnikMail> mailingListe, List<ZahtevZaObilazak> zahtevi) {
        this.korisnikID = korisnikID;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
        this.brPoena = brPoena;
        this.uloga = uloga;
        this.mailingListe = mailingListe;
        this.zahtevi = zahtevi;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;

        if(o == null || getClass() !=o.getClass())
            return  false;

        Korisnik korisnik = (Korisnik) o;
        return Objects.equals(ime, korisnik.ime);
    }

    @Override
    public int hashCode(){
        return Objects.hash(ime);
    }

    public Long getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(Long korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipKorisnika getUloga() {
        return uloga;
    }

    public String getNazivUloge() {
        return uloga.getNazivUloge();
    }

    public void setUloga(TipKorisnika uloga) {
        this.uloga = uloga;
    }

    public List<KorisnikMail> getMailingListe() {
        return mailingListe;
    }

    public void setMailingListe(List<KorisnikMail> mailingListe) {
        this.mailingListe = mailingListe;
    }

    public Integer getBrPoena() {
        return brPoena;
    }

    public void setBrPoena(Integer brPoena) {
        this.brPoena = brPoena;
    }

    public List<ZahtevZaObilazak> getZahtevi() {
        return zahtevi;
    }

    public void setZahtevi(List<ZahtevZaObilazak> zahtevi) {
        this.zahtevi = zahtevi;
    }
}
