/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.StavkaIznajmljivanja;
import forme.FormaPrikazStavkiIznajmljivanja;
import forme.ModelTabeleStavkeIznajmljivanja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Igor
 */
public class PrikazStavkiIznajmljivanjaController {

    private final FormaPrikazStavkiIznajmljivanja ptsi;
    private List<StavkaIznajmljivanja> stavke;

    public PrikazStavkiIznajmljivanjaController(FormaPrikazStavkiIznajmljivanja ptsi) {
        this.ptsi = ptsi;
        addActionListeners();
    }

    public void pripremiFormu(List<StavkaIznajmljivanja> lista) {
        stavke = lista;
        ModelTabeleStavkeIznajmljivanja mtsi = new ModelTabeleStavkeIznajmljivanja(stavke);
        ptsi.getjTableStavkeIznajmljivanja().setModel(mtsi);
//        
//        termini = komunikacija.Komunikacija.getInstance().ucitajTermineDezurstava();
//        if (termini != null) {
//            for (TerminDezurstva td : termini) {
//                String tipTermina = td.getTipTermina();
//                boolean postoji = false;
//                for (int i = 0; i < ptz.getjComboBoxSmena().getItemCount(); i++) {
//                    if (ptz.getjComboBoxSmena().getItemAt(i).equals(tipTermina)) {
//                        postoji = true;
//                        break;
//                    }
//                }
//                if (!postoji) {
//                    ptz.getjComboBoxSmena().addItem(tipTermina);
//                }
//            }
//            ptz.getjComboBoxSmena().setSelectedIndex(-1);
//        }
    }

    public void otvoriFormu(List<StavkaIznajmljivanja> lista) {
        pripremiFormu(lista);
        ptsi.setVisible(true);
    }

    private void addActionListeners() {
        ptsi.obrisiStavkuIznajmljivanjaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ptsi.getjTableStavkeIznajmljivanja().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ptsi, "Sistem ne moze da obrise stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleStavkeIznajmljivanja mtsi = (ModelTabeleStavkeIznajmljivanja) ptsi.getjTableStavkeIznajmljivanja().getModel();
                    StavkaIznajmljivanja si = mtsi.getLista().get(red);
                    Iterator<StavkaIznajmljivanja> iterator = stavke.iterator();
                    while (iterator.hasNext()) {
                        StavkaIznajmljivanja s = iterator.next();
                        if (s.equals(si)) { 
                            iterator.remove();
                            break;
                        }
                    }
                    pripremiFormu(stavke);
                    cordinator.Cordinator.getInstance().azurirajVrednosti();
                }

            }
        });
        
        ptsi.azurirajStavkuIznajmljivanjaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ptsi.getjTableStavkeIznajmljivanja().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ptsi, "Sistem ne moze da azurira stavku iznajmljivanja!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {

                    ModelTabeleStavkeIznajmljivanja mtsi = (ModelTabeleStavkeIznajmljivanja) ptsi.getjTableStavkeIznajmljivanja().getModel();
                    StavkaIznajmljivanja so = mtsi.getLista().get(red);
                    cordinator.Cordinator.getInstance().azurirajStavku(so);
                }

            }

            
        });
    }

}
