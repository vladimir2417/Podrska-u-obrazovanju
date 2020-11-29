package Podrska.u.obrazovanju.security_config;

import Podrska.u.obrazovanju.models.Korisnik;
import Podrska.u.obrazovanju.models.Ustanova;
import Podrska.u.obrazovanju.repositories.KorisnikRepository;
import Podrska.u.obrazovanju.repositories.UstanovaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MojKorisnikDetaljiService implements UserDetailsService {

    @Autowired
    KorisnikRepository korisnikRepository;
    @Autowired
    UstanovaRepository ustanovaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Korisnik> korisnik = korisnikRepository.findKorisnikByUsername(username);
        if (korisnik.isPresent()) {
            korisnik.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
            return korisnik.map(MojKorisnikDetalji::new).get();
        } else {
            Optional<Ustanova> ustanova = ustanovaRepository.findUstanovaByUsername(username);
                ustanova.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
                return ustanova.map(MojaUstanovaDetalji::new).get();
        }
    }
}
