ActiveAndroid的使用说明：  
1.配置:
 project下的build.gradle中操作如下：

    allprojects {
       repositories {
           jcenter()
             mavenCentral()
             maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
     }
 }

 Module下的build.gradle中添加如下语句:

    compile 'com.michaelpardo:activeandroid:3.1.0-SNAPSHOT'

2.编写代码：
  1>创建数据库：
     <1>.自定义Application,在AndroidManifest.xml文件中声明：
     <application
             android:name=".ActiveApplication"
             ........>
     </Application>
     自定义Application内容如下:

     public class ActiveApplication  extends Application {
         @Override
         public void onCreate() {
             super.onCreate();
             ActiveAndroid.initialize(this);
         }

         @Override
         public void onTerminate() {
             super.onTerminate();
             ActiveAndroid.dispose();
         }
     }

   <2>默认创建的数据库名称为Application.db，需要修改数据库名称以及进行版本管理需要在清单文件中添加如下节点

      <meta-data android:name="AA_DB_NAME" android:value="sky.db" />
      <meta-data android:name="AA_DB_VERSION" android:value="1" />
  2>创建表:
     <1>先创建实体类:
      示例如下:
        //创建表(其中@开头的表示注解)

        @Table(name = "user",id = "_id")//name:表名,id是主键
        public class User extends Model {
            @Column//表示是表的一个字段
            String name;
            @Column//表示是表的一个字段
            int age;

            public User() {
            }

            public User(String name, int age) {
                this.name = name;
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            @Override
            public String toString() {
                return "User{" +
                        "age='" + age + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

  3>进行数据库中表的增删查改操作:
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
  //查询所有

        public void queryAll(View view) {
            List<User> userList = new Select().from(User.class).execute();
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                Log.e("=====","==query==="+user.toString()+"==id=="+user.getId());

            }
        }
 //查询单条数据

        public void querySingleData(View view) {
            User user = new Select().from(User.class).where("age = ?",30).executeSingle();
            Log.e("=====","==query==="+user.toString());

        }
 //类似分页加载

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
  //删除单条数据有2种

        //第一种方式
        public void deleteDataByOne(View view) {
            User item = User.load(User.class, 1);
            item.delete();
        }
        //第二种方式
        public void deleteOneByTwo(View view) {
             User.delete(User.class, 1);

        }
