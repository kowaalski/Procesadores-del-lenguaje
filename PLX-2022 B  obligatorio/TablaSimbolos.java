import java.time.chrono.ThaiBuddhistChronology;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class TablaSimbolos {
    public static LinkedList<HashMap<String, String[]>> linkedList = new LinkedList<>(); //VARIABLE : TIPO (INT,FLOAT,CHAR) EL HASHMAP DE DENTRO ES MI DICT VAR
    public static HashMap<String, String[]>   dictVar = new HashMap   <String, String[]>();                         
    public static HashMap<String, String[]> dictArrays = new HashMap<String, String[]>();                   //VARIABLE (ARRAY) : ARRAY-->TIPO,LONGITUD
    public static HashMap<String, LinkedList<String>> dictSets = new HashMap<String, LinkedList<String>>();// VARIABLE (SET) : LISTA DE SUS VALORES GUARDADOS, EN CUYA POS 0 SIEMPRE IRA EL TIPO

    public static boolean asig_Array_ALL=false;

    // tipo CHAR se guarda en dictSets tal cual así 'A', despues en el generator.SALIDA es donde lo convertimos a ASCII, ya que el compilador debe trabjarlo en ASCII, pero a la hora de decirle que lo 
    // pinte por pantalla en el codigo ctd debemos hacerlo asi writec NUM ASCII asi lo desconvertira y salra la letra correspondiente



    public TablaSimbolos() {
    }



////////////////////////////////  SET  ////////////////////////////////////////////////////////////////////////////////


public static void operatoriaBorrarElementoSet(String set, String elem){
    
    PLXC.out.println("# Quitamos un elemento");
    String t= Generator.nuevaTemp();
    LinkedList<String> lista = dictSets.get(set);

    int indiceElem=lista.indexOf(elem)-1;
    // PLXC.out.println("INDICE ELEMENTO A BORRAR: "+indiceElem);
    TablaSimbolos.asig_Array_ALL=true; //Para que al hacer la salida ARRAY, no castee nada

    if(indiceElem>=0){
        for(int i=indiceElem ; i<lista.size()-1 ;i++){ //Operatoria de impresión moviendo todas los elementos 1 a la izquierda, y el ultimo asignandole null, asi el compilador sabra que se borra

            if((lista.size()-2)==i){
                Generator.salida(Generator.ARRAY,String.valueOf(i),"null", set);
            }else{
                Generator.salida(Generator.ASIG_ARRAY, set, String.valueOf(i+1), t);
                Generator.salida(Generator.ARRAY,String.valueOf(i),t, set);

            }
            
            
            
        }

    }

    lista.remove(elem); //Finalmente lo quitamos de nuestra lista interna.
}


public static String getTipo_desde_VarOSet(String v){ //Le pasamos una variable o un set, y devuelve su tipo

    if(esSet(v)){
        // PLXC.out.println("es variable SET su tipo es: "+getTipoSet(v)+" -");
        return getTipoSet(v);
    }else{
        // PLXC.out.println("OBTENER TIPO DE VARIABLE: "+getTipo(v));
        
        // PLXC.out.println("LISTA DE VARIABLES NORMALES "+ v +"  "+dictVar.get(v));
        // PLXC.out.println("es variable normal su tipo es: "+getTipo(v)+" -");
        return getTipo(v);
    }
}


//En este punto el array original esta vacio solo tiene el tipo, mientras que el auxiliar tiene todo, por lo que deberemos volcarlo sobre el original
public static void VolcarArrayAux_a_Original_SET(String arrayOriginal,String arrayAux, boolean copiar) { // operatoria de impresión por pantalla cuando se asigna un array así: a={1,2+3,1+x} ó a=b (este caso serí copiar b en a), tb cuando aplicacmos a<=={2,3,5} o a<==b (siendo b un array) en estos casos se deben meter los elemeentos no repetidos
        
    if(!arrayOriginal.equals(arrayAux)){ 
                String aux = Generator.nuevaTemp(); //Esta variable NO le estoy guardando valor CUIDADO
                put(aux, TablaSimbolos.getTipoSet(arrayAux));
                LinkedList<String> lista = dictSets.get(arrayOriginal);
                LinkedList<String> listaAux = dictSets.get(arrayAux);
                // PLXC.out.println("------------Lista Origiginal: "+lista.toString()+" // tam: " + lista.size());
                // PLXC.out.println("------------Lista Auxiliar:   "+listaAux.toString()+" // tam: " + listaAux.size()+"\n");

                if (copiar){ //Si estamos en este caso a=b, debemos copiar b en a eliminando todo lo que hay en a, e introduciendo todo b en nuestro dictSets asoaciado a la lista de a
                    lista.clear();
                    lista.add(0, listaAux.get(0)); //Para guardar el tipo 
                    for(int i=0 ; i<listaAux.size()-1 ; i++){
                        TablaSimbolos.putElementoSet(arrayOriginal, listaAux.get(i+1));
                        Generator.salida(Generator.ASIG_ARRAY , arrayAux, String.valueOf(i), aux); // aux = arrayAux[i]
                        Generator.salida(Generator.ARRAY, String.valueOf(i),aux, arrayOriginal);   // arrayOriginal[i]=aux
                    }
                    //  PLXC.out.println("LISTA ORIGINAL TRAS COPIA: "+lista.toString());

                }else{

                    // PLXC.out.println("------------Lista Auxiliar:   "+lista.toString()+" // tam: " + lista.size());
                    if(dictSets.get(arrayOriginal).size()==1){ //Significa que tendremos que meter todos los elementos de array aux en el original, aqui caeria siempre que es c = {1,2,3} ó c <== {1,2,3} (sin haber metido antes nada en c)
                            for(int i=0 ; i<listaAux.size()-1 ; i++){
                                // PLXC.out.println("TAM ARRAY ORIGINAL BUCLE "+listaAux.size()+"-------DENTRO");
                                TablaSimbolos.putElementoSet(arrayOriginal, listaAux.get(i+1));
                                Generator.salida(Generator.ASIG_ARRAY , arrayAux, String.valueOf(i), aux); // aux = arrayAux[i]
                                Generator.salida(Generator.ARRAY, String.valueOf(i),aux, arrayOriginal);   // arrayOriginal[i]=aux
                            }

                    }else{ //Añadir los elementos NO duplicados

                            for(int i=0; i<listaAux.size()-1 ; i++){
                                
                                String elem=listaAux.get(i+1); //Podemos tener guardada una variable, por lo tanto sacamos su valor asociado.
                                if(float_int_char_variable(elem).equals("VARIABLE")){
                                    elem=getElemVar(elem);
                                }
                                
                                if(!TablaSimbolos.existeElemSet(arrayOriginal,  elem)){ //Vamos recorriendo el array aux y mirando no existe en el original, SI no existe lo añadimos al final del set y realizamos la operatoria de impresion
                                    
                                    TablaSimbolos.putElementoSet(arrayOriginal, elem);
                                    int ultPos=getUltPosSet(arrayOriginal);
                                    //Operatoria de impresion
                                    Generator.salida(Generator.ASIG_ARRAY , arrayAux, String.valueOf(i), aux); //   ej:   _t3 = _t2[0];   t2 es nuestro arrayaux y t3 nuestra aux(variable temporal creada para esto)
                                    Generator.salida(Generator.ARRAY, String.valueOf(ultPos),aux, arrayOriginal);//      c[3] = _t3;      c nuestro set original
                                }
                            }
                    }

                }
            
        
        
        
              }  

    
    }
            






public static boolean comprobacionASIG_SET(String var1,String var2) { // Devuelve true si se pueden asignar como set (del mismo tipo) las dos variables pasadas, devuelve false si son dos variables(o numeros) normales y no sets. 
        
    boolean esSet1=esSet(var1);
    boolean esSet2=esSet(var2);

    if( esSet1 && esSet2){
        if(getTipoSet(var1).equals(getTipoSet(var2))){
            return true;
        }else{
            PLXC.out.println("#error; Sets de distintos tipos \nerror;");
            System.exit(-1);
            return false;

        }

    }else if( !esSet1 && !esSet2){
        return false;
    }else{
        PLXC.out.println("error; Se esta intentando asignar a un SET un NO SET o viceversa");
        System.exit(-1);
        return false;

    }
   
}


public static boolean esSet(String variable){
    
        if(dictSets.get(variable)!=null){
            return true;
        }else{
            return false;
        }
    
}

public static boolean existeElemSet(String variable, String elemento){ //Elementos repetidos NO se añaden
    
    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }

    LinkedList<String> lista = dictSets.get(variable);
    // PLXC.out.println(lista.toString());

    if(lista.contains(elemento)){
        return true;
    }else{
        return false;
    }


}



