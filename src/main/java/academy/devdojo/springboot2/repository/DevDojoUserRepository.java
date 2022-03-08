package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.DevDojoUser;
import academy.devdojo.springboot2.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {

    List<DevDojoUser> findByUsername(String username);

}
