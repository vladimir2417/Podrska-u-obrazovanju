package Podrska.u.obrazovanju.controllers;

import Podrska.u.obrazovanju.models.*;
import Podrska.u.obrazovanju.repositories.ZahtevZaObilazakRepository;
import Podrska.u.obrazovanju.email_config.MailService;

import Podrska.u.obrazovanju.repositories.MailingListaRespository;
import Podrska.u.obrazovanju.repositories.OglasRespository;
import Podrska.u.obrazovanju.repositories.UstanovaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class UstanoveController {

    @Autowired
    private UstanovaRepository ustanovaRepository;
    @Autowired
    private MailingListaRespository mailingListaRespository;
    @Autowired
    private OglasRespository oglasRespository;
    @Autowired
    private ZahtevZaObilazakRepository zahtevZaObilazakRepository;
    @Autowired
    private MailService mailService;

    private Podrska.u.obrazovanju.convertors.izOglasaUFormu izOglasaUFormu;
    @Autowired
    public void setIzOglasaUFormu(Podrska.u.obrazovanju.convertors.izOglasaUFormu izOglasaUFormu) { this.izOglasaUFormu = izOglasaUFormu; }

    private Podrska.u.obrazovanju.convertors.izFormeUOglas izFormeUOglas;
    @Autowired
    public void setIzFormeUOglas(Podrska.u.obrazovanju.convertors.izFormeUOglas izFormeUOglas) { this.izFormeUOglas = izFormeUOglas; }

    private Podrska.u.obrazovanju.convertors.izUstanoveUFormu izUstanoveUFormu;
    @Autowired
    public void setIzUstanoveUFormu(Podrska.u.obrazovanju.convertors.izUstanoveUFormu izUstanoveUFormu) { this.izUstanoveUFormu = izUstanoveUFormu; }

    @GetMapping("/ustanova/pocetna")
    public String ustanovaDeo(){
        return "ustanova-pocetna";
    }

    @RequestMapping("/ustanova/oglasi/lista")
    public String ustanovaListaOglasa(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Ustanova> loginovanaUstanova= ustanovaRepository.findUstanovaByUsername(usernameLoginovanog);
        Ustanova ustanova = loginovanaUstanova.get();
        List<Oglas> oglasi = oglasRespository.findAllByUstanova(ustanova);
        model.addAttribute("oglasi", oglasi);
        return "ustanova-oglasi-lista";
    }

    @RequestMapping("/ustanova/oglasi/izmeni/{oglasID}")
    public String ustanovaIzmeniOglas(@PathVariable String oglasID, Model model){
        Oglas oglas = oglasRespository.getByOglasID(Long.valueOf(oglasID));
        OglasDto oglasDto = izOglasaUFormu.convert(oglas);
        model.addAttribute("oglasDto", oglasDto);
        return "ustanova-oglasi-izmeni";
    }

    @RequestMapping("/ustanova/oglasi/dodaj")
    public String ustanovaNoviOglas(Model model){
        model.addAttribute("oglasDto", new OglasDto());
        return "ustanova-oglasi-dodaj";
    }

    @RequestMapping(value = "/ustanova/oglasi/sacuvaj", method = RequestMethod.POST)
    public String ustanovaSacuvajOglas(@Valid OglasDto oglasDto, BindingResult bindingResult) throws IOException {

        String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads/static/slikeOglasa";
        MultipartFile slika = oglasDto.getSlikaMulti();
        Path imageNameAndPath = Paths.get(uploadDirectory, slika.getOriginalFilename());
        String putanja = "../uploads/static/slikeOglasa/" + slika.getOriginalFilename();
        Files.write(imageNameAndPath, slika.getBytes());
        oglasDto.setSlika(putanja);
        if(bindingResult.hasErrors()){
            return "ustanova-oglasi-dodaj";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Ustanova> loginovanaUstanova= ustanovaRepository.findUstanovaByUsername(usernameLoginovanog);
        Long idLoginovaneUstanove = loginovanaUstanova.get().getUstanoveID();
        Optional<Ustanova> ustanova = ustanovaRepository.findById(idLoginovaneUstanove);
        if(ustanova.isPresent()){
            oglasDto.setUstanove(ustanova.get());
            oglasRespository.save(izFormeUOglas.convert(oglasDto));
            return "redirect:/ustanova/oglasi/lista";
        }
        return "redirect:/ustanova/oglasi/lista";
    }

    @RequestMapping(value = "/ustanova/oglasi/sacuvaj-izmene", method = RequestMethod.POST)
    public String ustanovaSacuvajIzmeneOglasa(@Valid OglasDto oglasDto, BindingResult bindingResult) {
        Oglas oglasNas = oglasRespository.getByOglasID(oglasDto.getOglasID());
        oglasDto.setSlika(oglasNas.getSlika());
        oglasRespository.save(izFormeUOglas.convert(oglasDto));
        return "redirect:/ustanova/oglasi/lista";
    }

    @RequestMapping("/ustanova/oglasi/obrisi/{oglasID}")
    public String ustanovaObrisiOglas(@PathVariable String oglasID){
        oglasRespository.deleteById(Long.valueOf(oglasID));
        return "redirect:/ustanova/oglasi/lista";
    }

    @RequestMapping("/ustanova/zahtevi")
    public String ustanovaListaZahteva(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Ustanova> loginovanaUstanova= ustanovaRepository.findUstanovaByUsername(usernameLoginovanog);
        Ustanova ustanova = loginovanaUstanova.get();

        List<ZahtevZaObilazak> zahtevi = zahtevZaObilazakRepository.findAllByUstanovaAndStanjeLike(ustanova,"Na cekanju");
        if (!zahtevi.isEmpty()){
            model.addAttribute("zahtevi", zahtevi);
        }
        else{
            model.addAttribute("poruka", "Trenutno nema zahteva");
        }
        return "ustanova-zahtevi";
    }

    @RequestMapping("/ustanova/zahtevi/odobreni")
    public String ustanovaListaZahtevaOdobrenih(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovanog = authentication.getName();
        Optional<Ustanova> loginovanaUstanova= ustanovaRepository.findUstanovaByUsername(usernameLoginovanog);
        Ustanova ustanova = loginovanaUstanova.get();

        List<ZahtevZaObilazak> zahtevi = zahtevZaObilazakRepository.findAllByUstanovaAndStanjeLike(ustanova,"Odobreno");
        if (!zahtevi.isEmpty()){
            model.addAttribute("zahtevi", zahtevi);
        }
        else{
            model.addAttribute("poruka", "Trenutno nema zahteva");
        }

        return "ustanova-zahtevi-odobreni";
    }

    @RequestMapping("/ustanova/zahtevi/obrisi/{zahtevID}")
    public String ustanovaObrisiZahtev(@PathVariable String zahtevID){
        zahtevZaObilazakRepository.deleteById(Long.valueOf(zahtevID));
        return "redirect:/ustanova/zahtevi";
    }

    @RequestMapping("/ustanova/zahtevi/odobri/{zahtevID}")
    public String ustanovaPotvrdiZahtev(@PathVariable String zahtevID){
        ZahtevZaObilazak zahtevZaObilazak = zahtevZaObilazakRepository.getOne(Long.valueOf(zahtevID));
        zahtevZaObilazak.setStanje("Odobreno");
        zahtevZaObilazakRepository.save(zahtevZaObilazak);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovaneUstanove = authentication.getName();
        Optional<Ustanova> loginovanaUstanova = ustanovaRepository.findUstanovaByUsername(usernameLoginovaneUstanove);
        Ustanova ustanova = loginovanaUstanova.get();

        String putanjaLogo = ustanova.getSlika();
        String nazivUstanove = ustanova.getNaziv();
        String opisUstanove = ustanova.getOpis();
        String datum = String.valueOf(zahtevZaObilazak.getDatum());
        String vreme = String.valueOf(zahtevZaObilazak.getVreme());


        String from = "thegrouppodrska@gmail.com";
        String recipient = zahtevZaObilazak.getKorisnik().getEmail();
        String subject = "Odobren obilazak";
        String message = "Uspešno ste zakazali obilazak ustanove.";

        mailService.pripremiIPosaljiObilazak(from, recipient, subject, message, nazivUstanove, opisUstanove, datum, vreme, putanjaLogo);
        return "redirect:/ustanova/zahtevi";
    }

    @RequestMapping("/ustanova/slanje-mejla")
    public String ustanovaSlanjeMejla(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovaneUstanove = authentication.getName();
        Optional<Ustanova> loginovanaUstanova = ustanovaRepository.findUstanovaByUsername(usernameLoginovaneUstanove);
        Ustanova ustanova = loginovanaUstanova.get();
        model.addAttribute("korisnici", mailingListaRespository.getByUstanova(ustanova).getKorisnici());
        return "ustanova-slanje-mejla";
    }

    @PostMapping("/ustanova/posalji-mejl")
    public String ustanovaSlanjeMejlaSabskrajberima(@RequestParam(value="poruka") String poruka){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameLoginovaneUstanove = authentication.getName();
        Optional<Ustanova> loginovanaUstanova = ustanovaRepository.findUstanovaByUsername(usernameLoginovaneUstanove);
        Ustanova ustanova = loginovanaUstanova.get();

        String putanjaLogo = ustanova.getSlika();
        String nazivUstanove = ustanova.getNaziv();
        String opisUstanove = ustanova.getOpis();

        String from = "thegrouppodrska@gmail.com";
        String subject = "Promo poruka";
        String message = poruka;

        for (KorisnikMail korisnici:mailingListaRespository.getByUstanova(ustanova).getKorisnici()
        ) {

            String recipient = korisnici.getKorisnik().getEmail();
            mailService.pripremiIPosaljiSubscribe(from, recipient, subject, message, nazivUstanove, opisUstanove, putanjaLogo);

            System.out.println("from: " + from);
            System.out.println("recipient: " + recipient);
            System.out.println("subject: " + subject);
            System.out.println("message: " + message);
        }
        return "ustanova-slanje-mejla";
    }
}


