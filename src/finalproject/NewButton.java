
package finalproject;

import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.awt.Point;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import java.util.Collections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.io.*;

public class NewButton {

    public Button getBtn() {//передаем кнопку
        return btn;
    }
    
    private boolean isEmptyButton;//логическая переменнная для идентификации пустой кнопки
    private Button btn;//кнопка
    public final Point rightLocation;//правильные координаты кнопки
    
    public NewButton(int col, int row){//конструктор пустой кнопки
        btn=new Button();
        btn.setStyle(FinalProject.styleGame);
        this.setEmptyButton();//идентификатор пустой кнопки true
        rightLocation=new Point (col, row);//запоминаем координаты
        interactions();
    }
    
    public NewButton(Image img, int col, int row) {//конструктор кнопки с изображением
        btn=new Button();
        isEmptyButton = false;//идентификатор пустой кнопки false
        ImageView iv=new ImageView(img);
        iv.setFitHeight(FinalProject.BTN_GAME_SIZE);
        iv.setFitWidth(FinalProject.BTN_GAME_SIZE);
        btn.setGraphic(iv);//фоном кнопки будет кусочек изображения
        rightLocation=new Point (col, row);//запоминаем координаты
        interactions();
    }
    
    public NewButton(int col, int row, int number){
        btn=new Button();
        isEmptyButton = false;//идентификатор пустой кнопки false
        btn.setText(Integer.toString(number));
        btn.setStyle(FinalProject.styleGame);
        rightLocation=new Point (col, row);//запоминаем координаты
        interactions();
    }
    
    private void interactions(){//отслеживаем нажатие кнопок
        btn.setOnMousePressed((MouseEvent me) -> {//при нажатии кнопки
            int emptyIndex = 0;//индекс пустой кнопки
            int btnIndex=-10;//индекс нажатой кнопки
            ArrayList<NewButton> buttons=Game.getAllButtons();//лист всех кнопок
            
            for (NewButton button : buttons) {//проходим по всем кнопкам
                if (button.isEmptyButton()) {//если пустая кнопка
                    emptyIndex = buttons.indexOf(button);//индекс пустой кнопки
                }
                if (button.getBtn().equals((Button)btn)){//если текущая кнопка эквивалентна нажатой
                    btnIndex =buttons.indexOf(button);//индекс нажатой кнопки
                }
            }
            //если (индекс нажатой - или +1) равен индексу пустой или если (индекс нажатой - или + кол-во кнопок в строке(столбцов)) равен индексу пустой
            if ((btnIndex - 1 == emptyIndex) || (btnIndex + 1 == emptyIndex)
                    || (btnIndex - Game.getCol() == emptyIndex) || (btnIndex + Game.getCol() == emptyIndex)) {
                Collections.swap(buttons, btnIndex, emptyIndex);//то меняем кнопки друг с другом
                update();
                FinalProject.clicks++;
                FinalProject.currentScore.setText("Число ходов: "+String.valueOf(FinalProject.clicks));
            }
        });
        
    }
    
    private void update() {//обновляем поле

            FinalProject.game.getChildren().clear();//очищаем корневой узел
            ArrayList<NewButton> buttons=Game.getAllButtons();//лист всех кнопок
            
            for (int i = 0; i < Game.getRow(); i++) {//идем по строкам
                for (int j = 0; j < Game.getCol(); j++) {//идем по столбцам
                    NewButton btn=buttons.get(Game.getSolution().indexOf(new Point(j, i)));//берем кнопку с текущими координатами
                    FinalProject.game.add(btn.getBtn(), j, i);//добавляем в узел
                }
            }
            check();
    }
    
    private void check() {//проверка на правильность картинки

        ArrayList<Point> current = new ArrayList<>();//лист с правильными координатами кнопок в текущем положении
        ArrayList<NewButton> buttons=Game.getAllButtons();//лист с кнопками
        
        for (int i = 0; i < Game.getRow(); i++) {//идем по строкам
                for (int j = 0; j < Game.getCol(); j++) {//идем по столбцам
                    NewButton btn=buttons.get(Game.getSolution().indexOf(new Point(j, i)));//берем кнопку с текущими координатами
                    current.add((Point) btn.rightLocation);//добавляем в лист правильные координаты кнопок в текущем порядке
                }
        }
        if (current.equals(Game.getSolution())) {//если порядок правильных координат верен
            //showAlert();//показываем сообщение
            Records rec = new Records();
            rec.newRecord();
        }
    }
    /*
    private void showAlert() {//сообщение
        Alert alert = new Alert(AlertType.INFORMATION);//создаем окно сообщения
        alert.setTitle("Congratulations!");//название окна
        alert.setHeaderText(null);//без заголовка
        alert.setContentText("Congratulations! You win!");//добавляем сообщение
        alert.showAndWait();//показываем сообщение
        
    }*/
    
    public void setEmptyButton() {//пустая кнопка
        isEmptyButton = true;
    }

    public boolean isEmptyButton() {//берем значение идентификатора
        return isEmptyButton;
    }
}
