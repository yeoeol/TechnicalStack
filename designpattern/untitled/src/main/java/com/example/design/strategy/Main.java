package com.example.design.strategy;

public class Main {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        Item A = new Item("TestA", 100);
        Item B = new Item("TestB", 300);

        cart.addItem(A);
        cart.addItem(B);

        // pay by LUNACard
        cart.pay(new LUNACardStrategy("test@example.com", "test"));

        // pay by KAKAOCard
        cart.pay(new KAKAOCardStrategy("test", "123456789", "123", "12/20"));
    }
}
