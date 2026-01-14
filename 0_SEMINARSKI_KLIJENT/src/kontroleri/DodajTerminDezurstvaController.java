/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroleri;

import domen.TerminDezurstva;
import forme.FormaDodajTerminDezurstva;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Nikola
 */
public class DodajTerminDezurstvaController {

    private final FormaDodajTerminDezurstva fdtd;

    public DodajTerminDezurstvaController(FormaDodajTerminDezurstva fdtd) {
        this.fdtd = fdtd;
        addActionListener();
        fdtd.getjTextFieldVremeOd().setEnabled(false);
        fdtd.getjTextFieldVremeDo().setEnabled(false);
    }

    private void addActionListener() {

        fdtd.getjRadioButtonPrvaSmena().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fdtd.getjRadioButtonPrvaSmena().isSelected()) {
                    fdtd.getjTextFieldVremeOd().setEnabled(true);
                    fdtd.getjTextFieldVremeDo().setEnabled(true);
                    fdtd.getjRadioButtonDrugaSmena().setEnabled(false);
                    fdtd.getjRadioButtonMedjusmena().setEnabled(false);
                    fdtd.getjLabelSmena().setVisible(true);
                    fdtd.getjLabelSmena().setText("<html>Prva smena mora poceti u periodu od 6h-16h.<br>Smena mora trajati 8 sati.</html>");

                    fdtd.getjTextFieldVremeOd().setText("06:00 - 08:00");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                    fdtd.getjTextFieldVremeDo().setText("14:00 - 16:00");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);

