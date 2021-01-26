package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmAddNew {
    public JSONObject user;
    private ObservableList<UserMovie> results;
    @FXML
    TextField text_search;
    @FXML
    Button button_search;
    @FXML
    Button button_addmovie;
    @FXML
    TableView tableView;
    @FXML
    ChoiceBox choice_story;
    @FXML
    ChoiceBox choice_acting;
    @FXML
    ChoiceBox choice_editing;
    @FXML
    ChoiceBox choice_music;
    @FXML
    DatePicker date_watched;
    @FXML
    TextArea text_userreview;
    @FXML
    public void initialize()
    {
        user = (JSONObject) Stage.getWindows().get(0).getUserData();
        System.out.println(user.toString());
        TableColumn<UserMovie,String> title = new TableColumn<>();
        TableColumn<UserMovie,String> relase = new TableColumn<>();
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        relase.setCellValueFactory(new PropertyValueFactory<>("Relase"));
        tableView.getColumns().addAll(title,relase);
        FillChoiceBoxes();
    }
    public void CancelButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void SearchButtonClicked(ActionEvent event) throws IOException
    {
        String searched = text_search.getText();
        if (searched.isEmpty()==false)
        {
            tableView.getItems().clear();
            results = new TmdbApiFunctions.moviedata().GetUserMovieSearch(searched);
            tableView.setItems(results);
        }

    }
    public void AddMovieButtonClicked(ActionEvent event) throws IOException
    {
        int index = results.indexOf(tableView.getSelectionModel().getSelectedItem());
        JSONObject post = new JSONObject();
        JSONArray cast = new TmdbApiFunctions.moviedata().getTmdbMovieCast(results.get(index).tmdbid);
        JSONObject director=(JSONObject) cast.get(1);
        post.put("PersonID",user.get("PersonID"));
        post.put("tmdbid",results.get(index).tmdbid);
        post.put("title",results.get(index).getTitle());
        post.put("director",director.get("name"));
        post.put("relasedate",results.get(index).getRelase());
        post.put("story",choice_story.getSelectionModel().getSelectedItem());
        post.put("acting",choice_acting.getSelectionModel().getSelectedItem());
        post.put("editing",choice_editing.getSelectionModel().getSelectedItem());
        post.put("music",choice_music.getSelectionModel().getSelectedItem());
        post.put("datewatch",date_watched.getValue().toString());
        post.put("review",text_userreview.getText());
        System.out.println(post.toString());
        new BfgReviewApi.UserMethods().PostUserMovieJsonObject(post);
    }
    public void FillChoiceBoxes()
    {
        List<Integer> choices = new ArrayList<Integer>();
        choices.add(1);
        choices.add(2);
        choices.add(3);
        choices.add(4);
        choices.add(5);
        choice_story.getItems().addAll(choices);
        choice_acting.getItems().addAll(choices);
        choice_editing.getItems().addAll(choices);
        choice_music.getItems().addAll(choices);
    }
}
