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

public class SeriesPaneController {
    private JSONObject user;
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
        userseries = new BfgReviewApi.UserMethods().GetUserSeriesJsonObject(user);
        FillSeriesTable();
        seriestableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                FillGuiElements(userseries.indexOf(seriestableView.getSelectionModel().getSelectedItem()));
                try {
                    FillTmdbApiData(userseries.indexOf(seriestableView.getSelectionModel().getSelectedItem()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void FillSeriesTable()
    {
        TableColumn<UserMovie,String> title = new TableColumn<>();
        TableColumn<UserMovie,String> director = new TableColumn<>();
        TableColumn<UserMovie,String> relase = new TableColumn<>();
        TableColumn<UserMovie,String> datewatched = new TableColumn<>();
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        director.setCellValueFactory(new PropertyValueFactory<>("Director"));
        relase.setCellValueFactory(new PropertyValueFactory<>("Relase"));
        datewatched.setCellValueFactory(new PropertyValueFactory<>("DateWatched"));
        seriestableView.getColumns().addAll(title,director,relase,datewatched);
        seriestableView.setItems(userseries);

    }
    public void FillGuiElements(int index)
    {
        label_title.setText(userseries.get(index).getTitle());
        label_director.setText(userseries.get(index).getDirector());
        label_relasedate.setText(userseries.get(index).getRelase().toString());
        label_story.setText(userseries.get(index).story.getValue().toString());
        label_acting.setText(userseries.get(index).acting.getValue().toString());
        label_editing.setText(userseries.get(index).editing.getValue().toString());
        label_music.setText(userseries.get(index).music.getValue().toString());
        label_average.setText(String.valueOf(userseries.get(index).getAvrg()));
        textArea_review.setText(userseries.get(index).getReview());
    }
    public void FillTmdbApiData(int index) throws IOException {
        JSONObject serie = new TmdbApiFunctions.moviedata().getTmdbSeries(userseries.get(index).tmdbid);
        JSONArray created = (JSONArray) serie.get("created_by");
        JSONObject producer = (JSONObject) created.get(0);
        String producerurl = "https://image.tmdb.org/t/p/w185"+producer.get("profile_path").toString();
        Image producerimg = new Image(producerurl);
        imageView_director.setImage(producerimg);
        JSONArray cast = new TmdbApiFunctions.moviedata().getTmdbSeriesCast(userseries.get(index).tmdbid);
        if (cast.isEmpty()==false)
        {
            JSONObject actor = (JSONObject) cast.get(0);
            String actorurl = "https://image.tmdb.org/t/p/w185"+actor.get("profile_path").toString();
            Image actorimg = new Image(actorurl);
            imageView_actor.setImage(actorimg);
            label_actor.setText(actor.get("name").toString());
        }
        String posterurl= "https://image.tmdb.org/t/p/w500"+ serie.get("poster_path").toString();
        Image poster = new Image(posterurl);
        imageView_poster.setImage(poster);
        label_tmdbrating.setText(serie.get("vote_average").toString());

    }

    public void AddNewSeriesButtonClicked(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("SeriesAddNew.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
