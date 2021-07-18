package com.mercadolibre.challengue.shared.config;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public final class IdentifierCreator {
    public static UUID generateUUID() {
        return Generators.timeBasedGenerator().generate();
    }
}
