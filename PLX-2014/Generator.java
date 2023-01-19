public class Generator {
    public static int IFGOTO = -35;
    public static int GOTO = -36;
    public static int LABEL = -37;


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
            PLXC.out.println("\t" + res + " = " + arg1 + " + " + arg2 + ";");
        } else if (inst == sym.MENOS) {
            PLXC.out.println("\t" + res + " = " + arg1 + " - " + arg2 + ";");
        } else if (inst == sym.POR) {
            PLXC.out.println("\t" + res + " = " + arg1 + " * " + arg2 + ";");
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
            PLXC.out.println("\t" + res + " = " + arg1 + ";");
        } else if (inst == sym.DIV) {
            PLXC.out.println("\t" + res + " = " + arg1 + " / " + arg2 + ";");
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

            
        }
    }
}
