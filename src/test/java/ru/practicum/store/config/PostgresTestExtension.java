package ru.practicum.store.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class PostgresTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        PostgresTestContainer.getInstance().start();
    }
}