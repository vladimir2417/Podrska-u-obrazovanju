package Podrska.u.obrazovanju.models;


import org.springframework.web.multipart.MultipartFile;

public class OglasDto {

    private Long oglasID;

    private String naziv;

    private String oblast;

    private String grad;

    private String nivo;

    private String opis;

    private Float cena;

    private String slika;

    private MultipartFile slikaMulti;

    public MultipartFile getSlikaMulti() {
        return slikaMulti;
    }

    public void setSlikaMulti(MultipartFile slikaMulti) {
        this.slikaMulti = slikaMulti;
    }

    private Ustanova ustanove;

    public Long getOglasID() {
        return oglasID;
    }

    public void setOglasID(Long oglasID) {
        this.oglasID = oglasID;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
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

    public Ustanova getUstanove() {
        return ustanove;
    }

    public void setUstanove(Ustanova ustanove) {
        this.ustanove = ustanove;
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
}

