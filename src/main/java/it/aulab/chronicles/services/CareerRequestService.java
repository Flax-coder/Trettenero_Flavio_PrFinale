package it.aulab.chronicles.services;

import it.aulab.chronicles.models.CareerRequest;
import it.aulab.chronicles.models.User;

public interface CareerRequestService {
    boolean isRoleAlreadyAssigned(User user, CareerRequest careerRequest);
    void save(CareerRequest careerRequest, User user);
    void careerAccept(Long requestId);
    CareerRequest find(Long id);
}
