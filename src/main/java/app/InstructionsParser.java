package app;

import elevator.OrderDirection;

import java.util.stream.IntStream;

/**
 * Class responsible for parsing user provided commands to instructions.
 * Class stores data about type of instruction and instruction arguments.
 */
public class InstructionsParser {
    private String instruction;
    private int[] instructionArguments;
    private OrderDirection pickupDirection = null;

    InstructionsParser(String[] arguments) {
        parse(arguments);
    }

    private void parse(String[] arguments) {
        switch (arguments[0]) {
            case "status" -> parseStatus(arguments);
            case "step" -> parseStep(arguments);
            case "pickup" -> parsePickup(arguments);
            case "update" -> parseUpdate(arguments);
            case "order" -> parseOrder(arguments);
            default -> throw new IllegalArgumentException("There is no such an instruction");
        }
    }

    private void parseStatus(String[] arguments) {
        instruction = arguments[0];
    }

    private void parseOrder(String[] arguments) {
        instruction = arguments[0];
        if (arguments.length < 3) {
            throw new IllegalArgumentException("Wrong number of arguments for order");
        }
        instructionArguments = IntStream.range(0, 2)
                .map(i -> Integer.parseInt(arguments[i + 1]))
                .toArray();
    }

    private void parseUpdate(String[] arguments) {
        instruction = arguments[0];
        if (arguments.length < 4) {
            throw new IllegalArgumentException("Wrong number of arguments for update");
        }
        instructionArguments = IntStream.range(0, 3)
                .map(i -> Integer.parseInt(arguments[i + 1]))
                .toArray();
    }

    private void parsePickup(String[] arguments) {
        instruction = arguments[0];
        if (arguments.length < 3) {
            throw new IllegalArgumentException("Wrong number of arguments for pickup");
        }
        instructionArguments = IntStream.range(0, 1)
                .map(i -> Integer.parseInt(arguments[i + 1]))
                .toArray();
        if (arguments[2].equals("up")) {
            pickupDirection = OrderDirection.UPWARDS;
        } else if (arguments[2].equals("down")) {
            pickupDirection = OrderDirection.DOWNWARDS;
        } else {
            System.out.println("Wrong direction");
        }
    }

    private void parseStep(String[] arguments) {
        instruction = arguments[0];
        instructionArguments = new int[1];
        if (arguments.length < 2) {
            instructionArguments[0] = 1;
        } else {
            instructionArguments[0] = Integer.parseInt(arguments[1]);
        }
    }

    String getInstruction() {
        return instruction;
    }

    int[] getInstructionArguments() {
        return instructionArguments;
    }

    OrderDirection getPickupDirection() {
        return pickupDirection;
    }
}
