/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.modeltabele;

import domen.Iznajmljivanje;
import domen.Mesto;
import domen.Osoba;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleIznajmljivanja extends AbstractTableModel {

    List<Iznajmljivanje> lista;
    String[] kolone = {"Zaposleni", "Osoba", "Datum iznajmljivanja", "Ukupan broj sati", "Ukupan iznos"};

    public ModelTabeleIznajmljivanja(List<Iznajmljivanje> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Iznajmljivanje i = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return i.getIdZaposleni().getIme() + " " + i.getIdZaposleni().getPrezime();
            case 1:
                return i.getIdOsoba().getIme() + " " + i.getIdOsoba().getPrezime();
            case 2:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                return i.getDatumIznajmljivanja().format(formatter);
            case 3:
                return i.getUkupnoSati();
            case 4:
                return String.format(Locale.US, "%.2f", i.getUkupanIznos());
            case 5:
                return i.getNacinPlacanja();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Iznajmljivanje> getLista() {
        return lista;
    }

    public void setLista(List<Iznajmljivanje> lista) {
        this.lista = lista;
    }

    public Iznajmljivanje vratiIznajmljivanje(int selectedRow) {
        return lista.get(selectedRow);
    }

    public void pretrazi(String zaposleni, String osoba, LocalDate datumIznajmljivanja, String ukupanBrojSati, String ukupanIznos) {
        List<Iznajmljivanje> filteredList = lista.stream()
                .filter(z -> zaposleni == null || zaposleni.isEmpty() || (z.getIdZaposleni().getIme() + " " + z.getIdZaposleni().getPrezime()).toLowerCase().contains(zaposleni.toLowerCase()))
                .filter(z -> osoba == null || osoba.isEmpty() || (z.getIdOsoba().getIme() + " " + z.getIdOsoba().getPrezime()).toLowerCase().contains(osoba.toLowerCase()))
                .filter(z -> datumIznajmljivanja == null || z.getDatumIznajmljivanja().isEqual(datumIznajmljivanja))
                .filter(z -> ukupanBrojSati == null || ukupanBrojSati.isEmpty() || z.getUkupnoSati() == Integer.parseInt(ukupanBrojSati))
                .filter(z -> ukupanIznos == null || ukupanIznos.isEmpty() || String.valueOf(z.getUkupanIznos()).contains(ukupanIznos))
                .collect(Collectors.toList());;
        this.lista = filteredList;
        fireTableDataChanged();
    }

}
