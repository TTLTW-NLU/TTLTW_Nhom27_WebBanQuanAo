<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ADMIN</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/admin/favicon-32x32.png" type="image/png">

    <!-- Frameworks -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Sharp" rel="stylesheet"/>
    <!-- CSS native -->
    <link rel="stylesheet" href="assets/css/base.css">
    <link rel="stylesheet" href="admin/css/admin.css">

    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css">

</head>

<body>
<div class="container--admin">
    <aside>
        <div class="top">
            <div class="logo">
                <!-- <img src="./assets/imgs/admin/logoadmin.jpg" alt="Logo" /> -->
                <h2>LASMANATE</h2>
            </div>
            <div class="close" id="close-btn">
                <span class="material-icons-sharp"> close </span>
            </div>
        </div>

        <div class="sidebar">
            <a class="active" href="admin/manager-orders" onclick="showMain(event, 'admin/manager-orders')">
                <span class="material-icons-sharp"> dashboard </span>
                <h3>Bảng thông tin</h3>
            </a>
            <a href="admin/manager-users" onclick="showMain(event, 'admin/manager-users')">
                <span class="material-icons-sharp"> person_outline </span>
                <h3>Người dùng</h3>
            </a>
            <a href="admin/manager-products" onclick="showMain(event, 'admin/manager-products')">
                <span class="material-icons-sharp"> inventory </span>
                <h3>Sản phẩm</h3>
            </a>
            <a href="#">
                <span class="material-icons-sharp"> settings </span>
                <h3>Settings</h3>
            </a>
            <a href="user.jsp">
                <span class="material-icons-sharp"> logout </span>
                <h3>Logout</h3>
            </a>
        </div>
    </aside>

    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Info and Orders

---------------------------------------------------------
---------------------------------------------------------->
    <main id="admin/manager-orders">
        <h1>Bảng Thông Tin</h1>

        <div class="dash-top">
            <div class="date">
                <input type="date"/>
            </div>
        </div>

        <div class="insights">
            <!-- SALES -->
            <div class="sales">
                <span class="material-icons-sharp"> analytics </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Doanh Thu</h3>
                        <h1>2500VND</h1>

                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>81%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- EXPENSES -->
            <div class="expenses">
                <span class="material-icons-sharp"> bar_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Chi Phí</h3>
                        <h1>1000VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>62%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>

            <!-- INCOME -->
            <div class="income">
                <span class="material-icons-sharp"> stacked_line_chart </span>
                <div class="middle">
                    <div class="left">
                        <h3>Tổng Lợi Nhuận</h3>
                        <h1>1500VND</h1>
                    </div>
                    <div class="progress">
                        <svg>
                            <circle cx="38" cy="38" r="36"></circle>
                        </svg>
                        <div class="number">
                            <p>44%</p>
                        </div>
                    </div>
                </div>
                <small class="text-muted"> Last 24 hours </small>
            </div>
        </div>

        <div class="recent-orders">
            <h2>Đơn Hàng Gần Đây</h2>
            <table id="recent-orders--table">
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Khách hàng</th>
                    <th>Thanh toán</th>
                    <th>Mã giảm giá</th>
                    <th>Ngày Tạo</th>
                    <th>Tổng Tiền</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit order -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Đơn Hàng</h2>
                <form id="order-edit-form">
                    <table id="order_edit--table">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Khách hàng</th>
                            <th>Thanh toán</th>
                            <th>Mã Giảm Giá</th>
                            <th>Ngày Tạo</th>
                            <th>Tổng Tiền</th>
                            <th>Trạng Thái</th>
                            <th>Hành Động</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td style="width: 7%"><input type="number" name="id" id="edit-idOrder" readonly></td>
                            <td><input type="text" name="firstName" id="edit-firstNameOrder" required></td>
                            <td>
                                <select name="paymentId" id="edit-paymentId"
                                        style="align-items: center; text-align-last: center;"
                                        required>
                                    <option >Tiền mặt</option>
                                    <option value="2">Chuyển khoản</option>
                                    <option value="3">Thẻ tín dụng</option>
                                </select>
                            </td>

                            <td><input type="text" name="code" id="edit-code"></td>
                            <td><input type="datetime-local" name="orderDate" id="edit-orderDate" required></td>
                            <td><input type="number" name="totalPrice" id="edit-totalPrice" required></td>
                            <td>
                                <select name="status" id="edit-statusOrder"
                                        style="text-align: center; text-align-last: center;"
                                        required>
                                    <option value="0">Đã Hủy</option>
                                    <option value="1">Đang xử lý</option>
                                    <option value="2">Đang giao hàng</option>
                                    <option value="3">Đã giao hàng</option>
                                    <option value="4">Đã nhận hàng</option>
                                    <option value="5">Đã xóa</option>
                                </select>
                            </td>
                            <td><span type="submit" onclick="saveOrderEdits(event)"
                                      class="primary material-icons-sharp">save</span></td>
                        </tr>
                        </tbody>
                    </table>

                </form>
            </div>
        </div>

        <!-- Popup chi tiết đơn hàng -->
        <div class="overlay overlay-orderItemDetails">
            <div class="recent-orders popup popup-orderItemDetails"
                 style="position: relative; transform: translate(14%, 40%);">
                <span onclick="hideOverlayOrderItemDetails(event)" class="material-icons-sharp close"> close </span>
                <h2>Chi Tiết Đơn Hàng</h2>
                <form id="order-item-detail-form">
                    <table id="orderItem-details--table">
                        <thead>
                        <tr>
                            <th>Mã</th>
                            <th>Mã đơn hàng</th>
                            <th>Sản phẩm</th>
                            <th>Màu</th>
                            <th>Kích cỡ</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Giảm giá</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Dữ liệu sẽ được chèn vào đây bằng JavaScript -->
                        </tbody>
                    </table>
                </form>
            </div>
        </div>


    </main>

    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Users

