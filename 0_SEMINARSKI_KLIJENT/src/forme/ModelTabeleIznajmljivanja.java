/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import domen.Iznajmljivanje;
import domen.Mesto;
import domen.Osoba;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nikola
 */
public class ModelTabeleIznajmljivanja extends AbstractTableModel {
    
    List<Iznajmljivanje> lista;
    String[] kolone = {"Zaposleni","Osoba","Datum iznajmljivanja","Ukupan broj sati","Ukupan iznos"};

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
                return i.getUkupnoSati();
            case 3:
                return i.getUkupanIznos();
            case 4:
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
    
}
