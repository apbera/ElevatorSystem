package app;

/**
 * Class responsible for parsing initial program arguments: floors amount, elevators amount.
 */
public class OptionsParser {

    private int floorsAmount;
    private int elevatorsAmount;

    boolean checkIfArgsCorrectAndParse(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments needed: floorsAmount, elevatorsAmount");
            return false;
        }
        try {
            floorsAmount = Integer.parseInt(args[0]);
            elevatorsAmount = Integer.parseInt(args[1]);

        } catch (NumberFormatException ex) {
            System.out.println("Arguments need to be Integers: floorsAmount, elevatorsAmount");
            return false;
        }
        return areElevatorsAndFloorsAmountCorrect();
    }

    private boolean areElevatorsAndFloorsAmountCorrect() {
        if (floorsAmount < 0 || elevatorsAmount < 0) {
            System.out.println("Arguments can't be less than 0");
            return false;
        }
        if (elevatorsAmount > 16) {
            System.out.println("System supports up to 16 elevators");
            return false;
        }
        return true;
    }

    int getFloorsAmount() {
        return floorsAmount;
    }

    int getElevatorsAmount() {
        return elevatorsAmount;
    }
}
