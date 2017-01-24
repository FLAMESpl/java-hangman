package pl.wikihangman.web.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({
    HangmanTests.class,
    UserTests.class
})
public class TestSuite {

}