                    fdtd.getjTextFieldVremeOd().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().equals("06:00 - 08:00")) {
                                fdtd.getjTextFieldVremeOd().setText("");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeOd().setText("06:00 - 08:00");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                            }
                        }
                    });

                    fdtd.getjTextFieldVremeDo().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().equals("14:00 - 16:00")) {
                                fdtd.getjTextFieldVremeDo().setText("");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeDo().setText("14:00 - 16:00");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);
                            }
                        }
                    });

                } else {
                    fdtd.getjTextFieldVremeOd().setEnabled(false);
                    fdtd.getjTextFieldVremeDo().setEnabled(false);
                    fdtd.getjRadioButtonDrugaSmena().setEnabled(true);
                    fdtd.getjRadioButtonMedjusmena().setEnabled(true);
                    fdtd.getjLabelSmena().setText(" ");

                    fdtd.getjTextFieldVremeOd().setText("");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                    fdtd.getjTextFieldVremeDo().setText("");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                }
            }
        });

        fdtd.getjRadioButtonDrugaSmena().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fdtd.getjRadioButtonDrugaSmena().isSelected()) {
                    fdtd.getjTextFieldVremeOd().setEnabled(true);
                    fdtd.getjTextFieldVremeDo().setEnabled(true);
                    fdtd.getjRadioButtonPrvaSmena().setEnabled(false);
                    fdtd.getjRadioButtonMedjusmena().setEnabled(false);
                    fdtd.getjLabelSmena().setVisible(true);
                    fdtd.getjLabelSmena().setText("<html>Druga smena mora poceti u periodu od 14h-00h.<br>Smena mora trajati 8 sati.</html>");

                    fdtd.getjTextFieldVremeOd().setText("14:00 - 16:00");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                    fdtd.getjTextFieldVremeDo().setText("22:00 - 00:00");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);

                    fdtd.getjTextFieldVremeOd().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().equals("14:00 - 16:00")) {
                                fdtd.getjTextFieldVremeOd().setText("");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeOd().setText("14:00 - 16:00");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                            }
                        }
                    });

                    fdtd.getjTextFieldVremeDo().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().equals("22:00 - 00:00")) {
                                fdtd.getjTextFieldVremeDo().setText("");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeDo().setText("22:00 - 00:00");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);
                            }
                        }
                    });

                } else if (!fdtd.getjRadioButtonDrugaSmena().isSelected()) {
                    fdtd.getjTextFieldVremeOd().setEnabled(false);
                    fdtd.getjTextFieldVremeDo().setEnabled(false);
                    fdtd.getjRadioButtonPrvaSmena().setEnabled(true);
                    fdtd.getjRadioButtonMedjusmena().setEnabled(true);
                    fdtd.getjLabelSmena().setText(" ");

                    fdtd.getjTextFieldVremeOd().setText("");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                    fdtd.getjTextFieldVremeDo().setText("");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                }

            }
        }
        );

        fdtd.getjRadioButtonMedjusmena().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fdtd.getjRadioButtonMedjusmena().isSelected()) {
                    fdtd.getjTextFieldVremeOd().setEnabled(true);
                    fdtd.getjTextFieldVremeDo().setEnabled(true);
                    fdtd.getjRadioButtonPrvaSmena().setEnabled(false);
                    fdtd.getjRadioButtonDrugaSmena().setEnabled(false);
                    fdtd.getjLabelSmena().setVisible(true);
                    fdtd.getjLabelSmena().setText("<html>Medjusmena mora poceti u periodu od 10h-20h.<br>Smena mora trajati 8 sati.</html>");

                    fdtd.getjTextFieldVremeOd().setText("10:00 - 12:00");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                    fdtd.getjTextFieldVremeDo().setText("18:00 - 20:00");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);

                    fdtd.getjTextFieldVremeOd().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().equals("10:00 - 12:00")) {
                                fdtd.getjTextFieldVremeOd().setText("");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeOd().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeOd().setText("10:00 - 12:00");
                                fdtd.getjTextFieldVremeOd().setForeground(Color.GRAY);
                            }
                        }
                    });

                    fdtd.getjTextFieldVremeDo().addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().equals("18:00 - 20:00")) {
                                fdtd.getjTextFieldVremeDo().setText("");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent evt) {
                            if (fdtd.getjTextFieldVremeDo().getText().isEmpty()) {
                                fdtd.getjTextFieldVremeDo().setText("18:00 - 20:00");
                                fdtd.getjTextFieldVremeDo().setForeground(Color.GRAY);
                            }
                        }
                    });
                } else if (!fdtd.getjRadioButtonMedjusmena().isSelected()) {
                    fdtd.getjTextFieldVremeOd().setEnabled(false);
                    fdtd.getjTextFieldVremeDo().setEnabled(false);
                    fdtd.getjRadioButtonPrvaSmena().setEnabled(true);
                    fdtd.getjRadioButtonDrugaSmena().setEnabled(true);
                    fdtd.getjLabelSmena().setText(" ");

                    fdtd.getjTextFieldVremeOd().setText("");
                    fdtd.getjTextFieldVremeOd().setForeground(Color.BLACK);
                    fdtd.getjTextFieldVremeDo().setText("");
                    fdtd.getjTextFieldVremeDo().setForeground(Color.BLACK);
                }

            }
        }
        );

        fdtd.dodajTerminDezurstvaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodaj(e);
            }

            private void dodaj(ActionEvent e) {
                
                String tipTermina = null;
                String vremeOd1 = fdtd.getjTextFieldVremeOd().getText();
                String vremeDo1 = fdtd.getjTextFieldVremeDo().getText();
                
                String[] vremena = {"06:00 - 08:00", "10:00 - 12:00", "14:00 - 16:00", "22:00 - 00:00"};

                for (String vreme : vremena) {
                    if (vremeOd1.equals(vreme) || vremeDo1.equals(vreme)) {
                        JOptionPane.showMessageDialog(fdtd, "Morate uneti oba vremena (Od i Do).", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (vremeOd1.isEmpty() || vremeDo1.isEmpty()) {
                    JOptionPane.showMessageDialog(fdtd, "Morate uneti oba vremena (Od i Do).", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(fdtd.getjRadioButtonPrvaSmena().isSelected()){
                    tipTermina = "Prva smena";
                }
                else if(fdtd.getjRadioButtonDrugaSmena().isSelected()){
                    tipTermina = "Druga smena";
                }
                else if(fdtd.getjRadioButtonMedjusmena().isSelected()){
                    tipTermina = "Medjusmena";
                }
                else{
                    JOptionPane.showMessageDialog(fdtd, "Niste oznacili smenu.", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime vremeOd = null;
                LocalTime vremeDo = null;
                try {
                    vremeOd = LocalTime.parse(vremeOd1, format);
                    vremeDo = LocalTime.parse(vremeDo1, format);

                    if (vremeOd.isAfter(vremeDo)) {
                        JOptionPane.showMessageDialog(fdtd, "Vreme 'Od' ne može biti posle vremena 'Do'.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (vremeOd.plusHours(8).isAfter(vremeDo)) {
                        JOptionPane.showMessageDialog(fdtd, "Vreme 'Od' mora biti najmanje 8 sati pre vremena 'Do'.", "Greška", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (Komunikacija.getInstance().postojiTermin(vremeOd, vremeDo)) {
                        JOptionPane.showMessageDialog(fdtd, "Termin dezurstva vec postoji!", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtd, "Unesite vreme u formatu HH:mm (HH-sati, mm-minuti)!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TerminDezurstva td = new TerminDezurstva(-1, tipTermina, vremeOd, vremeDo);

                try {
                    Komunikacija.getInstance().dodajTerminDezurstva(td);
                    JOptionPane.showMessageDialog(fdtd, "Sistem je kreirao termin dezurstva.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    fdtd.setVisible(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(fdtd, "Sistem ne moze da kreira termin dezurstva.", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

    }

    public void otvoriFormu() {
        fdtd.setVisible(true);
    }

}
