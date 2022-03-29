package com.koval.spring.service;

import com.koval.spring.constant.ConstantClass;
import com.koval.spring.entity.UserEntity;
import com.koval.spring.dao.HibernateUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;

@Component("userDetailsService")
public class UserDetailService implements UserDetailsService {
    private final HibernateUserDao hibernateUserDao;

    @Autowired
    public UserDetailService(HibernateUserDao hibernateUserDao) {
        this.hibernateUserDao = hibernateUserDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = hibernateUserDao.findByLogin(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
        return new User(userEntity.getLogin(), userEntity.getPassword(), mapRolesToAuthorities(userEntity));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserEntity userEntity) {
        return Collections.singleton(new SimpleGrantedAuthority(ConstantClass.AUTHENTICATION_ROLE_SUFFIX + userEntity.getRoleEntity()
                .getName().toUpperCase()));
    }
}