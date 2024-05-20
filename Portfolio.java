package com.example.stockfinal;


import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private final List<Security> securities;

    public Portfolio() {
        securities = new ArrayList<>();
    }

    public void addSecurity(Security security) {
        securities.add(security);
    }

    public void removeSecurity(Security security) {
        securities.remove(security);
    }

    public double calculateTotalValue() {
        double total = 0;
        for (Security security : securities) {
            total += security.calculateValue();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Security security : securities) {
            sb.append(security.toString()).append("\n");
        }
        return sb.toString();
    }
}
