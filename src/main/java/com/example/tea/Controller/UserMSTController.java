package com.example.tea.Controller;


import com.example.tea.DTO.GenericResponse;
import com.example.tea.DTO.UserLoginRequest;
import com.example.tea.Model.UserMST;
import com.example.tea.Services.UserMSTService;
import com.example.tea.Utility.Constant.Constant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/userMST")
public class UserMSTController {

    @Autowired
    private UserMSTService userMSTService;

    @PostMapping(value = Constant.CREATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse create(@Valid @RequestBody UserMST userMST){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.create(userMST));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();;
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    /** Admin-only: create a user, honouring the admin flag (so an admin can add another admin). */
    @PostMapping(value = "/createAdmin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse createAdmin(@Valid @RequestBody UserMST userMST){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.createByAdmin(userMST));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    @PutMapping(value = Constant.UPDATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@Valid @RequestBody UserMST userMST){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.update(userMST));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();;
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll(){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.getAll(true));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();;
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    @GetMapping(value = Constant.GET_ALL_ACTIVE)
    public GenericResponse getAllActive(){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.getAll(false));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();;
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    @DeleteMapping(value = Constant.DELETE+"/{id}")
    public GenericResponse delete(@PathVariable("id") Long userMSTId) {
        GenericResponse genericResponse = new GenericResponse();
        try {
            genericResponse.setData(userMSTService.deleteById(userMSTId));
            genericResponse.setMessage("Successful");
            genericResponse.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setData(null);
        }
        return genericResponse;
    }
    @PostMapping(value = Constant.LOGIN,consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse login(@RequestBody UserLoginRequest userLoginRequest){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setData(userMSTService.login(userLoginRequest.getUsername(),userLoginRequest.getPassword()));
            genericResponse.setMessage("SuccessFul");
            genericResponse.setStatus(true);
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setStatus(false);
            genericResponse.setMessage(e.getMessage());
        }
        return genericResponse;
    }
}
