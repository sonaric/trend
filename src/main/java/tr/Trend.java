package tr;

import java.util.ArrayList;
import java.util.Arrays;

public class Trend {
    private double[] x;
    private double[] y;
    private double[] b;
    private int n;
    private double[] grb = new double[3];
    private ArrayList<Double> trend_v;
    private double fisherCreterion;
    private double studentCreterion;
    private ArrayList<ArrayList<Double>> resiltList;
    private double[] rand_m;
    private double[] rand_a;
    private ArrayList<Double> corrFuncValue;
    private double kv_ST = 1.6794273927;
    private double dM;
    private ArrayList<ArrayList<Double>> yearForecast;

    public ArrayList<ArrayList<Double>> getYearForecast() {
        return yearForecast;
    }

    public ArrayList<Double> getCorrFuncValue() {
        return corrFuncValue;
    }

    public ArrayList<ArrayList<Double>> getResiltList() {
        return resiltList;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public ArrayList<Double> getTrend_v() {
        return trend_v;
    }

    public void setB(double ... b){
        this.b = new double[b.length];

        for(int i = 0; i < b.length; i++){
            this.b[i] = b[i];
        }
        System.out.println(Arrays.toString(b));

    }

    public ArrayList<Double> getB(){
        ArrayList<Double> listB = new ArrayList<Double>();

        for(int i = 0; i < b.length; i++){
            listB.add(b[i]);
        }

        return listB;
    }

    public void setX(double []x){
        this.x = x;
    }

    public void setY(double []y){
        this.y = y;
    }

    public void setN(int n){
        this.n = n;
    }

    private double searchF(){
        double f = 0;
        for(int i=0; i<n; i++){
            f = f + Math.pow(y[i] - (b[0] + (b[1] * x[i]) + (b[2] * Math.pow(x[i], 2))), 2);
        }
        return f;
    }

    private void searchGrb(){
        grb[0]=0;
        grb[1]=0;
        grb[2]=0;

        for(int i=0; i<n; i++) {
            grb[0] = grb[0] + 2 * (y[i] - (b[0] + (b[1] * x[i]) + (b[2] * Math.pow(x[i], 2)))) * (-1);
            grb[1] = grb[1] + 2 * (y[i] - (b[0] + (b[1] * x[i]) + (b[2] * Math.pow(x[i], 2)))) * ((-1) * x[i]);
            grb[2] = grb[2] + 2 * (y[i] - (b[0] + (b[1] * x[i]) + (b[2] * Math.pow(x[i], 2)))) * ((-1) * Math.pow(x[i], 2));
        }
    }

    private boolean calcError(double[] grb){
        return Math.pow(Math.pow(grb[0],2)+Math.pow(grb[1],2)+Math.pow(grb[2],2),2)>0.01;
    }

    public ArrayList<Double> searchTrend(){
        double[] hb = {0.000001, 0.000001, 0.00000001};
        trend_v = new ArrayList<Double>();

        searchGrb();

        double fold=0, fnew=0;

        do{
            do{
                fold=searchF();

                for( int i = 0; i < b.length; i++)
                    b[i]=b[i]-(hb[i]*grb[i]);

                fnew=searchF();

            }while (fnew>fold);

            searchGrb();

        }while (calcError(grb));

        for(int i=0; i<n; i++) {
            trend_v.add(b[0] + b[1] * x[i] + b[2] * Math.pow(x[i], 2));
        }

        return trend_v;
    }

    public double[] checkFisherStudent(){
        double []res = new double[3];
        double y_s = 0;
        for(int i = 0; i<n; i++){
            y_s = y_s + y[i];
        }
        y_s = y_s/(double)n;


        for(int i=0; i<n; i++){
            dM = dM+Math.pow((y[i]-trend_v.get(i)), 2);
        }

        dM = dM/(n-3);

        fisherCreterion = 0;
        for (int i=0; i<n; i++){
            fisherCreterion = fisherCreterion + Math.pow((trend_v.get(i)-y_s),2)/(3-1);
        }
        fisherCreterion = fisherCreterion/dM;

        System.out.println("Fisher: "+ fisherCreterion);

        double xsr = 0;
        for (int i =0; i< n; i++){
            xsr = xsr + x[i];
        }

        xsr = xsr / n;

        double sum1=0, sum2=0;
        for(int i=0; i< n; i++){
            sum1=sum1+Math.pow(x[i], 2);
            sum2=sum2+Math.pow((x[i]-xsr), 2);
        }

        double s0=0, s1=0, t0=0, t1=0;
        s0 = Math.sqrt((dM*sum1/sum2/n));
        s1=Math.sqrt((dM/sum2));

        t0 = b[0]/s0;
        t1 = b[1]/s1;

        System.out.println("Student: "+t1);
        res[0]=fisherCreterion;
        res[1]=t0;
        res[2]=t1;
        return res;
    }

    public void seassonComponent(boolean val){
        resiltList = new ArrayList<>();
        double sez_m[] = new double[n];
        double sez_sr_m[] = new double[n];

        if(!val){
            for (int i =0; i<n; i++){
                sez_m[i]=y[i]/trend_v.get(i);
            }
            for(int i=0; i<n/2; i++){
                sez_sr_m[i]=0;
                for(int k=0; k<2; k++){
                    sez_sr_m[i]=sez_sr_m[i]+(sez_m[i+k*(n/2)])/2;
                }
            }
            for(int i=n/2; i<n; i++){
                sez_sr_m[i]=0;
                sez_sr_m[i]=sez_sr_m[i-(n/2)];
                sez_m[i]=sez_sr_m[i];
            }
            ArrayList<Double> sez_list = new ArrayList<>();

            for(int i=0; i<n; i++){
                sez_list.add(sez_sr_m[i]);
            }

            resiltList.add(sez_list);
        }

        rand_m = new double[n];

        if(!val){
            for(int i=0; i<n; i++){
                rand_m[i]=y[i]/trend_v.get(i)/sez_sr_m[i];
            }
            ArrayList<Double> rand_list = new ArrayList<>();
            for(int i=0; i<n; i++){
                rand_list.add(rand_m[i]);
            }
            resiltList.add(rand_list);
        }

        double sez_a[]=new double[n];
        double sez_sr_a[]=new double[n];

        if(val){
            for(int i=0; i<n; i++){
                sez_a[i]=y[i]-trend_v.get(i);
            }
            for(int i=0; i<n/2; i++){
                sez_sr_a[i]=0;
                for(int k=0; k<2; k++){
                    sez_sr_a[i]=sez_sr_a[i]+(sez_a[i+k*(n/2)])/2;
                }
            }
            for(int i=n/2; i<n; i++){
                sez_sr_a[i]=0;
                sez_sr_a[i]=sez_sr_a[i-(n/2)];
            }
            ArrayList<Double> sez_list = new ArrayList<>();

            for(int i=0; i<n; i++){
                sez_list.add(sez_sr_a[i]);
            }

            resiltList.add(sez_list);
        }
        rand_a = new double[n];

        if(val){
            for(int i=0; i<n; i++){
                rand_a[i]=y[i]-trend_v.get(i)-sez_sr_a[i];
            }
            ArrayList<Double> rand_list = new ArrayList<>();
            for(int i=0; i<n; i++){
                rand_list.add(rand_a[i]);
            }
            resiltList.add(rand_list);
        }

    }

    public void corrFunc(boolean val){
        corrFuncValue = new ArrayList<>();
        double [] cor = new double[30];
        double d = 0;

        for(int i = 0; i<n;i++){
            d=d+Math.pow(rand_a[i],2);
        }
        d=d/n;

        if(!val){
           double d_m=0;
           for(int i=0; i<n; i++){
               d_m=d_m+Math.pow((rand_m[i]-1),2);
           }
           d_m=d_m/n;
           for(int k = 0; k < 30; k++){
               cor[k]=0;
               for (int i=0; i<n-k; i++){
                   cor[k]=cor[k]+(rand_m[i]-1)*(rand_m[i+k]-1)/n/d_m;
               }
           }
        }
        if(val){
            for (int k = 0; k<30; k++){
                cor[k]=0;
                for(int i=0; i<n-k; i++){
                    cor[k] = cor[k]+rand_a[i]*rand_a[i+k]/n/d;
                }
            }
        }
        for(int i=0; i< 30; i++){
            corrFuncValue.add(cor[i]);
        }
    }

    public void forecastYear(boolean val){
        yearForecast = new ArrayList<>();
        ArrayList<Double> y_new = new ArrayList<Double>();
        ArrayList<Double> vm_dop_y = new ArrayList<Double>();
        ArrayList<Double> nm_dop_y = new ArrayList<Double>();
        double tr_new[] = new double[12];;

        for(int i=0; i<12; i++){
            tr_new[i]=b[0]+b[1]*x[i]+Math.pow(b[2]*x[i],2);
        }

        if(!val){
            for(int i=0; i<12; i++){
                if (tr_new[i]<1)
                    tr_new[i]=1;
                y_new.add(tr_new[i]*getResiltList().get(0).get(i));
                vm_dop_y.add(y_new.get(i)+kv_ST*Math.sqrt(dM));
                nm_dop_y.add(y_new.get(i)-kv_ST*Math.sqrt(dM));
            }
        }
        if(val){
            for(int i=0; i<12; i++){
                if (tr_new[i]<0)
                    tr_new[i]=0;
                y_new.add(tr_new[i]+getResiltList().get(0).get(i));
                vm_dop_y.add(y_new.get(i)+kv_ST*Math.sqrt(dM));
                nm_dop_y.add(y_new.get(i)-kv_ST*Math.sqrt(dM));
            }
        }
        System.out.println(kv_ST*Math.sqrt(dM));
        yearForecast.add(vm_dop_y);
        yearForecast.add(nm_dop_y);
        yearForecast.add(y_new);
    }

    public static void main(String[] args){
        Trend tr = new Trend();
        double[] x = new double[24];
        double[] y = {1,2,3,4,3,2,3,4,5,4,3,4,6,5,4,3,4,3,2,4,3,2,1,2};

        for(int i=0; i<24; i++){
            x[i]=i+1;
        }

        tr.setB(1,1,0);
        tr.setN(24);
        tr.setX(x);
        tr.setY(y);

        tr.searchTrend().forEach(System.out::println);
        tr.getB().forEach(System.out::println);
        tr.checkFisherStudent();
    }


}
