package ru.tchallenge.service.kernel.generic;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ru.tchallenge.service.kernel.domain.account.AccountInfo;
import ru.tchallenge.service.kernel.generic.entity.EnumeratedEntity;
import ru.tchallenge.service.kernel.generic.repository.EnumeratedEntityRepository;
import ru.tchallenge.service.kernel.security.authentication.AuthenticationInfo;
import ru.tchallenge.service.kernel.validation.access.AccessValidationExceptionProvider;

public abstract class EnumeratedEntityFacade<T extends EnumeratedEntity> extends GenericFacade {

    @Autowired
    private AccessValidationExceptionProvider accessValidationExceptionProvider;

    public Collection<EnumeratedInfo> getAll() {
        authenticated();
        return getRepository()
                .findAll()
                .stream()
                .map(getMapper()::asInfo)
                .sorted()
                .collect(Collectors.toList());
    }

    protected abstract EnumeratedEntityMapper<T> getMapper();

    protected abstract EnumeratedEntityRepository<T> getRepository();

    private AccountInfo authenticated() {
        final AuthenticationInfo authentication = getAuthenticationContext().getAuthentication();
        if (authentication == null) {
            accessValidationExceptionProvider.unauthorized();
        }
        final AccountInfo account = authentication.getAccount();
        if (account == null) {
            accessValidationExceptionProvider.unauthorized();
        }
        return account;
    }
}
