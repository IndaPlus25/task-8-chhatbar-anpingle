import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class interpreter{
    private int[] registers = {0,1,2,3};
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
    private boolean isValidOp(String op){
        return op.equals("add") || op.equals("sub") || op.equals("set") || op.equals("jeq") || op.equals("j") || op.equals("input") || op.equals("print") || op.equals("exit");
    }
    private boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isValidRegister(String s){
        return s.equals("#1") || s.equals("#2") || s.equals("#3");
    }
    private boolean isValidImm(String s) {
        return s.equals("0") || s.equals("1");
    }
    public boolean isValidInstructions(ArrayList<String> instructions){
        int num = 0;
        for (String line : instructions) {
            num++;
            if (line.isBlank()){
                continue;
            }
            String[] words = line.split("\\s+");
            String operator = words[0];
            if (!isValidOp(operator)){
                System.out.println("Syntax ERROR at line "+num+": Unknown operator " + operator +"");
                return false;
            }
            switch (operator){
                case "input":
                case "print":
                case "exit":
                    if (words.length != 1){
                        System.out.println("Syntax ERROR at line "+num+": Operator "+operator+" takes no arguments");
                        return false;
                    }
                    break;
                case "add":
                case "sub":
                case "set":
                case "jeq":
                    if (words.length != 4){
                        System.out.println("Syntax ERROR at line "+num+": Operator "+operator+" requires 3 arguments");
                        return false;
                    }
                    if (!isValidRegister(words[1]) || !isValidRegister(words[2])){
                        System.out.println("Syntax ERROR at line "+num+": Invalid registry");
                        return false;
                    }
                    if (!isValidImm(words[3])){
                        System.out.println("Syntax ERROR at line "+num+": Imm must be either 0 or 1");
                        return false;
                    }
                    break;
                case "j":
                    if (words.length != 2){
                        System.out.println("Syntax ERROR at line "+num+": Operator "+operator+" requires 1 argument");
                        return false;
                    }
                    if (!isInteger(words[1])){
                        System.out.println("Syntax ERROR at line "+num+": Operator "+operator+" requires 1 INTEGER argument");
                        return false;
                    }
                    break;
            }
        }
        return true;
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
        ArrayList<String> p = test.read_instructions(k);
        if (test.isValidInstructions(p)){
            test.parse_instructions(p);
        }
    }
}
