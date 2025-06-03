function fetchOrdersForUser() {
    $.ajax({
        url: "/WebBanQuanAo/user/orderController",
        type: "GET",
        dataType: "json",
        cache: false,
        success: function (data) {
            if (!Array.isArray(data)) {
                $("#order-list").html(`<p class="text-danger">Dữ liệu không hợp lệ!</p>`);
                return;
            }

            if (data.length === 0) {
                $("#order-list").html(`<p class="text-muted">Bạn chưa có đơn hàng nào.</p>`);
                return;
            }

            // Sắp xếp đơn hàng theo ngày đặt hàng giảm dần (mới nhất lên đầu)
            data.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));

            let html = `<table id="order-table" class="table table-bordered" style="width: 100%; font-size: 1.4rem; text-align: center; vertical-align: middle;">`;
            html += `<thead><tr>
                <th>Đơn hàng</th>
                <th>Ngày đặt</th>
                <th>Thanh toán</th>
                <th>Trạng thái</th>
                <th>Sản phẩm</th>
                <th>Tổng cộng</th>
                <th>Hành động</th>
            </tr></thead><tbody>`;

            data.forEach(order => {
                const orderDate = new Date(order.orderDate);
                const formattedDate = `${orderDate.getDate()}-${orderDate.getMonth() + 1}-${orderDate.getFullYear()}`;

                let productHtml = "";
                if (order.orderDetails && order.orderDetails.length > 0) {
                    order.orderDetails.forEach(item => {
                        productHtml += `
                            <div class="d-flex align-items-center mb-2" style="text-align: left;">
                                <img src="${item.imageUrl || 'assets/imgs/default.jpg'}" alt="${item.productName}"
                                     class="order-product-img rounded mr-3" style="width: 80px; height: 80px;">
                                <div>
                                    <h3 style="font-size: 1.6rem;">${item.productName}</h3>
                                    <p class="text-muted mb-0">Màu: ${item.color || 'Không rõ'}, Size: ${item.size || 'N/A'}</p>
                                    <p class="text-muted mb-0">Số lượng: ${item.quantity}</p>
                                    <p class="mb-1"><s>${item.unitPrice.toLocaleString()}đ</s></p>
                                    <p class="text-danger mb-0"><strong>${(item.unitPrice - item.discount).toLocaleString()}đ</strong></p>
                                </div>
                            </div>`;
                    });
                } else {
                    productHtml = `<p>Không có sản phẩm nào.</p>`;
                }

                const isCancelable = order.status !== 0 && order.status !== 3 && order.status !== 4;
                const isConfirm = order.status === 3;

                html += `
                    <tr>
                        <td style="vertical-align: middle;">#${order.id}</td>
                        <td style="vertical-align: middle;">${formattedDate || 'N/A'}</td>
                        <td style="vertical-align: middle;">${order.paymentMethod || 'Không rõ'}</td>
                        <td style="vertical-align: middle;">
                            <span class="${getStatusColor(order.status)}">
                            ${getStatusText(order.status)}
                            </span>
                        </td>
                        <td style="text-align: left;">${productHtml}</td>
                        <td style="vertical-align: middle;">${order.totalPrice.toLocaleString()}đ</td>
                        <td style="vertical-align: middle;">
                            <button onclick="openCancelReason(event)" id="order-cancel-btn" class="btn btn-danger btn-sm" 
                                data-orderId="${order.id}" style="font-size: 1.4rem;" ${!isCancelable ? 'disabled' : ''}>
                                Hủy đơn hàng
                            </button>
                            <button onclick="completedOrder(event)" id="order-completed-btn" class="btn btn-success btn-sm" 
                                data-orderId="${order.id}" style="font-size: 1.4rem; margin-top: 5px" ${!isConfirm ? 'disabled' : ''}>
                                Đã nhận hàng
                            </button>
                        </td>
                    </tr>`;
            });

            html += `</tbody></table>`;

            $("#order-list").html(html);

            // Khởi tạo DataTable với tìm kiếm và phân trang
            $('#order-table').DataTable({
                "paging": true,
                "searching": true,
                "pageLength": 5,
                "language": {
                    "search": "Tìm kiếm:",
                    "lengthMenu": "Hiển thị _MENU_ đơn hàng",
                    "info": "Hiển thị _START_ đến _END_ của _TOTAL_ đơn hàng",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Tiếp",
                        "previous": "Trước"
                    }
                }
            });
        },
        error: function () {
            $("#order-list").html(`<p class="text-danger">Lỗi khi tải lịch sử đơn hàng!</p>`);
        }
    });
}

