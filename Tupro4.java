import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Tupro4 {
    static double p1or2(String[][] train, String a){
        int b=0;
        for (int i = 0; i < train.length; i++) {
            if (a.equals(train[i][2])) b++;
        }
        return (double) b/train.length;
    }
    static double mean(String[][] train, int a, String b){
        double c = 0;
        int d = 0;
        for (int i = 0; i < train.length; i++) {
            if (b.equals(train[i][2])){
                c= c + Double.parseDouble(train[i][a]);
                d++;
            }
        }
        return (double) c/d;
    }
    static double std(String[][] train, int a, String b){
        double rata = mean(train,a,b);
        double c = 0;
        int d = 0;
        double f = 0;
        for (int i = 0; i < train.length; i++) {
            if (b.equals(train[i][2])){
                d++;
                c += Math.pow((Double.parseDouble(train[i][a])-rata), 2);
            }
        }
       return Math.sqrt(c/(d-1));
    }
    static String[][] subhimpunan(String[][] train){
        String[][] model=new String[298][3];
        for (int i = 0; i < model.length; i++) {
            Random rand = new Random();
            int r = rand.nextInt(298);
            model[i] = train[r];
        }
        return model;
    }
    static double naivebayes(String[][] train, String b, String[] test){
        double a = p1or2(train, b);
        double mean0 = mean(train, 0, b); double mean1 = mean(train, 1, b);
        double std0 = std(train, 0, b); double std1 = std(train, 1, b);
        return a*(Math.pow(Math.E, -(Math.pow((Double.parseDouble(test[0])-mean0)/std0, 2)/2)))/std0*Math.sqrt(2*Math.PI)*
              (Math.pow(Math.E, -(Math.pow((Double.parseDouble(test[1])-mean1)/std1, 2)/2)))/std1*Math.sqrt(2*Math.PI);
    }
    static double getLabel(String[][] train, String[] test){
        double a = naivebayes(train, "1",test);
        double b = naivebayes(train, "2",test);
        if (a>b) return 1;
        else return 2;
    }
    static String voting(double label1, double label2, double label3, double label4, double label5, double label6, double label7){
        double a = label1+label2+label3+label4+label5+label6+label7;
        if (a<11) return "1";
        else return "2";
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File filetrain= new File("TrainsetTugas4ML.xls");
        File filetest= new File("TestsetTugas4ML.xls");
        Scanner tes= new Scanner(filetest);
        tes.nextLine();
        Scanner tra = new Scanner(filetrain);
        tra.nextLine();
        String[][] train=new String[298][3];
        String[][] model1=new String[298][3]; String[][] model2=new String[298][3]; String[][] model3=new String[298][3];
        String[][] model4=new String[298][3]; String[][] model5=new String[298][3]; String[][] model6=new String[298][3];
        String[][] model7=new String[298][3];
        int i = 0;
        double a=0;
        while (tra.hasNext()){
            train[i]=tra.nextLine().split(",");
            i++;
        }
        model1 = subhimpunan(train);
        model2 = subhimpunan(train);
        model3 = subhimpunan(train);
        model4 = subhimpunan(train);
        model5 = subhimpunan(train);
        model6 = subhimpunan(train);
        model7 = subhimpunan(train);
        FileWriter fw = new FileWriter("TebakanTugas4ML.csv");
        i = 0;
        while (tes.hasNext()){
            String[] test = tes.nextLine().split(",");
            double label1 = getLabel(model1, test);
            double label2 = getLabel(model2, test);
            double label3 = getLabel(model3, test);
            double label4 = getLabel(model4, test);
            double label5 = getLabel(model5, test);
            double label6 = getLabel(model6, test);
            double label7 = getLabel(model7, test);
            i++;
            try{
               fw.append(String.valueOf(voting(label1,label2,label3,label4,label5,label6,label7))+'\n');
            }catch(Exception e){}
        }
        fw.flush();
        fw.close();
    }
}