import model.Espressione;
import model.Frazione;

import java.util.Scanner;

public class Test {
    static void main() {
//        Frazione f1 = null, f2 = null, exponent;
//        Scanner sc = new Scanner(System.in);
//        long n1, n2, d1, d2;
//
//        System.out.println("numeratore 1:");
//        n1 = sc.nextLong();
//        System.out.println("denominatore 1:");
//        d1 = sc.nextLong();
//        System.out.println("numeratore 2:");
//        n2 = sc.nextLong();
//        System.out.println("denominatore 2:");
//        d2 = sc.nextLong();
//        System.out.println("esponente:");
//        exponent = new Frazione(sc.nextInt(), 1);
//        try{
//            f1 = new Frazione(n1, d1);
//            f2 = new Frazione(n2, d2);
//        }catch(ArithmeticException ex){
//            System.out.println(ex.getMessage());
//        }
//        System.out.println(f1);
//        System.out.println(f2);
//        System.out.println(f1 + " * " + f2 + " = " + f1.mult(f2));
//
//        try {
//            System.out.println(f1 + " / " + f2 + " = " + f1.div(f2));
//        } catch (ArithmeticException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(f1 + " + " + f2 + " = " + f1.add(f2));
//        System.out.println(f1 + " - " + f2 + " = " + f1.sub(f2));
//
//        try {
//            System.out.println(f1 + "^" + exponent + " = " + f1.pow(exponent));
//        } catch (ArithmeticException e) {
//            System.out.println(e.getMessage());
//        }
        Espressione exp = new Espressione("(22/31-17/12)^2");
        exp.scanner();
        System.out.println(exp.getTokensExpr());

    }

}
