package newtonsmethod;

import java.io.*;

public class NewtonsMethod {
    
    public static double NewtonsMethod(double x, long n){
        double previous = 1; //previous == X[n]
        double next = previous; //next == X[n+1]
        double f = Math.pow(previous, n) - x; //function on X[n]
        double fslope = n*(Math.pow(previous, (n-1))); //slope on X[n]

        if (x < 0 || n <=0 ) {System.out.println("error"); return 0;}
        else if (x == 0) return 0;
        else if (n == 1 || x == 1) return x;
        
        int repetitions = 100;
        
        System.out.println("x[1] = " + previous + "\n");

        for (int i = 0; i < repetitions; i++){
            System.out.println("x[" + (i+1) + "] = " + previous);
            System.out.println("f(x[" + (i+1) + "]) = " + f);
            System.out.println("f'(x[" + (i+1) + "]) = " + fslope);
            System.out.print("-> ");
          
            next = previous - (f/fslope);
            
            System.out.println("x[" + (i+2) + "] = " + next + "\n");

            if (Math.abs(next - previous) < 0.000000000000001) break; //precision

            f = Math.pow(next, n) - x; //function on X[n]
            fslope = n*(Math.pow(next, (n-1))); //slope on X[n]
            previous = next;
        }
        
        return next;
    }

    
    public static void main(String[] args) throws IOException {
        //n root of x
        double x = 2;
        long n = 2;
        
        System.out.println("result = " + NewtonsMethod(x, n));
    }
}