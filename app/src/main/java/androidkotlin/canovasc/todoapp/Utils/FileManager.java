package androidkotlin.canovasc.todoapp.Utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import androidkotlin.canovasc.todoapp.Task;

public class FileManager {

    public static void writeToFile(Context context, Task task){
        if (TextUtils.isEmpty(task.filename)){
            task.filename = UUID.randomUUID().toString() + ".todoTask";
        }

        try{
            FileOutputStream fileOutputStream = context.openFileOutput(task.filename, Context.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream (fileOutputStream);
            outputStream.writeObject(task);
            outputStream.close();
            System.out.println("File saved");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }



    }

    public static void deleteTask(Context context, Task task){
        context.deleteFile(task.filename);
    }

    public static ArrayList<Task> loadTasks(Context context){
        ArrayList <Task> tasks = new ArrayList<>();
        File tasksDir = context.getFilesDir();

        for (String filename : tasksDir.list()){
            Task task = loadTask(context,filename);
            tasks.add(task);

        }


        System.out.println("File loaded");
        return tasks;
    }

    private static Task loadTask(Context context, String filename){

        Task task = null;

        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            task = (Task) objectInputStream.readObject();
            fileInputStream.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(ClassNotFoundException e){
            e.printStackTrace();

        }


        return task;
    }
}
