package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailInfoView;
import org.springframework.stereotype.Component;

@Component
class UserEmailInfoMapper {
    UserEmailInfoView toDto(User user) {
        return new UserEmailInfoView(user.getId(),
                user.getEmail());
    }
}
