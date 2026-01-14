/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nikola
 */
public class TerminDezurstva implements ApstraktniDomenskiObjekat {

    private int idTerminDezurstva;
    private String tipTermina;
    private LocalTime vremeOd;
    private LocalTime vremeDo;
    
    public TerminDezurstva() {
    }

    public TerminDezurstva(int idTerminDezurstva, String tipTermina, LocalTime vremeOd, LocalTime vremeDo) {
        this.idTerminDezurstva = idTerminDezurstva;
        this.tipTermina = tipTermina;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }

    public int getIdTerminDezurstva() {
        return idTerminDezurstva;
    }

    public void setIdTerminDezurstva(int idTerminDezurstva) {
        this.idTerminDezurstva = idTerminDezurstva;
    }

    public LocalTime getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(LocalTime vremeOd) {
        this.vremeOd = vremeOd;
    }

    public LocalTime getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(LocalTime vremeDo) {
        this.vremeDo = vremeDo;
    }

    public String getTipTermina() {
        return tipTermina;
    }

    public void setTipTermina(String tipTermina) {
        this.tipTermina = tipTermina;
    }
    
    
    
    @Override
    public String toString() {
        return idTerminDezurstva + " " + tipTermina +" " + vremeOd + " " + vremeDo + " ";
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
        final TerminDezurstva other = (TerminDezurstva) obj;
        if (this.idTerminDezurstva != other.idTerminDezurstva) {
            return false;
        }
        if (!Objects.equals(this.tipTermina, other.tipTermina)) {
            return false;
        }
        if (!Objects.equals(this.vremeOd, other.vremeOd)) {
            return false;
        }
        return Objects.equals(this.vremeDo, other.vremeDo);
    }

    

    @Override
    public String vratiNazivTabele() {
        return "termin_dezurstva";
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "tipTermina, vremeOd, vremeDo";
    }

    @Override
    public String vratiVrednostZaUbacivanje() {
        return "'" + tipTermina + "', '" + vremeOd + "', '" + vremeDo + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "termin_dezurstva.idTerminDezurstva=" + idTerminDezurstva;
    }

    @Override
    public String vratiVrednostZaIzmenu() {
        return "tipTermina='" + tipTermina + "vremeOd='" + vremeOd + "', vremeDo='" + vremeDo + "'";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        while(rs.next()){
            int IdTerminDezurstva = rs.getInt("idTerminDezurstva");
            String TipTermina = rs.getString("tipTermina");
            LocalTime VremeOd = rs.getTime("vremeOd").toLocalTime();
            LocalTime VremeDo = rs.getTime("vremeDo").toLocalTime();
            
            TerminDezurstva td = new TerminDezurstva(IdTerminDezurstva, TipTermina, VremeOd, VremeDo);
            lista.add(td);
        }
        
        return lista;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
