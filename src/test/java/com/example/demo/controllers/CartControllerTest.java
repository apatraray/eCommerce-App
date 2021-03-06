package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
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

        User nUser = new User();
        nUser.setUsername("test");
        nUser.setPassword("test");
        nUser.setId(1);

        Item item = new Item();
        item.setName("phone");
        item.setId((long) 0);
        item.setPrice(100);

        Cart cart = new Cart();
        cart.setUser(nUser);
        cart.setId((long) 0);
        nUser.setCart(cart);

        when(userRepo.findByUsername("test")).thenReturn(nUser);
        when(itemRepo.findById((long) 0)).thenReturn(java.util.Optional.of(item));
        final ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart nCart = response.getBody();
        User user = nCart.getUser();
        assertNotNull(nCart);
        assertNotNull(user);
        assertEquals(nUser, cart.getUser());
        assertEquals(item, cart.getItems().get(0));
    }

    @Test
    public void verify_removeFromcart() throws Exception{
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(0);
        request.setQuantity(3);

        User nUser = new User();
        nUser.setUsername("test");
        nUser.setPassword("test");
        nUser.setId(1);

        Item item = new Item();
        item.setName("phone");
        item.setId((long) 0);
        item.setPrice(100);

        Cart cart = new Cart();
        cart.setUser(nUser);
        cart.setId((long) 0);
        nUser.setCart(cart);

        when(userRepo.findByUsername("test")).thenReturn(nUser);
        when(itemRepo.findById((long) 0)).thenReturn(java.util.Optional.of(item));
        final ResponseEntity<Cart> response = cartController.removeFromcart(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
