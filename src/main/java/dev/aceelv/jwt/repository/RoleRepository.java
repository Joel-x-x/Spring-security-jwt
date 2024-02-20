package dev.aceelv.jwt.repository;

import dev.aceelv.jwt.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

}
