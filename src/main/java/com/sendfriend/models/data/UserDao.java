package com.sendfriend.models.data;

import com.sendfriend.models.Beta;
import com.sendfriend.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int userId);
}