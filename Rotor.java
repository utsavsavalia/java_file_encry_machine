package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        /* FIX ME - The item has been fixed. */
        _name = name;
        _permutation = perm;
        setting = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        /* FIX ME - The item has been fixed. */
        return setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        /* FIX ME - The item has been fixed. */
        setting  = permutation().wrap(posn);
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        /* FIX ME - The item has been fixed. */
        int index = _permutation.alphabet().toInt(cposn);
        setting = index;
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        /* FIX ME - The item has been fixed. */
        int wrap = permutation().wrap(p + setting() - ringSetting);
        int temp = permutation().permute(wrap);
        int returnValue = permutation().wrap(temp - setting() + ringSetting);
        return returnValue;
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        /* FIX ME - The item has been fixed. */
        int wrap = permutation().wrap(e + setting() - ringSetting);
        int temp = permutation().invert(wrap);
        int returnValue = permutation().wrap(temp - setting() + ringSetting);
        return returnValue;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /**@returns setting. Getter for private var setting. */
    public int getSetting() {
        return setting;
    }

    /**@param theSetting Setter for private var setting. */
    public void setSetting(int theSetting) {
        setting = theSetting;
    }

    /**@param theRingSetting Setter for private var ringSetting. */
    public void setRingSetting(int theRingSetting) {
        ringSetting = theRingSetting;
    }

    /**@returns setting. Getter for private var getRingSetting. */
    public int getRingSetting() {
        return ringSetting;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** private var setting. */
    private int setting;

    /** private var ringSetting. */
    private int ringSetting = 0;

}
