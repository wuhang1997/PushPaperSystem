package cn.zjut.wangjie.pushpaper.sql;

import cn.zjut.wangjie.pushpaper.pojo.User;
import lombok.extern.java.Log;
import org.apache.ibatis.jdbc.SQL;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-03 16:45
 **/
@Log
public class UserSQLFactory {
    public  String addUserSQL(User user){
        SQL sql = new SQL(){
            {
                INSERT_INTO("user");
                VALUES("name","#{name}");
                VALUES("email","#{email}");
                VALUES("password","#{password}");
            }
        };
        log.info("\nsql----------------------\n"+sql.toString());
        return sql.toString();
    }

    public  String findUserSQL(User user){
        SQL sql = new SQL(){
            {
                SELECT("*");
                FROM("user");
                if (user.getUid()!=null){
                    WHERE("uid="+user.getUid());
                }
                if (user.getEmail()!= null){
                    WHERE("email='"+user.getEmail()+"'");
                }
                if (user.getPassword()!=null){
                    WHERE("password='"+user.getPassword()+"'");
                }
            }
        };
        log.info("\nsql----------------------\n"+sql.toString());
        return sql.toString();
    }
}
