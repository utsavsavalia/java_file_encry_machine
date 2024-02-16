package enigma;
import java.util.ArrayList;
import java.util.List;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Utsav Savalia - Skeleton provided CS61B staff, edited by student.
 */
class Alphabet {

    /** A new alphabet containing CHARS. The K-th character has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        for (int x = 0; x < chars.length(); x++) {
            String temp = "" + chars.charAt(x);
            if (!myAlphabet.contains(temp)) {
                myAlphabet.add(temp);
            }
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        /* changed the FIX ME*/
        return myAlphabet.size();
    }

    /** Returns true if CH is in this alphabet. */
    boolean contains(char ch) {
        /* changed the FIX ME*/
        return myAlphabet.contains(("" + ch));
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        /* changed the FIX ME*/
        String returnStringVal = myAlphabet.get(index);
        return returnStringVal.charAt(0);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        /* changed the FIX ME*/
        return myAlphabet.indexOf(("" + ch));
    }


    /**@return myAlphanet. Getter method for myAlphabet.*/
    public List<String> getMyAlphabet() {
        return myAlphabet;
    }
    /**Additional Data Structure to store the Alphabet.*/
    private List<String> myAlphabet = new ArrayList<String>();
}
