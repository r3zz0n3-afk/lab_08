package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    
    private static final String LIGHT_YAGAMI = "Light Yagami";
    private static final String THANOS = "Thanos";
    private static final String DEATH_FOR_KARTING = "karting accident";
    private static final String DETAILS_DEATH = "ran for too long";

    DeathNote testDeathNote;

    @BeforeEach
    void setUp() {
        testDeathNote = new DeathNoteImpl();
    }

    @Test
    void testGetterRules() {

        for (var index : List.of(-1 , 0 , DeathNote.RULES.size() + 1)) {
            try { 
                testDeathNote.getRule(index);
                Assertions.fail("Return a rule that doesn't exsit");
            } catch (IllegalArgumentException e) {
                assertNotNull(e.getMessage()); // Non-null message
                assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            }
        }
    }

    @Test
    void testExistenceRules() {

        for (String rule : DeathNote.RULES) {

            assertNotNull(rule); // the rule doesn't null
            assertFalse(rule.isBlank()); // the rule doesn't empty 
        }
    }

    @Test
    void testWritingName() {
        assertFalse(testDeathNote.isNameWritten(LIGHT_YAGAMI));
        testDeathNote.writeName(LIGHT_YAGAMI);
        assumeTrue(testDeathNote.isNameWritten(LIGHT_YAGAMI));
        assertFalse(testDeathNote.isNameWritten(THANOS));
        testDeathNote.writeName("");
        assertFalse(testDeathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause() {
        final long sleepTime = 100;
        final String changedCause = "car incident";

        assertThrows(IllegalStateException.class, new Executable() {
           @Override
           public void execute() throws Throwable {
                testDeathNote.writeDeathCause(LIGHT_YAGAMI);
           } 
        });
        testDeathNote.writeName(LIGHT_YAGAMI);
        assertEquals("Heart attack", testDeathNote.getDeathCause(LIGHT_YAGAMI));
        testDeathNote.writeName(THANOS);
        assertTrue(testDeathNote.writeDeathCause(DEATH_FOR_KARTING));
        assertEquals(DEATH_FOR_KARTING, testDeathNote.getDeathCause(THANOS));
        
        try {
            Thread.sleep(sleepTime); 
        } catch (InterruptedException e) {}
        assertFalse(testDeathNote.writeDeathCause(changedCause));
        assertNotEquals(changedCause, testDeathNote.getDeathCause(THANOS));
    }

    @Test
    void testDetailsDeath() {
        final long sleepTime = 6100;
        final String changeDetails = "details that do not write it";

        assertThrows(IllegalStateException.class, new Executable() {
           @Override
           public void execute() throws Throwable {
                testDeathNote.writeDeathCause(LIGHT_YAGAMI);
           } 
        });
        testDeathNote.writeName(LIGHT_YAGAMI);
        assertTrue(testDeathNote.getDeathDetails(LIGHT_YAGAMI).isEmpty());
        assertTrue(testDeathNote.writeDetails(DETAILS_DEATH));

        testDeathNote.writeName(THANOS);
        try {
            Thread.sleep(sleepTime); 
        } catch (InterruptedException e) {}
        assertFalse(testDeathNote.writeDetails(changeDetails));
    }
}