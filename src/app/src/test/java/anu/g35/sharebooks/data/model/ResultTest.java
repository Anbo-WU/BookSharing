package anu.g35.sharebooks.data.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import anu.g35.sharebooks.exceptions.UserNotFoundException;


/**
 * Test of the Result class
 *
 * @author u7703248 Chuang Ma
 * @since 2024-05-9
 */
public class ResultTest {

    @Test
    public void testNewResult() {

        Result<String> resultSuccess =  new  Result.Success<>("Success");
        Result<String> resultError =  new Result.Error<>(new UserNotFoundException("Error"));

        assertEquals("Success[data=Success]", resultSuccess.toString());
        assertEquals("Error[exception=anu.g35.sharebooks.exceptions.UserNotFoundException: Error]",
                resultError.toString());


    }

}
