package com.chatfuel;

import com.chatfuel.domain.Direction;
import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ElevatorController {

    private final ElevatorService service;
    @Autowired
    public ElevatorController(ElevatorService service) {
        this.service = service;
    }

    @PostMapping
    @SuppressWarnings("unchecked")
    @RequestMapping(path="/initiate")
    public ResponseEntity innitialiseSystem (@RequestBody ElevatorService entity) {

        service.setDoorsDelay(entity.getDoorsDelay());
        service.setFloorHeight(entity.getFloorHeight());
        service.setFloorTotal(entity.getFloorTotal());
        service.setSpeed(entity.getSpeed());

        // if you would like to add more elevators into the system, use your value here
        service.setElevatorNumber(entity.getElevatorNumber());

        service.elevatorInitiate();
        return new ResponseEntity(service, HttpStatus.OK);
    }

    @PostMapping
    @SuppressWarnings("unchecked")
    @RequestMapping(path="/callOutside")
    public ResponseEntity callOutside (@RequestBody Person person) {

        Elevator applicableElevator = service.getApplicableElevator();
        applicableElevator.addPersonToQueueOutside(person);

        if (!applicableElevator.isUnderOperate())
            service.operateElevator(applicableElevator);

        return new ResponseEntity(applicableElevator, HttpStatus.OK);
    }

}
