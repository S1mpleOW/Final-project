package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.Item;

public interface ItemService {
    Item save(Item item);

    Item findById(Long id);

    boolean existsById(Long id);

    boolean existsByNameIgnoreCase(String name);

    Item findByName(String name);

    int findSellPrice(Long id);

    int getPurchasePriceByMonth(int month, int year);

    int getPurchasePriceByDay(int day, int month, int year);
}
