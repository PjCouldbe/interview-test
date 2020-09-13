package com.optisystems.interview.test.model.base;

/**
 * Determine that entity has PK (where is my boat?)
 * Helps to share pk of entity in facades
 */
public interface Key<PK extends Comparable<PK>> {
    PK getPK();

    void setPK(PK pk);
}
