package com.dimon.tododemo.service.impl;

import com.dimon.tododemo.domain.TodoList;
import com.dimon.tododemo.repository.TodoListRepository;
import com.dimon.tododemo.service.TodoListService;
import com.dimon.tododemo.service.dto.TodoListDTO;
import com.dimon.tododemo.service.mapper.TodoListMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TodoList}.
 */
@Service
@Transactional
public class TodoListServiceImpl implements TodoListService {

    private final Logger log = LoggerFactory.getLogger(TodoListServiceImpl.class);

    private final TodoListRepository todoListRepository;

    private final TodoListMapper todoListMapper;

    public TodoListServiceImpl(TodoListRepository todoListRepository, TodoListMapper todoListMapper) {
        this.todoListRepository = todoListRepository;
        this.todoListMapper = todoListMapper;
    }

    @Override
    public TodoListDTO save(TodoListDTO todoListDTO) {
        log.debug("Request to save TodoList : {}", todoListDTO);
        TodoList todoList = todoListMapper.toEntity(todoListDTO);
        todoList = todoListRepository.save(todoList);
        return todoListMapper.toDto(todoList);
    }

    @Override
    public Optional<TodoListDTO> partialUpdate(TodoListDTO todoListDTO) {
        log.debug("Request to partially update TodoList : {}", todoListDTO);

        return todoListRepository
            .findById(todoListDTO.getId())
            .map(
                existingTodoList -> {
                    todoListMapper.partialUpdate(existingTodoList, todoListDTO);

                    return existingTodoList;
                }
            )
            .map(todoListRepository::save)
            .map(todoListMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoListDTO> findAll() {
        log.debug("Request to get all TodoLists");
        return todoListRepository.findAll().stream().map(todoListMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TodoListDTO> findOne(Long id) {
        log.debug("Request to get TodoList : {}", id);
        return todoListRepository.findById(id).map(todoListMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TodoList : {}", id);
        todoListRepository.deleteById(id);
    }
}
