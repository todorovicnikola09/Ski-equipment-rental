/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.mesto;

import domen.Mesto;
import domen.Osoba;
import forme.prikaz.FormaPrikazMesta;
import forme.prikaz.FormaPrikazOsoba;
import forme.modeltabele.ModelTabeleMesta;
import forme.modeltabele.ModelTabeleOsobe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazMestaController {

    private final FormaPrikazMesta pm;

    public PrikazMestaController(FormaPrikazMesta pm) {
        this.pm = pm;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pm.setVisible(true);
    }

    public void pripremiFormu() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        ModelTabeleMesta mtm = new ModelTabeleMesta(mesta);
        pm.getjTableMesta().setModel(mtm);
    }

    private void pripremiFormu2() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        if (mesta != null) {
            for (Mesto m : mesta) {
                //pm.getjComboBoxMesto().setSelectedItem(m);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Greška: Lista mesta nije učitana sa servera.");
        }

    }

    private void addActionListeners() {
        pm.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pm.getjTableMesta().getSelectedRow();
                System.out.println(red);
                if (red == -1) {
                    JOptionPane.showMessageDialog(pm, "Sistem ne moze da azurira mesto!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleMesta mtm = (ModelTabeleMesta) pm.getjTableMesta().getModel();
                    Mesto m = mtm.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Mesto", m);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniMesto();
                }

            }

        });

        pm.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pm.getjTableMesta().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pm, "Sistem ne moze da obrise mesto!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleMesta mtm = (ModelTabeleMesta) pm.getjTableMesta().getModel();
                    Mesto m = mtm.getLista().get(red);
                    
                    try {
                        String poruka = Komunikacija.getInstance().obrisiMesto(m);

                        if (poruka.length() != 0) {
                            JOptionPane.showMessageDialog(pm, poruka, "Greška", JOptionPane.ERROR_MESSAGE);
                            pripremiFormu();
                        } else {
                            JOptionPane.showMessageDialog(pm, "Sistem je uspesno obrisao mesto!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                            pripremiFormu();
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pm, "Došlo je do greške u komunikaciji sa serverom.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        pm.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = pm.getjTextFieldNaziv().getText().trim();

                ModelTabeleMesta mtm = (ModelTabeleMesta) pm.getjTableMesta().getModel();
                mtm.pretrazi(naziv);

            }
        });

        pm.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.getjTextFieldNaziv().setText("");
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
