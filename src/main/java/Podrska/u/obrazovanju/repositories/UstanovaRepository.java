package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.Oglas;
import Podrska.u.obrazovanju.models.Ustanova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UstanovaRepository extends JpaRepository<Ustanova, Long> {
    Optional<Ustanova> findUstanovaByUsername(String username);
    Ustanova findUstanovaByOglasi(Oglas oglas);
    Ustanova getByUstanoveID(Long ustanoveID);
    Ustanova getByUsername(String username);
}
