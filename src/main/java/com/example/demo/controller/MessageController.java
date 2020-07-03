package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.service.MessageService;
import com.example.demo.service.SpecUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "MessageController")
@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    private SpecUtil specUtil;

    @Autowired
    MessageController(MessageService messageService,SpecUtil specUtil) {
        this.messageService = messageService;
        this.specUtil = specUtil;
    }

    @ApiOperation(value = "get course message", notes = "get course message",httpMethod = "GET")
    @GetMapping("")
    public List<MessageEntity> getAll() {
        return messageService.getAll();
    }

    @ApiOperation(value = "get message list by phone and course id", notes = "get message list by phone and course id",httpMethod = "GET")
    @GetMapping("/phoneAndLessonId")
    public List<MessageEntity> getByPhoneAndLessonId(@RequestParam @ApiParam(value = "phone") String phone,
                                                     @RequestParam @ApiParam(value = "course_id")String lessonId){
        return messageService.getByPhoneAndLessonId(phone,lessonId);
    }

    @PreAuthorize("hasRole('student')")
    @ApiOperation(value = "get message list by phone", notes = "get message list by phone",httpMethod = "GET")
    @GetMapping("/phone")
    public List<MessageEntity> getByPhone(@RequestParam @ApiParam(value = "student phone") String phone){
        return messageService.getByPhone(phone);
    }

    @ApiOperation(value = "get message list by phone and course id", notes = "get message list by phone and course id",httpMethod = "GET")
    @GetMapping("/getPagesByPhoneAndLessonId")
    public Page<MessageEntity> getPagesByPhoneAndLessonId(@PageableDefault(size = 12, sort = {"time"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                             @RequestParam(value = "phone")@ApiParam(value = "phone") String phone,
                                             @RequestParam(value = "lessonId")@ApiParam(value = "course id") String lessonId) {
        Specification<MessageEntity> specification = specUtil.createSpecificationByPhoneAndLessonId(phone,lessonId);
        return messageService.getAll(specification,pageable);
    }

    @ApiOperation(value = "get message list by phone", notes = "get message list by phone",httpMethod = "GET")
    @GetMapping("/getPagesByPhone")
    public Page<MessageEntity> getPagesByPhone(@PageableDefault(size = 12, sort = {"time"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息") Pageable pageable,
                                               @RequestParam(value = "phone")@ApiParam(value = "phone") String phone) {
        Specification<MessageEntity> specification = specUtil.createSpecificationByPhone(phone);
        return messageService.getAll(specification,pageable);
    }

    @ApiOperation(value = "insert message into data", notes = "insert message into data",httpMethod = "POST")
    @ApiParam(name = "messageEntity",value = "cannot be null")
    @PostMapping("")
    public void insertMessage(@RequestBody MessageEntity messageEntity) {
        messageService.insertMessage(messageEntity);
    }

    @ApiOperation(value = "delet course message by stu id", notes = "delete course message by stu id",httpMethod = "DELETE")
    @ApiParam(name = "id",value = "student course messageid")
    @DeleteMapping("")
    public void deleteMessage(@RequestParam String id) {
        messageService.deleteMessage(id);
    }
}
