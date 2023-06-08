package fr.iut.crazycircus;

import fr.iut.crazycircus.components.*;

import java.util.*;

public class Main {

    static String ASCII_RESET = "\u001B[0m";
    static String ASCII_RED = "\u001B[31m";
    static String ASCII_BLUE = "\u001B[34m";
    static String ASCII_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        int numberPlayer = 0;

        //Génération des podiums
        Podium[] currentPodiums = new Podium[2];
        currentPodiums[0] = new Podium("BLEU", ASCII_BLUE);
        currentPodiums[1] = new Podium("ROUGE", ASCII_RED);

        Podium[] finalPodiums = new Podium[2];
        finalPodiums[0] = new Podium("BLEU", ASCII_BLUE);
        finalPodiums[1] = new Podium("ROUGE", ASCII_RED);

        LinkedList<Podium[]> possibilities = genererPossibilites();
        currentPodiums = possibilities.getFirst();
        possibilities.addLast(possibilities.removeFirst());
        finalPodiums = possibilities.removeFirst();



        //Récupération du nombre de joueur
        while (true) {
            System.out.print("Veuillez saisir le nombre de joueurs : ");
            Scanner scan = new Scanner(System.in);
            if (scan.hasNextInt()) {
                numberPlayer = scan.nextInt();
                if(numberPlayer > 1){
                    break;
                }
            } else {
                // si l'entrée n'est pas un entier, on vide le scanner et on redemande
                scan.nextLine();
                System.out.println(ASCII_RED + "Vous devez renseigner un chiffre."+ASCII_RESET);
            }
        }

        List<Player> players = new ArrayList<>();

        //Créations des joueurs
        for (int i=0; i< numberPlayer; i++) {

            String playerName = null;

            while (true) {
                System.out.print("Joueur n°" + (i+1) + ", comment t'appelles tu ? : ");
                Scanner scan = new Scanner(System.in);
                if (scan.hasNext()) {
                    playerName = scan.next();
                    break;
                } else {
                    scan.nextLine();
                    System.out.println("Vous devez renseigner votre pseudo."+ASCII_RESET);
                }
            }

            players.add(new Player(playerName));
            System.out.println("Bonjour, Joueur " + (i + 1)  + " : " + players.get(players.size()-1).getName() + " !");
        }

        //Début du jeu
        while(!possibilities.isEmpty()) {
            manche(players, currentPodiums, finalPodiums);
            currentPodiums = finalPodiums;
            finalPodiums = possibilities.removeFirst();

            for(Player p : players){
                p.setCanPlay(true);
            }

            if(possibilities.size() != 0){
                System.out.println(ASCII_BLUE + "Manche suivante ! (encore " + possibilities.size() + " manche" + (possibilities.size() > 1 ? "s": "") + ")" + ASCII_RESET);
            }
        }

        Player winner = players.get(0);
        for(Player p : players){
            System.out.printf("%-15s %-15s\n",p.getName(), p.getScore());
            if(winner.getScore() < p.getScore()){
                winner = p;
            }
        }

