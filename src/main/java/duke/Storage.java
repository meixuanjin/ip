package duke;

import duke.tasks.TaskList;

import java.io.*;

public class Storage {
    private static final String text = "./data/duke.txt";
    private static BufferedWriter writer;

    //takes in a task and adds it to the list while writing it to a file
    static void writeToFile(String task) throws IOException {
        try {
            Parser.parseTask(task);
            if (task.startsWith("deadline") ||
                    (task.startsWith("event") && task.contains("/at ")) ||
                    (task.startsWith("todo"))) {
                TaskList.addSpecificTask(task);
                writer.write(TaskList.getLast().toString() + "\n");
            }
            writer.close();
            FileWriter fWriter = new FileWriter(text, true);
            writer = new BufferedWriter(fWriter);
        } catch (IOException | DukeException e) {
            System.out.println("Something went wrong");
        }
    }

    static void readFromFile() throws IOException {
        try {
            //create a BufferedReader which loads the data when duke.Duke starts up
            String line;
            FileReader fReader = new FileReader(text);
            BufferedReader reader = new BufferedReader(fReader);
            FileWriter fWriter = new FileWriter(text, true);
            writer = new BufferedWriter(fWriter);

            //reading data
            while((line = reader.readLine()) != null) {
                String[] curr = new String[4];
                curr[0] = line.substring(1,2);
                curr[1] = line.substring(4,5);
                curr[2] = line.substring(7);
                switch (curr[1]) {
                    case "T" -> {
                        TaskList.addSpecificTask("todo " + curr[2]);
                        if (curr[1].equals("X")) {
                            TaskList.getLast().markAsDone();
                        }
                    }
                    case "E" -> {
                        TaskList.addSpecificTask("event " + curr[2]);
                        if (curr[1].equals("X")) {
                            TaskList.getLast().markAsDone();
                        }
                    }
                    case "D" -> {
                        TaskList.addSpecificTask("deadline " + curr[2]);
                        if (curr[1].equals("X")) {
                            TaskList.getLast().markAsDone();
                        }
                    }
                    default -> {
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            File dir = new File(text);
            FileWriter fWriter = new FileWriter(text, true);
            writer = new BufferedWriter(fWriter);
        }
    }

    public static void overwrite() throws IOException {
        FileWriter fWriter = new FileWriter(text, false);
        writer = new BufferedWriter(fWriter);
        writer.write(TaskList.overwrite());
        writer.close();
        FileWriter newWriter = new FileWriter(text, true);
        writer = new BufferedWriter(newWriter);
    }
}