
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

    public static ArrayList<NewButton> getAllButtons() {
        return allButtons;
    }

    private static ArrayList<NewButton> allButtons=new ArrayList<>();//лист со всеми кнопками
    private NewButton emptyButton;//пустая кнопка
    private static ArrayList<Point> solution=new ArrayList<>();//лист с порядком координат
    private SplitPicture splitPicture;//изображение
    
    public Game(){
        solution.clear();
        splitPicture = null;
        allButtons.clear();
        
        
        for(int i=0; i<FinalProject.size;i++){
            for (int j=0; j<FinalProject.size; j++){
                solution.add(new Point(j, i));//создаем лист координат
            }
        }
        if (FinalProject.isNumeric){
            int number=0;
            for (int i = 0; i < FinalProject.size; i++) {
                for (int j = 0; j < FinalProject.size; j++) {
                    number++;
                    if (i==FinalProject.size-1 && j==FinalProject.size-1){//если это последняя кнопка
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
            splitPicture=new SplitPicture(FinalProject.size, FinalProject.size);//создаем изображение
        
            for (int i = 0; i < FinalProject.size; i++) {
                for (int j = 0; j < FinalProject.size; j++) {
                    Image img=splitPicture.getListOfImage().get(solution.indexOf(new Point(j, i)));//берем кусочек изображения с текущими координатами
                    if (i==FinalProject.size-1 && j==FinalProject.size-1){//если это последняя кнопка
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
    }
    
    //Проверка на решаемость
    //Если конкретной пятнашке предшествует пятнашка с более 
    //высоким значением, это считается инверсией. Когда пустая пятнашка находится на своем месте, 
    //число инверсий должно быть четным, чтобы головоломка была разрешимой. 
    //Итак, мы подсчитываем количество инверсий и возвращаем true, если число четное.)
    private boolean isSolvable() {
        int countInversions = 0;
        int I=0, J=0;
        for (int i = 0; i < Math.pow(FinalProject.size, 2); i++) {
          for (int j = 0; j < i; j++) {
            I=allButtons.get(i).rightLocation.y*FinalProject.size+allButtons.get(i).rightLocation.x;
            J=allButtons.get(j).rightLocation.y*FinalProject.size+allButtons.get(j).rightLocation.x;
            if (I<J)
              countInversions++;
          }
        }
        return countInversions % 2 == 0;
    }
    
}