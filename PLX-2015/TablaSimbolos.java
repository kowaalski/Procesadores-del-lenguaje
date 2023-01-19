import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class TablaSimbolos {
    public static LinkedList<HashMap<String, String>> linkedList = new LinkedList<>();
    public static HashMap<String, String> dictVar = new HashMap<String, String>();
    public static HashMap<String, String[]> dictArrays = new HashMap<String, String[]>();
    public static boolean asig_Array_ALL=false;



    public TablaSimbolos() {
    }

////////////////////////////////  ARRAYS  ////////////////////////////////////////////////////////////////////////////////
   


public static String getTipo_desde_VarOArray(String v){

    if(existeArray(v)){
        return getTipoArray(v);
    }else{
        return getTipo(v);
    }
}

public static boolean comprobacionASIG_Arrays(String var1,String var2) { // Devuelve true si se pueden asignar como array (del mismo tipo y tamaño) las dos variables pasadas, devuelve false si son dos variables(o numeros) normales y no arrays. 
        
        boolean esArray1=existeArray(var1);
        boolean esArray2=existeArray(var2);
        boolean resul=false;

        if( esArray1 && esArray2){
            if(getTipoArray(var1).equals(getTipoArray(var2))){
                if( Integer.parseInt(getTamArray(var1))>=Integer.parseInt(getTamArray(var2))){
                    return true;
                }else{
                    PLXC.out.println("error; Las dimensiones de los Arrays no encajan");
                    System.exit(-1);
                }
            }else{
                PLXC.out.println("error; Arrays de distintos tipos");
                System.exit(-1);
                return resul;

            }

            return resul;
            
        }else if( !esArray1 && !esArray2){
            return false;
        }else{
            PLXC.out.println("error; Se esta intentando asignar a un array un NO array o viceversa");
            System.exit(-1);
            return resul;

        }
    

        
    }


public static void VolcarArrayAux_a_Original(String arrayOriginal,String arrayAux, int tam_aux) { // operatoria de impresión por pantalla cuando se asigna un array así: a={1,2+3,1+x}
        String aux=Generator.nuevaTemp();
        int tam_Original=Integer.parseInt(TablaSimbolos.getTamArray(arrayOriginal));
        if(tam_aux>tam_Original){
            PLXC.out.println("error: Las dimensiones de los arrays no encajan \n halt;");
            System.exit(-1);
        }
        
        for(int i=0 ; i<tam_aux ; i++){
            Generator.salida(Generator.ASIG_ARRAY , arrayAux, String.valueOf(i), aux);
            Generator.salida(Generator.ARRAY, String.valueOf(i),aux, arrayOriginal);
        }

        //Generator.salida(sym.IDENT, arrayAux, null, arrayOriginal);
        
    }

    
    public static void putArray(String variable,String tipo) { 
        String[] a=new String[3];
        a[0]=tipo;
        a[1]=null;

        dictArrays.put(variable,a);
        
    }
    
    public static void putArray(String variable,String tipo, String longitud ) { 
        String[] a=new String[3];
        a[0]=tipo;
        a[1]=longitud;

        dictArrays.put(variable,a);
        
    }

    public static boolean existeArray(String variable) { //SI NO EXISTE EL ARRAY DEVUELVE FALSE
        boolean encontrado=true;

        if(dictArrays.get(variable)==null){
            encontrado=false;
        }

        return encontrado;
                
    }

    public static String getTipoArray(String variable) { //OBTENER TIPO DEL ARRAY, SI NO EXISTE EL ARRAY LANZA ERROR
        
        if(dictArrays.get(variable)==null){
            System.out.println("# Variable no declarada");
            System.exit(-1);
        }

        String[] a=dictArrays.get(variable);
        return a[0];
                
    }

    public static String getTamArray(String variable) { //OBTENER EL TAMAÑO Y EL TIPO DEL ARRAY, SI NO EXISTE EL ARRAY LANZA ERROR
        
        if(dictArrays.get(variable)==null){
            System.out.println("# Variable no declarada");
            System.exit(-1);
        }

        String[] a=dictArrays.get(variable);
        return a[1];
                
    }




    public static void comprobacionRango( String array, String posAacceder) { //Comprueba cuando se va a guardar algo en una array, si la posicion es correcta, eso lo hacemos haciendo paranoyas con los ifs de abajo para que el compilador cuando lo lea sepa ejecutarlo y que entre por el if del error si la pos es invalida
        int tam = Integer.parseInt(dictArrays.get(array)[1]);
        if (tam > 1) {
            PLXC.out.println("# Comprobacion de rango");
            String labelError = Generator.nuevaLabel();
            String labelBuena = Generator.nuevaLabel();

            Generator.salida(Generator.IFGOTO, posAacceder  + " < ","0", labelError);
            Generator.salida(Generator.IFGOTO, tam + " < ", posAacceder, labelError);
            Generator.salida(Generator.IFGOTO, tam + " == ", posAacceder, labelError);
            Generator.salida(Generator.GOTO, null, null, labelBuena);
            Generator.salida(Generator.LABEL, null, null, labelError);
            PLXC.out.println("\terror;");
            PLXC.out.println("\thalt;");
            Generator.salida(Generator.LABEL, null, null, labelBuena);
        }
    }





    public static void comprobarRangoArray(String array, String tam){
        
        try { //Por si nos pasan una variable ne vez de  un numero, salta el error al parsear y no hace nada
            int tamOriginal =  Integer.parseInt(dictArrays.get(array)[1]);
            int tamAcomprobar= Integer.parseInt(tam); 
            if(!(tamAcomprobar>=1) && !(tamAcomprobar<=tamOriginal)){
                PLXC.out.println("error: Fuera del rango del array \n"+"halt; ");
                System.exit(-1);
            }

        } catch (NumberFormatException excepcion) {
       
        }

    }
    
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public static String getTipo(String variable) { //OBTENER EL TIPO DE UNA VARIABLE YA GUARDADA EN EL DICCIONARIO
        String resul="";
        if (linkedList.isEmpty()) linkedList.addLast(new HashMap<>());

        ListIterator listIterator = linkedList.listIterator(linkedList.size());
        while (listIterator.hasPrevious()) {
            HashMap<String, String> temp = (HashMap<String, String>) listIterator.previous();
            if(temp.get(variable)!=null){
                resul=temp.get(variable);
                break;
            }
            
        }
        return resul;
    }


    public static String[] calcGetTipos(String e1, String e2) { //OBTENER EL TIPO DE DOS COSAS que pueden ser: ( VARIABLE ó "345" (NUM ENTERO) ó "2.54" (NUM REAL)), nos dan string y devolvemos ya sea "INT" ó "FLOAT" 
        String tipo1,tipo2;
        String[] tipos=new String[2];

        if(!e1.startsWith("_")){ //Por si me viene de ambito es decir, y_2, le quito el la _2 para asi poder buscar bien por mi diccionario ya que las guardo sin ambito
            String[] parts1 = e1.split("_"); //Lo del if es para no cargarme las variables temporales _t0
            e1=parts1[0];
        }

        if(!e2.startsWith("_")){
            String[] parts2 = e2.split("_");
            e2=parts2[0];

        }
        
        
        if(TablaSimbolos.float_int_variable(e1).equals("VARIABLE")){
            // PLXC.out.println("DENTRO1a");
            tipo1 = TablaSimbolos.getTipo(e1);
        }else{
            // PLXC.out.println("DENTRO1b");
            tipo1 = TablaSimbolos.float_int_variable(e1);
        }


        if(TablaSimbolos.float_int_variable(e2).equals("VARIABLE")){
            // PLXC.out.println("DENTRO2a");
            tipo2 = TablaSimbolos.getTipo(e2);
        }else{
            // PLXC.out.println("DENTRO2b");
            tipo2 = TablaSimbolos.float_int_variable(e2);
        }
        tipos[0]=tipo1;
        tipos[1]=tipo2;
        return tipos;
    }




    public static String float_int_variable(String variable) {
        String resul="";

 		try {
            Double.parseDouble(variable);
            resul = "FLOAT";
        } catch (NumberFormatException excepcion) {
            resul ="VARIABLE";  
        }

        try {
            Integer.parseInt(variable);

          resul ="INT";
        } catch (NumberFormatException excepcion) {
       
        }



        return resul;
    }


    public static String comprobarCompatibilidadTipos(String tipo_Asignacion, String e) { //Para comprobar si estamos asignando a una variable el tipo que debe, ej: int a=23.423 ERROR
        String tipo_A_Asignar;
        if(TablaSimbolos.float_int_variable(e).equals("VARIABLE")){   
            tipo_A_Asignar = TablaSimbolos.getTipo(e);
        }else{
            tipo_A_Asignar = TablaSimbolos.float_int_variable(e);
        }

        if(tipo_Asignacion.equals("INT") && tipo_A_Asignar.equals("FLOAT") ){ // int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
            PLXC.out.println("ERROR: Se esta asignando un valor real a una variable entera\n halt ;");
            System.exit(-1);
        }
        //Si son compatibles: DEVOLVEMOS: "INT" ó "FLOAT" ó "CASTING" si debemos hacerle un casteo 
        
        if(tipo_Asignacion.equals(tipo_A_Asignar)){
            return tipo_Asignacion;
        }else{
            return "CASTING";
        }
        
    }

    



    public static String tipoResultanteDeOperacion(String e1, String e2){
        
        // PLXC.out.println("TIPOS A OPERAR: "+e1+"  "+e2);
        if(e1.equals("FLOAT") && e2.equals("FLOAT")){
            return "FLOAT";
        }else if(e1.equals("INT") && e2.equals("INT")){
            return "INT";
        }else{
            return "FLOAT";
        }
   
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
        } else { // x_2 variable x estaria en otro bloque distinto afuera (hashmap) asi que indicamos que esta ahora en el bloque 2
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
            System.out.println("# Variable no declarada4");
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
