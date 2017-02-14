package com.sky.sky.activeandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.sky.sky.activeandroiddemo.bean.User;

import java.util.List;
//官网：https://github.com/pardom/ActiveAndroid
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //单条插入
    public void insert(View view) {
        User user = new User();
        user.setName("哈哈");
        user.setAge(20);
        user.save();
    }
    //批量插入
    public void batchInsert(View view) {
        //事务处理，效率高
        ActiveAndroid.beginTransaction();
        for (int i = 0; i < 10; i++) {
            User user1 = new User();
            user1.setName("a===" + i);
            user1.setAge(21 + i);
            user1.save();
        }

        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();

    }

    public void queryAll(View view) {
        //查询所有
        List<User> userList = new Select().from(User.class).execute();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Log.e("=====","==query==="+user.toString()+"==id=="+user.getId());

        }
    }
    public void querySingleData(View view) {
        //查询单条数据
        User user = new Select().from(User.class).where("age = ?",30).executeSingle();
        Log.e("=====","==query==="+user.toString());

    }
    public void queryByPage(View view) {
        //类似分页加载:limit(int n).offset(int start),从第start开始查询n条数据
        List<User> userList = new Select().from(User.class).limit(5).offset(3).execute();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Log.e("=====","==query==="+user.toString());
        }
    }
    //模糊查询
    public void queryByLike(View view) {
        List<User> userList = new Select().from(User.class).where("name like '%a%'")
                .orderBy("_id desc")
                .execute();
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Log.e("=====","==query==="+user.toString());
        }
    }
    //删除数据
    public void update(View view) {
        new Update(User.class).set("name = ?","呵呵").where("age = ?",30).execute();
    }
    //删除所有
    public void deleteAll(View view) {
        new Delete().from(User.class)
                .execute();
    }
    public void deleteDataByOne(View view) {
        User item = User.load(User.class, 1);
        item.delete();
    }
    public void deleteOneByTwo(View view) {
        //第二种方式
         User.delete(User.class, 1);

    }
}
