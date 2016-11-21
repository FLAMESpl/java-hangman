package pl.wikihangman.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Łukasz Szafirski
 * @version 1.0.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GameServiceTests.class,
    HangmanTests.class,
    UserTests.class,
    AccountServiceTests.class})

public class TestSuite {
}
