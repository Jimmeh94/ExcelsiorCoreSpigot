package ecore.services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Service<T> {

    protected List<T> objects = new CopyOnWriteArrayList<>();

    public void add(T t){
        objects.add(t);
    }

    public void remove(T t){
        objects.remove(t);
    }

    public List<T> getObjects() {
        return objects;
    }
}
