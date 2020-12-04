package org.example.dao.store;

import org.example.model.Store;

import java.util.Collection;

public interface StoreDao {

    Collection<Store> readAll();
}
