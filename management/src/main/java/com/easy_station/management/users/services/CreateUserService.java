package com.easy_station.management.users.services;

import com.easy_station.management.exceptions.BadRequestException;
import com.easy_station.management.grpc.SSOClientService;
import com.easy_station.management.grpc.dto.UserReturnDTO;
import com.easy_station.management.users.database.User;
import com.easy_station.management.users.database.UserRepository;
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
    private SSOClientService ssoClientService;

    public UserDTO run(CreateUserDTO createUserDTO) {
        UserReturnDTO externalUser = getUserInSSO(createUserDTO);
        throwIfUserAlreadyExists(externalUser);
        User userBuilt = buildUser(externalUser, createUserDTO);
        User userCreated = createUser(userBuilt);

        logger.info("Return created user...");
        return new UserDTO(
                userCreated.getId(),
                userCreated.getEmail(),
                userCreated.getCompanyId()
        );
    }

    private UserReturnDTO getUserInSSO(CreateUserDTO createUserDTO) {
        logger.info(format("Getting user with id %s...", createUserDTO.externalUserId()));
        UserReturnDTO externalUser = ssoClientService.findById(createUserDTO.externalUserId());
        logger.info("User obtained");

        return externalUser;
    }

    private void throwIfUserAlreadyExists(UserReturnDTO externalUser) {
        User userAlreadyExists = userRepository.findByExternalId(externalUser.getId()).orElse(null);
        if (userAlreadyExists != null) {
            logger.error(format("External User with id %s already exists", externalUser.getId()));
            throw new BadRequestException(format("Usuário já existente."));
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
        System.out.println("Saving user...");
        User userCreated = userRepository.save(userToCreate);
        System.out.println("User saved");
        return userCreated;
    }
}
