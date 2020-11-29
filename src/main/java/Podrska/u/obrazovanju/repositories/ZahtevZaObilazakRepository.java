package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.Korisnik;
import Podrska.u.obrazovanju.models.Ustanova;
import Podrska.u.obrazovanju.models.ZahtevZaObilazak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZahtevZaObilazakRepository extends JpaRepository<ZahtevZaObilazak, Long> {
    Optional<ZahtevZaObilazak> findAllByKorisnikAndUstanova(Korisnik korisnik, Ustanova ustanova);
    List<ZahtevZaObilazak> findAllByUstanovaAndStanjeLike(Ustanova ustanova, String stanje);
    Optional<ZahtevZaObilazak> findByZahtevIDAndStanjeLike(Long zahtevID, String stanje);

}
