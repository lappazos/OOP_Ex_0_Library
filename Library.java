/**
 * @author lioraryepaz
 * This class represents a Library, which has various limitations, and foolow up on its books and patrons.
 */
class Library {

    /**
     * The maximal number of books this library can hold
     */
    final int maxBookCapacity;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time
     */
    int maxBorrowedBooks;

    /**
     * The maximal number of registered patrons this library can handle
     */
    int maxPatronCapacity;

    /**
     * Books array - index is bookid
     */
    Book[] librarybooksArray;

    /**
     * Patrons array - index is patronid
     */
    Patron[] librarypatronsArray;

    /**
     * follow up on how much books each patron has according to index
     */
    int[] borrowedbyPatrons;

    /**
     * a counter of books registered to the library
     */
    int booksInlibrary = 0;

    /**
     * a counter of patrons registered to the library
     */
    int patronsInlibrary = 0;

    Library(int libraryMaxBookCapacity, int libraryMaxBorrowedBooks, int libraryMaxPatronCapacity) {
        maxBookCapacity = libraryMaxBookCapacity;
        maxBorrowedBooks = libraryMaxBorrowedBooks;
        maxPatronCapacity = libraryMaxPatronCapacity;
        librarybooksArray = new Book[maxBookCapacity];
        librarypatronsArray = new Patron[maxPatronCapacity];
        borrowedbyPatrons = new int[maxPatronCapacity];
    }

    /**
     * @return a non-negative id number for the book if there was a spot and the book was successfully added, or if the
     * book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i < librarybooksArray.length; i++) {
            if (librarybooksArray[i] == book) {
                return i;
            }
        }
        if (booksInlibrary < maxBookCapacity) {
            librarybooksArray[booksInlibrary] = book;
            booksInlibrary += 1;
            return booksInlibrary - 1;
        } else {
            return -1;
        }
    }

    boolean isBookIdValid(int bookId) {
        if (bookId > maxBookCapacity) {
            return false;
        } else {
            return (librarybooksArray[bookId] != null);
        }
    }

    int getBookId(Book book) {
        for (int i = 0; i < librarybooksArray.length; i++) {
            if (librarybooksArray[i] == book) {
                return i;
            }
        }
        return -1;
    }

    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId)) {
            return (librarybooksArray[bookId].getCurrentBorrowerId() == -1);
        } else {
            return false;
        }
    }

    /**
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully registered,
     * a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < librarypatronsArray.length; i++) {
            if (librarypatronsArray[i] == patron) {
                return i;
            }
        }
        if (patronsInlibrary < maxPatronCapacity) {
            librarypatronsArray[patronsInlibrary] = patron;
            patronsInlibrary += 1;
            return patronsInlibrary - 1;
        } else {
            return -1;
        }
    }

    boolean isPatronIdValid(int patronId) {
        if (patronId > maxPatronCapacity) {
            return false;
        } else {
            return (librarypatronsArray[patronId] != null);
        }
    }

    int getPatronId(Patron patron) {
        for (int i = 0; i < librarypatronsArray.length; i++) {
            if (librarypatronsArray[i] == patron) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this book is
     * available, the given patron isn't already borrowing the maximal number of books allowed, and if the patron will
     * enjoy this book.
     *
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if ((isBookIdValid(bookId)) && (isPatronIdValid(patronId))) {
            Book tempBook = librarybooksArray[bookId];
            Patron tempPatron = librarypatronsArray[patronId];
            if (tempBook.getCurrentBorrowerId() != -1) {
                return false;
            } else {
                if (borrowedbyPatrons[patronId] >= maxBorrowedBooks) {
                    return false;
                } else {
                    if (tempPatron.willEnjoyBook(librarybooksArray[bookId])) {
                        tempBook.setBorrowerId(patronId);
                        borrowedbyPatrons[patronId] += 1;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else return false;
    }

    void returnBook(int bookId) {
        if (!isBookIdValid(bookId)) {
            return;
        }
        Book tempBook = librarybooksArray[bookId];
        int tempPatronid = tempBook.getCurrentBorrowerId();
        borrowedbyPatrons[tempPatronid] -= 1;
        tempBook.setBorrowerId(-1);
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he will enjoy,
     * if any such exist.
     *
     * @param patronId
     * @return The available book the patron with the given will enjoy the most. Null if no book is available
     */
    Book suggestBookToPatron(int patronId) {
        if (!isPatronIdValid(patronId)) {
            return null;
        }
        Patron tempPatron = librarypatronsArray[patronId];
        int[] suggestionList = new int[maxBookCapacity];
        Book suggestBook = null;
        int answer = 0;
        for (Book tempBook : librarybooksArray) {
            if ((tempBook.getCurrentBorrowerId() == -1) && (tempPatron.willEnjoyBook(tempBook))) {
                int tempScore = tempPatron.getBookScore(tempBook);
                if (tempScore > answer) {
                    answer = tempScore;
                    suggestBook = tempBook;
                }
            }
        }
        return suggestBook;
    }
}
