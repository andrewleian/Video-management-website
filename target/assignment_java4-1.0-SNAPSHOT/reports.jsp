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
  <h2 class="text-center">BAO CAO THONG KE</h2>

  <div>
    <h4 class="text-center">FAVORITES</h4>

    <table class="table table-striped table-bordered text-center">
      <thead >
      <th>VIDEO TITLE</th>
      <th>FAVORITE COUNT</th>
      <th>LATEST DATE</th>
      <th>OLDEST DATE</th>
      </thead>

      <tbody>
      <c:forEach var="i" items="${favoriteCustom}">
        <tr>
          <td>${i.videoTitle}</td>
          <td>${i.favoriteCount}</td>
          <td>${i.latestDate}</td>
          <td>${i.oldestDate}</td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
  </div>

  <ul class="pagination justify-content-center my-5">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
  </ul>

  <div>
    <h4 class="text-center">FAVORITE USERS</h4>
    <div class="row my-3">
      <div class="col-2"><label class="">VIDEO TITLE</label></div>
      <div class="col-10">
        <select class="" name="" onchange="favoriteUser(this.value)" style="width: 100%;">
          <c:forEach var="i" items="${videoTitleList}">
            <option value="/admin/favoriteUsers/?videoIdFU=${i[0]}&videoIdSF=${SFSelected}" ${FUSelected == i[0]? 'selected' : ''}>${i[1]}</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <table class="table table-striped table-bordered text-center">
      <thead >
      <th>USERNAME</th>
      <th>FULLNAME</th>
      <th>EMAIL</th>
      <th>FAVORITE DATE</th>
      </thead>

      <tbody>
        <c:forEach var="i" items="${favoriteUsers}">
          <tr>
            <td>${i.user.id}</td>
            <td>${i.user.fullname}</td>
            <td>${i.user.email}</td>
            <td>${i.likeDate}</td>
          </tr>
        </c:forEach>

      </tbody>
    </table>
  </div>


  <ul class="pagination justify-content-center my-5">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
  </ul>

  <div>
    <h4 class="text-center">SHARE FRIENDS</h4>
    <div class="row my-3">
      <div class="col-2"><label class="">VIDEO TITLE</label></div>
      <div class="col-10">
        <select class="" name="" onchange="shareFriend(this.value)" style="width: 100%;">
          <c:forEach var="i" items="${videoTitleList}">
            <option value="/admin/shareFriends/?videoIdSF=${i[0]}&videoIdFU=${FUSelected}" ${SFSelected == i[0]? 'selected' : ''}>${i[1]}</option>
          </c:forEach>
        </select>
      </div>
    </div>
    <table class="table table-striped table-bordered text-center">
      <thead >
      <th>SENDER NAME</th>
      <th>SENDER EMAIL</th>
      <th>RECEIVER EMAIL</th>
      <th>SENT DATE</th>
      </thead>

      <tbody>
      <c:forEach var="i" items="${favoriteByVideoId}">
        <tr>
          <td>${i.user.fullname}</td>
          <td>${i.user.email}</td>
          <td>${i.emails}</td>
          <td>${i.shareDate}</td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
  </div>

  <ul class="pagination justify-content-center my-5">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    <li class="page-item"><a class="page-link" href="#">1</a></li>
    <li class="page-item"><a class="page-link" href="#">2</a></li>
    <li class="page-item"><a class="page-link" href="#">3</a></li>
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
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
    function shareFriend(a){
      window.location.href = a;
    }
    function favoriteUser(a){
      window.location.href = a;
    }
  </script>
</body>
</html>

