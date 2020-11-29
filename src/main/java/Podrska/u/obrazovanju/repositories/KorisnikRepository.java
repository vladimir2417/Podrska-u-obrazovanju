package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.Korisnik;
import Podrska.u.obrazovanju.models.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Optional<Korisnik> findKorisnikByUsername(String username);
    Optional<Korisnik> findKorisnikByEmail(String email);
    List<Korisnik> findAllByUloga(TipKorisnika uloga);
}
