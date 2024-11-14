
# IS442 Project: Ticket Booking System (G3T7)

# Project Overview

As part of IS442 Object Oriented Programming under Prof. Bin Zhu, this project aims to build a ticketing system that manages the booking of tickets for various events, such as music concerts, theatre shows, and seminars. To ensure secure payments, we have utilised Spring Security, together with JWTs to secure our endpoints with roles-based access control. Disclaimer: As this is a school project, we made use of some mock data in development. 

# Demonstration
[![Video](![photo_2024-11-14_19-45-48](https://github.com/user-attachments/assets/790d0b72-8a0c-42f9-9fb0-05315a8c8c5f)
)](https://www.youtube.com/watch?v=P8-abY2ZRwE)

# Tech Stack

### Front End

1. ReactJS
2. Ant Design 5.0

### Back End
1. Spring Boot
2. MySQL
3. Docker

## Open Source Libararies Used
### Spring Libraries
1. Spring Boot Devtools
2. Spring Boot Starter Mail
3. Spring Boot Starter Parent
4. Spring Boot Starter Data JPA
5. Spring Boot Starter Security
6. Spring Security Crypto
7. Spring Boot Starter Validation
8. Spring Boot Starter Web

### Others
1. Java JWT
2. Google OAuth 
3. Jackson Databind
4. MySQL Connector-J
5. superCSV



# UML Diagram
```mermaid
classDiagram
direction LR
class Booking {
  + Booking() 
  - Long id
  - Date cancelDate
  - Date bookingDate
  - User user
  + getBookingID() long
  + setBookingID(long) void
  + getBookingDate() Date
  + getEvent() Event
  + setUser(User) void
  + getCancelDate() Date
  + setEvent(Event) void
  + setBookingDate(Date) void
  + getUser() User
  + toString() String
  + setCancelDate(Date) void
}
class EnumRole {
<<enumeration>>
  + EnumRole() 
  +  ROLE_MANAGER
  +  ROLE_USER
  +  ROLE_OFFICER
  + valueOf(String) EnumRole
  + values() EnumRole[]
}
class Event {
  + Event() 
  + Event(String, User, Date, String, Date, int, double, double) 
  - double ticketPrice
  - int capacity
  - String eventName
  - Date dateTime
  - Long id
  - String venue
  - Date cancelDate
  - double cancellationFee
  + getEventCancelDate() Date
  + getId() Long
  + setId(Long) void
  + isEventCancelled() boolean
  + getCancellationFee() double
  + setCapacity(int) void
  + setCancellationFee(double) void
  + getTicketPrice() double
  + getCapacity() int
  + setVenue(String) void
  + setEventName(String) void
  + setDateTime(Date) void
  + setTicketPrice(double) void
  + getManagerID() User
  + toString() String
  + getDateTime() Date
  + setManagerID(User) void
  + getVenue() String
  + getEventName() String
  + updateDetails(String, User, Date, String, Date, int, double, double) void
  + setEventCancelled(Date) void
}
class EventManager {
  + EventManager(String, String, String, String, Set~Role~, boolean, double, TicketService) 
  + EventManager() 
  + setEventCancellationFee(Event, double) void
}
class Payment {
  + Payment() 
  + Payment(Booking, double, Date) 
  - long paymentId
  - Date paymentDate
  - double amount
  + setBooking(Booking) void
  + setPaymentId(long) void
  + getAmount() double
  + setAmount(double) void
  + getPaymentDate() Date
  + getBooking() Booking
  + toString() String
  + getPaymentId() long
  + setPaymentDate(Date) void
}
class Provider {
<<enumeration>>
  + Provider() 
  +  LOCAL
  +  GOOGLE
  + values() Provider[]
  + valueOf(String) Provider
}
class RefreshToken {
  + RefreshToken() 
  - String token
  - Instant expiryDate
  - long id
  + setUser(User) void
  + setId(long) void
  + getUser() User
  + getToken() String
  + setExpiryDate(Instant) void
  + getId() long
  + getExpiryDate() Instant
  + setToken(String) void
}
class Refund {
  + Refund() 
  + Refund(Booking, Date) 
  - Date refundDate
  - Long refundId
  + getBooking() Booking
  + setRefundDate(Date) void
  + getRefundId() Long
  + setBooking(Booking) void
  + setRefundId(Long) void
  + getRefundDate() Date
}
class Role {
  + Role() 
  + Role(EnumRole) 
  - Integer id
  + getId() Integer
  + setId(Integer) void
  + setName(EnumRole) void
  + getName() EnumRole
}
class Ticket {
  + Ticket() 
  - boolean redeemed
  - long ticketId
  - Image image
  + setBooking(Booking) void
  + getBooking() Booking
  + isRedeemed() boolean
  + toString() String
  + getImage() Image
  + setImage(Image) void
  + getId() long
  + setRedeemed(boolean) void
  + setId(long) void
}
class TicketingOfficer {
  + TicketingOfficer(String, String, String, String, Set~Role~, boolean, double) 
  + TicketingOfficer() 
}
class User {
  + User(String, String, String, String, double) 
  + User(String, String, String, String, Set~Role~, boolean, double) 
  + User(String, String, String, String) 
  + User() 
  - Long id
  - String firstName
  - String password
  - String email
  - Provider provider
  - String lastName
  - boolean emailVerified
  - double accountBalance
  - Set~Role~ roles
  + setAccountBalance(double) void
  + setLastName(String) void
  + toString() String
  + setFirstName(String) void
  + getId() Long
  + getRoles() Set~Role~
  + getEmailVerified() boolean
  + setPassword(String) void
  + getProvider() Provider
  + setProvider(Provider) void
  + isEmailVerified() boolean
  + incrementAccountBalance(double) void
  + getAccountBalance() double
  + decrementAccountBalance(double) void
  + setEmail(String) void
  + setId(Long) void
  + setEmailVerified(boolean) void
  + getLastName() String
  + getPassword() String
  + getFirstName() String
  + getEmail() String
  + setRoles(Set~Role~) void
}

EventManager  --|>  User 
TicketingOfficer  --|>  User 
RefreshToken "1" ..> "1" User
User "*" ..> "*" Role
Role "*" ..> "*" EnumRole
Payment "1" ..> "1" Booking
Booking "*" ..> "1" Event
Event "*" ..> "1" EventManager
Ticket "*" ..> "1" Booking
Refund "1" ..> "1" Booking
User "*" ..> "1" Provider
```


# Set up project
To set up and run our application, firstly ensure that your computer has docker installed. If not head to <a> https://docs.docker.com/engine/install/ </a> and install docker. 

Next, clone this GitHub repository through GitHub Desktop or through the CLI. Once you have done that, `cd` into the root directory of the project and run `docker compose up`.

Vist <a>http://localhost:3000/ </a> in your browser to open up the web application. Use the login credentials for the different roles listed below to test out system functionality 

### Login credentials

#### Customer

Username: `customer1@gmail.com`
Password: `customer`

#### Event Manager

Username: `eventManager@gmail.com`
Password: `eventmanager`

#### Ticketing Officer

Username: `ticketingOfficer@gmail.com`
Password: `ticketingofficer`
