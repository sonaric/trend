package tr;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SearcherTrend {

    public static void main(String[] args){
        int n=24;
        int k_p=2;
        double kv_ST=2.0769;
        double[] x = new double[n];
        double[] y={1,2,3,4,3,2,3,4,5,4,3,4,6,5,4,3,4,3,2,4,3,2,1,2};
        for(int i=0; i<n; i++){
             x[i]=i+1;
        }
        double b0=1, b1=1, b2=0;
        double hb0=0.00001, hb1=0.00001, hb2=0.0000001;
        double grb0=0, grb1=0, grb2=0;

        for(int i=0; i<n; i++){
            grb0=grb0+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*(-1);
            grb1=grb1+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*((-1)*x[i]);
            grb2=grb2+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*((-1)*Math.pow(x[i],2));
        }

        System.out.println("First calc:\n"+grb0+" "+grb1+" "+grb2);

        double fold=0, fnew=0;

        do{
           do{
               fold=0;
               for(int i=0; i<n; i++){
                   fold=fold+Math.pow(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)) ,2);
               }

               b0=b0-hb0*grb0;
               b1=b1-hb1*grb1;
               b2=b2-hb2*grb2;

               fnew=0;
               for(int i=0; i<n; i++){
                   fnew=fnew+Math.pow(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)) ,2);
               }

           }while (fnew>fold);
            grb0=0;
            grb1=0;
            grb2=0;
            for(int i=0; i<n; i++){
                grb0=grb0+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*(-1);
                grb1=grb1+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*(-x[i]);
                grb2=grb2+2*(y[i]-(b0+b1*x[i]+b2*Math.pow(x[i],2)))*((-1)*Math.pow(x[i],2));
            }

            System.out.println("Iter calc:\n"+grb0+" "+grb1+" "+grb2);

        }while (Math.pow(Math.pow(grb0,2)+Math.pow(grb1,2)+Math.pow(grb2,2),2)>0.01);

        double[] tr = new double[n];

        for(int i=0; i<n; i++){
            tr[i]=b0+b1*x[i]+b2*Math.pow(x[i],2);
        }

        System.out.println(Arrays.toString(tr));
    }
}