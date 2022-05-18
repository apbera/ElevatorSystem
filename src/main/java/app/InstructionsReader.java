package app;

import elevator.ElevatorSystem;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class responsible for reading user input in command line.
 */
public class InstructionsReader {
    void readInstructions(ElevatorSystem elevatorSystem) {
        Scanner scanner = new Scanner(System.in);
        InstructionsHandler instructionsHandler = new InstructionsHandler(elevatorSystem);
        while (true) {
            String instruction = scanner.nextLine().trim();
            if (instruction.equals("exit")) {
                return;
            }
            String[] splitInstruction = instruction.split("\\s+");
            try {
                InstructionsParser instructionsParser = new InstructionsParser(splitInstruction);
                instructionsHandler.handleInstruction(instructionsParser);
            } catch (NumberFormatException ex) {
                System.out.println("Wrong arguments type");
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
