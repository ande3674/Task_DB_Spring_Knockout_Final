package com.clara.taskdb.controller;

import com.clara.taskdb.model.Task;
import com.clara.taskdb.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskAPIController {

    private final TaskRepository tasks;

    // Constructor to set the Repository
    @Autowired
    public TaskAPIController(TaskRepository tasks){
        this.tasks = tasks;

        // Example tasks. Remove for real app
//        tasks.save(new Task("task 1", true, false));
//        tasks.save(new Task("task 2", true, true));
//        tasks.save(new Task("task 3", false, false));
//        tasks.save(new Task("task 4", false, false));
    }

    // Post route for adding a new task
    @PostMapping(value="/add")
    public ResponseEntity addTask(@RequestBody Task task){
        System.out.println("New task: " + task); // just for debugging
        try {
            tasks.save(task);
        } catch (Exception e) {
            return new ResponseEntity<>("Task object is invalid", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // Route for getting all the tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> queryTasks(){
        return new ResponseEntity<>(tasks.findAllByOrderByUrgentDesc(), HttpStatus.OK);
    }

    // PATCH route for updating task completed
    // The client sends the task to update
    // Update tasks, count number of task table rows updated
    @PatchMapping(value="/completed")
    public ResponseEntity markTaskAsCompleted(@RequestBody Task task){

        int tasksUpdated = tasks.setTaskCompleted(task.isCompleted(), task.getId());
        if (tasksUpdated == 0){
            return new ResponseEntity(HttpStatus.NOT_FOUND); // Error, task is not in DB
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT); //OK, no content needed in response
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteTask(@RequestBody Task task){
        tasks.delete(task);
        return new ResponseEntity(HttpStatus.OK);
    }
}