package fr.univlorrainem1archi.friendsfiestas_v1.task.services;

import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;

import java.util.List;

public interface ITaskService {
    List<Task> getTasks();
    Task getTask(Long id);
    Task create(RequestBodyTask task);
    Task update(Long id,RequestBodyTask task);
    boolean delete(Long id);

}
