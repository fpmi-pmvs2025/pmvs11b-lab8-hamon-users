package com.hamonusers.booklibrary;


import org.junit.*;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;


@RunWith(AndroidJUnit4.class)
public class BookDbHelperUnitTest {

    private BookDbHelper dbHelper;
    private BookInfo testBook;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new BookDbHelper(context);

        testBook = new BookInfo();
        testBook.setPreviewLink("test_preview_link");
        testBook.setTitle("Test Title");
        testBook.setSubtitle("Test Subtitle");
        testBook.setAuthors(new ArrayList<>(Arrays.asList("Author One", "Author Two")));
        testBook.setPublisher("Test Publisher");
        testBook.setPublishedDate("2023-01-01");
        testBook.setDescription("Test Description");
        testBook.setPageCount(123);
        testBook.setThumbnail("https://example.com/thumb.jpg");
        testBook.setInfoLink("https://example.com/info");
        testBook.setBuyLink("https://example.com/buy");

        dbHelper.deleteBook(testBook.getPreviewLink()); // clean up before
    }

    @After
    public void tearDown() {
        dbHelper.deleteBook(testBook.getPreviewLink()); // clean up after
        dbHelper.close();
    }

    @Test
    public void testAddBook_success() {
        boolean result = dbHelper.addBook(testBook);
        assertTrue("Book should be added successfully", result);
    }

    @Test
    public void testIsBookSaved_trueAfterAdd() {
        dbHelper.addBook(testBook);
        boolean saved = dbHelper.isBookSaved(testBook.getPreviewLink());
        assertTrue("Book should be marked as saved", saved);
    }

    @Test
    public void testIsBookSaved_falseIfNotAdded() {
        boolean saved = dbHelper.isBookSaved("non_existing_link");
        assertFalse("Book should not be marked as saved", saved);
    }

    @Test
    public void testGetAllBooks_containsAddedBook() {
        dbHelper.addBook(testBook);
        ArrayList<BookInfo> books = dbHelper.getAllBooks();
        boolean found = false;
        for (BookInfo b : books) {
            if (b.getPreviewLink().equals(testBook.getPreviewLink())) {
                found = true;
                break;
            }
        }
        assertTrue("Added book should be in the list", found);
    }

    @Test
    public void testDeleteBook_removesBook() {
        dbHelper.addBook(testBook);
        dbHelper.deleteBook(testBook.getPreviewLink());
        boolean exists = dbHelper.isBookSaved(testBook.getPreviewLink());
        assertFalse("Book should be deleted", exists);
    }

    @Test
    public void testAddDuplicateBook_returnsFalse() {
        dbHelper.addBook(testBook);
        boolean secondAttempt = dbHelper.addBook(testBook);
        assertFalse("Duplicate add should return false", secondAttempt);
    }
}