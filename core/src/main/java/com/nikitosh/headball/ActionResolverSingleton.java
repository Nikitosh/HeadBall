package com.nikitosh.headball;

public final class ActionResolverSingleton {
    private static ActionResolver actionResolver;

    private ActionResolverSingleton() {}

    public static ActionResolver getInstance() {
        return actionResolver;
    }

    public static void initialize(ActionResolver actionResolver) {
        ActionResolverSingleton.actionResolver = actionResolver;
    }
}
