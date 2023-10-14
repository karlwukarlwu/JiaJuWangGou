package uploadAndDownLoad;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 获取字段名和获取字段值
 *
 是的，这两个方法有区别，并且它们用于不同的目的。让我们详细地看看这两个方法：

 fileItem.getFieldName():

 这个方法返回FileItem对象的字段名，也就是在HTTP表单中<input>标签的name属性值。
 示例：对于表单字段<input type="text" name="username">，getFieldName()方法将返回"username"。
 fileItem.getString("utf-8"):

 这个方法用于获取FileItem对象的值，并将其转化为一个字符串，使用指定的字符编码（在此例中是"utf-8"）。
 对于一个普通的文本字段，这将返回该字段的值。但对于一个文件上传字段，这会返回文件的内容作为一个字符串（通常，获取文件内容使用其他方法更为合适，如getInputStream()）。
 示例：对于表单字段<input type="text" name="username" value="JohnDoe">，getString("utf-8")方法将返回"JohnDoe"。
 总结：

 getFieldName()用于获取字段的名称。
 getString("utf-8")用于获取字段的值，并将其转化为一个使用指定编码的字符串。
 在处理文件上传和表单数据时，通常需要两者结合使用，首先通过getFieldName()判断正在处理哪个字段，然后使用getString("utf-8")或其他相应的方法来处理该字段的值。





 */
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("FileUploadServlet 被调用...");

        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (ServletFileUpload.isMultipartContent(request)) {
            //System.out.println("OK");
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //3. 创建一个解析上传数据的工具对象
            /**
             *     表单提交的数据就是 input 元素
             *     <input type="file" name="pic" id="" value="2xxx.jpg" onchange="prev(this)"/>
             *     家居名: <input type="text" name="name"><br/>
             *     <input type="submit" value="上传"/>
             */
            ServletFileUpload servletFileUpload =
                    new ServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            servletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            //   老师的编程心得体会: 如果我们不知道一个对象是什么结构[1.输出 2.debug 3. 底层自动看到]
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //文件实际上已经给你上传好了，你需要自定义你自己要的目录
                /*
                list==>

                [name=3.jpg, StoreLocation=D:\hspedu_javaweb\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\temp\xupload__7e34374f_17fce4168b1__7f4b_00000000.tmp, size=106398bytes, isFormField=false, FieldName=pic,
                name=null, StoreLocation=D:\hspedu_javaweb\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\temp\xupload__7e34374f_17fce4168b1__7f4b_00000001.tmp, size=6bytes, isFormField=true, FieldName=name]

                 */
                //System.out.println("list==>" + list);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //System.out.println("fileItem=" + fileItem);
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text
                        String name = fileItem.getString("utf-8");
                        //这个是你自己网页输入的名字
                        System.out.println("家具名=" + name);
                    } else {//是一个文件

                        //用一个方法
                        //获取上传的文件的名字
                        String name = fileItem.getName();
                        System.out.println("上传的文件名=" + name);

                        //把这个上传到 服务器的 temp下的文件保存到你指定的目录
                        //1.指定一个目录 , 就是我们网站工作目录下
                        String filePath = "/upload/";
                        //2. 获取到完整目录 [io/servlet基础]
                        //  这个目录是和你的web项目运行环境绑定的. 是动态.
                        //在out那里 不是在我们的src里面
                        //fileRealPath=D:\hspedu_javaweb\fileupdown\out\artifacts\fileupdown_war_exploded\xupload\
                        String fileRealPath =
                                request.getServletContext().getRealPath(filePath);
                        System.out.println("fileRealPath=" + fileRealPath);

                        //3. 创建这个上传的目录=> 创建目录?=> Java基础
                        //   老师思路; 我们也一个工具类，可以返回 /2024/11/11 字符串
                        //为什么要用日期分页 因为上传文件，如果都放在一个目录下，会导致目录下的文件太多，不好管理
                        //自定义一个webutils 工具类,实现分页功能
                        File fileRealPathDirectory = new File(fileRealPath + WebUtils2.getYearMonthDay());
                        if (!fileRealPathDirectory.exists()) {//不存在，就创建
                            fileRealPathDirectory.mkdirs();//创建
                        }

                        //4. 将文件拷贝到fileRealPathDirectory目录
                        //   构建一个上传文件的完整路径 ：目录+文件名
                        //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                        // 为什么要加 UUID.randomUUID().toString() 因为如果传入同名的文件 会覆盖，用uuid 保证唯一
                        // System.currentTimeMillis() 你要是害怕uuid也重复 可以加上这个
                        name = UUID.randomUUID().toString() + "_" +System.currentTimeMillis() + "_" + name;
                        String fileFullPath = fileRealPathDirectory + "/" +name;
                        fileItem.write(new File(fileFullPath));

                        //5. 提示信息
                        response.setContentType("text/html;charset=utf-8");
                        response.getWriter().write("上传成功~");


                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
