package co.zero.health.util;

import co.zero.health.common.Constant;
import co.zero.health.model.Company;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    /**
     * Specific function that gets the companyID of the current logged user from the JWT
     * @return Identifier of the company for the current user
     */
    public static final Long getCompanyId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DecodedJWT jwtInfo = (DecodedJWT) authentication.getDetails();
        return jwtInfo.getClaim(Constant.CLAIM_COMPANY_ID).asLong();
    }

    /**
     * Usefult method that creates the company object with the information from JWT.
     * @return
     */
    public static final Company getCompany() {
        Long companyId = getCompanyId();
        return new Company(companyId);
    }
}
