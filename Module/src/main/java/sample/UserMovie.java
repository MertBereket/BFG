package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserMovie {
    public SimpleStringProperty Title;

    public String getTitle() {
        return Title.get();
    }

    public SimpleStringProperty titleProperty() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title.set(title);
    }

    public String getDirector() {
        return Director.get();
    }

    public SimpleStringProperty directorProperty() {
        return Director;
    }

    public void setDirector(String director) {
        this.Director.set(director);
    }

    public String getRelase() {
        return Relase.get();
    }

    public SimpleStringProperty relaseProperty() {
        return Relase;
    }

    public void setRelase(String relase) {
        this.Relase.set(relase);
    }

    public int getStory() {
        return story.get();
    }

    public SimpleIntegerProperty storyProperty() {
        return story;
    }

    public void setStory(int story) {
        this.story.set(story);
    }

    public int getActing() {
        return acting.get();
    }

    public SimpleIntegerProperty actingProperty() {
        return acting;
    }

    public void setActing(int acting) {
        this.acting.set(acting);
    }

    public int getEditing() {
        return editing.get();
    }

    public SimpleIntegerProperty editingProperty() {
        return editing;
    }

    public void setEditing(int editing) {
        this.editing.set(editing);
    }

    public int getMusic() {
        return music.get();
    }

    public SimpleIntegerProperty musicProperty() {
        return music;
    }

    public void setMusic(int music) {
        this.music.set(music);
    }

    public String getDateWatched() {
        return DateWatched.get();
    }

    public SimpleStringProperty dateWatchedProperty() {
        return DateWatched;
    }

    public void setDateWatched(String dateWatched) {
        this.DateWatched.set(dateWatched);
    }

    public String getReview() {
        return Review.get();
    }

    public SimpleStringProperty reviewProperty() {
        return Review;
    }

    public void setReview(String review) {
        this.Review.set(review);
    }

    public SimpleStringProperty Director;
    public SimpleStringProperty Relase;
    public SimpleIntegerProperty story;
    public SimpleIntegerProperty acting;
    public SimpleIntegerProperty editing;
    public SimpleIntegerProperty music;
    public SimpleStringProperty DateWatched;
    public SimpleStringProperty Review;
    public int tmdbid;
    public float avrg;

    public int getTmdbid() {
        return tmdbid;
    }

    public void setTmdbid(int tmdbid) {
        this.tmdbid = tmdbid;
    }

    public float getAvrg() {
        return (this.story.floatValue()+this.editing.getValue()+this.music.getValue()+this.acting.getValue())/4;
    }
}
