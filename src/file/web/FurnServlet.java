package file.web;

import file.entity.Furn;
import file.entity.Page;
import file.service.FurnService;
import file.service.imp.FurnServiceImp;
import file.utils.DataUtils;
import file.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import uploadAndDownLoad.WebUtils2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Karl Rules!
 * 2023/9/21
 * now File Encoding is UTF-8
 */
public class FurnServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImp();

    //同样是反射+动态绑定拿到对应的方法
    protected void list(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("list method is called");
        //调用service层的方法
        List<Furn> furns = furnService.queryFurns();
//将数据存到request域中
        request.setAttribute("furns", furns);
        //转发到jsp页面
        try {
            request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (Furn furn :
//                furns) {
//            {
//                System.out.println(furn);
//            }
//
//        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //request的多余传入的参数 会被BeanUtils.populate()方法忽略掉
        //会自动找需要的参数
        Furn furn = DataUtils.copyParameter(req.getParameterMap(), new Furn());
        furnService.addFurn(furn);
        try {
            //这里一定要手动改成list 不然会无线循环 因为action 一直没有被改成list 而是add
            // 为什么这里不用请求转发
//            因为使用请求转发 等于每次都重复执行一边add 然后再list
            //但是重定向 重新定位到list网页 这样每次刷新只是重新显示list网页
            //在第八章
//            req.getRequestDispatcher("/manage/furnServlet?action=list").forward(req, resp);
//            resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
            //改成跳转回原来页面
            resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo =" + req.getParameter("pageNo"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);

        furnService.deleteFurn(id);

//        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    //家具回显
    protected void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);
        req.setAttribute("furn", furn);

        req.setAttribute("pageNo", req.getParameter("pageNo"));


        System.out.println(furn);
        req.getRequestDispatcher("/views/manage/furn_update.jsp").forward(req, resp);
    }

    //家具修改 这个是不考虑改图片的版本
    protected void updateFurn01(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Furn furn = DataUtils.copyParameter(req.getParameterMap(), new Furn());
        furnService.updateFurnById(furn);
        System.out.println(furn);
//        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=list");
        resp.sendRedirect(req.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    //    家具修改 带上图片了更新上面的图片
    protected void updateFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        表单变成了multipart/form-data req.getParameter()方法就不好使了
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);
        //todo 这里的furn是空的怎么办

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
//                文本处理
                for (FileItem fileItem : list) {
                    //System.out.println("fileItem=" + fileItem);
                    //判断是不是一个文件
                    if (fileItem.isFormField()) {//如果是true就是文本 input text
//<input type="text" name="username">，getFieldName()方法将返回"username"。
                        if ("name".equals(fileItem.getFieldName())) {
//fileItem.getString 这个方法用于获取FileItem对象的值，并将其转化为一个字符串，
//示例：对于表单字段<input type="text" name="username" value="JohnDoe">，getString("utf-8")方法将返回"JohnDoe"
//                            value 是默认值 如果用户输入了则getString 获取的是用户输入的值
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {
                            furn.setMaker(fileItem.getString("utf-8"));
                        }else if("price".equals(fileItem.getFieldName())){
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            furn.setStock(new Integer(fileItem.getString()));
                        }
//                        图片处理
                    } else {//是一个文件

                        //用一个方法
                        //获取上传的文件的名字
                        String name = fileItem.getName();
//                        这里有问题 如果我们不改图片 这里每次修改就是null 会导致图片丢失
//                        解决办法是在这里判断 如果是null 就不改变图片
                        if (!(name == null || name.equals(""))) {
                            String preImgPath = furn.getImgPath();
                            System.out.println("preImgPath=" + preImgPath);
                            String fileRealPath01 =
                                    request.getServletContext().getRealPath(preImgPath);

                            File preFile = new File(fileRealPath01);
                            if (preFile.exists()) {
                                preFile.delete();
                                System.out.println("file deleted");
                            }else {
                                System.out.println("file not exist");
                            }

                            System.out.println("上传的文件名=" + name);

                            //把这个上传到 服务器的 temp下的文件保存到你指定的目录
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = WebUtils.UPLOAD_PATH;
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
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
//                      fileItem.write(new File(fileFullPath)); 这个方法的意思是将上传的文件内容写入到服务器的指定路径。这里的 new File(fileFullPath) 创建一个文件路径指向要保存上传文件的位置。
                            fileItem.write(new File(fileFullPath));
//                        fileItem.getOutputStream().close();
                            furn.setImgPath(filePath + WebUtils2.getYearMonthDay() + name);

//                            //5. 提示信息
//                            response.setContentType("text/html;charset=utf-8");
//                            response.getWriter().write("上传成功~");


                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        furnService.updateFurnById(furn);
        request.getRequestDispatcher("/views/manage/update_ok.jsp").forward(request, response);
    }

    //分页
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数 pageNo 和 pageSize pageNo和pageSize 从前端获取
        int pageNo = DataUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.调用FurnService.page(pageNo,pageSize):Page对象
        Page<Furn> page = furnService.page(pageNo, pageSize);
        System.out.println(page);
//        page.setUrl("manage/furnServlet?action=page");
        //3.保存Page对象到Request域中
        req.setAttribute("page", page);
        //4.请求转发到pages/manager/furn_manager.jsp页面
        //为什么要转发到这个页面？而且没有url 因为实际上跳转的url已经在前端设置好了
        //请求转发并不改变url
        //这里跳转回来的目的是回显数据 而真正的定向是在前端进行的数据拼接
        //http: localhost:8080/shopping/manage/furnServlet?action=page&pageNo=4
//       这个是前端写好的
        req.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(req, resp);
        // 这个只是显示单纯的页面 这个搭配上面的域对象才是完整的有数据的页面
    }
}
