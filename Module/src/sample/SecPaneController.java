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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class SecPaneController {
    private JSONObject user;
    private ObservableList<UserMovie> usermovies;
    private List<JSONObject> userseries;
    @FXML
    TextField searchtext;
    @FXML
    Button button_find;
    @FXML
    Button button_addnew;
    @FXML
    Button button_delete;
    @FXML
    TableView tableView;
    @FXML
    Label label_title;
    @FXML
    Label label_relasedate;
    @FXML
    Label label_tmdbrating;
    @FXML
    Label label_director;
    @FXML
    Label label_actor;
    @FXML
    Label label_story;
    @FXML
    Label label_editing;
    @FXML
    Label label_acting;
    @FXML
    Label label_music;
    @FXML
    Label label_average;
    @FXML
    ImageView imageView_poster;
    @FXML
    ImageView imageView_director;
    @FXML
    ImageView imageView_actor;
    @FXML
    TextArea textArea_review;
    @FXML
    protected void initialize() throws IOException {
        user = (JSONObject) Stage.getWindows().get(0).getUserData();
        System.out.println("Kullanici="+user.toString());
        if (tableView.getId().equals("filmtable"))
        {
            System.out.println("Bu filmtabledir!");
            usermovies = new BfgReviewApi.UserMethods().GetUserMoviesJsonObject(user);
            FillMovieTable();
        }
        else if (tableView.getId().equals("dizitable"))
        {
            System.out.println("Bu dizitabledir");
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                FillGuiElements(usermovies.indexOf(tableView.getSelectionModel().getSelectedItem()));
            }
        });

    }
    public void FillMovieTable()
    {
        TableColumn<UserMovie,String> title = new TableColumn<>();
        TableColumn<UserMovie,String> director = new TableColumn<>();
        TableColumn<UserMovie,String> relase = new TableColumn<>();
        TableColumn<UserMovie,String> datewatched = new TableColumn<>();
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        director.setCellValueFactory(new PropertyValueFactory<>("Director"));
        relase.setCellValueFactory(new PropertyValueFactory<>("Relase"));
        datewatched.setCellValueFactory(new PropertyValueFactory<>("DateWatched"));
        tableView.getColumns().addAll(title,director,relase,datewatched);
        tableView.setItems(usermovies);

    }
    public void FillGuiElements(int index)
    {

        label_title.setText(usermovies.get(index).getTitle());
        label_director.setText(usermovies.get(index).getDirector());
        label_relasedate.setText(usermovies.get(index).getRelase());
        label_story.setText(usermovies.get(index).story.toString());
        label_acting.setText(usermovies.get(index).acting.toString());
        label_editing.setText(usermovies.get(index).editing.toString());
        label_music.setText(usermovies.get(index).music.toString());
        textArea_review.setText(usermovies.get(index).getReview());
    }
    public void AddNewButtonClicked(ActionEvent event) throws IOException
    {

        Parent parent = FXMLLoader.load(getClass().getResource("FilmAddNew.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void AddNewSeriesButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("SeriesAddNew.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void AddNewGameButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("GamesAddNew.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
