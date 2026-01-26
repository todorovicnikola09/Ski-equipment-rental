/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.skijaskaoprema;

import com.sun.source.tree.ContinueTree;
import cordinator.Cordinator;
import domen.SkijaskaOprema;
import forme.dodaj.FormaDodajSkijaskuOpremu;
import forme.FormaMod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajSkijaskuOpremuController {

    private final FormaDodajSkijaskuOpremu fds;
    SkijaskaOprema skiBaza;

    public DodajSkijaskuOpremuController(FormaDodajSkijaskuOpremu fds) {
        this.fds = fds;
        addActionListener();

    }

    private void pripremiFormu2(FormaMod mod) {
        switch (mod) {
            case DODAJ:
                fds.getjButtonAzuriraj().setVisible(false);
                fds.getjButtonDodaj().setVisible(true);
                fds.getjButtonDodaj().setEnabled(true);
                fds.getjTextFieldID().setVisible(false);
                fds.getjLabelID().setVisible(false);
                break;
            case IZMENI:
                fds.getjButtonAzuriraj().setVisible(true);
                fds.getjButtonAzuriraj().setEnabled(true);
                fds.getjButtonDodaj().setVisible(false);
                fds.getjLabelID().setVisible(false);
                fds.getjTextFieldID().setVisible(false);
                SkijaskaOprema so = (SkijaskaOprema) Cordinator.getInstance().vratiParam("Skijaska oprema");
                fds.getjTextFieldID().setText(so.getIdSkiOprema() + "");
                fds.getjTextFieldID().setEnabled(false);
                fds.getjComboBoxNaziv().setSelectedItem(so.getNaziv());
                fds.getjTextFieldCenaPoSatu().setText(so.getSatCena() + "");
                fds.getjComboBoxTipSkijaskeOpreme().setSelectedItem(so.getTipOprema());
                skiBaza = new SkijaskaOprema(so.getIdSkiOprema(), so.getNaziv(), so.getSatCena(), so.getTipOprema());
                
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addActionListener() {
        fds.dodajSkijaskuOpremuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                String naziv;
                try {
                    naziv = fds.getjComboBoxNaziv().getSelectedItem().toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati naziv!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String satCena = fds.getjTextFieldCenaPoSatu().getText();
                String tipOpreme;
                try {
                    tipOpreme = fds.getjComboBoxTipSkijaskeOpreme().getSelectedItem().toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Morate odabrati tip opreme!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double satC = 0;

                try {
                    satC = Double.parseDouble(satCena);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Molimo unesite broj za cenu!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<SkijaskaOprema> skiBaza = Komunikacija.getInstance().ucitajSkijaskuOpremu();
                for (SkijaskaOprema skijaskaOprema : skiBaza) {
                    if (skijaskaOprema.getNaziv().equals(naziv) && skijaskaOprema.getTipOprema().equals(tipOpreme)) {
                        JOptionPane.showMessageDialog(fds, "Takva oprema vec postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                SkijaskaOprema so = new SkijaskaOprema(-1, naziv, satC, tipOpreme);

                try {
                    Komunikacija.getInstance().dodajSkijaskuOpremu(so);
                    JOptionPane.showMessageDialog(fds, "Sistem je kreirao skijasku opremu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fds.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fds, "Sistem ne moze da kreira skijasku opremu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        fds.getjComboBoxNaziv().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if ("Skije".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Alpske skije");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Nordijske skije");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Freeride/Freestyle skije");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Snowboard oprema".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Snowboard daske");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Snowboard vezovi");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Snowboard čizme");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Obuca".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Pancerice");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Cizme za nordijsko skijanje");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Snowboard čizme");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Odeca".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Skijaske jakne");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Pantalone");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Termo odeća");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Rukavice");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Carape");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Zastitna oprema".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Kacige");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Stitnici za ledja");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Stitnici za kolena");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Naočare za skijanje/snowboard");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Pribor".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Stapovi za skijanje");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Torbe za opremu");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Lavinski setovi");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if ("Oprema za odrzavanje".equals(fds.getjComboBoxNaziv().getSelectedItem())) {
                    fds.getjComboBoxTipSkijaskeOpreme().removeAllItems();
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Vosak za skije/snowboard");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Strugalice");
                    fds.getjComboBoxTipSkijaskeOpreme().addItem("Setovi za popravku");
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(true);
                    fds.getjComboBoxTipSkijaskeOpreme().setSelectedIndex(-1);
                }
                if (fds.getjComboBoxNaziv().getSelectedIndex() == -1) {
                    fds.getjComboBoxTipSkijaskeOpreme().setEnabled(false);
                }
            }

        });

        fds.azurirajSkijaskuOpremuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(fds.getjTextFieldID().getText());
                String naziv;
                try {
                    naziv = fds.getjComboBoxNaziv().getSelectedItem().toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Molimo odaberite naziv!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String satCena = fds.getjTextFieldCenaPoSatu().getText();
                String tipOpreme;
                try {
                    tipOpreme = fds.getjComboBoxTipSkijaskeOpreme().getSelectedItem().toString();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Molimo odaberite tip opreme!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double satC = 0;

                try {
                    satC = Double.parseDouble(satCena);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Molimo unesite ispravan broj!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                SkijaskaOprema so = new SkijaskaOprema(id, naziv, satC, tipOpreme);
                List<SkijaskaOprema> skiUcitane = Komunikacija.getInstance().ucitajSkijaskuOpremu();
                
                if(skiBaza.equals(so)){
                    
                }else{
                    for (SkijaskaOprema skijaskaOprema : skiUcitane) {
                    if (skijaskaOprema.getNaziv().equals(naziv) && skijaskaOprema.getTipOprema().equals(tipOpreme)) {
                        JOptionPane.showMessageDialog(fds, "Takva oprema vec postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                }

                

                try {
                    Komunikacija.getInstance().azurirajSkijaskuOpremu(so);
                    JOptionPane.showMessageDialog(fds, "Sistem je azurirao skijasku opremu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fds.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fds, "Sistem ne moze da azurira skijasku opremu.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            

        });
    }
//
//    public void otvoriFormu(FormaMod formaMod) {
//        pripremiFormu(formaMod);
//        fdm.setVisible(true);
//    }
//
//    private void pripremiFormu(FormaMod mod) {
//        switch (mod) {
//            case DODAJ:
//                fdm.getjButtonAzuriraj().setVisible(false);
//                fdm.getjButtonDodaj().setVisible(true);
//                fdm.getjButtonDodaj().setEnabled(true);
//                fdm.getjTextFieldID().setVisible(false);
//                fdm.getjLabelID().setVisible(false);
//                break;
//            case IZMENI:
//                fdm.getjButtonAzuriraj().setVisible(true);
//                fdm.getjButtonAzuriraj().setEnabled(true);
//                fdm.getjButtonDodaj().setVisible(false);
//                Mesto m = (Mesto) Cordinator.getInstance().vratiParam("Mesto");
//                fdm.getjTextFieldID().setText(m.getIdMesto()+ "");
//                fdm.getjTextFieldID().setEnabled(false);
//                fdm.getjTextFieldNaziv().setText(m.getNaziv());
//                
//
//                break;
//            default:
//                throw new AssertionError();
//        }
//     }

    public void otvoriFormu(FormaMod mod) {
        pripremiFormu2(mod);
        fds.setVisible(true);
    }

}
