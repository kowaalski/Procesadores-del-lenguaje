public class Generator {
    public static int IFGOTO = -35;
    public static int GOTO = -36;
    public static int LABEL = -37;
    public static int ARRAY = -38;
    public static int ASIG_ARRAY = -39;

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

            
        }else if (inst == ARRAY){
            
            String tipoArray=TablaSimbolos.getTipoArray(res);
            String tipoElemento=TablaSimbolos.float_int_variable(arg2);;
            
            if(tipoElemento.equals("VARIABLE")){
                tipoElemento=TablaSimbolos.getTipo(arg2);
            }
           
           
            if(!asig_Array_ALL){ //TENGO QUE CASTEAR LOS TIPOS, ESTO ES PARA IMPRIMIR x[5]= (float) 4, ó y[3] = 5 ó saltar error

                // PLXC.out.println("TIPO DE VARIABLES: "+ tipoArray+"   "+tipoElemento);
            
                //Controlo los tipos y realizo casteos al asignar elementos al array
                if(tipoArray.equals("FLOAT") && tipoElemento.equals("INT")){  //int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
                    String t=Generator.nuevaTemp();
                    PLXC.out.println("\t" + t +" = (float) "+arg2+";");
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+t+";");
                    
                    
                }else if(tipoArray.equals("INT") && tipoElemento.equals("FLOAT")){
                    PLXC.out.println("#error; Se esta intentando asignar a un array de INT un FLOAT \nerror;");
                    System.exit(-1);
                }else{
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+arg2+";");
                }
            
           }else{ //Esto es para NO castear los tipos, aqui venimos cuando nos llaman de: float x[3] ; x = {1.1, 2, 3.3};     ó   float x[3] = {1.1, 2, 3.3}; Asique soltamos error.
            
                if(!tipoArray.equals(tipoElemento)){  //int x ; x=5.7 ERROR /// float x ; x=5 BIEN --> x= (float) 5
                    PLXC.out.println("#error; Se esta intentando asignar a un array tipos incompatibles \nerror;");
                    System.exit(-1);
                    
                }else{
                    PLXC.out.println("\t" + res + "["+arg1+"] = "+arg2+";");
                    
                }
            

           }

                

        }else if(inst == ASIG_ARRAY){;
            PLXC.out.println("\t" + res + " = "+arg1+ "["+arg2+"];");

    }
}
}
