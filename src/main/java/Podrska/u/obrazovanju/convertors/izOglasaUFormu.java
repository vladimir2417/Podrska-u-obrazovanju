package Podrska.u.obrazovanju.convertors;

import Podrska.u.obrazovanju.models.Oglas;
import Podrska.u.obrazovanju.models.OglasDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

@Component
public class izOglasaUFormu implements Converter<Oglas, OglasDto> {

    @Override
    public OglasDto convert(Oglas oglas) {
        OglasDto oglasDto = new OglasDto();
        oglasDto.setOglasID(oglas.getOglasID());
        oglasDto.setNaziv(oglas.getNaziv());
        oglasDto.setOblast(oglas.getOblast());
        oglasDto.setGrad(oglas.getGrad());
        oglasDto.setNivo(oglas.getNivo());
        oglasDto.setOpis(oglas.getOpis());
        oglasDto.setCena(oglas.getCena());
        oglasDto.setSlika(oglas.getSlika());
        oglasDto.setUstanove(oglas.getUstanova());
        return oglasDto;
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
