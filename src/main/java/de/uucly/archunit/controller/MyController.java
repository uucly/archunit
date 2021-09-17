package de.uucly.archunit.controller;

import de.uucly.archunit.service.MyControllerService;
import de.uucly.archunit.business.MyBusinessObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyControllerService myService = new MyControllerService();

    @GetMapping
    public MyResponseDTO getRequest(MyRequestDTO requestDTO) {

        myService.getBusinessObject(new MyBusinessObject());
        return new MyResponseDTO();
    }
}
