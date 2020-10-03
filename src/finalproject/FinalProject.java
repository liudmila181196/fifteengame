
package finalproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class FinalProject extends Application {
    public static GridPane root = new GridPane();
    private final int COLUMNS=3;//задаем кол-во столбцов
    private final int ROWS=3;//задаем кол-во строк
    @Override
    public void start(Stage primaryStage) {
        
        Game game1=new Game(COLUMNS, ROWS);//создаем игру
        
        Scene scene = new Scene(root, 1250, 830);
        
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
