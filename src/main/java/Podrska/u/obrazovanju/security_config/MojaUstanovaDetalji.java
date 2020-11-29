package Podrska.u.obrazovanju.security_config;

import Podrska.u.obrazovanju.models.Ustanova;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MojaUstanovaDetalji implements UserDetails {

    private String naziv;
    private String email;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public MojaUstanovaDetalji(Ustanova ustanova){
        this.naziv = ustanova.getNaziv();
        this.email = ustanova.getEmail();
        this.username = ustanova.getUsername();
        this.password = ustanova.getPassword();
        this.authorities = Arrays.stream(ustanova.getNazivUloge().split(","))
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
