package com.example.Makeup.controller.web.web;

import com.example.Makeup.dto.model.FeedBackDTO;
import com.example.Makeup.dto.model.StaffDTO;
import com.example.Makeup.dto.model.TypeMakeupDTO;
import com.example.Makeup.service.common.CacheFeedBackService;
import com.example.Makeup.service.common.CacheStaffService;
import com.example.Makeup.service.common.CacheTypeMakeupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

  private final CacheFeedBackService cacheFeedBackService;
  private final CacheTypeMakeupService cacheTypeMakeupService;
  private final CacheStaffService cacheStaffService;

  @RequestMapping({"/home", "/"})
  public String home(ModelMap model) {
    List<TypeMakeupDTO> serviceList = cacheTypeMakeupService.cacheAllTypeMakeup().getResult();
    List<StaffDTO> staffList = cacheStaffService.cacheAllStaff().getResult();
    List<FeedBackDTO> feedBackDTOS = cacheFeedBackService.cacheGoodFeedback().getResult();

    model.addAttribute("typeMakeupList", serviceList);
    model.addAttribute("staffList", staffList);
    model.addAttribute("feedbacks", feedBackDTOS);
    return "user/index";
  }
}
