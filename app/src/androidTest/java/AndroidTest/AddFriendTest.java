package AndroidTest;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.example.app_test.AddFriend;
import com.example.app_test.MainActivity;
import com.example.app_test.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by 10192582 on 2016/11/17.
 */
public class AddFriendTest {
    @Rule
    //public ActivityTestRule<AddFriend> activityTestRule = new ActivityTestRule<AddFriend>(AddFriend.class);
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    @Before
    public void setUp() throws Exception {
        Thread.sleep(1000);
        closeSoftKeyboard();
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
        System.out.print("LOADING ");

        Matcher login = withId(R.id.button_login);
        onView(login).perform(click());

        Thread.sleep(5000);



    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void ADD_FRIEND() throws Exception {
       // Activity activity =activityTestRule.getActivity();

        ActivityTestRule<AddFriend> activityTestRule = new ActivityTestRule<AddFriend>(AddFriend.class);
        Activity activity =activityTestRule.getActivity();
        Matcher user = withId(R.id.add_friend);
        onView(user).perform(click());
    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void back() throws Exception {

    }

}