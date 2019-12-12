package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void verify_addTocart() throws Exception{
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(0);
        request.setQuantity(3);
        final ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        User nUser = new User();
        nUser.setId(0);
        nUser.setUsername("test");
        nUser.setPassword("thisIsHashed");
        nUser.setCart(cart);
        assertNotNull(cart);
        assertEquals(java.util.Optional.of(0), cart.getId());
        assertEquals(nUser, cart.getUser());
        List<Item> IList = new ArrayList<Item>();
        Item Inew = new Item();
        Inew.setId((long) 0);
        Inew.setName("shampoo");
        Inew.setDescription("nice");
        Inew.setPrice(2);
        IList.add(Inew);
        assertEquals(IList, cart.getItems());
        assertEquals(6, cart.getTotal());
    }

    @Test
    public void verify_removeFromcart() throws Exception{
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(0);
        request.setQuantity(3);
        final ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        User nUser = new User();
        nUser.setId(0);
        nUser.setUsername("test");
        nUser.setPassword("thisIsHashed");
        nUser.setCart(cart);
        assertNotNull(cart);
        assertEquals(java.util.Optional.of(0), cart.getId());
        assertEquals(nUser, cart.getUser());
        List<Item> IList = cart.getItems();
        for(int i=0; i<IList.size(); i++)
        {
            assertNotEquals(0, IList.get(i).getId().intValue());
        }

        assertEquals(0, cart.getTotal());
    }
}
