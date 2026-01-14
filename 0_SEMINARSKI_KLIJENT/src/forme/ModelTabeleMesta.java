/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.Mesto;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleMesta extends AbstractTableModel{

    List<Mesto> lista;
    String[] kolone = {"ID Mesta","Naziv mesta"};

    public ModelTabeleMesta(List<Mesto> lista) {
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
        Mesto m = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return m.getIdMesto();
            case 1:
                return m.getNaziv();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Mesto> getLista() {
        return lista;
    }

    public void setLista(List<Mesto> lista) {
        this.lista = lista;
    }

    public void pretrazi(String naziv) {
        List<Mesto> filteredList = lista.stream().filter(m -> naziv == null || naziv.isEmpty() || m.getNaziv().toLowerCase().contains(naziv.toLowerCase())).collect(Collectors.toList()); 
        this.lista = filteredList;
        fireTableDataChanged();
    }
    
}
