/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.Mesto;
import domen.Osoba;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleOsobe extends AbstractTableModel{
    List<Osoba> lista;
    String[] kolone = {"ID","Ime","Prezime","Telefon","Email","ID Mesta"};

    public ModelTabeleOsobe(List<Osoba> lista) {
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
        Osoba o = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return o.getIdOsoba();
            case 1:
                return o.getIme();
            case 2:
                return o.getPrezime();
            case 3:
                return o.getTelefon();
            case 4:
                return o.getEmail();
            case 5:
                return o.getIdMesto();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Osoba> getLista() {
        return lista;
    }

    public void setLista(List<Osoba> lista) {
        this.lista = lista;
    }

    public void pretrazi(String ime, String prezime, String telefon, String email, Mesto m) {
        List<Osoba> filteredList = lista.stream()
                .filter(o -> ime == null || ime.isEmpty() || o.getIme().toLowerCase().contains(ime.toLowerCase()))
                .filter(o -> prezime == null || prezime.isEmpty() || o.getPrezime().toLowerCase().contains(prezime.toLowerCase()))
                .filter(o -> telefon == null || telefon.isEmpty() || o.getTelefon().contains(telefon))
                .filter(o -> email == null || email.isEmpty() || o.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(o -> m == null || o.getIdMesto().equals(m)).collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
    
    
}
