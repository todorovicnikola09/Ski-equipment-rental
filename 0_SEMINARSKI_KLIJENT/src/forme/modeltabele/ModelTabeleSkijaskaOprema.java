/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.modeltabele;

import domen.SkijaskaOprema;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleSkijaskaOprema extends AbstractTableModel {
    List<SkijaskaOprema> lista;
    String[] kolone = {"Naziv","Cena po satu","Tip opreme"};

    public ModelTabeleSkijaskaOprema(List<SkijaskaOprema> lista) {
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
        SkijaskaOprema so = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return so.getNaziv();
            case 1:
                return so.getSatCena();
            case 2:
                return so.getTipOprema();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<SkijaskaOprema> getLista() {
        return lista;
    }

    public void setLista(List<SkijaskaOprema> lista) {
        this.lista = lista;
    }

    public void pretrazi(String naziv, String tipOpreme) {
        List<SkijaskaOprema> filteredList = lista.stream()
                .filter(so -> naziv == null || naziv.isEmpty() || so.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                .filter(so -> tipOpreme == null || tipOpreme.isEmpty() || so.getTipOprema().toLowerCase().contains(tipOpreme.toLowerCase()))
                .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
}
