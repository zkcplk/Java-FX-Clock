//---------------------
//  Zeki ÇIPLAK
//  github.com/zkcplk
//---------------------
package saatclock;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SaatClock extends Application {
  // matematiksel işlemler için geçici değişken
  double temp;

  // saniye, dakika ve saat değerleri
  int saat;
  int dakika;
  int saniye;

  // saniye çizgisi
  Line sn = new Line(0, 0, 0, 0);

  // dakika çizgisi
  Line dk = new Line(0, 0, 0, 0);

  // saat çizgisi
  Line sa = new Line(0, 0, 0, 0);

  // orta aşağıda zamanı gösteren etiket
  Label zamanLabel = new Label();

  // x koordinatının cosinüsü
  double cosX;

  // y koordinatının sinüsü
  double sinY;

  // saatin yarı çapı
  double yaricap = 200;

  // akrebin boyu, rengi
  int boyAkrep = 100;
  Paint renkAkrep = Color.GREEN;

  // yelkovanın boyu, rengi
  int boyYelkovan = 150;
  Paint renkYelkovan = Color.BLUE;

  // saniyenin boyu, rengi
  int boySaniye = 120;
  Paint renkSaniye = Color.RED;

  @Override
  public void start(Stage primaryStage) {

    // çember çizimi
    Circle circle = new Circle();
    circle.setCenterX(yaricap / 2);
    circle.setCenterY(yaricap / 2);
    circle.setRadius(yaricap);
    circle.setStroke(Color.BLACK);
    circle.setFill(Color.WHITE);

    // 3, 6, 9 ve 12 sayılarının saate yerleştirilmesi
    Label label12 = new Label("12");
    label12.setTranslateX(0);
    label12.setTranslateY(-190);

    Label label9 = new Label("9");
    label9.setTranslateX(-190);
    label9.setTranslateY(0);

    Label label6 = new Label("6");
    label6.setTranslateX(0);
    label6.setTranslateY(190);

    Label label3 = new Label("3");
    label3.setTranslateX(190);
    label3.setTranslateY(0);

    // Zaman
    zamanGuncelle();

    // StackPane
    StackPane pane = new StackPane();
    pane.getChildren().add(circle);
    pane.getChildren().add(label12);
    pane.getChildren().add(label9);
    pane.getChildren().add(label6);
    pane.getChildren().add(label3);
    pane.getChildren().add(zamanLabel);
    pane.getChildren().add(sn);
    pane.getChildren().add(dk);
    pane.getChildren().add(sa);

    // Scene
    Scene scene = new Scene(pane, 440, 470);
    primaryStage.setTitle("JavaFX Clock (Saat) Tasarımı - Zeki ÇIPLAK");
    primaryStage.setScene(scene);
    primaryStage.show();

    // Zamanın güncellenmesi
    Timeline guncelle = new Timeline(new KeyFrame(Duration.millis(1000), ZekiCiplak -> zamanGuncelle()));
    guncelle.setCycleCount(Timeline.INDEFINITE);
    guncelle.play();
  }

  public void zamanGuncelle() {
    // zaman tespiti
    zamanBul();

    // tek haneli gözüken sayılar, çift haneli gözükmeli
    String ciftHaneli = String.format("%02d", saat) + ":"
            + String.format("%02d", dakika) + ":"
            + String.format("%02d", saniye);

    zamanLabel.setText(ciftHaneli);
    zamanLabel.setTranslateX(0);
    zamanLabel.setTranslateY(220);

    // saniye çizimi
    temp = saniye * (2 * Math.PI / 60) - Math.PI / 2;
    cosX = boySaniye * Math.cos(temp);
    sinY = boySaniye * Math.sin(temp);
    sn.setStartX(0);
    sn.setStartY(0);
    sn.setEndX(cosX);
    sn.setEndY(sinY);
    sn.setStroke(renkSaniye);
    sn.setTranslateX(cosX / 2.0);
    sn.setTranslateY(sinY / 2.0);

    // dakika çizimi
    temp = dakika * (2 * Math.PI / 60) - Math.PI / 2;
    cosX = boyYelkovan * Math.cos(temp);
    sinY = boyYelkovan * Math.sin(temp);
    dk.setStartX(0);
    dk.setStartY(0);
    dk.setEndX(cosX);
    dk.setEndY(sinY);
    dk.setStroke(renkYelkovan);
    dk.setTranslateX(cosX / 2.0);
    dk.setTranslateY(sinY / 2.0);
    dk.setStyle("-fx-stroke-width: 3px");

    // saat çizimi
    temp = (saat % 12 + dakika / 60.0) * (2 * Math.PI / 12) - Math.PI / 2;
    cosX = boyAkrep * Math.cos(temp);
    sinY = boyAkrep * Math.sin(temp);
    sa.setStartX(0);
    sa.setStartY(0);
    sa.setEndX(cosX);
    sa.setEndY(sinY);
    sa.setStroke(renkAkrep);
    sa.setTranslateX(cosX / 2.0);
    sa.setTranslateY(sinY / 2.0);
    sa.setStyle("-fx-stroke-width: 3px");
  }

  public void zamanBul() {
    // Türkiye saat ayarı (+3 saat ek)
    long saniyeToplam = System.currentTimeMillis() / 1000 + 3600 * 3;

    // Şu anki saniye
    saniye = (int) saniyeToplam % 60;
    int dakikaToplam = (int) saniyeToplam / 60;

    // Şu anki dakika
    dakika = (int) dakikaToplam % 60;
    int saatToplam = (int) dakikaToplam / 60;

    // Şu anki saat
    saat = (int) saatToplam % 24;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
