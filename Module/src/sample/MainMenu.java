package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainMenu {

    public void ExitButtonClicked(ActionEvent event) throws IOException
    {

        Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    Pane secPane;
    @FXML
    Label userlabel;
    public void loadFilmPane (javafx.event.ActionEvent event) throws IOException  {
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();
        //TableView table = (TableView) scene.lookup("filmtable");
        //table.getColumns().clear();
        JSONObject user = (JSONObject) stage.getUserData();
        userlabel.setText(user.get("Name")+" "+user.get("Surname"));
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("Filmpane.fxml"));
        //List<Node> parentChildren = ((Pane)secPane.getParent()).getChildren();

        secPane.getChildren().add(newLoadedPane);
    }
    public void loadTvPane (javafx.event.ActionEvent event) throws IOException  {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("TvSeriesPane.fxml"));
        secPane.getChildren().add(newLoadedPane);
    }
    public void loadGamePane (javafx.event.ActionEvent event) throws IOException  {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("GamesPane.fxml"));
        secPane.getChildren().add(newLoadedPane);
    }
}
