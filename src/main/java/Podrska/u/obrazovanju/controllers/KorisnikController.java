package Podrska.u.obrazovanju.controllers;

import Podrska.u.obrazovanju.convertors.izKorisnikUFormu;
import Podrska.u.obrazovanju.models.*;
import Podrska.u.obrazovanju.repositories.*;
import Podrska.u.obrazovanju.email_config.Barcode_Image;
import Podrska.u.obrazovanju.email_config.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class KorisnikController {

    @Autowired
    private MailingListaRespository mailingListaRespository;
    @Autowired
    private UstanovaRepository ustanovaRepository;
    @Autowired
    private TipKorisnikaRepository tipKorisnikaRepository;
    @Autowired
    private OglasRespository oglasRespository;
    @Autowired
    private KorisnikMailRepository korisnikMailRepository;
    @Autowired
    private ZahtevZaObilazakRepository zahtevZaObilazakRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static MailService mailService;
    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService= mailService;
    }

    private static KorisnikRepository korisnikRepository;
    @Autowired
    public void setKorisnikRepository(KorisnikRepository korisnikRepository) {
        this.korisnikRepository= korisnikRepository;
    }

    private Podrska.u.obrazovanju.convertors.izKorisnikUFormu izKorisnikUFormu;
    @Autowired
    public void setIzKorisnikUFormu(izKorisnikUFormu izKorisnikUFormu) {
        this.izKorisnikUFormu = izKorisnikUFormu;
    }

    private Podrska.u.obrazovanju.convertors.izFormeUKorisnik izFormeUKorisnik;
    @Autowired
    public void setIzFormeUKorisnik(Podrska.u.obrazovanju.convertors.izFormeUKorisnik izFormeUKorisnik) { this.izFormeUKorisnik = izFormeUKorisnik; }

    //SVI
    @RequestMapping("/registracija")
    public String noviKorisnik(Model model){
        model.addAttribute("korisnikDto", new KorisnikDto());
        return "registracija";
    }

    //SVI
    @RequestMapping(value = "/korisnik-sacuvaj", method = RequestMethod.POST)
    public String sacuvajIliIzmeniKorisnika(@Valid KorisnikDto korisnikDto, BindingResult bindingResult,RedirectAttributes redirectAttrs){
        if(bindingResult.hasErrors()){
            return "registracija";
        }
        Optional<TipKorisnika> uloge = tipKorisnikaRepository.findById(1);
        if(uloge.isPresent()){
            Optional<Korisnik> probaKorisnik = korisnikRepository.findKorisnikByUsername(korisnikDto.getUsername());
            Optional<Korisnik> probaKorisnik2 = korisnikRepository.findKorisnikByEmail(korisnikDto.getEmail());
            Optional<Ustanova> probaUstanova = ustanovaRepository.findUstanovaByUsername(korisnikDto.getUsername());
            if(!probaKorisnik.isPresent()){
                if(!probaKorisnik2.isPresent()) {
                    if(!probaUstanova.isPresent()) {
                        korisnikDto.setUloga(uloge.get());
                        korisnikDto.setBrPoena(100);
                        korisnikDto.setPassword(passwordEncoder.encode(korisnikDto.getPassword()));
                        korisnikRepository.save(izFormeUKorisnik.convert(korisnikDto));
                        String from = "thegrouppodrska@gmail.com";
                        String recipient = korisnikDto.getEmail();
                        String subject = "Uspešna registracija!";
                        String message = "Hvala sto ste se registrovali na sajt. Vaš username je: "+ korisnikDto.getUsername();
                        mailService.pripremiIPosaljiNoviKorisnik(from, recipient, subject, message);
                    }
                    else {
                        redirectAttrs.addFlashAttribute("poruka", "Postoji registrovana ustanova sa ovim korisničkim imenom.");
                        return "redirect:/registracija";
                    }
                }
                else {
                    redirectAttrs.addFlashAttribute("poruka", "Postoji registrovan korisnik sa ovim email-om.");
                    return "redirect:/registracija";
                }
            }
            else {
                redirectAttrs.addFlashAttribute("poruka", "Postoji registrovan korisnik sa ovim korisničkim imenom.");
                return "redirect:/registracija";
            }
        }
        return "redirect:/login";
    }

    @RequestMapping("/pocetna")
    public String pocetna(Model model){
        model.addAttribute("oglasi", oglasRespository.findAll());
        model.addAttribute("its", ustanovaRepository.getByUsername("its"));
        model.addAttribute("singy", ustanovaRepository.getByUsername("singy"));
        model.addAttribute("etf", ustanovaRepository.getByUsername("etf"));
        return "pocetna";
    }

    @GetMapping("/o-nama")
    public String oNama(){
        return "o-nama";
    }

    @RequestMapping("/oglasi")
    public String prikaziListuOglasa(@RequestParam(value="oblast", required = false) String oblast,
                                     @RequestParam(value="grad", required = false) String grad,
                                     @RequestParam(value="nivo", required = false) String nivo,
                                     Model model, RedirectAttributes redirectAttrs){

        List<Oglas> oglasi = oglasRespository.findAllByOblastAndGradAndNivo(oblast, grad, nivo);
        if(!oglasi.isEmpty()){
            model.addAttribute("oglasi", oglasi);
            model.addAttribute("ustanove", ustanovaRepository.findAll());
        }
        else {
            redirectAttrs.addFlashAttribute("poruka", "Nema oglasa za unete parametre :(");
            return "redirect:/pocetna";
        }
        return "oglasi-lista";
    }

    @RequestMapping("/oglasi/svi")
    public String prikaziListuSvihOglasa(Model model, RedirectAttributes redirectAttrs){

        List<Oglas> oglasi = oglasRespository.findAll();
        if(!oglasi.isEmpty()){
            model.addAttribute("oglasi", oglasi);
            model.addAttribute("ustanove", ustanovaRepository.findAll());
        }
        else {
            redirectAttrs.addFlashAttribute("poruka", "Nema oglasa za unete parametre :(");
            return "redirect:/pocetna";
        }
        return "oglasi-lista";
    }

    @RequestMapping("/oglasi/{ustanoveID}")
    public String prikaziListuOglasaZaUstanovu(@PathVariable String ustanoveID,
                                     Model model, RedirectAttributes redirectAttrs){

        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));
        List<Oglas> oglasi = oglasRespository.findAllByUstanova(ustanova);
        if(!oglasi.isEmpty()){
            model.addAttribute("oglasi", oglasi);
            model.addAttribute("ustanove", ustanovaRepository.findAll());
        }
        else {
            redirectAttrs.addFlashAttribute("poruka", "Nema oglasa za unete parametre :(");
            return "redirect:/pocetna";

        }
        return "oglasi-lista";
    }

    @RequestMapping("/oglasi/oblast/{oblast}")
    public String prikaziListuOglasaPoOblasti(@PathVariable String oblast,
                                               Model model, RedirectAttributes redirectAttrs){

        List<Oglas> oglasi = oglasRespository.findAllByOblast(oblast);
        if(!oglasi.isEmpty()){
            model.addAttribute("oglasi", oglasi);
            model.addAttribute("ustanove", ustanovaRepository.findAll());
        }
        else {
            redirectAttrs.addFlashAttribute("poruka", "Nema oglasa za unete parametre :(");
            return "redirect:/pocetna";

        }
        return "oglasi-lista";
    }

    @RequestMapping("/oglas/{oglasID}")
    public String prikaziOglas(@PathVariable String oglasID, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);

        Oglas oglas = oglasRespository.getByOglasID(Long.valueOf(oglasID));
        Ustanova ustanova = ustanovaRepository.findUstanovaByOglasi(oglas);

        if(logovanKorisnik.isPresent()) {
            Korisnik korisnik = logovanKorisnik.get();

            MailingLista mailingLista = mailingListaRespository.getByUstanova(ustanova);
            Optional<KorisnikMail> vecJePrijavljen = korisnikMailRepository.findAllByKorisnikAndMailingLista(korisnik, mailingLista);
            Optional<ZahtevZaObilazak> vecJeZakazao = zahtevZaObilazakRepository.findAllByKorisnikAndUstanova(korisnik, ustanova);

            if (vecJeZakazao.isPresent()) {
                model.addAttribute("daLiJeZakazao", true);
            }
            else{
                model.addAttribute("daLiJeZakazao", false);
            }

            if (vecJePrijavljen.isPresent()) {
                model.addAttribute("daLiJePrijavljen", true);
            }
            else{
                model.addAttribute("daLiJePrijavljen", false);
            }
        }

        model.addAttribute("oglas", oglas);
        model.addAttribute("oglasiUstanove", oglasRespository.findAllByUstanova(ustanova));
        model.addAttribute("ustanova", ustanova);

        return "oglasi-jedan";
    }

    @RequestMapping(value = "/mejling-lista-sacuvaj/{ustanoveID}/{oglasID}")
    public String sacuvajMejlingListu(@PathVariable String ustanoveID, @PathVariable String oglasID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);
        Korisnik korisnik = logovanKorisnik.get();

        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));
        MailingLista mailingLista = mailingListaRespository.getByUstanova(ustanova);

        KorisnikMail korisnikMail = new KorisnikMail(korisnik, mailingLista);

        korisnik.getMailingListe().add(korisnikMail);
        korisnik.setBrPoena(korisnik.getBrPoena()+100);
        korisnikRepository.save(korisnik);

        if(korisnik.getBrPoena()>=200){
            korisnik.setBrPoena(0);
            korisnikRepository.save(korisnik);
            return "redirect:/cestitamo";
        }
        else {
            return "redirect:/oglas/"+oglasID;
        }
    }

    @RequestMapping(value = "/mejling-lista-obrisi/{ustanoveID}/{oglasID}")
    public String obrisiIzMejlingListe(@PathVariable String ustanoveID, @PathVariable String oglasID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);
        Korisnik korisnik = logovanKorisnik.get();

        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));
        MailingLista mailingLista = mailingListaRespository.getByUstanova(ustanova);

        Optional<KorisnikMail> korisnikMail = korisnikMailRepository.findAllByKorisnikAndMailingLista(korisnik, mailingLista);
        if(korisnikMail.isPresent()) {
            korisnikMailRepository.delete(korisnikMail.get());
            if (korisnik.getBrPoena() > 0) {
                korisnik.setBrPoena(korisnik.getBrPoena()-100);
            }
            korisnikRepository.save(korisnik);
        }
        return "redirect:/oglas/"+oglasID;

    }

    @RequestMapping("/cestitamo")
    public static String cestitamo(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);
        Korisnik korisnik = logovanKorisnik.get();
        Barcode_Image.createImage(korisnik.getIme()+korisnik.getPrezime()+"Vaucer.png", String.valueOf(korisnik.getKorisnikID()));

        String from = "thegrouppodrska@gmail.com";
        String recipient = korisnik.getEmail();
        String subject = "Poklon vaučer";
        String message = "POKLON VAUČER";
        String putanjaBarcode = System.getProperty("user.dir") + "/src/main/uploads/static/Barcode/"+korisnik.getIme()+korisnik.getPrezime()+"Vaucer.png";
        mailService.pripremiIPosaljiVaucer(from, recipient, subject, message, putanjaBarcode);
        return "cestitamo";
    }

    @RequestMapping("/zakazivanje-obilaska/{ustanoveID}")
    public String zakazivanjeObilaska(Model model, @PathVariable String ustanoveID ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);
        Korisnik korisnik = logovanKorisnik.get();
        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));

        Optional<ZahtevZaObilazak> zahtevZaObilazakProba = zahtevZaObilazakRepository.findAllByKorisnikAndUstanova(korisnik, ustanova);
        if(zahtevZaObilazakProba.isPresent()){
            model.addAttribute("daLiJeZakazao", true);
        }

        else{
            model.addAttribute("poruka", "Zakažite obilazak ustanove");
            model.addAttribute("daLiJeZakazao", false);
            model.addAttribute("zahtevZaObilazak", new ZahtevZaObilazak());
        }

        model.addAttribute("oglasi", oglasRespository.findAll());
        model.addAttribute("ustanova", ustanova);
        return "zakazivanje-obilaska";
    }

    @RequestMapping(value = "/sacuvaj-zahtev", method = RequestMethod.POST)
    public String sacuvajZahtev(@Valid ZahtevZaObilazak zahtevZaObilazak,
                                @RequestParam(value="ustanoveID") String ustanoveID,
                                @RequestParam(value="datum", required = false) String datumStr,
                                @RequestParam(value="vreme", required = false) String vremeStr,
                                BindingResult bindingResult, RedirectAttributes redirectAttrs) throws ParseException {
        if(bindingResult.hasErrors()){
            return "zakazivanje-obilaska";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Korisnik> logovanKorisnik= korisnikRepository.findKorisnikByUsername(usernameLoginovanog);
        Korisnik korisnik = logovanKorisnik.get();

        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));

        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
        String second = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));

        if(month.length()<2){ month="0"+month; }
        if(day.length()<2){ day="0"+day; }
        if(hour.length()<2){ hour="0"+hour; }
        if(minute.length()<2){ minute="0"+minute; }
        if(second.length()<2){ second="0"+second; }

        String dateStart = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
        String dateStop = datumStr + " "+ vremeStr+":00";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        d1 = format.parse(dateStart);
        d2 = format.parse(dateStop);

        long diff = d2.getTime() - d1.getTime();

        if(diff-60000L>0){

            zahtevZaObilazak.setKorisnik(korisnik);
            zahtevZaObilazak.setUstanova(ustanova);
            zahtevZaObilazak.setStanje("Na cekanju");
            zahtevZaObilazakRepository.save(zahtevZaObilazak);

            TimerTask timerTask = new TimerTask() {
                @Transactional
                @Modifying
                @Override
                public void run() {
                    System.out.println("Zahtev obrisan za " + diff + " "+dateStart+" "+dateStop );
                    Optional<ZahtevZaObilazak> zahtevZaObilazakProba = zahtevZaObilazakRepository.findByZahtevIDAndStanjeLike(zahtevZaObilazak.getZahtevID(), "Na cekanju");
                    if(zahtevZaObilazakProba.isPresent()){
                        zahtevZaObilazakRepository.deleteById(zahtevZaObilazak.getZahtevID());
                    }
                    cancel();
                }
            };

            Timer timer = new Timer("Timer");
            long period = 1000L;
            timer.scheduleAtFixedRate(timerTask, diff-30000L, period);

            TimerTask timerTask2 = new TimerTask() {
                @Transactional
                @Modifying
                @Override
                public void run() {
                    System.out.println("Zahtev obrisan za " + diff + " "+dateStart+" "+dateStop );
                    Optional<ZahtevZaObilazak> zahtevZaObilazakProba = zahtevZaObilazakRepository.findByZahtevIDAndStanjeLike(zahtevZaObilazak.getZahtevID(), "Odobreno");
                    if(zahtevZaObilazakProba.isPresent()){
                        zahtevZaObilazakRepository.deleteById(zahtevZaObilazak.getZahtevID());
                    }
                    cancel();
                }
            };

            Timer timer2 = new Timer("Timer");
            timer2.scheduleAtFixedRate(timerTask2, diff, period);

            redirectAttrs.addFlashAttribute("porukaUspesno", "Uspešno ste zakazali obilazak. Sačekajte potvrdu ustanove koja će Vam stići na email.");
            return "redirect:/zakazivanje-obilaska/"+ustanoveID;
        }

        else{
            redirectAttrs.addFlashAttribute("porukaNeuspesno", "Greška. Neuspešna konekcija sa bazom podataka.");
            return "redirect:/zakazivanje-obilaska/"+ustanoveID;
        }
    }
}

