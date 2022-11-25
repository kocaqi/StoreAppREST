package com.localweb.storeapp.service;

import com.localweb.storeapp.entity.Client;
import com.localweb.storeapp.entity.Order;
import com.localweb.storeapp.payload.Response;
import com.localweb.storeapp.payload.entityDTO.OrderDTO;
import com.localweb.storeapp.repository.ClientRepository;
import com.localweb.storeapp.repository.OrderRepository;
import com.localweb.storeapp.repository.UserRepository;
import com.localweb.storeapp.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, UserRepository userRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    public OrderDTO create(long clientId, Principal principal) {
        //convert DTO to entity
        Order order = new Order();
        Client client = clientRepository.findClientById(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client", "id", clientId));
        order.setClient_id(client);
        order.setDateCreated(LocalDate.now());
        order.setDateUpdated(LocalDate.now());
        String email = principal.getName();
        order.setUser(userRepository.findUserByEmail(email).orElseThrow(()->new UsernameNotFoundException("User with email "+email+" not found!" )));
        order.setAmount(0);
        Order newOrder = orderRepository.save(order);

        //convert entity to DTO
        return modelMapper.map(newOrder, OrderDTO.class);
    }

    public Response<OrderDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> orders = orderRepository.findAll(pageable);

        List<Order> orderList = orders.getContent();

        List<OrderDTO> content = orderList.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());

        Response<OrderDTO> orderResponse = new Response<>();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());

        return orderResponse;
    }

    public OrderDTO getById(long id) {
        Order order = orderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order getOrderById(long id) {
        return orderRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}
