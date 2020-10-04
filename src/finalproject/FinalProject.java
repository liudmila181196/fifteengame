
package finalproject;

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

public class FinalProject extends Application {
    public static GridPane game = new GridPane();
    public static HBox root = new HBox(20);
    public static VBox settings = new VBox(20);
    public static VBox gameMenu = new VBox(20);
    public static VBox score = new VBox(20);
    private static int columns=3;//задаем кол-во столбцов
    private static int rows = 3;//задаем кол-во строк
    private static final int WIDTH = 900;//ширина окна
    private static final int HEIGHT = 600;//высота окна
    public static int BTN_GAME_SIZE = (HEIGHT-100)/columns;//ширина окна
    private final int BTN_WIDTH =200;//ширина кнопок меню
    private final int BTN_HEIGHT =50;//высота кнопок меню
    public static VBox menu = new VBox();
    public static int clicks =0;//переменная для подсчета ходов
    public static Label currentScore = new Label("Число ходов: 0");//отображает число текущих ходов
    public static String imageName = "BigWave.jpg";
    public static String style = "-fx-font-size: 2em; ";
    public static String styleGame = "-fx-font-size: 5em; -fx-border-color: #000000";
    private static boolean isNumeric = false;
    
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
        menu.getChildren().addAll(playBtn, settingsBtn, scoreBtn);
        menu.setMargin(playBtn,new Insets(HEIGHT/3, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        menu.setMargin(settingsBtn,new Insets(20, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        menu.setMargin(scoreBtn,new Insets(20, WIDTH/2-BTN_WIDTH/2,20,WIDTH/2-BTN_WIDTH/2));
        
        //ИГРА
        currentScore.setStyle(style);
        //Button backBtn = new Button ("Вернуться в меню");
        //backBtn.setStyle(style);
        gameMenu.getChildren().addAll(currentScore);
        gameMenu.setPadding(new Insets(50));
        root.getChildren().addAll(game, gameMenu);
        root.setMargin(currentScore, new Insets(20));
        
        //НАСТРОЙКИ ИГРЫ
        //выбор размера
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
        settings.getChildren().addAll(labelSize, threeBtn, fourBtn);
        settings.setPadding(new Insets(100));
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
        //заголовок
        Label labelScore = new Label("Список рекордов");
        labelScore.setStyle(style);
        //кнопка возврата в меню
        Button okBtn2 = new Button("OK");
        okBtn2.setMaxWidth(BTN_WIDTH);
        okBtn2.setStyle(style);
        //страница рекордов
        score.getChildren().addAll(labelScore,table, okBtn2);
        
        
        Scene sceneMenu = new Scene(menu, WIDTH, HEIGHT);
        Scene sceneGame = new Scene (root, WIDTH, HEIGHT);
        Scene sceneSettings = new Scene (settings, WIDTH, HEIGHT);
        Scene sceneScore = new Scene (score, WIDTH, HEIGHT);
        
        primaryStage.setTitle("Game");
        primaryStage.setScene(sceneMenu);
        primaryStage.show();
        
        //кнопка "Играть"
        playBtn.setOnMousePressed((MouseEvent me) -> {
            //создаем игру
            Game game1=new Game(columns, rows, isNumeric);
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
            columns = 3;
            rows = 3;
            BTN_GAME_SIZE = HEIGHT/columns;//обновляем размер
        });
        //Выбор размера игры 4*4
        fourBtn.setOnMousePressed((MouseEvent me) -> {
            columns = 4;
            rows = 4;
            BTN_GAME_SIZE = HEIGHT/columns;//обновляем размер
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
                if (imageComboBox.getValue().equals("Большая волна"))
                    imageName = "BigWave.jpg";
                else if (imageComboBox.getValue().equals("Котик"))
                    imageName = "cat.jpg";
                else if (imageComboBox.getValue().equals("Цифры"))
                    isNumeric = true;
        } );
        /*backBtn.setOnMousePressed((MouseEvent me) -> {
            primaryStage.setScene(sceneMenu);
            primaryStage.show();
        });*/
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
