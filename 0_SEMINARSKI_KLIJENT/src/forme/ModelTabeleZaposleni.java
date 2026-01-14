/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.Zaposleni;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleZaposleni extends AbstractTableModel{
    List<Zaposleni> lista;
    String[] kolone = {"ID","Ime","Prezime","Email","Username"};

    public ModelTabeleZaposleni(List<Zaposleni> lista) {
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
        Zaposleni z = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return z.getIdZaposleni();
            case 1:
                return z.getIme();
            case 2:
                return z.getPrezime();
            case 3:
                return z.getEmail();
            case 4:
                return z.getUsername();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Zaposleni> getLista() {
        return lista;
    }

    public void setLista(List<Zaposleni> lista) {
        this.lista = lista;
    }

    public void pretrazi(String ime, String prezime, String email, String username) {
        List<Zaposleni> filteredList = lista.stream()
                .filter(z -> ime == null || ime.isEmpty() || z.getIme().toLowerCase().contains(ime.toLowerCase()))
                .filter(z -> prezime == null || prezime.isEmpty() || z.getPrezime().toLowerCase().contains(prezime.toLowerCase()))
                .filter(z -> email == null || email.isEmpty() || z.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(z -> username == null || username.isEmpty() || z.getUsername().toLowerCase().contains(username.toLowerCase())).collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
}
