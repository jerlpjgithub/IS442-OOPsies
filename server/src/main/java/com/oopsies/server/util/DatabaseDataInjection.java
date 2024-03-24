package com.oopsies.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
      User customer = new User("customer1@gmail.com", encoder.encode("customer"), "Customer", "One");

      ticketingOfficer.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_OFFICER).get())));
      eventManager.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_MANAGER).get())));
      customer.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(EnumRole.ROLE_USER).get())));

      userRepository.save(ticketingOfficer);
      userRepository.save(eventManager);
      userRepository.save(customer);
    }

    Optional<User> eventManager = userRepository.findByEmail("eventManager@gmail.com");
    if (eventManager.isPresent() && eventRepository.findAll().isEmpty()) {
      Date currentDate = new Date();
      currentDate.setDate(currentDate.getDate() + 7);
      ArrayList<HashMap<String, Object>> events = new ArrayList<>(
          Arrays.asList(
              new HashMap<>(Map.of(
                  "eventName", "Taylor Swift - Era 1989",
                  "dateTime", currentDate,
                  "venue", "Singapore Indoor Sports Hall",
                  "capacity", 2000,
                  "cancellationFee", 100.0,
                  "ticketPrice", 500.0)),
              new HashMap<>(Map.of(
                  "eventName", "Ed Sheeran - Divide Tour",
                  "dateTime", currentDate,
                  "venue", "Singapore Indoor Sports Hall",
                  "capacity", 3000,
                  "cancellationFee", 150.0,
                  "ticketPrice", 750.0)),
              new HashMap<>(Map.of(
                  "eventName", "Michael Jackson - This Is It",
                  "dateTime", currentDate,
                  "venue", "National Stadium",
                  "capacity", 4000,
                  "cancellationFee", 200.0,
                  "ticketPrice", 1000.0)),
              new HashMap<>(Map.of(
                  "eventName", "TWICE - World Tour",
                  "dateTime", currentDate,
                  "venue", "National Stadium",
                  "capacity", 5000,
                  "cancellationFee", 250.0,
                  "ticketPrice", 1250.0)),
              new HashMap<>(Map.of(
                  "eventName", "Eason Chan - Live Concert",
                  "dateTime", currentDate,
                  "venue", "Singapore Indoor Sports Hall",
                  "capacity", 6000,
                  "cancellationFee", 300.0,
                  "ticketPrice", 1500.0))));

      for (HashMap<String, Object> event : events) {
        System.out.println(event.get("eventName"));
        Event newEvent = new Event(
            (String) event.get("eventName"),
            eventManager.get(),
            (Date) event.get("dateTime"),
            (String) event.get("venue"),
            null,
            (int) event.get("capacity"),
            (double) event.get("cancellationFee"),
            (double) event.get("ticketPrice"));

        eventRepository.save(newEvent);
      }
    }

    // Optional<User> customer = userRepository.findByEmail("customer1@gmail.com");
    // if (customer.isPresent()) {
    //   ArrayList<Event> events = new ArrayList<>(eventRepository.findAll());

    //   for (Event event : events) {
    //   bookingService.createBooking(customer.get().getId(), event.getId(), 5);
    //   }
    // }
  }
}