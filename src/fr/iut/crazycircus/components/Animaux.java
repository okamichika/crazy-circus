package fr.iut.crazycircus.components;
//public enum Animaux {
//    ELEPHANT, LION, OURS
//}
/*
class Ours {
    String name = "Ours";
    int ID = 1;
}

class Lion {
    String name = "Lion";
    int ID = 2;
}
class Elephant {
    String name = "Elephant";
    int ID = 3;
}
*/

public enum Animaux {
    ELEPHANT(0), LION(1), OURS(2);

    private final int id;

    Animaux(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /*public static int getIdFromName(String name) {
        switch (name) {
            case "ELEPHANT":
                return ELEPHANT.getId();
            case "LION":
                return LION.getId();
            case "OURS":
                return OURS.getId();
            default:
                throw new IllegalArgumentException("Nom d'animal invalide : " + name);
        }
    }*/
}

