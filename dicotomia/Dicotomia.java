package babilonico;

import java.io.*;

public class Babilonico {
    
    public static double babilonico(double x, long n){
        double result;
        double max = (x < 1)? 1 : x; //superior limit
        double min = 0; //inferior limit
        double medium = (max+min)/2; //medium
        
        if (x < 0 || n <=0 ) {System.out.println("error"); return -1;}
        else if (x == 0) return 0;
        else if (n == 1 || x == 1) return x;
        
        int repetitions = 100;
        
        for (int i = 0; i < repetitions; i++) {
            System.out.println(i+1);
            System.out.println("min = " + min);            
            System.out.println("max = " + max);
            System.out.println("medium = " + medium);
            System.out.print("-> ");
            
            double power = Math.pow(medium, n);
            
            if (power < x){
                min = medium;
                System.out.println("new min = " + min);
            } else if (power == x) return medium;
            else { // > x
                max = medium;
                System.out.println("new max = " + max);
            }
            medium = (max+min)/2;
            System.out.println();
            if (max - min < 0.000000000000001) break; //precision
        }
        System.out.println("precision range: between " + min + " and " + max);
        System.out.println("margin of error = +- " + (max - medium));

        result = medium;
        return result;
    }

    
    public static void main(String[] args) throws IOException {
        //n square of x
        double x = 239874537;
        long n = 275847584;
        
        System.out.println("\n" + "result = " + babilonico(x, n));
    }
}