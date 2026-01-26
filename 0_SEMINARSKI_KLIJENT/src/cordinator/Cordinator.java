/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cordinator;

import domen.StavkaIznajmljivanja;
import domen.Zaposleni;
import forme.dodaj.FormaDodajIznajmljivanje;
import forme.dodaj.FormaDodajMesto;
import forme.dodaj.FormaDodajOsobu;
import forme.dodaj.FormaDodajSkijaskuOpremu;
import forme.dodaj.FormaDodajTerminDezurstva;
import forme.dodaj.FormaDodajTerminZaposlenog;
import forme.dodaj.FormaDodajZaposlenog;
import forme.FormaLogin;
import forme.FormaMod;
import forme.prikaz.FormaPrikazMesta;
import forme.prikaz.FormaPrikazOsoba;
import forme.prikaz.FormaPrikazSkijaskeOpreme;
import forme.prikaz.FormaPrikazTerminaDezurstva;
import forme.prikaz.FormaPrikazTerminaZaposlenog;
import forme.prikaz.FormaPrikazZaposlenih;
import forme.prikaz.FormaPrikaziIznajmljivanje;
import forme.GlavnaForma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kontroleri.iznajmljivanje.DodajIznajmljivanjeController;
import kontroleri.mesto.DodajMestoController;
import kontroleri.osoba.DodajOsobuController;
import kontroleri.skijaskaoprema.DodajSkijaskuOpremuController;
import kontroleri.termindezurstva.DodajTerminDezurstvaController;
import kontroleri.terminzaposlenog.DodajTerminZaposlenogController;
import kontroleri.zaposleni.DodajZaposlenogController;
import kontroleri.GlavnaFormaController;
import kontroleri.LoginController;
import kontroleri.iznajmljivanje.PrikazIznajmljivanjaController;
import kontroleri.mesto.PrikazMestaController;
import kontroleri.osoba.PrikazOsobaController;
import kontroleri.skijaskaoprema.PrikazSkijaskeOpremeController;
import kontroleri.termindezurstva.PrikazTerminaDezurstvaController;
import kontroleri.terminzaposlenog.PrikazTerminaZaposlenogController;
import kontroleri.zaposleni.PrikazZaposlenihController;

/**
 *
 * @author Nikola
 */
public class Cordinator {
    private static Cordinator instance;
    private LoginController loginController;
    private Zaposleni ulogovani;
    private GlavnaFormaController glavnaFormaController;
    private PrikazOsobaController prikazOsobaController;
    private DodajOsobuController dodajOsobuController;
    private Map<String,Object> parametri; 
    private PrikazIznajmljivanjaController prikazIznajmljivanjaController;
    private PrikazMestaController prikazMestaController;
    private DodajMestoController dodajMestoController;
    private DodajSkijaskuOpremuController dodajSkijaskuOpremuController;
    private PrikazSkijaskeOpremeController prikazSkijaskeOpremeController;
    private DodajTerminDezurstvaController dodajTerminDezurstvaController;
    private PrikazTerminaDezurstvaController prikazTerminaDezurstvaController;
    private DodajZaposlenogController dodajZaposlenogController;
    private PrikazZaposlenihController prikazZaposlenihController;
    private DodajTerminZaposlenogController dodajTerminZaposlenogController;
    private PrikazTerminaZaposlenogController prikazTerminaZaposlenogController;
    private DodajIznajmljivanjeController dodajIznajmljivanjeController;
    
    private Cordinator() {
        parametri = new HashMap<>();
    }

