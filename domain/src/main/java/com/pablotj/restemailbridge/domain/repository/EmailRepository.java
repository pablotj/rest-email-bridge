package com.pablotj.restemailbridge.domain.repository;

import com.pablotj.restemailbridge.domain.model.Email;

public interface EmailRepository {

    Email save(Email email);
}
