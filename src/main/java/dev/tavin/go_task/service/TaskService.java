package dev.tavin.go_task.service;

import dev.tavin.go_task.infra.dto.task.TaskRequestDto;
import dev.tavin.go_task.infra.dto.task.TaskResponseDto;
import dev.tavin.go_task.infra.dto.task.TaskUpdateStatusDto;
import dev.tavin.go_task.infra.dto.task.TaskUpdateDto;
import dev.tavin.go_task.infra.entity.Task;
import dev.tavin.go_task.infra.entity.User;
import dev.tavin.go_task.infra.entity.enums.Status;
import dev.tavin.go_task.infra.repository.TaskRepository;
import dev.tavin.go_task.infra.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,  UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponseDto createTask(UUID userId, TaskRequestDto  req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setUser(user);
        taskRepository.save(task);

        return new TaskResponseDto(task.getTitle(), task.getDescription(), task.getStatusTask(), task.getId());
    }

    public TaskResponseDto getTaskById(UUID userId, UUID taskId) {
        Task task = taskRepository
                .findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return new TaskResponseDto(task.getTitle(), task.getDescription(), task.getStatusTask(), task.getId());
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> findMyTasks(UUID userId) {
        return taskRepository.findAllTaskByUserId(userId)
                .stream()
                .map(task -> new TaskResponseDto(
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatusTask(),
                        task.getId()
                ))
                .toList();
    }

    public void deleteTaskById(UUID userId, UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found!"));

        if (!task.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot access this task!");
        }

        taskRepository.delete(task);
    }

    public TaskResponseDto updateTaskById(UUID userId ,UUID taskId, TaskUpdateDto req) {
        Task taskModel = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        Optional.ofNullable(req.description())
                .ifPresent(taskModel::setDescription);

        Optional.ofNullable(req.title())
                .ifPresent(taskModel::setTitle);

        taskRepository.save(taskModel);
        return new TaskResponseDto(taskModel.getTitle(), taskModel.getDescription(), taskModel.getStatusTask(), taskModel.getId());
    }

    public TaskResponseDto updateStatusTask(UUID userId , UUID taskId, TaskUpdateStatusDto status){
        Task taskModel = taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found!"));

        if (taskModel.getStatusTask() == Status.DONE) {
            throw new IllegalArgumentException("Completed tasks cannot be modified");
        }

        taskModel.setStatusTask(status.status());

        taskRepository.save(taskModel);

        return new TaskResponseDto(taskModel.getTitle(), taskModel.getDescription(), taskModel.getStatusTask(), taskModel.getId());
    }

}
