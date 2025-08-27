package com.example.Makeup.controller.web.web;

import com.example.Makeup.service.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AppointmentController {

  private final IAppointmentService appointmentService;

  @RequestMapping("/my-appointment")
  public String appointment() {
    return "user/appointment";
  }
}
