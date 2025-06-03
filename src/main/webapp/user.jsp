<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.user.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Người dùng</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/admin/favicon-32x32.png" type="image/png">
    <!-- Frameworks -->
    <!-- Reset CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">

    <!-- CSS native -->
    <link rel="stylesheet" href="./assets/css/base.css">
    <link rel="stylesheet" href="user/css/user.css">
    <link rel="stylesheet" href="./assets/css/header-footer.css">

    <style>
        <%--css cho thanh search cua datable--%>

        #order-table_filter {
            display: flex;
            justify-content: end;
            position: relative;
            bottom: 2.5rem;
        }

        #order-table_filter input {
            padding: 0.5rem 1.5rem !important;
            font-size: 1.4rem !important;
            border: 2px solid #4A90E2 !important;
            border-radius: 2rem !important;
            outline: none !important;
            transition: border-color 0.3s ease, box-shadow 0.3s ease !important;
        }

        /* Hiệu ứng khi focus vào ô tìm kiếm */
        #order-table_filter input:focus {
            border-color: #2C6CB0 !important;
            box-shadow: 0 0 8px rgba(74, 144, 226, 0.5) !important;
        }

        /* Tùy chỉnh nhãn 'Tìm kiếm:' */
        #order-table_filter label {
            font-size: 1.4rem !important;
            font-weight: bold !important;
            color: #333 !important;
            display: flex !important;
            align-items: center !important;
            gap: 1rem !important;
        }

        /* Css cho chon so luong hien thi */
        #order-table_length {
            margin-top: 2.5rem;
        }

        /* Tùy chỉnh phần select */
        #order-table_length select {
            padding: 0.6rem 1rem !important;
            font-size: 1.4rem !important;
            border: 2px solid #4A90E2 !important;
            border-radius: 1.5rem !important;
            outline: none !important;
            background: #fff !important;
            cursor: pointer !important;
            pointer-events: auto !important;
            transition: border-color 0.3s ease, box-shadow 0.3s ease !important;
        }

        /* Hiệu ứng khi focus vào select */
        #order-table_length select:focus {
            border-color: #2C6CB0 !important;
            box-shadow: 0 0 8px rgba(74, 144, 226, 0.5) !important;
        }

        /* Tùy chỉnh phần label */
        #order-table_length label {
            font-size: 1.4rem !important;
            font-weight: bold !important;
        / color: #333 !important;
            display: flex !important;
            align-items: center !important;
            gap: 1rem !important;
        }

        /* Tăng độ rộng của select */
        #order-table_length select {
            min-width: 8rem !important;
        }

        .input-box {
            position: relative;
        }

        .input-box i {
            position: absolute;
            right: 20px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 20px;
            color: #888;
        }

        .toggle-password {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            font-size: 16px;
            color: #666;
            outline: none;
        }

        .toggle-password i {
            font-size: 18px;
            cursor: pointer;
        }

        /* Các style cho điều kiện mật khẩu */
        .valid {
            color: green;
        }

        .invalid {
            color: red;
        }
    </style>
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>

<div style="height: 12rem;"></div>

