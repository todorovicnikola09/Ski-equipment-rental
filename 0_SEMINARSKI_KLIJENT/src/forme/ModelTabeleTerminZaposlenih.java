/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.ZapTermin;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleTerminZaposlenih extends AbstractTableModel {

    List<ZapTermin> lista;
    String[] kolone = {"Ime","Prezime","Smena","Vreme pocetka smene","Vreme zavrsetka smene","Datum radnog dana"};

    public ModelTabeleTerminZaposlenih(List<ZapTermin> lista) {
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
        ZapTermin zt = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return zt.getIdZaposleni().getIme();
            case 1:
                return zt.getIdZaposleni().getPrezime();
            case 2:
                return zt.getIdTerminDezurstva().getTipTermina();
            case 3:
                return zt.getIdTerminDezurstva().getVremeOd();
            case 4:
                return zt.getIdTerminDezurstva().getVremeDo();
            case 5:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                return zt.getDatumRada().format(formatter);
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<ZapTermin> getLista() {
        return lista;
    }

    public void setLista(List<ZapTermin> lista) {
        this.lista = lista;
    }

    public void pretrazi(String ime, String prezime, String tipTermina, LocalDate datumRada) {
        List<ZapTermin> filteredList = lista.stream()
                .filter(zt -> tipTermina == null || tipTermina.isEmpty() || zt.getIdTerminDezurstva().getTipTermina().toLowerCase().contains(tipTermina.toLowerCase()))
                .filter(zt -> ime == null || ime.isEmpty() || zt.getIdZaposleni().getIme().toLowerCase().contains(ime.toLowerCase()))
                .filter(zt -> prezime == null || prezime.isEmpty() || zt.getIdZaposleni().getPrezime().toLowerCase().contains(prezime.toLowerCase()))
                .filter(zt -> datumRada == null || zt.getDatumRada().isEqual(datumRada))
                .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
    
}
