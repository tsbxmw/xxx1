package AndroidTest;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.app_test.AddFriend;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;

/**
 * Created by 10192582 on 2016/11/17.
 */
public class AddFriendTest {
    @Rule
    public ActivityTestRule<AddFriend> activityTestRule = new ActivityTestRule<AddFriend>(AddFriend.class);
    @Before
    public void setUp() throws Exception {
        Thread.sleep(10000);
        closeSoftKeyboard();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void ADD_FRIEND() throws Exception {
        Activity activity =activityTestRule.getActivity();
        Intent intent = new Intent();
        activityTestRule.launchActivity(intent);
    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void back() throws Exception {

    }

}