/**
 * @author lioraryepaz
 * This class represents a Patron, which has a first name, last name and different literary prefrences.
 */
class Patron {

    final String firstName;

    final String lastName;

    int comicTendency;

    int dramaticTendency;

    int educationalTendency;

    /**
     * The minimal literary value a book must have for this patron to enjoy it
     */
    int enjoymentThreshold;

    Patron(String patronFirstName, String patronLastName, int patronComicTendency, int patronDramaticTendency,
           int patronEducationalTendency, int patronEnjoymentThreshold) {
        firstName = patronFirstName;
        lastName = patronLastName;
        comicTendency = patronComicTendency;
        dramaticTendency = patronDramaticTendency;
        educationalTendency = patronEducationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * @return Returns the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        return book.getComicValue() * comicTendency + book.getDramaticValue() * dramaticTendency +
                book.getEducationalValue() * educationalTendency;
    }

    /**
     * will enjoy a given book according to his threshold
     */
    boolean willEnjoyBook(Book book) {
        return (getBookScore(book) > enjoymentThreshold);
    }


}
