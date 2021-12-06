package fr.univlorrainem1archi.friendsfiestas_v1.task.services;

import fr.univlorrainem1archi.friendsfiestas_v1.member.model.Member;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.TaskDTO;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.TaskMapper;
import fr.univlorrainem1archi.friendsfiestas_v1.task.repository.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TaskService implements ITaskService {

    private final TaskRepo taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepo taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> getTasks() {
        log.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    @Override
    public Task getTask(Long id) {
        log.info("Fetching the task id :{}", id);
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public Task create(Task task) {
        log.info("Saving new task :{}", task.getDescription());
        TaskDTO taskDTO = taskMapper.to(task);
        return taskRepository.save(this.taskMapper.to(taskDTO));
    }

    @Override
    public Task update(Long id, Task task) {
        log.info("Update the task id: {}", id);
        if (!existById(id)){
            throw new IllegalArgumentException("Not task found by id "+id);
        }
        //currentTask.setDescription(task.getDescription());
        return taskRepository.save(task);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting task by id {}:", id);
        if (!existById(id)) {
            throw new IllegalArgumentException("No task found by id:" + id);
        }
        taskRepository.deleteById(id);
        return true;
    }

    public Task convert(RequestBodyTask task){
        TaskDTO taskDTO = taskMapper.to(task);
        return this.taskMapper.to(taskDTO);
    }

    public RequestBodyTask convertTo(Task task){
        RequestBodyTask taskBody = new RequestBodyTask();
        taskBody.setDescription(task.getDescription());
        return taskBody;
    }

    public boolean existById(Long id) {
        return taskRepository.existsById(id);
    }
}
