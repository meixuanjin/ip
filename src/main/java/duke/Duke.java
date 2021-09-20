package duke;

import java.io.IOException;
import java.util.Scanner;

/**
 * This is a Duke application, which allows for user interaction.
 */
public class Duke {
    private static Parser parser;
    private static Storage storage;
    private static final String LINE = "-----------------------------------------";

    public Duke() {
        parser = new Parser();
        storage = new Storage();
    }

    /**
     * Main method of the application.
     * @param args -
     * @throws IOException an IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        Ui.sayHello();
        String task = sc.nextLine();
        storage.readFromFile();

        while (!task.equals("bye")) {
            try {
                System.out.println(parser.parseTask(task));
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
            task = sc.nextLine();
        }
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
        sc.close();
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) throws DukeException, IOException {
        try {
            return parser.parseTask(input);
        } catch (DukeException | IOException e) {
            return e.getMessage();
        }
    }
}
