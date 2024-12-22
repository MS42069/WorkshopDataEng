package com.fhwedel.softwareprojekt.v1.model;

import java.util.UUID;

/** Interface to make entities, which are uniquely identifiable by their ID, compatible. */
public interface IdentifiableEntity {
    UUID getId();
}
