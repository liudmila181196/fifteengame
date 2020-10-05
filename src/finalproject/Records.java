/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.*;
import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Scanner;
/**
 *
 * @author nazar
 */
public class Records implements Comparable<Records> {
    public Records(String name, int score){
        this.name = name;
        this.score = score;
    }
    
    public Records(){
    }
    
    public int compareTo(Records r){
     
        return score-r.getScore();
    }
    public TreeSet getRecords(){
        return listOfRecords;
    }
    
    public void setRecords(){
        
    }
    public String getName(){
        return this.name;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public static String filename = "records.txt";
    public static  TreeSet<Records> listOfRecords= new TreeSet<Records>();
    public String name="";
    public int score=0;
    
    public void newRecord(){
        TextInputDialog dialog = new TextInputDialog("Best Gamer");
        dialog.setTitle("Введите имя");
        dialog.setHeaderText("Поздравляем! Хотите быть в списке рекордов?");
        dialog.setContentText("Пожалуйста, введите имя:");

        // Traditional way to get the response value.
        Optional<String> name = dialog.showAndWait();
        if (name.isPresent()){
            try
            {
                FileWriter writer = new FileWriter("src/finalproject/Records/"+filename, true);
               // запись всей строки
                String text = name.get()+"#"+FinalProject.clicks+"\n";
                writer.write(text);
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            } 
        }
        
    }

    public void readRecords(){
        try
        {
            InputStream ist= getClass().getResourceAsStream("Records/"+filename);
            Scanner sc = new Scanner(ist);
            //чтение построчно
            String s;
            while((s=sc.nextLine())!=null){
                String[] words = s.split("#");
                listOfRecords.add(new Records(words[0], Integer.parseInt(words[1])));
                System.out.println("");
            }
            sc.close();
        }
         catch(Exception ex){
            System.out.println(ex.getMessage());
        }  
    }
   
    
}
