package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.Korisnik;
import Podrska.u.obrazovanju.models.KorisnikMail;
import Podrska.u.obrazovanju.models.KorisnikMailID;
import Podrska.u.obrazovanju.models.MailingLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikMailRepository extends JpaRepository<KorisnikMail, KorisnikMailID> {
    Optional<KorisnikMail> findAllByKorisnikAndMailingLista(Korisnik korisnik, MailingLista mailingLista);
}
