package com.room4306.mediaplayer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class myMediaPlayer extends Application {

    // 布局
    VBox rVBox = new VBox();
    VBox vBoxUp = new VBox();
    VBox vBoxDown = new VBox();
    HBox hBoxPgsBar = new HBox();
    HBox hBoxBtn = new HBox();


    // 媒体文件
    File curFile;
    int curPlay = 0; // 0->music, 1->video

    // 播放器组件
    Media media = null;
    MediaPlayer player;
    MediaView mediaView;

    // 播放器初始尺寸
    double height = 400;
    double width;
    // 播放器控件
    ProgressBar progressBar;
    double startX; // 进度条位置

    @Override
    public void init() throws Exception {
        curFile = new File("D:\\桌面\\大学\\英语\\大二下（英语演讲与辩论）\\refutation and rebuttal.mp4");
        curPlay = 1;
    }

    @Override
    public void start(Stage stage) {
        // 标题
        stage.setTitle("好兄弟播放器");
        stage.getIcons().add(new Image("player.png"));


        vBoxUp.setStyle("-fx-alignment: center;" + "-fx-pref-height80%;" + "-fx-background-color: black");
        vBoxUp.setPrefHeight(400);
        vBoxDown.setStyle("-fx-alignment: center;" + "-fx-pref-height: 20%");


        // 菜单
            // MenuBar
        MenuBar menuBar = new MenuBar();


            // Menu
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Help");

            // Menu -> MenuBar
        menuBar.getMenus().addAll(m1,m2);

            // MenuItem
        ImageView msc = new ImageView("music.png");
        msc.setFitHeight(10);
        msc.setFitWidth(10);
        ImageView vdo = new ImageView("video.png");
        vdo.setFitHeight(10);
        vdo.setFitWidth(10);
        MenuItem mi11 = new MenuItem("打开新音频", msc);
        MenuItem mi12 = new MenuItem("打开新视频", vdo);
        MenuItem mi21 = new MenuItem("关于我们");

            // 菜单功能实现
        mi11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //初始化文件选择器
                FileChooser fileChooser = new FileChooser();
                File file = new File("C:/");     //以C盘为根目录
                fileChooser.setInitialDirectory(file);    //设置初始目录
                fileChooser.setTitle("请选择要播放的音频文件");  //设置文件选择器的标题
                //文件选择器样式
                Stage stage1 = new Stage();
                stage1.setHeight(1000);

                File file1 = fileChooser.showOpenDialog(stage1); //获取要计算的文件
                Scanner scanner;
                try {
                    scanner = new Scanner(file1);
                } catch (Exception e) {
                    System.out.println("请选择音频文件");
                    return;
                }

                curFile = file1;
                curPlay = 0;

                playMedia();
            }
        });

        mi12.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //初始化文件选择器
                FileChooser fileChooser = new FileChooser();
                File file = new File("C:/");     //以C盘为根目录
                fileChooser.setInitialDirectory(file);    //设置初始目录
                fileChooser.setTitle("请选择要播放的视频文件");  //设置文件选择器的标题
                //文件选择器样式
                Stage stage1 = new Stage();
                stage1.setHeight(1000);

                File file1 = fileChooser.showOpenDialog(stage1); //获取要计算的文件
                Scanner scanner;
                try {
                    scanner = new Scanner(file1);
                } catch (Exception e) {
                    System.out.println("请选择视频文件");
                    return;
                }

                curFile = file1;
                curPlay = 1;

                playMedia();
            }
        });

            // MenuItem -> Menu
        m1.getItems().addAll(mi11,mi12);
        m2.getItems().addAll(mi21);

        // 播放器画面
        mediaView = new MediaView();
        playMedia();

        // 进度条
        progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: #c89063;");
        progressBar.setPrefHeight(10);
        progressBar.setPrefWidth(1000);
        progressBar.setMinWidth(0);
        progressBar.setTooltip(new Tooltip());
        startX = player.getCurrentRate();
        progressBar.setProgress(startX);
        System.out.println(player.getCurrentRate());

        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMousePressed(mouseEvent);
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleMouseDragged(mouseEvent);
            }
        });

        

        // 布局的层次、组件的装入
        rVBox.getChildren().add(menuBar);
        rVBox.getChildren().add(vBoxUp);
        rVBox.getChildren().add(vBoxDown);
        vBoxDown.getChildren().add(hBoxPgsBar);
        vBoxDown.getChildren().add(hBoxBtn);


        // 组件装入布局

        vBoxUp.getChildren().add(mediaView);
        rVBox.setVgrow(vBoxUp, Priority.ALWAYS);
        rVBox.setVgrow(vBoxDown, Priority.ALWAYS);

        hBoxPgsBar.getChildren().add(progressBar);



        // 布局装入场景
        Scene scene = new Scene(rVBox);

        // 展示舞台
        stage.setMinHeight(540);
        stage.setMinWidth(540);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

    public void playMedia() {
        // 播放器组件
        //player.pause();

        media = null;
        try {
            media = new Media(curFile.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        player = new MediaPlayer(media);
        player.setStartTime(new Duration(100));
        mediaView = new MediaView(player);

        if (curPlay == 1){
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);



            Image image = mediaView.snapshot(null,null);
            ImageView imageView = new ImageView(image);



            height = imageView.getFitHeight();
            width = imageView.getFitWidth();
            System.out.println(curFile.getName());
            System.out.println(height + " " + width);
        }
        else {
            height = 400;
            width = 400;
        }




        // 设置样式
        vBoxUp.setStyle("-fx-alignment: center;" + "-fx-background-color: black;" + "-fx-fit-to-height: true");
        vBoxDown.setStyle("-fx-alignment: center;" + "-fx-fit-to-height: true");

        // 设置播放页面大小
        if (height > width)
            mediaView.setFitHeight(height);
        else
            mediaView.setFitWidth(width);

        mediaView.setVisible(true);


        player.play();
    }

    private void handleMousePressed(MouseEvent event) {
        startX = event.getX();
        progressBar.setProgress(startX / progressBar.getWidth());
    }

    private void handleMouseDragged(MouseEvent event) {
        double progressBarWidth = progressBar.getWidth();
        double newProgress = event.getX() / progressBarWidth;

        progressBar.setProgress(newProgress);
    }
}
