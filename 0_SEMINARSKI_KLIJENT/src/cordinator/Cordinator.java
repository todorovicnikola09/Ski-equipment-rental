/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cordinator;

import domen.StavkaIznajmljivanja;
import domen.Zaposleni;
import forme.FormaDodajIznajmljivanje;
import forme.FormaDodajMesto;
import forme.FormaDodajOsobu;
import forme.FormaDodajSkijaskuOpremu;
import forme.FormaDodajTerminDezurstva;
import forme.FormaDodajTerminZaposlenog;
import forme.FormaDodajZaposlenog;
import forme.FormaLogin;
import forme.FormaMod;
import forme.FormaPrikazMesta;
import forme.FormaPrikazOsoba;
import forme.FormaPrikazSkijaskeOpreme;
import forme.FormaPrikazStavkiIznajmljivanja;
import forme.FormaPrikazTerminaDezurstva;
import forme.FormaPrikazTerminaZaposlenog;
import forme.FormaPrikazZaposlenih;
import forme.FormaPrikaziIznajmljivanje;
import forme.GlavnaForma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kontroleri.DodajIznajmljivanjeController;
import kontroleri.DodajMestoController;
import kontroleri.DodajOsobuController;
import kontroleri.DodajSkijaskuOpremuController;
import kontroleri.DodajTerminDezurstvaController;
import kontroleri.DodajTerminZaposlenogController;
import kontroleri.DodajZaposlenogController;
import kontroleri.GlavnaFormaController;
import kontroleri.LoginController;
import kontroleri.PrikazIznajmljivanjaController;
import kontroleri.PrikazMestaController;
import kontroleri.PrikazOsobaController;
import kontroleri.PrikazSkijaskeOpremeController;
import kontroleri.PrikazStavkiIznajmljivanjaController;
import kontroleri.PrikazTerminaDezurstvaController;
import kontroleri.PrikazTerminaZaposlenogController;
import kontroleri.PrikazZaposlenihController;

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
    private PrikazStavkiIznajmljivanjaController prikazStavkiIznajmljivanjaController;
    
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

    public void otvoriPrikazStavkiIznajmljivanja(List<StavkaIznajmljivanja> lista) {
        prikazStavkiIznajmljivanjaController = new PrikazStavkiIznajmljivanjaController(new FormaPrikazStavkiIznajmljivanja());
        prikazStavkiIznajmljivanjaController.otvoriFormu(lista);
    }

    public void azurirajVrednosti() {
        dodajIznajmljivanjeController.osveziFormu();
    }

    public void azurirajStavku(StavkaIznajmljivanja so) {
        dodajIznajmljivanjeController.azurirajStavkuIznajmljivanja(so);
    }

   
    
}
