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

    private DeathNote deathNote;

    @BeforeEach
    void setUp() {
        deathNote = new DeathNoteImpl();
    }

    @Test
    void testGetterRules() {

        for (final var index : List.of(-1, 0, deathNote.RULES.size() + 1)) {
            try { 
                deathNote.getRule(index);
                Assertions.fail("Return a rule that doesn't exsit");
            } catch (final IllegalArgumentException e) {
                assertNotNull(e.getMessage()); // Non-null message
                assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            }
        }
    }

    @Test
    void testExistenceRules() {

        for (final String rule : deathNote.RULES) {

            assertNotNull(rule); // the rule doesn't null
            assertFalse(rule.isBlank()); // the rule doesn't empty 
        }
    }

    @Test
    void testWritingName() {
        assertFalse(deathNote.isNameWritten(LIGHT_YAGAMI));
        deathNote.writeName(LIGHT_YAGAMI);
        assumeTrue(deathNote.isNameWritten(LIGHT_YAGAMI));
        assertFalse(deathNote.isNameWritten(THANOS));
        deathNote.writeName("");
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    void testDeathCause() {
        final long sleepTime = 100;
        final String changedCause = "car incident";

        assertThrows(IllegalStateException.class, new Executable() {
           @Override
           public void execute() throws Throwable {
                deathNote.writeDeathCause(LIGHT_YAGAMI);
           } 
        });
        deathNote.writeName(LIGHT_YAGAMI);
        assertEquals("Heart attack", deathNote.getDeathCause(LIGHT_YAGAMI));
        deathNote.writeName(THANOS);
        assertTrue(deathNote.writeDeathCause(DEATH_FOR_KARTING));
        assertEquals(DEATH_FOR_KARTING, deathNote.getDeathCause(THANOS));

        try {
            Thread.sleep(sleepTime); 
        } catch (final InterruptedException e) {
            Assertions.fail("The proces is arrested");
        }
        assertFalse(deathNote.writeDeathCause(changedCause));
        assertNotEquals(changedCause, deathNote.getDeathCause(THANOS));
    }

    @Test
    void testDetailsDeath() {
        final long sleepTime = 6100;
        final String changeDetails = "details that do not write it";

        assertThrows(IllegalStateException.class, new Executable() {
           @Override
           public void execute() throws Throwable {
                deathNote.writeDeathCause(LIGHT_YAGAMI);
           } 
        });
        deathNote.writeName(LIGHT_YAGAMI);
        assertTrue(deathNote.getDeathDetails(LIGHT_YAGAMI).isEmpty());
        assertTrue(deathNote.writeDetails(DETAILS_DEATH));

        deathNote.writeName(THANOS);
        try {
            Thread.sleep(sleepTime); 
        } catch (final InterruptedException e) {
            Assertions.fail("The proces is arrested");
        }
        assertFalse(deathNote.writeDetails(changeDetails));
    }
}