---------------------------------------------------------
---------------------------------------------------------->

    <main id="admin/manager-users">
        <h1>Quản lý người dùng</h1>

        <div class="dash-top">
            <div class="date">
                <input type="date"/>
            </div>

            <button type="submit" onclick="showAddUserForm()" id="btn-addUser">
                <span class="material-icons-sharp"> add </span>
                <h3>Thêm Người Dùng</h3>
            </button>
        </div>

<%------/*==============================*/--%>
<%------/*-------- Add User Form -------*/--%>
<%------/*==============================*/--%>
        <section class="overlay-addUser">
            <form id="add-user-form">
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="addUserNameUser">Tên đăng nhập</label>
                        <input type="text" id="addUserNameUser" name="userName" placeholder="Nhập tên đăng nhập."
                               required>
                    </div>
                    <div class="form-group--items">
                        <label for="addPassWordUser">Mật Khẩu</label>
                        <input type="passWord" id="addPassWordUser" name="passWord" placeholder="Nhập mật khẩu."
                               required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="addFirstNameUser">Họ</label>
                        <input type="text" id="addFirstNameUser" name="firstName" placeholder="Nhập họ." required>
                    </div>
                    <div class="form-group--items">
                        <label for="addLastNameUser">Tên</label>
                        <input type="text" id="addLastNameUser" name="lastName" placeholder="Nhập tên." required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="addEmailUser">Email</label>
                        <input type="email" id="addEmailUser" name="email" placeholder="Nhập email." required>
                    </div>
                    <div class="form-group--items">
                        <label for="addAvatarUser">Avatar</label>
                        <input type="text" id="addAvatarUser" name="avatar" placeholder="Nhập avatar." required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="addAddressUser">Địa Chỉ</label>
                        <input type="text" id="addAddressUser" name="address" placeholder="Nhập địa chỉ." required>
                    </div>
                    <div class="form-group--items">
                        <label for="addPhoneUser">Số Điện Thoại</label>
                        <input type="tel" id="addPhoneUser" name="phone" placeholder="Nhập số điện thoại." required>
                    </div>
                </div>
