package com.tang.newcloud.service.chat.controller.api;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.util.JwtInfo;
import com.tang.newcloud.common.base.util.JwtUtils;
import com.tang.newcloud.service.chat.entity.ChatGroup;
import com.tang.newcloud.service.chat.service.ChatGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: NewCloud
 * @description: 群组
 * @author: tanglei
 * @create: 2023-02-09 22:52
 **/
@RestController
@RequestMapping("api/chat/group")
@Api(tags = "群组管理")
public class ApiChatGroupController {

    @Autowired
    private ChatGroupService chatGroupService;

    //创建群组
    @ApiOperation("创建群组")
    @PostMapping("auth/create")
    public R createGroup(@ApiParam(value = "chatgroup对象",required = true)
                         @RequestBody ChatGroup chatGroup, HttpServletRequest request){

        JwtInfo token = JwtUtils.getMemberIdByJwtToken(request);
        String userId = token.getId();
        ChatGroup mygroup = chatGroupService.saveGroup(userId,chatGroup);
        return mygroup!=null?R.ok().data("mygroup",mygroup).message("插入成功"):R.error().message("插入失败");
    }
    //删除群组
    @ApiOperation("根据id解散群组")
    @DeleteMapping("auth/delete/{groupId}")
    public R remove(@ApiParam(value = "群组id",required = true)@PathVariable String groupId,
                    @ApiParam(value = "群组id",required = true)@RequestParam String masterId,
                    HttpServletRequest request){
        JwtInfo jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String userId = jwtToken.getId();
        if(userId!=masterId){
            return R.error().message("你没有权限");
        }
        int i = chatGroupService.deleteGroup(userId,groupId);
        return i>0?R.ok().message("删除群组成功"):R.error().message("删除失败,请稍后再试");
    }
    //修改群组
    @ApiOperation("修改群组")
    @PutMapping("auth/update")
    public R changeGroup(@ApiParam(value = "群组对象",required = true)@RequestBody ChatGroup chatGroup,
                         HttpServletRequest request){
        JwtInfo jwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String userId = jwtToken.getId();
        if(userId!=chatGroup.getGroupMasterId()){
            return R.error().message("你没有权限");
        }
        ChatGroup mygroup = chatGroupService.changeGroup(chatGroup);
        return mygroup!=null?R.ok().data("mygroup",mygroup).message("插入成功"):R.error().message("插入失败");
    }
    //查看群组
    @ApiOperation("根据id查看群组")
    @GetMapping("/get/{groupId}")
    public R readGroup(@ApiParam(value = "群组id")@PathVariable("groupId") String groupId){
        ChatGroup chatGroup = chatGroupService.getGroup(groupId);
        return R.ok().data("chatGroup",chatGroup);
    }
}
