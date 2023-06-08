package fr.iut.crazycircus.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Podium {

    ArrayList<Animaux> container;
    String name;
    String color;

    public Podium(String name, String color){
        this.name = name;
        this.color = color;
        this.container = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Animaux> getAnimaux(){
        return container;
    }

    public boolean isSameAs(Podium p){
        if(p.getAnimaux().size() != this.getAnimaux().size()) return false;

        for (int i = 0; i < p.getAnimaux().size(); i++){
            if(p.getAnimaux().get(i).getId() != this.getAnimaux().get(i).getId()) return false;
        }

        return true;
    }

    public void copyContent(Podium p){
        for (int i = 0; i < p.getAnimaux().size(); i++) this.add(p.getAnimaux().get(i));
    }

    public String getColor(){
        return this.color;
    }

    public void add(Animaux element) {
        this.container.add(element);
    }

    public Animaux getElement(int index){
        return this.container.get(index);
    }

    public void setElement(int index, Animaux animal){
        this.container.set(index, animal);
    }
    public Animaux remove() {
        return this.container.remove(this.container.size()-1);
    }

    public void putFirst() {
        this.add(this.container.remove(0));
    }

    public boolean isEmpty(){
        return this.container.isEmpty();
    }

    public int getSize(){
        return this.container.size();
    }

}
