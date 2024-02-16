package enigma;

import static enigma.EnigmaException.*;

/**
 * Class that represents a rotating rotor in the enigma machine.
 *
 * @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        myNotches = notches;
        setSetting(0);
    }

    @Override
    boolean rotates() {
        return rotates;
    }
    /* FIX ME - The item has been fixed. */

    @Override
    void advance() {
        setSetting(permutation().wrap(getSetting() + 1));
    }

    @Override
    boolean atNotch() {
        int setting = getSetting();
        String theNotchChar = "" + permutation().alphabet().toChar(setting);
        if (myNotches.indexOf(theNotchChar) >= 0) {
            return true;
        }
        return false;
    }

    /**@return - myNotches. Getter method for myNotches. */
    public String getMyNotches() {
        return myNotches;
    }

    /** instance var myNotches. */
    private String myNotches;

    /** instance var to check if it rotates. */
    private boolean rotates = true;
    /* FIX ME - The item has been fixed. */

}
