package com.example.Makeup.controller.web.web;
import com.example.Makeup.dto.model.ServiceMakeupDTO;
import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.service.ServiceMakeupService;
import com.example.Makeup.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ServiceMakeupService serviceMakeupService;
    private final StaffService staffService;

    @RequestMapping({"/home","/"})
    public String home(ModelMap model) throws InterruptedException {

        List<ServiceMakeupDTO> serviceList = serviceMakeupService.getAllServices().getResult();
        List<StaffDTO> staffList = staffService.getAllStaff().getResult();
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("staffList", staffList);
        return "user/index";
    }

}
