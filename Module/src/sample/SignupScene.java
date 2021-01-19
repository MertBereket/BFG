package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SignupScene {

    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField email;
    @FXML
    TextField username;
    @FXML
    TextField pasword;

    public void SignupButtonClicked(ActionEvent event) throws IOException
    {
        JSONObject object = new JSONObject();
        object.put("Name",name.getText());
        object.put("Surname",surname.getText());
        object.put("Email",email.getText());
        object.put("UserName",username.getText());
        object.put("Pasword",pasword.getText());
        new BfgReviewApi.UserMethods().SignUpUserJsonObject(object);
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
    public void CancelButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
