package com.example;

class Guerrier extends Personnage {
    public Guerrier(String nom) {
        super(nom, 100, 10);
    }

    @Override
    public void attaquer(Personnage cible) {
        System.out.println(nom + " attaque " + cible.getNom() + " !");
        cible.recevoirDegats(degats);
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println(nom + " utilise sa compétence spéciale : Coup de rage !");
        cible.recevoirDegats(degats * 2);
    }
}
