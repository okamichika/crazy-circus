package fr.iut.crazycircus.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Order {

    static int INDICE_RED = 1;
    static int INDICE_BLUE = 0;

    public static void KI(Podium[] p) {
        if(p[INDICE_BLUE].getSize() != 0){
            p[INDICE_RED].add(p[INDICE_BLUE].remove());
        }
    }
    public static void LO(Podium[] p) {
        if(p[INDICE_RED].getSize() != 0) {
            p[INDICE_BLUE].add(p[INDICE_RED].remove());
        }
    }
    public static void SO(Podium[] p) {
        if (p[INDICE_BLUE].getSize() != 0 && p[INDICE_RED].getSize() != 0 ) {
            Animaux tmp = p[INDICE_BLUE].remove();
            p[INDICE_BLUE].add(p[INDICE_RED].remove());
            p[INDICE_RED].add(tmp);
        }
    }
    public static void NI(Podium[] p) {
        permut(p[INDICE_BLUE]);
    }
    public static void MA(Podium[] p) {
        permut(p[INDICE_RED]);
    }

    public static void permut(Podium p){

        if (p.getSize() <= 1) return;
        Animaux temp = p.getElement(p.getSize()-1); // on stocke temporairement le premier élément
        for (int i = p.getSize()-1; i > 0; i--) p.setElement(i, p.getElement(i-1)); // on déplace l'élément suivant à la position courante
        p.setElement(0, temp); // on déplace le premier élément à la position du dernier


    }
}
