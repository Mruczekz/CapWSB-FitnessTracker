package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleView;
import org.springframework.stereotype.Component;

@Component
class UserSimpleMapper {
    UserSimpleView toDto(User user) {
        return new UserSimpleView(user.getId(),
                user.getFirstName(),
                user.getLastName());
    }
}
