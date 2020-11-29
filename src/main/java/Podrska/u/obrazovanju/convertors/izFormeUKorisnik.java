package Podrska.u.obrazovanju.convertors;

import Podrska.u.obrazovanju.models.Korisnik;
import Podrska.u.obrazovanju.models.KorisnikDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class izFormeUKorisnik implements Converter<KorisnikDto, Korisnik> {
    @Override
    public Korisnik convert(KorisnikDto korisnikDto) {
        Korisnik korisnik = new Korisnik();
        if (korisnikDto.getKorisnikID() != null  && !StringUtils.isEmpty(korisnikDto.getKorisnikID())) {
            korisnik.setKorisnikID(new Long(korisnikDto.getKorisnikID()));
        }
        korisnik.setIme(korisnikDto.getIme());
        korisnik.setPrezime(korisnikDto.getPrezime());
        korisnik.setUsername(korisnikDto.getUsername());
        korisnik.setPassword(korisnikDto.getPassword());
        korisnik.setBrPoena(korisnikDto.getBrPoena());
        korisnik.setEmail(korisnikDto.getEmail());
        korisnik.setUloga(korisnikDto.getUloga());
        return korisnik;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
