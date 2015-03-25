package com.rodrigosaito.mockwebserver.player;

import java.util.List;

public class Tape {

    private String name;
    private List<Interaction> interactions;
    private int delay;

    public Tape() {}

    public Tape(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(final List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public boolean haRequestMatching() {
        for (Interaction interaction : getInteractions()) {
            if (interaction.getRequest() != null) {
                return true;
            }
        }

        return false;
    }

    public void setDelay(final int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}
