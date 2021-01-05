package numero;

/**
 * z = a + b*i
 * @author Lucas Paz
 */
public class NumeroComplexo {

    private double a;
    private double b;

    public static final NumeroComplexo ZERO = new NumeroComplexo(0, 0);
    public static final NumeroComplexo I_ELEVADO_A_0 = new NumeroComplexo(1, 0);
    public static final NumeroComplexo I = new NumeroComplexo(0, 1);
    public static final NumeroComplexo I_ELEVADO_A_2 = new NumeroComplexo(-1, 0);
    public static final NumeroComplexo I_ELEVADO_A_3 = new NumeroComplexo(0, -1);

    public NumeroComplexo(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public NumeroComplexo mais(NumeroComplexo parcela) {
        NumeroComplexo soma = new NumeroComplexo(this.a + parcela.a, this.b + parcela.b);

        return soma;
    }

    public NumeroComplexo menos(NumeroComplexo subtraendo) {
        NumeroComplexo diferenca = new NumeroComplexo(this.a - subtraendo.a, this.b - subtraendo.b);

        return diferenca;
    }

    public NumeroComplexo vezes(NumeroComplexo fator) {
        NumeroComplexo produto = new NumeroComplexo(this.a*fator.a - this.b*fator.b, this.a*fator.b + this.b*fator.a);

        return produto;
    }

    public NumeroComplexo divididoPor(NumeroComplexo divisor) throws ArithmeticException {
        if (divisor.a == 0d && divisor.b == 0d) throw new ArithmeticException("Divisão por 0.");

        NumeroComplexo quociente;

        NumeroComplexo numerador = this.vezes(conjugadoDe(divisor));
        double novoA = numerador.a;
        double novoB = numerador.b;

        double denominador = divisor.a*divisor.a + divisor.b*divisor.b;

        novoA = novoA/denominador;
        novoB = novoB/denominador;

        quociente = new NumeroComplexo(novoA, novoB);

        return quociente;
    }

    public NumeroComplexo elevadoA(int expoente) throws ArithmeticException {
        NumeroComplexo potencia;

        if (expoente < 0) return I_ELEVADO_A_0.divididoPor(this.elevadoA( - expoente)); //throw new IllegalArgumentException("Expoente < 0.");
        else if (expoente == 0) return I_ELEVADO_A_0;
        else if (this.a == 0 && this.b == 0) return ZERO;
        else if (expoente == 1) return this;
        else if (expoente == 2) return this.vezes(this);
        else if (expoente == 3) return this.vezes(this).vezes(this);

        double novoModulo = Math.pow(this.getModulo(), expoente);
        double argAntigo = this.getArgEmRadianos();
        double novoA = novoModulo * Math.cos(expoente * argAntigo);
        double novoB = novoModulo * Math.sin(expoente * argAntigo);

        potencia = new NumeroComplexo(novoA , novoB);

        return potencia;
    }

    public NumeroComplexo[] raizes(int indice) throws ArithmeticException {
        if (indice < 0) return (I_ELEVADO_A_0.divididoPor(this)).raizes( - indice);
        else if (indice == 0) throw new ArithmeticException("Índice = 0.");
        else if (indice == 1) return new NumeroComplexo[]{this};

        NumeroComplexo raizes[] = new NumeroComplexo[indice];

        if (this.a == 0 && this.b == 0) {
            for (NumeroComplexo raiz : raizes) {
                raiz = ZERO;
            }
            return raizes;
        }

        double novoModulo = Math.pow(this.getModulo(), 1d/(double) indice);

        double argNovo=0d;
        double cosNovo=0d;
        double senNovo=0d;
        double novoA=0d;
        double novoB=0d;

        for (int k = 0; k < indice; k++) {
            argNovo = (this.getArgEmRadianos()/(double) indice) + k*((2*Math.PI)/(double) indice);
            cosNovo = Math.cos(argNovo);
            senNovo = Math.sin(argNovo);
            novoA = argNovo * cosNovo;
            novoB = argNovo * senNovo;

            raizes[k] = new NumeroComplexo(novoA, novoB);
        }

       return raizes;
    }

    public double getA() {
        return this.a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return this.b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public NumeroComplexo getConjugado(){
        return new NumeroComplexo(this.a, -this.b);
    }

    public static NumeroComplexo conjugadoDe(NumeroComplexo z){
        return new NumeroComplexo(z.a, -z.b);
    }

    public double getModulo() {
        return Math.pow(this.a*this.a + this.b*this.b, 1d/2d);
    }

    public static double moduloDe(NumeroComplexo z) {
        return Math.pow(z.a*z.a + z.b*z.b, 1d/2d);
    }

    public double getCosArgEmRad() throws ArithmeticException {
        return this.a/this.getModulo();
    }

    public static double cosArgEmRadDe(NumeroComplexo z) throws ArithmeticException {
        return z.a/moduloDe(z);
    }

    public double getSenArgEmRad() throws ArithmeticException {
        return this.b/this.getModulo();
    }

    public static double senArgEmRadDe(NumeroComplexo z) throws ArithmeticException {
        return z.b/moduloDe(z);
    }

    public double getArgEmRadianos() throws ArithmeticException {
        if (this.a == 0d && this.b == 0d) throw new ArithmeticException("a e b = 0.");

        double cos = getCosArgEmRad();
        double sen = getSenArgEmRad();

        double arcCos = Math.acos(cos);
        double arcSen = Math.asin(sen);

        if (sen >= 0d) {
            return arcCos;
        } else {
            return 2 * Math.PI - arcCos;
        }
    }

    public static double argEmRadianos(NumeroComplexo z) throws ArithmeticException {
        if (z.a == 0d && z.b == 0d) throw new ArithmeticException("a e b = 0.");

        double cos = cosArgEmRadDe(z);
        double sen = senArgEmRadDe(z);

        double arcCos = Math.acos(cos);
        double arcSen = Math.asin(sen);

        if (sen >= 0d) {
            return arcCos;
        } else {
            return 2 * Math.PI - arcCos;
        }
    }

    @Override
    public String toString() {
        //z = a + b*i
        return (this.a + " + " + this.b + "*i");
    }

    public String toStringNaFormaPolar() throws ArithmeticException {
        //z = |z|*(cos(arg) + i*sen(arg))
        double modulo = this.getModulo();
        double cos = this.getCosArgEmRad();
        double sen = this.getSenArgEmRad();

        return (this.getModulo() + "*(" + cos + " + i*" + sen + ")");
    }

}