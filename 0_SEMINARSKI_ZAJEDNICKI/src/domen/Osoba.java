/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.*;
/**
 *
 * @author Nikola
 */
public class Osoba implements ApstraktniDomenskiObjekat{
    private int idOsoba;
    private String ime;
    private String prezime;
    private String telefon;
    private String email;
    private Mesto idMesto;
    
    public Osoba() {
    }

    public Osoba(int idOsoba, String ime, String prezime, String telefon, String email, Mesto idMesto) {
        this.idOsoba = idOsoba;
        this.ime = ime;
        this.prezime = prezime;
        this.telefon = telefon;
        this.email = email;
        this.idMesto = idMesto;
    }
    
    public int getIdOsoba() {
        return idOsoba;
    }

    public void setIdOsoba(int idOsoba) {
        this.idOsoba = idOsoba;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Mesto getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Mesto idMesto) {
        this.idMesto = idMesto;
    }

    


    @Override
    public String toString() {
        return ime + " " + prezime;
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
        final Osoba other = (Osoba) obj;
        if (this.idOsoba != other.idOsoba) {
            return false;
        }
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        if (!Objects.equals(this.telefon, other.telefon)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.idMesto, other.idMesto);
    }

    @Override
    public String vratiNazivTabele() {
        return "osoba";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime, prezime, telefon, email, idMesto";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + ime + "', '" + prezime + "', '" + telefon + "', '" + email + "', " + idMesto.getIdMesto();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "osoba.idOsoba=" + idOsoba;
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', telefon='" + telefon + "', email='" + email + "', idmesto=" + idMesto.getIdMesto();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            
            int IdOsoba = rs.getInt("osoba.idOsoba");
            String Ime = rs.getString("osoba.ime");
            String Prezime = rs.getString("osoba.prezime");
            String Telefon = rs.getString("osoba.telefon");
            String Email = rs.getString("osoba.email");
            int id = rs.getInt("osoba.idMesto");
            String nazivMesta = rs.getString("mesto.naziv");
            Mesto m = new Mesto(id, nazivMesta);
            Osoba o = new Osoba(IdOsoba, Ime, Prezime, Telefon, Email, m);
            lista.add(o);
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