public static int getPosElemSet(String variable, String elemento){ //Elementos repetidos NO se añaden
    int pos;
    
    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }

    LinkedList<String> lista = dictSets.get(variable);
    pos=lista.indexOf(elemento)-1; //Hay que ajustar en la primera pos tenemos el tipo

    return pos;

}


public static int getUltPosSet(String variable){ //Elementos repetidos NO se añaden
    int pos;
    
    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }

    LinkedList<String> lista = dictSets.get(variable);
    pos=lista.size()-2; //Para ajusta a la ultima pos y la primera pos que se guarda el tipo

    return pos;

}



public static void putSet(String variable, String tipo){
    if(dictSets.get(variable)!=null){
        System.out.println("# Este Set: "+variable+" ya ha sido declarada \n error;");
        System.exit(-1);
    }
    LinkedList<String> lista = new LinkedList<String>();
    lista.add(0, tipo);


    dictSets.put(variable,lista);
    
}

public static void putElementoSet(String variable, String elemento){ //Elementos repetidos NO se añaden
    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }

    LinkedList<String> lista = dictSets.get(variable);

    if(!lista.contains(elemento)){
        lista.add(elemento); //Al añadir en la lista esta, se modifica automaticamente en la lista de dentro de hashmap
    }

}

public static String getTipoSet(String variable){
    String tipo;

    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }
    
    LinkedList<String> lista = dictSets.get(variable);
    tipo = lista.get(0);


    return tipo;
    
}


