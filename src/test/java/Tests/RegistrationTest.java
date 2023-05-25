package Tests;

import Config.BaseClass;
import Config.Constants;
import DTO.UserDTO;
import methods.*;
import org.testng.annotations.Test;
import java.io.IOException;


public class RegistrationTest extends BaseClass {


    @Test
    public void RegistrationAPI() throws IOException {
        UserDTO dto = new UserDTO();
        ApiRequests requests = new ApiRequests(driver);
        requests.generateRandomEmailForTest();
        requests.performRegistration(dto.getEmail());
        requests.retrieveVerificationEmail();
        String verificationLink = requests.extractVerificationLink(dto.getRegistrationMail());
        System.out.println(Constants.VERIFICATION_LINK + verificationLink);
        if (verificationLink != null) {
            driver.get(verificationLink);
        } else {
            System.out.println(Constants.VERIFICATION_LINK_NOT_FOUND);
        }
    }


}


