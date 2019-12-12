package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_getItems() throws Exception{
        Item itemOne = new Item();
        itemOne.setName("phone");
        itemOne.setId(Long.valueOf(0));
        itemOne.setPrice(100);

        Item itemTwo = new Item();
        itemOne.setName("purse");
        itemOne.setId(Long.valueOf(1));
        itemOne.setPrice(50);

        List<Item> items = Arrays.asList(itemOne, itemTwo);
        when(itemRepo.findAll()).thenReturn(items);
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> lItem = response.getBody();
        assertNotNull(lItem);
    }

    @Test
    public void verify_getItemById() throws Exception {
        Item item = new Item();
        item.setName("phone");
        item.setId((long) 0);
        item.setPrice(100);

        when(itemRepo.findById((long) 0)).thenReturn(java.util.Optional.of(item));
        final ResponseEntity<Item> response = itemController.getItemById((long) 0);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item itemNew = response.getBody();
        assertNotNull(itemNew);
     //   assertEquals(0, itemNew.getId());
        assertEquals("phone", itemNew.getName());
    }

    @Test
    public void verify_getItemsByName() throws Exception {
        Item item = new Item();
        item.setName("phone");
        item.setId((long) 0);
        item.setPrice(100);
        List<Item> lItem = Arrays.asList(item);
        when(itemRepo.findByName("phone")).thenReturn(lItem);
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("phone");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> itemsNew = response.getBody();
        assertNotNull(itemsNew);
    //    assertEquals(0, itemsNew.get(0).getId());
        assertEquals("phone", itemsNew.get(0).getName());
        assertEquals(1, itemsNew.size());
    }

}