/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.zaposleni;

import cordinator.Cordinator;
import domen.Mesto;
import domen.Osoba;
import domen.Zaposleni;
import forme.dodaj.FormaDodajZaposlenog;
import forme.FormaMod;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajZaposlenogController {

    private final FormaDodajZaposlenog fdz;
    Zaposleni zaposleniBrisanje;

    public DodajZaposlenogController(FormaDodajZaposlenog fdz) {
        this.fdz = fdz;

        addActionListener();

    }

    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdz.getjButtonOmoguciIzmenu().setVisible(false);
                fdz.getjButtonObrisiZaposlenog().setVisible(false);
                fdz.getjButtonAzuriraj().setVisible(false);
                fdz.getjButtonDodaj().setVisible(true);
                fdz.getjButtonDodaj().setEnabled(true);
                fdz.getjTextFieldID().setVisible(false);
                fdz.getjLabelID().setVisible(false);
                break;
            case IZMENI:
                fdz.getjButtonOmoguciIzmenu().setVisible(true);
                fdz.getjButtonObrisiZaposlenog().setVisible(true);
                fdz.getjButtonAzuriraj().setVisible(true);
                fdz.getjButtonDodaj().setVisible(false);
                fdz.getjTextFieldID().setVisible(false);
                fdz.getjLabelID().setVisible(false);
                
                fdz.getjButtonAzuriraj().setEnabled(false);
                fdz.getjTextFieldIme().setEnabled(false);
                fdz.getjTextFieldPrezime().setEnabled(false);
                fdz.getjTextFieldEmail().setEnabled(false);
                fdz.getjTextFieldUsername().setEnabled(false);
                fdz.getjPasswordFieldLozinka().setEnabled(false);
                fdz.getjPasswordFieldPotvrdaLozinke().setEnabled(false);
                zaposleniBrisanje = (Zaposleni) Cordinator.getInstance().vratiParam("Zaposleni");
                fdz.getjTextFieldID().setText(zaposleniBrisanje.getIdZaposleni() + "");
                fdz.getjTextFieldID().setEnabled(false);
                fdz.getjTextFieldIme().setText(zaposleniBrisanje.getIme());
                fdz.getjTextFieldPrezime().setText(zaposleniBrisanje.getPrezime());
                fdz.getjTextFieldEmail().setText(zaposleniBrisanje.getEmail());
                fdz.getjTextFieldUsername().setText(zaposleniBrisanje.getUsername());
                fdz.getjPasswordFieldLozinka().setText(zaposleniBrisanje.getPassword());
                fdz.getjPasswordFieldPotvrdaLozinke().setText(zaposleniBrisanje.getPassword());
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        fdz.omoguciIzmenuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fdz.getjButtonObrisiZaposlenog().setEnabled(false);
                fdz.getjButtonAzuriraj().setEnabled(true);
                fdz.getjTextFieldIme().setEnabled(true);
                fdz.getjTextFieldPrezime().setEnabled(true);
                fdz.getjTextFieldEmail().setEnabled(true);
                fdz.getjTextFieldUsername().setEnabled(true);
                fdz.getjPasswordFieldLozinka().setEnabled(true);
                fdz.getjPasswordFieldPotvrdaLozinke().setEnabled(true);
            }
        });
        fdz.obrisiZaposlenogAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int id = Integer.parseInt(fdz.getjTextFieldID().getText().trim());
                String ime = fdz.getjTextFieldIme().getText().trim();
                String prezime = fdz.getjTextFieldPrezime().getText().trim();
                String email = fdz.getjTextFieldEmail().getText().trim();
                String username = fdz.getjTextFieldUsername().getText().trim();
                String lozinka = String.valueOf(fdz.getjPasswordFieldLozinka().getPassword());

                Zaposleni z = new Zaposleni(id, ime, prezime, email, username, lozinka);

                int potvrda = JOptionPane.showConfirmDialog(
                        fdz,
                        "Da li ste sigurni da želite da obrišete ovog zaposlenog?",
                        "Potvrda brisanja",
                        JOptionPane.YES_NO_OPTION
                );

                if (potvrda == JOptionPane.YES_OPTION) {
                    try {
                        String poruka = Komunikacija.getInstance().obrisiZaposlenog(z);

                        if (poruka != null && !poruka.isEmpty()) {
                            JOptionPane.showMessageDialog(
                                    fdz,
                                    poruka,
                                    "Greška",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    fdz,
                                    "Sistem je uspešno obrisao zaposlenog.",
                                    "Obaveštenje",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            fdz.setVisible(false);
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                fdz,
                                "Došlo je do greške u komunikaciji sa serverom.",
                                "GRESKA",
                                JOptionPane.ERROR_MESSAGE
                        );

                    }
                }
            }
        });

        fdz.dodajZaposlenogAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String ime = fdz.getjTextFieldIme().getText().trim();
                String prezime = fdz.getjTextFieldPrezime().getText().trim();
                String email = fdz.getjTextFieldEmail().getText().trim();
                String username = fdz.getjTextFieldUsername().getText().trim();
                String lozinka = String.valueOf(fdz.getjPasswordFieldLozinka().getPassword());
                String potvrdaLozinke = String.valueOf(fdz.getjPasswordFieldPotvrdaLozinke().getPassword());

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti ime zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdz, "Ime mora imati veliko pocetno slovo.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (prezime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti prezime zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(prezime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdz, "Prezime mora imati veliko pocetno slovo.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti email zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.contains("@")
                        || !(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com"))) {
                    JOptionPane.showMessageDialog(fdz, "Email mora sadrzati @ i biti domena gmail.com, yahoo.com ili outlook.com!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti username zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Zaposleni> ucitajSveZaposlene = Komunikacija.getInstance().ucitajZaposlene();
                for (Zaposleni z : ucitajSveZaposlene) {
                    if (z.getUsername().equals(username)) {
                        JOptionPane.showMessageDialog(fdz, "Zadati username vec postoji u sistemu.", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (lozinka.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti lozinku zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (lozinka.length() < 8) {
                    JOptionPane.showMessageDialog(fdz, "Lozinka mora imati minimum 8 karaktera.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!lozinka.equals(potvrdaLozinke)) {
                    JOptionPane.showMessageDialog(fdz, "Lozinke se ne podudaraju.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Zaposleni z = new Zaposleni(-1, ime, prezime, email, username, lozinka);
                try {
                    Komunikacija.getInstance().dodajZaposlenog(z);
                    JOptionPane.showMessageDialog(fdz, "Sistem je kreirao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdz.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdz, "Sistem ne moze da kreira zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }

        });
        fdz.azurirajOsobuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(fdz.getjTextFieldID().getText().trim());
                String ime = fdz.getjTextFieldIme().getText().trim();
                String prezime = fdz.getjTextFieldPrezime().getText().trim();
                String email = fdz.getjTextFieldEmail().getText().trim();
                String username = fdz.getjTextFieldUsername().getText().trim();
                String lozinka = String.valueOf(fdz.getjPasswordFieldLozinka().getPassword());
                String potvrdaLozinke = String.valueOf(fdz.getjPasswordFieldPotvrdaLozinke().getPassword());

                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti ime zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdz, "Ime mora imati veliko pocetno slovo.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (prezime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti prezime zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Character.isUpperCase(prezime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdz, "Prezime mora imati veliko pocetno slovo.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti email zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.contains("@")
                        || !(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@outlook.com"))) {
                    JOptionPane.showMessageDialog(fdz, "Email mora sadrzati @ i biti domena gmail.com, yahoo.com ili outlook.com!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti username zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Zaposleni> ucitajSveZaposlene = Komunikacija.getInstance().ucitajZaposlene();

                if (lozinka.isEmpty()) {
                    JOptionPane.showMessageDialog(fdz, "Morate uneti lozinku zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (lozinka.length() < 8) {
                    JOptionPane.showMessageDialog(fdz, "Lozinka mora imati minimum 8 karaktera.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!lozinka.equals(potvrdaLozinke)) {
                    JOptionPane.showMessageDialog(fdz, "Lozinke se ne podudaraju.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!lozinka.equals(potvrdaLozinke)) {
                    JOptionPane.showMessageDialog(fdz, "Lozinke se ne podudaraju.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Zaposleni z = new Zaposleni(id, ime, prezime, email, username, lozinka);

                if (z.equals(zaposleniBrisanje)) {
                } else {
                    for (Zaposleni za : ucitajSveZaposlene) {
                        if (za.getUsername().equals(username)) {
                            JOptionPane.showMessageDialog(fdz, "Zadati username vec postoji u sistemu.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                try {
                    Komunikacija.getInstance().azurirajZaposlenog(z);
                    JOptionPane.showMessageDialog(fdz, "Sistem je zapamtio zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdz.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdz, "Sistem ne moze da zapamti zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }


        });
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        fdz.setVisible(true);
    }
}
