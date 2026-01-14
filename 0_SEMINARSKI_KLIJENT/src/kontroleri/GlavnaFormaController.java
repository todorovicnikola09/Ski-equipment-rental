/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.Zaposleni;
import forme.FormaLogin;
import forme.GlavnaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikola
 */
public class GlavnaFormaController {

    private GlavnaForma gf;

    public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }

    private void addActionListeners() {
        gf.getjMenuItemOdjaviSe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int odgovor = JOptionPane.showConfirmDialog(gf, "Da li ste sigurni da želite da se odjavite?", "Potvrda odjave", JOptionPane.YES_NO_OPTION);
                if (odgovor == JOptionPane.YES_OPTION) {
                    cordinator.Cordinator.getInstance().setUlogovani(null);
                    komunikacija.Komunikacija.getInstance().zatvoriKonekciju();
                    komunikacija.Komunikacija.getInstance().resetujKomunikaciju();
                    gf.dispose();

                    FormaLogin loginForma = new FormaLogin();
                    new LoginController(loginForma).otvoriFormu();
                }
            }
        });
    }

    public void otvoriFormu() {
        Zaposleni ulogovani = Cordinator.getInstance().getUlogovani();
        String imePrezime = ulogovani.getIme() + " " + ulogovani.getPrezime();
        gf.setVisible(true);
        gf.getjLabelNaziv().setText(imePrezime);
    }
}
