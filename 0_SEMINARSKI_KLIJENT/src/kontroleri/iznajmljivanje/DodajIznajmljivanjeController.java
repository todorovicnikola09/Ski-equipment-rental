/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.iznajmljivanje;

import cordinator.Cordinator;
import domen.Iznajmljivanje;
import domen.Osoba;
import domen.SkijaskaOprema;
import domen.StavkaIznajmljivanja;
import domen.Zaposleni;
import forme.dodaj.FormaDodajIznajmljivanje;
import forme.dodaj.FormaDodajOsobu;
import forme.FormaMod;
import forme.modeltabele.ModelTabeleIznajmljivanja;
import forme.modeltabele.ModelTabeleStavkeIznajmljivanja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Statement;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Igor
 */
public class DodajIznajmljivanjeController {

    private final FormaDodajIznajmljivanje fdi;
    private List<SkijaskaOprema> skijaskaOprema;
    private double ukupanI = 0;
    private int ukupnoS = 0;
    List<StavkaIznajmljivanja> lista = new ArrayList<>();
    StavkaIznajmljivanja selektovanaStavka;
    Iznajmljivanje iznajmljivanjeAzuriranje = null;

    public DodajIznajmljivanjeController(FormaDodajIznajmljivanje fdi) {
        this.fdi = fdi;
        addActionListener();

        fdi.getjButtonAzurirajStavku().setVisible(false);
        fdi.getjButtonPotvrdiAzuriranje().setVisible(false);
        fdi.getjTextFieldUkupanIznos().setEditable(false);
        fdi.getjTextFieldUkupnoSati().setEditable(false);
        fdi.getjTextFieldCena().setEditable(false);
        fdi.getjTextFieldCenaPoSatu().setEditable(false);
        dodajDocumentListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu2(mod);
        fdi.setVisible(true);
    }

