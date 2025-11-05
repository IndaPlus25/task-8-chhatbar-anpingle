import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class interpreter{
    private int[] registers = {0,1,2,3};
    private String[] r = {"add","sub","set", "jeq"};
    private String[] j = {"j"};
    private String[] spec = {"input", "print","exit"};
    private ArrayList<String> lines = new ArrayList<>();
    public ArrayList<String> fileline(String filename) {
        lines = new ArrayList<>();
        try (Scanner text = new Scanner(new File(filename))){
            while (text.hasNextLine()){
                String line = text.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e){
            System.out.println("Error"+ e.getMessage());
        }
        return lines;
    }
    public static void main(String[] args) {
        interpreter test = new interpreter();
        ArrayList<String> k = test.fileline("example.bbvv");
        System.out.println(k);
    }
}