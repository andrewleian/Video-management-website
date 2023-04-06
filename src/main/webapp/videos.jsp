<%--
  Created by IntelliJ IDEA.
  User: yenbo
  Date: 4/1/2023
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            padding: 25px 20px  10px  20px;
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

<header class="mb-5 container-fluid">
    <div class="container">
        <div class="row">
            <p class="col-3" style="font-weight: bold; font-size: 20px; padding: 25px 20px 10px 20px;">ADMINISTRATION TOOL</p>
            <div class="col-9" >
                <div class="float-end" >
                    <ul class=" d-flex gap-5" >
                        <a href="/showTrangChu"><li>HOME</li></a>
                        <a href="/admin/showVideos"><li>VIDEOS</li></a>
                        <a href="/admin/showUsers"><li>USERS</li></a>
                        <a href="/admin/showReports"><li>REPORTS</li></a>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</header>

<main class="container">
    <h2 class="text-center">QUẢN LÝ VIDEO</h2>
    <div class="">
        <form class="row" enctype="multipart/form-data" method="post">
            <div class="col-4 my-3">
                <label class="form-label">POSTER</label>
                <input type="file" style="width: 400px;" onchange="loadPoster()" id="filePoster" name="poster" value="${idEdit.poster}">
                <p class="fw-bold text-danger">${posterError}</p>
                <br><br>
                <img src="/img/${idEdit.poster}" alt="" style="width: 400px;" id="imgPoster">
            </div>
            <div class="col-8">
                <div class="my-3">
                    <label class="form-label" >YOUTUBE ID</label>
                    <input class="form-control" type="text" name="id" value="${idEdit.id}" ${isIdEnable}>
                    <p class="fw-bold text-danger">${idError}</p>
                </div>
                <div class="my-3">
                    <label class="form-label" >VIDEO TITLE</label>
                    <input class="form-control" type="text" name="title" value="${idEdit.title}">
                    <p class="fw-bold text-danger">${titleError}</p>
                </div>
                <div class="my-3">
                    <label class="form-label">VIEW COUNT</label>
                    <input class="form-control" type="number" name="views" value="${idEdit.views}" required  defaultValue=0>
                    <p class="fw-bold text-danger">${viewsError}</p>
                </div>
                <div class="my-4">
                    <input class="" type="radio" name="active" value="true" ${idEdit.active? "checked" : ""} required>
                    <label class="form-label" >ACTIVE</label>
                    <input class="ms-3" type="radio" name="active" value="false" ${!idEdit.active && idEdit != null ? "checked" : ""} required>
                    <label class="form-label" >INACTIVE</label>
                    <p class="fw-bold text-danger">${activeError}</p>
                </div>
            </div>
            <br>
            <label class="form-label" >DESCRIPTION</label>
            <textarea name="description" cols="50" rows="10">${idEdit.description}</textarea>
            <p class="fw-bold text-danger">${descriptionError}</p>
            <div class="my-5 text-end" style="margin: 0px; padding: 0px;">
                <hr>
                <button class="btn btn-primary px-4" formaction="/admin/createVideo/?page=${currentPage}" ${isCreateable}>Create</button>
                <button class="btn btn-primary px-4" formaction="/admin/updateVideo/?page=${currentPage}" ${isUpdateable}>Update</button>
                <button class="btn btn-primary px-4" formaction="/admin/deleteVideo/?page=${currentPage}" ${isDeleteable}>Delete</button>
                <button class="btn btn-primary px-4" formaction="/admin/resetVideo/?page=${currentPage}">Reset</button>
            </div>
        </form>

    </div>

    <div>
        <h4 class="text-center">VIDEO LIST</h4>

        <table class="table table-striped table-bordered text-center">
            <thead >
            <th>Youtube Id</th>
            <th>Video Title</th>
            <th>View Count</th>
            <th>Status</th>
            <th>Action</th>
            </thead>

            <tbody>
            <c:forEach var="i" items="${videoList}">
                <tr>
                    <td>${i.id}</td>
                    <td>${i.title}</td>
                    <td>${i.views}</td>
                    <c:if test="${i.active}">
                        <td>Active</td>
                    </c:if>
                    <c:if test="${!i.active}">
                        <td>Inactive</td>
                    </c:if>

                    <td><a class="btn btn-primary px-5" href="/admin/editVideo/?idEdit=${i.id}&page=${currentPage}">Edit</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <ul class="pagination justify-content-center my-5">
        <li class="page-item"><a class="page-link" href="/admin/showVideos/?page=1">First</a></li>
        <c:forEach  begin="1" end="${totalPage}" var="i">
            <li class="page-item"><a class="page-link" href="/admin/showVideos/?page=${i}">${i}</a></li>
        </c:forEach>
        <li class="page-item"><a class="page-link" href="/admin/showVideos/?page=${totalPage}">Last</a></li>
    </ul>
</main>

<footer>
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

    function loadPoster(){
        var filePoster = document.getElementById("filePoster");
        var imgPoster = document.getElementById("imgPoster");

        if(filePoster.files && filePoster.files[0]){
            var reader = new FileReader();
            reader.onload = function(e){
                imgPoster.src = e.target.result;
            }
        }
        reader.readAsDataURL (filePoster.files[0]);
    }

</script>
</body>
</html>

