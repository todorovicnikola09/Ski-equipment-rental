/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.TerminDezurstva;
import domen.ZapTermin;
import domen.Zaposleni;
import forme.FormaDodajTerminDezurstva;
import forme.FormaDodajTerminZaposlenog;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajTerminZaposlenogController {

    private List<TerminDezurstva> termini;
    private final FormaDodajTerminZaposlenog fdtz;
    private ZapTermin podaci = null;
    
    public DodajTerminZaposlenogController(FormaDodajTerminZaposlenog fdtz) {
        this.fdtz = fdtz;
        addActionListener();
        fdtz.getjLabelVreme().setEnabled(false);
        fdtz.getjComboBoxVreme().setEnabled(false);
        pripremiFormu();
    }

    private void pripremiFormu() {
        List<Zaposleni> zaposleni = komunikacija.Komunikacija.getInstance().ucitajZaposlene();
        if (zaposleni != null) {
            for (Zaposleni z : zaposleni) {
                fdtz.getjComboBoxZaposleni().addItem(z);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Zaposleni nisu ucitani.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        fdtz.getjComboBoxZaposleni().setSelectedIndex(-1);
        termini = komunikacija.Komunikacija.getInstance().ucitajTermineDezurstava();
        if (termini != null) {
            for (TerminDezurstva td : termini) {
                String tipTermina = td.getTipTermina();
                boolean postoji = false;
                for (int i = 0; i < fdtz.getjComboBoxTermin().getItemCount(); i++) {
                    if (fdtz.getjComboBoxTermin().getItemAt(i).equals(tipTermina)) {
                        postoji = true;
                        break;
                    }
                }
                if (!postoji) {
                    fdtz.getjComboBoxTermin().addItem(tipTermina);
                }
            }
            fdtz.getjComboBoxTermin().setSelectedIndex(-1);
        } else {
            JOptionPane.showMessageDialog(null, "Termini nisu ucitani.", "Greska", JOptionPane.ERROR_MESSAGE);
        }

        fdtz.getjComboBoxTermin().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fdtz.getjComboBoxVreme().removeAllItems();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    fdtz.getjLabelVreme().setEnabled(true);
                    fdtz.getjComboBoxVreme().setEnabled(true);
                    String izabraniTip = (String) fdtz.getjComboBoxTermin().getSelectedItem();
                    osveziVremenaZaTip(izabraniTip);
                }
            }
        });
    }

    private void osveziVremenaZaTip(String tip) {

        for (TerminDezurstva td : termini) {
            if (td.getTipTermina().equalsIgnoreCase(tip)) {
                fdtz.getjComboBoxVreme().addItem(td.getVremeOd() + " - " + td.getVremeDo());
            }
        }
        if (fdtz.getjComboBoxVreme().getItemCount() == 0) {
            fdtz.getjComboBoxVreme().addItem("Nema dostupnih vremena za ovaj tip");
        }
    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdtz.getjButtonAzuriraj().setVisible(false);
                fdtz.getjButtonDodaj().setVisible(true);
                fdtz.getjButtonDodaj().setEnabled(true);
                break;
            case IZMENI:
                fdtz.getjButtonAzuriraj().setVisible(true);
                fdtz.getjButtonAzuriraj().setEnabled(true);
                fdtz.getjButtonDodaj().setVisible(false);
                ZapTermin zt = (ZapTermin) Cordinator.getInstance().vratiParam("Termin zaposlenog");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                fdtz.getjTextFieldDatumRada().setText(zt.getDatumRada().format(formatter));
                System.out.println(zt.getIdZaposleni().getIdZaposleni());
                fdtz.getjComboBoxZaposleni().setSelectedItem(zt.getIdZaposleni());
                fdtz.getjComboBoxTermin().setSelectedItem(zt.getIdTerminDezurstva().getTipTermina() + "");
                fdtz.getjComboBoxVreme().setSelectedItem(zt.getIdTerminDezurstva().getVremeOd() + " " + zt.getIdTerminDezurstva().getVremeDo());
                
                
                Zaposleni z = new Zaposleni(zt.getIdZaposleni().getIdZaposleni(), zt.getIdZaposleni().getIme(), zt.getIdZaposleni().getPrezime(), zt.getIdZaposleni().getEmail(), zt.getIdZaposleni().getUsername(), zt.getIdZaposleni().getPassword());
                TerminDezurstva td = new TerminDezurstva(zt.getIdTerminDezurstva().getIdTerminDezurstva(), zt.getIdTerminDezurstva().getTipTermina(), zt.getIdTerminDezurstva().getVremeOd(), zt.getIdTerminDezurstva().getVremeDo());
                LocalDate datumRada = zt.getDatumRada();
                podaci = new ZapTermin(z,td,datumRada);
                System.out.println(podaci);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        fdtz.dodajTerminZaposlenogAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                Zaposleni z = (Zaposleni) fdtz.getjComboBoxZaposleni().getSelectedItem();
                String datumRada1 = fdtz.getjTextFieldDatumRada().getText();
                String tipTermina = (String) fdtz.getjComboBoxTermin().getSelectedItem();
                String vreme = (String) fdtz.getjComboBoxVreme().getSelectedItem();
                
                System.out.println(z.getIdZaposleni());
                TerminDezurstva trazeniTermin = null;

                for (TerminDezurstva td : termini) {
                    String vremeFormatirano = td.getVremeOd() + " - " + td.getVremeDo();
                    if (td.getTipTermina().equalsIgnoreCase(tipTermina) && vremeFormatirano.equals(vreme)) {
                        trazeniTermin = td;
                        break;
                    }
                }

                if (trazeniTermin == null) {
                    JOptionPane.showMessageDialog(fdtz, "Smena nije pronađena za dati tip i vreme!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumRada = null;
                try {
                    datumRada = LocalDate.parse(datumRada1, format);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtz, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ZapTermin zt = new ZapTermin(z, trazeniTermin, datumRada);

                try {
                    Komunikacija.getInstance().dodajTerminZaposlenog(zt);
                    JOptionPane.showMessageDialog(fdtz, "Sistem je kreirao termin zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdtz.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtz, "Sistem ne moze da kreira temin zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        
        fdtz.azurirajTerminZaposlenogAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                
                Zaposleni z = (Zaposleni) fdtz.getjComboBoxZaposleni().getSelectedItem();
                String datumRada1 = fdtz.getjTextFieldDatumRada().getText();
                String tipTermina = (String) fdtz.getjComboBoxTermin().getSelectedItem();
                String vreme = (String) fdtz.getjComboBoxVreme().getSelectedItem();
                
                System.out.println(z.getIdZaposleni());
                TerminDezurstva trazeniTermin = null;

                for (TerminDezurstva td : termini) {
                    String vremeFormatirano = td.getVremeOd() + " - " + td.getVremeDo();
                    if (td.getTipTermina().equalsIgnoreCase(tipTermina) && vremeFormatirano.equals(vreme)) {
                        trazeniTermin = td;
                        break;
                    }
                }

                if (trazeniTermin == null) {
                    JOptionPane.showMessageDialog(fdtz, "Smena nije pronađena za dati tip i vreme!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumRada = null;
                try {
                    datumRada = LocalDate.parse(datumRada1, format);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtz, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ZapTermin zt = new ZapTermin(z, trazeniTermin, datumRada);
                
                try {
                    Komunikacija.getInstance().azurirajTerminZaposlenog(zt,podaci);
                    JOptionPane.showMessageDialog(fdtz, "Sistem je azurirao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdtz.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtz, "Sistem ne moze da azurira zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                osveziTabelu();
            }

            private void osveziTabelu() {
                List<ZapTermin> lista = komunikacija.Komunikacija.getInstance().ucitajTermineZaposlenih();
                for (ZapTermin termini : lista) {
                    System.out.println(termini);
                }
            }

        });
        
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu2(mod);
        fdtz.setVisible(true);
    }

}
