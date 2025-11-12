package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import it.unibo.deathnote.api.DeathNote;

/** 
 * Implemetation of deth note
 */
public class DeathNoteImpl implements DeathNote {

    private Map<String, InnerDeathNoteImpl> deathNote;
    private String lastNameWritten;

    public DeathNoteImpl() {
        deathNote = new HashMap<>();
    }

    @Override
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalStateException("The name not written on notebook");
        } 
        return deathNote.get(name).getDeathCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalStateException("The name not written on notebook");
        }
        return deathNote.get(name).getDetailsOfDeath();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Index out of bound");
        }
        return DeathNote.RULES.get(ruleNumber);
    }

    @Override
    public boolean isNameWritten(final String name) {
        Objects.requireNonNull(name);
        return deathNote.containsKey(name);
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (deathNote.isEmpty()) {
            throw new IllegalStateException("None name is wrtitten on notebook");
        } else if (cause == null) {
            throw new IllegalStateException("The cause can't be null");
        }
        InnerDeathNoteImpl newDeathInfo = deathNote.get(lastNameWritten).writeDeathCause(cause);
        if (!deathNote.get(lastNameWritten).equals(newDeathInfo)) {
            deathNote.put(lastNameWritten, newDeathInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if (deathNote.isEmpty()) {
            throw new IllegalStateException("None name is wrtitten on notebook");
        } else if (details == null) {
            throw new IllegalStateException("The details can't be null");
        }
        InnerDeathNoteImpl newDeathInfo = deathNote.get(lastNameWritten).writeDeathDetails(details);
        if (!deathNote.get(lastNameWritten).equals(newDeathInfo)) {
            deathNote.put(lastNameWritten, newDeathInfo);
            return true;
        }
        return false;
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            return;
        }
        lastNameWritten = name;
        deathNote.put(name, new InnerDeathNoteImpl());
    }

private static class InnerDeathNoteImpl {
    
        static final int INTERVAL_OF_DEATH = 40;
        static final long INTERVAL_FOR_DETAILS = TimeUnit.SECONDS.toMillis(6) + INTERVAL_OF_DEATH;

        private final long timeOfDeath;
        private final String causeOfDeath;
        private final String detailsOfDeath;

        private InnerDeathNoteImpl(final String cause, final String details) {
            this.causeOfDeath = cause;
            this.detailsOfDeath = details;
            timeOfDeath = System.currentTimeMillis();
        }

        InnerDeathNoteImpl() {
            this("Heart attack", ""); //Deafault case
        }

        public InnerDeathNoteImpl writeDeathCause(final String cause) {    
            return System.currentTimeMillis() - timeOfDeath <=  INTERVAL_OF_DEATH 
                ? new InnerDeathNoteImpl(cause, this.detailsOfDeath) 
                : this;
        }

        public InnerDeathNoteImpl writeDeathDetails(final String deatails) {
            
            return System.currentTimeMillis() - timeOfDeath <= INTERVAL_FOR_DETAILS 
                ? new InnerDeathNoteImpl(this.causeOfDeath, deatails) 
                : this;
        }

        public String getDeathCause() {
            return this.causeOfDeath;
        }

        public String getDetailsOfDeath() {
            return this.detailsOfDeath;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((causeOfDeath == null) ? 0 : causeOfDeath.hashCode());
            result = prime * result + ((detailsOfDeath == null) ? 0 : detailsOfDeath.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final InnerDeathNoteImpl other = (InnerDeathNoteImpl) obj;
            if (causeOfDeath == null) {
                if (other.causeOfDeath != null) {
                    return false;
                }
            } else if (!causeOfDeath.equals(other.causeOfDeath)) {
                return false;
            }
            if (detailsOfDeath == null) {
                if (other.detailsOfDeath != null) {
                    return false;
                }
            } else if (!detailsOfDeath.equals(other.detailsOfDeath)) {
                return false;
            }
            return true;
        }
    } 
}
