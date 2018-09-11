package Project.Window.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InfoWindowController {
    @FXML
    public Button btnOk;

    @FXML
    void showDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        //если нажата не кнопка - вызодим из метода
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "btnOk": {

                actionClose(actionEvent);
                break;
            }
        }

    }
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        System.out.println("close"); //вывод о закрытие в консоль
        stage.close(); //закрытие окна
    }
}
