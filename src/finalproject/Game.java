
package finalproject;

import com.sun.glass.ui.Application;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;

public class Game {

    public static ArrayList<Point> getSolution() {
        return solution;
    }

    public static int getCol() {
        return col;
    }

    public static int getRow() {
        return row;
    }

    public static ArrayList<NewButton> getAllButtons() {
        return allButtons;
    }

    private static ArrayList<NewButton> allButtons=new ArrayList<>();//лист со всеми кнопками
    private NewButton emptyButton;//пустая кнопка
    private static ArrayList<Point> solution=new ArrayList<>();//лист с порядком координат
    private SplitPicture splitPicture;//изображение
    private static int col;//кол-во столбцов
    private static int row;//кол-во строк
    
    public Game(int columns, int rows, boolean isNumeric){
        col=columns;
        row=rows;
        
        for(int i=0; i<row;i++){
            for (int j=0; j<col; j++){
                solution.add(new Point(j, i));//создаем лист координат
            }
        }
        if (isNumeric){
            int number=0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    number++;
                    if (i==row-1 && j==col-1){//если это последняя кнопка
                        emptyButton = new NewButton(j, i);//создаем пустую кнопку с текущими координатами
                        emptyButton.getBtn().setMinSize(FinalProject.BTN_GAME_SIZE, FinalProject.BTN_GAME_SIZE);//задаем размер кнопки
                        allButtons.add(emptyButton);//добавляем в лист
                    }
                    else{
                        NewButton btn=new NewButton(j, i, number);//создаем кнопку с текущими координатами и нужным кусочком изображения
                        btn.getBtn().setMinSize(FinalProject.BTN_GAME_SIZE, FinalProject.BTN_GAME_SIZE);
                        allButtons.add(btn);//добавляем в лист
                    }
                }
            }
        }
        else{
            splitPicture=new SplitPicture(col, row);//создаем изображение
        
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Image img=splitPicture.getListOfImage().get(solution.indexOf(new Point(j, i)));//берем кусочек изображения с текущими координатами
                    if (i==row-1 && j==col-1){//если это последняя кнопка
                        emptyButton = new NewButton(j, i);//создаем пустую кнопку с текущими координатами
                        emptyButton.getBtn().setMinSize(FinalProject.BTN_GAME_SIZE+16, FinalProject.BTN_GAME_SIZE+8);//задаем размер кнопки
                        allButtons.add(emptyButton);//добавляем в лист
                    }
                    else{
                        NewButton btn=new NewButton(img, j, i);//создаем кнопку с текущими координатами и нужным кусочком изображения
                        btn.getBtn().setMinSize(FinalProject.BTN_GAME_SIZE+8, FinalProject.BTN_GAME_SIZE+8);
                        allButtons.add(btn);//добавляем в лист
                    }
                }
            }
        }
        
        //перемешиваем кнопки
        do{
            Collections.shuffle(allButtons.subList(0, allButtons.size()-1));
        }while(!isSolvable());
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                NewButton btn=allButtons.get(solution.indexOf(new Point(j, i)));//берем кнопку с текущими координатами
                FinalProject.game.add(btn.getBtn(), j, i);//добавляем кнопку в узел
            }
        }
    }
    
    //Проверка на решаемость
    //Если конкретной пятнашке предшествует пятнашка с более 
    //высоким значением, это считается инверсией. Когда пустая пятнашка находится на своем месте, 
    //число инверсий должно быть четным, чтобы головоломка была разрешимой. 
    //Итак, мы подсчитываем количество инверсий и возвращаем true, если число четное.)
    private boolean isSolvable() {
        int countInversions = 0;
        int I=0, J=0;
        for (int i = 0; i < col*row-1; i++) {
          for (int j = 0; j < i; j++) {
            I=allButtons.get(i).rightLocation.y*col+allButtons.get(i).rightLocation.x;
            J=allButtons.get(j).rightLocation.y*col+allButtons.get(j).rightLocation.x;
            if (I<J)
              countInversions++;
          }
        }
        return countInversions % 2 == 0;
    }
    
}