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
public class StavkaIznajmljivanja implements ApstraktniDomenskiObjekat{
    private int rb;
    private Iznajmljivanje idIznajmljivanje;
    private int brojSati;
    private double cena;
    private double satCena;
    private LocalDate datumPovratkaOpreme;
    private SkijaskaOprema idSkiOprema;
     
    
    public StavkaIznajmljivanja() {
    }

    public StavkaIznajmljivanja(int rb, Iznajmljivanje idIznajmljivanje, int brojSati, double cena, 
            double satCena, LocalDate datumPovratkaOpreme, SkijaskaOprema idSkiOprema) {
        this.rb = rb;
        this.idIznajmljivanje = idIznajmljivanje;
        this.brojSati = brojSati;
        this.cena = cena;
        this.satCena = satCena;
        this.datumPovratkaOpreme = datumPovratkaOpreme;
        this.idSkiOprema = idSkiOprema;
    }

    public StavkaIznajmljivanja( Iznajmljivanje idIznajmljivanje, int brojSati, double cena, double satCena, LocalDate datumPovratkaOpreme, SkijaskaOprema idSkiOprema) {
        
        this.idIznajmljivanje = idIznajmljivanje;
        this.brojSati = brojSati;
        this.cena = cena;
        this.satCena = satCena;
        this.datumPovratkaOpreme = datumPovratkaOpreme;
        this.idSkiOprema = idSkiOprema;
    }
    

    public Iznajmljivanje getIdIznajmljivanje() {
        return idIznajmljivanje;
    }

    public void setIdIznajmljivanje(Iznajmljivanje idIznajmljivanje) {
        this.idIznajmljivanje = idIznajmljivanje;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public LocalDate getDatumPovratkaOpreme() {
        return datumPovratkaOpreme;
    }

    public void setDatumPovratkaOpreme(LocalDate datumPovratkaOpreme) {
        this.datumPovratkaOpreme = datumPovratkaOpreme;
    }

    public SkijaskaOprema getIdSkiOprema() {
        return idSkiOprema;
    }

    public void setIdSkiOprema(SkijaskaOprema idSkiOprema) {
        this.idSkiOprema = idSkiOprema;
    }

    public int getBrojSati() {
        return brojSati;
    }

    public void setBrojSati(int brojSati) {
        this.brojSati = brojSati;
    }

    public double getSatCena() {
        return satCena;
    }

    public void setSatCena(double satCena) {
        this.satCena = satCena;
    }
    
    

    @Override
    public String toString() {
        return rb + " " + idIznajmljivanje.getIdIznajmljivanje() + " " + brojSati + " " + cena + " " + satCena + " " + datumPovratkaOpreme + " " + idSkiOprema.getIdSkiOprema();
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
        final StavkaIznajmljivanja other = (StavkaIznajmljivanja) obj;
        if (this.rb != other.rb) {
            return false;
        }
        if (this.brojSati != other.brojSati) {
            return false;
        }
        if (Double.doubleToLongBits(this.cena) != Double.doubleToLongBits(other.cena)) {
            return false;
        }
        if (Double.doubleToLongBits(this.satCena) != Double.doubleToLongBits(other.satCena)) {
            return false;
        }
        if (!Objects.equals(this.idIznajmljivanje, other.idIznajmljivanje)) {
            return false;
        }
        if (!Objects.equals(this.datumPovratkaOpreme, other.datumPovratkaOpreme)) {
            return false;
        }
        return Objects.equals(this.idSkiOprema, other.idSkiOprema);
    }
    
    

    @Override
    public String vratiNazivTabele() {
        return "stavka_iznajmljivanja";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "rb, idIznajmljivanje, brojSati, cena, satCena, datumPovratkaOpreme, idSkiOprema";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return rb + ", " + idIznajmljivanje.getIdIznajmljivanje() + ", " +  brojSati + ", " + cena + ", " + satCena + ", '" + datumPovratkaOpreme + "', " + idSkiOprema.getIdSkiOprema();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "stavka_iznajmljivanja.rb=" + rb + " AND stavka_iznajmljivanja.idIznajmljivanje=" + idIznajmljivanje.getIdIznajmljivanje();
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "brojSati=" + brojSati + ", cena=" + cena + ", satCena=" + satCena + ", datumPovratkaOpreme='" + datumPovratkaOpreme + "'" + ", idSkiOprema=" + idSkiOprema.getIdSkiOprema();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            int Rb = rs.getInt("stavka_iznajmljivanja.rb");
            int IdIznajmljivanje = rs.getInt("stavka_iznajmljivanja.idIznajmljivanje");
//            LocalDate DatumIznajmljivanja = rs.getDate("iznajmljivanje.datumIznajmljivanja").toLocalDate();
//            int UkupnoSati = rs.getInt("iznajmljivanje.ukupnoSati");
//            double UkupanIznos = rs.getDouble("iznajmljivanje.ukupanIznos");
//            String NacinPlacanja = rs.getString("iznajmljivanje.nacinPlacanja");
//            int idzaposleni = rs.getInt("iznajmljivanje.idZaposleni");
//            String imeZap = rs.getString("zaposleni.ime");
//            String prezimeZap = rs.getString("zaposleni.prezime");
//            String emailZap = rs.getString("zaposleni.email");
//            String userZap = rs.getString("zaposleni.username");
//            String passZap = rs.getString("zaposleni.password");
//            int idosoba = rs.getInt("iznajmljivanje.idOsoba");
//            String imeOsobe = rs.getString("osoba.ime");
//            String prezimeOsobe = rs.getString("osoba.prezime");
//            String telefonOsobe = rs.getString("osoba.telefon");
//            String emailOsobe = rs.getString("osoba.email");
//            int mestoOsobe = rs.getInt("osoba.idMesto");
            
//            Mesto m = new Mesto(mestoOsobe, null);
//            Osoba o = new Osoba(idosoba, imeOsobe, prezimeOsobe, telefonOsobe, emailOsobe, m);
//            Zaposleni z = new Zaposleni(idzaposleni, imeZap, prezimeZap, emailZap, userZap, passZap);
            Iznajmljivanje i = new Iznajmljivanje();
            i.setIdIznajmljivanje(IdIznajmljivanje);
            
            
            int BrojSati = rs.getInt("stavka_iznajmljivanja.brojSati");
            double Cena = rs.getDouble("stavka_iznajmljivanja.cena");
            double SatCena = rs.getDouble("stavka_iznajmljivanja.satCena");
            
            int IdSkiOprema = rs.getInt("stavka_iznajmljivanja.idSkiOprema");
            String Naziv = rs.getString("skijaska_oprema.naziv");
            double SatCena1 = rs.getDouble("skijaska_oprema.satCena");
            String TipOpreme = rs.getString("skijaska_oprema.tipOpreme");
            
            SkijaskaOprema so = new SkijaskaOprema(IdSkiOprema, Naziv, SatCena1, TipOpreme);
            
            LocalDate DatumPovratkaOpreme = rs.getDate("datumPovratkaOpreme").toLocalDate();
            
            StavkaIznajmljivanja si = new StavkaIznajmljivanja(Rb, i, BrojSati, Cena, SatCena, DatumPovratkaOpreme, so);
            lista.add(si);
                    
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
