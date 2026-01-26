/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.mesto;

import cordinator.Cordinator;
import domen.Mesto;
import domen.Osoba;
import forme.dodaj.FormaDodajMesto;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajMestoController {

    private final FormaDodajMesto fdm;
    int idMesto;

    public DodajMestoController(FormaDodajMesto fdm) {
        this.fdm = fdm;
        addActionListener();

    }

    private void addActionListener() {
        fdm.dodajMestoAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String naziv = fdm.getjTextFieldNaziv().getText().trim();

                if (naziv.isEmpty()) {
                    JOptionPane.showMessageDialog(fdm, "Morate uneti naziv mesta!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!Character.isUpperCase(naziv.charAt(0))) {
                    JOptionPane.showMessageDialog(fdm, "Naziv mesta mora poceti velikim slovom!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Mesto> mestaBaza = Komunikacija.getInstance().ucitajMesta();
                for (Mesto mesto : mestaBaza) {
                    if (mesto.getNaziv().trim().equalsIgnoreCase(naziv.trim())) {
                        JOptionPane.showMessageDialog(fdm, "Već postoji mesto sa tim imenom!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Mesto m = new Mesto(-1, naziv);

                try {
                    Komunikacija.getInstance().dodajMesto(m);
                    JOptionPane.showMessageDialog(fdm, "Sistem je kreirao mesto.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdm.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdm, "Sistem ne moze da kreira mesto.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        );
        fdm.azurirajMestoAddActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e
            ) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(fdm.getjTextFieldID().getText().trim());
                String ime = fdm.getjTextFieldNaziv().getText().trim();
                
                if (ime.isEmpty()) {
                    JOptionPane.showMessageDialog(fdm, "Morate uneti naziv mesta!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!Character.isUpperCase(ime.charAt(0))) {
                    JOptionPane.showMessageDialog(fdm, "Naziv mesta mora poceti velikim slovom!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                List<Mesto> mestaBaza = Komunikacija.getInstance().ucitajMesta();
                for (Mesto mes : mestaBaza) {
                    if (mes.getNaziv().equalsIgnoreCase(ime) && mes.getIdMesto() != id) {
                        JOptionPane.showMessageDialog(fdm,
                                "Mesto sa tim nazivom već postoji u sistemu!",
                                "GRESKA", JOptionPane.ERROR_MESSAGE);
                        return; // prekid — ne ide dalje na azuriranje
                    }
                }

                Mesto m = new Mesto(id, ime);

                try {
                    Komunikacija.getInstance().azurirajMesto(m);
                    JOptionPane.showMessageDialog(fdm, "Sistem je azurirao mesto.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdm.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdm, "Sistem ne moze da azurira mesto.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
    }

    public void otvoriFormu(FormaMod formaMod) {
        pripremiFormu(formaMod);
        fdm.setVisible(true);
    }

    private void pripremiFormu(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdm.getjButtonAzuriraj().setVisible(false);
                fdm.getjButtonDodaj().setVisible(true);
                fdm.getjButtonDodaj().setEnabled(true);
                fdm.getjTextFieldID().setVisible(false);
                fdm.getjLabelID().setVisible(false);
                break;
            case IZMENI:
                fdm.getjButtonAzuriraj().setVisible(true);
                fdm.getjButtonAzuriraj().setEnabled(true);
                fdm.getjButtonDodaj().setVisible(false);
                fdm.getjLabelID().setVisible(false);
                fdm.getjTextFieldID().setVisible(false);
                Mesto m = (Mesto) Cordinator.getInstance().vratiParam("Mesto");
                fdm.getjTextFieldID().setText(m.getIdMesto() + "");
                fdm.getjTextFieldID().setEnabled(false);
                fdm.getjTextFieldNaziv().setText(m.getNaziv());

                break;
            default:
                throw new AssertionError();
        }
    }

}
