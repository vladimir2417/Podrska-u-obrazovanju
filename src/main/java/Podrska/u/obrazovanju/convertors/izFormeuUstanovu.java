package Podrska.u.obrazovanju.convertors;

import Podrska.u.obrazovanju.models.Ustanova;
import Podrska.u.obrazovanju.models.UstanovaDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class izFormeuUstanovu implements Converter<UstanovaDto, Ustanova> {

    @Override
    public Ustanova convert(UstanovaDto ustanovaDto) {
        Ustanova ustanova = new Ustanova();
        if (ustanovaDto.getUstanoveID() != null  && !StringUtils.isEmpty(ustanovaDto.getUstanoveID())) {
            ustanova.setUstanoveID(new Long(ustanovaDto.getUstanoveID()));
        }
        ustanova.setUstanoveID(ustanovaDto.getUstanoveID());
        ustanova.setNaziv(ustanovaDto.getNaziv());
        ustanova.setAdresa(ustanovaDto.getAdresa());
        ustanova.setGrad(ustanovaDto.getGrad());
        ustanova.setBrTelefona(ustanovaDto.getBrTelefona());
        ustanova.setEmail(ustanovaDto.getEmail());
        ustanova.setUsername(ustanovaDto.getUsername());
        ustanova.setPassword(ustanovaDto.getPassword());
        ustanova.setOpis(ustanovaDto.getOpis());
        ustanova.setSlika(ustanovaDto.getSlika());
        ustanova.setUloge(ustanovaDto.getUloge());
        ustanova.setLista(ustanovaDto.getLista());
        ustanova.setOglasi(ustanovaDto.getOglasi());
        return ustanova;
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
