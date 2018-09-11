package Project.Window.Controllers;

import Project.Logic.Interfaces.Constants;
import Project.Logic.Scheme;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartWindowController implements Constants {

    static Scheme[] schemes = new Scheme[20];
    static int countOfScheme = 0;

    private ObservableList<String> variantsList = FXCollections.observableArrayList("Вариант 0", "Вариант 1", "Вариант 2", "Вариант 3");
    private ObservableList<String> distributionList = FXCollections.observableArrayList("Экспоненциальное", "Равномерное", "Релея");

    private int variant;

    private int countElements;

//    private boolean exist = false;

    private String typeOfDistribLaw0; //закон распределения вероятности
    private String typeOfDistribLaw1; //закон распределения вероятности
    private String typeOfDistribLaw2; //закон распределения вероятности
    private String typeOfDistribLaw3; //закон распределения вероятности
    private String typeOfDistribLaw4; //закон распределения вероятности

    private int[] paramScheme = new int[COUNT_PARAM_SCHEME];
    private String[] typeOfDistribLaw;
    private double[][] paramsDistribLaw;
    private int[][] intervals;
    private boolean isExistWindiwInfo = false;
    private boolean isExistWindiwHelp = false;
    private boolean isInitialSMTHdata = false;

    private void dataCollection() { //сбор данных схемы


//        for (int i = 0; i < countElements; i++) {
//            typeOfDistribLaw[i] = "";
//        }

        typeOfDistribLaw[0] = typeOfDistribLaw0;
        typeOfDistribLaw[1] = typeOfDistribLaw1;
        typeOfDistribLaw[2] = typeOfDistribLaw2;
        typeOfDistribLaw[3] = typeOfDistribLaw3;

        paramScheme[0] = getVariant();
        paramScheme[1] = countElements;
        paramScheme[2] = Integer.parseInt(periodSystemWork.getText());
        paramScheme[3] = Integer.parseInt(fullTimeSystemWork.getText());
        paramScheme[4] = Integer.parseInt(stepOnDelete.getText());

        paramsDistribLaw[0][0] = Double.parseDouble(c0.getText());
        paramsDistribLaw[0][1] = Double.parseDouble(d0.getText());
        paramsDistribLaw[1][0] = Double.parseDouble(c1.getText());
        paramsDistribLaw[1][1] = Double.parseDouble(d1.getText());
        paramsDistribLaw[2][0] = Double.parseDouble(c2.getText());
        paramsDistribLaw[2][1] = Double.parseDouble(d2.getText());
        paramsDistribLaw[3][0] = Double.parseDouble(c3.getText());
        paramsDistribLaw[3][1] = Double.parseDouble(d3.getText());

        intervals[0][0] = Integer.parseInt(a0.getText());
        intervals[0][1] = Integer.parseInt(b0.getText());
        intervals[1][0] = Integer.parseInt(a1.getText());
        intervals[1][1] = Integer.parseInt(b1.getText());
        intervals[2][0] = Integer.parseInt(a2.getText());
        intervals[2][1] = Integer.parseInt(b2.getText());
        intervals[3][0] = Integer.parseInt(a3.getText());
        intervals[3][1] = Integer.parseInt(b3.getText());


        if (countElements == 5) {
            typeOfDistribLaw[4] = typeOfDistribLaw4;
            paramsDistribLaw[4][0] = Double.parseDouble(c4.getText());
            paramsDistribLaw[4][1] = Double.parseDouble(d4.getText());
            intervals[4][0] = Integer.parseInt(a4.getText());
            intervals[4][1] = Integer.parseInt(b4.getText());
        }

    }

    @FXML
    private MenuItem menuOpen;

    @FXML
    private MenuItem menuSaveToFile;

    @FXML
    private MenuItem menuQuit;

    @FXML
    private MenuItem menuAboutProgramm;

    @FXML
    private ImageView schemeImage;

    @FXML
    private ChoiceBox<String> choiceBoxVariants;

    @FXML
    private TextField periodSystemWork;

    @FXML
    private TextField fullTimeSystemWork;

    @FXML
    private TextField stepOnDelete;

    @FXML
    private TextField a0;

    @FXML
    private TextField b0;

    @FXML
    private ChoiceBox<String> choiceDistributionLaw0;

    @FXML
    private TextField c0;

    @FXML
    private TextField d0;

    @FXML
    private TextField a1;

    @FXML
    private TextField b1;

    @FXML
    private ChoiceBox<String> choiceDistributionLaw1;

    @FXML
    private TextField c1;

    @FXML
    private TextField d1;

    @FXML
    private TextField a2;

    @FXML
    private TextField b2;

    @FXML
    private ChoiceBox<String> choiceDistributionLaw2;

    @FXML
    private TextField c2;

    @FXML
    private TextField d2;

    @FXML
    private TextField a3;

    @FXML
    private TextField b3;

    @FXML
    private ChoiceBox<String> choiceDistributionLaw3;

    @FXML
    private TextField c3;

    @FXML
    private TextField d3;

    @FXML
    private TextField a4;

    @FXML
    private TextField b4;

    @FXML
    private ChoiceBox<String> choiceDistributionLaw4;

    @FXML
    private TextField c4;

    @FXML
    private TextField d4;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnAutoFill;

    @FXML
    private Button btnHelp;


    @FXML
    void showDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        //если нажата не кнопка - вызодим из метода
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "btnAccept": {

                System.out.println("btnAccept");
                dataCollection();

                schemes[countOfScheme] = new Scheme(paramScheme, paramsDistribLaw, intervals, typeOfDistribLaw);
                countOfScheme++;

                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/Project/Window/fxml/ResultsWindow.fxml"));
                    stage.setResizable(false);
                    stage.setTitle("Результаты вычислений");
                    stage.setMinHeight(root.getScaleX());
                    stage.setMinWidth(root.getScaleY());
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
            case "btnHelp": {
                System.out.println("btnHelp");
                showHelp();
                break;
            }
            case "btnAutoFill": {
                System.out.println("btnHelp");
                setTestData();
//                setCountElements(0);
                break;
            }
            case "btnOk": {

                actionClose(actionEvent);
                break;
            }
        }

    }


    private void setTestData() {
        choiceBoxVariants.setValue("Вариант 0");
        countElements = 4;
        periodSystemWork.setText("10");
        fullTimeSystemWork.setText("500");
        stepOnDelete.setText("5");
        a0.setText("0");
        b0.setText("5");
        a1.setText("2");
        b1.setText("3");
        a2.setText("5");
        b2.setText("8");
        a3.setText("0");
        b3.setText("7");

        choiceDistributionLaw0.setValue("Экспоненциальное");
        choiceDistributionLaw1.setValue("Экспоненциальное");
        choiceDistributionLaw2.setValue("Экспоненциальное");
        choiceDistributionLaw3.setValue("Экспоненциальное");

        c0.setText("0.002");
        c1.setText("0.002");
        c2.setText("0.002");
        c3.setText("0.002");

        d0.setText("0");
        d1.setText("0");
        d2.setText("0");
        d3.setText("0");

        if (!isInitialSMTHdata) {
            typeOfDistribLaw = new String[5];
            paramsDistribLaw = new double[5][COUNT_PARAMS_DISTRIB_LAW];
            intervals = new int[5][COUNT_INTERVALS];
            isInitialSMTHdata = true;
        }
    }

    @FXML
    void showHelp() {
        if (!isExistWindiwHelp) {
            isExistWindiwHelp = false;
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Project/Window/fxml/help.fxml"));
                stage.setResizable(false);
                stage.setTitle("Параметры");
                stage.setMinHeight(root.getScaleX());
                stage.setMinWidth(root.getScaleY());
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void menuShowDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        MenuItem menuItem = (MenuItem) source;

        switch (menuItem.getId()) {
            case "menuSaveToFile": {

                break;
            }
            case "menuQuit": {
//                actionQuit( source);
                break;
            }
            case "menuAboutProgramm": {
                System.out.println("О программе");
                showInfo();
                break;
            }
        }

    }

    private void showInfo() {
        if (!isExistWindiwInfo) {
            isExistWindiwInfo = false;
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Project/Window/fxml/info.fxml"));
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

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        System.out.println("close Window");
        stage.close();
    }


    @FXML
    void choiceBoxClicked(DragEvent event) {
////        if(event.getEventType() == MouseEvent.MOUSE_RELEASED){
//        Object source = event.getSource();
//
//        ChoiceBox<String> choiceBox = (ChoiceBox<String>) source;
//        System.out.println("Your chose: " + choiceBox.getValue());
////        }
////        choiceBox.getSelectionModel().selectedIndexProperty().addListener(
////                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
////                        System.out.println("Your chose: " + choiceBox.getValue()));

    }

    @FXML
    private void listener() {
        choiceBoxVariants.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор схемы
                    variant = new_val.intValue();
                    schemeImage.setImage(new Image("Files/image/schemes/" + variant + ".PNG"));
                    setCountElements(variant);
                    openFieldsInterval(new_val.intValue());

//                    if (!isInitialSMTHdata) {
                        typeOfDistribLaw = new String[countElements];
                        paramsDistribLaw = new double[countElements][COUNT_PARAMS_DISTRIB_LAW];
                        intervals = new int[countElements][COUNT_INTERVALS];
                        isInitialSMTHdata = true;
//                    }
                });

        // 0 - exp
        // 1 - ravnomernoe
        // 2 - relay
        choiceDistributionLaw0.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор распр
                    c0.setDisable(false);
                    d0.setDisable(true);
//                    c0.setText("");
                    d0.setText("0");
                    if (new_val.intValue() == 1) {
                        d0.setDisable(false);
                    }
                    typeOfDistribLaw0 = choiseTypeOfDistribLaw(new_val.intValue());
                });

        choiceDistributionLaw1.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор распр
                    c1.setDisable(false);
                    d1.setDisable(true);
