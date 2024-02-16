package enigma;

import java.util.ArrayList;
import java.util.Collection;

import static enigma.EnigmaException.*;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
class Machine {

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        /* changed the FIX ME*/
        _alphabet = alpha;
        myNumRotors = numRotors;
        myPawls = pawls;
        myAllRotors = new ArrayList<Rotor>(allRotors);
    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        /* changed the FIX ME*/
        return myNumRotors;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        /* changed the FIX ME*/
        return myPawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        myRotor = new ArrayList<Rotor>();
        boolean found = false;
        for (int x = 0; x < rotors.length; x++) {
            found = false;
            for (int y = 0; y < myAllRotors.size(); y++) {
                if (myAllRotors.get(y).name().equals(rotors[x])) {
                    Rotor r = myAllRotors.get(y);
                    if (myRotor.contains(r)) {
                        throw new EnigmaException("Duplicate Rotor");
                    }
                    myRotor.add(r);
                    found = true;
                }
            }
            if (!found) {
                throw new EnigmaException("Bad Rotor name");
            }
        }
        int countMovingRotors = 0;
        for (int x = 0; x < myRotor.size(); x++) {
            if (myRotor.get(x).rotates()) {
                countMovingRotors++;
            }
        }
        if (countMovingRotors != numPawls()) {
            throw new EnigmaException("Wrong number of Arguments");
        }
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 characters in my alphabet. The first letter refers
     * to the leftmost rotor setting (not counting the reflector).
     */
    void setRotors(String setting) {
        /* changed the FIX ME*/
        if (setting.length() != numRotors() - 1) {
            throw new EnigmaException("Setting size != the valid rotor count");
        }
        for (int x = 0; x < myRotor.size() - 1; x++) {
            if (_alphabet.contains(setting.charAt(x))) {
                myRotor.get(x + 1).set(setting.charAt(x));
            } else {
                throw new EnigmaException("Bad character in Wheel Setting");
            }
        }
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugBoard) {
        /* changed the FIX ME*/
        this.plugboard = plugBoard;
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * the machine.
     */
    int convert(int c) {
        boolean[] notchArray = new boolean[myRotor.size()];
        for (int x = 0; x < numRotors() - 1; x++) {
            if (myRotor.get(x + 1).atNotch()) {
                notchArray[x] = true;
            }
            if (myRotor.get(x).atNotch() && myRotor.get(x - 1).rotates()) {
                notchArray[x] = true;
            }
        }
        myRotor.get(myRotor.size() - 1).advance();
        for (int y = myRotor.size() - 2; y > 0; y--) {
            if (notchArray[y]) {
                myRotor.get(y).advance();
            }
        }

        int intVersion = c;
        char theValueOfIntVersionForward = _alphabet.toChar(intVersion);
        if (plugboard != null) {
            intVersion = plugboard.permute(c);
        }
        for (int x = myRotor.size() - 1; x >= 0; x--) {
            intVersion = myRotor.get(x).convertForward(intVersion);
            theValueOfIntVersionForward = _alphabet.toChar(intVersion);
        }
        char theValueofIntVersionBackward = _alphabet.toChar(intVersion);
        for (int y = 1; y < myRotor.size(); y++) {
            intVersion = myRotor.get(y).convertBackward(intVersion);
            theValueofIntVersionBackward = _alphabet.toChar(intVersion);
        }

        if (plugboard != null) {
            intVersion = plugboard.invert(intVersion);
        }
        return intVersion;
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        /* changed the FIX ME*/
        String retValue = "";
        for (int x = 0; x < msg.length(); x++) {
            if (msg.charAt(x) == ' ') {
                x = x;
            } else if (msg.charAt(x) != ' ') {
                int intValue = _alphabet.toInt(msg.charAt(x));
                int convertedIndex = convert(intValue);
                retValue += _alphabet.toChar(convertedIndex);
            }
        }
        return retValue;
    }

    /** @return - myRotor. getter method private var myRotor. */
    public ArrayList<Rotor> getMyRotor() {
        return myRotor;
    }

    /** @return - myAllRotor. getter method private var myAllRotor. */
    public ArrayList<Rotor> getMyAllRotors() {
        return myAllRotors;
    }

    /**
     * @return myPlugboard - Getter plugboard method.
     */
    public Permutation getPlugboard() {
        return plugboard;
    }

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;

    /**
     * Instance var numRotor.
     */
    private int myNumRotors;

    /**
     * Instance var numPawls.
     */
    private int myPawls;

    /**
     * Instance var myAllRotors.
     */
    private ArrayList<Rotor> myAllRotors;

    /**
     * Instance var myRotor.
     */
    private ArrayList<Rotor> myRotor = new ArrayList<Rotor>();

    /**
     * Instance var plugboard.
     */
    private Permutation plugboard;
    /* changed the FIX ME*/
}
