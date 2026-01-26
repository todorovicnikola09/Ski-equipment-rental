/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.osoba;

import domen.Mesto;
import domen.Osoba;
import forme.prikaz.FormaPrikazOsoba;
import forme.modeltabele.ModelTabeleOsobe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazOsobaController {

    private final FormaPrikazOsoba po;

    public PrikazOsobaController(FormaPrikazOsoba po) {
        this.po = po;
        po.getjButtonAzuriraj().setEnabled(false);
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pripremiFormu2();
        po.setVisible(true);
    }

    public void pripremiFormu() {
        List<Osoba> osobe = komunikacija.Komunikacija.getInstance().ucitajOsobe();
        ModelTabeleOsobe mto = new ModelTabeleOsobe(osobe);
        po.getjTableOsobe().setModel(mto);
    }

    private void pripremiFormu2() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        if (mesta != null) {
            for (Mesto m : mesta) {
                po.getjComboBoxMesto().addItem(m);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Greška: Lista mesta nije učitana sa servera.");
        }
        po.getjComboBoxMesto().setSelectedIndex(-1);
    }

    private void addActionListeners() {
        po.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = po.getjTableOsobe().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(po, "Sistem ne moze da nadje osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleOsobe mto = (ModelTabeleOsobe) po.getjTableOsobe().getModel();
                    Osoba o = mto.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Osoba", o);
                    JOptionPane.showMessageDialog(po, "Sistem je nasao osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniOsobu();
                }

            }

        });

        po.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = po.getjTextFieldIme().getText().trim();
                String prezime = po.getjTextFieldPrezime().getText().trim();
                String telefon = po.getjTextFieldTelefon().getText().trim();
                String email = po.getjTextFieldEmail().getText().trim();
                Mesto m = (Mesto) po.getjComboBoxMesto().getSelectedItem();

                ModelTabeleOsobe mto = (ModelTabeleOsobe) po.getjTableOsobe().getModel();
                mto.pretrazi(ime, prezime, telefon, email, m);
                if (mto.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(po, "Sistem ne moze da nadje osobe po zadatim kriterijumima!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(po, "Sistem je našao osobe po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    po.getjButtonAzuriraj().setEnabled(true);
                }

            }
        });

        po.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                po.getjTextFieldIme().setText("");
                po.getjTextFieldPrezime().setText("");
                po.getjTextFieldTelefon().setText("");
                po.getjTextFieldEmail().setText("");
                po.getjComboBoxMesto().setSelectedIndex(-1);
                po.getjButtonAzuriraj().setEnabled(false);
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
        po.getjTextFieldIme().setText("");
        po.getjTextFieldPrezime().setText("");
        po.getjTextFieldTelefon().setText("");
        po.getjTextFieldEmail().setText("");
        po.getjComboBoxMesto().setSelectedIndex(-1);
        po.getjButtonAzuriraj().setEnabled(false);
    }

}
