package Podrska.u.obrazovanju.security_config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("mojKorisnikDetaljiService")
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
                http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**","/uploads/static/slikeOglasa/**","/uploads/static/Barcode/**","/uploads/static/slikeUstanova/**", "/css/**","/js/**","/fonts/**","/fonts/flaticon", "/css/bootstrap/**", "/images/**","/imagesNovi/**","/cute-alert-master/**").permitAll()
                .antMatchers( "/admin/pocetna","/admin/korisnici/lista","/admin/korisnici/obrisi/{korisnikID}","/admin/oglasi/lista","/admin/oglasi/izmeni/{oglasID}","/admin/oglasi/sacuvaj-izmene","/admin/oglasi/obrisi/{oglasID}","/admin/ustanove/lista","/admin/ustanove/izmeni/{ustanoveID}","/admin/ustanove/dodaj","/admin/ustanove/sacuvaj","/admin/ustanove/sacuvaj-izmene","/admin/ustanove/obrisi/{ustanoveID}").hasAuthority("admin")
                        .antMatchers("/ustanova/pocetna","/ustanova/oglasi/lista","/ustanova/oglasi/izmeni/{oglasID}","/ustanova/oglasi/dodaj","/ustanova/oglasi/sacuvaj","/ustanova/oglasi/sacuvaj-izmene","/ustanova/oglasi/obrisi/{oglasID}","/ustanova/zahtevi","/ustanova/zahtevi/odobreni","/ustanova/zahtevi/obrisi/{zahtevID}","/ustanova/zahtevi/odobri/{zahtevID}","/ustanova/slanje-mejla","/ustanova/posalji-mejl").hasAuthority("ustanova")
                        .antMatchers( "/mejling-lista-sacuvaj/{ustanoveID}/{oglasID}","/mejling-lista-obrisi/{ustanoveID}/{oglasID}","/cestitamo","/zakazivanje-obilaska/{ustanoveID}","/sacuvaj-zahtev").hasAuthority("korisnik")
                .antMatchers("/pocetna","/","/registracija","/korisnik-sacuvaj", "/login","/logout","/oglasi","/oglasi/svi","/oglasi/{ustanoveID}","/oglasi/oblast/{oblast}", "/oglas/{oglasID}","/kontakt","/o-nama", "/forgotPass", "/salji-pass").permitAll()
                        .anyRequest().authenticated()
                 .and()
               .formLogin()
               .loginPage("/login")
                        .successHandler(myAuthenticationSuccessHandler())
              .permitAll()
              .and()

              .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/pocetna");
    }
}

