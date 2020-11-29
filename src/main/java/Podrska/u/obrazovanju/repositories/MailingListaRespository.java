package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.MailingLista;
import Podrska.u.obrazovanju.models.Ustanova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MailingListaRespository extends JpaRepository<MailingLista, Long> {
    List<MailingLista> findAllByUstanova(Ustanova ustanova);
    MailingLista getByUstanova(Ustanova ustanova);
}
