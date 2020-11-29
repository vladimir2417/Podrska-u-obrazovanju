package Podrska.u.obrazovanju.convertors;

import Podrska.u.obrazovanju.models.Oglas;
import Podrska.u.obrazovanju.models.OglasDto;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class izFormeUOglas implements Converter<OglasDto, Oglas> {
    @Override
    public Oglas convert(OglasDto oglasDto) {
        Oglas oglas = new Oglas();
        if (oglasDto.getOglasID() != null  && !StringUtils.isEmpty(oglasDto.getOglasID())) {
            oglas.setOglasID(new Long(oglasDto.getOglasID()));
        }

        oglas.setNaziv(oglasDto.getNaziv());
        oglas.setOblast(oglasDto.getOblast());
        oglas.setGrad(oglasDto.getGrad());
        oglas.setNivo(oglasDto.getNivo());
        oglas.setOpis(oglasDto.getOpis());
        oglas.setCena(oglasDto.getCena());
        oglas.setSlika(oglasDto.getSlika());
        oglas.setUstanova(oglasDto.getUstanove());
        return oglas;
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
