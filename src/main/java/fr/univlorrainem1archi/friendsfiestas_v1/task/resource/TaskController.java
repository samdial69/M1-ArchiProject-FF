package fr.univlorrainem1archi.friendsfiestas_v1.task.resource;

import fr.univlorrainem1archi.friendsfiestas_v1.common.Response;
import fr.univlorrainem1archi.friendsfiestas_v1.common.ResponseBuilder;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.RequestBodyTask;
import fr.univlorrainem1archi.friendsfiestas_v1.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/friends-fiestas/taches")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getTasks(){
        return new ResponseBuilder(HttpStatus.OK,"Retrieving all tasks","tasks",taskService.getTasks()).buildResponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getTask(@PathVariable("id") Long id){
        return new ResponseBuilder(HttpStatus.OK,"Retrieving a task","task",taskService.getTask(id)).buildResponse();
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Response> create(@RequestBody RequestBodyTask task){
        return new ResponseBuilder(HttpStatus.OK,"Created successfully!","task",taskService.create(task)).buildResponse();
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id,@RequestBody RequestBodyTask task){
        if (!taskService.existById(id)){
            return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
        return new ResponseBuilder(HttpStatus.OK,"Updated successfully!","task",taskService.update(id,task)).buildResponse();
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id){
        if (!taskService.existById(id)) {
            return (ResponseEntity<Response>) ResponseEntity.notFound();
        }
            return new ResponseBuilder(HttpStatus.OK,"Deleted successfully!","isDeleted",taskService.delete(id)).buildResponse();
    }
}
