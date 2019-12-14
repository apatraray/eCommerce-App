package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private OrderController orderController;

    private UserRepository userRepo = mock(UserRepository.class);

    private OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void verify_submit() throws Exception{
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
        cart.setItems(Arrays.asList(item));
        cart.setTotal(BigDecimal.valueOf(300));
        cart.setId((long) 0);
        nUser.setCart(cart);
        when(userRepo.findByUsername("test")).thenReturn(nUser);
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder u = response.getBody();
        assertNotNull(u);
        assertEquals(nUser, u.getUser());
    }

    @Test
    public void verify_getOrdersForUser() throws Exception{
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
        cart.setItems(Arrays.asList(item));
        cart.setTotal(BigDecimal.valueOf(300));
        cart.setId((long) 0);
        nUser.setCart(cart);

        UserOrder uOrder = new UserOrder();
        uOrder.setUser(nUser);
        uOrder.setId((long) 0);
        when(userRepo.findByUsername("test")).thenReturn(nUser);
        when(orderRepo.findByUser(nUser)).thenReturn(Arrays.asList(uOrder));
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UserOrder> u = response.getBody();
        assertNotNull(u);
        assertEquals(nUser, u.get(0).getUser());
    }

}
