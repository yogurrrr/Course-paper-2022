package com.knapsnack_problem_fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class problem_solver {

    public static ObservableList<Item> getBestItems(CollectionItems Items)
    {
        ObservableList<Item> sortedItems = Items.getItems();

        sortedItems.sort((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));

        Item[] ArrItems = sortedItems.toArray(Item[]::new);
        //проверка массива
//        System.out.println("Оптимальное содержимое рюкзака:");
//        for(int i=0; i<ArrItems.length; i++) {
//            System.out.println(ArrItems[i].getName());
//        }

        int[][] A;
        A = new int[ArrItems.length + 1][];
        for (int i = 0; i < ArrItems.length + 1; i++) {
            A[i] = new int[Items.getCapacity() + 1];
        }

        for (int k = 0; k <= ArrItems.length; k++) {
            for (int s = 0; s <= Items.getCapacity(); s++) {
                if (k == 0 || s == 0) {
                    A[k][s] = 0;
                } else {
                    if (s >= ArrItems[k - 1].getWeight()) {
                        A[k][s] = Math.max(A[k - 1][s], A[k - 1][s - ArrItems[k - 1].getWeight()] + ArrItems[k - 1].getValue());
                    } else {
                        A[k][s] = A[k - 1][s];
                    }
                }
            }
        }

        ObservableList<Item> putItems = FXCollections.observableArrayList();
        traceResult(A, ArrItems, ArrItems.length, Items.getCapacity(), putItems);

        //жадный алгоритм
//        sortedItems.sort((a, b) -> Integer.compare(b.getValue()/b.getWeight(), a.getValue()/a.getWeight()));
//        Iterator<Item> Iter = sortedItems.iterator();//создаем итератор
//        Integer w = Items.getCapacity();
//        ObservableList<Item> putItems = FXCollections.observableArrayList();
//
//        while (Iter.hasNext())
//        {
//            Item nextI = Iter.next();
//            if (nextI.getWeight()>w){break;}
//            else{
//            putItems.add(nextI);
//            w -= nextI.getWeight();}
//        }
        //return putItems;
        return putItems;
    };

    private static void traceResult(int[][] A, Item[] ArrItems, int k, int s, ObservableList<Item> result) {
        if (A[k][s] == 0) {
            return;
        }
        if (A[k -1][s] == A[k][s]) {
            traceResult(A, ArrItems, k - 1, s, result);
        } else {
            traceResult(A, ArrItems, k - 1, s - ArrItems[k - 1].getWeight(), result);
            result.add(ArrItems[k-1]);
        }
    }
}
