/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.iznajmljivanje;

import domen.Iznajmljivanje;
import domen.StavkaIznajmljivanja;
import forme.prikaz.FormaPrikaziIznajmljivanje;
import forme.modeltabele.ModelTabeleIznajmljivanja;
import forme.modeltabele.ModelTabeleStavkeIznajmljivanja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazIznajmljivanjaController {

    private final FormaPrikaziIznajmljivanje pic;
    List<Iznajmljivanje> iznajmljivanja = komunikacija.Komunikacija.getInstance().ucitajIznajmljivanja();
    Iznajmljivanje selektovanoIznajmljivanje;
    List<StavkaIznajmljivanja> stavke = new ArrayList<>();

    public PrikazIznajmljivanjaController(FormaPrikaziIznajmljivanje pic) {
        this.pic = pic;
        pic.getjButtonAzurirajIznajmljivanje().setEnabled(false);
        pic.getjButtonObrisiStavkuIznajmljivanja().setEnabled(false);
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pic.setVisible(true);
    }

    public void pripremiFormu() {
        List<Iznajmljivanje> iznajmljivanje = komunikacija.Komunikacija.getInstance().ucitajIznajmljivanja();
        ModelTabeleIznajmljivanja mti = new ModelTabeleIznajmljivanja(iznajmljivanje);
        pic.getjTableIznajmljivanja().setModel(mti);

        ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(new ArrayList<>());
        pic.getjTableStavkeIznajmljivanja().setModel(mtsi);
    }

    private void addActionListeners() {

        pic.jTableIznajmljivanjeMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = pic.getjTableIznajmljivanja().getSelectedRow();
                    if (selectedRow >= 0) {
                        ModelTabeleIznajmljivanja mti = (ModelTabeleIznajmljivanja) pic.getjTableIznajmljivanja().getModel();
                        selektovanoIznajmljivanje = mti.vratiIznajmljivanje(selectedRow);

                        stavke = selektovanoIznajmljivanje.getLista();
                        if (stavke == null) {
                            stavke = new ArrayList<>();
                        }
                        ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(stavke);
                        pic.getjTableStavkeIznajmljivanja().setModel(mtsi);
                    }
                }

            }
        }
        );

