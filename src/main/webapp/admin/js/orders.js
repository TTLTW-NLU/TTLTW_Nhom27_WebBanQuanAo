// ================= Manager Orders ===================

// ===============Recent_order_data===============//
function fetchOrders() {
    $.ajax({
        url: '/WebBanQuanAo/admin/api/orders',
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (response) {
            const orders = response.data;
            const table = $("#recent-orders--table");

            if ($.fn.DataTable.isDataTable(table)) {
                table.DataTable().destroy();
                table.find("tbody").empty();
            }

            const tbody = buildTableOrders(orders);
            table.append(tbody);

            table.DataTable({
                searching: true,
                info: true,
                order: [[0, 'asc']],
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/vi.json'
                },
                paging: true,
                pageLength: 5,
                lengthChange: true,
            });

            targetMain.classList.add("data-loaded");
        },
        error: function (xhr, status, error) {
            console.error('Error fetching orders:', error);
            alert("Không thể lấy danh sách đơn hàng. Vui lòng thử lại.");
        }
    });
}

// ================= Get Status Text ===================
function getStatusText(status) {
    const statusMap = {
        0: "Đã hủy",
        1: "Đang xử lý",
        2: "Đang giao",
        3: "Đã giao",
        4: "Đã nhận hàng",
        5: "Đã xóa"
    };
    return statusMap[status] || "Không xác định";
}

function mapPaymentMethodToId(paymentMethod) {
    const map = {
        "Tiền mặt": 1,
        "Chuyển khoản": 2,
        "Thẻ tín dụng": 3,
    };
    return map[paymentMethod] || null;
}

function getPaymentMethodText(paymentId) {
    const map = {
        1: "Tiền mặt",
        2: "Chuyển khoản",
        3: "Thẻ tín dụng",
    };
    return map[paymentId] || "Không xác định";
}

// =============== Build Table Body ================//
const buildTableOrders = (orders) => {
    let orderContent = "";
    for (const order of orders) {
        orderContent += `
            <tr>
                <td>${order.id}</td>
                <td>${order.firstName}</td>
                <td>${getPaymentMethodText(order.paymentId)}</td>
                <td>${order.code}</td>
                <td>${order.orderDate}</td>
                <td>${order.totalPrice} VND</td>
                <td>${getStatusText(order.status)}</td>
                <td class="primary">
                    <span onclick="openEditOrderPopup(event)" class="material-icons-sharp" data-orderId="${order.id}">edit</span>
                    <span onclick="deleteOrder(event)" class="material-icons-sharp" data-orderId="${order.id}">delete</span>
                    <span onclick="openOrderItemDetails(event)" class="material-icons-sharp" data-orderItemsId="${order.id}">info</span>
                </td>
            </tr>
        `;
    }
    return `<tbody>${orderContent}</tbody>`;
};

// ================ Open Edit Popup ===================
function openEditOrderPopup(event) {
    const orderId = event.target.getAttribute("data-orderId");
    const main = event.target.closest("main");
    const overlay = main.querySelector(".overlay");
    overlay.style.display = "block";

    $.ajax({
        url: `/WebBanQuanAo/admin/api/orders/${orderId}`,
        type: 'GET',
        dataType: 'json',
        cache: false,
        success: function (response) {
            const data = response.data;
            if (!data) {
                alert("Không tìm thấy đơn hàng.");
                return;
            }

            $("#edit-idOrder").val(data.id);
            $("#edit-firstNameOrder").val(data.firstName);
            const paymentId = mapPaymentMethodToId(data.paymentMethod);
            if (paymentId !== null) {
                $("#edit-paymentId").val(String(paymentId)).change();
            } else {
                console.warn("Không tìm thấy paymentId cho phương thức:", data.paymentMethod);
            }
            $("#edit-code").val(data.code);
            $("#edit-orderDate").val(data.orderDate);
            $("#edit-totalPrice").val(data.totalPrice);
            $("#edit-statusOrder").val(data.status);

            console.log("paymentId từ server:", data.paymentId);
            console.log("Kiểu:", typeof data.paymentId);
        },
        error: function (xhr, status, error) {
            console.error('Lỗi khi tải đơn hàng:', error);
            const message = xhr.responseJSON?.message || "Không thể tải đơn hàng. Lỗi không xác định từ server.";
            alert(message);
        }
    });
}

// =================== Delete Order ====================
function deleteOrder(event) {
    const orderId = event.target.getAttribute("data-orderId");

    if (confirm(`Bạn có chắc chắn muốn xóa đơn hàng với ID: ${orderId}?`)) {
        $.ajax({
            url: `/WebBanQuanAo/admin/api/orders/${orderId}`,
            type: 'DELETE',
            dataType: 'json',
            cache: false,
            success: function (response) {
                alert(response.message || "Đã xóa đơn hàng thành công.");
                fetchOrders();
            },
            error: function (xhr, status, error) {
                console.error('Lỗi khi xóa đơn hàng:', error);
                const message = xhr.responseJSON?.message || "Không thể xóa đơn hàng. Vui lòng thử lại sau.";
                alert(message);
            }
        });
    }
}

// =================== Save Edits ====================
function saveOrderEdits(event) {
    event.preventDefault();

    const order = {
        id: parseInt($("#edit-idOrder").val()),
        firstName: $("#edit-firstNameOrder").val(),
        paymentId: parseInt($("#edit-paymentId").val()),
        code: $("#edit-code").val(),
        orderDate: new Date($("#edit-orderDate").val()).toISOString(),
        totalPrice: parseFloat($("#edit-totalPrice").val()),
        status: parseInt($("#edit-statusOrder").val())
    };

    $.ajax({
        url: `/WebBanQuanAo/admin/api/orders/${order.id}`,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(order),
        cache: false,
        success: function (response) {
            alert(response.message || "Cập nhật thông tin đơn hàng thành công!");
            fetchOrders();
            hideOverlay();
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật thông tin đơn hàng:", error);
            console.error("Chi tiết lỗi:", xhr.responseText);
            const message = xhr.responseJSON?.message || "Không thể cập nhật thông tin đơn hàng. Lỗi không xác định từ server.";
            alert(message);
        }
    });
}
