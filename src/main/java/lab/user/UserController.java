package lab.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDaoImpl userDao;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<HashMap> create(@RequestBody UserMaster user) {
        HashMap resp=new HashMap();
        if (user != null) {
            resp=userDao.create(user);
        }else resp.put("message","Input data incorrect");

        return new ResponseEntity<HashMap>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseEntity<HashMap> find(@RequestBody UserMaster user) {

        if (user != null) {
            List res=userDao.findUserByIdAndPassword(user.getUser_name(), user.getPassword());
            if(res!=null&&res.size()>0) user=(UserMaster) res.get(0);
            else user=null;
        }
        HashMap resp = new HashMap();
        resp.put("user",user);
        resp.put("response message",user!=null?"User exists":"User and/or password are incorrect");
        return new ResponseEntity<HashMap>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ResponseEntity<List<UserMaster>> findAll() {

        List<UserMaster> lst=   userDao.findAll();


        return new ResponseEntity<List<UserMaster>>(lst, HttpStatus.OK);
    }
}
