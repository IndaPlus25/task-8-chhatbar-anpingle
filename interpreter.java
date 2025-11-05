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

    public ArrayList<String> read_instructions(ArrayList<String> a) {
        
        ArrayList<String> fixedLines = new ArrayList<String>();

        for (String line : lines) {
            int end = line.indexOf("//");
            // if (line != "\n" || line!=" "){
                if (end ==-1) {
                    line = line.strip();
                }
                else {
                    line = line.substring(0,end).strip();
                }
                fixedLines.add(line);
            // }
        }
        // for (int i = 0; i < fixedLines.size(); i++) {
        //     if (fixedLines.get(i)==" "){
        //         fixedLines.remove(i);
        //     }
        // }
        
        return fixedLines;
                    //ArrayList<String> words = line.split(" ");
            //int index = 0;
            //for (String s : words) {


                /* 
                String strippedString = 
                
                if (s == " ") {
                    words.remove(index);
                }
                else if (s == ) //hello this is a comment
                {

                }

                index ++;
                */
    
    }

    public void read_input() {
        Scanner sc = new Scanner(System.in);
        registers[1] = sc.nextInt();
    }

    public int removehash(String str) {
        String[] x = str.split("#");
        int y = Integer.parseInt(x[1]);
        return y;
    }

    public void parse_instructions(ArrayList<String> instructions){
        int i = 0;
        while (i < instructions.size()){
            String[] nospace = instructions.get(i).split(" ");
            String instruction = nospace[0];
            if(instruction.equals("input")){
                read_input();
            }
            else if (instruction.equals("exit")){
                break;
            }
            else if (instruction.equals("print")){
                System.out.println(registers[1]);
            }
            else if (instruction.equals("add")){
                int r1 = removehash(nospace[1]);
                int r2 = removehash(nospace[2]);
                int imm = Integer.parseInt(nospace[3]);
                registers[r1] = registers[r1] + registers[r2] + imm;
            }
            else if (instruction.equals("sub")){
                int r1 = removehash(nospace[1]);
                int r2 = removehash(nospace[2]);
                int imm = Integer.parseInt(nospace[3]);
                registers[r1] = registers[r1] - registers[r2] - imm;

            }
            else if (instruction.equals("set")){
                int r1 = removehash(nospace[1]);
                int r2 = removehash(nospace[2]);
                int imm = Integer.parseInt(nospace[3]);
                registers[r1] = registers[r2] + imm;

            }
            else if (instruction.equals("j")){
                i+=Integer.parseInt(nospace[1]);
            }
            else if (instruction.equals("jeq")){
                int r1 = removehash(nospace[1]);
                int r2 = removehash(nospace[2]);
                int imm = Integer.parseInt(nospace[3]);
                if (registers[r1]==registers[r2]){
                    i+=imm;
                }
                else{
                    if (imm==0){
                        i+=1;
                    }
                }
            }
            i++;
        }
    }

    public static void main(String[] args) {
        interpreter test = new interpreter();
        ArrayList<String> k = test.fileline("example.bbvv");
        System.out.println(k);
        ArrayList<String> p = test.read_instructions(k);
        System.out.println(p);
        test.parse_instructions(p);
    }
}
