/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;


import domen.Zaposleni;
import forme.FormaMod;
import forme.FormaPrikazZaposlenih;
import forme.ModelTabeleZaposleni;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazZaposlenihController {
    
    private final FormaPrikazZaposlenih pz;

    public PrikazZaposlenihController(FormaPrikazZaposlenih pz) {
        this.pz = pz;
        addActionListener();
    }

    

    public void pripremiFormu() {
        List<Zaposleni> osobe = komunikacija.Komunikacija.getInstance().ucitajZaposlene();
        ModelTabeleZaposleni mtz = new ModelTabeleZaposleni(osobe);
        pz.getjTableZaposleni().setModel(mtz);
    }
    
    public void otvoriFormu() {
        pripremiFormu();
        pz.setVisible(true);
    }

    private void addActionListener() {
        pz.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pz.getjTableZaposleni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pz, "Sistem ne moze da azurira zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleZaposleni mtz = (ModelTabeleZaposleni) pz.getjTableZaposleni().getModel();
                    Zaposleni z = mtz.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Zaposleni", z);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniZaposlenog();
                }

            }

            
        });

        pz.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pz.getjTableZaposleni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pz, "Sistem ne moze da obrise zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleZaposleni mtz = (ModelTabeleZaposleni) pz.getjTableZaposleni().getModel();
                    Zaposleni z = mtz.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiZaposlenog(z);
                        JOptionPane.showMessageDialog(null, "Sistem je obrisao zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Sistem ne moze da obrise zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        
        pz.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pz.getjTextFieldIme().getText().trim();
                String prezime = pz.getjTextFieldPrezime().getText().trim();
                String email = pz.getjTextFieldEmail().getText().trim();
                String username = pz.getjTextFieldUsername().getText().trim();
                
                ModelTabeleZaposleni mtz = (ModelTabeleZaposleni) pz.getjTableZaposleni().getModel();
                mtz.pretrazi(ime, prezime, email, username);

            }
        });

        pz.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

    
}
