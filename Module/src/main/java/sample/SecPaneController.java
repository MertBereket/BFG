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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class SecPaneController {
    private JSONObject user;
    private ObservableList<UserMovie> usermovies;
    private ObservableList<UserMovie> userseries;
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
    TableView seriestableView;
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
        Stage.getWindows().get(0).setUserData(user);
        System.out.println("Kullanici="+user.toString());
        System.out.println("Bu filmtabledir!");
        usermovies = new BfgReviewApi.UserMethods().GetUserMoviesJsonObject(user);
        FillMovieTable();
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                    FillGuiElements(usermovies.indexOf(tableView.getSelectionModel().getSelectedItem()));
                try {
                    FillTmdbApiData(usermovies.indexOf(tableView.getSelectionModel().getSelectedItem()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        label_relasedate.setText(usermovies.get(index).getRelase().toString());
        label_story.setText(usermovies.get(index).story.getValue().toString());
        label_acting.setText(usermovies.get(index).acting.getValue().toString());
        label_editing.setText(usermovies.get(index).editing.getValue().toString());
        label_music.setText(usermovies.get(index).music.getValue().toString());
        label_average.setText(String.valueOf(usermovies.get(index).getAvrg()));
        textArea_review.setText(usermovies.get(index).getReview());
    }
    public void FillTmdbApiData(int index) throws IOException {
        JSONObject movie = new TmdbApiFunctions.moviedata().getTmdbMovie(usermovies.get(index).tmdbid);
        JSONArray cast = new TmdbApiFunctions.moviedata().getTmdbMovieCast(usermovies.get(index).tmdbid);
        if (cast.isEmpty()==false)
        {
            JSONObject actor = (JSONObject) cast.get(0);
            JSONObject director = (JSONObject) cast.get(1);
            String actorurl = "https://image.tmdb.org/t/p/w185"+actor.get("profile_path").toString();
            Image actorimg = new Image(actorurl);
            imageView_actor.setImage(actorimg);
            label_actor.setText(actor.get("name").toString());
            String directorurl = "https://image.tmdb.org/t/p/w185"+director.get("profile_path").toString();
            Image directorimg = new Image(directorurl);
            imageView_director.setImage(directorimg);
        }
        String posterurl= "https://image.tmdb.org/t/p/w500"+movie.get("poster_path").toString();
        Image poster = new Image(posterurl);
        imageView_poster.setImage(poster);
        label_tmdbrating.setText(movie.get("vote_average").toString());



    }
    public void AddNewButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("FilmAddNew.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setUserData(user);
        window.setScene(scene);
        window.show();
    }
    public void SuggestButtonClicked(ActionEvent event) throws IOException
    {
        int index=usermovies.indexOf(tableView.getSelectionModel().getSelectedItem());
        String title = usermovies.get(index).getTitle();
        String str = new BfgReviewApi.UserMethods().SuggestJsonObject(title);
        System.out.println(str);
        showSuggest(str,title);
    }
    public void showSuggest(String suggestion,String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Film Onerisi");
        alert.setHeaderText(title + "Filmine benzer filmler!");
        alert.setContentText(suggestion);
        alert.showAndWait();
    }
}
