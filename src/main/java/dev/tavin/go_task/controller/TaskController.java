package dev.tavin.go_task.controller;

import dev.tavin.go_task.infra.dto.task.TaskRequestDto;
import dev.tavin.go_task.infra.dto.task.TaskResponseDto;
import dev.tavin.go_task.infra.dto.task.TaskUpdateDto;
import dev.tavin.go_task.infra.dto.task.TaskUpdateStatusDto;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto req, @AuthenticationPrincipal User user){
        return new ResponseEntity<>(taskService.createTask(user.getId(), req), HttpStatus.CREATED );
    }

    @GetMapping()
    public ResponseEntity<List<TaskResponseDto>> getAllTasksByUser(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(taskService.findMyTasks(user.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTasksById(@PathVariable UUID id,@AuthenticationPrincipal User user){
        return new ResponseEntity<>(taskService.getTaskById(user.getId(), id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@Valid @RequestBody TaskUpdateDto req, @PathVariable UUID id, @AuthenticationPrincipal User user){
        return new ResponseEntity<>(taskService.updateTaskById(user.getId(), id, req), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(@PathVariable UUID id,@Valid @RequestBody TaskUpdateStatusDto status, @AuthenticationPrincipal User user){
        return new ResponseEntity<>(taskService.updateStatusTask(user.getId(), id, status), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteTask(@PathVariable UUID id, @AuthenticationPrincipal User user){
        taskService.deleteTaskById(user.getId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
