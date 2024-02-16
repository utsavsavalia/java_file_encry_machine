package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * Enigma simulator.
 *
 * @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        /* FIX ME - The item has been fixed. */
        Machine temp = readConfig();
        Boolean theAsteriskCheck = false;
        while (_input.hasNextLine()) {
            String firstInput = _input.nextLine();
            if (!firstInput.equals("") && firstInput.charAt(0) == '*') {
                setUp(temp, firstInput);
                theAsteriskCheck = true;
            } else if (theAsteriskCheck) {
                String msg = temp.convert(firstInput);
                printMessageLine(msg);
            } else {
                throw new EnigmaException("Error line 93 Main.java");
            }
        }
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    private Machine readConfig() {
        /* FIX ME - The item has been fixed. */
        try {
            _alphabet = new Alphabet(_config.nextLine());
            int numRotors = _config.nextInt();
            int pawls = _config.nextInt();
            Collection<Rotor> allRotors = new ArrayList<>();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
            }
            return new Machine(_alphabet, numRotors, pawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        /* FIX ME - The item has been fixed. */
        try {
            String name;
            String type;
            Rotor retRotor;
            if (_config.hasNext()) {
                name = _config.next();
                String temp = _config.next();
                String notch = "" + temp.substring(1);
                String matcher = "\\(.*\\)";
                String cycles = "";
                while (_config.hasNext(matcher)) {
                    cycles += _config.next();
                }
                Permutation thePermutation = new Permutation(cycles, _alphabet);
                if (temp.charAt(0) == 'M') {
                    retRotor = new MovingRotor(name, thePermutation, notch);
                    return retRotor;
                } else if (temp.charAt(0) == 'N') {
                    retRotor = new FixedRotor(name, thePermutation);
                    return retRotor;
                } else if (temp.charAt(0) == 'R') {
                    retRotor = new Reflector(name, thePermutation);
                    return retRotor;
                }
            }
            retRotor = new Rotor("ERTR", new Permutation("", new Alphabet("")));
            return retRotor;
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        /* FIX ME - The item has been fixed. */
        Scanner temp = new Scanner(settings);
        ArrayList<String> listRotors = new ArrayList<String>();
        temp.next();
        int surpassed = 0;
        while (temp.hasNext()) {
            if (surpassed == M.numRotors()) {
                break;
            }
            listRotors.add(temp.next());
            surpassed++;
        }

        String rotorSetting = listRotors.get(listRotors.size() - 1);
        if (surpassed == M.numRotors() && temp.hasNext()) {
            rotorSetting = temp.next();
        } else {
            listRotors.remove(listRotors.size() - 1);
        }
        String[] theRotors = new String[listRotors.size()];
        for (int x = 0; x < theRotors.length; x++) {
            theRotors[x] = listRotors.get(x);
        }

        if (rotorSetting.length() < M.numRotors() - 1) {
            throw new EnigmaException("Wheel setting too short");
        } else if (rotorSetting.length() > M.numRotors() - 1) {
            throw new EnigmaException("Wheel setting too long");
        }
        M.insertRotors(theRotors);
        if (!M.getMyRotor().get(0).reflecting()) {
            throw new EnigmaException("Reflector in wrong position.");
        }
        M.setRotors(rotorSetting);
        if (temp.hasNext()) {
            String plugBoard = temp.nextLine();
            plugBoard = plugBoard.substring(1);
            if (plugBoard.charAt(0) != '(') {
                for (int x = 1; x <= plugBoard.length(); x++) {
                    char settingChar = plugBoard.charAt(x - 1);
                    Rotor rotorToBeSet = M.getMyRotor().get(x);
                    rotorToBeSet.setRingSetting(_alphabet.toInt(settingChar));
                }
            } else {
                Permutation thePlug = new Permutation(plugBoard, _alphabet);
                M.setPlugboard(thePlug);
            }
        }
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        String temp = "";
        int fiveCounter = 0;
        for (int x = 0; x < msg.length(); x++) {
            if (fiveCounter == 5) {
                temp += " ";
                fiveCounter = 0;
            }
            temp += msg.charAt(x);
            fiveCounter++;
        }
        _output.println(temp);
    }

    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;

    /**
     * Source of input messages.
     */
    private Scanner _input;

    /**
     * Source of machine configuration.
     */
    private Scanner _config;

    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;
}
