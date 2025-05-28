package com.hamonusers.booklibrary;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MainActivityUiTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testViewsAreVisible() {
        onView(withId(R.id.searchEditText)).check(matches(isDisplayed()));
        onView(withId(R.id.searchEditText)).check(matches(withHint("Search books...")));

        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.savedBooksButton)).check(matches(isDisplayed()));

        onView(withId(R.id.rv)).check(matches(isDisplayed()));

        // Проверка, что ProgressBar изначально скрыт
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    @Test
    public void testSearchInputAndButtonClick() throws InterruptedException {
        // Ввод текста в поле поиска
        onView(withId(R.id.searchEditText))
                .perform(replaceText("Harry Potter"));

        // Клик по кнопке поиска
        onView(withId(R.id.searchButton))
                .perform(click());

        Thread.sleep(5000);

        // Проверяем, что RecyclerView обновился и содержит хотя бы один элемент
        onView(withId(R.id.rv))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSavedBooksButtonClick() {
        // Клик по кнопке "Saved Books"
        onView(withId(R.id.savedBooksButton))
                .perform(click());

        // Проверяем, что RecyclerView отображается (предполагается, что он показывает сохранённые книги)
        onView(withId(R.id.rv2)).check(matches(isDisplayed()));

    }
}
