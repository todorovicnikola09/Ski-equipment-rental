/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri.termindezurstva;

import domen.TerminDezurstva;
import forme.prikaz.FormaPrikazTerminaDezurstva;
import forme.modeltabele.ModelTabeleTerminDezurstva;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazTerminaDezurstvaController {

    private final FormaPrikazTerminaDezurstva ptd;

    public PrikazTerminaDezurstvaController(FormaPrikazTerminaDezurstva ptd) {
        this.ptd = ptd;
        addActionListeners();
    }

    public void pripremiFormu() {
        List<TerminDezurstva> terminDezurstva = komunikacija.Komunikacija.getInstance().ucitajTermineDezurstava();
        ModelTabeleTerminDezurstva mttd = new ModelTabeleTerminDezurstva(terminDezurstva);
        ptd.getjTableTerminDezurstva().setModel(mttd);
    }

    public void otvoriFormu() {
        pripremiFormu();
        ptd.setVisible(true);
    }

    private void addActionListeners() {
        ptd.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ptd.getjTableTerminDezurstva().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ptd, "Sistem ne moze da obrise termin dezurstva!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleTerminDezurstva mttd = (ModelTabeleTerminDezurstva) ptd.getjTableTerminDezurstva().getModel();
                    TerminDezurstva td = mttd.getLista().get(red);

                    int potvrda = JOptionPane.showConfirmDialog(
                            ptd,
                            "Da li ste sigurni da želite da obrišete ovaj termin dezurstva?",
                            "Potvrda brisanja",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (potvrda == JOptionPane.YES_OPTION) {
                        try {
                            String poruka = Komunikacija.getInstance().obrisiTerminDezurstva(td);

                            if (poruka != null && !poruka.isEmpty()) {
                                JOptionPane.showMessageDialog(
                                        ptd,
                                        poruka,
                                        "Greška",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            } else {
                                JOptionPane.showMessageDialog(
                                        ptd,
                                        "Sistem je obrisao termin dezurstva.",
                                        "Obaveštenje",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                pripremiFormu();
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(
                                    ptd,
                                    "Došlo je do greške u komunikaciji sa serverom.",
                                    "GRESKA",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        }
                    }
                }

            }
        });

        ptd.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipTermina = ptd.getjTextFieldTipTermina().getText().trim();

                ModelTabeleTerminDezurstva mtso = (ModelTabeleTerminDezurstva) ptd.getjTableTerminDezurstva().getModel();
                mtso.pretrazi(tipTermina);

            }
        });

        ptd.jButtonResetujPretraguActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ptd.getjTextFieldTipTermina().setText("");
                pripremiFormu();
            }
        });
    }

}