<%--                <div class="form-group">--%>
<%--                    <div class="form-group--items">--%>
<%--                        <label for="addStatusUser">Trạng Thái</label>--%>
<%--                        <select id="addStatusUser" name="status" required>--%>
<%--                            <option type="number" value="0">Chưa kích hoạt</option>--%>
<%--                            <option type="number" value="1">Hoạt Động</option>--%>
<%--                            <option type="number" value="2">Bị khóa</option>--%>
<%--                            <option type="number" value="3">Bị cấm</option>--%>
<%--                            <option type="number" value="4">Đã xóa</option>--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                </div>--%>
                <button type="submit" onclick="createUser(event)" class="btn-primary">
                    <span class="material-icons-sharp"> add </span>
                    <h3>Thêm Người Dùng</h3>
                </button>
            </form>
        </section>


        <div class="recent-orders">
            <h1 style="text-align: center;">Danh Sách Người Dùng</h1>
            <table id="users--table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Tài khoản</th>
                    <th>Họ</th>
                    <th>Tên</th>
                    <th>Email</th>
                    <th>Avatar</th>
                    <th>Địa chỉ</th>
                    <th>SDT</th>
                    <th>Ngày tạo</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit users -->
        <div class="overlay">
            <div class="recent-orders popup">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Người Dùng</h2>
                <form id="user-edit-form">
                    <table id="user_edit--table">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Tài khoản</th>
                            <th>Tên</th>
                            <th>Họ</th>
                            <th>Email</th>
                            <th>Avatar</th>
                            <th>Địa chỉ</th>
                            <th>SDT</th>
                            <th>Ngày tạo</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>

                        <tbody>
                        <!-- Các trường nhập sẽ được thêm vào ở đây qua JavaScript hoặc từ server -->
                        <tr>
                            <td><input type="number" name="id" id="edit-id" readonly></td>
                            <td><input type="text" name="username" id="edit-username" readonly></td>
                            <td><input type="text" name="lastName" id="edit-lastName" required></td>
                            <td><input type="text" name="firstName" id="edit-firstName" required></td>
                            <td><input type="text" name="email" id="edit-email" required></td>
                            <td><input type="text" name="avatar" id="edit-avatar"></td>
                            <td><input type="text" name="address" id="edit-address" required></td>
                            <td><input type="number" name="phone" id="edit-phone" required></td>
                            <td><input type="datetime-local" name="createdDate" id="edit-createdDate" readonly></td>
                            <td>
                                <select name="status" id="edit-status" required>
                                    <option value="0">Chưa kích hoạt</option>
                                    <option value="1">Hoạt Động</option>
                                    <option value="2">Bị khoá</option>
                                    <option value="3">Bị cấm</option>
                                    <option value="4">Đã xóa</option>
                                </select>
                            </td>
                            <td><span type="submit" onclick="saveUserEdits(event)" class="primary material-icons-sharp">save</span>
                            </td>
                        </tr>
                        </tbody>
                    </table>

                </form>
            </div>
        </div>

        <!-- Popup vai trò và quyền của user -->
        <div class="overlay overlay-userRolePermissons">
            <div class="recent-orders popup popup-userRolePermissons"
                 style="width: 700px;position: relative; transform: translate(62%, 40%);">
                <span onclick="hideOverlayuserRolePermissons(event)" class="material-icons-sharp close"> close </span>
                <h2>Quyền hạn người dùng</h2>
                <form id="userRolePermissons-form">
                    <table id="userRolePermissons--table">
                        <thead>
                        <tr>
                            <th style="width: 125px">Tài khoản</th>
                            <th style="width: 125px">Tên</th>
                            <th style="width: 150px; margin-right: 10px">Vai trò</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Dữ liệu sẽ được chèn vào đây bằng JavaScript -->
                        </tbody>
                    </table>
                </form>
            </div>
        </div>

    </main>


    <!--------------------------------------------------------
---------------------------------------------------------

                   Manager Products

