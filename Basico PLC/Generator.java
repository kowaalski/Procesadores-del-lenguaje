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
            PLC.out.println("\t" + res + " = " + arg1 + " + " + arg2 + ";");
        } else if (inst == sym.MENOS) {
            PLC.out.println("\t" + res + " = " + arg1 + " - " + arg2 + ";");
        } else if (inst == sym.POR) {
            PLC.out.println("\t" + res + " = " + arg1 + " * " + arg2 + ";");
        } else if (inst == sym.PRINT) {
            PLC.out.println("\t" + "print " + res + ";");
        } else if (inst == sym.AND) {
            PLC.out.println(res + "=" + arg1 + "&&" + arg2);
        } else if (inst == IFGOTO) {
            PLC.out.println("\tif (" + arg1 + arg2 + ") goto " + res + ";");
        } else if (inst == GOTO) {
            PLC.out.println("\tgoto " + res + ";");
        } else if (inst == LABEL) {
            PLC.out.println(res + ":");
        } else if (inst == sym.ELSE) {
            PLC.out.println(res + "=" + arg1 + "/" + arg2);
        } else if (inst == sym.MINUS) {
            PLC.out.println("\t" + res + " = -" + arg1 + ";");
        } else if (inst == sym.ENTERO) {
            PLC.out.println("\t" + res + " = " + arg1);
        } else if (inst == sym.IDENT) {
            PLC.out.println("\t" + res + " = " + arg1 + ";");
        } else if (inst == sym.DIV) {
            PLC.out.println("\t" + res + " = " + arg1 + " / " + arg2 + ";");
        }
    }
}
