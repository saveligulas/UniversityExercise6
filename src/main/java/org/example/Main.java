package org.example;

import org.example.collections.DynamicLinkedList;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DynamicLinkedList<String> list = new DynamicLinkedList<>();
        list.add("foo");
        list.add("bar");
        list.add("baz");
        list.add("foo");
        list.add("too");
        list.remove("foo");
        System.out.println(list.size());
        list.remove("bar");
        System.out.println(list.size());
        System.out.println(list.get(1));
        System.out.println(list.get(0));
        System.out.println(list.get(2));
    }
}