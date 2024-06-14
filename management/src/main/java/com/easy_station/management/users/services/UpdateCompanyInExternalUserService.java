package com.easy_station.management.users.services;

import com.easy_station.management.companies.dto.CompanyDTO;
import com.easy_station.management.companies.services.FindOneCompanyService;
import com.easy_station.management.grpc.SSOClientService;
import com.easy_station.management.grpc.dto.UserReturnDTO;
import com.easy_station.management.users.dto.CreateUserDTO;
import com.easy_station.management.users.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCompanyInExternalUserService {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCompanyInExternalUserService.class.getName());

    @Autowired
    private SSOClientService ssoClientService;

    @Autowired
    private FindOneCompanyService findOneCompanyService;

    public UserReturnDTO run(CreateUserDTO createUserDTO) {
        CompanyDTO companyDTO = getCompany(createUserDTO.companyId());
        UserDTO userToUpdate = buildUserToUpdate(createUserDTO.externalUserId(), companyDTO);
        UserReturnDTO externalUser = updateExternalUser(userToUpdate);

        logger.info("Return user...");
        return externalUser;
    }

    public CompanyDTO getCompany(String companyId) {
        logger.info("Calling findOneCompanyService for get company...");
        CompanyDTO companyDTO = findOneCompanyService.run(companyId);
        logger.info("Company found");
        return companyDTO;
    }

    public UserDTO buildUserToUpdate(String externalUserId, CompanyDTO companyDTO) {
        logger.info("Create user to update...");
        UserDTO userToUpdate = new UserDTO();
        userToUpdate.setExternalId(externalUserId);
        userToUpdate.setCompany(companyDTO);

        return userToUpdate;
    }

    public UserReturnDTO updateExternalUser(UserDTO userToUpdate) {
        logger.info("Updating user...");
        UserReturnDTO externalUser = ssoClientService.update(userToUpdate);
        logger.info("User updated");
        return externalUser;
    }

}
