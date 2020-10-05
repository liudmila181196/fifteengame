
package finalproject;

import javafx.scene.image.*;
import java.util.ArrayList;

public class SplitPicture {

    public ArrayList <Image> getListOfImage() {//возвращает лист с частями изображения
        return listOfImage;
    }
    
    private Image image;//оригинальное изображение
    private int width;//ширина изображения
    private int height;//высота изображения
    private ArrayList <Image> listOfImage=new ArrayList<>();//лист с частями изображения
    
    
    public SplitPicture(int columns, int rows){
        
        try{
            image=new Image(getClass().getResource("Images/"+FinalProject.imageName).toString());//загружаем изображение
            width=(int)image.getWidth();//ширина изображения
            height=(int)image.getHeight();//высота изображения
            } catch (Exception e) {//ловим ошибку
                System.out.println("ОШИБКА: Загрузка изображения");
            }
        
        PixelReader pixelReader = image.getPixelReader();//создаем PixelReader на основе изображения
        
        try{
            for (int i = 0; i < rows; i++) {//идем по строкам
                    for (int j = 0; j < columns; j++) {//идем по столбцам
                        //вырезаем из изображения нужный кусочек в соответствии с текущей стобцом и строкой
                        Image img=(Image)new WritableImage(pixelReader , j * width / columns, i * height / rows, width / columns, height / rows);
                        listOfImage.add(img);//добавляем кусочек в лист
                    }
            }
        }catch(Exception ex){//ловим ошибку
            System.out.println("ОШИБКА: Добавление в listOfImage");
        }
    }  
}