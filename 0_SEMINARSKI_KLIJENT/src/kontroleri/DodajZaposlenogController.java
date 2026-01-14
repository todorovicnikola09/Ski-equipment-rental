/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.Mesto;
import domen.Osoba;
import domen.Zaposleni;
import forme.FormaDodajZaposlenog;
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

    public DodajZaposlenogController(FormaDodajZaposlenog fdz) {
        this.fdz = fdz;
        addActionListener();

    }
    
    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdz.getjButtonAzuriraj().setVisible(false);
                fdz.getjButtonDodaj().setVisible(true);
                fdz.getjButtonDodaj().setEnabled(true);
                fdz.getjTextFieldID().setVisible(false);
                fdz.getjLabelID().setVisible(false);
                break;
            case IZMENI:
                fdz.getjButtonAzuriraj().setVisible(true);
                fdz.getjButtonAzuriraj().setEnabled(true);
                fdz.getjButtonDodaj().setVisible(false);
                Zaposleni z = (Zaposleni) Cordinator.getInstance().vratiParam("Zaposleni");
                fdz.getjTextFieldID().setText(z.getIdZaposleni()+ "");
                fdz.getjTextFieldID().setEnabled(false);
                fdz.getjTextFieldIme().setText(z.getIme());
                fdz.getjTextFieldPrezime().setText(z.getPrezime());
                fdz.getjTextFieldEmail().setText(z.getEmail());
                fdz.getjTextFieldUsername().setText(z.getUsername());
                fdz.getjPasswordFieldLozinka().setText(z.getPassword());
                fdz.getjPasswordFieldPotvrdaLozinke().setText(z.getPassword());
                break;
            default:
                throw new AssertionError();
        }
    }
    
    private void addActionListener() {
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
                
                if (!lozinka.equals(potvrdaLozinke)) {
                    JOptionPane.showMessageDialog(fdz, "Lozinke se ne podudaraju.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Zaposleni z = new Zaposleni(id,ime, prezime, email, username, lozinka);
                try {
                    Komunikacija.getInstance().azurirajZaposlenog(z);
                    JOptionPane.showMessageDialog(fdz, "Sistem je azurirao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdz.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdz, "Sistem ne moze da azurira zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                osveziTabelu();
            }

            private void osveziTabelu() {
                List<Zaposleni> lista = komunikacija.Komunikacija.getInstance().ucitajZaposlene();
                for (Zaposleni zaposleni : lista) {
                    System.out.println(zaposleni);
                }
            }

        });
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu(mod);
        fdz.setVisible(true);
    }
}
