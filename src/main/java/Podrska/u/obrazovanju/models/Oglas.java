package Podrska.u.obrazovanju.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="oglasi")
public class Oglas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long oglasID;

    @NotBlank(message = "Unesite naziv oglasa!")
    @Column(length = 50)
    private String naziv;

    @NotBlank(message = "Izaberite oblast!")
    @Column(length = 50)
    private String oblast;

    @NotBlank(message = "Izaberite grad!")
    @Column(length = 50)
    private String grad;

    @NotBlank(message = "Izaberite nivo!")
    @Column(length = 50)
    private String nivo;

    @Size(min = 5, max = 200, message = "Opis mora imati izmedju 5 i 200 karaktera")
    @Column(length = 200)
    private String opis;

    @NotNull(message = "Unesite cenu oglasa")
    private Float cena;

    private String slika;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ustanoveID")
    private Ustanova ustanova;

    public Oglas() { }

    public Oglas(Long oglasID, String naziv, String oblast, String grad, String nivo, @Size(min = 5, max = 200, message = "Opis mora imati izmedju 5 i 200 karaktera") String opis, @NotNull(message = "Unesite cenu oglasa") Float cena, String slika, Ustanova ustanova) {
        this.oglasID = oglasID;
        this.naziv = naziv;
        this.oblast = oblast;
        this.grad = grad;
        this.nivo = nivo;
        this.opis = opis;
        this.cena = cena;
        this.slika = slika;
        this.ustanova = ustanova;
    }

    public Long getOglasID() {
        return oglasID;
    }

    public void setOglasID(Long oglasID) {
        this.oglasID = oglasID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public String getOblast() {
        return oblast;
    }

    public void setOblast(String oblast) {
        this.oblast = oblast;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getNivo() {
        return nivo;
    }

    public void setNivo(String nivo) {
        this.nivo = nivo;
    }

    public Ustanova getUstanova() {
        return ustanova;
    }

    public void setUstanova(Ustanova ustanova) {
        this.ustanova = ustanova;
    }
}
