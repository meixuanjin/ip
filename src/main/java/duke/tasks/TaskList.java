package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks.
 */
public final class TaskList {
    private static final List<String> taskString = new ArrayList<>();
    private static final List<Tasks> taskList = new ArrayList<>();
    private static int taskNumber = 1;
    private static final String LINE = "-----------------------------------------";

    /**
     * Adds a task to the list and prints the addition.
     * @param task Task to be added
     */
    public static void add(Tasks task) {
        taskString.add(taskNumber + ". ");
        taskList.add(task);
        taskNumber++;
        System.out.println(LINE);
        System.out.println("Got it. I've added this task: \n" + task);
        System.out.println("Now you have " + taskList.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Adds a specific task according to the correct class
     * @param task task to be added
     */
    public static void addSpecificTask(String task) {
        if (task.startsWith("deadline")) {
            String[] input = task.split(" /by ", 2);
            LocalDateTime dueTime = LocalDateTime.parse(input[1], DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
            TaskList.add(new Deadline(task, dueTime));
        } else if (task.startsWith("event") && task.contains("/at ")) {
            String[] input = task.split(" /at ", 2);
            LocalDateTime startTime = LocalDateTime.parse(input[1], DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
            TaskList.add(new Event(task, startTime));
        } else if (task.startsWith("todo")) {
            String[] input = task.split("todo ", 2);
            TaskList.add(new Todo(input[1]));
        }
    }

    /**
     * Marks a task as completed and prints this change.
     * @param task Task to complete
     */
    public static void complete(String task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        String numString = task.replaceAll("[^0-9]", "");
        int num = Integer.parseInt(numString);
        for (int counter = 0; counter < taskString.size(); counter++) {
            if (counter == num - 1) {
                taskList.get(num - 1).markAsDone();
            }
        }
        System.out.println(LINE);
    }

    /**
     * Prints the current task list.
     */
    public static void printList() {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        int counter = 0;
        for (String str : taskString) {
            System.out.println(str + taskList.get(counter).toString());
            counter++;
        }
        System.out.println(LINE);
    }

    /**
     * Deletes an item from the taskList and taskString.
     * @param i Index of task to be deleted
     */
    public static void deleteTask(int i) {
        Tasks deletedTasks = taskList.get(i - 1);
        taskList.remove(i - 1);
        taskString.remove(i - 1);
        System.out.println("Noted. I've removed this task: \n" + deletedTasks + "\nNow you have "
                + taskString.size() + " tasks in the list.");
    }

    /**
     * Rewrites the duke.txt file with the new list of tasks.
     * @return a String representation of the task list
     */
    public static String overwrite() {
        String str = "";
        for (Tasks task : taskList) {
            str = task.toString() + "\n";
            System.out.println(task);
        }
        return str;
    }

    /**
     * Gets the most recent task.
     * @return most recent Task
     */
    public static Tasks getLast() {
        return taskList.get(taskList.size() - 1);
    }

    /**
     * Prints the tasks containing a keyword that the user has searched.
     * @param key keyword being searched.
     */
    public static void find(String key) {
        System.out.println(LINE);
        System.out.println("Here are the matching tasks in your list:");
        int count = 1;
        for (Tasks task : taskList) {
            if (task.toString().contains(key)) {
                System.out.println(count + ". " + task.toString());
                count++;
            }
        }
        System.out.println(LINE);
    }
}
