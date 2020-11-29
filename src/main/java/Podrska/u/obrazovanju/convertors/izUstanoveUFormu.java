package Podrska.u.obrazovanju.convertors;

import Podrska.u.obrazovanju.models.Ustanova;
import Podrska.u.obrazovanju.models.UstanovaDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

@Component
public class izUstanoveUFormu implements Converter<Ustanova, UstanovaDto> {
    @Override
    public UstanovaDto convert(Ustanova ustanova) {
        UstanovaDto ustanovaDto = new UstanovaDto();
        ustanovaDto.setUstanoveID(ustanova.getUstanoveID());
        ustanovaDto.setNaziv(ustanova.getNaziv());
        ustanovaDto.setAdresa(ustanova.getAdresa());
        ustanovaDto.setGrad(ustanova.getGrad());
        ustanovaDto.setBrTelefona(ustanova.getBrTelefona());
        ustanovaDto.setEmail(ustanova.getEmail());
        ustanovaDto.setUsername(ustanova.getUsername());
        ustanovaDto.setPassword(ustanova.getPassword());
        ustanovaDto.setOpis(ustanova.getOpis());
        ustanovaDto.setSlika(ustanova.getSlika());
        ustanovaDto.setUloge(ustanova.getUloge());
        ustanovaDto.setLista(ustanova.getLista());
        ustanovaDto.setOglasi(ustanova.getOglasi());
        return ustanovaDto;
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
