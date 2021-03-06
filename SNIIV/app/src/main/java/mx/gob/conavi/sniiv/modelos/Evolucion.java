package mx.gob.conavi.sniiv.modelos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import mx.gob.conavi.sniiv.parsing.ParseFechas;

/**
 * Created by octavio.munguia on 24/09/2015.
 */
public class Evolucion {
    private static String TAG = Evolucion.class.getSimpleName();
    public static final String FINANCIAMIENTO = "Financiamientos";
    public static final String SUBSIDIO = "Subsidios";
    public static final String REGISTRO_VIVIENDA = "Registro Vivienda";

    Map<String, EvolucionResultado> periodos;

    public Evolucion() {
        periodos = new TreeMap<>();
    }

    public Evolucion(Map<String, EvolucionResultado> periodos) {
        this.periodos = periodos;
    }

    public Evolucion(JSONObject json) throws JSONException {
        this();

        Iterator<?> keys = json.keys();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            JSONArray array = (JSONArray) json.get(key);
            EvolucionResultado efr = new EvolucionResultado(array);
            periodos.put(key, efr);
        }
    }

    public Map<String, EvolucionResultado> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Map<String, EvolucionResultado> periodos) {
        this.periodos = periodos;
    }

    public ArrayList<String> getParties() {
        ArrayList<String> parties = new ArrayList<>(periodos.keySet());
        return parties;
    }

    public ArrayList<double[]> getYValuesAcciones() {
        ArrayList<double[]> yValues = new ArrayList<>();
        for (String key : periodos.keySet()) {
            EvolucionResultado resultado = periodos.get(key);
            Consulta[] meses = resultado.getMeses();
            double[] values = new double[meses.length];
            for (int i = 0; i < meses.length; i++) {
                if (meses[i] == null) {break;}
                values[i] = meses[i].getAcciones();
            }
            yValues.add(values);
        }

        return yValues;
    }

    public ArrayList<double[]> getYValuesMontos() {
        ArrayList<double[]> yValues = new ArrayList<>();
        for (String key : periodos.keySet()) {
            EvolucionResultado resultado = periodos.get(key);
            Consulta[] meses = resultado.getMeses();
            double[] values = new double[meses.length];
            for (int i = 0; i < meses.length; i++) {
                if (meses[i] == null) {break;}
                values[i] = meses[i].getMonto();
            }
            yValues.add(values);
        }

        return yValues;
    }
}
