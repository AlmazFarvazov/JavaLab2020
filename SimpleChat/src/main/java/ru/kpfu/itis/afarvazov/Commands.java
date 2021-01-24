package ru.kpfu.itis.afarvazov;

public enum Commands {

    EXIT ("Leaving form chat...");

    private String name;

    Commands(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
