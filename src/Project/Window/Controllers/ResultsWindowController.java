package Project.Window.Controllers;


import Project.Logic.Element;
import Project.Logic.Scheme;
import Project.Window.POJO.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ResultsWindowController {

    private int numberOfScheme = StartWindowController.countOfScheme - 1;

    @FXML
    private MenuItem open;

    @FXML
    private MenuItem saveAs;

    @FXML
    private MenuItem aboutProgram;

    @FXML
    private ImageView resultSchemeImg;

    @FXML
    private Label resultSchemeName;

    @FXML
    private Pane paneViewElements;

    @FXML
    private Pane paneView;

    @FXML
    private TextArea resultQuestions;

    @FXML
    private TextArea resultOutputArea;

    @FXML
    private Button saveFileButton;

    @FXML
    private TableView<Entry> resultTable;

    @FXML
    private TableColumn<Entry, Integer> columnT;

    @FXML
    private TableColumn<Entry, Integer> columnK;

    @FXML
    private TableColumn<Entry, Double> columnP1;

    @FXML
    private TableColumn<Entry, Double> columnP2;

    @FXML
    private TableColumn<Entry, Double> columnP3;

    @FXML
    private TableColumn<Entry, Double> columnP4;

    @FXML
    private TableColumn<Entry, Double> columnP5;

    @FXML
    private TableColumn<Entry, Double> columnRSP;

    @FXML
    private TableColumn<Entry, Double> columnRS;

    private boolean isExistWindiwInfo = false;

    @FXML
    void aboutProgramWI(ActionEvent event) {
        if (!isExistWindiwInfo) {
            isExistWindiwInfo = true;
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/info.fxml"));
                stage.setResizable(false);
                stage.setTitle("О программе");
                stage.setMinHeight(root.getScaleX());
                stage.setMinWidth(root.getScaleY());
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void closeProgramm(ActionEvent event) {

    }

    @FXML
    void openFile(ActionEvent event) {

    }

    @FXML
    void saveFile(ActionEvent event) {

    }


    @FXML
    private void initialize() {

        Scheme scheme = StartWindowController.schemes[numberOfScheme];
        int[] x = new int[scheme.getTimeSystemWork() / scheme.getStep() + 1];
        x[0] = 0;

        for (int i = 1; i < x.length; i++) {
            x[i] = x[i - 1] + scheme.getStep();
        }

        resultSchemeName.setText("Вариант №" + scheme.getVariant());
        resultSchemeImg.setImage(new Image("Files/image/schemes/" + scheme.getVariant() + ".PNG"));

        resultOutputArea.setText(outputData(scheme));

        loadData(scheme.getProbabRSP(), scheme.getProbabRS(), x);

        loadDataElements(scheme.getElements(), x);

        loadTableData(scheme, x);

    }

    private ObservableList<Entry> entryData = FXCollections.observableArrayList();
    private void loadTableData(Scheme scheme, int[] x) {
        // t k  p1  p2  p3  p4  <p5> РСП РС
        for (int i = 0; i < x.length; i++) {
            entryData.add(new Entry(scheme.getT()[i], scheme.getK()[i],
                    scheme.getElements().get(0).getProbabilityTimeline()[i],
                    scheme.getElements().get(1).getProbabilityTimeline()[i],
                    scheme.getElements().get(2).getProbabilityTimeline()[i],
                    scheme.getElements().get(3).getProbabilityTimeline()[i],
                    scheme.getProbabRSP()[i], scheme.getProbabRS()[i]));
        }

        // устанавливаем тип и значение которое должно хранится в колонке
        columnT.setCellValueFactory(new PropertyValueFactory<Entry, Integer>("t"));
        columnK.setCellValueFactory(new PropertyValueFactory<Entry, Integer>("k"));
        columnP1.setCellValueFactory(new PropertyValueFactory<Entry, Double>("p1"));
        columnP2.setCellValueFactory(new PropertyValueFactory<Entry, Double>("p2"));
        columnP3.setCellValueFactory(new PropertyValueFactory<Entry, Double>("p3"));
        columnP4.setCellValueFactory(new PropertyValueFactory<Entry, Double>("p4"));
        columnRSP.setCellValueFactory(new PropertyValueFactory<Entry, Double>("rsp"));
        columnRS.setCellValueFactory(new PropertyValueFactory<Entry, Double>("rs"));

        if (scheme.getCountElement() == 5) {
            for (int i = 0; i < x.length; i++) {
                entryData.get(i).setP5(scheme.getElements().get(4).getProbabilityTimeline()[i]);
            }
            columnP5.setCellValueFactory(new PropertyValueFactory<Entry, Double>("p5"));
        } else columnP5.setVisible(false);

        // заполняем таблицу данными
        resultTable.setItems(entryData);
    }

    private void loadDataElements(ArrayList<Element> elements, int[] x) {
        paneViewElements.getChildren().clear();
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Время (ч)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Вероятность");
        AreaChart<Number, Number> elementsChart = new AreaChart(xAxis, yAxis);
        elementsChart.setTitle("Элементы");

        XYChart.Series series = new XYChart.Series();
        series.setName("P1");
        for (int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>(x[i], elements.get(0).getProbabilityTimeline()[i]));
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("P2");
        for (int i = 0; i < x.length; i++) {
            series1.getData().add(new XYChart.Data<>(x[i], elements.get(1).getProbabilityTimeline()[i]));
        }
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("P3");
        for (int i = 0; i < x.length; i++) {
            series2.getData().add(new XYChart.Data<>(x[i], elements.get(2).getProbabilityTimeline()[i]));
        }
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("P4");
        for (int i = 0; i < x.length; i++) {
            series3.getData().add(new XYChart.Data<>(x[i], elements.get(3).getProbabilityTimeline()[i]));
        }

        elementsChart.getData().add(series);
        elementsChart.getData().add(series1);
        elementsChart.getData().add(series2);
        elementsChart.getData().add(series3);
        elementsChart.setMaxWidth(400);
        elementsChart.setMaxHeight(300);


        if (elements.size() == 5) {
            XYChart.Series series4 = new XYChart.Series();
            series4.setName("P5");
            for (int i = 0; i < x.length; i++) {
                series4.getData().add(new XYChart.Data<>(x[i], elements.get(4).getProbabilityTimeline()[i]));
            }
            elementsChart.getData().add(series4);

        }

        paneViewElements.getChildren().add(elementsChart);
    }


    void loadData(double[] d1, double[] d2, int[] x) {
        paneView.getChildren().clear();
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Время (ч)");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Вероятность");
        AreaChart<Number, Number> milkChart = new AreaChart(xAxis, yAxis);
        milkChart.setTitle("Система");

        XYChart.Series series = new XYChart.Series();
        series.setName("С простоем");
        for (int i = 0; i < x.length; i++) {
            series.getData().add(new XYChart.Data<>(x[i], d1[i]));
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Без простоя");
        for (int i = 0; i < x.length; i++) {
            series2.getData().add(new XYChart.Data<>(x[i], d2[i]));
        }

        milkChart.getData().add(series);
        milkChart.getData().add(series2);
        milkChart.setMaxWidth(367);
        milkChart.setMaxHeight(272);

        paneView.getChildren().add(milkChart);

    }

    private String outputData(Scheme scheme) {
        StringBuilder str = new StringBuilder();

        str.append("Среднее время безотказной работы системы\n - без учёта времени простоя элементов:\n\t");
        str.append(scheme.getAvarageTimeWork());
        str.append(" часов.\n");
        str.append(" - с учётом времени простоя элементов:\n\t");
        str.append(scheme.getAvarageTimeWorkTIMELINE());
        str.append(" часов.\n");
        str.append("\nСреднее время безотказной работы элементов с \nучётом их времени простоя:\n");
        str.append("\tT1 = ");
        str.append(scheme.getElements().get(0).getAverageTimeWorkTimeline());
        str.append(" часов.\n");
        str.append("\tT2 = ");
        str.append(scheme.getElements().get(1).getAverageTimeWorkTimeline());
        str.append(" часов.\n");
        str.append("\tT3 = ");
        str.append(scheme.getElements().get(2).getAverageTimeWorkTimeline());
        str.append(" часов.\n");
        str.append("\tT4 = ");
        str.append(scheme.getElements().get(3).getAverageTimeWorkTimeline());
        str.append(" часов.\n");
        if (scheme.getCountElement() == 5) {
            str.append("\tT5 = ");
            str.append(scheme.getElements().get(4).getAverageTimeWorkTimeline());
            str.append(" часов.\n");
        }
        return str.toString();
    }

}

