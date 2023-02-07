import java.util.LinkedList;

public class Generator {
    public static int IFGOTO = -35;
    public static int GOTO = -36;
    public static int LABEL = -37;
    public static int ARRAY = -38;
    public static int ASIG_ARRAY = -39;
    public static int WRITE = -40;
    public static int WRITEC = -41;
    public static int PRINTC = -42;


    public static boolean asig_Array_ALL=false;



    private static int indiceTemp = 0;
    private static int indiceLabel = 0;

    public static String nuevaTemp() {
        return "_t" + indiceTemp++;
    }

    public static String nuevaLabel() {
        return "L" + indiceLabel++;
    }

    public static void salida(int inst, String arg1, String arg2, String res) {
        
        if (inst == sym.MAS) {
            String tipo= TablaSimbolos.getTipo(res);
            if(tipo.equals("FLOAT")){ //SI es tipo float la operacion debe llevar una r
                PLXC.out.println("\t" + res + " = " + arg1 + " +r " + arg2 + ";"); 
            }else{
                PLXC.out.println("\t" + res + " = " + arg1 + " + " + arg2 + ";");
            }
            
        } else if (inst == sym.MENOS) {
            String tipo= TablaSimbolos.getTipo(res);
            if(tipo.equals("FLOAT")){
                PLXC.out.println("\t" + res + " = " + arg1 + " -r " + arg2 + ";"); 
            }else{
                PLXC.out.println("\t" + res + " = " + arg1 + " - " + arg2 + ";");
            }
            
        } else if (inst == sym.POR) {
            String tipo= TablaSimbolos.getTipo(res);
            if(tipo.equals("FLOAT")){
                PLXC.out.println("\t" + res + " = " + arg1 + " *r " + arg2 + ";"); 
            }else{
                PLXC.out.println("\t" + res + " = " + arg1 + " * " + arg2 + ";");
            }
            
        } else if (inst == sym.PRINT) {
            PLXC.out.println("\t" + "print " + res + ";");
        } else if (inst == sym.AND) {
            PLXC.out.println(res + "=" + arg1 + "&&" + arg2);
        } else if (inst == IFGOTO) {
            PLXC.out.println("\tif (" + arg1 + arg2 + ") goto " + res + ";");
        } else if (inst == GOTO) {
            PLXC.out.println("\tgoto " + res + ";");
        } else if (inst == LABEL) {
            PLXC.out.println(res + ":");
        } else if (inst == sym.ELSE) {
            PLXC.out.println(res + "=" + arg1 + "/" + arg2);
        } else if (inst == sym.MINUS) {
            PLXC.out.println("\t" + res + " = -" + arg1 + ";");
        } else if (inst == sym.ENTERO) {
            PLXC.out.println("\t" + res + " = " + arg1);
        } else if (inst == sym.IDENT) {
            if(arg2!=null){
                if(arg2.equals("INT")){
                    PLXC.out.println("\t" + res + " = (int) " + arg1 + ";");
                }

                if(arg2.equals("FLOAT")){
                    PLXC.out.println("\t" + res + " = (float) " + arg1 + ";");
                }
               
            }else{
                PLXC.out.println("\t" + res + " = " + arg1 + ";");
            }
            
        } else if (inst == sym.DIV) {
            String tipo= TablaSimbolos.getTipo(res);
            if(tipo.equals("FLOAT")){
                PLXC.out.println("\t" + res + " = " + arg1 + " /r " + arg2 + ";"); 
            }else{
                PLXC.out.println("\t" + res + " = " + arg1 + " / " + arg2 + ";");
            }
            
        } else if (inst == sym.MASMAS || inst == sym.MENOSMENOS) {
            String op = inst == sym.MASMAS ? " + " : " - ";
            if(res.startsWith("_")){
                op=op+op;
                PLXC.out.println("error; No se puede hacer "+ op + " en una expresion");
                System.exit(-1);
            }

            if(arg1==null){
                PLXC.out.println("\t" + res + " = " + res + op + "1" + ";");
            }else{
                PLXC.out.println("\t" + res + " = " + res + op + arg1 + ";");
            }

            
        }else if (inst == ARRAY){ //  x[i] = algo
            
            
            String tipoSet=TablaSimbolos.getTipoSet(res);
            String tipoElemento=TablaSimbolos.float_int_char_variable(arg2);
            // PLXC.out.println(res+"  "+arg2);Generator.salida(Generator.ARRAY, set, String.valueOf(i), t);

            
            if(tipoElemento.equals("VARIABLE")){
                // PLXC.out.println("ES UNA VARIABLE: "+arg2+" : "+TablaSimbolos.getTipo_desde_VarOSet(tipoElemento));
                tipoElemento=TablaSimbolos.getTipo_desde_VarOSet(arg2);
            }

            // PLXC.out.println("\n"+res+": " +tipoSet+" // "+arg2+": "+tipoElemento);


            // PLXC.out.println(tipoSet + "   :  "+tipoElemento);
           
            
            if(!asig_Array_ALL){ //TENGO QUE CASTEAR LOS TIPOS, ESTO ES PARA IMPRIMIR x[5]= (float) 4, ó y[3] = 5 ó saltar error

                // PLXC.out.println("TIPO DE VARIABLES: "+ tipoSet+"   "+tipoElemento);
            
                //Controlo los tipos y realizo casteos al asignar elementos al array
                if(tipoSet.equals("FLOAT") && tipoElemento.equals("INT")){  //int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
                    String t=Generator.nuevaTemp();
                    PLXC.out.println("\t" + t +" = (float) "+arg2+";");
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+t+";");
                    
                    
                }else if(tipoSet.equals("INT") && tipoElemento.equals("FLOAT")){
                    PLXC.out.println("#error; Se esta intentando asignar a un SET de INT un FLOAT \nerror;");
                    System.exit(-1);
                }else{
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+arg2+";");
                }
            
           }else{ //Esto es para NO castear los tipos, aqui venimos cuando nos llaman de: float x[3] ; x = {1.1, 2, 3.3};     ó   float x[3] = {1.1, 2, 3.3}; Asique soltamos error.
                
           
                if(!tipoSet.equals(tipoElemento)){  //int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
                    PLXC.out.println("#error; Se esta intentando asignar a un SET tipos incompatibles \nerror;");
                    System.exit(-1);
                    
                }else if(tipoElemento.equals("CHAR")){
                    arg2=arg2.replaceAll("'", ""); //Le quito los apostrofes al string ej: 'A'
                    char l = arg2.charAt(0); //Convierto el string a tipo char
           

                    int representacioCharEnAsci=(int) l; //Casteo el char a INT dandome asi su valor ascii
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+String.valueOf((representacioCharEnAsci))+";");
                
                }else{
                    
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+arg2+";");
                    
                }
                

            

           }

                

        }else if(inst == ASIG_ARRAY){ // x = t [arg2];
            PLXC.out.println("\t" + res + " = "+arg1+ "["+arg2+"];");

        }else if(inst == WRITE){
            PLXC.out.println("\twrite " + arg1 + ";");
        
        }else if (inst == WRITEC){
            PLXC.out.println("\twritec " + arg1 + ";");

        }else if (inst == PRINTC){ //Lo mismo que writec pero mete salto de linea al final
            PLXC.out.println("\tprintc " + arg1 + ";");

        
        }else if(inst == sym.SET){ // operatoria de impresión por pantalla cuando se recibe un print de un set:  print(c)
            boolean desconvertirASCII=false;
            LinkedList<String> lista = TablaSimbolos.dictSets.get(res);
            

            if(lista.get(0).equals("CHAR")){ //Por si es un CHAR desconvertirlo de ASCII a caracter
                desconvertirASCII=true;
            }

            int length=lista.size()-1; //Ya que en la primera pos tenemos el tipo
            String longitud_imprimir=res+"_length";
            String t=Generator.nuevaTemp();
            String t1=Generator.nuevaTemp();
            TablaSimbolos.put(t, "INT");
            String etiq1=Generator.nuevaLabel();
            String etiq2=Generator.nuevaLabel();
            String etiq3=Generator.nuevaLabel();
    
            Generator.salida(sym.IDENT,String.valueOf(length),null,longitud_imprimir);
            Generator.salida(sym.IDENT, "0", null, t);   
            Generator.salida(Generator.LABEL,null,null,etiq1);
            Generator.salida(Generator.IFGOTO, t+" < ", longitud_imprimir,etiq2); 
            Generator.salida(Generator.GOTO, null, null, etiq3);


            Generator.salida(Generator.LABEL, null, null, etiq2);
            Generator.salida(Generator.ASIG_ARRAY,res,t,t1 );
            if(desconvertirASCII){
                Generator.salida(Generator.WRITEC,t1,null,null); //Con writec el compilador convierte el valor pasado en ascii a char
            }else{
                Generator.salida(Generator.WRITE,t1,null,null);
            }
            Generator.salida(Generator.WRITEC,"32",null,null);
            Generator.salida(sym.IDENT, t+" + 1", null, t);
            Generator.salida(Generator.GOTO,null,null,etiq1);
            
            Generator.salida(Generator.LABEL,null,null,etiq3);
            Generator.salida(Generator.WRITEC, "10", null, null);
    
        }else if(inst == sym.CHAR){
            Generator.salida(Generator.PRINTC, arg1, null, null);
        }
}
}
