import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class TablaSimbolos {
    public static LinkedList<HashMap<String, String>> linkedList = new LinkedList<>();
    public static HashMap<String, String> dictVar = new HashMap<String, String>();


    public TablaSimbolos() {
    }


    public static void put(String st, String t) { //En el mismo bloque {..} no se puede declarar la misma variable varias veces, pero declararla en un bloque, y acontinuacion en otro bloque si se puede, por lo que a la hora de hacer el put, el error solo vendra si esta en el mismo bloque dos vecesc por lo que solo tenemos que mirar en el bloque en el que estamos es decir en el último bloque (nivel) si ya se encuentra, sino nos da igual
        if (getNivel().keySet().contains(st)){
            System.out.println("# ERROR: Variable \"" + st + "\" ya ha sido declarada");
            System.exit(-1);

        }

        getNivel().put(st, t); //Introducimos la variable en el último nivel, ultimo hashmap que es el nivel en el que estamos.

    
    }


    /**
     * @param st
     * @return
     */
    //Salta error si no esta la variable, si esta devuelve la el string _POS.DELA.LISTA.DONDE.ESTA, SI LA POS ES 1 O 0 DEVUELVE ""
    public static String get(String st) { //Toda la liada de la linked list con diccionarios es para lo del ambito de las variables 

        
        if (linkedList.isEmpty()) {
            linkedList.addLast(new HashMap<>());
        }
        int cont = linkedList.size();

        boolean enc = false;

        ListIterator listIterator = linkedList.listIterator(linkedList.size()); //Ponemos el iterador al final de la lista de hashmaps (niveles)


        while (listIterator.hasPrevious()) { //Vamos sacando hashmaps del final de la lista hacia atras Y BUSCAMOS SI EN ALGUNO ESTA LA VARIABLE QUE NOS PIDEN
            HashMap<String, Integer> temp = (HashMap<String, Integer>) listIterator.previous();

            if (temp.keySet().contains(st)) {
                enc = true;
                break;
            }
            cont--;
        }

        if (!enc) {
            System.out.println("error;");
            System.out.println("# Variable no declarada");
            System.exit(-1);
            

            return "";
        } else { // _2x variable x estaria en otro bloque distinto afuera (hashmap) asi que indicamos que esta ahora en el bloque 2
            return cont <= 1 ? "" : "_" + cont; //Esto es para devolver en el bloque que esta, ya que con el rollo del ambito necsitamos que las variables aunqeu sean las mismas en bloque distintas tengan distintos nombres para que el compilador lo pille biem
        }
    }

    public static String getConcat(String st) {
        if (linkedList.isEmpty()) {
            linkedList.addLast(new HashMap<>());
        }
        int cont = linkedList.size();

        boolean enc = false;

        ListIterator listIterator = linkedList.listIterator(linkedList.size());


        while (listIterator.hasPrevious()) {
            HashMap<String, Integer> temp = (HashMap<String, Integer>) listIterator.previous();

            for (String s : temp.keySet()) {
                String sC = cont > 1 ? s + "_" + cont : s;

                if (sC.equals(st)) {
                    enc = true;
                    break;
                }
            }
            cont--;
        }

        if (!enc) {
            System.out.println("error;");
            System.out.println("# Variable no declarada");
            //System.out.println(st);
            //System.out.println(linkedList);
        }

        return st;
    }

    public static void quitarNivel() {
        linkedList.removeLast();
    }

    public static void anadirNivel() {
        linkedList.addLast(new HashMap<>());
    }

    public static HashMap<String, String> getNivel() { //Devuelve el ultimo hashmap de la lista
        if (linkedList.isEmpty()) {
            linkedList.addLast(new HashMap<>());
        }

        return linkedList.getLast();
    }

}
