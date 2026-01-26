/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.Zaposleni;
import forme.FormaLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class LoginController {

    private final FormaLogin fl;

    public LoginController(FormaLogin fl) {
        this.fl = fl;
        addActionListeners();
    }

    private void addActionListeners() {
        fl.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            private void prijava(ActionEvent e) {
                String ki = fl.getjTextFieldUsername().getText().trim();
                String loz = String.valueOf(fl.getjPasswordField1().getPassword());
                
                Komunikacija.getInstance().konekcija();
                
                if (!Komunikacija.getInstance().serverKonekcija()) {
                    JOptionPane.showMessageDialog(fl, "Niste povezani sa serverom.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                 
                Zaposleni ulogovani = Komunikacija.getInstance().login(ki, loz);

                if (ulogovani == null) {
                    JOptionPane.showMessageDialog(fl, "Korisnicko ime i sifra nisu ispravni!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fl, "Ne moze da se otvori glavna forma i meni!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    Cordinator.getInstance().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(fl, "Korisnicko ime i sifra su ispravni.", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().otvoriGlavnuFormu();
                    fl.dispose();
                }
            }

        });
    }

    public void otvoriFormu() {
        fl.setVisible(true);
    }

}
