/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.Mesto;
import domen.Osoba;
import forme.FormaPrikazOsoba;
import forme.ModelTabeleOsobe;
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
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
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
                po.getjComboBoxMesto().setSelectedItem(m);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Greška: Lista mesta nije učitana sa servera.");
        }

    }

    private void addActionListeners() {
        po.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = po.getjTableOsobe().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(po, "Sistem ne moze da azurira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleOsobe mto = (ModelTabeleOsobe) po.getjTableOsobe().getModel();
                    Osoba o = mto.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Osoba", o);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniOsobu();
                }

            }

            
        });

        po.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = po.getjTableOsobe().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(po, "Sistem ne moze da obrise osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleOsobe mto = (ModelTabeleOsobe) po.getjTableOsobe().getModel();
                    Osoba o = mto.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiOsobu(o);
                        JOptionPane.showMessageDialog(null, "Sistem je obrisao osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Sistem ne moze da obrise osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
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

            }
        });

        po.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();

            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
