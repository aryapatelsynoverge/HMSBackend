package com.system.audit;




import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


public class EntityAuditorAware implements AuditorAware<String> {

public static String sessionEmail;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(sessionEmail);
    }

}






