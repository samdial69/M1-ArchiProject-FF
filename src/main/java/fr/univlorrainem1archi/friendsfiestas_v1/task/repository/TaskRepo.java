package fr.univlorrainem1archi.friendsfiestas_v1.task.repository;

import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long> {
}
