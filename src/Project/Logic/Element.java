package Project.Logic;

import java.util.Arrays;

public class Element {
    private int intervalFromA; //a - интервал работы элементо - От a
    private int intervalToB; //b - интервал работы элементо - До b
    private double[] probabilityTimeline; //вероятность безотказной работы элемента с простоями
    private double[] probability; //вероятность безотказной работы элемента без простоев
    private String typeOfDistribLaw; //закон распределения вероятности
    private double[] paramsOfDistribLaw; //параметры закона распределения
    private int averageTimeWork; //среднее время безотказной работы элемента без простоя
    private int averageTimeWorkTimeline; //среднее время безотказной работы элемента с простоями


    public Element(int periodSystemWork, int intervalFromA, int intervalToB, String typeOfDistribLaw,
                   double[] paramsOfDistribLaw, int[] k, int step) {
        this.intervalFromA = intervalFromA;
        this.intervalToB = intervalToB;
        this.typeOfDistribLaw = typeOfDistribLaw;
        this.paramsOfDistribLaw = paramsOfDistribLaw;

        setProbabylityTimeline(periodSystemWork, k, step); //подсчёт вероятности
        setProbabylity(typeOfDistribLaw, paramsOfDistribLaw, k, step);
        setAverageTimeWork();
        setAverageTimeWorkTimeline(k, periodSystemWork);
    }


    private void setProbabylityTimeline(int periodSystemWork, int[] k, int step) {
        double[] p = new double[k.length];
        int t1;
        for (int i = 0, t = 0; i < k.length; i++, t += step) {
            if ((0 <= t) && (t <= intervalFromA)) {
                p[i] = 1.;
            } else if ((t > k[i] * periodSystemWork + intervalFromA) && (t <= k[i] * periodSystemWork + intervalToB)) {
                t1 = t - k[i] * (periodSystemWork - intervalToB + intervalFromA) - intervalFromA;
                p[i] = Methods.probabilityCalculate(typeOfDistribLaw, paramsOfDistribLaw, t1 );
            } else /*if ((k[i] > 0) && (t > (k[i] - 1) * periodSystemWork + intervalToB) && (t <= k[i] * periodSystemWork + intervalFromA))*/ {
                t1 = k[i] * (intervalToB - intervalFromA);
                p[i] = Methods.probabilityCalculate(typeOfDistribLaw, paramsOfDistribLaw, t1 );
            }
        }
        probabilityTimeline = p;
    } //Формула (8.1) подсчёт вероятнсти безотказной работы элемента с интервалами простоя

    private void setProbabylity(String typeOfDistribLaw, double[] paramsOfDistribLaw, int[] k, int step) {
        double[] p = new double[k.length];
        for (int i = 0, t = 0; i < k.length; i++, t += step) {
            p[i] = Methods.probabilityCalculate(typeOfDistribLaw, paramsOfDistribLaw, t);
        }
        probability = p;
    } //подсчёт вероятнсти безотказной работы элемента без интервалов простоя - просто формула для распределения
    // получается что для каждого элемента она одинаковая

    private void setAverageTimeWork() {
        averageTimeWork = (int) Methods.initAvarageTimeWork(typeOfDistribLaw, paramsOfDistribLaw);
    }

    public void setAverageTimeWorkTimeline(int[] k, int periodSystemWork) {
        /*int from = 0;
        for (int i = 0; i < k.length; i++ ) {
            if (k[i] == 1) {
                from = i;
                break;
            }
        }
        double p = 0.;
        for (int i = 0, j = from; i < k.length - from; i++, j++) {
            p += Methods.probabilityCalculate(typeOfDistribLaw, paramsOfDistribLaw, k[j] * (intervalToB - intervalFromA));
        }
        averageTimeWorkTimeline = (int) (averageTimeWork + intervalFromA + (periodSystemWork - intervalToB + intervalFromA) * p);
        System.out.println();*/
        //отсчёт к начинается с 1, поэтому сначала нужно найти позицию
        double p = 0.;
        for (int i = 0; i < 5000; i++) {
            p += Methods.probabilityCalculate(typeOfDistribLaw, paramsOfDistribLaw, (i + 1) * (intervalToB - intervalFromA));
        }
        averageTimeWorkTimeline = (int) (averageTimeWork + intervalFromA + (periodSystemWork - intervalToB + intervalFromA) * p);
        System.out.println();


    }

    public double[] getProbabilityTimeline() {
        return probabilityTimeline;
    }

    public double[] getProbability() {
        return probability;
    }

    public int getIntervalFromA() {
        return intervalFromA;
    }

    public int getIntervalToB() {
        return intervalToB;
    }

    public String getTypeOfDistribLaw() {
        return typeOfDistribLaw;
    }

    public double[] getParamsOfDistribLaw() {
        return paramsOfDistribLaw;
    }

    public int getAverageTimeWork() {
        return averageTimeWork;
    }

    public int getAverageTimeWorkTimeline() {
        return averageTimeWorkTimeline;
    }

    @Override
    public String toString() {
        return "Element{" +
                "intervalFromA=" + intervalFromA +
                ",\n intervalToB=" + intervalToB +
                ",\n probabilityTimeline=" + Arrays.toString(probabilityTimeline) +
//                ",\n probability=" + Arrays.toString(probability) +
                ",\n typeOfDistribLaw='" + typeOfDistribLaw + '\'' +
                ",\n paramsOfDistribLaw=" + Arrays.toString(paramsOfDistribLaw) +
                ",\n averageTimeWork=" + averageTimeWork +
                ",\n averageTimeWorkTimeline=" + averageTimeWorkTimeline +
                '}';
    }
}
