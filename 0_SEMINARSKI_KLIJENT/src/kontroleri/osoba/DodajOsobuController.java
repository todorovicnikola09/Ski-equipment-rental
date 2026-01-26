/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.osoba;

import com.sun.java.accessibility.util.AWTEventMonitor;
import cordinator.Cordinator;
import domen.Mesto;
import domen.Osoba;
import domen.Zaposleni;
import forme.dodaj.FormaDodajOsobu;
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
    private int idUcitane;

    public DodajOsobuController(FormaDodajOsobu fdo) {
        this.fdo = fdo;
        addActionListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu();
        pripremiFormu2(mod);
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
        fdo.getjComboBoxMesto().setSelectedIndex(-1);
    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdo.getjButtonOmoguciIzmenu().setVisible(false);
                fdo.getjButtonObrisi().setVisible(false);
                fdo.getjButtonAzuriraj().setVisible(false);
                fdo.getjButtonDodaj().setVisible(true);
                fdo.getjButtonDodaj().setEnabled(false);
                fdo.getjTextFieldId().setVisible(false);
                fdo.getjLabelId().setVisible(false);
                break;
            case IZMENI:
                fdo.setTitle("Izmeni osobu");
                fdo.getjButtonKreiraj().setVisible(false);
                fdo.getjButtonAzuriraj().setVisible(true);
                fdo.getjButtonDodaj().setVisible(false);
                fdo.getjLabelId().setVisible(false);
                fdo.getjTextFieldId().setVisible(false);
                fdo.getjButtonObrisi().setEnabled(true);
                fdo.getjButtonAzuriraj().setEnabled(false);
                fdo.getjTextFieldIme().setEnabled(false);
                fdo.getjTextFieldPrezime().setEnabled(false);
                fdo.getjTextFieldEmail().setEnabled(false);
                fdo.getjTextFieldTelefon().setEnabled(false);
                fdo.getjComboBoxMesto().setEnabled(false);
                Osoba o = (Osoba) Cordinator.getInstance().vratiParam("Osoba");
                fdo.getjTextFieldId().setText(o.getIdOsoba() + "");
                fdo.getjTextFieldId().setEnabled(false);
                fdo.getjTextFieldIme().setText(o.getIme());
                fdo.getjTextFieldPrezime().setText(o.getPrezime());
                fdo.getjTextFieldTelefon().setText(o.getTelefon());
                fdo.getjTextFieldEmail().setText(o.getEmail());
                fdo.getjComboBoxMesto().setSelectedItem(o.getIdMesto());

                idUcitane = o.getIdOsoba();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        fdo.kreirajOsobuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = fdo.getjTextFieldIme().getText().trim();
                String prezime = fdo.getjTextFieldPrezime().getText().trim();
                String email = fdo.getjTextFieldEmail().getText().trim();
                String telefon = fdo.getjTextFieldTelefon().getText().trim();
                Mesto m = (Mesto) fdo.getjComboBoxMesto().getSelectedItem();

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti ime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Ime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (prezime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti prezime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(prezime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Prezime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                List<Osoba> osobe = Komunikacija.getInstance().ucitajOsobe();

                if (telefon.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti telefon osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (telefon.length() < 9 || telefon.length() > 10) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Telefon mora da bude duzine od 9 ili 10 karaktera.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Osoba os : osobe) {
                    if (os.getTelefon().equals(telefon)) {
                        JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(fdo, "Vec postoji osoba sa tim brojem telefona.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti email osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!email.contains("@")
                        || !(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com"))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Email mora sadrzati @ i biti domena gmail.com, yahoo.com ili outlook.com!", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Osoba os : osobe) {
                    if (os.getEmail().equals(email)) {
                        JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(fdo, "Taj email je vec iskoriscen.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                if (fdo.getjComboBoxMesto().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da kreira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate izabrati mesto osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(fdo, "Sistem je kreirao osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                fdo.getjButtonDodaj().setEnabled(true);
            }

        });

        fdo.omoguciIzmenuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fdo.getjButtonObrisi().setEnabled(false);
                fdo.getjButtonAzuriraj().setEnabled(true);
                fdo.getjTextFieldIme().setEnabled(true);
                fdo.getjTextFieldPrezime().setEnabled(true);
                fdo.getjTextFieldEmail().setEnabled(true);
                fdo.getjTextFieldTelefon().setEnabled(true);
                fdo.getjComboBoxMesto().setEnabled(true);
            }

        });

        fdo.obrisiOsobuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int id = Integer.parseInt(fdo.getjTextFieldId().getText().trim());
                String ime = fdo.getjTextFieldIme().getText().trim();
                String prezime = fdo.getjTextFieldPrezime().getText().trim();
                String email = fdo.getjTextFieldEmail().getText().trim();
                String telefon = fdo.getjTextFieldTelefon().getText().trim();
                Mesto m = (Mesto) fdo.getjComboBoxMesto().getSelectedItem();

                Osoba o = new Osoba(id, ime, prezime, telefon, email, m);

                int potvrda = JOptionPane.showConfirmDialog(
                        fdo,
                        "Da li ste sigurni da želite da obrišete ovu osobu?",
                        "Potvrda brisanja",
                        JOptionPane.YES_NO_OPTION
                );

                if (potvrda == JOptionPane.YES_OPTION) {
                    try {
                        String poruka = Komunikacija.getInstance().obrisiOsobu(o);

                        if (poruka != null && !poruka.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                    fdo,
                                    poruka,
                                    "Greška",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    fdo,
                                    "Sistem je obrisao osobu.",
                                    "Obaveštenje",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            fdo.setVisible(false);
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                fdo,
                                "Došlo je do greške u komunikaciji sa serverom.",
                                "GRESKA",
                                JOptionPane.ERROR_MESSAGE
                        );

                    }
                }
            }
        });

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

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti ime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Ime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (prezime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti prezime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(prezime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Prezime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                List<Osoba> osobe = Komunikacija.getInstance().ucitajOsobe();

                if (telefon.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti telefon osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (telefon.length() < 9 || telefon.length() > 10) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Telefon mora da bude duzine od 9 ili 10 karaktera.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Osoba os : osobe) {
                    if (os.getTelefon().equals(telefon)) {
                        JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(fdo, "Vec postoji osoba sa tim brojem telefona.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti email osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!email.contains("@")
                        || !(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com"))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Email mora sadrzati @ i biti domena gmail.com, yahoo.com ili outlook.com!", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Osoba os : osobe) {
                    if (os.getEmail().equals(email)) {
                        JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(fdo, "Taj email je vec iskoriscen.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }

                if (fdo.getjComboBoxMesto().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate izabrati mesto osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Osoba o = new Osoba(-1, ime, prezime, telefon, email, mesto);

                try {
                    Komunikacija.getInstance().dodajOsobu(o);
                    JOptionPane.showMessageDialog(fdo, "Sistem je zapamtio osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdo.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
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

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti ime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Ime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (prezime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti prezime osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(prezime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Prezime mora imati veliko pocetno slovo.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                List<Osoba> osobe = Komunikacija.getInstance().ucitajOsobe();

                if (telefon.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti telefon osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (telefon.length() < 9 || telefon.length() > 10) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Telefon mora da bude duzine od 9 ili 10 karaktera.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (idUcitane == id) {

                } else {
                    for (Osoba os : osobe) {
                        if (os.getTelefon().equals(telefon)) {
                            JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(fdo, "Vec postoji osoba sa tim brojem telefona.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate uneti email osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!email.contains("@")
                        || !(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com"))) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Email mora sadrzati @ i biti domena gmail.com, yahoo.com ili outlook.com!", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (idUcitane == id) {

                } else {
                    for (Osoba os : osobe) {
                        if (os.getEmail().equals(email)) {
                            JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                            JOptionPane.showMessageDialog(fdo, "Taj email je vec iskoriscen.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }

                if (fdo.getjComboBoxMesto().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdo, "Morate izabrati mesto osobe.", "Greska", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Osoba o = new Osoba(id, ime, prezime, telefon, email, mesto);

                try {
                    Komunikacija.getInstance().azurirajOsobu(o);
                    JOptionPane.showMessageDialog(fdo, "Sistem je zapamtio osobu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdo.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdo, "Sistem ne moze da zapamti osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

}
