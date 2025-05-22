
package com.capgemini.Booking.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.capgemini.Booking.Dto.User;


@FeignClient(name = "user-service", url = "http://localhost:8007/api/users")
public interface UserServiceClient {

    @GetMapping("/view/{userId}")
    User getUserById(@PathVariable("userId") Long userId);
}