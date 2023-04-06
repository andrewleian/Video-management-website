<%--
  Created by IntelliJ IDEA.
  User: yenbo
  Date: 4/1/2023
  Time: 2:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>
<body>


<main class="container">
    <div class="card text-center mt-5">
        <div class="card-header h5 text-white bg-primary">Edit profile</div>
        <h2 class="text-danger">${error}</h2>
        <h2 class="text-success">${success}</h2>
        <div class="card-body px-5">
            <p class="card-text py-2">
                Your information
            </p>
            <form action="/user/editProfile" method="post">
                <div class="row">
                    <div class="form-outline py-3 col-6">
                        <label class="form-label" >Username</label>
                        <input type="text"  class="form-control" name="id" value="${object.id}" disabled/>
                    </div>
                    <div class="form-outline py-3 col-6">
                        <label class="form-label" >Pasword</label>
                        <input type="password" class="form-control" name="password" value="${object.password}"/>
                    </div>
                    <div class="form-outline py-3 col-6">
                        <label class="form-label" >Fullname</label>
                        <input type="text" class="form-control" name="fullname" value="${object.fullname}"/>
                    </div>
                    <div class="form-outline py-3 col-6">
                        <label class="form-label" >Email address</label>
                        <input type="email" class="form-control" name="email" value="${object.email}"/>
                    </div>
                    <div class="form-outline py-3 col-6">
                        <input type="hidden" class="form-control" name="admin" value="${object.admin}"/>
                    </div>
                </div>
                <button class="btn btn-primary">Update</button>
            </form>
            <div class="d-flex justify-content-between mt-4">
                <a class="" href="/showTrangChu">Back to home page</a>
                <a class="" href="/user/signOut">Sign out</a>
            </div>
        </div>
    </div>
</main>



</body>
</html>

