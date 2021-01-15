package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void loadFilmPane (javafx.event.ActionEvent event) throws IOException  {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("Filmpane.fxml"));
        secPane.getChildren().add(newLoadedPane);
    }
    public void loadTvPane (javafx.event.ActionEvent event) throws IOException  {
        Pane newLoadedPane =  FXMLLoader.load(getClass().getResource("TvSeriesPane.fxml"));
        secPane.getChildren().add(newLoadedPane);
    }
}
