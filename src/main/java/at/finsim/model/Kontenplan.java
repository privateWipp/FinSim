package at.finsim.model;

import at.finsim.model.konto.Konto;

import java.lang.reflect.Array;
import java.util.HashMap;

public class Kontenplan {
    private Array[] kontenplan;
    private Array[] klassenbezeichnungen;

    public Kontenplan(){
        Array[] kontenplan = new Array[9](String);
    }
}
