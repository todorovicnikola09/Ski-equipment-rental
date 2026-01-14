/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nikola
 */
public class Zaposleni implements ApstraktniDomenskiObjekat {

    private int idZaposleni;
    private String ime;
    private String prezime;
    private String email;
    private String username;
    private String password;

    public Zaposleni() {
    }

    public Zaposleni(int idZaposleni, String ime, String prezime, String email, String username, String password) {
        this.idZaposleni = idZaposleni;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(int idZaposleni) {
        this.idZaposleni = idZaposleni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
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
        final Zaposleni other = (Zaposleni) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }

    
    
    @Override
    public String vratiNazivTabele() {
        return "zaposleni";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime, prezime, email, username, password";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + ime + "', '" + prezime + "', '" + email + "', '" + username + "', '" + password + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zaposleni.idZaposleni=" + idZaposleni;
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "ime='" + ime + "', prezime='" + prezime + "', email='" + email + "', username='" + username + "', password='" + password + "'";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
       List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            int IdZaposleni = rs.getInt("zaposleni.idZaposleni");
            String Ime = rs.getString("zaposleni.ime");
            String Prezime = rs.getString("zaposleni.prezime");
            String Email = rs.getString("zaposleni.email");
            String Username = rs.getString("zaposleni.username");
            String Password = rs.getString("zaposleni.password");
            
            Zaposleni z = new Zaposleni(IdZaposleni, Ime, Prezime, Email, Username, Password);
            lista.add(z);
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
