package com.example;

import java.util.Random;

class Voleur extends Personnage {
    public Voleur(String nom) {
        super(nom, 70, 12);
    }

    @Override
    public void attaquer(Personnage cible) {
        System.out.println(nom + " attaque furtivement " + cible.getNom() + " !");
        cible.recevoirDegats(degats);
        // Chance d'une attaque critique
        if (Math.random() < 0.2) {
            System.out.println(nom + " inflige un coup critique !");
            cible.recevoirDegats(degats);
        }
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println(nom + " utilise sa compétence spéciale : Attaque rapide !");
        cible.recevoirDegats(degats * 3);
    }
}
