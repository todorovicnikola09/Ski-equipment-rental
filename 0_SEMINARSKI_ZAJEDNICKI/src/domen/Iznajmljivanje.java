/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nikola
 */
public class Iznajmljivanje implements ApstraktniDomenskiObjekat {

    private int idIznajmljivanje;
    private LocalDate datumIznajmljivanja;
    private int ukupnoSati;
    private double ukupanIznos;
    private Zaposleni idZaposleni;
    private Osoba idOsoba;
    private String nacinPlacanja;
    private List<StavkaIznajmljivanja> lista;
    
    public Iznajmljivanje() {
    }
    
    public Iznajmljivanje(int idIznajmljivanje, LocalDate datumIznajmljivanja, int ukupnoSati, 
            double ukupanIznos, String nacinPlacanja, Zaposleni idZaposleni, Osoba idOsoba) {
        this.idIznajmljivanje = idIznajmljivanje;
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.ukupnoSati = ukupnoSati;
        this.ukupanIznos = ukupanIznos;
        this.nacinPlacanja = nacinPlacanja;
        this.idZaposleni = idZaposleni;
        this.idOsoba = idOsoba;
    }

    public Iznajmljivanje(LocalDate datumIznajmljivanja, int ukupnoSati, double ukupanIznos,String nacinPlacanja, Zaposleni idZaposleni, Osoba idOsoba) {
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.ukupnoSati = ukupnoSati;
        this.ukupanIznos = ukupanIznos;
        this.idZaposleni = idZaposleni;
        this.idOsoba = idOsoba;
        this.nacinPlacanja = nacinPlacanja;
    }

    public Iznajmljivanje(LocalDate datumIznajmljivanja, int ukupnoSati, double ukupanIznos, Zaposleni idZaposleni, Osoba idOsoba, String nacinPlacanja, List<StavkaIznajmljivanja> lista) {
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.ukupnoSati = ukupnoSati;
        this.ukupanIznos = ukupanIznos;
        this.idZaposleni = idZaposleni;
        this.idOsoba = idOsoba;
        this.nacinPlacanja = nacinPlacanja;
        this.lista = lista;
    }
    
    public Iznajmljivanje(int idIznajmljivanje, LocalDate datumIznajmljivanja, int ukupnoSati, double ukupanIznos, Zaposleni idZaposleni, Osoba idOsoba, String nacinPlacanja, List<StavkaIznajmljivanja> lista) {
        this.idIznajmljivanje = idIznajmljivanje;
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.ukupnoSati = ukupnoSati;
        this.ukupanIznos = ukupanIznos;
        this.idZaposleni = idZaposleni;
        this.idOsoba = idOsoba;
        this.nacinPlacanja = nacinPlacanja;
        this.lista = lista;
    }

    public int getIdIznajmljivanje() {
        return idIznajmljivanje;
    }

    public void setIdIznajmljivanje(int idIznajmljivanje) {
        this.idIznajmljivanje = idIznajmljivanje;
    }

    public LocalDate getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }

    public void setDatumIznajmljivanja(LocalDate datumIznajmljivanja) {
        this.datumIznajmljivanja = datumIznajmljivanja;
    }

    public int getUkupnoSati() {
        return ukupnoSati;
    }

    public void setUkupnoSati(int ukupnoSati) {
        this.ukupnoSati = ukupnoSati;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Zaposleni getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(Zaposleni idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    public Osoba getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(Osoba idOsoba) {
        this.idOsoba = idOsoba;
    }

    public String getNacinPlacanja() {
        return nacinPlacanja;
    }

    public void setNacinPlacanja(String nacinPlacanja) {
        this.nacinPlacanja = nacinPlacanja;
    }

    public List<StavkaIznajmljivanja> getLista() {
        return lista;
    }

    public void setLista(List<StavkaIznajmljivanja> lista) {
        this.lista = lista;
    }
    
    

    @Override
    public String toString() {
        return idIznajmljivanje + " " + datumIznajmljivanja + " " + ukupnoSati + " " + ukupanIznos + " "  + nacinPlacanja + " " + idZaposleni + " " + idOsoba;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Iznajmljivanje other = (Iznajmljivanje) obj;
        if (this.ukupnoSati != other.ukupnoSati) {
            return false;
        }
        if (Double.doubleToLongBits(this.ukupanIznos) != Double.doubleToLongBits(other.ukupanIznos)) {
            return false;
        }
        if (!Objects.equals(this.nacinPlacanja, other.nacinPlacanja)) {
            return false;
        }
        if (!Objects.equals(this.datumIznajmljivanja, other.datumIznajmljivanja)) {
            return false;
        }
        if (!Objects.equals(this.idZaposleni, other.idZaposleni)) {
            return false;
        }
        return Objects.equals(this.idOsoba, other.idOsoba);
    }

   

    @Override
    public String vratiNazivTabele() {
        return "iznajmljivanje";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datumIznajmljivanja, ukupnoSati, ukupanIznos, nacinPlacanja, idZaposleni, idOsoba";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + datumIznajmljivanja + "', " + ukupnoSati + ", " + ukupanIznos + ", '" + nacinPlacanja + "', " + idZaposleni.getIdZaposleni() + ", " + idOsoba.getIdOsoba();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "iznajmljivanje.idIznajmljivanje=" + idIznajmljivanje;
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "datumIznajmljivanja='" + datumIznajmljivanja + "', ukupnoSati=" + ukupnoSati + ", ukupanIznos=" + ukupanIznos + ", nacinPlacanja='" + nacinPlacanja + "', idZaposleni=" + idZaposleni.getIdZaposleni() + ", idOsoba=" + idOsoba.getIdOsoba();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            int IdIznajmljivanje = rs.getInt("iznajmljivanje.idIznajmljivanje");
            LocalDate DatumIznajmljivanja = rs.getDate("iznajmljivanje.datumIznajmljivanja").toLocalDate();
            int UkupnoSati = rs.getInt("iznajmljivanje.ukupnoSati");
            double UkupanIznos = rs.getDouble("iznajmljivanje.ukupanIznos");
            String NacinPlacanja = rs.getString("iznajmljivanje.nacinPlacanja");
            int idzaposleni = rs.getInt("iznajmljivanje.idZaposleni");
            String imeZap = rs.getString("zaposleni.ime");
            String prezimeZap = rs.getString("zaposleni.prezime");
            String emailZap = rs.getString("zaposleni.email");
            String userZap = rs.getString("zaposleni.username");
            String passZap = rs.getString("zaposleni.password");
            int idosoba = rs.getInt("iznajmljivanje.idOsoba");
            String imeOsobe = rs.getString("osoba.ime");
            String prezimeOsobe = rs.getString("osoba.prezime");
            String telefonOsobe = rs.getString("osoba.telefon");
            String emailOsobe = rs.getString("osoba.email");
            int mestoOsobe = rs.getInt("osoba.idMesto");
            
            
            Mesto m = new Mesto(mestoOsobe, null);
            Zaposleni z = new Zaposleni(idzaposleni, imeZap, prezimeZap, emailZap, userZap, passZap);
            Osoba o = new Osoba(idosoba, imeOsobe, prezimeOsobe, telefonOsobe, emailOsobe, m);
            
            Iznajmljivanje i = new Iznajmljivanje(IdIznajmljivanje, DatumIznajmljivanja, UkupnoSati, UkupanIznos, NacinPlacanja, z, o);
            lista.add(i);
                    
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