    public Zaposleni getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Zaposleni ulogovani) {
        this.ulogovani = ulogovani;
    }
    
    public static Cordinator getInstance(){
        if(instance==null){
            instance = new Cordinator();
        }
        return instance;
    }

    public void otvoriLoginFormu() {
        loginController = new LoginController(new FormaLogin());
        loginController.otvoriFormu();
    }
    
    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }

    public void otvoriPrikazOsoba() {
        prikazOsobaController = new PrikazOsobaController(new FormaPrikazOsoba());
        prikazOsobaController.otvoriFormu();
    }

    public void otvoriDodajOsobu() {
        dodajOsobuController = new DodajOsobuController(new FormaDodajOsobu());
        dodajOsobuController.otvoriFormu(FormaMod.DODAJ);
    }
    
    public void dodajParam(String s, Object o){
        parametri.put(s, o);
    }
    
    public Object vratiParam(String s){
        return parametri.get(s);
    }

    public void otvoriFormuIzmeniOsobu() {
        dodajOsobuController = new DodajOsobuController(new FormaDodajOsobu());
        dodajOsobuController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormu() {
        prikazOsobaController.osveziFormu();
    }
    
    public void otvoriPrikazIznajmljivanja() {
        prikazIznajmljivanjaController = new PrikazIznajmljivanjaController(new FormaPrikaziIznajmljivanje());
        prikazIznajmljivanjaController.otvoriFormu();
    }

    public void otvoriPretraziMesto() {
        prikazMestaController = new PrikazMestaController(new FormaPrikazMesta());
        prikazMestaController.otvoriFormu();
    }
    
    public void otvoriKreirajMesto() {
        dodajMestoController = new DodajMestoController(new FormaDodajMesto());
        dodajMestoController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriFormuIzmeniMesto() {
        dodajMestoController = new DodajMestoController(new FormaDodajMesto());
        dodajMestoController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormuMesta() {
        prikazMestaController.osveziFormu();
    }
    
    public void otvoriFormuDodajSkijaskuOpremu() {
        dodajSkijaskuOpremuController = new DodajSkijaskuOpremuController(new FormaDodajSkijaskuOpremu());
        dodajSkijaskuOpremuController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriFormuPregledSkijaskeOpreme() {
        prikazSkijaskeOpremeController = new PrikazSkijaskeOpremeController(new FormaPrikazSkijaskeOpreme());
        prikazSkijaskeOpremeController.otvoriFormu();
    }

    public void otvoriKreirajTerminDezurstva() {
        dodajTerminDezurstvaController = new DodajTerminDezurstvaController(new FormaDodajTerminDezurstva());
        dodajTerminDezurstvaController.otvoriFormu();
    }

    public void otvoriFormuIzmeniSkijaskuOpremu() {
        dodajSkijaskuOpremuController = new DodajSkijaskuOpremuController(new FormaDodajSkijaskuOpremu());
        dodajSkijaskuOpremuController.otvoriFormu(FormaMod.IZMENI);
    }
    
    public void osveziFormuSkijaskaOprema() {
        prikazSkijaskeOpremeController.osveziFormu();
    }

    public void otvoriPregledTermineDezurstava() {
        prikazTerminaDezurstvaController = new PrikazTerminaDezurstvaController(new FormaPrikazTerminaDezurstva());
        prikazTerminaDezurstvaController.otvoriFormu();
    }
    
    public void otvoriKreirajZaposlenog() {
        dodajZaposlenogController = new DodajZaposlenogController(new FormaDodajZaposlenog());
        dodajZaposlenogController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriPrikazZaposlenih() {
        prikazZaposlenihController = new PrikazZaposlenihController(new FormaPrikazZaposlenih());
        prikazZaposlenihController.otvoriFormu();
    }

    public void otvoriFormuIzmeniZaposlenog() {
        dodajZaposlenogController = new DodajZaposlenogController(new FormaDodajZaposlenog());
        dodajZaposlenogController.otvoriFormu(FormaMod.IZMENI);
    }
    
    public void osveziFormuZaposleni() {
        prikazZaposlenihController.osveziFormu();
    }

    public void otvoriKreirajTerminZaposlenog() {
        dodajTerminZaposlenogController = new DodajTerminZaposlenogController(new FormaDodajTerminZaposlenog());
        dodajTerminZaposlenogController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriPrikazTerminaZaposlenih() {
        prikazTerminaZaposlenogController = new PrikazTerminaZaposlenogController(new FormaPrikazTerminaZaposlenog());
        prikazTerminaZaposlenogController.otvoriFormu();
    }

    public void otvoriFormuIzmeniTerminZaposlenog() {
        dodajTerminZaposlenogController = new DodajTerminZaposlenogController(new FormaDodajTerminZaposlenog());
        dodajTerminZaposlenogController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormuTerminZaposlenog() {
        prikazTerminaZaposlenogController.osveziFormu();
    }

    public void otvoriKreirajIznajmljivanje() {
        dodajIznajmljivanjeController = new DodajIznajmljivanjeController(new FormaDodajIznajmljivanje());
        dodajIznajmljivanjeController.otvoriFormu(FormaMod.DODAJ);
    }


    public void azurirajVrednosti() {
        dodajIznajmljivanjeController.osveziFormu();
    }

    public void osveziFormuIznajmljivanje() {
        prikazIznajmljivanjaController.osveziFormu();
    }

    public void otvoriFormuIzmeniIznajmljivanje() {
        dodajIznajmljivanjeController = new DodajIznajmljivanjeController(new FormaDodajIznajmljivanje());
        dodajIznajmljivanjeController.otvoriFormu(FormaMod.IZMENI);
    }



    

   
    
}
