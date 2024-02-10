package com.oopsies.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// /**
//  * The TestController is used for testing purposes only.
//  * It is used to validate if roles based authorisation is working.
//  * Roles available are - enum('ROLE_USER','ROLE_OFFICER','ROLE_MANAGER')
//  */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/all")
    public ResponseEntity<String> allAccess() {
        return new ResponseEntity<>("Public Content.", HttpStatus.OK);
    }

    @GetMapping("/officer")
    @PreAuthorize("hasRole('ROLE_OFFICER') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> userAccess() {
        return new ResponseEntity<>("Officer Content.", HttpStatus.OK);
    }

    @GetMapping("/manager")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<String> moderatorAccess() {
        return new ResponseEntity<>("Manager Board.", HttpStatus.OK);
    }

    // @GetMapping("/admin")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<String> adminAccess() {
    //     return new ResponseEntity<>("Admin Board.", HttpStatus.OK);
    // }

}