<div class="container-fluid mt-5" style="position: relative; width: 1450px">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 bg-light p-0 sidebar">
            <ul class="list-group list-group-flush">
                <li class="nav-item active" onclick="showMain(event, 'profile-info')"><i class="fas fa-info-circle"></i>Thông
                    tin tài khoản
                </li>
                <li class="nav-item" onclick="showMain(event, 'order-container')"><i class="fas fa-history"></i> Lịch sử
                    đơn
                    hàng
                </li>
                <li class="nav-item" onclick="showMain(event, 'voucher-wallet')"><i class="fas fa-wallet"></i> Ví
                    Voucher
                </li>
                <li class="nav-item" onclick="showMain(event, 'order-feedback')"><i class="fas fa-star"></i> Đánh giá và
                    phản
                    hồi
                </li>
                <li class="nav-item" onclick="showMain(event, 'notification')" style="position: relative;"><i
                        class="fas fa-question-circle"></i> Thông
                    báo<span class="notification-count">1</span></li>
                <li class="nav-item">
                    <a href="javascript:void(0);" onclick="document.getElementById('logoutForm').submit();">
                        <i class="fas fa-sign-out-alt"></i> Đăng xuất
                    </a>
                    <form id="logoutForm" action="logout" method="post" style="display: none;">
                    </form>
                </li>
            </ul>
        </div>

        <!-- Profile Info -->
        <main id="profile-info" class="col-md-9 p-4 bg-white rounded">
            <h4 class="font-weight-bold mb-4 text-second-color">Thông tin tài khoản</h4>
            <form method="post" action="updateProfileServlet">
                <%
                    User authUser = (User) session.getAttribute("auth");
                    if (authUser == null) {
                        response.sendRedirect("login.jsp");
                        return;
                    }
                %>

                <div class="form-group">
                    <label for="name">Họ và tên</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="<%= authUser.getLastName() + " " + authUser.getFirstName() %>" required>
                </div>

                <div class="form-group">
                    <label for="gmail">Email</label>
                    <input type="email" class="form-control" id="gmail" name="gmail"
                           value="<%= authUser.getEmail() %>" required>
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại</label>
                    <input type="text" class="form-control" id="phone" name="phone"
                           value="<%= authUser.getPhone() %>" required>
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ</label>
                    <input type="text" class="form-control" id="address" name="address"
                           value="<%= authUser.getAddress() %>" required>
                </div>

                <button type="submit" class="btn btn-second-color">Cập nhật</button>
            </form>
            <!-- Login Info -->
            <h4 class="font-weight-bold mt-5 mb-4 text-second-color">Thông tin đăng nhập</h4>
            <div class="inform-login">
                <div class="form-group">
                    <label for="userName">User name</label>
                    <input type="text" class="form-control" id="userName"
                           value="<%= ((User) session.getAttribute("auth")).getUserName() %>" readonly>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" class="form-control" id="password" value="************" readonly>
                </div>
                <button class="btn btn-second-color" id="open">Đổi mật khẩu</button>
                <div id="confirmMessage" style="display:none; margin-top: 10px; color: blue; font-weight: 600;">
                    Vui lòng kiểm tra email để xác nhận thay đổi mật khẩu.
                </div>
            </div>
        </main>

        <!-- Lịch sử đơn hàng -->
        <main id="order-container" class="col-md-9 p-4 bg-white rounded">
            <!-- Header -->
            <div id="order-header" class="d-flex justify-content-between align-items-center mb-4">
                <h4 id="order-title" class="text-second-color">Lịch sử đơn hàng</h4>
                <a href="return-policy.jsp" id="order-policy" class="btn">Chính sách đổi trả 30 ngày</a>
            </div>

            <!-- Danh sách đơn hàng -->
            <div id="order-list"></div>
        </main>

        <!-- Đánh giá và phản hồi -->
        <main id="order-feedback" class="col-md-9 p-4 bg-white rounded">
            <h4 class="font-weight-bold mb-4 text-second-color">Đánh giá đơn hàng</h4>
            <!-- Order Feedback Card -->
            <div class="card bg-light p-4 rounded">
                <!-- Order Info -->
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="text-second-color">Đơn hàng #12345</h5>
                        <p class="text-muted">Ngày nhận: 01/12/2024</p>
                        <p class="text-muted">
                            Trạng thái: <strong class="text-success">Đã nhận</strong>
                        </p>
                    </div>
                </div>
                <hr>
                <!-- Product List -->
                <h5 class="font-weight-bold mb-3">Sản phẩm trong đơn hàng</h5>
                <ul class="list-group mb-4">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div class="d-flex align-items-center">
                            <img src="assets/imgs/Collection-running/epitem2.webp" alt="Sản phẩm 1"
                                 class="order-product-img rounded mr-3">
                            <div>
                                <h6 class="mb-1">Sản phẩm 1</h6>
                                <p class="text-muted mb-0">Số lượng: 1</p>
                            </div>
                        </div>
                        <div class="text-right">
                            <p class="mb-1"><strong>200.000đ</strong></p>
                        </div>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <div class="d-flex align-items-center">
                            <img src="assets/imgs/Collection-running/epitem1.1.webp" alt="Sản phẩm 2"
                                 class="order-product-img rounded mr-3">
                            <div>
                                <h6 class="mb-1">Sản phẩm 2</h6>
                                <p class="text-muted mb-0">Số lượng: 2</p>
                            </div>
                        </div>
                        <div class="text-right">
                            <p class="mb-1"><strong>400.000đ</strong></p>
                        </div>
                    </li>
                </ul>

                <!-- Rating & Review Form -->
                <h4 class="font-weight-bold mb-3">Đánh giá sản phẩm</h4>
                <form>
                    <div class="form-group">
                        <label for="rating">Đánh giá:</label>
                        <select class="form-control" id="rating" style="padding: 0;">
                            <option>Chọn đánh giá</option>
                            <option value="5">5 - Rất hài lòng</option>
                            <option value="4">4 - Hài lòng</option>
                            <option value="3">3 - Bình thường</option>
                            <option value="2">2 - Không hài lòng</option>
                            <option value="1">1 - Rất không hài lòng</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="review">Nhận xét:</label>
                        <textarea class="form-control" id="review" rows="4"
                                  placeholder="Viết nhận xét của bạn về sản phẩm..."></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                </form>
            </div>
        </main>


        <!-- Voucher -->
        <main id="voucher-wallet" class=" col-md-9 p-4 bg-white rounded">
            <div class="wallet-header">
                <h4>Ví Voucher</h4>
                <button class="btn-add-voucher">Nhận Voucher Mới</button>
            </div>

            <div class="voucher-list">
                <div class="voucher-card">
                    <div class="voucher-info">
                        <h5>Mã Voucher: <span class="voucher-code">ABC123</span></h5>
                        <p class="voucher-desc">Giảm giá 10% cho đơn hàng tiếp theo</p>
                        <p class="voucher-expiry">Hết hạn: 31 Tháng 12 2024</p>
                    </div>
                    <button class="btn-use">Sử Dụng</button>
                </div>

                <div class="voucher-card">
                    <div class="voucher-info">
                        <h5>Mã Voucher: <span class="voucher-code">XYZ456</span></h5>
                        <p class="voucher-desc">Miễn phí vận chuyển cho đơn hàng trên 50000VND</p>
                        <p class="voucher-expiry">Hết hạn: 15 Tháng 1 2025</p>
                    </div>
                    <button class="btn-use">Sử Dụng</button>
                </div>
            </div>
        </main>

        <!-- Notification -->
        <main id="notification" class="col-md-9 p-4 bg-white rounded">
            <h4 class="font-weight-bold mb-4 text-second-color">Thông báo</h4>
            <!-- Notification Example -->
            <div class="notification-card bg-light p-4 rounded mb-4">
                <h5 class="text-second-color">Thông báo 1</h5>
                <p class="text-muted">Ngày: 01/12/2024</p>
                <p>Nội dung thông báo chi tiết, ví dụ: Cảm ơn bạn đã mua hàng tại cửa hàng của chúng tôi!</p>
            </div>
            <div class="notification-card bg-light p-4 rounded mb-4">
                <h5 class="text-second-color">Thông báo 2</h5>
                <p class="text-muted">Ngày: 30/11/2024</p>
                <p>Nội dung thông báo chi tiết, ví dụ: Đơn hàng #12345 của bạn đã được giao thành công.</p>
            </div>
            <div class="notification-card bg-light p-4 rounded mb-4">
                <h5 class="text-second-color">Thông báo 3</h5>
                <p class="text-muted">Ngày: 29/11/2024</p>
                <p>Nội dung thông báo chi tiết, ví dụ: Bạn có voucher giảm giá 10% sắp hết hạn vào ngày 31/12/2024.</p>
            </div>
        </main>
    </div>

</div>

<!-- Order history -->

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script> <!-- Link to Font Awesome icons -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="./assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>


<!-- DataTables JS -->
<script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>

<script src="./user/js/orders-history.js"></script>

<script>
    document.getElementById('open').addEventListener('click', function (e) {
        e.preventDefault();

        const messageEl = document.getElementById('confirmMessage');
        messageEl.style.display = 'block';
        messageEl.textContent = 'Vui lòng kiểm tra email để xác nhận thay đổi mật khẩu.';

        fetch('/WebBanQuanAo/sendChangePasswordEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({userName: document.getElementById('userName').value})
        })
            .then(res => res.json())
            .then(data => {
                if (!data.success) {
                    messageEl.textContent = '';
                    alert('Gửi email xác nhận thất bại. Vui lòng thử lại.');
                }
            })
            .catch(err => {
                messageEl.textContent = '';
                alert('Có lỗi xảy ra. Vui lòng thử lại.');
            });
    });
</script>
<!-- base js -->
<script src="./assets/js/base.js"></script>
<script src="./assets/js/user.js"></script>
</body>

</html>