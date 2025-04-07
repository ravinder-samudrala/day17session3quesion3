package com.Day17Session3que3;
import com.Day17Session3que3.repository.OrderRepository;
import com.Day17Session3que3.service.OrderService;

import com.Day17Session3que3.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetOrderById() {
		Order mockOrder = new Order("John Doe", "Laptop, Phone", "Pending", 1500.0);
		mockOrder.setId(1L);

		when(orderRepository.findById(1L)).thenReturn(Optional.of(mockOrder));

		Order order = orderService.getOrderById(1L);

		assertNotNull(order);
		assertEquals("John Doe", order.getCustomerName());
		assertEquals("Pending", order.getStatus());
		verify(orderRepository, times(1)).findById(1L);
	}

	@Test
	public void testCreateOrder() {
		Order mockOrder = new Order("John Doe", "Laptop, Phone", "Pending", 1500.0);

		when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

		Order savedOrder = orderService.createOrder(mockOrder);

		assertNotNull(savedOrder);
		assertEquals("John Doe", savedOrder.getCustomerName());
		verify(orderRepository, times(1)).save(mockOrder);
	}

	@Test
	public void testDeleteOrder() {
		when(orderRepository.existsById(1L)).thenReturn(true);
		doNothing().when(orderRepository).deleteById(1L);

		orderService.deleteOrder(1L);

		verify(orderRepository, times(1)).existsById(1L);
		verify(orderRepository, times(1)).deleteById(1L);
	}
}
