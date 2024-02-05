import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Eve {

    private static final String directoryPath = "./data";
    private static final String filePath = "./data/Eve.txt";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Hello! I'm Eve");
        System.out.println(" What can I do for you?");
        
        //from CS2103T website
        try {
            printFileContents(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        String file2 = "./temp/lines.txt";
        try {
            writeToFile(file2, "first line" + System.lineSeparator() + "second line");
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

        String input = "";
        ArrayList<Task> list = new ArrayList<>();

        // refactor into cases
        while(!input.equals("bye")){
            input = sc.nextLine();

            String[] tempyArr = input.split(" ",2);
            String commandCheck = tempyArr[0];
            try {
                switch(commandCheck){

                case "bye":
                    commandBye();
                    break;
                case "list":
                    commandList(list);
                    break;
                case "mark":
                    commandMark(tempyArr, list);
                    break;
                case "unmark":
                    commandUnMark(tempyArr, list);
                    break;
                case "delete":
                    commandDelete(tempyArr, list);
                    break;
                case "todo":        
                    commandTodo(tempyArr, list);
                    break;
                case "deadline":    
                    commandDeadline(tempyArr, list);
                    break;
                case "event":
                    commandEvent(tempyArr, list);
                    break;    

                default:
                    throw new EveExceptions("OOPS!!! I'm sorry, but I don't know what that means, please try again");

                }
            } catch (EveExceptions e) {
                System.out.println(e.getMessage());
            }
                
        }

            
        
            

        sc.close();
    }

    // commands, to be refactored into own class later
    public static void commandBye(){
        System.out.println(" Bye. Hope to see you again soon !");
    }

    public static void commandList(ArrayList<Task> list){
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++ ){
            int j = i + 1;
            Task temp = list.get(i);
            System.out.println(j + "." + temp.toString());
        }
    }

    
    public static void commandMark(String[] tempyArr, ArrayList<Task> list){
        int index = Integer.parseInt(tempyArr[1]) - 1 ;
        Task temp = list.get(index);
        temp.markAsDone();
        System.out.println(" Nice! I've marked this task as done: ");
        System.out.println(temp.toString());
                
    }

    public static void commandUnMark(String[] tempyArr, ArrayList<Task> list){
        int index = Integer.parseInt(tempyArr[1]) - 1 ;
        Task temp = list.get(index);
        temp.markAsNotDone();
        System.out.println(" Nice! I've marked this task as not done yet: ");
        System.out.println(temp.toString());
                
    }

    public static void commandDelete(String[] tempyArr, ArrayList<Task> list){
        int index = Integer.parseInt(tempyArr[1]) - 1 ;
        Task temp = list.get(index);
        list.remove(temp);
        System.out.println("Noted. I've removed this task: ");
        System.out.println(temp.toString());
        System.out.println("Now you have " + list.size() +" tasks in the list.");       
    }

    public static void commandTodo (String[] tempyArr, ArrayList<Task> list) throws EveExceptions {
        
        if (tempyArr.length < 2) {
            throw new EveExceptions("This todo can't be empty");
        }
        String description = tempyArr[1];
        Task t = new Todo(description);
        list.add(t);

        System.out.println("Got it. I've added this task: ");
        System.out.println(t.toString());
        System.out.println("Now you have " + list.size() +" tasks in the list.");
    }

    public static void commandDeadline(String[] tempyArr, ArrayList<Task> list){
        String description = tempyArr[1];
        String arrTemp[] = description.split(" /by ");
        description = arrTemp[0];
        String by = arrTemp[1];
        Task t = new Deadline(description, by);
        list.add(t);

        System.out.println("Got it. I've added this task: ");
        System.out.println(t.toString());
        System.out.println("Now you have " + list.size() +" tasks in the list.");
    }

    public static void commandEvent(String[] tempyArr, ArrayList<Task> list){
        String description = tempyArr[1];
        String arrTemp[] = description.split(" /from ");
        description = arrTemp[0];
        String dateArr[] = arrTemp[1].split(" /to ");
        String startDate = dateArr[0];
        String endDate = dateArr[1];
        Task t = new Event(description, startDate, endDate);
        list.add(t);
        
        System.out.println("Got it. I've added this task: ");
        System.out.println(t.toString());
        System.out.println("Now you have " + list.size() +" tasks in the list.");
    }

    // The below functions are from CS2103T website Week 3
    private static void printFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        while (s.hasNext()) {
            System.out.println(s.nextLine());
        }
    }

    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd);
        fw.close();
    }

    private static void appendToFile(String filePath, String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(filePath, true); // create a FileWriter in append mode
        fw.write(textToAppend);
        fw.close();
    }
    

}
