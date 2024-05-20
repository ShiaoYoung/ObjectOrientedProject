module com.room4306.mediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.room4306.mediaplayer to javafx.fxml;
    exports com.room4306.mediaplayer;
}