//                    c1.setText("");
                    d1.setText("0");
                    if (new_val.intValue() == 1) {
                        d1.setDisable(false);
                    }
                    typeOfDistribLaw1 = choiseTypeOfDistribLaw(new_val.intValue());
                });

        choiceDistributionLaw2.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор распр
                    c2.setDisable(false);
                    d2.setDisable(true);
//                    c2.setText("");
                    d2.setText("0");
                    if (new_val.intValue() == 1) {
                        d2.setDisable(false);
                    }
                    typeOfDistribLaw2 = choiseTypeOfDistribLaw(new_val.intValue());
                });

        choiceDistributionLaw3.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор распр
                    c3.setDisable(false);
                    d3.setDisable(true);
//                    c3.setText("");
                    d4.setText("0");
                    if (new_val.intValue() == 1) {
                        d3.setDisable(false);
                    }
                    typeOfDistribLaw3 = choiseTypeOfDistribLaw(new_val.intValue());
                });

        choiceDistributionLaw4.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //выполнение действий при выборе схемы
                    System.out.println("Your chose: " + new_val.intValue()); //выбор распр
                    c4.setDisable(false);
                    d4.setDisable(true);

//                    c4.setText("");
                    d4.setText("0");

                    if (new_val.intValue() == 1) {
                        d4.setDisable(false);
                    }
                    typeOfDistribLaw4 = choiseTypeOfDistribLaw(new_val.intValue());
                });
    }

    private String choiseTypeOfDistribLaw(int i) {
        switch (i) {
            case 0: {
                return "exp";
            }
            case 1: {
                return "ravnomernoe";
            }
            case 2: {
                return "relay";
            }
            default:
                return "exp";
        }
    }

    @FXML
    private void initialize() {
        System.out.println("lololo");
        choiceBoxVariants.setItems(variantsList);
        choiceBoxVariants.setValue("Вариант 0");
        choiceBoxVariants.setTooltip(new Tooltip("Выберете схему"));

        choiceDistributionLaw0.setItems(distributionList);
        choiceDistributionLaw1.setItems(distributionList);
        choiceDistributionLaw2.setItems(distributionList);
        choiceDistributionLaw3.setItems(distributionList);
        choiceDistributionLaw4.setItems(distributionList);
        choiceDistributionLaw4.setDisable(true);


        schemeImage.setImage(new Image("Files/image/schemes/0.PNG"));

        menuSaveToFile.setDisable(true);
        menuOpen.setDisable(true);

        btnAccept.setTooltip(new Tooltip("Рассчитать"));
        btnAutoFill.setTooltip(new Tooltip("Автозаполнение тестовыми данными"));
        btnHelp.setTooltip(new Tooltip("Законы распределения"));

        choiceDistributionLaw0.setTooltip(new Tooltip("Выберете распределение"));
        choiceDistributionLaw1.setTooltip(new Tooltip("Выберете распределение"));
        choiceDistributionLaw2.setTooltip(new Tooltip("Выберете распределение"));
        choiceDistributionLaw3.setTooltip(new Tooltip("Выберете распределение"));
        choiceDistributionLaw4.setTooltip(new Tooltip("Выберете распределение"));

        c0.setDisable(true);
        c1.setDisable(true);
        c2.setDisable(true);
        c3.setDisable(true);
        c4.setDisable(true);

//        d0.setText("0");
//        d1.setText("0");
//        d2.setText("0");
//        d3.setText("0");

        d0.setDisable(true);
        d1.setDisable(true);
        d2.setDisable(true);
        d3.setDisable(true);
        d4.setDisable(true);

        variant = getVariant();
        openFieldsInterval(variant);
        listener();

        System.out.println(periodSystemWork.getText());

//        choiceBoxVariants.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) ->
//                        System.out.println("Your chose: " + choiceBoxVariants.getValue()));

        //открытие зон для ввода данных


    }

//    private String getChoice(ChoiceBox choiceBox) {
//        return (String) choiceBox.getSelectionModel();
//    }

    private int getVariant() {
        return Integer.parseInt(String.valueOf(choiceBoxVariants.getValue().charAt(choiceBoxVariants.getValue().length() - 1)));
    } //получение текущего варианта


    private void openFieldsInterval(int variant) {
        System.out.println("Your variant:" + variant);
        if (variant == 0 || variant == 3) {
            a0.setDisable(false);
            a1.setDisable(false);
            a2.setDisable(false);
            a3.setDisable(false);
            a4.setDisable(true);

            b0.setDisable(false);
            b1.setDisable(false);
            b2.setDisable(false);
            b3.setDisable(false);
            b4.setDisable(true);
            choiceDistributionLaw4.setDisable(true);
            c4.setDisable(true);
            d4.setDisable(true);
        } else {
            a4.setDisable(false);
            b4.setDisable(false);
            choiceDistributionLaw4.setDisable(false);
        }
    }

    public void setCountElements(int variant) {
        if (variant == 0 || variant == 3) {
            countElements = 4;
        } else countElements = 5;
    }
}
