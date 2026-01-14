/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.Serializable;

/**
 *
 * @author Nikola
 */
public class Zahtev implements Serializable {
    private Operacija operacija;
    private Object parametar;
    private Object parametar1;
    
    
    public Zahtev() {
    }

    public Zahtev(Operacija operacija, Object parametar, Object parametar1) {
        this.operacija = operacija;
        this.parametar = parametar;
        this.parametar1 = parametar1;
    }
    
    
    
    public Zahtev(Operacija operacija, Object parametar) {
        this.operacija = operacija;
        this.parametar = parametar;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }

    public Object getParametar1() {
        return parametar1;
    }

    public void setParametar1(Object parametar1) {
        this.parametar1 = parametar1;
    }
    
    
    
}