        if(winner.getScore() == 0){
            System.out.println(ASCII_RED + "Aucun gagnant pour cette fois !"+ASCII_RESET);
        }else{
            System.out.println(ASCII_BLUE + winner.getName() + " est le grand gagnant avec " + winner.getScore() + " point" + (winner.getScore() > 1 ? "s": "")+ASCII_RESET);
        }
        System.out.println(ASCII_GREEN + "Fin de la partie ! :)"+ ASCII_RESET);

    }

    //Générer les 24 possibilités
    public static LinkedList<Podium[]> genererPossibilites() {
        LinkedList<Podium[]> possibilites = new LinkedList<>();

        Animaux[] animaux = Animaux.values();
        for (int i = 0; i < 2; i++) {
            for (Animaux a : animaux) {
                Podium[] podiums = {new Podium("BLEU", ASCII_BLUE), new Podium("ROUGE", ASCII_RED)};

                podiums[i].add(a);

                for (Animaux b : animaux) {
                    if (b != a) {
                        podiums[i].add(b);
                    }
                }

                possibilites.addFirst(podiums);

                Animaux temp = podiums[i].getAnimaux().get(2);
                podiums[i].getAnimaux().set(2, podiums[i].getAnimaux().get(1));
                podiums[i].getAnimaux().set(1, temp);

                possibilites.addFirst(podiums);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (Animaux a : animaux) {
                for (Animaux b : animaux) {
                    if (b != a) {
                        Podium[] podiums = {new Podium("BLEU", ASCII_BLUE), new Podium("ROUGE", ASCII_RED)};

                        // Ajoute l'animal courant sur le premier podium
                        podiums[i].add(a);

                        // Ajoute l'autre animal sur le deuxième podium
                        podiums[1-i].add(b);

                        for (Animaux c : animaux) {
                            if (c != a && c != b) {
                                podiums[1-i].add(c);
                            }
                        }

                        // Ajoute la possibilité
                        possibilites.addFirst(podiums);
                    }
                }
            }
        }

        Collections.shuffle(possibilites); //On mélange la liste

        return possibilites;
    }


    //Fonction de gestion d'une manche de la partie
    public static void manche(List<Player> players, Podium[] pStart, Podium[] pFinal) {
        Scanner currentPlayer = new Scanner(System.in);
        boolean endManche = false;

        while(!endManche) {
            Podium[] current = new Podium[2];
            current[0] = new Podium(pStart[0].getName(), ASCII_BLUE);
            current[0].copyContent(pStart[0]);
            current[1] = new Podium(pStart[1].getName(), ASCII_RED);
            current[1].copyContent(pStart[1]);

            display(current, pFinal);

            System.out.println("Entrer le nom du joueur qui veut jouer ainsi que la liste des commandes.");
            String nomJoueur = currentPlayer.next();
            String commande = currentPlayer.next();
            commande = commande.toUpperCase();


            Player player = findPlayer(players, nomJoueur);
            if(player != null){
                if (player.canPlay()) {
                    String[] commandes = commande.split("(?<=\\G.{" + 2 + "})");
                    for (String c : commandes) {
                        switch (c) {
                            case "KI":
                                Order.KI(current);
                                break;
                            case "LO":
                                Order.LO(current);
                                break;
                            case "SO":
                                Order.SO(current);
                                break;
                            case "NI":
                                Order.NI(current);
                                break;
                            case "MA":
                                Order.MA(current);
                                break;


                        }
                    }
                    if (isSamePodiums(current, pFinal)) {
                        player.addScore();
                        System.out.println(ASCII_GREEN + player.getName() + " remporte cette manche. (" + player.getScore() + " point" + (player.getScore() > 1 ? "s":"") + ")" + ASCII_RESET);
                        endManche = true;
                    }
                    else {
                        player.setCanPlay(false);
                    }
                }else{ System.out.println(ASCII_RED + "Tu as déjà joué cette manche. " + ASCII_RESET);}
            }else{
                System.out.println(ASCII_RED + "Joueur inconnu. " + ASCII_RESET);
            }

            if(!somebodyCanPlay(players)){
                endManche = true;
                display(current, pFinal);
                System.out.println(ASCII_RED + "Dommage pour cette manche, plus personne ne peut jouer !" + ASCII_RESET);
            }
        }
    }

    public static boolean somebodyCanPlay(List<Player> players){
        for(Player p : players){
            if(p.canPlay())return true;
        }
        return false;
    }

    public static Player findPlayer(List<Player> players, String name){
        for (Player player : players){
            if(player.getName().equalsIgnoreCase(name)){
                return player;
            }
        }
        return null;
    }

    //Gestion de l'affichage
    public static void display(Podium[] podiums, Podium[] pFinal) {

        Animaux[] animaux = Animaux.values();

        for (int j = 3; j >= 0; j--) {
            String[] animal = new String[podiums.length + pFinal.length];

            for (int i = 0; i < podiums.length; i++) {
                animal[i] = "";
                animal[i+ podiums.length] = "";

                if(podiums[i].getAnimaux().size() > j){
                    animal[i] = animaux[podiums[i].getAnimaux().get(j).getId()].name();
                }
                if(pFinal[i].getAnimaux().size() > j){
                    animal[i+ podiums.length] = animaux[pFinal[i].getAnimaux().get(j).getId()].name();
                }

            }

            System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", animal[0], animal[1], "", animal[2], animal[3]);
        }

        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "----", "----", "==>", "----", "----");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", podiums[0].getName(), podiums[1].getName(), "", pFinal[0].getName(), pFinal[1].getName());
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-15s %-15s\n","KI: BLEU --> ROUGE", "NI: BLEU ^");
        System.out.printf("%-15s %-15s\n","LO: BLEU <-- ROUGE", "MA: ROUGE ^");
        System.out.printf("%-15s %-15s\n","SO: BLEU <-> ROUGE", "");
        System.out.println();

    }

    public static boolean isSamePodiums(Podium[] podiums, Podium[] pFinal){
        return podiums[0].isSameAs(pFinal[0]) && podiums[1].isSameAs(pFinal[1]);
    }


}
