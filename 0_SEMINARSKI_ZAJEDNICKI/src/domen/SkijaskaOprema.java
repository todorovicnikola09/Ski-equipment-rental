/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nikola
 */
public class SkijaskaOprema implements ApstraktniDomenskiObjekat{
    private int idSkiOprema;
    private String naziv;
    private double satCena;
    private String tipOpreme;

    public SkijaskaOprema() {
    }

    public SkijaskaOprema(int idSkiOprema, String naziv, double satCena, String tipOpreme) {
        this.idSkiOprema = idSkiOprema;
        this.naziv = naziv;
        this.satCena = satCena;
        this.tipOpreme = tipOpreme;
    }

    public int getIdSkiOprema() {
        return idSkiOprema;
    }

    public void setIdSkiOprema(int idSkiOprema) {
        this.idSkiOprema = idSkiOprema;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public double getSatCena() {
        return satCena;
    }

    public void setSatCena(double minutCena) {
        this.satCena = minutCena;
    }

    public String getTipOprema() {
        return tipOpreme;
    }

    public void setTipOprema(String tipOpreme) {
        this.tipOpreme = tipOpreme;
    }

    @Override
    public String toString() {
        return idSkiOprema + " " + naziv + " " + satCena + " " + tipOpreme;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final SkijaskaOprema other = (SkijaskaOprema) obj;
        if (this.idSkiOprema != other.idSkiOprema) {
            return false;
        }
        if (Double.doubleToLongBits(this.satCena) != Double.doubleToLongBits(other.satCena)) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        return Objects.equals(this.tipOpreme, other.tipOpreme);
    }

    @Override
    public String vratiNazivTabele() {
        return "skijaska_oprema";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv, satCena, tipOpreme";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + naziv + "', " + satCena + ", '" + tipOpreme + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "skijaska_oprema.idSkiOprema=" + idSkiOprema;
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "naziv='" + naziv + "', satCena=" + satCena + ", tipOpreme='" + tipOpreme + "'";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            int IdSkiOprema = rs.getInt("idSkiOprema");
            String Naziv = rs.getString("naziv");
            double SatCena = rs.getDouble("satCena");
            String TipOpreme = rs.getString("tipOpreme");
            
            SkijaskaOprema so = new SkijaskaOprema(IdSkiOprema, Naziv, SatCena, TipOpreme);
            lista.add(so);
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
