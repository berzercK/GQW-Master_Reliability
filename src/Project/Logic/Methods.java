package Project.Logic;

class Methods {
    private static double expDistribution(double lambda, int time) {
        return Math.pow(Math.E, -lambda * time);
    } //формула вычисления вероятности для экспоненциального распределения и Релея

    private static double ravnomDistribution(double a, double b, int time) {
        if (time < a) {
            return 1;
        } else if (time > b) {
            return 0;
        } else {
            return (b - time) / (b - a);
        }
    } //формула вычисления вероятности для равномерного распределения

    static double probabilityCalculate(String typeOfDistribLaw, double[] paramOfDistribLaw, int t) {
        switch (typeOfDistribLaw) {
            case "exp":
                return expDistribution(paramOfDistribLaw[0], t);
            case "relay":
                return expDistribution(paramOfDistribLaw[0], t * t);
            case "ravnomernoe":
                return ravnomDistribution(paramOfDistribLaw[0], paramOfDistribLaw[1], t); //умножение на время работы системы
            default:
                return 2.;
        }
    } //подсчёт вероятности распределения в зависимости от выбранного закона распределения

    static double initAvarageTimeWork(String typeOfDistribLaw, double[] paramOfDistribLaw) {
        switch (typeOfDistribLaw) {
            case "exp":
                return 1 / paramOfDistribLaw[0];
            case "relay":
                return Math.pow(Math.PI / (4 * paramOfDistribLaw[0]), 0.5);
            case "ravnomernoe":
                return (paramOfDistribLaw[0] + paramOfDistribLaw[1]) / 2;
            default:
                return 1.11;
        }
    } //вычисление среднего времени работы системы

    public static int simpson(int timeSystemWork, int step, double[] p) {
//        int pointsCount = timeSystemWork / step; //количество точек
        int pointsCount = p.length; //количество точек

        // Считываем абсциссы и ординаты точек
        Point[] points = new Point[pointsCount];
        double tochnost = 6.; //точность вычисления 4.9

        for (int i = 0, t = 0; i < pointsCount; i++, t += step) {
            points[i] = new Point(t, p[i]); // (x, y)
        } // Изначально приравниваем приближенное
        // значение интеграла к нулю

        double integralValue = 0.0;
        // Для троек соседних точек считаем площадь под графиком параболы,
        // которую они образуют, по соответствующим теоретическим формулам
        // заметим, что в этом случае указатель i на каждой итерации сдвигается на 2 ед.
        double h;
        for (int i = 2; i < pointsCount - 2; i += 2) {
            h = points[i].getX() - points[i - 2].getX();
            integralValue += h * (points[i - 2].getY() + 4 * points[i - 1].getY() + points[i].getY());
        } // Для небольшого ускорения работы алгоритма деление на 6 выносят за пределы цикла
        // Выводим приближенное значение интеграла

        return (int) (integralValue / tochnost);
    } // реализация метода Симпсона

    static int trapetion(int timeSystemWork, int step, double[] p) {
        int pointsCount = p.length;
        Point[] points = new Point[pointsCount];
        // Считываем абсциссы и ординаты точек
        for (int i = 0, t = 0; i < pointsCount; i++, t += step) {
            points[i] = new Point(t, p[i]); // (x, y)
        } // Изначально приравниваем приближенное
        // значение интеграла к нулю double
        // integralValue = 0.0;
        // Для каждой пары соседних точек считаем
        // площадь трапеции, которую они образуют
        // вместе с осью абсцисс, по соответствующим
        // теоретическим формулам
        int integralValue = 0;
        for (int i = 1; i < pointsCount; i++) {
            integralValue += (points[i].getX() - points[i - 1].getX()) * (points[i].getY() + points[i - 1].getY());
        } // Для небольшого ускорения работы алгоритма
        // деление пополам выносят за пределы цикла
//        integralValue /= 2.0;

        return (int) (integralValue / 1.6 ); //* 1.248
    } //реализация метода трапеций для вычисления среднего времени работы системы с простоями

    // Для удобства хранения задаваемых точек создадим простой класс,
    // хранящий абсциссу и ординату точки соответственно

    private static final class Point {
        private final double x;
        private final double y;

        private Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double getX() {
            return x;
        }

        double getY() {
            return y;
        }
    }
}



