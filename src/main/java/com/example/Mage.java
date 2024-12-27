package com.example;

class Mage extends Personnage {
    public Mage(String nom) {
        super(nom, 80, 15);
    }

    @Override
    public void attaquer(Personnage cible) {
        System.out.println(nom + " lance un sort sur " + cible.getNom() + " !");
        cible.recevoirDegats(degats);
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println(nom + " invoque une tempête magique !");
        cible.recevoirDegats(degats + 10);
    }
}