//        pic.jButtonObrisiIznajmljivanjeActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int red = pic.getjTableIznajmljivanja().getSelectedRow();
//                if (red == -1) {
//                    JOptionPane.showMessageDialog(pic, "Sistem ne moze da obrise iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    ModelTabeleIznajmljivanja mti = (ModelTabeleIznajmljivanja) pic.getjTableIznajmljivanja().getModel();
//                    selektovanoIznajmljivanje = mti.getLista().get(red);
//
//                    try {
//                        Komunikacija.getInstance().obrisiIznajmljivanje(selektovanoIznajmljivanje);
//                        JOptionPane.showMessageDialog(null, "Sistem je obrisao iznajmljivanje i sve njene stavke.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
//                        pripremiFormu();
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(null, "Sistem ne moze da obrise iznajmljivanje.", "Greska", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//
//        });
        pic.jButtonObrisiStavkuIznajmljivanjaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pic.getjTableStavkeIznajmljivanja().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pic, "Sistem ne moze da obrise stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    try {
                        ModelTabeleStavkeIznajmljivanja mtsi = (ModelTabeleStavkeIznajmljivanja) pic.getjTableStavkeIznajmljivanja().getModel();
                        StavkaIznajmljivanja si = mtsi.getLista().get(red);
                        if(selektovanoIznajmljivanje.getLista().size()==1){
                            JOptionPane.showMessageDialog(pic, "Ne mozete obrisati stavku iznajmljivanja ukoliko je ona jedina u iznajmljivanju.", "Greska", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        selektovanoIznajmljivanje.getLista().remove(si);
                        
                        int ukupnoSati = 0;
                        double ukupnoCena = 0;
                        for (StavkaIznajmljivanja s : selektovanoIznajmljivanje.getLista()) {
                            ukupnoSati += s.getBrojSati();
                            ukupnoCena += s.getCena();
                        }
                        selektovanoIznajmljivanje.setUkupnoSati(ukupnoSati);
                        selektovanoIznajmljivanje.setUkupanIznos(ukupnoCena);
                        
                        Komunikacija.getInstance().azurirajIznajmljivanje(selektovanoIznajmljivanje);
                        JOptionPane.showMessageDialog(null, "Sistem je obrisao stavku iznajmljivanja.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        mtsi.fireTableDataChanged();
                        ((ModelTabeleIznajmljivanja) pic.getjTableIznajmljivanja().getModel()).fireTableDataChanged();;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Sistem ne moze da obrise stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        });

        pic.jButtonAzurirajIznajmljivanjeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pic.getjTableIznajmljivanja().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pic, "Sistem ne moze da nadje iznajmljivanje!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleIznajmljivanja mti = (ModelTabeleIznajmljivanja) pic.getjTableIznajmljivanja().getModel();
                    Iznajmljivanje i = mti.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Iznajmljivanje", i);
                    JOptionPane.showMessageDialog(pic, "Sistem je nasao iznajmljivanje.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniIznajmljivanje();
                    pic.dispose();
                }
            }
        });

        pic.jButtonPretraziIznajmljivanjeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String zaposleni = pic.getjTextFieldZaposleni().getText().trim();
                String osoba = pic.getjTextFieldOsoba().getText().trim();
                String datumIznajmljivanja = pic.getjTextFieldDatumIznajmljivanja().getText().trim();
                String ukupanBrojSati = pic.getjTextFieldUkupanBrojSati().getText().trim();
                String ukupanIznos = pic.getjTextFieldUkupanIznos().getText().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumIz = null;

                if (!datumIznajmljivanja.isEmpty()) {
                    try {
                        datumIz = LocalDate.parse(datumIznajmljivanja, formatter);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(pic, "Pogresan format datuma.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Neispravan format datuma: " + datumIznajmljivanja);
                        return;
                    }
                }

                ModelTabeleIznajmljivanja mti = (ModelTabeleIznajmljivanja) pic.getjTableIznajmljivanja().getModel();
                mti.pretrazi(zaposleni, osoba, datumIz, ukupanBrojSati, ukupanIznos);
                if (mti.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(pic, "Sistem ne moze da nadje iznajmljivanja po zadatim kriterijumima!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(pic, "Sistem je našao iznajmljivanja po zadatim kriterijumima.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    pic.getjButtonAzurirajIznajmljivanje().setEnabled(true);
                    pic.getjButtonObrisiStavkuIznajmljivanja().setEnabled(true);
                }

            }
        });

        pic.jButtonResetujPretraguIznajmljivanjaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
                pic.getjTextFieldZaposleni().setText("");
                pic.getjTextFieldOsoba().setText("");
                pic.getjTextFieldDatumIznajmljivanja().setText("");
                pic.getjTextFieldUkupanBrojSati().setText("");
                pic.getjTextFieldUkupanIznos().setText("");
            }
        });

    }

    public void osveziFormu() {
        List<Iznajmljivanje> iznajmljivanje = komunikacija.Komunikacija.getInstance().ucitajIznajmljivanja();
        ModelTabeleIznajmljivanja mti = new ModelTabeleIznajmljivanja(iznajmljivanje);
        pic.getjTableIznajmljivanja().setModel(mti);

        ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(stavke);
        pic.getjTableStavkeIznajmljivanja().setModel(mtsi);
        pic.getjTextFieldZaposleni().setText("");
        pic.getjTextFieldOsoba().setText("");
        pic.getjTextFieldDatumIznajmljivanja().setText("");
        pic.getjTextFieldUkupanBrojSati().setText("");
        pic.getjTextFieldUkupanIznos().setText("");
        pic.getjButtonAzurirajIznajmljivanje().setEnabled(false);
        pic.getjButtonObrisiStavkuIznajmljivanja().setEnabled(false);
    }


}
