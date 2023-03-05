import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Utility {
    public static void writeFile(ArrayList<Chat> chats){
        try {
            FileWriter myWriter = new FileWriter("history.txt");
            for (Chat chat : chats) {
                myWriter.write(chat.getChat());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ArrayList<Chat> readFile(){
        ArrayList<Chat> chats = new ArrayList<>();
        try {
            File myObj = new File("history.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data[] = myReader.nextLine().split("#");
              chats.add(new Chat(data[0], data[1]));
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("No History Found!");
            e.printStackTrace();
          }

        return chats;
    }
}
