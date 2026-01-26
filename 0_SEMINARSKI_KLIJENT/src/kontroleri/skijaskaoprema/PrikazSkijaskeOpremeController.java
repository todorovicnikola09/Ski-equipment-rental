/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.skijaskaoprema;

import domen.SkijaskaOprema;
import forme.prikaz.FormaPrikazSkijaskeOpreme;
import forme.modeltabele.ModelTabeleSkijaskaOprema;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import java.sql.*;

/**
 *
 * @author Nikola
 */
public class PrikazSkijaskeOpremeController {

    private final FormaPrikazSkijaskeOpreme pso;

    public PrikazSkijaskeOpremeController(FormaPrikazSkijaskeOpreme pso) {
        this.pso = pso;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pso.setVisible(true);
    }

    public void pripremiFormu() {
        List<SkijaskaOprema> skijaskaOprema = komunikacija.Komunikacija.getInstance().ucitajSkijaskuOpremu();
        ModelTabeleSkijaskaOprema mtso = new ModelTabeleSkijaskaOprema(skijaskaOprema);
        pso.getjTableSkijaskaOprema().setModel(mtso);
    }

    private void addActionListeners() {
        pso.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pso.getjTableSkijaskaOprema().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pso, "Sistem ne moze da azurira osobu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleSkijaskaOprema mtso = (ModelTabeleSkijaskaOprema) pso.getjTableSkijaskaOprema().getModel();
                    SkijaskaOprema so = mtso.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Skijaska oprema", so);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniSkijaskuOpremu();
                }

            }

        });

        pso.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pso.getjTableSkijaskaOprema().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pso, "Sistem ne moze da obrise skijasku opremu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleSkijaskaOprema mtso = (ModelTabeleSkijaskaOprema) pso.getjTableSkijaskaOprema().getModel();
                    SkijaskaOprema so = mtso.getLista().get(red);
                    try {

                        String poruka = Komunikacija.getInstance().obrisiSkijaskuOpremu(so);

                        if (poruka.length() != 0) {

                            JOptionPane.showMessageDialog(pso,poruka,"Greška",JOptionPane.ERROR_MESSAGE);
                            pripremiFormu();
                        } else {
                            JOptionPane.showMessageDialog(pso,"Sistem je uspesno obrisao skijasku opremu!","Obaveštenje",JOptionPane.INFORMATION_MESSAGE);
                            pripremiFormu();
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pso,"Došlo je do greške u komunikaciji sa serverom.","GRESKA",JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        }
        );

        pso.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = pso.getjTextFieldNaziv().getText().trim();
                String tipOpreme = pso.getjTextFieldTipOpreme().getText().trim();

                ModelTabeleSkijaskaOprema mtso = (ModelTabeleSkijaskaOprema) pso.getjTableSkijaskaOprema().getModel();
                mtso.pretrazi(naziv, tipOpreme);

            }
        });

        pso.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pso.getjTextFieldNaziv().setText("");
                pso.getjTextFieldTipOpreme().setText("");
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
