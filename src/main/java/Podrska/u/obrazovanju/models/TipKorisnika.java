package Podrska.u.obrazovanju.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "tipKorisnika")
public class TipKorisnika  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ulogeID;

    @NotBlank
    @Column(length = 20)
    private String nazivUloge;

    @OneToMany(mappedBy = "uloga")
    private Set<Korisnik> korisnici;

    @OneToMany(mappedBy = "uloge")
    private Set<Ustanova> ustanove;

    public String getNazivUloge() {
        return nazivUloge;
    }

    public void setNazivUloge(String nazivUloge) {
        this.nazivUloge = nazivUloge;
    }

    public Set<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(Set<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }

    public Set<Ustanova> getUstanove() {
        return ustanove;
    }

    public void setUstanove(Set<Ustanova> ustanove) {
        this.ustanove = ustanove;
    }

    public TipKorisnika() { }
}
