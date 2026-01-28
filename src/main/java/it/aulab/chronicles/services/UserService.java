package it.aulab.chronicles.services;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.aulab.chronicles.dtos.UserDto;
import it.aulab.chronicles.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void saveUser(UserDto userDto, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest, HttpServletResponse response);
    User findUserByEmail(String email);
    User find(Long id);
}
