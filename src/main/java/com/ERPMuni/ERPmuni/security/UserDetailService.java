package com.ERPMuni.ERPmuni.security;

import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + email));
        if (userModel == null) {
            System.out.println("error");
        }
        System.out.println("Email: " + userModel.getEmail());
        return new UserDetails(userModel);
    }
}
