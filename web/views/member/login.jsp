<%--<!DOCTYPE html>--%>
<%--要改成jsp文件头--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>韩顺平教育-家居网购</title>
<!--    有了base 就要改下面所有的href-->
<%--    用jsp的写法--%>
    <base href="<%=request.getContextPath()+"/" %>"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
<!--    ctrl+home最上面-->
<!--    ctrl+end最上面-->
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function (){
            // ajax查询用户名是否存在
            $('#username').blur(function() {
                // alert("...")
                var username = $(this).val();
                // 这个{}类似url的参数 只不过这里这么写
                // 类似action=checkUsername&username=username
                $.getJSON("memberServlet",{"action":"isExistUserName","username":username},function (data){
                    //这里是后端写的属性 前端看console.log(data) 然后知道这个属性
                    console.log(data.existsUsername)
                    // alert(data);
                    if(data.existsUsername){
                        $(".errorMsg").html("用户名已存在");
                        // return false;
                    }else{
                        $(".errorMsg").html("用户名可以使用");
                    }
                })
            })
            //ajax查询验证码是否正确 我自己写的
            $("#code").blur(function (){
                var codeText = $("#code").val();
                codeText = $.trim(codeText);
                $.getJSON("memberServlet",{"action":"verificationCode","code":codeText},function (data){
                    console.log(data)
                    if(!data.result){
                        $(".errorMsg").html("验证码错误");
                    }else{
                        $(".errorMsg").html("验证码正确");
                    }
                })
            })
                // 模拟一个点击事件
            if("${requestScope.active}"=="register"){
                $("#register_tab")[0].click();
            }
            //id绑定 #id
            $("#sub-btn").click(function (){
                var username = $("#username").val();
                // alert(username);
                var usernamePattern = /^\w{6,10}$/;
                if(!usernamePattern.test(username)){
                    //类选择器 。class
                    $(".errorMsg").html("用户名必须是6-10位的字母数字下划线");
                    //return false 拒绝跳转
                    return false;
                }
                //检验密码格式
                var password = $("#password").val();
                var passwordPattern = /^\w{6,10}$/;
                if (!passwordPattern.test(password)){
                    $(".errorMsg").html("密码必须是6-10位的字母数字下划线");
                    return false;
                }
                //完成对密码的校验
                var repwd = $("#repwd").val();
                if(repwd != password){
                    $(".errorMsg").html("两次密码不一致");
                    return false;
                }
                //完成对邮箱的校验
                var email = $("#email").val();
                var emailPattern = /^\w+@\w+(\.\w+)+$/;
                if(!emailPattern.test(email)){
                    $(".errorMsg").html("邮箱格式不正确");
                    return false;
                }
                //完成对验证码的校验
                var codeText = $("#code").val();
                codeText = $.trim(codeText);
                if(codeText==null|| codeText==""){
                    $("span.errorMsg").html("验证码不能为空");
                    return false;
                }
                $("span.errorMsg").html("通过");
                //这里记得要改成true
                // 当验证通过的时候将信息传给后台
                return true;
            })
            //点击图片切换验证码
            $("#codeImg").click(function (){
                // alert("点击了图片");
                // 为什么要加上math.random()?
                // 因为浏览器会缓存图片，如果不加上随机数，那么图片就会被缓存，不会重新加载
                // 或者你加上个new Date()也可以
                this.src = "kaptchaServlet?d="+new Date()
                // $(this).attr("src","kaptchaServlet?"+Math.random());
            })
        })
    </script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="../../assets/images/logo/logo.png" alt="Site Logo" /></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Header Area End  -->
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a  id="register_tab" data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
<%--                                    在这里提示错误信息--%>
                                    <span style="font-size: 18pt;font-weight: bold;float: right;color: red;" >${requestScope.msg}</span>
<%--                                    ${requestScope.msg}--%>
                                    <form action="/shopping/memberServlet" method="post">
<%--                                        el表达式--%>
<%--                                        隐藏的login 表达--%>
                                        <input type="hidden" name = "action" value="login">
                                        <input type="text" name="username" value = "${requestScope.username}" placeholder="Username"/>
                                        <input type="password" name="password" placeholder="Password"/>
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button type="submit"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span style="font-size: 18pt;font-weight: bold;float: right;color: red;" class="errorMsg">${requestScope.msg}</span>
<!--                                    注册表单    -->
                                    <form action="/shopping/memberServlet" method="post">
<%--                                        隐藏的 register表示--%>
                                        <input type="hidden" name = "action" value="register">
                                        <input type="text" id="username" name="username" placeholder="Username" value="${requestScope.username}"/>
                                        <input type="password" id="password" name="password" placeholder="输入密码" />
                                        <input type="password" id="repwd" name="repassword" placeholder="确认密码"/>
                                        <input name="email" id="email" placeholder="电子邮件" type="email" value="${requestScope.email}"/>
                                        <input type="text" id="code" name="code" style="width: 50%"
                                               placeholder="验证码"/>　　<img alt="" id ="codeImg" src="kaptchaServlet">
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a></li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.jsp">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 韩顺平教育~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>