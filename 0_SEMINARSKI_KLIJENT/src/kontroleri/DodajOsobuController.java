/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import com.sun.java.accessibility.util.AWTEventMonitor;
import cordinator.Cordinator;
import domen.Mesto;
import domen.Osoba;
import domen.Zaposleni;
import forme.FormaDodajOsobu;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajOsobuController {

    private final FormaDodajOsobu fdo;

    public DodajOsobuController(FormaDodajOsobu fdo) {
        this.fdo = fdo;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu2(mod);
        pripremiFormu();
        fdo.setVisible(true);
    }

    private void pripremiFormu() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        if (mesta != null) {
            for (Mesto m : mesta) {
                fdo.getjComboBoxMesto().addItem(m);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Greška: Lista mesta nije učitana sa servera.");
        }

    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdo.getjButtonAzuriraj().setVisible(false);
                fdo.getjButtonDodaj().setVisible(true);
                fdo.getjButtonDodaj().setEnabled(true);
                fdo.getjTextFieldId().setVisible(false);
                fdo.getjLabelId().setVisible(false);
                break;
            case IZMENI:
                fdo.getjButtonAzuriraj().setVisible(true);
                fdo.getjButtonAzuriraj().setEnabled(true);
                fdo.getjButtonDodaj().setVisible(false);
                Osoba o = (Osoba) Cordinator.getInstance().vratiParam("Osoba");
                fdo.getjTextFieldId().setText(o.getIdOsoba() + "");
                fdo.getjTextFieldId().setEnabled(false);
                fdo.getjTextFieldIme().setText(o.getIme());
                fdo.getjTextFieldPrezime().setText(o.getPrezime());
                fdo.getjTextFieldTelefon().setText(o.getTelefon());
                fdo.getjTextFieldEmail().setText(o.getEmail());
                fdo.getjComboBoxMesto().setSelectedItem(o.getIdMesto().getNaziv());

                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        fdo.dodajOsobuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String ime = fdo.getjTextFieldIme().getText().trim();
                String prezime = fdo.getjTextFieldPrezime().getText().trim();
                String telefon = fdo.getjTextFieldTelefon().getText().trim();
                String email = fdo.getjTextFieldEmail().getText().trim();
                Mesto mesto = (Mesto) fdo.getjComboBoxMesto().getSelectedItem();

                Osoba o = new Osoba(-1, ime, prezime, telefon, email, mesto);

                try {
                    Komunikacija.getInstance().dodajOsobu(o);
                    JOptionPane.showMessageDialog(fdo, "Sistem je kreirao osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdo.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        fdo.azurirajOsobuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(fdo.getjTextFieldId().getText().trim());
                String ime = fdo.getjTextFieldIme().getText().trim();
                String prezime = fdo.getjTextFieldPrezime().getText().trim();
                String telefon = fdo.getjTextFieldTelefon().getText().trim();
                String email = fdo.getjTextFieldEmail().getText().trim();
                Mesto mesto = (Mesto) fdo.getjComboBoxMesto().getSelectedItem();
                System.out.println(mesto);
                System.out.println(mesto.getIdMesto());
                Osoba o = new Osoba(id, ime, prezime, telefon, email, mesto);

                try {
                    Komunikacija.getInstance().azurirajOsobu(o);
                    JOptionPane.showMessageDialog(fdo, "Sistem je azurirao osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdo.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da azurira osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
                osveziTabelu();
            }

            private void osveziTabelu() {
                System.out.println("alo");
                List<Osoba> lista = komunikacija.Komunikacija.getInstance().ucitajOsobe();
                for (Osoba osoba : lista) {
                    System.out.println(osoba);
                }
            }

        });
    }

}
