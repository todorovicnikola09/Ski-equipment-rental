/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.modeltabele;


import domen.TerminDezurstva;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleTerminDezurstva extends AbstractTableModel {

    List<TerminDezurstva> lista;
    String[] kolone = {"Tip termina dezurstva","Vreme pocetka smene","Vreme zavrsetka smene"};

    public ModelTabeleTerminDezurstva(List<TerminDezurstva> lista) {
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
        TerminDezurstva td = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return td.getTipTermina();
            case 1:
                return td.getVremeOd();
            case 2:
                return td.getVremeDo();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<TerminDezurstva> getLista() {
        return lista;
    }

    public void setLista(List<TerminDezurstva> lista) {
        this.lista = lista;
    }

    public void pretrazi(String tipTermina) {
        List<TerminDezurstva> filteredList = lista.stream()
                .filter(td -> tipTermina == null || tipTermina.isEmpty() || td.getTipTermina().toLowerCase().contains(tipTermina.toLowerCase()))
                .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
    
}
