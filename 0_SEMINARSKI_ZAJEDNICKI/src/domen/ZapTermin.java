/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nikola
 */
public class ZapTermin implements ApstraktniDomenskiObjekat {

    private Zaposleni idZaposleni;
    private TerminDezurstva idTerminDezurstva;
    private LocalDate datumRada;

    public ZapTermin() {
    }

    public ZapTermin(Zaposleni idZaposleni, TerminDezurstva idTerminDezurstva, LocalDate datumRada) {
        this.idZaposleni = idZaposleni;
        this.idTerminDezurstva = idTerminDezurstva;
        this.datumRada = datumRada;
    }

    public Zaposleni getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(Zaposleni idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    public TerminDezurstva getIdTerminDezurstva() {
        return idTerminDezurstva;
    }

    public void setIdTerminDezurstva(TerminDezurstva idTerminDezurstva) {
        this.idTerminDezurstva = idTerminDezurstva;
    }

    public LocalDate getDatumRada() {
        return datumRada;
    }

    public void setDatumRada(LocalDate datumRada) {
        this.datumRada = datumRada;
    }

    @Override
    public String toString() {
        return idZaposleni + " " + idTerminDezurstva + " " + datumRada;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ZapTermin other = (ZapTermin) obj;
        if (!Objects.equals(this.idZaposleni, other.idZaposleni)) {
            return false;
        }
        if (!Objects.equals(this.idTerminDezurstva, other.idTerminDezurstva)) {
            return false;
        }
        return Objects.equals(this.datumRada, other.datumRada);
    }

    @Override
    public String vratiNazivTabele() {
        return "zap_termin";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idZaposleni, idTerminDezurstva, datumRada";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + idZaposleni.getIdZaposleni() + "', '" + idTerminDezurstva.getIdTerminDezurstva() + "', '" + datumRada + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zap_termin.idZaposleni = " + idZaposleni.getIdZaposleni()
         + " AND zap_termin.idTerminDezurstva = " + idTerminDezurstva.getIdTerminDezurstva()
         + " AND zap_termin.datumRada = '" + datumRada.toString() + "'";
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "idZaposleni='" + idZaposleni.getIdZaposleni() + "', idTerminDezurstva='" + idTerminDezurstva.getIdTerminDezurstva() + "', datumRada='" + datumRada + "'";
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
            int IdTerminDezurstva = rs.getInt("termin_dezurstva.idTerminDezurstva");
            String TipTermina = rs.getString("termin_dezurstva.tipTermina");
            LocalTime VremeOd = rs.getTime("termin_dezurstva.vremeOd").toLocalTime();
            LocalTime VremeDo = rs.getTime("termin_dezurstva.vremeDo").toLocalTime();
            LocalDate DatumRada = rs.getDate("zap_termin.datumRada").toLocalDate();
            
            Zaposleni z = new Zaposleni(IdZaposleni, Ime, Prezime, Email, Username, Password);
            TerminDezurstva td = new TerminDezurstva(IdTerminDezurstva, TipTermina, VremeOd, VremeDo);
            
            ZapTermin zt = new ZapTermin(z, td, DatumRada);
            lista.add(zt);
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
