package eve.parser;

import java.time.LocalDateTime;
import java.util.ArrayList;

import eve.Eve;
import eve.TaskList;
import eve.exceptions.EveExceptions;
import eve.tasks.Task;

/**
 * Parser class is used to parse the input and execute the commands
 */

public class Parser {

    private Eve eve;

    public Parser(Eve eve) {
        this.eve = eve;
    }

    /**
     * This method is used to parse the input and execute the commands
     * this is the main business logic of the chatbot
     * @param input
     * @return String
     * @throws EveExceptions
     */
    public String parseAndExecute(String input) throws EveExceptions {
        String[] tempyArr = getSplitParts(input);
        String commandCheck = tempyArr[0].toUpperCase();

        assert tempyArr.length > 0 : "Input is empty";

        ArrayList<Task> list = eve.getTaskList();
        try {
            switch (commandCheck) {
            case "BYE":
                return Commands.commandBye();
            case "LIST":
                return TaskList.commandList(list);
            case "MARK":
                return TaskList.commandMark(tempyArr, list);

            case "UNMARK":
                return TaskList.commandUnMark(tempyArr, list);
            case "DELETE":
                return TaskList.commandDelete(tempyArr, list);
            case "TODO":
                return TaskList.commandTodo(tempyArr, list);
            case "DEADLINE":
                return TaskList.commandDeadline(tempyArr, list);
            case "EVENT":
                return TaskList.commandEvent(tempyArr, list);
            case "FIND":
                return TaskList.commandFind(tempyArr, list);
            case "TAG":
                return TaskList.commandTag(tempyArr, list);

            default:
                return "OOPS!!! I'm sorry, but I don't know what that means, please try again";

            }
        } catch (EveExceptions e) {
            return "Error occured: " + e.getMessage();
        }

    }

    /**
     * This method is used to convert a string to a date time object
     * @param s
     * @return LocalDateTime
     */
    public static LocalDateTime stringToDateTime(String s) {
        String[] temp = s.split(" ");
        int hour = Integer.parseInt(temp[1].substring(0, 2));
        int min = Integer.parseInt(temp[1].substring(2));
        String[] dateTemp = temp[0].split("/");

        assert dateTemp.length == 3 : "Date format is wrong";
        int day = Integer.parseInt(dateTemp[0]);
        int month = Integer.parseInt(dateTemp[1]);
        int year = Integer.parseInt(dateTemp[2]);



        LocalDateTime newDateTime = LocalDateTime.of(year, month, day, hour, min);

        return newDateTime;
    }

    private static String[] getSplitParts(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        return parts;
    }
}