    private void pripremiFormu() {
        List<Zaposleni> zaposleni = komunikacija.Komunikacija.getInstance().ucitajZaposlene();
        if (zaposleni != null) {
            for (Zaposleni z : zaposleni) {
                fdi.getjComboBoxZaposleni().addItem(z);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Zaposleni nisu ucitani.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        fdi.getjComboBoxZaposleni().setSelectedIndex(-1);

        List<Osoba> osobe = komunikacija.Komunikacija.getInstance().ucitajOsobe();
        if (osobe != null) {
            for (Osoba o : osobe) {
                fdi.getjComboBoxOsoba().addItem(o);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Osobe nisu ucitane.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        fdi.getjComboBoxOsoba().setSelectedIndex(-1);

        skijaskaOprema = komunikacija.Komunikacija.getInstance().ucitajSkijaskuOpremu();
        if (skijaskaOprema != null) {
            for (SkijaskaOprema so : skijaskaOprema) {
                String oprema = so.getNaziv();
                boolean postojiOprema = false;
                for (int i = 0; i < fdi.getjComboBoxOprema().getItemCount(); i++) {
                    if (fdi.getjComboBoxOprema().getItemAt(i).equals(oprema)) {
                        postojiOprema = true;
                        break;
                    }
                }
                if (!postojiOprema) {
                    fdi.getjComboBoxOprema().addItem(oprema);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Oprema nije ucitana.", "Greska", JOptionPane.ERROR_MESSAGE);
        }
        fdi.getjComboBoxOprema().setSelectedIndex(-1);

    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdi.setTitle("Kreiraj iznajmljivanje");
                pripremiFormu();
                fdi.getjButtonDodaj().setEnabled(false);
                fdi.getjButtonObrisiIznajmljivanje().setVisible(false);
                fdi.getjButtonOmoguciIzmenu().setVisible(false);
                fdi.getjComboBoxOprema().setEnabled(false);
                fdi.getjComboBoxTipOpreme().setEnabled(false);
                fdi.getjTextFieldCenaPoSatu().setEnabled(false);
                fdi.getjTextFieldCena().setEnabled(false);
                fdi.getjTextFieldBrojSati().setEnabled(false);
                fdi.getjTextFieldDatumPovratkaOpreme().setEnabled(false);
                fdi.getjButtonDodajStavku().setEnabled(false);
                fdi.getjButtonDodaj().setEnabled(false);
                ModelTabeleStavkeIznajmljivanja mtsi1 = new ModelTabeleStavkeIznajmljivanja(lista);
                fdi.getjTableStavkeIznajmljivanja().setModel(mtsi1);
                fdi.getjButtonAzuriraj().setVisible(false);
                fdi.getjButtonDodaj().setVisible(true);
                break;
            case IZMENI:
                fdi.setTitle("Izmeni iznajmljivanje");
                fdi.getjButtonObrisiIznajmljivanje().setVisible(false);
                fdi.getjButtonOmoguciIzmenu().setVisible(true);
                fdi.getjButtonAzurirajStavku().setVisible(true);
//                addActionListener1();
                pripremiFormu();
                fdi.getjButtonAzuriraj().setVisible(true);
                fdi.getjButtonAzuriraj().setEnabled(false);
                fdi.getjButtonDodaj().setVisible(false);
                fdi.getjButtonKreirajRacun().setVisible(false);
                fdi.getjComboBoxZaposleni().setEnabled(false);
                fdi.getjComboBoxOsoba().setEnabled(false);
                fdi.getjComboBoxNacinPlacanja().setEnabled(false);
                fdi.getjTextFieldDatumIznajmljivanja().setEnabled(false);
                fdi.getjComboBoxOprema().setEnabled(false);
                fdi.getjComboBoxTipOpreme().setEnabled(false);
                fdi.getjTextFieldCenaPoSatu().setEnabled(false);
                fdi.getjTextFieldCena().setEnabled(false);
                fdi.getjTextFieldBrojSati().setEnabled(false);
                fdi.getjTextFieldDatumPovratkaOpreme().setEnabled(false);
                fdi.getjButtonAzurirajStavku().setEnabled(false);
                fdi.getjButtonDodajStavku().setEnabled(false);
                
                iznajmljivanjeAzuriranje = (Iznajmljivanje) Cordinator.getInstance().vratiParam("Iznajmljivanje");

                fdi.getjComboBoxZaposleni().setSelectedItem(iznajmljivanjeAzuriranje.getIdZaposleni());
                fdi.getjComboBoxOsoba().setSelectedItem(iznajmljivanjeAzuriranje.getIdOsoba());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                fdi.getjTextFieldDatumIznajmljivanja().setText(iznajmljivanjeAzuriranje.getDatumIznajmljivanja().format(dtf) + "");
                fdi.getjComboBoxNacinPlacanja().setSelectedItem(iznajmljivanjeAzuriranje.getNacinPlacanja());
                fdi.getjTextFieldUkupanIznos().setText(String.format(Locale.US, "%.2f", iznajmljivanjeAzuriranje.getUkupanIznos()));
                fdi.getjTextFieldUkupnoSati().setText(iznajmljivanjeAzuriranje.getUkupnoSati() + "");
                ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(iznajmljivanjeAzuriranje.getLista());
                fdi.getjTableStavkeIznajmljivanja().setModel(mtsi);
                lista = iznajmljivanjeAzuriranje.getLista();
                for (StavkaIznajmljivanja stavkaIznajmljivanja : lista) {
                    System.out.println(stavkaIznajmljivanja);
                }
                ukupanI = iznajmljivanjeAzuriranje.getUkupanIznos();
                ukupnoS = iznajmljivanjeAzuriranje.getUkupnoSati();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void azurirajCenuPoMinutu() {
        String selektovanaOprema = (String) fdi.getjComboBoxOprema().getSelectedItem();
        String selektovaniTipOpreme = (String) fdi.getjComboBoxTipOpreme().getSelectedItem();

        if (selektovanaOprema != null && selektovaniTipOpreme != null && skijaskaOprema != null) {
            for (SkijaskaOprema so : skijaskaOprema) {
                if (so.getNaziv().equals(selektovanaOprema) && so.getTipOprema().equals(selektovaniTipOpreme)) {
                    fdi.getjTextFieldCenaPoSatu().setText(String.valueOf(so.getSatCena()));
                    return;
                }
            }
        }

        fdi.getjTextFieldCenaPoSatu().setText("");
    }

    private void azurirajUkupnuCenu() {
        try {
            String cenaPoSatu = fdi.getjTextFieldCenaPoSatu().getText();
            String satStr = fdi.getjTextFieldBrojSati().getText();

            if (!cenaPoSatu.isEmpty() && !satStr.isEmpty()) {
                double cenaPoMinutu1 = Double.parseDouble(cenaPoSatu);
                int minuta = Integer.parseInt(satStr);

                double ukupanIznos = cenaPoMinutu1 * minuta;
                fdi.getjTextFieldCena().setText(String.format(Locale.US, "%.2f", ukupanIznos));
            } else {
                fdi.getjTextFieldCena().setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(fdi, "Unesite validne brojeve za cenu i minutažu.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dodajDocumentListener() {
        fdi.getjTextFieldBrojSati().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                azurirajUkupnuCenu();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                azurirajUkupnuCenu();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                azurirajUkupnuCenu();
            }
        });
    }

    private void addActionListener() {
        
        fdi.omoguciIzmenuAddActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                fdi.getjComboBoxNacinPlacanja().setEnabled(true);
                fdi.getjTextFieldDatumIznajmljivanja().setEnabled(true);
                fdi.getjComboBoxOprema().setEnabled(true);
                fdi.getjComboBoxTipOpreme().setEnabled(true);
                fdi.getjTextFieldCenaPoSatu().setEnabled(true);
                fdi.getjTextFieldCena().setEnabled(true);
                fdi.getjTextFieldBrojSati().setEnabled(true);
                fdi.getjTextFieldDatumPovratkaOpreme().setEnabled(true);
                fdi.getjButtonAzuriraj().setEnabled(true);
                fdi.getjButtonAzurirajStavku().setEnabled(true);
                fdi.getjButtonDodajStavku().setEnabled(true);
                fdi.getjButtonDodaj().setEnabled(true);
            }
        });
        
        fdi.kreirajRacunAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zaposleni z = (Zaposleni) fdi.getjComboBoxZaposleni().getSelectedItem();
                Osoba o = (Osoba) fdi.getjComboBoxOsoba().getSelectedItem();
                String datumIznajmljivanja1 = fdi.getjTextFieldDatumIznajmljivanja().getText();
                String nacinPlacanja = (String) fdi.getjComboBoxNacinPlacanja().getSelectedItem();

                if (fdi.getjComboBoxZaposleni().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fdi.getjComboBoxOsoba().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fdi.getjComboBoxNacinPlacanja().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumIznajmljivanja = null;
                try {
                    datumIznajmljivanja = LocalDate.parse(datumIznajmljivanja1, format);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if(datumIznajmljivanja.isBefore(LocalDate.now())){
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdi, "Datum iznajmljivanja ne moze biti u proslosti!", "Greška", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(fdi, "Sistem je kreirao iznajmljivanje!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                fdi.getjComboBoxOprema().setEnabled(true);
                fdi.getjComboBoxTipOpreme().setEnabled(true);
                fdi.getjTextFieldCenaPoSatu().setEnabled(true);
                fdi.getjTextFieldCena().setEnabled(true);
                fdi.getjTextFieldBrojSati().setEnabled(true);
                fdi.getjTextFieldDatumPovratkaOpreme().setEnabled(true);
                fdi.getjButtonDodajStavku().setEnabled(true);
                fdi.getjButtonDodaj().setEnabled(true);
                fdi.getjComboBoxZaposleni().setEnabled(false);
                fdi.getjComboBoxOsoba().setEnabled(false);
                fdi.getjTextFieldDatumIznajmljivanja().setEnabled(false);
                fdi.getjComboBoxNacinPlacanja().setEnabled(false);
            }
        });

        fdi.getjComboBoxOprema().addActionListener(e -> {
            String selektovanaOprema = (String) fdi.getjComboBoxOprema().getSelectedItem();
            if (selektovanaOprema != null && skijaskaOprema != null) {
                fdi.getjComboBoxTipOpreme().removeAllItems();
                for (SkijaskaOprema so : skijaskaOprema) {
                    if (so.getNaziv().equals(selektovanaOprema)) {
                        fdi.getjComboBoxTipOpreme().addItem(so.getTipOprema());
                    }
                }
                fdi.getjComboBoxTipOpreme().setSelectedIndex(-1);
            } else {
                fdi.getjComboBoxTipOpreme().removeAllItems();
            }
            azurirajCenuPoMinutu();
        });

        fdi.getjComboBoxTipOpreme().addActionListener(e -> azurirajCenuPoMinutu());

        fdi.getjComboBoxTipOpreme().addActionListener(e -> {
            azurirajCenuPoMinutu();
            azurirajUkupnuCenu();
        });

        fdi.dodajStavkuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                if (fdi.getjTextFieldBrojSati().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (fdi.getjTextFieldCena().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (fdi.getjTextFieldCenaPoSatu().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int brojSati = Integer.parseInt(fdi.getjTextFieldBrojSati().getText());
                double cena = Double.parseDouble(fdi.getjTextFieldCena().getText());
                double satCena = Double.parseDouble(fdi.getjTextFieldCenaPoSatu().getText());
                String datumPovratkaOpreme1 = fdi.getjTextFieldDatumPovratkaOpreme().getText();
                String selektovanaOprema = (String) fdi.getjComboBoxOprema().getSelectedItem();
                String selektovaniTipOpreme = (String) fdi.getjComboBoxTipOpreme().getSelectedItem();

                if (selektovanaOprema == null || selektovaniTipOpreme == null) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idSkiOprema = -1;
                for (SkijaskaOprema so : skijaskaOprema) {
                    if (so.getNaziv().equals(selektovanaOprema) && so.getTipOprema().equals(selektovaniTipOpreme)) {
                        idSkiOprema = so.getIdSkiOprema();
                        break;
                    }
                }

                if (idSkiOprema == -1) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumPovratkaOpreme = null;
                try {
                    datumPovratkaOpreme = LocalDate.parse(datumPovratkaOpreme1, format);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String datumIznajmljivanjaText = fdi.getjTextFieldDatumIznajmljivanja().getText();

                LocalDate datumIznajmljivanja = LocalDate.parse(datumIznajmljivanjaText, format);

                if (datumPovratkaOpreme.isBefore(datumIznajmljivanja)) {
                    JOptionPane.showMessageDialog(fdi,
                            "Datum povratka opreme ne moze biti pre datuma iznajmljivanja!","Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SkijaskaOprema so = new SkijaskaOprema(idSkiOprema, selektovanaOprema, satCena, selektovaniTipOpreme);

                StavkaIznajmljivanja si = new StavkaIznajmljivanja(null, brojSati, cena, satCena, datumPovratkaOpreme, so);

                try {
                    ukupanI += cena;
                    ukupnoS += brojSati;
                    fdi.getjTextFieldUkupanIznos().setText(String.format(Locale.US, "%.2f", ukupanI));
                    fdi.getjTextFieldUkupnoSati().setText(String.valueOf(ukupnoS));

                    lista.add(si);
                    ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(lista);
                    fdi.getjTableStavkeIznajmljivanja().setModel(mtsi);

                    fdi.getjComboBoxTipOpreme().setSelectedIndex(-1);
                    fdi.getjComboBoxOprema().setSelectedIndex(-1);
                    fdi.getjTextFieldBrojSati().setText("");
                    fdi.getjTextFieldDatumPovratkaOpreme().setText("");
                    JOptionPane.showMessageDialog(fdi, "Stavka iznajmljivanja je dodata.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    return;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        );
        
        fdi.obrisiIznajmljivanjeAddActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(fdi,"Da li ste sigurni da želite da obrišete ovo iznajmljivanje?","Potvrda brisanja",JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return; // ako klikne "Ne"
                }

                try {
                    Iznajmljivanje i = iznajmljivanjeAzuriranje;

                    Komunikacija.getInstance().obrisiIznajmljivanje(i);
                    JOptionPane.showMessageDialog(fdi,"Sistem je uspesno obrisao iznajmljivanje.","Uspeh",JOptionPane.INFORMATION_MESSAGE);
                    fdi.setVisible(false);
                    Cordinator.getInstance().otvoriFormuIzmeniIznajmljivanje();


                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(fdi,"Sistem ne može da obriše iznajmljivanje.","Greška",JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        fdi.dodajIznajmljivanjeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                Zaposleni z = (Zaposleni) fdi.getjComboBoxZaposleni().getSelectedItem();
                Osoba o = (Osoba) fdi.getjComboBoxOsoba().getSelectedItem();
                String datumIznajmljivanja1 = fdi.getjTextFieldDatumIznajmljivanja().getText();
                String nacinPlacanja = (String) fdi.getjComboBoxNacinPlacanja().getSelectedItem();
                
                if(lista.isEmpty()){
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da zapamti iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumIznajmljivanja = null;
                try {
                    datumIznajmljivanja = LocalDate.parse(datumIznajmljivanja1, format);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Iznajmljivanje i = new Iznajmljivanje(datumIznajmljivanja, ukupnoS, ukupanI, z, o, nacinPlacanja, lista);
                try {
                    Komunikacija.getInstance().dodajIznajmljivanje(i);
                    JOptionPane.showMessageDialog(fdi, "Sistem je zapamtio iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdi.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da zapamti iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        fdi.azurirajStavkuIznajmljivanjaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = fdi.getjTableStavkeIznajmljivanja().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(fdi, "Morate izabrati stavku koju zelite da azurirate!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    fdi.getjComboBoxZaposleni().setEnabled(false);
                    fdi.getjComboBoxOsoba().setEnabled(false);
                    fdi.getjComboBoxNacinPlacanja().setEnabled(false);
                    fdi.getjTextFieldDatumIznajmljivanja().setEnabled(false);
                    fdi.getjButtonDodaj().setEnabled(false);
                    fdi.getjButtonDodajStavku().setEnabled(false);
                    fdi.getjButtonAzuriraj().setEnabled(false);
                    fdi.getjButtonAzurirajStavku().setEnabled(true);
                    ModelTabeleStavkeIznajmljivanja mtsi = (ModelTabeleStavkeIznajmljivanja) fdi.getjTableStavkeIznajmljivanja().getModel();
                    selektovanaStavka = mtsi.vratiStavku(red);
                    for (StavkaIznajmljivanja selektovana : lista) {
                        if (selektovana.getIdSkiOprema().equals(selektovanaStavka.getIdSkiOprema()) && selektovana.getSatCena() == selektovanaStavka.getSatCena() && selektovana.getCena() == selektovanaStavka.getCena()
                                && selektovana.getBrojSati() == selektovanaStavka.getBrojSati() && selektovana.getIdIznajmljivanje().equals(selektovanaStavka.getIdIznajmljivanje()) && selektovana.getDatumPovratkaOpreme().equals(selektovanaStavka.getDatumPovratkaOpreme())) {
                            selektovanaStavka.setRb(selektovana.getRb());
                        }
                    }
                    System.out.println(selektovanaStavka);
                    fdi.getjComboBoxOprema().setSelectedItem(selektovanaStavka.getIdSkiOprema().getNaziv());
                    fdi.getjComboBoxTipOpreme().setSelectedItem(selektovanaStavka.getIdSkiOprema().getTipOprema());
                    fdi.getjTextFieldCenaPoSatu().setText(selektovanaStavka.getSatCena() + "");
                    fdi.getjTextFieldBrojSati().setText(selektovanaStavka.getBrojSati() + "");
                    fdi.getjTextFieldCena().setText(selektovanaStavka.getCena() + "");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                    fdi.getjTextFieldDatumPovratkaOpreme().setText(selektovanaStavka.getDatumPovratkaOpreme().format(dtf) + "");
                    fdi.getjButtonAzurirajStavku().setVisible(false);
                    fdi.getjButtonPotvrdiAzuriranje().setVisible(true);
                }
            }

        });

        fdi.potvrdiAzuriranjeStavkeIznajmljivanjaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                int brojSati = Integer.parseInt(fdi.getjTextFieldBrojSati().getText());
                double cena = Double.parseDouble(fdi.getjTextFieldCena().getText());
                double satCena = Double.parseDouble(fdi.getjTextFieldCenaPoSatu().getText());
                String datumPovratkaOpreme1 = fdi.getjTextFieldDatumPovratkaOpreme().getText();

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumPovratkaOpreme = null;
                try {
                    datumPovratkaOpreme = LocalDate.parse(datumPovratkaOpreme1, format);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String selektovanaOprema = (String) fdi.getjComboBoxOprema().getSelectedItem();
                String selektovaniTipOpreme = (String) fdi.getjComboBoxTipOpreme().getSelectedItem();

                if (selektovanaOprema == null || selektovaniTipOpreme == null) {
                    JOptionPane.showMessageDialog(fdi, "Molimo izaberite opremu i tip opreme.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idSkiOprema = -1;
                for (SkijaskaOprema so : skijaskaOprema) {
                    if (so.getNaziv().equals(selektovanaOprema) && so.getTipOprema().equals(selektovaniTipOpreme)) {
                        idSkiOprema = so.getIdSkiOprema();
                        break;
                    }
                }

                if (idSkiOprema == -1) {
                    JOptionPane.showMessageDialog(fdi, "Odabrana ski oprema nije validna.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SkijaskaOprema so = new SkijaskaOprema(idSkiOprema, selektovanaOprema, satCena, selektovaniTipOpreme);

                StavkaIznajmljivanja si = new StavkaIznajmljivanja(selektovanaStavka.getRb(), selektovanaStavka.getIdIznajmljivanje(), brojSati, cena, satCena, datumPovratkaOpreme, so);

                try {
                    lista.remove(selektovanaStavka);
                    System.out.println("sklonio sam ovu stavku");
                    ukupanI -= selektovanaStavka.getCena();
                    ukupnoS -= selektovanaStavka.getBrojSati();
                    ukupanI += cena;
                    ukupnoS += brojSati;
                    fdi.getjTextFieldUkupanIznos().setText(String.format(Locale.US, "%.2f", ukupanI));
                    fdi.getjTextFieldUkupnoSati().setText(String.valueOf(ukupnoS));

                    lista.add(si);
                    ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(lista);
                    fdi.getjTableStavkeIznajmljivanja().setModel(mtsi);
                    fdi.getjComboBoxZaposleni().setEnabled(true);
                    fdi.getjComboBoxOsoba().setEnabled(true);
                    fdi.getjComboBoxNacinPlacanja().setEnabled(true);
                    fdi.getjTextFieldDatumIznajmljivanja().setEnabled(true);
                    fdi.getjButtonDodaj().setEnabled(true);
                    fdi.getjButtonDodajStavku().setEnabled(true);
                    fdi.getjButtonAzuriraj().setEnabled(true);
                    fdi.getjButtonAzurirajStavku().setVisible(true);
                    fdi.getjButtonPotvrdiAzuriranje().setVisible(false);

                    fdi.getjComboBoxTipOpreme().setSelectedIndex(-1);
                    fdi.getjComboBoxOprema().setSelectedIndex(-1);
                    fdi.getjTextFieldBrojSati().setText("");
                    fdi.getjTextFieldDatumPovratkaOpreme().setText("");
                    JOptionPane.showMessageDialog(fdi, "Stavka iznajmljivanja je azurirana.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    return;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fdi.azurirajIznajmljivanjeAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                Zaposleni z = (Zaposleni) fdi.getjComboBoxZaposleni().getSelectedItem();
                Osoba o = (Osoba) fdi.getjComboBoxOsoba().getSelectedItem();
                String datumIznajmljivanja1 = fdi.getjTextFieldDatumIznajmljivanja().getText();
                String nacinPlacanja = (String) fdi.getjComboBoxNacinPlacanja().getSelectedItem();

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumIznajmljivanja = null;
                try {
                    datumIznajmljivanja = LocalDate.parse(datumIznajmljivanja1, format);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                for (StavkaIznajmljivanja stIz : iznajmljivanjeAzuriranje.getLista()) {
                    if(datumIznajmljivanja.isAfter(stIz.getDatumPovratkaOpreme())){
                        JOptionPane.showMessageDialog(fdi, "Sistem ne moze da zapamti iznajmljivanje.", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                Iznajmljivanje i = new Iznajmljivanje(iznajmljivanjeAzuriranje.getIdIznajmljivanje(), datumIznajmljivanja, ukupnoS, ukupanI, z, o, nacinPlacanja, lista);
                try {
                    Komunikacija.getInstance().azurirajIznajmljivanje(i);
                    JOptionPane.showMessageDialog(fdi, "Sistem je zapamtio iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    Cordinator.getInstance().otvoriPrikazIznajmljivanja();
                    fdi.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da zapamti iznajmljivanje.", "Greska", JOptionPane.ERROR_MESSAGE);;
                }
            }

        });

    }

    public void osveziFormu() {
        ukupanI = 0;
        ukupnoS = 0;
        for (StavkaIznajmljivanja si : lista) {
            ukupanI += si.getCena();
            ukupnoS += si.getBrojSati();
        }
        fdi.getjTextFieldUkupanIznos().setText(String.format("%.2f", ukupanI));
        fdi.getjTextFieldUkupnoSati().setText(String.valueOf(ukupnoS));

    }
}
