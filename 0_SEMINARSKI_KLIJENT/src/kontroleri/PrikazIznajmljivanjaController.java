/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Iznajmljivanje;
import domen.Mesto;
import domen.Osoba;
import forme.FormaPrikazOsoba;
import forme.FormaPrikaziIznajmljivanje;
import forme.ModelTabeleIznajmljivanja;
import forme.ModelTabeleOsobe;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikola
 */
public class PrikazIznajmljivanjaController {
    private final FormaPrikaziIznajmljivanje pic;

    public PrikazIznajmljivanjaController(FormaPrikaziIznajmljivanje pic) {
        this.pic = pic;
        
    }

    public void otvoriFormu() {
        pripremiFormu();
        pic.setVisible(true);
    }

    public void pripremiFormu() {
        List<Iznajmljivanje> iznajmljivanje = komunikacija.Komunikacija.getInstance().ucitajIznajmljivanja();
        ModelTabeleIznajmljivanja mti = new ModelTabeleIznajmljivanja(iznajmljivanje);
        pic.getjTableIznajmljivanja().setModel(mti);
    }

    
}
