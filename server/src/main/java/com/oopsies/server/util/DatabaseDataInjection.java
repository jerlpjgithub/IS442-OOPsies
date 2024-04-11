package com.oopsies.server.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.oopsies.server.entity.EnumRole;
import com.oopsies.server.entity.Event;
import com.oopsies.server.entity.Role;
import com.oopsies.server.entity.User;
import com.oopsies.server.repository.EventRepository;
import com.oopsies.server.repository.RoleRepository;
import com.oopsies.server.repository.UserRepository;
import com.oopsies.server.services.BookingService;
import com.oopsies.server.services.ImageService;

/**
 * DatabaseDataInjection inserts the necessary database into the DB if they do
 * not exist.
 * This is to ensure consistency within development, since we are not using a
 * cloud-based storage.
 */
@Component
public class DatabaseDataInjection implements CommandLineRunner {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ImageService imageService;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private BookingService bookingService;

  @Override
  public void run(String... args) throws Exception {
    // Roles Injection
    if (roleRepository.findByName(EnumRole.ROLE_USER).isEmpty()) {
      roleRepository.save(new Role(EnumRole.ROLE_USER));
    }
    if (roleRepository.findByName(EnumRole.ROLE_OFFICER).isEmpty()) {
      roleRepository.save(new Role(EnumRole.ROLE_OFFICER));
    }
    if (roleRepository.findByName(EnumRole.ROLE_MANAGER).isEmpty()) {
      roleRepository.save(new Role(EnumRole.ROLE_MANAGER));
    }

    // Admin User Injection
    if (userRepository.findAll().isEmpty()) {
      User ticketingOfficer = new User("ticketingOfficer@gmail.com", encoder.encode("ticketingofficer"), "Ticketing",
          "Officer");
      User eventManager = new User("eventManager@gmail.com", encoder.encode("eventmanager"), "Event", "Manager");
      User customer = new User("customer1@gmail.com", encoder.encode("customer"), "Customer", "One", 1000);

      ticketingOfficer.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_OFFICER).get())));
      eventManager.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_MANAGER).get())));
      customer.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_USER).get())));

      userRepository.save(ticketingOfficer);
      userRepository.save(eventManager);
      userRepository.save(customer);
    }

    Resource resource = new ClassPathResource("data.sql");
    try (Connection connection = dataSource.getConnection()) {
      ScriptUtils.executeSqlScript(connection, resource);
    } catch (Exception e) {
      // Handle exceptions appropriately
      e.printStackTrace();
    }

    // Optional<User> customer = userRepository.findByEmail("customer1@gmail.com");
    // if (customer.isPresent()) {
    // ArrayList<Event> events = new ArrayList<>(eventRepository.findAll());

    // for (Event event : events) {
    // bookingService.createBooking(customer.get().getId(), event.getId(), 5);
    // }
    // }
  }
}