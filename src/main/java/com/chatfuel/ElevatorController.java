package com.chatfuel;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import com.chatfuel.services.ElevatorServiceBaseImplGen1;
import com.chatfuel.services.ElevatorServiceBaseImplGen2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.PriorityQueue;


@Controller
@RequestMapping(path="/gen2")
public class ElevatorController {

    private final ElevatorServiceBaseImplGen2 service;
    @Autowired
    public ElevatorController(ElevatorServiceBaseImplGen2 service) {
        this.service = service;
    }

    @PostMapping
    @SuppressWarnings("unchecked")
    @RequestMapping(path="/initiate")
    public ResponseEntity innitialiseSystem (@RequestBody ElevatorServiceBaseImplGen2 entity) {

        service.setDoorsDelay(entity.getDoorsDelay());
        service.setFloorHeight(entity.getFloorHeight());
        service.setFloorTotal(entity.getFloorTotal());
        service.setSpeed(entity.getSpeed());

        // if you would like to add more elevators into the system, use your value here
        service.setElevatorNumber(entity.getElevatorNumber());

        service.elevatorInitiate();
        service.getElevators().forEach(e->{
            e.setQueue(new PriorityQueue<>(service.getPersonComparator()));
            System.out.println(e.toString()+" Current floor is: "+e.getCurrentFloor());
        });

        return new ResponseEntity(service, HttpStatus.OK);
    }

    @PostMapping
    @SuppressWarnings("unchecked")
    @RequestMapping(path="/call")
    public ResponseEntity callOutside (@RequestBody Person person) {

        person.setTimeStamp(Instant.now().toEpochMilli());
        Elevator applicableElevator = service.getApplicableElevator();
        applicableElevator.addPersonToQueue(person);

        if (!applicableElevator.isUnderProgress())
            service.operateElevator(applicableElevator);

        return new ResponseEntity(applicableElevator, HttpStatus.OK);
    }

}
