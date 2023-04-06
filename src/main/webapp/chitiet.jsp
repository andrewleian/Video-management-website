<%--
  Created by IntelliJ IDEA.
  User: yenbo
  Date: 4/1/2023
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="css/main.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>

    <style>
        header{
            background-color: #ffc603;
        }
        header ul{
            list-style: none;
            font-weight: 400;
        }
        header li{
            padding: 10px 20px  10px  20px;
            color: blue;
            font-size: 20px;
            font-weight: bold;
        }
        footer ul{
            list-style: none;
            font-weight: 400;
        }
    </style>
</head>
<body>

<header class="mb-5">
    <div class="container">
        <ul class="d-flex justify-content-around">
            <a href="/showTrangChu"><li>ONLINE ENTERTAINMENT</li></a>
            <a href="/user/showMyFavorite"><li>MY FAVORITES</li></a>

            <%--            <a class="dropdown-item" href="/user/signOut"><li>Sign out</li></a>--%>
            <%--            <a class="dropdown-item" href="/user/showChangePassword"><li>Change Password</li></a>--%>
            <%--            <a class="dropdown-item" href="/user/showEditProfile"><li>Edit Profile</li></a>--%>





            <li class="dropdown dropdown-toggle" data-bs-toggle="dropdown">
                MY ACCOUNT
                <ul class="dropdown-menu">
                    <c:choose>
                        <c:when test="${sessionScope.userSession != null}">
                            <li onclick="redirect('/user/signOut')">Sign Out</li>
                            <li onclick="redirect('/user/changePassword')">>Change Password</li>
                            <li onclick="redirect('/user/editProfile')">Edit Profile</li>
                        </c:when>
                        <c:otherwise>
                            <li onclick="redirect('/showSignIn')">Login</li>
                            <li onclick="redirect('/showForgotPassword')">Forgot Password</li>
                            <li onclick="redirect('/showSignUp')">Registration</li>
                        </c:otherwise>
                    </c:choose>

                </ul>
            </li>
        </ul>
    </div>
</header>

<main class="container">
    <div class="row">
        <div class="col-9">
            <div class="card" style="width:800px">
                <iframe width="800px" height="450px" src="https://www.youtube.com/embed/${video.id}" frameborder="0" allowfullscreen ></iframe>
                <div class="card-body">
                    <h4 class="card-title">${video.title}</h4>
                    <div class="float-start">
                        <h4><strong>${video.views} views</strong></h4>
                    </div>
                    <div class="float-end">

                        <c:if test="${video.like}">
                            <a href="/user/chiTietUnlike/?videoId=${video.id}" class="btn btn-primary">Unlike</a>
                        </c:if>

                        <c:if test="${!video.like}">
                            <a href="/user/chiTietLike/?videoId=${video.id}" class="btn btn-primary">Like</a>
                        </c:if>

                        <a href="/user/showChiaSe/?videoId=${video.id}" class="btn btn-primary">Share</a>
                    </div>
                    <div class="mt-5 pt-2">
                        <p><strong>Description</strong></p>
                        <p class="card-text" style="width: 480px">${video.description}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-3">
            <c:forEach var="i" items="${watchedVideo}">
                <div class="card mb-3 border border-0" style="width:350px">
                    <div class="d-flex">
                        <a href="/showChiTiet/?videoId=${i.id}">
                            <img class="card-img-top" src="/img/${i.poster}" alt="Card image" style="width:200px">
                        </a>
                        <div class="card-body">
                            <h5 style="width: 150px ; height: 70px"  class="card-title">${i.showTitle()}</h5>
                        </div>
                    </div>
                </div>
            </c:forEach>


        </div>




    </div>
</main>

<footer class="mt-5">
    <div style="background-color: #0685aa;">
        <div class="row ">
            <div class="col-2"></div>

            <div class="col mt-5">
                <ul>
                    <li> <h4>Fpt Assignment</h4></li>
                    <li>Giới thiệu Công ty</li>
                    <li>Đội bay</li>
                    <li>Đối tác</li>
                    <li>Thông tin truyền thông</li>
                    <li>Trách nhiệm xã hội</li>
                    <li>Quan hệ cổ đông</li>
                    <li>Thông tin đấu thầu</li>
                    <li>Cẩm nang mua vé online</li>
                    <li>Việc làm với Fpt Airlines</li>
                </ul>
            </div>
            <!--  -->
            <div class="col mt-5">
                <ul>
                    <li> <h4>Pháp Lý</h4></li>
                    <li>Các Điều Kiện & Điều Khoản</li>
                    <li>Điều lệ vận chuyển</li>
                    <li>Điều Kiện Sử Dụng Cookies</li>
                    <li>Bảo Mật Thông Tin</li>
                    <li>Quy chế hoạt động sàn TMDT</li>
                </ul>
            </div>
            <!--  -->
            <div class="col mt-5">
                <ul>
                    <li> <h4>Hỗ Trợ</h4></li>
                    <li>Góp ý dịch vụ</li>
                    <li>Trung tâm trợ giúp</li>
                    <li>Chính sách bảo vệ hành khách</li>
                    <li>Trợ lý ảo của Fpt Airlines</li>
                </ul>
            </div>
            <!--  -->
            <div class="col mt-5">
                <ul>
                    <li> <h4>Vận Tải Hàng Hóa</h4></li>
                    <li>Trang web hàng hóa</li>
                </ul>
            </div>

            <!--  -->
            <div class="col mt-5">
                <ul>
                    <li> <h4>Thông tin hữu ích</h4></li>
                    <li>Cẩm nang mua vé online</li>
                    <li>Quảng cáo với Fpt Airlines</li>
                </ul>
            </div>



            <div class="col-2"></div>


        </div>
        <br>
        <br>

        <div class="row">
            <div class="col-3"></div>
            <div class="col-4">
                <img src="https://www.vietnamairlines.com/vn/vi/~/media/Images/VNANew/Footer/logo-footer-small-2" alt="ảnh" style="margin-left: 50px;">
            </div>
            <div class="col-4"></div>
        </div>
        <br>
        <hr  style="border-style: solid; border-width: 1px; border-color: white; font-weight: bold;">

        <div class="text-center" style="color: aliceblue;">
            <p>
                © 2019 Vietnam Cencher JSC<br>
                Tổng công ty Hàng không Việt Nam - CTCP. Số 200 Nguyễn Sơn, P.Bồ Đề, Q.Long Biên, Hà Nội. <br>
                Điện thoại:tel:+842438272289 (+84-24) 38272289. Fax: (+84-24) 38722375.<br>
                Giấy chứng nhận đăng ký doanh nghiệp, mã số doanh nghiệp: 0100107518, đăng ký lần đầu ngày 30/6/2010, đăng ký thay đổi lần thứ 9 ngày 12/01/2022, cấp bởi Sở KHĐT thành phố Hà Nội.
            </p>
        </div>
        <br><br>


    </div>
</footer>
    <script>
        function redirect(a){
            window.location.href =a;
        }
    </script>
</body>
</html>

