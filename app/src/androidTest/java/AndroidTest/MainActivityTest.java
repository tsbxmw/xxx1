package AndroidTest;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.example.app_test.MainActivity;
import com.example.app_test.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by 10192582 on 2016/11/17.
 */

public class MainActivityTest {
    @org.junit.Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @org.junit.Before
    public void setUp() throws Exception {
        Thread.sleep(1000);
        closeSoftKeyboard();

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void onCreate() throws Exception {

    }

    @org.junit.Test
    public void loginNow() throws Exception {
        Activity activity = mainActivityActivityTestRule.getActivity();
        Matcher user = allOf(withId(R.id.user),withHint("User-Email-Tel-Num"));
        onView(user).perform(clearText());
        onView(user).perform(typeText("123"));
        onView(user).check(matches(withText("123")));
        closeSoftKeyboard();
        Matcher pass = allOf(withId(R.id.pass),withHint("Password"));
        onView(pass).perform(clearText());
        onView(pass).perform(typeText("123"));
        closeSoftKeyboard();
        //onView(pass).check(matches(withText("..3")));
        //can not check
        Matcher rem = withId(R.id.remember);
        onView(rem).check(matches(isNotChecked()));
        onView(rem).perform(click());
        onView(rem).check(matches(isChecked()));
        onView(rem).perform(click());
        onView(rem).check(matches(isNotChecked()));

        Matcher autologin = withId(R.id.autologin);
        onView(autologin).check(matches(isNotChecked()));
        onView(autologin).perform(click());
        onView(autologin).check(matches(isChecked()));
        onView(autologin).perform(click());
        onView(autologin).check(matches(isNotChecked()));


        Matcher login = withId(R.id.button_login);
        onView(login).perform(click());

        Thread.sleep(10000);


    }

    @org.junit.Test
    public void registerNow() throws Exception {

    }

    @org.junit.Test
    public void back() throws Exception {
        Matcher back = withId(R.id.button_back);
        closeSoftKeyboard();
        onView(back).check(matches(isClickable()));
        onView(back).check(matches(isDisplayed()));

    }
}