public static String getTamSet(String variable){
    String tam;

    if(dictSets.get(variable)==null){
        System.out.println("# Este Set: "+variable+" NO ha sido declarado \n error;");
        System.exit(-1);
    }
    
    LinkedList<String> lista = dictSets.get(variable);
    tam = String.valueOf(lista.size()-1);


    return tam;
    
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


    public static String quitarAmbito (String e){ //Para quitarle a las variable :  a_2 el _2, si es _t0 no se le hace nada
        if(!e.startsWith("_")){
            return e.split("_")[0];
        }else{
            return e;
        }
    }



    public static String castElemOVariable(String e, String casteo) { //Nos pasan por "e" una variable o un num a pelo, y lo casteamos a INT o double segun la variable "casteo"
        String resul="";
        if(TablaSimbolos.float_int_char_variable(e).equals("VARIABLE")){
            resul = TablaSimbolos.getElemVar(e);    
        }else{
            resul = e;
        }
            
            
        if(casteo.equals("INT")){
                    resul= String.valueOf(Integer.parseInt(resul));
        }else{
                    resul= String.valueOf(Double.parseDouble(resul));
        }

        return resul;
}


    public static String getTipo(String variable) { //OBTENER EL TIPO DE UNA VARIABLE YA GUARDADA EN EL DICCIONARIO
        String resul="";
        if (linkedList.isEmpty()) linkedList.addLast(new HashMap<>());

        ListIterator listIterator = linkedList.listIterator(linkedList.size());
        while (listIterator.hasPrevious()) {
            HashMap<String, String[]> temp = (HashMap<String, String[]> )listIterator.previous();
            if(temp.get(variable)!=null){
                resul=temp.get(variable)[0];
                break;
            }
            
        }
        return resul;
    }


    public static String[] calcGetTipos(String e1, String e2) { //OBTENER EL TIPO DE DOS COSAS que pueden ser: ( VARIABLE ó "345" (NUM ENTERO) ó "2.54" (NUM REAL) ó char 'a'), nos dan string y devolvemos ya sea "INT" ó "FLOAT"  ò "CHAR"
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
        
        
        if(TablaSimbolos.float_int_char_variable(e1).equals("VARIABLE")){
            // PLXC.out.println("DENTRO1a");
            tipo1 = TablaSimbolos.getTipo(e1);
        }else{
            // PLXC.out.println("DENTRO1b");
            tipo1 = TablaSimbolos.float_int_char_variable(e1);
        }


        if(TablaSimbolos.float_int_char_variable(e2).equals("VARIABLE")){
            // PLXC.out.println("DENTRO2a");
            tipo2 = TablaSimbolos.getTipo(e2);
        }else{
            // PLXC.out.println("DENTRO2b");
            tipo2 = TablaSimbolos.float_int_char_variable(e2);
        }
        tipos[0]=tipo1;
        tipos[1]=tipo2;
        return tipos;
    }




    public static String float_int_char_variable(String variable) {
        String resul="";

        if(variable.startsWith("'") && variable.endsWith("'")){
            resul="CHAR";
        }else{
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
        }

        return resul;
    }


    public static String comprobarCompatibilidadTipos(String tipo_Asignacion, String e) { //Para comprobar si estamos asignando a una variable el tipo que debe, ej: int a=23.423 ERROR
        String tipo_A_Asignar;
        if(TablaSimbolos.float_int_char_variable(e).equals("VARIABLE")){   
            tipo_A_Asignar = TablaSimbolos.getTipo(e);
        }else{
            tipo_A_Asignar = TablaSimbolos.float_int_char_variable(e);
        }

        if(tipo_Asignacion.equals("INT") && tipo_A_Asignar.equals("FLOAT") ){ // int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
            PLXC.out.println("ERROR: Se esta asignando un valor real a una variable entera\n halt ;");
            System.exit(-1);
        }else if((tipo_Asignacion.equals("CHAR") && !tipo_A_Asignar.equals("CHAR"))|| (!tipo_Asignacion.equals("CHAR") && tipo_A_Asignar.equals("CHAR"))){
            PLXC.out.println("ERROR: Se esta asignando un char a una variable float o int, o viceversa \nhalt ;");
            System.exit(-1);
        }
        //Si son compatibles: DEVOLVEMOS: "INT" ó "FLOAT" ó "CASTING" si debemos hacerle un casteo 
        // PLXC.out.println("TIPO A ASIGNAR: "+tipo_A_Asignar);
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


    
    public static void putElemVarMASMAS(String st) { //Nos pasan una variable y lse sumamos uno a su valor asociado
         
        ListIterator listIterator = linkedList.listIterator(linkedList.size());

        
        while (listIterator.hasPrevious()) { 
            HashMap<String, String[]> temp = (HashMap<String, String[]>) listIterator.previous();

            if (temp.keySet().contains(st)) {
                String[] a=temp.get(st);
                a[1]=String.valueOf(Integer.parseInt(a[1])+1);
                break;
            }
        }

        
 
    }


    public static void putElemVarMENOSMENOS(String st) { //Nos pasan una variable y le restamos uno a su valor asociado
         
        ListIterator listIterator = linkedList.listIterator(linkedList.size());

        while (listIterator.hasPrevious()) { 
            HashMap<String, String[]> temp = (HashMap<String, String[]>) listIterator.previous();

            if (temp.keySet().contains(st)) {
                String[] a=temp.get(st);
                a[1]=String.valueOf(Integer.parseInt(a[1])-1);
                break;
            }
        }

        
 
    }


   //Asignamos a una variable un valor
    public static void putElemVar(String st, String elem) { //Por elem nos pasan una variable o un numero a pelo, si es una variable tenemos que sacar su numero a pelo asociado
         
        String valorAsacar;
        ListIterator listIterator = linkedList.listIterator(linkedList.size());

        // PLXC.out.println("DENTRO PUT ELEM: VARIABLE--> " +st +"  VALOR--> " +elem);

        if(!TablaSimbolos.float_int_char_variable(elem).equals("VARIABLE")){
            // PLXC.out.println("VALOR:" +elem+ " ES VALOR A PELO");
            valorAsacar=elem; 
        }else{
            valorAsacar=TablaSimbolos.getElemVar(elem);
            // PLXC.out.println("VALOR: " +elem+ " ES UNA VARIABLE CUYO VALOR ES: "+valorAsacar);
        }

        // HashMap<String, String[]> a= getNivel();

        // if(a.get(st)!=null){
        //     a.get(st)[1]=valorAsacar;
        // }

        while (listIterator.hasPrevious()) { 
            HashMap<String, String[]> temp = (HashMap<String, String[]>) listIterator.previous();

            if (temp.keySet().contains(st)) {
                String[] a=temp.get(st);
                a[1]=valorAsacar;
                break;
            }
        }

        
 
    }



    public static String getElemVar(String st) { //SI st es una variable te da su elemento asociado, si st es un numero a pelo te da ese numero a pelo
        String resul="";
        ListIterator listIterator = linkedList.listIterator(linkedList.size());

        // PLXC.out.println("GET VARIABLE "+st);
        if (float_int_char_variable(st).equals("VARIABLE")){
            
            while (listIterator.hasPrevious()) { //Vamos sacando hashmaps del final de la lista hacia atras Y BUSCAMOS SI EN ALGUNO ESTA LA VARIABLE QUE NOS PIDEN
                HashMap<String, String[]> temp = (HashMap<String, String[]>) listIterator.previous();
    
                if (temp.keySet().contains(st)) {
                    resul=temp.get(st)[1];
                    
                    break;
                }
            }
        }else{
            resul=st;
        }


        return resul;
 
    }



    public static void put(String st, String t) { //En el mismo bloque {..} no se puede declarar la misma variable varias veces, pero declararla en un bloque, y acontinuacion en otro bloque si se puede, por lo que a la hora de hacer el put, el error solo vendra si esta en el mismo bloque dos vecesc por lo que solo tenemos que mirar en el bloque en el que estamos es decir en el último bloque (nivel) si ya se encuentra, sino nos da igual
        if (getNivel().keySet().contains(st)){
            System.out.println("# ERROR: Variable \"" + st + "\" ya ha sido declarada");
            System.exit(-1);

        }

        String[] a=new String[2];
        a[0]=t;
        a[1]="0";


        getNivel().put(st, a); //Introducimos la variable en el último nivel, ultimo hashmap que es el nivel en el que estamos.

    
    }


    public static void put(String st, String t, String v) { //En el mismo bloque {..} no se puede declarar la misma variable varias veces, pero declararla en un bloque, y acontinuacion en otro bloque si se puede, por lo que a la hora de hacer el put, el error solo vendra si esta en el mismo bloque dos vecesc por lo que solo tenemos que mirar en el bloque en el que estamos es decir en el último bloque (nivel) si ya se encuentra, sino nos da igual
    if (getNivel().keySet().contains(st)){
        System.out.println("# ERROR: Variable \"" + st + "\" ya ha sido declarada");
        System.exit(-1);

    }

    String[] a=new String[2];
    a[0]=t;
    a[1]=v;


    getNivel().put(st, a); //Introducimos la variable en el último nivel, ultimo hashmap que es el nivel en el que estamos.


}


    
    //Salta error si no esta la variable, si esta devuelve la el string _POS.DELA.LISTA.DONDE.ESTA, SI LA POS ES 1 O 0 DEVUELVE ""
    public static String get(String st) { //Toda la liada de la linked list con diccionarios es para lo del ambito de las variables 

        if (linkedList.isEmpty()) {
            linkedList.addLast(new HashMap<>());
        }
        int cont = linkedList.size();

        boolean enc = false;

        ListIterator listIterator = linkedList.listIterator(linkedList.size()); //Ponemos el iterador al final de la lista de hashmaps (niveles)


        while (listIterator.hasPrevious()) { //Vamos sacando hashmaps del final de la lista hacia atras Y BUSCAMOS SI EN ALGUNO ESTA LA VARIABLE QUE NOS PIDEN
            HashMap<String, String[]> temp = (HashMap<String, String[]>) listIterator.previous();

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

    public static void quitarNivel() {
        linkedList.removeLast();
    }

    public static void anadirNivel() {
        linkedList.addLast(new HashMap<String, String[]>());
    }

    public static HashMap<String, String[]> getNivel() { //Devuelve el ultimo hashmap de la lista
        if (linkedList.isEmpty()) {
            linkedList.addLast(new HashMap<String, String[]>());
        }

        return linkedList.getLast();
    }

}
