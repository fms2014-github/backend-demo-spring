package com.springBoot.backend.entity.base;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(ColumnDefaultValueInjectListeners.class)
public abstract class BaseEntity {
}
