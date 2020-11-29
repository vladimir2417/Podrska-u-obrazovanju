package Podrska.u.obrazovanju.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ustanove")
public class Ustanova {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long ustanoveID;

    @NotBlank(message = "Unesite naziv ustanove")
    @Column(length = 50)
    private String naziv;

    @NotBlank(message = "Unesite adresu ustanove")
    @Column(length = 50)
    private String adresa;

    @NotBlank(message = "Unesite grad ustanove")
    @Column(length = 50)
    private String grad;

    @NotBlank(message = "Unesite br. telefona ustanove")
    @Column(length = 50)
    private String brTelefona;

    @Email(message = "Unesite email ustanove")
    @Column(length = 100)
    private String email;

    @NotBlank(message = "Unesite username ustanove")
    @Column(length = 50)
    private String username;

    @Size(min = 8, message = "Sifra mora imati najmanje 8 karaktera")
    private String password;

    @NotBlank(message = "Unesite opis ustanove")
    private String opis;

    private String slika;

    @ManyToOne
    @JoinColumn(name="ulogeID")
    private TipKorisnika uloge;

    @OneToMany(mappedBy="ustanova", cascade = CascadeType.ALL)
    private Set<MailingLista> lista;

    @OneToMany(mappedBy="ustanova", cascade = CascadeType.ALL)
    private Set<Oglas> oglasi;

    @OneToMany(mappedBy = "ustanova", cascade = CascadeType.ALL)
    private List<ZahtevZaObilazak> zahtevi = new ArrayList<>();

    public Ustanova() { }

    public Ustanova(Long ustanoveID, String naziv, String adresa, String grad, String brTelefona, String email, String username, @Size(min = 8, message = "Sifra mora imati najmanje 8 karaktera") String password, String opis, String slika, TipKorisnika uloge, Set<MailingLista> lista, Set<Oglas> oglasi, List<ZahtevZaObilazak> zahtevi) {
        this.ustanoveID = ustanoveID;
        this.naziv = naziv;
        this.adresa = adresa;
        this.grad = grad;
        this.brTelefona = brTelefona;
        this.email = email;
        this.username = username;
        this.password = password;
        this.opis = opis;
        this.slika = slika;
        this.uloge = uloge;
        this.lista = lista;
        this.oglasi = oglasi;
        this.zahtevi = zahtevi;
    }

    public Long getUstanoveID() {
        return ustanoveID;
    }

    public void setUstanoveID(Long ustanoveID) {
        this.ustanoveID = ustanoveID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public TipKorisnika getUloge() {
        return uloge;
    }

    public String getNazivUloge(){return uloge.getNazivUloge();}

    public void setUloge(TipKorisnika uloge) {
        this.uloge = uloge;
    }

    public Set<MailingLista> getLista() {
        return lista;
    }

    public void setLista(Set<MailingLista> lista) {
        this.lista = lista;
    }

    public Set<Oglas> getOglasi() {
        return oglasi;
    }

    public void setOglasi(Set<Oglas> oglasi) {
        this.oglasi = oglasi;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getBrTelefona() {
        return brTelefona;
    }

    public void setBrTelefona(String brTelefona) {
        this.brTelefona = brTelefona;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public List<ZahtevZaObilazak> getZahtevi() {
        return zahtevi;
    }

    public void setZahtevi(List<ZahtevZaObilazak> zahtevi) {
        this.zahtevi = zahtevi;
    }
}
