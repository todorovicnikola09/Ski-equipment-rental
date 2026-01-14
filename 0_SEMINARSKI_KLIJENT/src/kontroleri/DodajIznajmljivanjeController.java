/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import cordinator.Cordinator;
import domen.Iznajmljivanje;
import domen.Osoba;
import domen.SkijaskaOprema;
import domen.StavkaIznajmljivanja;
import domen.Zaposleni;
import forme.FormaDodajIznajmljivanje;
import forme.FormaDodajOsobu;
import forme.FormaMod;
import forme.FormaPrikazStavkiIznajmljivanja;
import forme.ModelTabeleStavkeIznajmljivanja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Statement;
import java.time.LocalDate;
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
    private int id = 0;
    private double ukupanI = 0;
    private int ukupnoS = 0;
    Iznajmljivanje iznajmljivanjeGlavno = null;
    List<StavkaIznajmljivanja> lista = new ArrayList<>();
    StavkaIznajmljivanja stavkaZaAzuriranje = null;

    public DodajIznajmljivanjeController(FormaDodajIznajmljivanje fdi) {
        this.fdi = fdi;
        addActionListener();

//        fdi.getjLabelBrojSati().setVisible(false);
//        fdi.getjTextFieldBrojSati().setVisible(false);
//        fdi.getjLabelCena().setVisible(false);
//        fdi.getjTextFieldCena().setVisible(false);
//        fdi.getjLabelCenaPoSatu().setVisible(false);
//        fdi.getjTextFieldCenaPoSatu().setVisible(false);
//        fdi.getjLabelDatumPovratkaOpreme().setVisible(false);
//        fdi.getjTextFieldDatumPovratkaOpreme().setVisible(false);
//        fdi.getjLabelOprema().setVisible(false);
//        fdi.getjComboBoxOprema().setVisible(false);
//        fdi.getjLabelTipOpreme().setVisible(false);
//        fdi.getjComboBoxTipOpreme().setVisible(false);
//        fdi.getjLabelUkupanIznos().setVisible(false);
//        fdi.getjTextFieldUkupanIznos().setVisible(false);
//        fdi.getjLabelUkupnoSati().setVisible(false);
//        fdi.getjTextFieldUkupnoSati().setVisible(false);
//        fdi.getjSeparator1().setVisible(false);
//        fdi.getjSeparator2().setVisible(false);
//        fdi.getjSeparator3().setVisible(false);
//        fdi.getjSeparator4().setVisible(false);
//        fdi.getjButtonAzuriraj().setVisible(false);
//        fdi.getjButtonDodaj().setVisible(false);
//        fdi.getjButtonDodajStavku().setVisible(false);
//        fdi.getjButtonPrikaziStavke().setVisible(false);
//        fdi.setSize(779, 250);
        fdi.getjTextFieldUkupanIznos().setEditable(false);
        fdi.getjTextFieldUkupnoSati().setEditable(false);
        fdi.getjTextFieldCena().setEditable(false);
        fdi.getjTextFieldCenaPoSatu().setEditable(false);
        dodajDocumentListener();
    }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu2(mod);
        pripremiFormu();
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

        ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(lista);
        fdi.getjTableStavkeIznajmljivanja().setModel(mtsi);

    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fdi.getjButtonAzuriraj().setVisible(false);
                fdi.getjButtonDodaj().setVisible(true);
                fdi.getjButtonDodaj().setEnabled(true);
                break;
            case IZMENI:
                fdi.getjButtonAzuriraj().setVisible(true);
                fdi.getjButtonAzuriraj().setEnabled(true);
                fdi.getjButtonDodaj().setVisible(false);
                Iznajmljivanje i = (Iznajmljivanje) Cordinator.getInstance().vratiParam("Iznajmljivanje");
                fdi.getjComboBoxZaposleni().setSelectedItem(i.getIdZaposleni());
                fdi.getjComboBoxOsoba().setSelectedItem(i.getIdOsoba());
                fdi.getjTextFieldDatumIznajmljivanja().setText(i.getDatumIznajmljivanja() + "");
                fdi.getjComboBoxNacinPlacanja().setSelectedItem(i.getNacinPlacanja());
                fdi.getjTextFieldUkupanIznos().setText(i.getUkupanIznos() + "");
                fdi.getjTextFieldUkupnoSati().setText(i.getUkupnoSati() + "");

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
            String cenaPoMinutu = fdi.getjTextFieldCenaPoSatu().getText();
            String minutaStr = fdi.getjTextFieldBrojSati().getText();

            if (!cenaPoMinutu.isEmpty() && !minutaStr.isEmpty()) {
                double cenaPoMinutu1 = Double.parseDouble(cenaPoMinutu);
                int minuta = Integer.parseInt(minutaStr);

                double ukupanIznos = cenaPoMinutu1 * minuta;
                fdi.getjTextFieldCena().setText(String.format(Locale.US,"%.2f", ukupanIznos));
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

//    private void dodajDocumentListener1() {
//        fdi.getjTextFieldUkupanIznos().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
//            @Override
//            public void insertUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupanIznos();
//            }
//
//            @Override
//            public void removeUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupanIznos();
//            }
//
//            @Override
//            public void changedUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupanIznos();
//            }
//        });
//    }
//    private void dodajDocumentListener2() {
//        fdi.getjTextFieldBrojSati().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
//            @Override
//            public void insertUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupnuCenu();
//            }
//
//            @Override
//            public void removeUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupnuCenu();
//            }
//
//            @Override
//            public void changedUpdate(javax.swing.event.DocumentEvent e) {
//                azurirajUkupnuCenu();
//            }
//        });
//    }
    private void addActionListener() {

//        fdi.kreirajIznajmljivanjeAddActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                kreiraj(e);
//            }
//
//            private void kreiraj(ActionEvent e) {
//                Zaposleni z = (Zaposleni) fdi.getjComboBoxZaposleni().getSelectedItem();
//                Osoba o = (Osoba) fdi.getjComboBoxOsoba().getSelectedItem();
//                String datumIznajmljivanja1 = fdi.getjTextFieldDatumIznajmljivanja().getText();
//                String nacinPlacanja = (String) fdi.getjComboBoxNacinPlacanja().getSelectedItem();
//
//                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
//
//                LocalDate datumIznajmljivanja = null;
//                try {
//                    datumIznajmljivanja = LocalDate.parse(datumIznajmljivanja1, format);
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(fdi, "Unesite vreme u formatu dd.MM.yyyy. (dd-dan, MM-mesec, yyyy-godina)!", "Greška", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                Iznajmljivanje i = new Iznajmljivanje(datumIznajmljivanja, 0, 0, nacinPlacanja, z, o);
//                int id = 0;
//                try {
//                    Komunikacija.getInstance().kreirajIznajmljivanje(i);
//                    JOptionPane.showMessageDialog(fdi, "Sistem je kreirao iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
//
//                    List<Iznajmljivanje> iznajmljivanja = Komunikacija.getInstance().ucitajIznajmljivanja();
//
//                    for (Iznajmljivanje izn : iznajmljivanja) {
//                        if (izn.getUkupnoSati() == 0 && izn.getUkupanIznos() == 0) {
//                            id = izn.getIdIznajmljivanje();
//                            iznajmljivanjeGlavno = izn;
//                        }
//                    }
//
//                    Iznajmljivanje i1 = new Iznajmljivanje(id, datumIznajmljivanja, 0, 0, nacinPlacanja, z, o);
//                    
//                    
//                    fdi.getjLabelBrojSati().setVisible(true);
//                    fdi.getjTextFieldBrojSati().setVisible(true);
//                    fdi.getjLabelCena().setVisible(true);
//                    fdi.getjTextFieldCena().setVisible(true);
//                    fdi.getjLabelCenaPoSatu().setVisible(true);
//                    fdi.getjTextFieldCenaPoSatu().setVisible(true);
//                    fdi.getjLabelDatumPovratkaOpreme().setVisible(true);
//                    fdi.getjTextFieldDatumPovratkaOpreme().setVisible(true);
//                    fdi.getjLabelOprema().setVisible(true);
//                    fdi.getjComboBoxOprema().setVisible(true);
//                    fdi.getjLabelTipOpreme().setVisible(true);
//                    fdi.getjComboBoxTipOpreme().setVisible(true);
//                    fdi.getjLabelUkupanIznos().setVisible(true);
//                    fdi.getjTextFieldUkupanIznos().setVisible(true);
//                    fdi.getjLabelUkupnoSati().setVisible(true);
//                    fdi.getjTextFieldUkupnoSati().setVisible(true);
//                    fdi.getjSeparator1().setVisible(true);
//                    fdi.getjSeparator2().setVisible(true);
//                    fdi.getjSeparator3().setVisible(true);
//                    fdi.getjSeparator4().setVisible(true);
//                    fdi.getjButtonAzuriraj().setVisible(true);
//                    fdi.getjButtonDodaj().setVisible(true);
//                    fdi.getjButtonDodajStavku().setVisible(true);
//                    fdi.getjButtonPrikaziStavke().setVisible(true);
//                    fdi.setSize(779, 545);
//
//                    fdi.addWindowListener(new WindowAdapter() {
//                        @Override
//                        public void windowClosing(WindowEvent e) {
//                            try {
//                                Komunikacija.getInstance().obrisiIznajmljivanje(i1);
//                                System.out.println("Iznajmljivanje obrisano iz baze.");
//                            } catch (Exception ex) {
//                                System.err.println("Greška prilikom brisanja iznajmljivanja: " + ex.getMessage());
//                           }
//                       }
//                    });
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira osobu.", "Greska", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//
//        });
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

                StavkaIznajmljivanja si = new StavkaIznajmljivanja(null, brojSati, cena, satCena, datumPovratkaOpreme, so);

                try {
                    ukupanI += cena;
                    ukupnoS += brojSati;
                    fdi.getjTextFieldUkupanIznos().setText(String.format(Locale.US,"%.2f", ukupanI));
                    fdi.getjTextFieldUkupnoSati().setText(String.valueOf(ukupnoS));

                    lista.add(si);
                    ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(lista);
                    fdi.getjTableStavkeIznajmljivanja().setModel(mtsi);
                    System.out.println("Stavka je dodata u listu.");
                    JOptionPane.showMessageDialog(fdi, "Stavka iznajmljivanja je dodata.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    return;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira stavku iznajmljivanja.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        );

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


                    JOptionPane.showMessageDialog(fdi, "Sistem je kreirao iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdi, "Sistem ne moze da kreira iznajmljivanje.", "Greska", JOptionPane.ERROR_MESSAGE);
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

    public void azurirajStavkuIznajmljivanja(StavkaIznajmljivanja so) {
        stavkaZaAzuriranje = so;
    }

}
