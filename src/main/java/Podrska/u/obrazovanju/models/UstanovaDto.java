package Podrska.u.obrazovanju.models;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class UstanovaDto {

    private Long ustanoveID;

    private String naziv;

    private String adresa;

    private String grad;

    private String brTelefona;

    private String email;

    private String username;

    private String password;

    private String opis;

    private String slika;

    private MultipartFile slikaMulti;


    private TipKorisnika uloge;

    private Set<MailingLista> lista;

    private  Set<Oglas> oglas;

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
        return oglas;
    }

    public void setOglasi(Set<Oglas> oglas) {
        this.oglas = oglas;
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

    public Set<Oglas> getOglas() {
        return oglas;
    }

    public void setOglas(Set<Oglas> oglas) {
        this.oglas = oglas;
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

    public MultipartFile getSlikaMulti() {
        return slikaMulti;
    }

    public void setSlikaMulti(MultipartFile slikaMulti) {
        this.slikaMulti = slikaMulti;
    }
}
