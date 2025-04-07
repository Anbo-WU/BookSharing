package anu.g35.sharebooks.data.simulate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import anu.g35.sharebooks.data.model.UserAction;

/**
 * Test of the SimulateSubject class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
@RunWith(RobolectricTestRunner.class)
public class SimulateSubjectTest {

    private SimulateSubject simulateSubject;
    @Before
    public void setUp() {
        simulateSubject = SimulateSubject.getInstance();
    }


    @Test
    public void testObserver() {

        Observer observer = new Observer() {
            @Override
            public void receiveAction(UserAction action, Subject from) {
                assertEquals(true, action != null);

            }
        };

        simulateSubject.registerObserver(observer);
        simulateSubject.notifyObservers(new UserAction());
        simulateSubject.removeObserver(observer);
    }


}