---------------------------------------------------------
---------------------------------------------------------->
    <main id="admin/manager-products">
        <h1>Quản lý sản phẩm</h1>

        <div class="dash-top">
            <div class="date">
                <input type="date"/>
            </div>
            <div class="btn-containerAdd">
                <button type="submit" onclick="showAddProductForm()" id="btn-addProduct">
                    <span class="material-icons-sharp"> add </span>
                    <h3>Thêm sản phẩm</h3>
                </button>
                <button type="submit" onclick="showAddProductDetailsForm()" id="btn-addProductDetails">
                    <span class="material-icons-sharp"> add </span>
                    <h3>Thêm chi tiết sản phẩm</h3>
                </button>
            </div>
        </div>

        <section class="overlay-addProduct">
            <form id="add-product-form">
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="typeId">Loại</label>
                        <select id="typeId" name="typeId" required>
                            <option type="number" value="1">Áo</option>
                            <option type="number" value="2">Quần</option>
                        </select>
                    </div>
                    <div class="form-group--items">
                        <label for="categoryId">Danh Mục</label>
                        <select id="categoryId" name="categoryId" required>
                            <option type="number" value="1">Áo nam</option>
                            <option type="number" value="2">Áo Nữ</option>
                            <option type="number" value="3">Áo trẻ em</option>
                            <option type="number" value="4">Quần nam</option>
                            <option type="number" value="5">Quần nữ</option>
                            <option type="number" value="6">Quần trẻ em</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="supplierId">Thương Hiệu</label>
                        <select id="supplierId" name="supplierId" required>
                            <option type="number" value="1">PEALO</option>
                            <option type="number" value="2">B Brown Studio</option>
                            <option type="number" value="3">BBRAND</option>
                            <option type="number" value="4">RUYCH STUDIO</option>
                        </select>
                    </div>
                    <div class="form-group--items">
                        <label for="name">Tên Sản Phẩm</label>
                        <input type="text" id="name" name="name" placeholder="Nhập tên sản phẩm."
                               required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="description">Mô tả sản phẩm</label>
                        <textarea id="description" name="description" placeholder="Nhập mô tả sản phẩm."
                                  required></textarea>
                    </div>
                    <div class="form-group--items">
                        <label for="releaseDate">Ngày phát hành</label>
                        <input type="date" id="releaseDate" name="releaseDate" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="unitSold">Số lượng đã bán</label>
                        <input type="number" id="unitSold" name="unitSold" placeholder="Nhập số lượng đã bán"
                               required>
                    </div>
                    <div class="form-group--items">
                        <label for="unitPrice">Giá đơn vị</label>
                        <input type="number" id="unitPrice" name="unitPrice" placeholder="Nhập giá đơn vị sản phẩm"
                               required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="product-status">Trạng thái</label>
                        <select id="product-status" name="status">
                            <option value="0">Đã xóa</option>
                            <option value="1" selected>Còn hàng</option>
                            <option value="2">Hết hàng</option>
                        </select>
                    </div>
                </div>

                <button type="submit" onclick="createProduct(event)" class="btn-primary"><span
                        class="material-icons-sharp"> add </span>
                    <h3>Thêm Sản Phẩm</h3>
                </button>
            </form>
        </section>

        <section class="overlay-addproductDetails">
            <form id="add-productDetails-form">
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="productId">ID Sản Phẩm Chính</label>
                        <input type="number" id="productId" name="productId" placeholder="Nhập ID sản phẩm chính"
                               required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="size">Kích Cỡ</label>
                        <input type="text" id="size" name="size" placeholder="Nhập kích cỡ sản phẩm" required>
                    </div>
                    <div class="form-group--items">
                        <label for="stock">Số Lượng Tồn Kho</label>
                        <input type="number" id="stock" name="stock" placeholder="Nhập số lượng tồn kho" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="form-group--items">
                        <label for="color">Màu Sắc</label>
                        <input type="text" id="color" name="color" placeholder="Nhập màu sắc sản phẩm" required>
                    </div>
                    <div class="form-group--items">
                        <label for="image">Hình Ảnh</label>
                        <input type="text" id="image" name="image" placeholder="Nhập đường dẫn hình ảnh sản phẩm"
                               required>
                    </div>
                </div>

                <button type="submit" onclick="createProductDetails(event)" class="btn-primary"><span
                        class="material-icons-sharp"> add </span>
                    <h3>Thêm Sản Phẩm</h3>
                </button>
            </form>
        </section>

        <div class="recent-orders">
            <h1 style="text-align: center" ;>Danh Sách Sản Phẩm</h1>
            <table id="products--table">
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Loại</th>
                    <th>Danh mục</th>
                    <th>Nhà cung cấp</th>
                    <th style="width: 170px">Tên</th>
                    <th style="width: 200px">Mô tả</th>
                    <th style="width: 120px">Ngày Nhập</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Trạng thái</th>
                    <th style="width: 120px">Hành động</th>
                </tr>
                </thead>
                <!-- Add tbody here | JS insertion -->
            </table>
            <a href="#">Show All</a>
        </div>

        <!-- edit products -->
        <div class="overlay overlay-productForId">
            <div class="recent-orders popup popup-productForId">
                <span onclick="hideOverlay(event)" class="material-icons-sharp close"> close </span>
                <h2>Chỉnh Sửa Sản Phẩm</h2>
                <form id="product-edit-form">
                    <table id="product_edit--table">
                        <thead>
                        <tr>
                            <th>Mã</th>
                            <th>Loại</th>
                            <th>Danh mục</th>
                            <th>Nhà cung cấp</th>
                            <th>Tên</th>
                            <th>Mô tả</th>
                            <th>Ngày Nhập</th>
                            <th>Số Lượng</th>
                            <th>Giá Tiền</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><input type="number" name="id" id="edit-idProduct" readonly></td>
                            <td>
                                <%--                               <input type="number" name="typeId" id="edit-typeId" required>--%>
                                <select name="typeId" id="edit-typeId" required>
                                    <option type="number" value="1">Áo</option>
                                    <option type="number" value="2">Quần</option>
                                </select>
                            </td>
                            <td>
                                <%--                                <input type="number" name="categoryId" id="edit-categoryId" required>--%>
                                <select name="categoryId" id="edit-categoryId" required>
                                    <option type="number" value="1">Áo nam</option>
                                    <option type="number" value="2">Áo nữ</option>
                                    <option type="number" value="3">Áo trẻ em</option>
                                    <option type="number" value="4">Quần nam</option>
                                    <option type="number" value="5">Quần nữ</option>
                                    <option type="number" value="6">Quần trẻ em</option>
                                </select>
                            </td>
                            <td>
                                <%--                                <input type="number" name="supplierId" id="edit-supplierId" required>--%>
                                <select name="supplierId" id="edit-supplierId" required>
                                    <option type="number" value="1">PEALO</option>
                                    <option type="number" value="2">B Brown Studio</option>
                                    <option type="number" value="3">BBRAND</option>
                                    <option type="number" value="4">RUYCH STUDIO</option>
                                </select>
                            </td>
                            <td><input type="text" name="name" id="edit-name" required></td>
                            <td><textarea name="description" id="edit-description" required></textarea></td>
                            <td><input type="date" name="releaseDate" id="edit-releaseDate" required></td>
                            <td><input type="number" name="unitSold" id="edit-unitSold" required></td>
                            <td><input type="number" name="unitPrice" id="edit-unitPrice" step="0.01" required></td>
                            <td>
                                <select name="status" id="edit-statusProduct" required>
                                    <option value="0">Đã xóa</option>
                                    <option value="1">Còn</option>
                                    <option value="2">Hết</option>
                                </select>
                            </td>
                            <td><span type="submit" onclick="saveProductEdits(event)"
                                      class="primary material-icons-sharp">save</span></td>
                        </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>

        <!-- Popup chi tiết sản phẩm -->
        <div class="overlay overlay-productDetails">
            <div class="recent-orders popup popup-productDetails"
                 style=" position: relative;transform: translate(14%, 40%);">
                <span onclick="hideOverlayProductDetails(event)" class="material-icons-sharp close"> close </span>
                <h2>Chi Tiết Sản Phẩm</h2>
                <form id="product-detail-form">
                    <table id="product-details--table">
                        <thead>
                        <tr>
                            <th>Mã</th>
                            <th>Mã sản phẩm</th>
                            <th>Size</th>
                            <th>Số lượng</th>
                            <th>Hình ảnh</th>
                            <th>Màu sắc</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Dữ liệu sẽ được chèn vào đây bằng JavaScript -->
                        </tbody>
                    </table>
                </form>
            </div>
        </div>

    </main>
</div>

<script src="assets/js/admin-data/recent-order-data.js"></script>
<script src="assets/js/admin-data/sales-analytics-data.js"></script>
<script src="assets/js/admin-data/update-data.js"></script>

<%-- JS --%>
<script src="admin/js/admin.js"></script>
<script src="admin/js/orders.js"></script>
<script src="admin/js/orderDetails.js"></script>
<script src="admin/js/users.js"></script>
<script src="admin/js/userRolePermission.js"></script>
<script src="admin/js/products.js"></script>
<script src="admin/js/productDetails.js"></script>


<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<!-- DataTables JS -->
<script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
<!-- DataTables CSS -->
<link href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css" rel="stylesheet">


</body>

</html>