package Podrska.u.obrazovanju.repositories;

import Podrska.u.obrazovanju.models.TipKorisnika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipKorisnikaRepository extends JpaRepository<TipKorisnika, Integer> {
    TipKorisnika findByUlogeID(Integer ulogeID);
}
