package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.Oglas;
import Podrska.u.obrazovanju.models.Ustanova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OglasRespository extends JpaRepository<Oglas, Long> {
    List<Oglas> findAllByUstanova(Ustanova ustanova);
    List<Oglas> findAllByOblastAndGradAndNivo(String oblast, String grad, String nivo);
    List<Oglas> findAllByOblast(String oblast);
    Oglas getByOglasID(Long oglasID);
}
