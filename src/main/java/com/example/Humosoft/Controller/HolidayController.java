package com.example.Humosoft.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Humosoft.DTO.Request.HolidayRequest;
import com.example.Humosoft.DTO.Response.Apiresponse;
import com.example.Humosoft.Model.Holiday;
import com.example.Humosoft.Service.HolidayService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {

 
    private final HolidayService holidayService;

    
    @PostMapping
    public Apiresponse<Holiday> createHoliday(@RequestBody HolidayRequest holidayRequest) {
        Holiday holiday = holidayService.create(holidayRequest);
        return Apiresponse.<Holiday>builder()
            .code(201)
            .message("Holiday created successfully")
            .result(holiday)
            .build();
    }

    // Get all holidays
    @GetMapping
    public Apiresponse<List<Holiday>> getAllHolidays() {
        return Apiresponse.<List<Holiday>>builder()
            .code(200)
            .message("Fetched holidays successfully")
            .result(holidayService.getAll())
            .build();
    }

    // Delete a holiday by name
    @DeleteMapping("/{Id}")
    public Apiresponse<Void> deleteHolidayByName(@PathVariable Integer Id) {
        holidayService.delete(Id);
        return Apiresponse.<Void>builder()
            .code(200)
            .message("Holiday deleted successfully")
            .build();
    }
}