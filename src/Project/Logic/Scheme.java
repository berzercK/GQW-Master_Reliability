package Project.Logic;

import java.util.ArrayList;
import java.util.Arrays;

public class Scheme {
    private int variant; //вариант схемы
    private int countElement; //количесвтво элементов системы n
    private int periodSystemWork; // период работы системы тао = 10 часов
    private int timeSystemWork; //полное время работы системы t
    private int step; //шаг
    private int[] k; //параметр
    private int[] t; //массив для вывода в таблицу
    private ArrayList<Element> elements; //коллекция элементов системы

    private double[] probabRS; //вероятность безотказной работы системы
    private double[] probabRSP; // вероятность безотказной работы системы с простоями

    public Scheme(int[] paramsScheme, double[][] paramsDistribLaw, int[][] intervals, String[] typeOfDistribLaw) {
        // paramScheme = { int variant, int countElement, int periodSystemWork, int timeSystemWork, int step}
        // param paramsDistribLaw = { smth1(например lambda), smth2} - параметры для законов распределения
        this.variant = paramsScheme[0];
        this.countElement = paramsScheme[1];
        this.periodSystemWork = paramsScheme[2];
        this.timeSystemWork = paramsScheme[3];
        this.step = paramsScheme[4];
        setK();
        setT();
//        setImage();
        setElements(typeOfDistribLaw, paramsScheme, paramsDistribLaw, intervals);
        setProbability();
        setAvarageTime();
    }


    //    private Image image;
    public double getAvarageTimeWork() {
        return avarageTimeWork;
    }

    private void setT() {
        t = new int[timeSystemWork / step + 1];
        t[0] = 0;

        for (int i = 1; i < t.length; i++) {
            t[i] =t[i - 1] + step;
        }
    }

    public int[] getT() {
        return t;
    }

    public double getAvarageTimeWorkTIMELINE() {
        return avarageTimeWorkTIMELINE;
    }

    private double avarageTimeWork; //среднее время безотказной работы системы без учёта времент простоя
    private double avarageTimeWorkTIMELINE; //среднее время безотказной работы системы с учётом простоя элементов


    @Override
    public String toString() {
        return "Scheme{" +
                "\nvariant=" + variant +
                ",\n countElement=" + countElement +
                ",\n periodSystemWork=" + periodSystemWork +
                ",\n timeSystemWork=" + timeSystemWork +
                ",\n step=" + step +
                ",\n k=" + Arrays.toString(k) +
//                ", elements=" + elements +
                ",\n probabRS=" + Arrays.toString(probabRS) +
                ",\n probabRSP=" + Arrays.toString(probabRSP) +
                ",\n avarageTimeWork=" + avarageTimeWork +
                ",\n avarageTimeWorkTIMELINE=" + avarageTimeWorkTIMELINE +
                '}';
    }

    private void setAvarageTime() {
        avarageTimeWork = Methods.simpson(timeSystemWork, step, probabRS);
        avarageTimeWorkTIMELINE = Methods.trapetion(timeSystemWork, step, probabRSP);
    }

//тут должен быть новый конструктор для занесения параметров при чтении из файла


//    private void setImage() {
//        String path = "src/Files/image/schemes/" + variant + ".PNG";
//        image = new Image(path);
//    }

    private void setK() {
        int[] k = new int[timeSystemWork / step + 1];
        for (int i = 0, t = 0; i < timeSystemWork / step + 1; i++, t += step) {
            k[i] = t / periodSystemWork;
        }
        this.k = k;
    } // создаю массив параметра k размером timeSystemWork


    private void setElements(String[] typeOfDistribLaw, int[] paramsScheme, double[][] paramsDistribLaw, int[][] intervals) {
        elements = new ArrayList(countElement);
        for (int i = 0; i < countElement; i++) {
            elements.add(new Element(paramsScheme[2], intervals[i][0], intervals[i][1], typeOfDistribLaw[i], paramsDistribLaw[i], k, step));
        }
    } // инициализация элементов системы и их параметров


    //расчёт безотказной работы системы
    private void setProbability() {
        probabRS = new double[k.length];
        probabRSP = new double[k.length];
        double[][] p = new double[countElement][k.length];
        double[][] pP = new double[countElement][k.length];

        for (int j = 0; j < countElement; j++) {
            p[j] = elements.get(j).getProbability();
            pP[j] = elements.get(j).getProbabilityTimeline();
        }

        for (int i = 0, n = 0; i < k.length; i++) {
            if (variant == 0) {
                probabRS[i] = 1 - (1 - p[n][i] * p[n + 1][i]) * (1 - p[n + 2][i] * p[n + 3][i]);
                probabRSP[i] = 1 - (1 - pP[n][i] * pP[n + 1][i]) * (1 - pP[n + 2][i] * pP[n + 3][i]);
            }
            if (variant == 1) {
                probabRS[i] = (1 - (1 - p[n][i]) * (1 - p[n + 3][i])) * (1 - (1 - p[n + 1][i]) * (1 - p[n + 4][i])) * p[n + 2][i];
                probabRSP[i] = (1 - (1 - pP[n][i]) * (1 - pP[n + 3][i])) * (1 - (1 - pP[n + 1][i]) * (1 - pP[n + 4][i])) * pP[n + 2][i];
            }
            if (variant == 2) {
                probabRS[i] = (1 - (1 - p[n + 1][i]) * (1 - p[n + 2][i]) * 1 - p[n + 3][i]) * p[n][i] * p[n + 4][i];
                probabRSP[i] = (1 - (1 - pP[n + 1][i]) * (1 - pP[n + 2][i]) * 1 - pP[n + 3][i]) * pP[n][i] * pP[n + 4][i];
            }
            if (variant == 3) {
                probabRS[i] = (1 - (1 - p[n][i]) * (1 - p[n + 1][i])) * (1 - (1 - p[n + 2][i]) * (1 - p[n + 3][i]));
                probabRSP[i] = (1 - (1 - pP[n][i]) * (1 - pP[n + 1][i])) * (1 - (1 - pP[n + 2][i]) * (1 - pP[n + 3][i]));
            }
        }

//        probabRS = pRS;
//        probabRSP = pRSP;

    } // без простоя и с простоем

    public double[] getProbabRS() {
        return probabRS;
    }

    public double[] getProbabRSP() {
        return probabRSP;
    }
    //    private double[] setProbabRSP_value() {
//        double[] pRSP = new double[0];
//        double[][] p = new double[countElement][timeSystemWork / step];
//
//        for (int j = 0; j < countElement; j++) {
//            p[j] = elements.get(j).getProbabilityTimeline();
//        }
//
//        for (int i = 0, n = 0; i < k.length; i++) {
//            pRSP[i] = 1 - (1 - p[n][i] * p[n + 1][i]) * (1 - p[n + 2][i] * p[n + 3][i]);
//        }
//        return pRSP;
//    } // с простоем


    public int getVariant() {
        return variant;
    }

    public int getCountElement() {
        return countElement;
    }

    public int getPeriodSystemWork() {
        return periodSystemWork;
    }

    public int getTimeSystemWork() {
        return timeSystemWork;
    }

    public int getStep() {
        return step;
    }

    public int[] getK() {
        return k;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

}
