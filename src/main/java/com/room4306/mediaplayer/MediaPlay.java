//package com.room4306.mediaplayer;
//
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.MediaView;
//
//import java.io.File;
//import java.net.MalformedURLException;
//
//public class MediaPlay{
//    private File toPlay;
//    private Media media;
//    private MediaPlayer player;
//    private MediaView mediaView;
//
//    // 媒体文件的一些属性
//    private double height;
//    private double width;
//
//
//    public MediaPlay(File mediaFile) {
//        toPlay = mediaFile;
//        if (toPlay != null && toPlay.isFile()) {
//            // 生成一系列组件
//            media = new Media(toPlay.toURI().toString());
//            player = new MediaPlayer(media);
//            mediaView = new MediaView(player);
//
//            // 配置属性
//            if (media.getWidth() != 0) width = media.getWidth();
//            if (media.getHeight() != 0) height = media.getHeight();
//        }
//        else {
//
//        }
//    }
//
//    public void begin(){
//        player.play();
//    }
//
//    public MediaView getMediaView() {
//        return mediaView;
//    }
//}
