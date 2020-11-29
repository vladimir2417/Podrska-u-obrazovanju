package Podrska.u.obrazovanju.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class KorisnikDto {

    private Long korisnikID;

    @NotBlank(message = "Unesite ime")
    @Size(min=2, max=50, message = "Ime mora imati između 2 i 50 karaktera")
    private String ime;

    @NotBlank(message = "Unesite prezime")
    @Size(min=2, max=50, message = "Prezime mora imati između 2 i 50 karaktera")
    private String prezime;

    @NotBlank(message = "Unesite email")
    @Pattern(
            regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$",
            message = "Email nije u dobrom formatu."
    )
    private String email;

    @NotBlank(message = "Unesite korisničko ime")
    @Size(min=2, max=50, message = "Korisničko ime mora imati između 2 i 50 karaktera")
    private String username;

    @NotBlank(message = "Unesite šifru")
    @Size(min=8, max=50, message = "Šifra mora imati najmanje 8 karaktera")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Šifra nije u dobrom formatu."
    )
    private String password;

    private Integer brPoena;

    private TipKorisnika uloga;

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

    public void setUloga(TipKorisnika uloga) {
        this.uloga = uloga;
    }

    public Integer getBrPoena() {
        return brPoena;
    }

    public void setBrPoena(Integer brPoena) {
        this.brPoena = brPoena;
    }
}
