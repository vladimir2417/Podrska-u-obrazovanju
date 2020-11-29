package Podrska.u.obrazovanju.controllers;

import Podrska.u.obrazovanju.models.*;
import Podrska.u.obrazovanju.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private OglasRespository oglasRespository;
    @Autowired
    private TipKorisnikaRepository tipKorisnikaRepository;
    @Autowired
    private UstanovaRepository ustanovaRepository;
    @Autowired
    private MailingListaRespository mailingListaRespository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Podrska.u.obrazovanju.convertors.izUstanoveUFormu izUstanoveUFormu;
    @Autowired
    public void setIzUstanoveUFormu(Podrska.u.obrazovanju.convertors.izUstanoveUFormu izUstanoveUFormu) { this.izUstanoveUFormu = izUstanoveUFormu; }

    private Podrska.u.obrazovanju.convertors.izFormeuUstanovu izFormeuUstanovu;
    @Autowired
    public void setIzFormeuUstanovu(Podrska.u.obrazovanju.convertors.izFormeuUstanovu izFormeuUstanovu) { this.izFormeuUstanovu = izFormeuUstanovu; }

    private Podrska.u.obrazovanju.convertors.izOglasaUFormu izOglasaUFormu;
    @Autowired
    public void setIzOglasaUFormu(Podrska.u.obrazovanju.convertors.izOglasaUFormu izOglasaUFormu) { this.izOglasaUFormu = izOglasaUFormu; }

    private Podrska.u.obrazovanju.convertors.izFormeUOglas izFormeUOglas;
    @Autowired
    public void setIzFormeUOglas(Podrska.u.obrazovanju.convertors.izFormeUOglas izFormeUOglas) { this.izFormeUOglas = izFormeUOglas; }

    @GetMapping("/admin/pocetna")
    public String adminDeo(){
        return "admin-pocetna";
    }

    @RequestMapping("/admin/korisnici/lista")
    public String listaKorisnika(Model model){
        TipKorisnika uloga = tipKorisnikaRepository.findByUlogeID(1);
        model.addAttribute("korisnici", korisnikRepository.findAllByUloga(uloga));
        return "admin-korisnik-lista";
    }

    @RequestMapping("/admin/korisnici/obrisi/{korisnikID}")
    public String obrisiKorisnika(@PathVariable String korisnikID){
        korisnikRepository.deleteById(Long.valueOf(korisnikID));
        return "redirect:/admin/korisnici/lista";
    }

    @RequestMapping("/admin/oglasi/lista")
    public String adminListaOglasa(Model model){
        model.addAttribute("oglasi", oglasRespository.findAll());
        return "admin-oglasi-lista";
    }

    @RequestMapping("/admin/oglasi/izmeni/{oglasID}")
    public String adminIzmeniOglas(@PathVariable String oglasID, Model model){
        Oglas oglas = oglasRespository.getByOglasID(Long.valueOf(oglasID));
        OglasDto oglasDto = izOglasaUFormu.convert(oglas);
        model.addAttribute("oglasDto", oglasDto);
        return "admin-oglasi-izmeni";
    }

    @RequestMapping(value = "/admin/oglasi/sacuvaj-izmene", method = RequestMethod.POST)
    public String adminSacuvajIzmeneOglasa(@Valid OglasDto oglasDto, BindingResult bindingResult) {
        Oglas oglasNas = oglasRespository.getByOglasID(oglasDto.getOglasID());
        oglasDto.setSlika(oglasNas.getSlika());
        oglasRespository.save(izFormeUOglas.convert(oglasDto));
        return "redirect:/admin/oglasi/lista";
    }

    @RequestMapping("/admin/oglasi/obrisi/{oglasID}")
    public String adminObrisiOglas(@PathVariable String oglasID){
        oglasRespository.deleteById(Long.valueOf(oglasID));
        return "redirect:/admin/oglasi/lista";
    }

    @RequestMapping("/admin/ustanove/lista")
    public String listaUstanova(Model model){
        model.addAttribute("ustanove", ustanovaRepository.findAll());
        return "admin-ustanove-lista";
    }

    @RequestMapping("/admin/ustanove/dodaj")
    public String adminDodajUstanovu(Model model){
        model.addAttribute("ustanovaDto", new UstanovaDto());
        return "admin-ustanove-dodaj";
    }

    @RequestMapping("/admin/ustanove/izmeni/{ustanoveID}")
    public String adminIzmeniUstanovu(@PathVariable String ustanoveID, Model model){
        Ustanova ustanova = ustanovaRepository.getByUstanoveID(Long.valueOf(ustanoveID));
        UstanovaDto ustanovaDto = izUstanoveUFormu.convert(ustanova);
        model.addAttribute("ustanovaDto", ustanovaDto);
        return "admin-ustanove-izmeni";
    }

    @RequestMapping(value = "/admin/ustanove/sacuvaj", method = RequestMethod.POST)
    public String adminSacuvajUstanovu(@Valid UstanovaDto ustanovaDto, BindingResult bindingResult, RedirectAttributes redirectAttrs) throws IOException {

        String uploadDirectory = System.getProperty("user.dir") + "/src/main/uploads/static/slikeUstanova";
        MultipartFile slika = ustanovaDto.getSlikaMulti();
        Path imageNameAndPath = Paths.get(uploadDirectory, slika.getOriginalFilename());
        String putanja = "../uploads/static/slikeUstanova/" + slika.getOriginalFilename();
        Files.write(imageNameAndPath, slika.getBytes());
        ustanovaDto.setSlika(putanja);

        if(bindingResult.hasErrors()){
            return "admin-ustanove-dodaj";
        }
        Optional<Ustanova> probaUstanova = ustanovaRepository.findUstanovaByUsername(ustanovaDto.getUsername());
        Optional<TipKorisnika> uloge = tipKorisnikaRepository.findById(3);
        if(uloge.isPresent()){
            if(!probaUstanova.isPresent()) {
                ustanovaDto.setUloge(uloge.get());
                ustanovaDto.setPassword(passwordEncoder.encode(ustanovaDto.getPassword()));
                ustanovaRepository.save(izFormeuUstanovu.convert(ustanovaDto));

                MailingLista mailingLista = new MailingLista();
                mailingLista.setUstanova(ustanovaRepository.getByUsername(ustanovaDto.getUsername()));
                mailingListaRespository.save(mailingLista);
            }
            else {
                redirectAttrs.addAttribute("poruka", "Postoji registrovan korisnik sa ovim korisničkim imenom.");
                return "redirect:/admin/ustanove/dodaj";
            }
        }
        return "redirect:/admin/ustanove/lista";
    }

    @RequestMapping(value = "/admin/ustanove/sacuvaj-izmene", method = RequestMethod.POST)
    public String adminSacuvajIzmeneUstanove(@Valid UstanovaDto ustanovaDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "admin-ustanove-dodaj";
        }
        Optional<TipKorisnika> uloge = tipKorisnikaRepository.findById(3);
        if(uloge.isPresent()){
            Ustanova ustanovaNasa = ustanovaRepository.getByUstanoveID(ustanovaDto.getUstanoveID());
            ustanovaDto.setUloge(uloge.get());
            ustanovaDto.setSlika(ustanovaNasa.getSlika());
            ustanovaRepository.save(izFormeuUstanovu.convert(ustanovaDto));
        }
        return "redirect:/admin/ustanove/lista";
    }

    @RequestMapping("/admin/ustanove/obrisi/{ustanoveID}")
    public String adminObrisiUstanovu(@PathVariable String ustanoveID){
        ustanovaRepository.deleteById(Long.valueOf(ustanoveID));
        return "redirect:/admin/ustanove/lista";
    }
}
