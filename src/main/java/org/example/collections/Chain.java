package org.example.collections;

public interface Chain<T> {
    public default void linkParent(Chain<T> parent) {
        parent.linkChild(this);
    }

    public void linkChild(Chain<T> child);

    public void unlinkParent();

    public void unlinkChild();

    public boolean hasChild();
}
