
package finalproject;

import java.awt.Point;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import java.util.TreeSet;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;

public class FinalProject extends Application {
    public static VBox menu = new VBox();//главное меню игры
    public static GridPane gameField = new GridPane();//поле игры
    public static VBox gameMenu = new VBox(20);//панель меню в игре
    public static HBox game = new HBox(20);//игра
    public static VBox settings = new VBox(20);//настройки
    public static VBox score = new VBox(20);//таблица рекордов
    
    public static int size=3;//задаем размер игры
    private static final int WIDTH = 900;//ширина окна
    private static final int HEIGHT = 600;//высота окна
    public static int BTN_GAME_SIZE = (HEIGHT-100)/size;//ширина окна
    private final int BTN_WIDTH =200;//ширина кнопок меню
    private final int BTN_HEIGHT =50;//высота кнопок меню
    public static String style = "-fx-font-size: 2em; ";
    public static String styleHead = "-fx-font-size: 3em; ";
    public static String styleGame = "-fx-font-size: 5em; -fx-border-color: #000000";
    
    public static int clicks =0;//переменная для подсчета ходов
    public static Label currentScore = new Label("Число ходов: 0");//отображает число текущих ходов
    public static String imageName = "BigWave.jpg";
    public static boolean isNumeric = false;
    
    
    @Override
    public void start(Stage primaryStage) {
        
        //МЕНЮ
        Button playBtn = new Button ("Играть!");
        playBtn.setMaxWidth(BTN_WIDTH);
        playBtn.setStyle(style);
        Button settingsBtn = new Button ("Настройки");
        settingsBtn.setMaxWidth(BTN_WIDTH);
        settingsBtn.setStyle(style);
        Button scoreBtn = new Button ("Рекорды");
        scoreBtn.setMaxWidth(BTN_WIDTH);
        scoreBtn.setStyle(style);
        /*String backgroundPath = getClass().getResource("Images/background.jpg").toString();
        try{
            Image image = new Image(backgroundPath);//загружаем изображение
            ImageView imageView = new ImageView(image);
            menu.getChildren().add(imageView);
            
        } catch (Exception e) {//ловим ошибку
            System.out.println("ОШИБКА: Загрузка изображения");
        }*/
        menu.getChildren().addAll(playBtn, settingsBtn, scoreBtn);
        menu.setMargin(playBtn,new Insets(HEIGHT/3, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        menu.setMargin(settingsBtn,new Insets(20, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        menu.setMargin(scoreBtn,new Insets(20, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        menu.setId("menu");
        //кнопка возврата в меню из игры
        Button backBtn = new Button ("Вернуться в меню");
        backBtn.setStyle(style);
        
        //НАСТРОЙКИ ИГРЫ
        Label labelSettings = new Label("Настройки");
        labelSettings.setStyle(styleHead);
        //выбор размера поля
        Label labelSize = new Label("Выберите размер игры:");
        labelSize.setStyle(style);
        RadioButton threeBtn = new RadioButton("3x3");
        threeBtn.setStyle(style);
        RadioButton fourBtn = new RadioButton("4x4");
        fourBtn.setStyle(style);
        ToggleGroup groupSize = new ToggleGroup();
        threeBtn.setToggleGroup(groupSize);// установка группы
        fourBtn.setToggleGroup(groupSize);
        Button okBtn = new Button("OK");
        okBtn.setMaxWidth(BTN_WIDTH);
        okBtn.setStyle(style);
        settings.getChildren().addAll(labelSettings, labelSize, threeBtn, fourBtn);
        settings.setPadding(new Insets(50));
        //выбор изображения
        ObservableList<String> imageList = FXCollections.observableArrayList("Большая волна", "Котик", "Цифры");
        ComboBox<String> imageComboBox = new ComboBox<String>(imageList);
        imageComboBox.setStyle(style);
        imageComboBox.setValue("Большая волна"); // устанавливаем выбранный элемент по умолчанию
        Label labelImage = new Label("Выберите картинку:");
        labelImage.setStyle(style);
        settings.getChildren().addAll(labelImage, imageComboBox);
        settings.getChildren().add(okBtn);//кнопка возвращения в меню
        
        //СПИСОК РЕКОРДОВ
        Records r = new Records();
        r.readRecords();//считываем из файла список
        TreeSet recordsList = Records.listOfRecords;
        TableView<Records> table = new TableView<Records>();
        table.getItems().addAll(recordsList);
        table.setPrefWidth(500);
        table.setPrefHeight(500);
        // столбец для вывода имени
        TableColumn<Records, String> nameColumn = new TableColumn<Records, String>("Имя");
        // определяем фабрику для столбца с привязкой к свойству name
        nameColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("name"));
        table.getColumns().add(nameColumn);
        // столбец для вывода счета
        TableColumn<Records, Integer> scoreColumn = new TableColumn<Records, Integer>("Счет");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<Records, Integer>("score"));
        table.getColumns().add(scoreColumn);
        table.setStyle(style);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //заголовок
        Label labelScore = new Label("Список рекордов");
        labelScore.setStyle(styleHead);
        //кнопка возврата в меню
        Button okBtn2 = new Button("OK");
        okBtn2.setMaxWidth(BTN_WIDTH);
        okBtn2.setStyle(style);
        //страница рекордов
        score.getChildren().addAll(labelScore,table, okBtn2);
        score.setPadding(new Insets(50));
        score.setMargin(okBtn2, new Insets(10,WIDTH/2-BTN_WIDTH/2,10,WIDTH/2-BTN_WIDTH/2));
        score.setMargin(labelScore, new Insets(10,WIDTH/4,10,WIDTH/4));
        
        Scene sceneMenu = new Scene(menu, WIDTH, HEIGHT);
        Scene sceneSettings = new Scene (settings, WIDTH, HEIGHT);
        Scene sceneScore = new Scene (score, WIDTH, HEIGHT);
        sceneMenu.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
        
        
        primaryStage.setTitle("Game");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
        
        //кнопка "Играть"
        playBtn.setOnMousePressed((MouseEvent me) -> {
            //создаем игру
            clicks =0;
            gameField = new GridPane();
            Game game1=new Game();
            addButtons();
            //ИГРА
            currentScore = new Label("Число ходов: 0");
            currentScore.setStyle(style);
            gameMenu=new VBox();
            gameMenu.getChildren().addAll(currentScore, backBtn);
            gameMenu.setPadding(new Insets(50));
            game = new HBox();
            game.getChildren().addAll(gameField, gameMenu);
            game.setMargin(currentScore,  new Insets(20));
            Scene sceneGame = new Scene (game, WIDTH, HEIGHT);
            
            primaryStage.setScene(sceneGame);
            primaryStage.show();
        });
        //кнопка "Настройки"
        settingsBtn.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneSettings);
            primaryStage.show();
        });
        scoreBtn.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneScore);
            primaryStage.show();
        });
        //Выбор размера игры 3*3
        threeBtn.setOnMousePressed((MouseEvent me) -> {
            size = 3;
            BTN_GAME_SIZE = HEIGHT/size;//обновляем размер
        });
        //Выбор размера игры 4*4
        fourBtn.setOnMousePressed((MouseEvent me) -> {
            size = 4;
            BTN_GAME_SIZE = HEIGHT/size;//обновляем размер
        });
        //кнопка "ОК", возвращаемся в меню
        okBtn.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneMenu);
            primaryStage.show();
        });
        okBtn2.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneMenu);
            primaryStage.show();
        });
        //выбор картинки
        imageComboBox.setOnAction(event -> {
                if (imageComboBox.getValue().equals("Большая волна")){
                    imageName = "BigWave.jpg";
                    isNumeric = false;
                }
                else if (imageComboBox.getValue().equals("Котик")){
                    imageName = "cat.jpg";
                    isNumeric = false;
                }
                else if (imageComboBox.getValue().equals("Цифры"))
                    isNumeric = true;
        } );
        backBtn.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneMenu);
            primaryStage.show();
        });
    }

    
    public static void addButtons (){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                NewButton btn=Game.getAllButtons().get(Game.getSolution().indexOf(new Point(j, i)));//берем кнопку с текущими координатами
                FinalProject.gameField.add(btn.getBtn(), j, i);//добавляем кнопку в узел
            }
        }
    };
    

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
