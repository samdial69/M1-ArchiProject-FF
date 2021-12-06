package fr.univlorrainem1archi.friendsfiestas_v1.task.services;

import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;

import java.util.List;

public interface ITaskService {
    List<Task> getTasks();
    Task getTask(Long id);
    Task create(Task task);
    Task update(Long id, Task task);
    boolean delete(Long id);

}
