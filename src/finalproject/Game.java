
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
    
    public Game(int columns, int rows){
        col=columns;
        row=rows;
        
        for(int i=0; i<row;i++){
            for (int j=0; j<col; j++){
                solution.add(new Point(j, i));//создаем лист координат
            }
        }
        
        splitPicture=new SplitPicture(col, row);//создаем изображение
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Image img=splitPicture.getListOfImage().get(solution.indexOf(new Point(j, i)));//берем кусочек изображения с текущими координатами
                if (i==row-1 && j==col-1){//если это последняя кнопка
                    emptyButton = new NewButton(j, i);//создаем пустую кнопку с текущими координатами
                    emptyButton.getBtn().setMinSize(img.getWidth()+16, img.getHeight()+8);//задаем размер кнопки
                    allButtons.add(emptyButton);//добавляем в лист
                }
                else{
                    NewButton btn=new NewButton(img, j, i);//создаем кнопку с текущими координатами и нужным кусочком изображения
                    allButtons.add(btn);//добавляем в лист
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
                FinalProject.root.add(btn.getBtn(), j, i);//добавляем кнопку в узел
                //System.out.println(solution.indexOf(new Point(j, i)));
            }
        }
    }
    
        
    private boolean isSolvable() {
        int countInversions = 0;
        int I=0, J=0;
        for (int i = 0; i < col*row-1; i++) {
          for (int j = 0; j < i; j++) {
            I=allButtons.get(i).rightLocation.x*col+allButtons.get(i).rightLocation.y;
            J=allButtons.get(j).rightLocation.x*col+allButtons.get(j).rightLocation.y;
            if (I<J)
              System.out.println(allButtons.get(i).rightLocation+"<"+allButtons.get(j).rightLocation);
              countInversions++;
          }
        }
        return countInversions % 2 == 0;
    }
    
}