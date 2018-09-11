package Project.Logic;

import Project.Logic.Interfaces.Constants;

public class MainTest implements Constants {
    public static void main(String[] args) {
//        int variant = 0;
//        int countElement = 4;
//        int periodSystemWork = 10;
//        int timeSystemWork = 500;
//        int step = 5;

        int variant = 1;            //2
        int countElement = 5;       //2
        int periodSystemWork = 10;  //2
        int timeSystemWork = 1000;   //2
        int step = 10;               //2

        int[] paramScheme = new int[COUNT_PARAM_SCHEME];
        paramScheme[0] = variant;
        paramScheme[1] = countElement;
        paramScheme[2] = periodSystemWork;
        paramScheme[3] = timeSystemWork;
        paramScheme[4] = step;


        int[][] intervals = new int[countElement][COUNT_INTERVALS];

        intervals[0][0] = 0;
        intervals[0][1] = 5;

        intervals[1][0] = 2;
        intervals[1][1] = 3;

        intervals[2][0] = 5;
        intervals[2][1] = 8;

        intervals[3][0] = 0;
        intervals[3][1] = 7;

        intervals[4][0] = 5;        //2
        intervals[4][1] = 6;        //2

        String[] typeOfDistribLaw = new String[countElement];
        for (int i = 0; i < countElement; i++) {
            typeOfDistribLaw[i] = "exp";
        }
//        typeOfDistribLaw[countElement - 1] = "relay"; //2
//        typeOfDistribLaw[countElement - 3] = "relay"; //2

        double[][] paramsDistribLaw = new double[countElement][COUNT_PARAMS_DISTRIB_LAW];
        double lambda = 0.002;

        for (int i = 0; i < countElement; i++) {
            paramsDistribLaw[i][0] = lambda;
            paramsDistribLaw[i][1] = 0;
        }

//        paramsDistribLaw[0][0] = lambda;
//        paramsDistribLaw[0][1] = 0;
//
//        paramsDistribLaw[1][0] = lambda;
//        paramsDistribLaw[1][1] = 0;
//
//        paramsDistribLaw[2][0] = lambda;
//        paramsDistribLaw[2][1] = 0;
//
//        paramsDistribLaw[3][0] = lambda;
//        paramsDistribLaw[3][1] = 0;
//
//        paramsDistribLaw[4][0] = lambda;
//        paramsDistribLaw[4][1] = 0;


        Scheme scheme1 = new Scheme(paramScheme, paramsDistribLaw, intervals, typeOfDistribLaw);
        System.out.println(scheme1.toString());

        System.out.println();

        for (int i = 0; i < scheme1.getElements().size(); i++) {
            System.out.println(scheme1.getElements().get(i).toString());
            System.out.println();
        }

        System.out.println(String.format("%.5f%n", scheme1.getProbabRSP()[10]));

    }
}
