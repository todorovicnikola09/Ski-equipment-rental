/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;


import domen.TerminDezurstva;
import domen.ZapTermin;
import forme.FormaPrikazTerminaZaposlenog;
import forme.ModelTabeleTerminZaposlenih;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class PrikazTerminaZaposlenogController {
    private final FormaPrikazTerminaZaposlenog ptz;
    private List<TerminDezurstva> termini;
    
    public PrikazTerminaZaposlenogController(FormaPrikazTerminaZaposlenog ptz) {
        this.ptz = ptz;
        addActionListeners();
    }

    public void pripremiFormu() {
        List<ZapTermin> terminZaposlenih = komunikacija.Komunikacija.getInstance().ucitajTermineZaposlenih();
        ModelTabeleTerminZaposlenih mttz = new ModelTabeleTerminZaposlenih(terminZaposlenih);
        ptz.getjTableTerminiZaposlenih().setModel(mttz);
        
        termini = komunikacija.Komunikacija.getInstance().ucitajTermineDezurstava();
        if (termini != null) {
            for (TerminDezurstva td : termini) {
                String tipTermina = td.getTipTermina();
                boolean postoji = false;
                for (int i = 0; i < ptz.getjComboBoxSmena().getItemCount(); i++) {
                    if (ptz.getjComboBoxSmena().getItemAt(i).equals(tipTermina)) {
                        postoji = true;
                        break;
                    }
                }
                if (!postoji) {
                    ptz.getjComboBoxSmena().addItem(tipTermina);
                }
            }
            ptz.getjComboBoxSmena().setSelectedIndex(-1);
        }
    }

    public void otvoriFormu() {
        pripremiFormu();
        ptz.setVisible(true);
    }

    private void addActionListeners() {
        ptz.jButtonObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ptz.getjTableTerminiZaposlenih().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ptz, "Sistem ne moze da obrise termin zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleTerminZaposlenih mttz = (ModelTabeleTerminZaposlenih) ptz.getjTableTerminiZaposlenih().getModel();
                    ZapTermin zt = mttz.getLista().get(red);
                    try {
                        Komunikacija.getInstance().obrisiTerminZaposlenog(zt);
                        JOptionPane.showMessageDialog(null, "Sistem je obrisao termin zaposlenog.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Sistem ne moze da obrise termin zaposlenog.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
        
        ptz.jButtonAzurirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = ptz.getjTableTerminiZaposlenih().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(ptz, "Sistem ne moze da azurira zaposlenog!", "Greska", JOptionPane.ERROR_MESSAGE);
                } else {
                    ModelTabeleTerminZaposlenih mttz = (ModelTabeleTerminZaposlenih) ptz.getjTableTerminiZaposlenih().getModel();
                    ZapTermin zt = mttz.getLista().get(red);
                    cordinator.Cordinator.getInstance().dodajParam("Termin zaposlenog", zt);
                    cordinator.Cordinator.getInstance().otvoriFormuIzmeniTerminZaposlenog();
                }

            }

            
        });
        
        ptz.jButtonPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = ptz.getjTextFieldIme().getText().trim();
                String prezime = ptz.getjTextFieldPrezime().getText().trim();
                String termin = (String) ptz.getjComboBoxSmena().getSelectedItem();
                String datum1 = ptz.getjTextFieldDatumRada().getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumRada = null;

                if (!datum1.isEmpty()) {
                    try {
                        datumRada = LocalDate.parse(datum1, formatter);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(ptz, "Pogresan format datuma.", "GRESKA", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Neispravan format datuma: " + datum1);

                        return;
                    }
                }
                
                ModelTabeleTerminZaposlenih mttz = (ModelTabeleTerminZaposlenih) ptz.getjTableTerminiZaposlenih().getModel();
                mttz.pretrazi(ime, prezime, termin, datumRada);

            }
        });

        ptz.jButtonResetujPretraguActionListener(new ActionListener() {
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
