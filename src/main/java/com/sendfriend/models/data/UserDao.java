package com.sendfriend.models.data;

import com.sendfriend.models.Beta;
import com.sendfriend.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findById(int userId);
    User findOne(Integer userId);

    @Transactional
    @Query("SELECT u " +
            "FROM User u " +
            "JOIN FETCH u.betas")
    List<Beta> getAllUsersWithBeta();

    @Transactional
    @Query("SELECT u FROM User u JOIN FETCH u.betas WHERE u.username = :username")
    Set<Beta> getUserBetaByUsername(@Param("username") String username);

    @Transactional
    @Query("SELECT u FROM User u JOIN FETCH u.betas WHERE u.id = :id")
    Set<Beta> getUserBetaByUserId(@Param("id") String id);

    @Transactional
    @Query("SELECT u FROM User u JOIN FETCH u.friends WHERE u.username = :username")
    Set<User> getUserFriendsByUsername(@Param("username") String username);

    @Transactional
    @Query("SELECT u FROM User u JOIN FETCH u.friends WHERE u.id = :id")
    Set<User> getUserFriendsByUserId(@Param("id") String id);
}

