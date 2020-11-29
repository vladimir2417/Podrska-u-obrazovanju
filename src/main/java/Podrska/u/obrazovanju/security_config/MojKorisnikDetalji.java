package Podrska.u.obrazovanju.security_config;

import Podrska.u.obrazovanju.models.Korisnik;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MojKorisnikDetalji implements UserDetails {

    private String ime;
    private String prezime;
    private String email;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public MojKorisnikDetalji(Korisnik korisnik){
        this.ime = korisnik.getIme();
        this.prezime = korisnik.getPrezime();
        this.email = korisnik.getEmail();
        this.username = korisnik.getUsername();
        this.password = korisnik.getPassword();
        this.authorities = Arrays.stream(korisnik.getNazivUloge().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
