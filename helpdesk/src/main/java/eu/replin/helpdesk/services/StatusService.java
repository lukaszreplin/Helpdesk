package eu.replin.helpdesk.services;

import eu.replin.helpdesk.domain.Status;
import eu.replin.helpdesk.domain.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;

    public Status getStatus(int id) {
        return statusRepository.findById(id);
    }

}
