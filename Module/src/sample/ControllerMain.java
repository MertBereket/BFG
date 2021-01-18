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



public class ControllerMain {

    @FXML
    TextField username;
    @FXML
    TextField pasword;

    public void SignupButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("SignupScene.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void LoginButtonClicked(ActionEvent event) throws IOException
    {
        JSONObject object = new JSONObject();
        object.put("UserName",username.getText());
        object.put("Pasword",pasword.getText());
        JSONObject user = new BfgReviewApi.SignupUser().SignInJsonObject(object);
        if (user!=null)
        {
            System.out.println("Succes!");
            Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setUserData(user);
            window.setScene(scene);
            window.show();
        }
        else
        {
            System.out.println("Hatali Giris!");
        }
    }
    public void DebugButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
