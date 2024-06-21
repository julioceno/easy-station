package com.easy_station.management.users.services;

import com.easy_station.management.common.exceptions.BadRequestException;
import com.easy_station.management.common.grpc.dto.UserReturnDTO;
import com.easy_station.management.users.domain.User;
import com.easy_station.management.users.domain.UserRepository;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class CreateUserService {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateCompanyInExternalUserService updateCompanyInExternalUserService;

    public UserDTO run(CreateUserDTO createUserDTO) {
        throwIfUserAlreadyExists(createUserDTO.externalUserId());
        UserReturnDTO externalUser = updateCompanyInExternalUserService.run(createUserDTO);
        User userBuilt = buildUser(externalUser, createUserDTO);
        User userCreated = createUser(userBuilt);

        logger.info("Return created user...");
        return new UserDTO(
                userCreated.getId(),
                userCreated.getEmail(),
                userCreated.getCompanyId()
        );
    }

    private void throwIfUserAlreadyExists(String externalUserId) {
        User userAlreadyExists = userRepository.findByExternalId(externalUserId).orElse(null);
        if (userAlreadyExists != null) {
            logger.error(format("External User with id %s already exists", externalUserId));
            throw new BadRequestException("Usuário já existente.");
        }
    }

    private User buildUser(UserReturnDTO externalUser, CreateUserDTO dto) {
        logger.info("Building user...");
        User userToCreate = new User();
        userToCreate.setEmail(externalUser.getEmail());
        userToCreate.setExternalId(externalUser.getId());
        userToCreate.setCompanyId(dto.companyId());

        logger.info(format("User builded %s", userToCreate.toString()));
        return userToCreate;
    }

    private User createUser(User userToCreate) {
        logger.info("Saving user...");
        User userCreated = userRepository.save(userToCreate);
        logger.info("User saved");
        return userCreated;
    }
}