// Gọi hàm khi tài liệu sẵn sàng
$(document).ready(function () {
    fetchOrdersForUser();
});

// Chuyển đổi status từ số sang chuỗi tiếng Việt
function getStatusText(status) {
    const statusMap = {
        0: "Đã hủy",
        1: "Đang xử lý",
        2: "Đang giao",
        3: "Đã giao",
        4: "Đã nhận hàng"
    };
    return statusMap[status] || "Không xác định";
}

// Xác định màu sắc theo trạng thái đơn hàng
function getStatusColor(status) {
    const colorMap = {
        0: "text-danger",    // Đã hủy (màu đỏ)
        1: "text-warning",   // Đang xử lý (màu vàng)
        2: "text-primary",   // Đang giao (màu xanh dương)
        3: "text-success",   // Đã giao (màu xanh lá)
    };
    return colorMap[status] || "text-secondary"; // Không xác định (màu xám)
}

// Mở modal nhập lý do hủy đơn hàng
function openCancelReason(event) {
    const orderId = event.target.getAttribute("data-orderId");

    // Nếu modal đã tồn tại, chỉ cần cập nhật thông tin và hiển thị
    let existingModal = document.getElementById("cancelOrderModal");
    if (existingModal) {
        document.getElementById("cancelOrderId").value = orderId;
        $("#cancelOrderModal").modal("show");
        return;
    }

// Tạo modal mới
    const modalHtml = `
    <div class="modal fade cancel-order-custom-modal" id="cancelOrderModal" tabindex="-1" role="dialog" aria-labelledby="cancelOrderLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="cancelOrderLabel">Nhập lý do hủy đơn</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="cancelOrderForm" onsubmit="submitCancelOrder(event)">
                        <input type="hidden" id="cancelOrderId" value="${orderId}">
                        <div class="form-group">
                            <label for="cancelReason">Lý do hủy đơn:</label>
                            <textarea class="form-control" id="cancelReason" rows="3" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-danger">Xác nhận hủy</button>
                    </form>
                </div>
            </div>
        </div>
    </div>`;

    document.body.insertAdjacentHTML("beforeend", modalHtml);

    // Hiển thị modal
    $("#cancelOrderModal").modal("show");
}

// Xử lý gửi yêu cầu hủy đơn
function submitCancelOrder(event) {
    event.preventDefault();

    const orderId = document.getElementById("cancelOrderId").value;
    const reason = document.getElementById("cancelReason").value;

    if (!reason.trim()) {
        alert("Vui lòng nhập lý do hủy đơn hàng.");
        return;
    }

    $.ajax({
        url: '/WebBanQuanAo/user/orderController?action=cancel',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ id: parseInt(orderId, 10), cancelReason: reason.trim() }),
        success: function (data) {
            alert(data.message);
            $("#cancelOrderModal").modal("hide");
            fetchOrdersForUser(); // Cập nhật lại danh sách đơn hàng
        },
        error: function () {
            alert("Đã xảy ra lỗi khi gửi yêu cầu hủy đơn hàng.");
        }
    });
}

function completedOrder(event) {
    const orderId = parseInt(event.target.getAttribute("data-orderId"), 10);

    if (!confirm('Bạn có chắc chắn xác nhận đã nhận hàng chứ?')) {
        return;
    }

    $.ajax({
        url: '/WebBanQuanAo/user/orderController?action=confirm',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({id: orderId}),
        cache: false,
        success: function (data) {
            alert(data.message);
            fetchOrdersForUser();
        },
        error: function () {
            alert('Đã xảy ra lỗi khi xác nhận đã nhận hàng .');
        }
    });
}
