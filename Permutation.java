package enigma;

import java.util.ArrayList;
import java.util.List;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        myCycles = cycles;
        setArrayListCycles();
    }

    /** Method to implement Data Structure to store Cycles. */
    private void setArrayListCycles() {
        String addValue = "";
        for (int x = 0; x < myCycles.length(); x++) {
            if (myCycles.charAt(x) == '(' || myCycles.charAt(x) == ' ') {
                x = x;
            } else if (myCycles.charAt(x) == ')') {
                cycleList.add(addValue);
                addValue = "";
            } else {
                addValue += myCycles.charAt(x);
            }
        }
    }
    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        cycleList.add(cycle);
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        /* FIX ME - The item has been fixed. */
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        /* FIX ME - The item has been fixed. */
        p = wrap(p);
        char firstLetter = _alphabet.toChar(p);
        char returnChar = ' ';
        for (int x = 0; x < cycleList.size(); x++) {
            if (cycleList.get(x).indexOf(firstLetter) >= 0) {
                int index = cycleList.get(x).indexOf(firstLetter);
                if (index == cycleList.get(x).length() - 1) {
                    String temp = cycleList.get(x);
                    return _alphabet.toInt(temp.charAt(0));
                } else {
                    return _alphabet.toInt(cycleList.get(x).charAt(index + 1));
                }
            }
        }
        return p;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        /* FIX ME - The item has been fixed. */
        c = wrap(c);
        char firstLetter = _alphabet.toChar(c);
        char returnChar = ' ';
        for (int x = 0; x < cycleList.size(); x++) {
            if (cycleList.get(x).indexOf(firstLetter) >= 0) {
                int index = cycleList.get(x).indexOf(firstLetter);
                if (index - 1 < 0) {
                    int lastIndex = cycleList.get(x).length() - 1;
                    char theCharAtEnd = cycleList.get(x).charAt(lastIndex);
                    return _alphabet.toInt(theCharAtEnd);
                } else {
                    return _alphabet.toInt(cycleList.get(x).charAt(index - 1));
                }
            }
        }
        return c;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        /* FIX ME - The item has been fixed. */
        int indexOfWord = _alphabet.toInt(p);
        if (indexOfWord == -1) {
            throw new EnigmaException("Character not in alphabet");
        }
        int returnedIndex = permute(indexOfWord);
        char returnChar = _alphabet.toChar(returnedIndex);
        return returnChar;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        int indexOfWord = _alphabet.toInt(c);
        if (indexOfWord == -1) {
            throw new EnigmaException("Character not in alphabet");
        }
        int returnedIndex = invert(indexOfWord);
        char returnChar = _alphabet.toChar(returnedIndex);
        return returnChar;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        /* FIX ME - The item has been fixed. */
        return true;
    }

    /**@returns - cycleList. Getter function for cycleList. */
    public List<String> getCycleList() {
        return cycleList;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** private var myCycles. */
    private String myCycles;

    /** private var cycleList. */
    private List<String> cycleList = new ArrayList<String>();
    /* FIX ME - The item has been fixed. */
}
