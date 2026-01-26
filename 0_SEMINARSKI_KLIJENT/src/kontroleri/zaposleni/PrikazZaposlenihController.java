/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.zaposleni;

import domen.Zaposleni;
import forme.FormaMod;
import forme.prikaz.FormaPrikazZaposlenih;
import forme.modeltabele.ModelTabeleZaposleni;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazZaposlenihController {

    private final FormaPrikazZaposlenih pz;

    public PrikazZaposlenihController(FormaPrikazZaposlenih pz) {
        this.pz = pz;
        addActionListener();
    }

    public void pripremiFormu() {
        List<Zaposleni> osobe = komunikacija.Komunikacija.getInstance().ucitajZaposlene();
        ModelTabeleZaposleni mtz = new ModelTabeleZaposleni(osobe);
        pz.getjTableZaposleni().setModel(mtz);
    }

    public void otvoriFormu() {
        pripremiFormu();
        pz.setVisible(true);
        pz.getjButtonAzuriraj().setEnabled(false);
    }

    private void addActionListener() {
        pz.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pz.getjTableZaposleni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pz, "Sistem ne moze da nadje zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleZaposleni mtz = (ModelTabeleZaposleni) pz.getjTableZaposleni().getModel();
                    Zaposleni z = mtz.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Zaposleni", z);
                    JOptionPane.showMessageDialog(pz, "Sistem je nasao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniZaposlenog();
                }

            }

        });

        pz.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pz.getjTextFieldIme().getText().trim();
                String prezime = pz.getjTextFieldPrezime().getText().trim();
                String email = pz.getjTextFieldEmail().getText().trim();
                String username = pz.getjTextFieldUsername().getText().trim();

                ModelTabeleZaposleni mtz = (ModelTabeleZaposleni) pz.getjTableZaposleni().getModel();
                mtz.pretrazi(ime, prezime, email, username);
                if (mtz.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(pz, "Sistem ne moze da nadje zaposlene po zadatim kriterijumima!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pz, "Sistem je našao zaposlene po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    pz.getjButtonAzuriraj().setEnabled(true);
                }
            }
        });

        pz.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pz.getjTextFieldIme().setText("");
                pz.getjTextFieldPrezime().setText("");
                pz.getjTextFieldEmail().setText("");
                pz.getjTextFieldUsername().setText("");
                pz.getjButtonAzuriraj().setEnabled(false);
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
        pz.getjButtonAzuriraj().setEnabled(false);
        pz.getjTextFieldIme().setText("");
        pz.getjTextFieldPrezime().setText("");
        pz.getjTextFieldEmail().setText("");
        pz.getjTextFieldUsername().setText("");
    }

}
