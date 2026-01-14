/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import domen.Iznajmljivanje;
import domen.Mesto;
import domen.Osoba;
import domen.SkijaskaOprema;
import domen.StavkaIznajmljivanja;
import domen.TerminDezurstva;
import domen.ZapTermin;
import domen.Zaposleni;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikola
 */
public class Komunikacija {

    private Socket socket;
    private static Komunikacija instance;
    private Posiljalac posiljalac;
    private Primalac primalac;
    boolean serverKonekcija;
    private boolean isServerConnected;

    private Komunikacija() {
        isServerConnected = false;
    }

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    public void zatvoriKonekciju() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                isServerConnected = false;
                System.out.println("Konekcija sa serverom zatvorena.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetujKomunikaciju() {
        if (instance != null) {
            instance.zatvoriKonekciju();
            instance = null;
        }
    }

    public boolean isServerConnected() {
        return isServerConnected;
    }

    public boolean serverKonekcija() {
        return serverKonekcija;
    }

    public void konekcija() {
        try {
            socket = new Socket("localhost", 9000);
            posiljalac = new Posiljalac(socket);
            primalac = new Primalac(socket);
            serverKonekcija = true;
            System.out.println("Uspostavljena konekcija sa serverom.");
        } catch (IOException ex) {
            serverKonekcija = false;
            System.out.println("Server nije povezan.");
        }
    }
    
    public Zaposleni login(String ki, String loz) {
        if (!serverKonekcija) {
            return null;
        } else {
            Zaposleni z = new Zaposleni();
            z.setUsername(ki);
            z.setPassword(loz);

            Zahtev zahtev = new Zahtev(Operacija.LOGIN, z);
            posiljalac.posalji(zahtev);

            Odgovor odg = (Odgovor) primalac.primi();
            z = (Zaposleni) odg.getOdgovor();
            return z;
        }
    }

    public List<Osoba> ucitajOsobe() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_OSOBE, null);
        List<Osoba> osobe = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        osobe = (List<Osoba>) odg.getOdgovor();

        return osobe;
    }

    public void obrisiOsobu(Osoba o) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_OSOBU, o);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<Mesto> ucitajMesta() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_MESTA, null);
        List<Mesto> lista = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        lista = (List<Mesto>) odg.getOdgovor();
        return lista;
    }

    public void dodajOsobu(Osoba o) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_OSOBU, o);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void azurirajOsobu(Osoba o) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.AZURIRAJ_OSOBU, o);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
            cordinator.Cordinator.getInstance().osveziFormu();
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<Iznajmljivanje> ucitajIznajmljivanja() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_IZNAJMLJIVANJA, null);
        List<Iznajmljivanje> iznajmljivanja = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        iznajmljivanja = (List<Iznajmljivanje>) odg.getOdgovor();

        return iznajmljivanja;
    }

    public void dodajMesto(Mesto m) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_MESTO, m);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void azurirajMesto(Mesto m) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.AZURIRAJ_MESTO, m);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
            cordinator.Cordinator.getInstance().osveziFormuMesta();
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void obrisiMesto(Mesto m) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_MESTO, m);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void dodajSkijaskuOpremu(SkijaskaOprema so) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_SKIJASKU_OPREMU, so);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<SkijaskaOprema> ucitajSkijaskuOpremu() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_SKIJASKU_OPREMU, null);
        List<SkijaskaOprema> skijaskaOprema = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        skijaskaOprema = (List<SkijaskaOprema>) odg.getOdgovor();

        return skijaskaOprema;
    }

    public void obrisiSkijaskuOpremu(SkijaskaOprema so) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_SKIJASKU_OPREMU, so);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void azurirajSkijaskuOpremu(SkijaskaOprema so) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.AZURIRAJ_SKIJASKU_OPREMU, so);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
            cordinator.Cordinator.getInstance().osveziFormuSkijaskaOprema();
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<TerminDezurstva> ucitajTermineDezurstava() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_TERMINE, null);
        List<TerminDezurstva> terminDezurstva = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        terminDezurstva = (List<TerminDezurstva>) odg.getOdgovor();

        return terminDezurstva;
    }

    public void dodajTerminDezurstva(TerminDezurstva td) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_TERMIN, td);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public boolean postojiTermin(LocalTime vremeOd, LocalTime vremeDo) {
        List<TerminDezurstva> termini = ucitajTermineDezurstava();

        for (TerminDezurstva termin : termini) {
            if (termin.getVremeOd().equals(vremeOd) && termin.getVremeDo().equals(vremeDo)) {
                return true;
            }
        }

        return false;
    }

    public void obrisiTerminDezurstva(TerminDezurstva td) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_TERMIN_DEZURSTVA, td);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void dodajZaposlenog(Zaposleni z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_ZAPOSLENOG, z);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<Zaposleni> ucitajZaposlene() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_ZAPOSLENE, null);
        List<Zaposleni> zaposleni = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        zaposleni = (List<Zaposleni>) odg.getOdgovor();

        return zaposleni;
    }

    public void azurirajZaposlenog(Zaposleni z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.AZURIRAJ_ZAPOSLENOG, z);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
            cordinator.Cordinator.getInstance().osveziFormuZaposleni();
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void obrisiZaposlenog(Zaposleni z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_ZAPOSLENOG, z);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void dodajTerminZaposlenog(ZapTermin zt) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_TERMIN_ZAPOSLENOG, zt);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<ZapTermin> ucitajTermineZaposlenih() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_TERMINE_ZAPOSLENIH, null);
        List<ZapTermin> termini = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        termini = (List<ZapTermin>) odg.getOdgovor();

        return termini;
    }

    public void obrisiTerminZaposlenog(ZapTermin zt) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_TERMIN_ZAPOSLENOG, zt);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void azurirajTerminZaposlenog(ZapTermin zt,ZapTermin podaci) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.AZURIRAJ_TERMIN_ZAPOSLENOG, zt, podaci);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
            cordinator.Cordinator.getInstance().osveziFormuTerminZaposlenog();
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void dodajStavkuIznajmljivanja(StavkaIznajmljivanja si) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_STAVKU_IZNAJMLJIVANJA, si);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void dodajIznajmljivanje(Iznajmljivanje i) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_IZNAJMLJIVANJE, i);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void obrisiIznajmljivanje(Iznajmljivanje i1) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_IZNAJMLJIVANJE, i1);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<StavkaIznajmljivanja> ucitajStavkeIznajmljivanja() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_STAVKE_IZNAJMLJIVANJA, null);
        List<StavkaIznajmljivanja> stavkeIznajmljivanja = new ArrayList<>();
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        stavkeIznajmljivanja = (List<StavkaIznajmljivanja>) odg.getOdgovor();

        return stavkeIznajmljivanja;
    }

    public void obrisiStavkeIznajmljivanja(StavkaIznajmljivanja si) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_STAVKU_IZNAJMLJIVANJA, si);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        if (odg.getOdgovor() == null) {
            System.out.println("Uspeh");
        } else {
            System.out.println("Greska");
            ((Exception) odg.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public int ucitajPoslednjeIznajmljivanje() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_POSLEDNJE_IZNAJMLJIVANJE, null);
        posiljalac.posalji(zahtev);
        Odgovor odg = (Odgovor) primalac.primi();
        int id = (int) odg.getOdgovor();

        return id;
    }

    

    

   

    

   

}
