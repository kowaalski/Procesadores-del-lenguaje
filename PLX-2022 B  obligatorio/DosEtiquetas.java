public class DosEtiquetas {
    private String v;
    private String f;

    public DosEtiquetas() {
        this.v = Generator.nuevaLabel();
        this.f = Generator.nuevaLabel();
    }

    public void swap() {
        String temp = v;
        v = f;
        f = temp;
    }

    public String v() {
        return v;
    }

    public String f() {
        return f;
    }
}
