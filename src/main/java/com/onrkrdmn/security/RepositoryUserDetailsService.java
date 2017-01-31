package com.onrkrdmn.security;

import com.onrkrdmn.domain.Player;
import com.onrkrdmn.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by onur on 28.01.17.
 * implementation of {@link UserDetailsService}
 */
@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> playerOptional = playerRepository.findByUserName(username);

        if (playerOptional.isPresent()) {
            return new UserDetailsDecorator(playerOptional.get());
        }

        throw new UsernameNotFoundException(username);
    }

}
