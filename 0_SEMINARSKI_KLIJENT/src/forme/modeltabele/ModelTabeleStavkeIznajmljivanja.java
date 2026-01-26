/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.modeltabele;

import domen.StavkaIznajmljivanja;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Igor
 */
public class ModelTabeleStavkeIznajmljivanja extends AbstractTableModel{

    List<StavkaIznajmljivanja> lista;
    String[] kolone = {"Oprema","Tip opreme","Broj sati","Cena po satu","Ukupna cena","Datum povratka opreme"};

    public ModelTabeleStavkeIznajmljivanja(List<StavkaIznajmljivanja> lista) {
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
        StavkaIznajmljivanja si = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return si.getIdSkiOprema().getNaziv();
            case 1:
                return si.getIdSkiOprema().getTipOprema();
            case 2:
                return si.getBrojSati();
            case 3:
                return si.getSatCena();
            case 4:
                return si.getCena();
            case 5:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                return si.getDatumPovratkaOpreme().format(formatter);
            
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<StavkaIznajmljivanja> getLista() {
        return lista;
    }

    public void setLista(List<StavkaIznajmljivanja> lista) {
        this.lista = lista;
    }
    
    public StavkaIznajmljivanja vratiStavku(int selectedRow) {
        return lista.get(selectedRow);
    }

//    public void pretrazi(String naziv, String tipOpreme) {
//        List<StavkaIznajmljivanja> filteredList = lista.stream()
//                .filter(si -> naziv == null || naziv.isEmpty() || so.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
//                .filter(si -> tipOpreme == null || tipOpreme.isEmpty() || so.getTipOprema().toLowerCase().contains(tipOpreme.toLowerCase()))
//                .collect(Collectors.toList());
//        this.lista = filteredList;
//        fireTableDataChanged();
//    }
    
}
